package com.ejuzuo.server.admin.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUserOperLog;
import com.ejuzuo.common.service.AdminUserOperLogService;
import com.ejuzuo.server.admin.manager.AdminUserOperLogManager;

@Service("adminUserOperLogService")
public class AdminUserOperLogServiceImpl implements AdminUserOperLogService {
	
	@Autowired
	private AdminUserOperLogManager adminUserOperLogManager;

	@Override
	public ModelResult<Boolean> insert(AdminUserOperLog obj) {
		
		boolean inserted = adminUserOperLogManager.insert(obj);
		
		ModelResult<Boolean> result = new ModelResult<Boolean>();
		result.setModel(inserted);
		
		return result;
	}

	@Override
	public PageResult<AdminUserOperLog> queryPage(
			DataPage<AdminUserOperLog> dataPage, 
			AdminUserOperLog qVo, 
			Calendar beginDate, 
			Calendar endDate) {
		
		dataPage = adminUserOperLogManager.queryPage(dataPage, qVo, beginDate, endDate);
		
		PageResult<AdminUserOperLog> result = new PageResult<AdminUserOperLog>();
		result.setPage(dataPage);
		
		return result;
	}

}
