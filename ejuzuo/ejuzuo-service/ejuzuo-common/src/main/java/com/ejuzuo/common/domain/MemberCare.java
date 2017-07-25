package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class MemberCare implements Serializable {
    private static final long serialVersionUID = 8607324292679703524L;
    private Integer id;

    private Integer memberId;

    /**
     * @see com.ejuzuo.common.domain.type.ObjectType
     */
    private Integer objectType;

    private Integer objectId;

    private Calendar createTime;

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

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
}