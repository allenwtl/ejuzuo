package com.ejuzuo.server.admin.manager;

import java.util.Calendar;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminUserLog;

public interface AdminUserLogManager {

	AdminUserLog insert(AdminUserLog obj);
	
	AdminUserLog query(Long id);
	
	DataPage<AdminUserLog> queryPage(DataPage<AdminUserLog> dataPage, AdminUserLog qObj, Calendar beginDate, Calendar endDate);
	
}
