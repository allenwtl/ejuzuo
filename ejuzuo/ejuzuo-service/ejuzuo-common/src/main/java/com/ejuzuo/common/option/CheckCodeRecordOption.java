package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.CheckCodeRecordStatus;

public class CheckCodeRecordOption implements Serializable{

	private static final long serialVersionUID = -4683531409462777188L;

	private Integer id;
	
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
		this.destNo = destNo;
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
