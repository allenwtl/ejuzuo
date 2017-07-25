package com.ejuzuo.server.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdminRole;
import com.ejuzuo.common.service.AdminRoleService;
import com.ejuzuo.server.admin.manager.AdminRoleManager;

@Service("adminRoleService")
public class AdminRoleServiceImpl implements AdminRoleService {
	
	@Autowired
	private AdminRoleManager AdminRoleManager;

	@Override
	public ModelResult<AdminRole> insert(AdminRole obj) {
		
		AdminRole role = AdminRoleManager.insert(obj);
		
		ModelResult<AdminRole> result = new ModelResult<AdminRole>();
		result.setModel(role);
		
		return result;
	}

	@Override
	public ModelResult<AdminRole> update(AdminRole obj) {
		
		AdminRole role = AdminRoleManager.update(obj);
		
		ModelResult<AdminRole> result = new ModelResult<AdminRole>();
		result.setModel(role);
		
		return result;
	}

	@Override
	public ModelResult<AdminRole> query(Long id) {
		
		AdminRole role = AdminRoleManager.query(id);
		
		ModelResult<AdminRole> result = new ModelResult<AdminRole>();
		result.setModel(role);
		
		return result;
	}
	
	@Override
	public ModelResult<AdminRole> query(String roleName) {
		
		AdminRole role = AdminRoleManager.query(roleName);
		
		ModelResult<AdminRole> result = new ModelResult<AdminRole>();
		result.setModel(role);
		
		return result;
	}
	
	@Override
	public ModelResult<List<AdminRole>> query(List<Long> ids) {
		
		List<AdminRole> list = AdminRoleManager.query(ids);
		
		ModelResult<List<AdminRole>> result = new ModelResult<List<AdminRole>>();
		result.setModel(list);
		
		return result;
	}
	
	@Override
	public ModelResult<List<AdminRole>> queryAll() {
		
		List<AdminRole> list = AdminRoleManager.queryAll();
		
		ModelResult<List<AdminRole>> result = new ModelResult<List<AdminRole>>();
		result.setModel(list);
		
		return result;
	}

	@Override
	public PageResult<AdminRole> queryPage(DataPage<AdminRole> dataPage, AdminRole qObj) {
		
		dataPage = AdminRoleManager.queryPage(dataPage, qObj);
		
		PageResult<AdminRole> result = new PageResult<AdminRole>();
		result.setPage(dataPage);
		
		return result;
	}

}
