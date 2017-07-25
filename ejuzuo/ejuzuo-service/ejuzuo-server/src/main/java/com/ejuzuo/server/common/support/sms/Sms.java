package com.ejuzuo.server.common.support.sms;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Sms implements Serializable {
	private String mobileNo;
	private String content;

	public Sms(String mobileNo, String content){
		this.mobileNo = mobileNo;
		this.content = content;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
