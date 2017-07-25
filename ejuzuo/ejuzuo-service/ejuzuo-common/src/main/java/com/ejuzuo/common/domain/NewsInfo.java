package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

import com.ejuzuo.common.domain.type.NewsInfoCategory;

public class NewsInfo implements Serializable {

    private static final long serialVersionUID = 7926389259217932633L;
    
    private Integer id;

    /** @see NewsInfoCategory */
    private Integer category; // 新闻类别

    private String thumbnail; // 缩略图路径

    private String title; // 标题

    private String brief; // 简介

    private String source; // 来源

    private String publisher; // 发布人

    private Calendar publishTime; // 发布时间

    private Integer status = 1; // 是否有效（0：无效，1：有效）

    private Integer editStatus; // 编辑状态（0：暂存，1：发布）

    private Integer viewCount; // 浏览次数

    private String remark; // 备注

    private Calendar createTime = Calendar.getInstance();

    private Calendar updateTime;
    
    /** 展示用*/
    private String content;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public Calendar getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Calendar publishTime) {
        this.publishTime = publishTime;
    }

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}

	public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}