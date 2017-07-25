package com.ejuzuo.server.admin.manager;

import java.util.List;

import com.ejuzuo.common.domain.AdminRolePerm;

public interface AdminRolePermManager {

	AdminRolePerm insert(AdminRolePerm obj);
	
	int delete(Long roleId);
	
	List<AdminRolePerm> query(Long roleId);
	
}
