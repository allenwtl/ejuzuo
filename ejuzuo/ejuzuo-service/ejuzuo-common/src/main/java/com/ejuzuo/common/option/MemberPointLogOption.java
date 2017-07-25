package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public class MemberPointLogOption implements Serializable {
    private static final long serialVersionUID = 1454454448771232159L;

    private Integer memberId ;

    private Integer transType ;
    
    private Integer relatedType;

    private Integer relatedId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar startTime ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endTime ;

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

	public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}
}
