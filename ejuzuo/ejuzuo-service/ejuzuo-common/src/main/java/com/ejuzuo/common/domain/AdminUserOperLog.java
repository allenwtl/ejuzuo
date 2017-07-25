package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.AdminOperType;

@SuppressWarnings("serial")
public class AdminUserOperLog implements Serializable {

	private Long id;
	private AdminOperType operType;
	private String account;
	private JSONObject remark;
	private Calendar createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdminOperType getOperType() {
		return operType;
	}

	public void setOperType(AdminOperType operType) {
		this.operType = operType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public JSONObject getRemark() {
		return remark;
	}

	public void setRemark(JSONObject remark) {
		this.remark = remark;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

}
