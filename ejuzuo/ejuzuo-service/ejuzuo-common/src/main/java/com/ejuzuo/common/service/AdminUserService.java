package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUser;

public interface AdminUserService {

	ModelResult<AdminUser> insert(AdminUser obj);
	
	ModelResult<AdminUser> update(AdminUser obj);
	
	ModelResult<AdminUser> query(Long id);
	
	ModelResult<AdminUser> query(String account);
	
	ModelResult<AdminUser> query(String account, String password);

	PageResult<AdminUser> queryPage(DataPage<AdminUser> dataPge, AdminUser qObj);
	
}
