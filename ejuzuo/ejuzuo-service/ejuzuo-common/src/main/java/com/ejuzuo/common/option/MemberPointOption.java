package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

public class MemberPointOption implements Serializable {
    private static final long serialVersionUID = 1454454448771232159L;

    private Integer id;
    
    private Integer memberId ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar startTime ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endTime ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
