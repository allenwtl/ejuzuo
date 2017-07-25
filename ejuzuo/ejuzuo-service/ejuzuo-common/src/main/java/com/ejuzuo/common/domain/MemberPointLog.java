package com.ejuzuo.common.domain;

import com.ejuzuo.common.constants.MemberPointLogTransType;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 会员积分明细
 */
public class MemberPointLog  implements Serializable {
    private static final long serialVersionUID = 237605021403791838L;
    
    private Integer id;

    private Integer memberId;

    /**
     * @see MemberPointLogTransType
     */
    private Integer transType; // 事务类型

    private Integer amount; // 发生数额，可正，可负

    private Integer pointBalance; 

    private String remark;

    private Integer relatedType; // 关联类型

    private Integer relatedId; 

    private String creator;

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

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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

    public Integer getPointBalance() {
        return pointBalance;
    }

    public void setPointBalance(Integer pointBalance) {
        this.pointBalance = pointBalance;
    }
}