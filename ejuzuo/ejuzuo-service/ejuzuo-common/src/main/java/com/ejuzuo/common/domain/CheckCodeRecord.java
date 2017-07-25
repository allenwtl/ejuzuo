package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.CheckCodeRecordStatus;

public class CheckCodeRecord implements Serializable {
	
	private static final long serialVersionUID = 4224133196921844352L;

	private Integer id;

	private String checkCode;

    /**
     * 校验码内容
     */
	private Calendar sendTime;

	private Calendar expireTime;

	/**
	 * 操作类型[0:注册，1:用户激活，2:验证手机，3:验证邮箱，4:找回密码]
	 */
	private CheckCodeRecordCheckType checkType;

	/**
	 * 目标类型[0:短信，1:邮箱]
	 */
	private CheckCodeRecordDestType destType;

    /**
     * 目标地址[手机或邮箱]
     */
    private String destNo;

    /**
     * 状态[0:待验证，1:已验证]
     */
    private CheckCodeRecordStatus status = CheckCodeRecordStatus.notUsed;

	private Integer memberId;

	private Calendar createTime;

	private Calendar updateTime;
    /**
     * 验证次数
     */
    private Integer verifyCount = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode == null ? null : checkCode.trim();
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}

	public Calendar getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Calendar expireTime) {
		this.expireTime = expireTime;
	}

	public CheckCodeRecordCheckType getCheckType() {
		return checkType;
	}

	public void setCheckType(CheckCodeRecordCheckType checkType) {
		this.checkType = checkType;
	}

	public CheckCodeRecordDestType getDestType() {
		return destType;
	}

	public void setDestType(CheckCodeRecordDestType destType) {
		this.destType = destType;
	}

	public String getDestNo() {
		return destNo;
	}

	public void setDestNo(String destNo) {
		this.destNo = destNo == null ? null : destNo.trim();
	}

	public CheckCodeRecordStatus getStatus() {
		return status;
	}

	public void setStatus(CheckCodeRecordStatus status) {
		this.status = status;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVerifyCount() {
		return verifyCount;
	}

	public void setVerifyCount(Integer verifyCount) {
		this.verifyCount = verifyCount;
	}
	
	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}