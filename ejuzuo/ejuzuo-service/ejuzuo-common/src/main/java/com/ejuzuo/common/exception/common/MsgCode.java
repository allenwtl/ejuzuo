package com.ejuzuo.common.exception.common;

import java.io.Serializable;

/**
 * 
 * @author fx
 *
 */
public class MsgCode implements Serializable {

    private static final long serialVersionUID = -2892956957010575101L;

    /**错误代码**/
    private String code;

    /**错误代码对应消息**/
    private String msg;
     
    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MsgCode(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }


    //充值错误代码
    public static final MsgCode CHANNEL_NOT_NULL = new MsgCode("channel.not.null", "充值渠道不能空");
    public static final MsgCode SELLCLIENT_NOT_NULL = new MsgCode("sellclient.not.null", "充值客户端不能空");
    public static final MsgCode PAYACCOUNT_NOT_NULL = new MsgCode("payaccount.not.null", "充值用户名不能空");
    public static final MsgCode PAY_MEMBER_NOT_EXIST = new MsgCode("pay.member.not.exist", "充值用户不存在");
    public static final MsgCode SIGN_NOT_NULL = new MsgCode("sign.not.null", "签名不能空");
    public static final MsgCode AMOUNT_NOT_LESS_THEN_ZERO = new MsgCode("amount.not.lessthen.zero", "充值金额须大于0");
    public static final MsgCode SIGN_FAIL = new MsgCode("sign.fail", "签名验证失败");
    public static final MsgCode CARD_TYPE_NOT_NULL = new MsgCode("card.type.not.null", "卡类型不能为空");
    public static final MsgCode CARD_NUM_NOT_NULL = new MsgCode("card.number.not.null", "充值卡号不能为空");
    public static final MsgCode MONEY_NOT_LESSTHEN_20 = new MsgCode("money.not.lessthen.20", "输入的金额不能小于20");
    public static final MsgCode SUC_URL_NOT_NULL = new MsgCode("suc.url.not.null", "充值成功url不能为空");
    public static final MsgCode FAIL_URL_NOT_NULL = new MsgCode("fail.url.not.null", "充值失败url不能为空");
    public static final MsgCode NOT_EXIST_PARTNER = new MsgCode("not.exist.partner", "充值合作商错误");
    public static final MsgCode CHARGENO_NOT_NULL = new MsgCode("chargeNo.not.null", "充值流水不能为空");
    public static final MsgCode CHARGE_LOG_NOT_FOUND = new MsgCode("charge.log.not.found", "未找到充值订单");
    public static final MsgCode DOWNLOADINFO_NOT_FOUND = new MsgCode("downloadinfo.not.found", "未找到订单信息");
    public static final MsgCode CHARGE_LOG_NOT_NULL = new MsgCode("charge.log.not.null", "订单对象不能为空");
    public static final MsgCode MEMBER_NOT_NULL = new MsgCode("member.not.null", "订单用户信息不能为空");
    public static final MsgCode RETURN_LOG_NOT_NULL = new MsgCode("return.log.not.null", "充值返回对象不能为空");
    public static final MsgCode SUCC_AMOUNT_NOT_NULL = new MsgCode("succ.amount.not.null", "充值成功金额未设置");
    public static final MsgCode SUCC_AMOUNT_NOT_MATCH = new MsgCode("succ.amount.not.match", "充值成功金额数据不匹配");
    public static final MsgCode PAY_DATA_ERROR = new MsgCode("pay.data.error", "网关数据被篡改");
    public static final MsgCode PAY_BANKINFO_INCORRECT = new MsgCode("pay.bankinfo.incorrect", "银行卡信息不正确");
    public static final MsgCode PAY_CARD_TRANSTIME_NOT_CORRECT = new MsgCode("pay.card.TransTime.not.correct", "该银行卡交易时间受限");
    public static final MsgCode PAY_BANKCARD_IN_BLACKLIST = new MsgCode("pay.bankcard.in.blacklist", "银行卡被列入黑名单，拒绝交易");
    public static final MsgCode PAY_BANKCARD_TIMEOUT_EXCEPTION = new MsgCode("pay.status.exception", "交易超时，请重试");
    public static final MsgCode PAY_STATUS_EXCEPTION = new MsgCode("pay.status.exception", "充值状态不匹配");
    public static final MsgCode PAY_NOT_KNOW_EXCEPTION = new MsgCode("pay.not.know.exception", "未知异常");
    public static final MsgCode PAY_JXCARD_NOT_SUCCESS = new MsgCode("pay.jxcard.not.success", "捷迅支付充值响应异常:充值請求失敗");
    public static final MsgCode PAY_BENEFICIARY_NOT_EXIST = new MsgCode("pay.beneficiary.not.exist", "充值受益人不存在");
    public static final MsgCode PAY_BENEFICIARY_ACCOUNT_NOT_NULL = new MsgCode("pay.beneficiary.account.not.null", "充值受益人用户名不能为空");
    public static final MsgCode PAYMENT_RETURN_DATA_ERROR = new MsgCode("payment.return.data.error", "支付返回数据异常");
    public static final MsgCode PAY_CARD_SUBMIT_FAIL = new MsgCode("pay.card.submit.fail", "卡信息提交失败");
    public static final MsgCode REFFER_ERROR = new MsgCode("reffer.error", "请重新从网站发起充值请求");
    public static final MsgCode PAY_COMBINE_ERROR = new MsgCode("pay.combine.error", "拼接请求串出错");
    public static final MsgCode PAY_CARDTYPE_ERROR = new MsgCode("pay.cardtype.error", "银行卡类型出错");
    public static final MsgCode PAY_BACNKCO_ERROR = new MsgCode("pay.bacnkco.error", "银行编码不能为空");
    public static final MsgCode PAY_PARA_BLANK_ERROR = new MsgCode("pay.bacnkco.error", "响应串必要参数为空");
    public static final MsgCode REFUND_SAVE_ERROR = new MsgCode("refund.save.error", "保存外部退款单失败");



}
