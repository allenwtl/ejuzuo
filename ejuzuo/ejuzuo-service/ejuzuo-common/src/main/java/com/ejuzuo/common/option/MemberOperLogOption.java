package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

public class MemberOperLogOption implements Serializable {
    private static final long serialVersionUID = 1454454448771232159L;

    private Integer memberId ;

    /**
     * @see com.ejuzuo.common.domain.type.MemberOperLogType
     */
    private Integer operType; // 操作类型[0:登录，1:修改密码，2:找回密码，3下载]

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

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
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
