package com.ejuzuo.common.service;

import java.util.List;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminRole;

public interface AdminRoleService {

	ModelResult<AdminRole> insert(AdminRole obj);
	
	ModelResult<AdminRole> update(AdminRole obj);
	
	ModelResult<AdminRole> query(Long id);
	
	ModelResult<AdminRole> query(String roleName);
	
	ModelResult<List<AdminRole>> query(List<Long> ids);
	
	ModelResult<List<AdminRole>> queryAll();
	
	PageResult<AdminRole> queryPage(DataPage<AdminRole> dataPge, AdminRole qObj);

}
