package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("serial")
public class AdminUserLog implements Serializable {
	
	public static final String STATUS_NORMAL = "NORMAL";
	public static final String STATUS_FAILED = "FAILED";
	public static final String STATUS_EXCEPTION = "EXCEPTION";

	private Long id;
	private String userName;
	private String action;
	private String uri;
	private String params;
	private String status = STATUS_NORMAL;
	private Calendar logDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		if (params != null && params.length() > 999) {
			this.params = params.substring(0, 999);
		} else {
			this.params = params;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Calendar getLogDate() {
		return logDate;
	}

	public void setLogDate(Calendar logDate) {
		this.logDate = logDate;
	}

	@Override
	public String toString() {
		return "AdminUserLog [id=" + id + ", userName=" + userName
				+ ", action=" + action + ", uri=" + uri + ", params=" + params
				+ ", status=" + status + ", logDate=" + logDate + "]";
	}

}
