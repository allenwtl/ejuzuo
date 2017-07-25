package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class MemberThirdLogin implements Serializable {

    private static final long serialVersionUID = 9124294588770137682L;

    private Integer id;

    private Integer memberId; // 用户ID

    private Integer thirdType; // 第三方登陆类型[0:qq, 1:微信]

    private String thirdId; // 第三方ID

    private Calendar createTime; // 登陆时间

    private Calendar updateTime;
    
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

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId == null ? null : thirdId.trim();
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