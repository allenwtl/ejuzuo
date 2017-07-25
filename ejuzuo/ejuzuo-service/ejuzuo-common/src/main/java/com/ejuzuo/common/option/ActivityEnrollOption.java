package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
public class ActivityEnrollOption implements Serializable {

	private Integer id;
	private Integer memberId;
	private Integer activityId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar enrollBeiginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar enrollEndTime;
	
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
	public Calendar getEnrollBeiginTime() {
		return enrollBeiginTime;
	}
	public void setEnrollBeiginTime(Calendar enrollBeiginTime) {
		this.enrollBeiginTime = enrollBeiginTime;
	}
	public Calendar getEnrollEndTime() {
		return enrollEndTime;
	}
	public void setEnrollEndTime(Calendar enrollEndTime) {
		this.enrollEndTime = enrollEndTime;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
}
