package com.ejuzuo.server.admin.manager;

import java.util.List;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminRole;

public interface AdminRoleManager {

	AdminRole insert(AdminRole obj);
	
	AdminRole update(AdminRole obj);
	
	AdminRole query(Long id);
	
	AdminRole query(String roleName);
	
	List<AdminRole> query(List<Long> ids);
	
	List<AdminRole> queryAll();
	
	DataPage<AdminRole> queryPage(DataPage<AdminRole> dataPage, AdminRole qObj);

}
