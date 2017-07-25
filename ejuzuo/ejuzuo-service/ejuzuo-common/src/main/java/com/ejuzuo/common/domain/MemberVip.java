package com.ejuzuo.common.domain;

import com.ejuzuo.common.constants.MemberVipType;

import java.io.Serializable;
import java.util.Calendar;

public class MemberVip implements Serializable{

    private static final long serialVersionUID = 4602430596235721876L;
    
    private Integer id;

    private Integer memberId;

    /**
     * @see MemberVipType
     */
    private Integer viped; // 是否VIP[0:否，1:是]

    private String remark;

    private Calendar startTime;

    private Calendar endTime;

    private Calendar createTime;

    private String creator;

    private Calendar updateTime;

    private String updator;
    
    /**展示用*/
    private String nickName; // 用户昵称
    
    private String account; // 用户账号
    
    public boolean isVip(){
    	if (viped == 1) {
			if (startTime != null && endTime != null && Calendar.getInstance().after(startTime) && Calendar.getInstance().before(endTime)) {
				return true;
			}
		}
    	return false;
    }

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

    public Integer getViped() {
        return viped;
    }

    public void setViped(Integer viped) {
        this.viped = viped;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
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