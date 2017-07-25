package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
public class MemberThirdLoginOption implements Serializable{

	private Integer memberId; // 用户ID

    private Integer thirdType; // 第三方登陆类型[0:qq, 1:微信]

    private String thirdId; // 第三方ID
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar beginDate;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar endDate;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getThirdType() {
		return thirdType;
	}

	public void setThirdType(Integer thirdType) {
		this.thirdType = thirdType;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public Calendar getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}
