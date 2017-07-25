package com.ejuzuo.web.handler;

import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.exception.common.MsgCode;
import com.ejuzuo.common.exception.common.PayException;
import com.ejuzuo.web.common.AlipayPropertyBean;
import com.ejuzuo.web.common.PayHelpBean;
import com.ejuzuo.web.util.AlipayUtil;
import com.ejuzuo.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/5/9 0009.
 */

@Component
public class AlipaymentWebHandler {

    private static final Logger logger = LoggerFactory.getLogger(AlipaymentWebHandler.class);

    public static final String CFT_INPUT_CHARSET = "utf-8";

    //商户名称
    public static final String payMentName = "北京巨作网络科技有限公司";

    @Resource
    private AlipayPropertyBean alipayPropertyBean;


    public void createPostParam(Map<String, String> paramMap, MemberSponsorLog memberSponsorLog) {
        paramMap.put("service", "create_direct_pay_by_user");    //接口名称
        paramMap.put("partner", alipayPropertyBean.getPartner());    //合作者身份ID
        paramMap.put("_input_charset", CFT_INPUT_CHARSET);    //参数编码字符集
        paramMap.put("notify_url", alipayPropertyBean.getNotifyUrl());    //服务器异步通知页面路径
        paramMap.put("return_url", alipayPropertyBean.getReturnUrl());    //页面跳转同步通知页面路径
        paramMap.put("out_trade_no", memberSponsorLog.getOrderNo());    //商户系统中唯一订单号
        paramMap.put("subject", payMentName);    //商品名称
        paramMap.put("total_fee", String.valueOf(memberSponsorLog.getAmount()));    //交易金额
        paramMap.put("seller_email", alipayPropertyBean.getSellerEmail());    //卖家支付宝帐号
        paramMap.put("payment_type", "1");    //收款类型
        paramMap.put("paymentUrl", alipayPropertyBean.getPaygateway());
    }

    public String createRedirectUrl(Map<String, String> paramMap){
        String sign_type = "MD5";
        String sign = AlipayUtil.buildMysign(paramMap, alipayPropertyBean.getKey());
        StringBuilder sb = new StringBuilder();
        /*sb.append(alipayPropertyBean.getPaygateway());*/

        List<String> keys = new ArrayList<String>(paramMap.keySet());

        for (int i = 0; i < keys.size(); ++i) {
            try {
                sb.append(keys.get(i)).append("=").append(URLEncoder.encode(paramMap.get(keys.get(i)), paramMap.get("_input_charset"))).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        sb.append("sign=").append(sign).append("&sign_type=").append(sign_type);

        return sb.toString() ;
    }


    public PayHelpBean notifyResponse(Map<String, String> responseMap){
        String notify_id = responseMap.get("notify_id");
        String parnter_id = alipayPropertyBean.getPartner();
        String url = alipayPropertyBean.getPaygateway() + "?service=notify_verify&notify_id=" + notify_id + "&partner=" + parnter_id;

        String responseTxt = RequestUtil.check(url);

        logger.info("验证请求的发送方是否为支付宝：{}", responseTxt);
        String mySign = AlipayUtil.sign(responseMap, alipayPropertyBean.getKey());

        if(!mySign.equals(responseMap.get("sign"))){
            throw new PayException(MsgCode.PAYMENT_RETURN_DATA_ERROR);
        }


        boolean paySuccess = "TRADE_FINISHED".equals(responseMap.get("trade_status"))
                || "TRADE_SUCCESS".equals(responseMap.get("trade_status"));
        String outTradeNo = responseMap.get("out_trade_no");
        logger.info("收到支付宝支付结果通知信息：订单号：[{}],支付结果[{}],", outTradeNo, paySuccess);
        String amount = responseMap.get("total_fee");
        BigDecimal successAmount = new BigDecimal(Double.valueOf(amount));
        String trade_no = responseMap.get("trade_no");
        PayHelpBean helpBean = new PayHelpBean(outTradeNo, successAmount, successAmount, "success", "fail",
                paySuccess, trade_no);
        return helpBean ;
    }

}
