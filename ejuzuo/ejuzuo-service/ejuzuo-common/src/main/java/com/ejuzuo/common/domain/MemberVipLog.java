package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class MemberVipLog  implements Serializable {
    private static final long serialVersionUID = -6094527061648898551L;
    private Integer id;

    private Integer memberId;

    /**
     * @see com.ejuzuo.common.constants.MemberVipLogTransType
     */
    private Integer transType; // 事务类型[0:其他，1:赞助,  2:分享,  3:系统赠送，4:异常处理]

    private Integer period; // 期限[月]，可正，可负。

    private String remark;

    private Integer relatedType; // 关联类型[0:其他，1:赞助,  2:分享]

    private Integer relatedId;

    private String creator;

    private Calendar createTime;
    
    /**冗余，展示用*/
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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
}