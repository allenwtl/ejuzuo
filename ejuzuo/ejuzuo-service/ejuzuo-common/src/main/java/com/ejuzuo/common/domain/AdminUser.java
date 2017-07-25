package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("serial")
public class AdminUser implements Serializable {
	
	private Long id;
	private String account;
	private String password;
	private String name;
	private String nickname;
	private String roles;
	private String roleNames;
	private Integer status;
	private String remark;
	private String createdUser;
	private String updatedUser;
	private Calendar createdDate;
	private Calendar updatedDate;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Calendar updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "AdminUser [id=" + id + ", account=" + account + ", password="
				+ password + ", name=" + name + ", nickname=" + nickname
				+ ", roles=" + roles + ", roleNames=" + roleNames + ", status="
				+ status + ", remark=" + remark + ", createdUser="
				+ createdUser + ", updatedUser=" + updatedUser
				+ ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}

}

