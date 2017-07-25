package com.ejuzuo.common.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AdminRolePerm implements Serializable {

	private Long roleId;
	private String permCode;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	@Override
	public String toString() {
		return "AdminRolePerm [roleId=" + roleId + ", permCode=" + permCode
				+ "]";
	}

}
