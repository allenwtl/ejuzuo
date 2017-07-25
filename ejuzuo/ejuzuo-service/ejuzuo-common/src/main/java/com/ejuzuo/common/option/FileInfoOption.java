package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

public class FileInfoOption implements Serializable{

	private static final long serialVersionUID = 1491776639164534741L;
	
	private Integer id;
	
	private String fileName; // 文件名[带上扩展名]

	private String ext; // 扩展名[不带点]
	
	private Integer relatedType;
	
	private Integer status;
	
	private String uploader;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar beginDate; // 上传时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar endDate; // // 上传时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
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
}
