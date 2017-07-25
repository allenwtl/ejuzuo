package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.type.ObjectType;

import java.io.Serializable;
import java.util.Calendar;

import com.ejuzuo.common.util.DateUtil;

public class ContentSearchResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 类型 @see com.ejuzuo.common.domain.type.ObjectType
     */
    private Integer objectType;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String brief;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String coverImg;
	
    /**
     * 创建时间
     */
    private Calendar createTime;

    /**
     * 发布时间
     */
    private Calendar publishTime;

    private String url;
    
    private String createTimeStr;

    private String publishTimeStr;

    public String getUrl() {
        if (ObjectType.huodong.getIndex() == objectType) {
            url = "/activity/detail/" + id;
        } else if (ObjectType.zixun.getIndex() == objectType) {
            url = "/newsInfo/detail/" + id;
        } else if (ObjectType.shejishi.getIndex() == objectType) {
            url = "/designer/detail/" + id;
        } else if (ObjectType.shuzijiaju.getIndex() == objectType) {
            url = "/digital/digitalDetail/" + id;
        } else if (ObjectType.shejizuopin.getIndex() == objectType) {
            url = "/designerWork/detail/"+id;
        }
        return url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Calendar publishTime) {
        this.publishTime = publishTime;
    }

    public String getObjectType() {
        return ObjectType.findByIndex(objectType).getDescription();
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

	public String getCreateTimeStr() {
		if (createTime != null) {
			createTimeStr = DateUtil.toyyyy_MM_dd_HH_mm(createTime);
		}
		return createTimeStr;
	}

	public String getPublishTimeStr() {
		if (publishTime != null) {
			publishTimeStr = DateUtil.toyyyy_MM_dd_HH_mm(publishTime);
		}
		return publishTimeStr;
	}
}
