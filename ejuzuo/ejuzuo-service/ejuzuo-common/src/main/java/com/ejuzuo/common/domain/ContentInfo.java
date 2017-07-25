package com.ejuzuo.common.domain;

import com.ejuzuo.common.domain.type.ContentInfoRelatedType;

import java.io.Serializable;
import java.util.Calendar;

public class ContentInfo implements Serializable  {

    private static final long serialVersionUID = -8291933021272783605L;

    private Integer id;

    /** @see ContentInfoRelatedType */
    private Integer relatedType; // 关联类型

    private Integer relatedId; // 关联ID

    private Calendar createdTime = Calendar.getInstance();

    private Calendar updateTime;

    private String content;

	public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}