package com.ejuzuo.common.domain;

import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.type.MemberLockType;

import java.io.Serializable;
import java.util.Calendar;

public class Member implements Serializable {

    private static final long serialVersionUID = 8738788875992738133L;

    private Integer id;

    private String account;

    private String password;

    //昵称
    private String nickName;

    private String profileImg; // 头像

    private String mobile;

    private String email;

    private Calendar registerTime; // 注册时间

    //激活状态[0:待激活，1：已激活]
    private Integer activeStatus;

    //用户状态[0无效，1有效]
    /**
     * @see
     * Status
     */
    private Integer status = Status.STATUS.getIndex();

    //锁定状态[0未锁定，1已锁定]
    /**
     * @see com.ejuzuo.common.domain.type.MemberLockType
     */
    private Integer locked = MemberLockType.unLock.getIndex();

    //是否认证[0:未认证，1:已认证]
    private Integer verified;

    private Calendar createTime;

    private Calendar updateTime;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg == null ? null : profileImg.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Calendar getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Calendar registerTime) {
        this.registerTime = registerTime;
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