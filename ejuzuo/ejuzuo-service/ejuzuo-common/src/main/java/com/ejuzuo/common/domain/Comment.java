package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1063625003103063100L;
    private Integer id;

    private Integer memberId; // 会员ID

    /**
     * @see com.ejuzuo.common.domain.type.ObjectType
     */
    private Integer objectType; // 被评论对象类型[0:数字家居，1:设计作品，2:活动，3:资讯，4:设计师]

    private Integer objectId; // 被评论对象的ID

    private String comment; // 评论内容

    /**
     * @see  com.ejuzuo.common.constants.Status
     */
    private Integer status; // 状态[0:无效，1:有效]

    /**
     * @see com.ejuzuo.common.domain.type.CommentMask
     */
    private Integer masked; // 是否被屏蔽[0未屏蔽，1已屏蔽]

    private String remark; // 备注

    private Calendar createTime; // 创建时间

    private String updator; // 更新者

    private Calendar updateTime;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMasked() {
        return masked;
    }

    public void setMasked(Integer masked) {
        this.masked = masked;
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

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
}