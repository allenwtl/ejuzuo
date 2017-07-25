package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class MemberOperLog implements Serializable{
    private static final long serialVersionUID = 4189384473478061235L;
    private Integer id;

    private Integer memberId;

    /**
     * @see com.ejuzuo.common.domain.type.MemberOperLogType
     */
    private Integer operType; // 操作类型

    private String remark; // 相关信息, 可以是JSON

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

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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