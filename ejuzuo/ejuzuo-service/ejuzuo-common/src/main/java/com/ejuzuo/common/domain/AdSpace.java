package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class AdSpace implements Serializable{
	
	private static final long serialVersionUID = -1618747193792703775L;

	private Integer id;
	
	/**
	 * @see com.ejuzuo.common.domain.type.PageCodeType
	 */
	private Integer pageCode; // 页面位置

    private String name; // 广告位名称

    private String content; // 广告位内容

    private String tip; // 帮助
    
    private String filePath; // 生成文件路径

    /**
     * @see  com.ejuzuo.common.constants.Status
     */
    private Integer status;

    private Calendar createTime;

    private Calendar updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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

	public Integer getPageCode() {
		return pageCode;
	}

	public void setPageCode(Integer pageCode) {
		this.pageCode = pageCode;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}