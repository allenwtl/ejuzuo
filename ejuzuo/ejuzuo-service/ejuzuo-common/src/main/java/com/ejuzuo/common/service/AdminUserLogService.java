package com.ejuzuo.common.service;

import java.util.Calendar;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUserLog;

public interface AdminUserLogService {

	ModelResult<AdminUserLog> insert(AdminUserLog obj);
	
	ModelResult<AdminUserLog> query(Long id);
	
	PageResult<AdminUserLog> queryPage(DataPage<AdminUserLog> dataPge, AdminUserLog qObj, Calendar beginDate, Calendar endDate);
	
}
