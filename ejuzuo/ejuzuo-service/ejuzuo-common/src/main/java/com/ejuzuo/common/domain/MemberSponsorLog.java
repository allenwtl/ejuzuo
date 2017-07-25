package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class MemberSponsorLog  implements Serializable {
    private static final long serialVersionUID = -109534430240354832L;
    
    private Integer id;

    private Integer memberId;

    private String account;

    private Integer sponsorType; // 赞助类型[4为49元档，5为99元档]

    private Integer amount; // 支付金额[单位分]

    private Integer paymentMethod; // 支付方式[0:支付宝支付]

    private Integer fee; // 手续费[单位分]

    private String remark;

    private Calendar createTime;

    private Calendar expire; // 过期时间

    private String orderNo; // 我方支付订单号

    private String requestInfo; // 支付平台请求原始信息

    /** @see com.ejuzuo.common.constants.PayStatus */
    private Integer status; // 支付状态[0:待支付，1:支付成功，2:支付失败]

    private String responseInfo; // 支付平台响应原始信息

    private Calendar payTime; // 支付响应时间

    private String payOrderNo; // 支付平台订单号

    private Calendar updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(Integer sponsorType) {
        this.sponsorType = sponsorType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getExpire() {
        return expire;
    }

    public void setExpire(Calendar expire) {
        this.expire = expire;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo == null ? null : requestInfo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo == null ? null : responseInfo.trim();
    }

    public Calendar getPayTime() {
        return payTime;
    }

    public void setPayTime(Calendar payTime) {
        this.payTime = payTime;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
}