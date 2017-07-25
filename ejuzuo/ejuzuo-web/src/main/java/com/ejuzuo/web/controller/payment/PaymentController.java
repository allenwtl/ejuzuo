package com.ejuzuo.web.controller.payment;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.ChargeWay;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.PayStatus;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.MemberOperLog;
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.exception.common.PayException;
import com.ejuzuo.common.service.MemberOperLogService;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.service.MemberSponsorLogService;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.common.PayHelpBean;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.handler.AlipaymentWebHandler;
import com.ejuzuo.web.util.RendererUtils;
import com.ejuzuo.web.util.RequestUtil;
import com.ejuzuo.web.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/5/9 0009.
 */

@Controller
@RequestMapping("payment")
public class PaymentController extends BaseController {


    @Resource
    private MemberSponsorLogService memberSponsorLogService;

    @Resource
    private AlipaymentWebHandler alipaymentWebHandler;

    @Resource
    private MemberOperLogService memberOperLogService ;

    @Resource
    private MemberPointLogService memberPointLogService ;

    @Resource
    private MemberService memberService ;

    @Login
    @RequestMapping("/charge/{donateId}")
    public ModelAndView charge(@PathVariable Integer donateId, HttpServletRequest request, HttpServletResponse response) {

        Member member = getMember(request);

        if (donateId != MemberPointLogTransType.DONATE_ONE.getIndex() && donateId != MemberPointLogTransType.DONATE_TWO.getIndex()) {
            throw new PayException("赞助类型不匹配");
        }

        MemberPointLogTransType memberPointLogTransType = null;
        int amount = 0;
        if (donateId == MemberPointLogTransType.DONATE_ONE.getIndex()) {
            memberPointLogTransType = MemberPointLogTransType.DONATE_ONE;
            amount = 49;
        } else {
            memberPointLogTransType = MemberPointLogTransType.DONATE_TWO;
            amount = 99;
        }


        //保存赞助记录
        MemberSponsorLog memberSponsorLog = new MemberSponsorLog();
        memberSponsorLog.setOrderNo(UUIDUtils.getOrderNo());
        memberSponsorLog.setMemberId(member.getId());
        memberSponsorLog.setAccount(member.getAccount());
        memberSponsorLog.setAmount(amount);
        memberSponsorLog.setFee(0);
        memberSponsorLog.setPaymentMethod(ChargeWay.zfb_web.getIndex());
        memberSponsorLog.setRequestInfo(request.getQueryString());
        memberSponsorLog.setSponsorType(memberPointLogTransType.getIndex());
        memberSponsorLog = memberSponsorLogService.save(memberSponsorLog).getModel();

        //保存用户操作记录
        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(member.getId());
        memberOperLog.setOperType(MemberOperLogType.CHARGE.getIndex());
        memberOperLog.setRemark(memberPointLogTransType.getDescription());
        memberOperLogService.save(memberOperLog);

        //构建充值请求参数
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("exter_invoke_ip", RequestUtil.getUserIp(request));
        alipaymentWebHandler.createPostParam(parameterMap, memberSponsorLog);

        String redirectUrl = parameterMap.get("paymentUrl") + "?" + alipaymentWebHandler.createRedirectUrl(parameterMap);

        try {
            logger.info("GET方式直接跳转地址:[{}]", redirectUrl);
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            logger.error("充值业务--跳转地址失败 : [{}],[{}]", redirectUrl, e);
        }

        return null;
    }

    /**
     * @param request
     * @param response
     * @param redirect 是否跳转
     * @return
     */
    @RequestMapping("/chargeReturn.do")
    public ModelAndView chargeReturn(HttpServletRequest request, HttpServletResponse response, boolean redirect) {

        //获取参数
        Map<String, String> responseMap = StringUtil.getGMap(request.getParameterMap());
        logger.info("充值业务--chargeReturn原始参数{}", responseMap);


        responseMap.remove("redirect");
        //转换 充值返回过来的参数
        PayHelpBean bean = alipaymentWebHandler.notifyResponse(responseMap);

        /**
         * 判断充值是否成功
         */
        if (!bean.isPaySuccess()) {// 如果充值失败，则直接返回处理成功字符串:注意，此字符串是返回给第三方支付的,让合作方不要发通知了
            RendererUtils.renderText(bean.getSuccessCallback(), response);
            return null;
        }

        MemberSponsorLog memberSponsorLog = memberSponsorLogService.queryByChargeNo(bean.getChargerNo()).getModel();

        if (memberSponsorLog == null) {
            logger.info("充值业务--充值编号:[{}] 查询不到充值记录", bean.getChargerNo());
            return doReturnHandle(response, bean, redirect, false);
        }

        if (memberSponsorLog.getStatus() == PayStatus.PAY_SUCCESS.getIndex()) {
            logger.info("充值业务--订单编号:[{}] 已经支付成功了", bean.getChargerNo());
            return doReturnHandle(response, bean, redirect, true);
        }

        if (bean.getAmount().compareTo(new BigDecimal(memberSponsorLog.getAmount())) != 0) {
            logger.info("充值业务--订单编号:[{}] 返回的充值金额:[{}] 和充值订单的充值金额:[{}] 不相等", bean.getChargerNo(), bean.getAmount(), memberSponsorLog.getAmount());
            return doReturnHandle(response, bean, redirect, false);
        }

        /**
         * 业务代码：
         * 1：更新赞助表
         * 2：赠送积分
         */
        MemberSponsorLog tempLog = new MemberSponsorLog();
        tempLog.setId(memberSponsorLog.getId());
        tempLog.setMemberId(memberSponsorLog.getMemberId());
        tempLog.setStatus(PayStatus.PAY_SUCCESS.getIndex());
        tempLog.setPayOrderNo(bean.getThirdPayNo());
        tempLog.setPayTime(Calendar.getInstance());
        tempLog.setSponsorType(memberSponsorLog.getSponsorType());
        tempLog.setResponseInfo(JSONObject.toJSONString(responseMap).toString());
        ModelResult<MemberSponsorLog> modelResult = memberSponsorLogService.updateByCharge(tempLog);

        if (!modelResult.isSuccess()) {
            logger.info("充值返回--业务逻辑:[{}]", modelResult.getErrorMsg());
            return doReturnHandle(response, bean, redirect, false);
        }
        return doReturnHandle(response, bean, redirect, true);
    }

    //充值返回的处理
    private ModelAndView doReturnHandle(HttpServletResponse response, PayHelpBean bean, boolean redirect, boolean isSuccess) {
        if (redirect) {
            String url = null;
            try {
                url = "/member/center";
                response.sendRedirect(url);
            } catch (IOException e) {
                logger.error("充值业务--跳转地址失败 : [{}],[{}]", url, e);
            }
            return null;
        }

        String rendText = isSuccess ? bean.getSuccessCallback() : bean.getFailCallback();
        logger.info("充值业务--本系统充值编号:[{}], 业务处理:[{}], 返回内容:[{}]", bean.getChargerNo(), isSuccess, rendText);
        RendererUtils.renderText(rendText, response);
        return null;
    }


}
