package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

public class MemberSponsorLogOption implements Serializable {
    private static final long serialVersionUID = 1454454448771232159L;

    private Integer memberId;
    
    private String account;
    
    private Integer sponsorType; // 赞助类型[4为49元档，5为99元档]
    
    private Integer paymentMethod; // 支付方式[0:支付宝支付]
    
    private String orderNo; // 我方支付订单号
    
    private String payOrderNo; // 支付平台订单号
    
    private Integer status; // 支付状态[0:待支付，1:支付成功，2:支付失败]

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar startTime ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endTime ;

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
		this.account = account;
	}

	public Integer getSponsorType() {
		return sponsorType;
	}

	public void setSponsorType(Integer sponsorType) {
		this.sponsorType = sponsorType;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
}
