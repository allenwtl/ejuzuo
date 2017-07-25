package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
public class ActivityInfoOption implements Serializable {

	private Long id;
	private String category;
	private String city;
	private String title;
	private String publisher;
	private Integer status;
	private Integer editStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar holdBeginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar holdEndDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Calendar getHoldBeginDate() {
		return holdBeginDate;
	}
	public void setHoldBeginDate(Calendar holdBeginDate) {
		this.holdBeginDate = holdBeginDate;
	}
	public Calendar getHoldEndDate() {
		return holdEndDate;
	}
	public void setHoldEndDate(Calendar holdEndDate) {
		this.holdEndDate = holdEndDate;
	}
}
