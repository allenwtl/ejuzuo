package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.ejuzuo.common.domain.type.SmsRecordMobileType;
import com.ejuzuo.common.domain.type.SmsRecordSendStatus;

public class SmsRecordOption implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mobile;
	/** '发送状态[0:失败，1:成功，2:发送中]' */
	private SmsRecordSendStatus sendStatus;
	/** '号码类型[0：其他，1:移动 2:联通 3:电信 ]' */
	private SmsRecordMobileType mobileType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar beginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar endDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public SmsRecordSendStatus getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(SmsRecordSendStatus sendStatus) {
		this.sendStatus = sendStatus;
	}
	public SmsRecordMobileType getMobileType() {
		return mobileType;
	}
	public void setMobileType(SmsRecordMobileType mobileType) {
		this.mobileType = mobileType;
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
