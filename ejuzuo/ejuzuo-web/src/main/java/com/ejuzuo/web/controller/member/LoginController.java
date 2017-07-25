package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.webutil.common.CookieUtils;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.*;
import com.ejuzuo.common.domain.type.MemberLockType;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.service.*;
import com.ejuzuo.common.util.MD5;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.annotation.AvoidDuplicateSubmitAnnotation;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.ExecutorUtil;
import com.ejuzuo.web.vo.MemberVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/3/26 0026.
 */

@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

    @Resource
    private MemberService memberService;

    @Resource
    private MemberVipService memberVipService;

    @Resource
    private DesignerService designerService;

    @Resource
    private ShoppingCarService shoppingCarService;

    @Resource
    private MemberOperLogService memberOperLogService;

    @Resource
    private MemberPointLogService memberPointLogService;

    @Resource
    private MemberPointService memberPointService;

    @AvoidDuplicateSubmitAnnotation(needSaveToken = true)
    @RequestMapping("/toLogin")
    public String toLogin(Model model) {
        model.addAttribute("menu", "login");
        return "/member/login";
    }

    @AvoidDuplicateSubmitAnnotation(needRemoveToken = true)
    @RequestMapping(value = "/in", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(Member member, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        ModelResult<Member> memberModelResult = memberService.queryByAccount(member.getAccount());
        if (!memberModelResult.isSuccess()) {
            logger.info("系统查询报错");
            jsonObject.put("code", 444);
            jsonObject.put("msg", memberModelResult.getErrorMsg());
            return jsonObject;
        }

        Member memberFromDB = memberModelResult.getModel();

        if (memberFromDB == null) {
            logger.info("帐号:{} 查询不到这个用户", member.getAccount());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "此用户名不存在");
            return jsonObject;
        }

        if (!memberFromDB.getPassword().equals(MD5.md5Encode(member.getPassword()))) {
            logger.info("帐号:{} 密码不对");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "用户名或者密码错误");
            return jsonObject;
        }

        if (memberFromDB.getStatus() == Status.UN_STATUS.getIndex()) {
            logger.info("帐号:{} 是无效用户");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "此用户名是无效用户！请联系客服");
            return jsonObject;
        }

        if (memberFromDB.getLocked() == MemberLockType.locked.getIndex()) {
            logger.info("帐号:{} 已经被锁住了");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "此用户名被拉黑了！请联系客服");
            return jsonObject;
        }

        final String buyCarUUID = CookieUtils.getCookieValue(request, GlobalParam.buyCar);

        ExecutorUtil.fixedThread().execute(new Runnable() {
            @Override
            public void run() {
                asnyAfterLogin(memberFromDB, buyCarUUID);
            }
        });

        String uuidStr = UUIDUtils.getUuidStr();

        MemberVo memberVo = new MemberVo();
        try {
            BeanUtils.copyProperties(memberVo, memberFromDB);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        MemberVip memberVip = memberVipService.judgeVipByMemberId(memberFromDB.getId()).getModel();
        if (memberVip != null) {
            memberVo.setVip(true);
        } else {
            memberVo.setVip(false);
        }

        Designer designer = designerService.queryByMemberId(memberFromDB.getId()).getModel();
        if (designer != null /***&& designer.getStatus()==2***/) { //完善资料自动升级为高级会员
            memberVo.setDesigner(true);
            memberVo.setDesignerId(designer.getId());
        } else {
            memberVo.setDesigner(false);
        }
        memberVo.setThird(false);
        MemberPoint memberPoint = memberPointService.selectByMemberId(memberVo.getId()).getModel();
        memberVo.setBalance(new BigDecimal(memberPoint.getBalance()));
        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
        CookieUtils.setCookie(request, response, globalParam.getUuidCookie(), uuidStr, globalParam.getMemberLoginCookieTime());
        logger.info("用户:{}登录 校验通过", memberFromDB.getAccount());
        jsonObject.put("code", 222);
        return jsonObject;
    }

    @Login
    @RequestMapping("/afterLogin")
    public String afterLogin(HttpServletRequest request) {
        String cookieURI = CookieUtils.getCookieValue(request, GlobalParam.uri);
        if (StringUtils.isBlank(cookieURI)) {
            return "redirect:/index";
        }
        String uri = memcachedClient.get(cookieURI);
        if (uri == null) {
            return "redirect:/index";
        }
        memcachedClient.delete(cookieURI);
        return "redirect:" + uri;
    }


    @Login
    @RequestMapping("/out")
    public String out(HttpServletRequest request) {
        deleteMemberInCache(request);
        return "/member/login";
    }

    @Login
    @RequestMapping("/asyncOut")
    @ResponseBody
    public JSONObject asyncOut(HttpServletRequest request) {
        deleteMemberInCache(request);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 222);
        return jsonObject;
    }


    private void asnyAfterLogin(Member memberFromDB, String buyCarUUID) {
        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(memberFromDB.getId());
        memberOperLog.setOperType(MemberOperLogType.LOGIN.getIndex());
        memberOperLog.setRemark("用户登录");
        memberOperLogService.save(memberOperLog);

        /****登录后暂时不送积分
         MemberPointLog memberPointLog = new MemberPointLog();
         memberPointLog.setMemberId(memberFromDB.getId());
         memberPointLog.setTransType(MemberPointLogTransType.LOGIN.getIndex());
         memberPointLogService.sendPoint(memberPointLog);***/

        if (StringUtils.isBlank(buyCarUUID)) {
            logger.info("从cookie中拿不到购物车值");
            return;
        }

        List<Integer> goodsCar = memcachedClient.get(buyCarUUID);
        if (goodsCar == null) {

            logger.info("从缓存中拿值为null");
            return;
        }
        try {
            memcachedClient.delete(buyCarUUID);
            shoppingCarService.saveList(goodsCar, memberFromDB.getId());
        } catch (Exception e) {
            logger.error("保存列表", e);
        }
    }
}
