package com.ejuzuo.web.controller.thirdlogin;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.webutil.common.CookieUtils;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.MemberVipType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.constants.ThirdType;
import com.ejuzuo.common.domain.*;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.option.MemberThirdLoginOption;
import com.ejuzuo.common.service.*;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.vo.MemberVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/24 0024.
 */
@Controller
@RequestMapping("/third")
public class MemberThirdController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MemberThirdController.class);

    @Resource
    private MemberThirdLoginService memberThirdLoginService;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberOperLogService memberOperLogService;

    @Resource
    private MemberPointLogService memberPointLogService;

    @Resource
    private DesignerService designerService ;

    @Resource
    private ShoppingCarService shoppingCarService;

    @Resource
    private MemberPointService memberPointService;

    @Resource
    private MemberVipService memberVipService;


    @RequestMapping("/qq/callback")
    public String qqCallback() {


        return "/third/thirdLogin";
    }

    @RequestMapping("/save/{thirdType}/{thirdId}")
    @ResponseBody
    public JSONObject saveThirdId(@PathVariable Integer thirdType, @PathVariable String thirdId,
                                  HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        ThirdType tempThirdType = ThirdType.findByIndex(thirdType);
        if (tempThirdType == null) {
            logger.info("第三方:[{}], 不存在", tempThirdType.getDescription());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "第三方不存在");
            return jsonObject;
        }
        MemberThirdLoginOption memberThirdLoginOption = new MemberThirdLoginOption();
        memberThirdLoginOption.setThirdId(thirdId);
        memberThirdLoginOption.setThirdType(tempThirdType.getIndex());

        ModelResult<MemberThirdLogin> modelResult = memberThirdLoginService.queryByOption(memberThirdLoginOption);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            logger.info("查询第三方:[{}]用户 openId:[{}]:异常:[{}]", tempThirdType.getDescription(), thirdId, modelResult.getErrorMsg());
            return jsonObject;
        }
        Member memberFromDB = null;
        if (modelResult.getModel() != null) {
            jsonObject.put("code", 222);
            jsonObject.put("msg", "已经存在了");
            logger.info("查询第三方:[{}]用户 openId:[{}]:已经存在了", tempThirdType.getDescription(), thirdId);
            memberFromDB = memberService.queryByMemberId(modelResult.getModel().getMemberId()).getModel();

            //写操作日志 送积分
            saveOperLog(memberFromDB.getId(), MemberOperLogType.LOGIN);

            //写cookie 写cache
            MemberVo memberVo = new MemberVo();
            try {
                BeanUtils.copyProperties(memberVo, memberFromDB);
            } catch (Exception e) {
                e.printStackTrace();
            }

            memberVo.setVip(false);
            memberVo.setDesigner(false);
            memberVo.setThird(true);
            MemberVip memberVip = memberVipService.judgeVipByMemberId(memberFromDB.getId()).getModel();
            if(memberVip != null){
                memberVo.setVip(true);
            }

            MemberPoint memberPoint = memberPointService.selectByMemberId(memberFromDB.getId()).getModel();
            memberVo.setBalance(new BigDecimal(memberPoint.getBalance()));

            Designer designer = designerService.queryByMemberId(memberFromDB.getId()).getModel();
            if(designer != null && designer.getStatus() == DesignerStatus.shenhetongguo.getIndex()){
                memberVo.setDesigner(true);
            }
            loginMember(memberVo, request, response);

            //送积分
            sendMemberPoint(memberFromDB.getId(), MemberPointLogTransType.LOGIN);

            //购物车
            buyCar(memberFromDB.getId(), request);

        } else {
            Member member = new Member();
            member.setAccount(UUIDUtils.getRandomString());
            member.setNickName(UUIDUtils.getRandomString());
            member.setPassword("123456");
            member.setActiveStatus(1);
            member.setStatus(Status.STATUS.getIndex());
            member.setVerified(0);
            memberFromDB = memberService.saveMember(member).getModel();

            MemberThirdLogin memberThirdLogin = new MemberThirdLogin();
            memberThirdLogin.setMemberId(memberFromDB.getId());
            memberThirdLogin.setThirdType(tempThirdType.getIndex());
            memberThirdLogin.setThirdId(thirdId);
            modelResult = memberThirdLoginService.save(memberThirdLogin);
            if (!modelResult.isSuccess()) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", modelResult.getErrorMsg());
                logger.info("用户:[{}]来自第三方:[{}]登录 异常信息[{}]", memberFromDB.getId(), tempThirdType.getDescription(), modelResult.getErrorMsg());
                return jsonObject;
            }

            //写操作日志 送积分
            saveOperLog(memberFromDB.getId(), MemberOperLogType.REGISTE);

            //写cookie 写缓存
            MemberVo memberVo = new MemberVo();
            try {
                BeanUtils.copyProperties(memberVo, memberFromDB);
            } catch (Exception e) {
                e.printStackTrace();
            }

            memberVo.setVip(false);
            memberVo.setDesigner(false);
            memberVo.setThird(false);
            memberVo.setBalance(new BigDecimal(0));
            loginMember(memberVo, request, response);

            //送积分
            sendMemberPoint(memberFromDB.getId(), MemberPointLogTransType.LOGIN);

            //创建vip
            createVip(memberVo);

            //购物车
            buyCar(memberFromDB.getId(), request);
        }

        jsonObject.put("code", 222);
        return jsonObject;
    }

    //写cookie 写cache
    private void loginMember(MemberVo memberVo, HttpServletRequest request, HttpServletResponse response){
        String uuidStr = UUIDUtils.getUuidStr();
        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
        CookieUtils.setCookie(request, response, globalParam.getUuidCookie(), uuidStr, globalParam.getMemberLoginCookieTime());
    }

    //写操作日志
    private void saveOperLog(Integer memberId, MemberOperLogType memberOperLogType){
        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(memberId);
        memberOperLog.setOperType(memberOperLogType.getIndex());
        memberOperLog.setRemark(memberOperLogType.getDescription());
        memberOperLogService.save(memberOperLog);
        logger.info("用户:[{}], 写操作日志成功", memberId);
    }


    //生成积分记录 送积分
    private void sendMemberPoint(Integer memberId, MemberPointLogTransType memberPointLogTransType){
        MemberPoint tempMemberPoint = memberPointService.selectByMemberId(memberId).getModel();
        if (tempMemberPoint == null) {
            MemberPoint memberPoint = new MemberPoint();
            memberPoint.setMemberId(memberId);
            memberPoint.setBalance(0);
            memberPoint.setRemark("生成钱包");
            memberPointService.save(memberPoint);
        }

        /****登录后暂时不送积分
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setTransType(memberPointLogTransType.getIndex());
        memberPointLogService.sendPoint(memberPointLog);
        logger.info("用户:[{}], 送积分业务:[{}] 成功", memberId, memberPointLogTransType.getDescription());
        ***/
    }

    //生成vip记录
    private void createVip(MemberVo memberVo){
        MemberVip memberVip = new MemberVip();
        memberVip.setMemberId(memberVo.getId());
        memberVip.setUpdator(memberVo.getAccount());
        memberVip.setViped(MemberVipType.IS_VIP.getIndex());
        memberVip.setStartTime(DateUtil.getTheDayZero());
        memberVip.setEndTime(DateUtil.getTheDayZero());
        memberVip.setCreator(memberVo.getAccount());
        memberVip.setRemark("初始化");
        memberVipService.saveMemberVip(memberVip);

        logger.info("用户:[{}], 生成vip记录 成功", memberVo.getId());
    }

    private void buyCar(Integer memberId, HttpServletRequest request) {
        String buyCarUUID = CookieUtils.getCookieValue(request, GlobalParam.buyCar);
        if (StringUtils.isBlank(buyCarUUID)) {
            logger.info("从cookie中拿不到购物车的值");
            return;
        }

        List<Integer> goodsCar = memcachedClient.get(buyCarUUID);
        if (goodsCar == null) {
            logger.info("购物车 从缓存中拿值为null");
            return;
        }
        try {
            memcachedClient.delete(buyCarUUID);
            shoppingCarService.saveList(goodsCar, memberId);
            logger.info("用户:[{}], 清理购物车 成功", memberId);
        } catch (Exception e) {
            logger.error("保存列表", e);
        }
    }


}
