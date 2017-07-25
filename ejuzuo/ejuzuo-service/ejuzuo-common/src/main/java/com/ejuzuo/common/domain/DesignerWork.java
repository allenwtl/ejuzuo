package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class DesignerWork  implements Serializable {

    private static final long serialVersionUID = 7505471913902981731L;
    
    private Integer id;
    
    private Integer designerId;

    private Integer memberId;

    private String name; // 作品名称

    private String coverImg; // 封面图片

    private String style; // 风格，对应代码集合11

    private String brief; // 简介

    private String content; // 内容JSON,格式为[{img:xx, intro:xx}, {img:xx, intro:xx}]

    private Integer viewCount; // 作品浏览量

    private Calendar uploadTime; // 上传时间

    /**
     * @see com.ejuzuo.common.constants.EditStatus
     */
    private Integer editStatus; // 编辑状态[0:暂存，1:发布]

    /**
     * @see com.ejuzuo.common.constants.Status
     */
    private Integer status; // 状态[0无效，1有效]

    private Calendar createTime = Calendar.getInstance();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Calendar getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Calendar uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public Integer getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
}