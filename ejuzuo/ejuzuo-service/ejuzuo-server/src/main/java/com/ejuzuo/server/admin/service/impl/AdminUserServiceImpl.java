package com.ejuzuo.server.admin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.common.service.AdminUserService;
import com.ejuzuo.server.admin.manager.AdminUserManager;

@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);
	
	@Autowired
	private AdminUserManager adminUserManager;
	
	@Override
	public ModelResult<AdminUser> insert(AdminUser obj) {
		
		AdminUser user = adminUserManager.insert(obj);
		
		ModelResult<AdminUser> result = new ModelResult<AdminUser>();
		result.setModel(user);
		
		return result;
	}


	@Override
	public ModelResult<AdminUser> update(AdminUser obj) {
		
		AdminUser user = adminUserManager.update(obj);
		
		ModelResult<AdminUser> result = new ModelResult<AdminUser>();
		result.setModel(user);
		
		return result;
	}


	@Override
	public ModelResult<AdminUser> query(Long id) {
		ModelResult<AdminUser> result = new ModelResult<AdminUser>();
		AdminUser adminUser = adminUserManager.query(id);
		result.setModel(adminUser);
		return result;
	}

	@Override
	public ModelResult<AdminUser> query(String account) {
		AdminUser user = adminUserManager.query(account);
		ModelResult<AdminUser> result = new ModelResult<AdminUser>();
		result.setModel(user);
		
		return result;
	}

	@Override
	public ModelResult<AdminUser> query(String account, String password) {
		AdminUser user = adminUserManager.query(account, password);
		
		ModelResult<AdminUser> result = new ModelResult<AdminUser>();
		result.setModel(user);
		
		return result;
	}
	
	@Override
	public PageResult<AdminUser> queryPage(DataPage<AdminUser> dataPage, AdminUser qObj) {
		
		dataPage = adminUserManager.queryPage(dataPage, qObj);
		
		PageResult<AdminUser> result = new PageResult<AdminUser>();
		result.setPage(dataPage);
		
		return result;
	}

}
