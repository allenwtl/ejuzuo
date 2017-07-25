package com.ejuzuo.server.admin.manager;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUser;

public interface AdminUserManager {

	AdminUser insert(AdminUser obj);
	
	AdminUser update(AdminUser obj);
	
	AdminUser query(Long id);
	
	AdminUser query(String account);
	
	AdminUser query(String account, String password);
	
	DataPage<AdminUser> queryPage(DataPage<AdminUser> dataPage, AdminUser qObj);
	
}
