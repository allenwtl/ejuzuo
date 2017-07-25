package com.ejuzuo.common.service;

import java.util.Calendar;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUserOperLog;

public interface AdminUserOperLogService {

	ModelResult<Boolean> insert(AdminUserOperLog obj);
	
	PageResult<AdminUserOperLog> queryPage(
			DataPage<AdminUserOperLog> dataPge, 
			AdminUserOperLog qVo, 
			Calendar beginDate, 
			Calendar endDate);
	
}
