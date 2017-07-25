package com.ejuzuo.web.controller.thirdlogin;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.webutil.common.CookieUtils;
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
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/30 0030.
 */

@Controller
@RequestMapping("/qq")
public class QQLoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(QQLoginController.class);

    private static String QQ_LOGIN_URL = "/qq/login/index";

    @Resource
    private MemberThirdLoginService memberThirdLoginService;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberOperLogService memberOperLogService;

    @Resource
    private MemberPointLogService memberPointLogService;

    @Resource
    private DesignerService designerService;

    @Resource
    private ShoppingCarService shoppingCarService;

    @Resource
    private MemberPointService memberPointService;

    @Resource
    private MemberVipService memberVipService;

    @Resource
    private MsgRecordService msgRecordService;    

    @RequestMapping("/login/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 1：判断是否登录  已登录 跳首页
         */
        if (isLogin(request)) {
            return "redirect:/index";
        }

        response.setContentType("text/html;charset=utf-8");

        try {
            String authorizeURL = new Oauth().getAuthorizeURL(request);
            return "redirect:" + authorizeURL;
        } catch (QQConnectException e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }


    @RequestMapping("/callback")
    public String callback(HttpServletResponse response, HttpServletRequest request) {
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            String accessToken = null,
                    openID = null;

            if (accessTokenObj.getAccessToken().equals("")) {
                logger.info("qq登录回调，没有获取用户的accessToke");
                return "redirect:" + QQ_LOGIN_URL;
            }

            accessToken = accessTokenObj.getAccessToken();

            // 利用获取到的accessToken 去获取当前用的openid -------- start
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();

            logger.info("qq登录 openId为:[{}] 登录成功!", openID);


            MemberThirdLoginOption memberThirdLoginOption = new MemberThirdLoginOption();
            memberThirdLoginOption.setThirdId(openID);
            memberThirdLoginOption.setThirdType(ThirdType.QQ.getIndex());

            ModelResult<MemberThirdLogin> modelResult = memberThirdLoginService.queryByOption(memberThirdLoginOption);
            if (!modelResult.isSuccess()) {
                logger.info("查询第三方:[QQ]用户 openId:[{}]:异常:[{}]", openID, modelResult.getErrorMsg());
                return null;
            }

            Member memberFromDB = null;
            if (modelResult.getModel() != null) {
                logger.info("查询第三方:[QQ]用户 openId:[{}]:已经存在了" , openID);
                memberFromDB = memberService.queryByMemberId(modelResult.getModel().getMemberId()).getModel();

                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);

                Member member = new Member();
                member.setId(memberFromDB.getId());
                member.setNickName(qzoneUserInfo.getUserInfo().getNickname());
                memberService.updateById(member);

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

                //送积分,登录暂时不送积分，commented by xmm 2016/08/07
                //sendMemberPoint(memberFromDB.getId(), MemberPointLogTransType.LOGIN);

                //购物车
                buyCar(memberFromDB.getId(), request);

                return "redirect:/index";
            }

            UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);

            Member member = new Member();
            member.setAccount(UUIDUtils.getRandomString().toLowerCase());
            member.setNickName(qzoneUserInfo.getUserInfo().getNickname());
            member.setPassword("123456");
            member.setActiveStatus(1);
            member.setStatus(Status.STATUS.getIndex());
            member.setVerified(0);
            memberFromDB = memberService.saveMember(member).getModel();

            MemberThirdLogin memberThirdLogin = new MemberThirdLogin();
            memberThirdLogin.setMemberId(memberFromDB.getId());
            memberThirdLogin.setThirdType(ThirdType.QQ.getIndex());
            memberThirdLogin.setThirdId(openID);
            modelResult = memberThirdLoginService.save(memberThirdLogin);
            if (!modelResult.isSuccess()) {
                logger.info("用户:[QQ]来自第三方:[{}]登录 异常信息[{}]", memberFromDB.getId(), modelResult.getErrorMsg());
                return "redirect:/index" ;
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

            //注册送积分
            sendMemberPoint(memberFromDB.getId(), MemberPointLogTransType.REGISTER);

            //创建vip
            createVip(memberVo);

            //购物车
            buyCar(memberFromDB.getId(), request);
            
            //发送个人消息
            logger.info("memberId [{}] QQ注册成功，发送个人消息", memberFromDB.getId());
            String msgContent = "您好！您已成功注册为巨作网的新会员，平台将赠送您50积分，请注意查看后台积分记录，请尽快完善资料，平台将再次赠送您50积分！";
            msgRecordService.addPersonMsg(memberFromDB.getId(), msgContent);

            return "redirect:/index";
        } catch (Exception e) {
            logger.info("QQ登录 回调方法 报错:{}", e.getMessage(), e);
            e.printStackTrace();
        }

        return "redirect:/index";
    }


    //写cookie 写cache
    private void loginMember(MemberVo memberVo, HttpServletRequest request, HttpServletResponse response) {
        String uuidStr = UUIDUtils.getUuidStr();
        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
        CookieUtils.setCookie(request, response, globalParam.getUuidCookie(), uuidStr, globalParam.getMemberLoginCookieTime());
        logger.info("QQ登录 用户:[{}] 写cookie 写cache 成功", memberVo.getId());
    }

    //写操作日志
    private void saveOperLog(Integer memberId, MemberOperLogType memberOperLogType) {
        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(memberId);
        memberOperLog.setOperType(memberOperLogType.getIndex());
        memberOperLog.setRemark(memberOperLogType.getDescription());
        memberOperLogService.save(memberOperLog);
        logger.info("用户:[{}], 写操作日志成功", memberId);
    }


    //生成积分记录 送积分
    private void sendMemberPoint(Integer memberId, MemberPointLogTransType memberPointLogTransType) {
        MemberPoint tempMemberPoint = memberPointService.selectByMemberId(memberId).getModel();
        if (tempMemberPoint == null) {
            MemberPoint memberPoint = new MemberPoint();
            memberPoint.setMemberId(memberId);
            memberPoint.setBalance(0);
            memberPoint.setRemark("生成钱包");
            memberPointService.save(memberPoint);
        }
                      
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setTransType(memberPointLogTransType.getIndex());
        memberPointLogService.sendPoint(memberPointLog);
        logger.info("用户:[{}], 送积分业务:[{}] 成功", memberId, memberPointLogTransType.getDescription());	
    }

    //生成vip记录
    private void createVip(MemberVo memberVo) {
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


}
