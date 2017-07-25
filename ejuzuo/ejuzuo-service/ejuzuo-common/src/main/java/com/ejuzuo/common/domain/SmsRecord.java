package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.ejuzuo.common.domain.type.SmsRecordMobileType;
import com.ejuzuo.common.domain.type.SmsRecordSendStatus;

public class SmsRecord implements Serializable {
	private static final long serialVersionUID = 5290614059916923420L;	
	private Integer id;
	/** 短信内容 */
	private String content;
	private String mobile;
	/** '发送状态[0:失败，1:成功，2:发送中]' */
	private SmsRecordSendStatus sendStatus;
	/** '号码类型[0：其他，1:移动 2:联通 3:电信 ]' */
	private SmsRecordMobileType mobileType;
	/** '短信网关[谐和:1]' */
	private Integer gateway;
	private Date createDate;
	private Date sendDate;
	private String errorMsg;
	private Integer memberId;
	private String source;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getGateway() {
		return gateway;
	}
	public void setGateway(Integer gateway) {
		this.gateway = gateway;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}
}
