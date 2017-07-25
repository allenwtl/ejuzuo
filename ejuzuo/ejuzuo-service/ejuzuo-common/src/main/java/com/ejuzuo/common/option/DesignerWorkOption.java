package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public class DesignerWorkOption implements Serializable {
    private static final long serialVersionUID = -357844896915685026L;

    private Integer id;
    
    private Integer memberId ;
    
    private String name; // 作品名称

    /**
     * @see com.ejuzuo.common.constants.EditStatus
     */
    private Integer editStatus;

    /**
     * @see com.ejuzuo.common.constants.Status
     */
    private Integer status;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar beginUploadTime; // 上传时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endUploadTime; // 上传时间

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

	public Calendar getBeginUploadTime() {
		return beginUploadTime;
	}

	public void setBeginUploadTime(Calendar beginUploadTime) {
		this.beginUploadTime = beginUploadTime;
	}

	public Calendar getEndUploadTime() {
		return endUploadTime;
	}

	public void setEndUploadTime(Calendar endUploadTime) {
		this.endUploadTime = endUploadTime;
	}
}
