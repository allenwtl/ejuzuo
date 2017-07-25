package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

public class ActivityInfo implements Serializable {

    private static final long serialVersionUID = -8969384702072382299L;

    private Integer id;

    private String category; // 活动类别，对应代码集合15

    private String city; // 活动城市，对应集合代码12

    private String thumbnail; // 缩略图路径

    private String title;

    private String brief;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar enrollBeginTime; // 报名时间起

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar enrollEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar holdBeginTime; // 举办开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar holdEndTime;

    private Integer status = 1; // 状态[0:无效，1:有效]

    private String publisher;

    private Calendar publishTime;

    private Integer viewCount;

    private String remark;

    private Integer editStatus; // 编辑状态[0:暂存，1:发布]

    private Calendar createTime = Calendar.getInstance();

    private Calendar updateTime;
    
    /** 展示用*/
    private String content;
    
    /**获取报名状态 0:未报名；1：报名中，2：已截止*/
    public Integer getEnrollStatus(){
    	Calendar now = Calendar.getInstance();
    	if (now.compareTo(enrollBeginTime) < 0) {
			return 0;
		}else if (now.compareTo(enrollEndTime) > 0) {
			return 2;
		}
    	return 1;
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

    public Calendar getEnrollBeginTime() {
        return enrollBeginTime;
    }

    public void setEnrollBeginTime(Calendar enrollBeginTime) {
        this.enrollBeginTime = enrollBeginTime;
    }

    public Calendar getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Calendar enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Calendar getHoldBeginTime() {
        return holdBeginTime;
    }

    public void setHoldBeginTime(Calendar holdBeginTime) {
        this.holdBeginTime = holdBeginTime;
    }

    public Calendar getHoldEndTime() {
        return holdEndTime;
    }

    public void setHoldEndTime(Calendar holdEndTime) {
        this.holdEndTime = holdEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
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