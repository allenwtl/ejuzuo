package com.ejuzuo.server.admin.manager;

import java.util.Calendar;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUserOperLog;

public interface AdminUserOperLogManager {

	boolean insert(AdminUserOperLog obj);
	
	DataPage<AdminUserOperLog> queryPage(
			DataPage<AdminUserOperLog> dataPage, 
			AdminUserOperLog qVo, 
			Calendar beginDate, 
			Calendar endDate);
	
}
