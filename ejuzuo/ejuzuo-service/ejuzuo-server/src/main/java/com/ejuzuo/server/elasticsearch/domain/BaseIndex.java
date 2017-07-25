package com.ejuzuo.server.elasticsearch.domain;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class BaseIndex {

	/** 主键ID */
	@Id
	private Integer id;

	/** 类型 @see com.ejuzuo.common.domain.type.ObjectType*/
	@Field(store = true, type = FieldType.Integer)
	private Integer objectType;
	
	/** 标题 ,对应新闻、活动的title；对应数字家居、设计师、设计师作品的name*/
	@Field(store = true, type = FieldType.String)
	private String title;

	/** 简介 */
	@Field(store = true, type = FieldType.String)
	private String brief;

	/** 内容 */
	@Field(store = false, type = FieldType.String)
	private String content = "";

	/** 图片 */
	@Field(store = false, type = FieldType.String, index = FieldIndex.not_analyzed)
	private String coverImg;
	
	/** 创建时间 */
	@Field(store = true, type = FieldType.Date, index = FieldIndex.not_analyzed)
	private Calendar createTime;

	/** 发布时间 */
	@Field(store = true, type = FieldType.Date, index = FieldIndex.not_analyzed)
	private Calendar publishTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		if(StringUtils.isNotBlank(coverImg)){
			this.coverImg = coverImg;
		} else {
			this.coverImg = "/images/notfound.jpg";
		}

	}
}
