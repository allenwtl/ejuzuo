package com.ejuzuo.common.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AdminRole implements Serializable {

	public static final int STATUS_ENABLED = 1;
	public static final int STATUS_DISABLED = 2;

	private Long id;
	private String roleName;
	private String roleCode;
	private Integer status;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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

	@Override
	public String toString() {
		return "AdminRole [id=" + id + ", roleName=" + roleName + ", roleCode="
				+ roleCode + ", status=" + status + ", remark=" + remark + "]";
	}

}
