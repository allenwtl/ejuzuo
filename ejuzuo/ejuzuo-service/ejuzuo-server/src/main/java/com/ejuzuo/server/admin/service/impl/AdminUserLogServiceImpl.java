package com.ejuzuo.server.admin.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUserLog;
import com.ejuzuo.common.service.AdminUserLogService;
import com.ejuzuo.server.admin.manager.AdminUserLogManager;

@Service("adminUserLogService")
public class AdminUserLogServiceImpl implements AdminUserLogService {
	
	@Autowired
	private AdminUserLogManager adminUserLogManager;

	@Override
	public ModelResult<AdminUserLog> insert(AdminUserLog obj) {
		
		AdminUserLog user = adminUserLogManager.insert(obj);
		
		ModelResult<AdminUserLog> result = new ModelResult<AdminUserLog>();
		result.setModel(user);
		
		return result;
	}

	@Override
	public ModelResult<AdminUserLog> query(Long id) {
		
		AdminUserLog user = adminUserLogManager.query(id);
		
		ModelResult<AdminUserLog> result = new ModelResult<AdminUserLog>();
		result.setModel(user);
		
		return result;
	}
	
	@Override
	public PageResult<AdminUserLog> queryPage(DataPage<AdminUserLog> dataPage, AdminUserLog qObj, Calendar beginDate, Calendar endDate) {
		
		dataPage = adminUserLogManager.queryPage(dataPage, qObj, beginDate, endDate);
		
		PageResult<AdminUserLog> result = new PageResult<AdminUserLog>();
		result.setPage(dataPage);
		
		return result;
	}

}
