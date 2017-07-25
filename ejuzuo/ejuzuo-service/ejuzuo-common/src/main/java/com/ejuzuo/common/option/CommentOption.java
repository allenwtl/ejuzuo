package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public class CommentOption implements Serializable{

    private static final long serialVersionUID = 3620324486909466558L;

    private Integer id ;

    private Integer objectId ;

    private Integer objectType ;

    private Integer status;

    private Integer masked;

    private Integer memberId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar beginDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar endDate;

    private Integer startIndex;

    private Integer pageSize ;

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMasked() {
        return masked;
    }

    public void setMasked(Integer masked) {
        this.masked = masked;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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
