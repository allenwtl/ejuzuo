package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class FileInfo implements Serializable {
    private static final long serialVersionUID = -4327351121444586478L;
    
    private Integer id;

    private String fileName; // 文件名[带上扩展名]

    private Long size; // 文件大小[byte]

    private String ext; // 扩展名[不带点]

    private String path; // 附件路径

    private String remark;

    private String uploader;

    private Calendar uploadTime;

    /** @see FileInfoRelatedType */
    private Integer relatedType; // 关联类型[0:空,1:新闻资讯，2:活动，3:数字家居，4：设计作品，5：品牌]

    private Integer relatedId;

    private Integer status;

    private Calendar createTime = Calendar.getInstance();

    private Calendar updateTime;

    
    public FileInfo() {
		super();
	}

	public FileInfo(Integer id, String fileName, Long size, String ext, String path, String remark, String uploader,
			Calendar uploadTime, Integer relatedType, Integer relatedId, Integer status, Calendar createTime,
			Calendar updateTime) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.size = size;
		this.ext = ext;
		this.path = path;
		this.remark = remark;
		this.uploader = uploader;
		this.uploadTime = uploadTime;
		this.relatedType = relatedType;
		this.relatedId = relatedId;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public Calendar getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Calendar uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
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
}