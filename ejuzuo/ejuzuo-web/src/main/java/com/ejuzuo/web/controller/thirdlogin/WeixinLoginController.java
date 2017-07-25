package com.ejuzuo.web.controller.thirdlogin;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.webutil.common.CookieUtils;
import com.alibaba.fastjson.JSON;
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
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/25 0025.
 */
@Controller
@RequestMapping("/weixin")
public class WeixinLoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WeixinLoginController.class);
    //授权登录
    private String WEIXIN_GET_CODE_URL = "https://open.weixin.qq.com/connect/qrconnect?response_type=code&scope=snsapi_login&appid=%s&redirect_uri=%s";
    //获取token
    private String WEIXIN_API_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    //获取用户个人信息
    private String WEIXIN_API_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
    //微信登录的url
    private static String WEIXIN_LOGIN_URL = "http://www.ejuzuo.com/weixin/login/wx/index";
    //微信开放平台回调地址
    private static String WEIXIN_OPEN_CALLBACK_URL = "http://www.ejuzuo.com/weixin/login/wx/callback";

    @Resource
    private MemberThirdLoginService memberThirdLoginService;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberPointLogService memberPointLogService;

    @Resource
    private MemberVipService memberVipService;

    @Resource
    private ShoppingCarService shoppingCarService;

    @Resource
    private MemberPointService memberPointService;

    @Resource
    private MemberOperLogService memberOperLogService ;

    @Resource
    private DesignerService designerService ;
    
    @Resource
    private MsgRecordService msgRecordService;   

    @Value("${wx_web_appid}")
    private String APPID;
    @Value("${wx_web_app_secret}")
    private String APP_SECRET;


    @RequestMapping("/login/wx/index")
    public String index(HttpServletRequest request) {
        /**
         * 1：判断是否登录  已登录 跳首页
         */
        if (isLogin(request)) {
            return "redirect:/index";
        }

        String loginUrl = String.format(WEIXIN_GET_CODE_URL, APPID, WEIXIN_OPEN_CALLBACK_URL);
        return "redirect:" + loginUrl;
    }

    @RequestMapping("/login/wx/callback")
    public String callBack(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        if (StringUtils.isBlank(code)) {
            return "redirect:" + WEIXIN_LOGIN_URL;
        }

        try {
            HttpClient client = new HttpClient();
            String getTokenUrl = String.format(WEIXIN_API_TOKEN_URL, APPID, APP_SECRET, code);
            GetMethod tokenMethod = new GetMethod(getTokenUrl);
            tokenMethod.getParams().setContentCharset("UTF-8");
            client.executeMethod(tokenMethod);
            String responseResult = tokenMethod.getResponseBodyAsString(); //响应结果
            JSONObject jsonObject = JSON.parseObject(responseResult);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            String unionid = jsonObject.getString("unionid");
            if (StringUtils.isBlank(access_token)) {
                logger.info("{msg:微信登录，获取token失败，不能为空}");
                return "redirect:" + WEIXIN_LOGIN_URL;
            }

            if (StringUtils.isBlank(openid) || StringUtils.isBlank(unionid)) {
                logger.info("{msg:微信登录，获取openid或unionid失败，不能为空}");
                return "redirect:" + WEIXIN_LOGIN_URL;
            }

            MemberThirdLoginOption memberThirdLoginOption = new MemberThirdLoginOption();
            memberThirdLoginOption.setThirdId(openid);
            memberThirdLoginOption.setThirdType(ThirdType.WEICAT.getIndex());
            ModelResult<MemberThirdLogin> modelResult = memberThirdLoginService.queryByOption(memberThirdLoginOption);
            if (!modelResult.isSuccess()) {
                logger.info("查询第三方:[微信], unionid:[{}], 异常", unionid, modelResult.getErrorMsg());
                return "redirect:" + WEIXIN_LOGIN_URL;
            }
            MemberThirdLogin memberThirdLogin = modelResult.getModel();
            if (memberThirdLogin != null) {
                logger.info("老用户:[{}] 通过第三方[微信],登录成功", memberThirdLogin.getMemberId());
                Member member = memberService.queryByMemberId(memberThirdLogin.getMemberId()).getModel();

                Member memberTemp = new Member();
                memberTemp.setId(member.getId());

                String getUserInfoUrl = String.format(WEIXIN_API_USER_INFO_URL, access_token , openid);
                tokenMethod = new GetMethod(getUserInfoUrl);
                tokenMethod.getParams().setContentCharset("UTF-8");
                client.executeMethod(tokenMethod);
                responseResult = tokenMethod.getResponseBodyAsString(); //响应结果
                jsonObject = JSON.parseObject(responseResult);

                memberTemp.setNickName(jsonObject.getString("nickname"));

                memberService.updateById(memberTemp);

                //写操作日志
                saveOperLog(member.getId(), MemberOperLogType.LOGIN);

                //写登录cookie
                MemberVo memberVo = new MemberVo();
                try {
                    BeanUtils.copyProperties(memberVo, member);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                memberVo.setVip(false);
                memberVo.setDesigner(false);
                memberVo.setThird(true);
                MemberVip memberVip = memberVipService.judgeVipByMemberId(member.getId()).getModel();
                if(memberVip != null){
                    memberVo.setVip(true);
                }

                MemberPoint memberPoint = memberPointService.selectByMemberId(member.getId()).getModel();
                memberVo.setBalance(new BigDecimal(memberPoint.getBalance()));

                Designer designer = designerService.queryByMemberId(member.getId()).getModel();
                if(designer != null && designer.getStatus() == DesignerStatus.shenhetongguo.getIndex()){
                    memberVo.setDesigner(true);
                }

                loginMember(memberVo, request , response);

                //送积分,登录暂时不送积分，commented by xmm 2016/08/07
                //sendMemberPoint(member.getId(), MemberPointLogTransType.LOGIN);

                //清购物车
                buyCar(member.getId(), request);

                return "redirect:/index";
            }

            String getUserInfoUrl = String.format(WEIXIN_API_USER_INFO_URL, access_token , openid);
            tokenMethod = new GetMethod(getUserInfoUrl);
            tokenMethod.getParams().setContentCharset("UTF-8");
            client.executeMethod(tokenMethod);
            responseResult = tokenMethod.getResponseBodyAsString(); //响应结果
            jsonObject = JSON.parseObject(responseResult);

            //生成member
            Member tempMember = new Member();
            tempMember.setAccount(UUIDUtils.getRandomString().toLowerCase());
            tempMember.setNickName(jsonObject.getString("nickname"));
            tempMember.setPassword("123456");
            tempMember.setActiveStatus(1);
            tempMember.setStatus(Status.STATUS.getIndex());
            tempMember.setVerified(0);
            tempMember = memberService.saveMember(tempMember).getModel();

            memberThirdLogin = new MemberThirdLogin();
            memberThirdLogin.setMemberId(tempMember.getId());
            memberThirdLogin.setThirdType(ThirdType.WEICAT.getIndex());
            memberThirdLogin.setThirdId(openid);
            modelResult = memberThirdLoginService.save(memberThirdLogin);
            if (!modelResult.isSuccess()) {
                logger.info("用户:[{}]来自第三方:[{微信}]登录 异常:[{}]", tempMember.getId(), modelResult.getErrorMsg());
                return "redirect:/index";
            }

            //写操作日志
            saveOperLog(tempMember.getId(), MemberOperLogType.REGISTE);

            //写登录cookie
            MemberVo memberVo = new MemberVo();
            try {
                BeanUtils.copyProperties(memberVo, tempMember);
            } catch (Exception e) {
                e.printStackTrace();
            }

            memberVo.setVip(false);
            memberVo.setDesigner(false);
            memberVo.setThird(true);
            memberVo.setBalance(new BigDecimal(0));
            loginMember(memberVo, request, response);
            logger.info("新生成的用户:[] 通过第三方[微信],登录成功", memberThirdLogin.getMemberId());

            //送积分
            sendMemberPoint(tempMember.getId(), MemberPointLogTransType.REGISTER);

            //创建vip
            createVip(memberVo);

            //购物车
            buyCar(tempMember.getId(), request);
            
            //发送个人消息
            logger.info("memberId [{}] weixin注册成功，发送个人消息", tempMember.getId());
            String msgContent = "您好！您已成功注册为巨作网的新会员，平台将赠送您50积分，请注意查看后台积分记录，请尽快完善资料，平台将再次赠送您50积分！";
            msgRecordService.addPersonMsg(tempMember.getId(), msgContent);

            return "redirect:/index";
        } catch (Exception e) {
            logger.info("{msg:微信登录，异常信息：" + e+ "}", e);
            model.addAttribute("errorMessage", "微信登录，系统繁忙，请稍后再试");
        }
        return "redirect:"+ WEIXIN_LOGIN_URL;
    }


    private boolean isLogin(HttpServletRequest request) {
        String uuidValue = CookieUtils.getCookieValue(request, globalParam.getUuidCookie());
        if (!StringUtils.isNotBlank(uuidValue)) {
            return false;
        }
        MemberVo memberVo = memcachedClient.get(uuidValue);
        if (memberVo == null) {
            return false;
        }
        logger.info("用户id:[{}], 已经登录了", memberVo.getId());
        return true;
    }



    //写cookie 写cache
    private void loginMember(MemberVo memberVo, HttpServletRequest request, HttpServletResponse response){
        String uuidStr = UUIDUtils.getUuidStr();
        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
        CookieUtils.setCookie(request, response, globalParam.getUuidCookie(), uuidStr, globalParam.getMemberLoginCookieTime());
        logger.info("微信登录 用户:[{}] 写cookie 写cache 成功", memberVo.getId());
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
