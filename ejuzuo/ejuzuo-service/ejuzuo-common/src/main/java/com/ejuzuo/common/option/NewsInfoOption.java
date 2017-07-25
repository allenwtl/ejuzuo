package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
public class NewsInfoOption implements Serializable {

	private Long id;
	private Integer category;
	private String title;
	private String publisher;
	private Integer status;
	private Integer editStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar publishBeginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar publishEndDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar beginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar endDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Integer getEditStatus() {
		return editStatus;
	}
	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}
	public Calendar getPublishBeginDate() {
		return publishBeginDate;
	}
	public void setPublishBeginDate(Calendar publishBeginDate) {
		this.publishBeginDate = publishBeginDate;
	}
	public Calendar getPublishEndDate() {
		return publishEndDate;
	}
	public void setPublishEndDate(Calendar publishEndDate) {
		this.publishEndDate = publishEndDate;
	}
	public Calendar getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
