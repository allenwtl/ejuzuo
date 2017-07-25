package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class ActivityEnroll implements Serializable{

    private static final long serialVersionUID = -6693195780515533665L;

    private Integer id;

    private Integer memberId; // 用户ID

    private Integer activityId; // 关联活动ID

    private Calendar enrollTime; // 报名时间

    private Calendar createTime;
    
    /**展示用*/
    private String nickName; // 用户昵称
    
    private String account; // 用户账号

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

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Calendar getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Calendar enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}