package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by tianlun.wu on 2016/3/29 0029.
 */
public class MemberOption implements Serializable {
    private static final long serialVersionUID = 8546875983828304839L;

    private Long id ;

    private String account ;

    private String nickName ;
    
    private Integer activeStatus; //激活状态[0:待激活，1：已激活]

    private Integer status; // [0无效，1有效]
    
    private Integer locked; // [0未锁定，1已锁定]
    
    private Integer verified; // [0:未认证，1:已认证]
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar beginDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endDate;
    
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Integer getVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
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
