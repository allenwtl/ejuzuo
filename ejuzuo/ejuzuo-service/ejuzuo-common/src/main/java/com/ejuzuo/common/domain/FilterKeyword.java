package com.ejuzuo.common.domain;

import java.util.Calendar;

public class FilterKeyword {
	
    private Long id;

    private String keyword; // 关键词

    private Integer filterLevel; // 过滤级别

    private Integer operatorId; // 添加人id

    private Calendar createTime;

    private Calendar updateTime;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getFilterLevel() {
        return filterLevel;
    }

    public void setFilterLevel(Integer filterLevel) {
        this.filterLevel = filterLevel;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
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
}