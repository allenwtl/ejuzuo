package com.ejuzuo.server.admin.manager.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericMybatisDao;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.server.admin.manager.AdminUserManager;

@Repository
public class AdminUserManagerImpl implements AdminUserManager {
	
	@Autowired
    private GenericMybatisDao dao;
	
	@Override
	public AdminUser insert(AdminUser obj) {
		
//		long id = dao.generateSequence("AdminUserMapper.generateSequence");
//		obj.setId(id);
		if (obj == null) {
			return null;
		}
		
		obj.setCreatedDate(Calendar.getInstance());
		obj.setUpdatedDate(Calendar.getInstance());
		int affected = dao.insertAndReturnAffectedCount("AdminUserMapper.insert", obj);
		
		System.out.println(obj);
		if (affected > 0) {
			return obj;
		} else {
			return null;
		}
	}

	@Override
	public AdminUser update(AdminUser obj) {
		
		if (obj == null) {
			return null;
		}
		
		obj.setUpdatedDate(Calendar.getInstance());
		int affected = dao.updateByObj("AdminUserMapper.update", obj);
		
		if (affected > 0) {
			return this.query(obj.getId());
		} else {
			return null;
		}
	}
	
	private AdminUser query(Long id, String account, String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (id != null && id > 0) {
			map.put("id", id);
		} else if (StringUtils.isNotBlank(account)) {
			map.put("account", account);
			
			if (StringUtils.isNotBlank(password)) {
				map.put("password", password);
			}
		}
		
		AdminUser obj = dao.queryUnique("AdminUserMapper.query", map);
		return obj;
	}

	@Override
	public AdminUser query(Long id) {
		return this.query(id, null, null);
	}

	@Override
	public AdminUser query(String account) {
		return this.query(null, account, null);
	}

	@Override
	public AdminUser query(String account, String password) {
		return this.query(null, account, password);
	}

	@Override
	public DataPage<AdminUser> queryPage(DataPage<AdminUser> dataPage, AdminUser qObj) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startIndex", dataPage.getStartIndex());
//		map.put("endIndex", dataPage.getEndIndex());
		map.put("offset", dataPage.getPageSize());
		
		if (qObj.getStatus() != null) {
			map.put("status", qObj.getStatus());
		}
		if (StringUtils.isNotBlank(qObj.getName())) {
			map.put("name", "%" + qObj.getName() + "%");
		}
		if (StringUtils.isNotBlank(qObj.getAccount())) {
			map.put("account", "%" + qObj.getAccount() + "%");
		}
		
		if (dataPage.isNeedTotalCount()) {
			int totalCount = dao.queryCount("AdminUserMapper.countPage", map);
			dataPage.setTotalCount(totalCount);
		}
		if (dataPage.isNeedData()) {
			List<AdminUser> list = dao.queryList("AdminUserMapper.queryPage", map);
			dataPage.setDataList(list);
		}
		
		return dataPage;
	}

}
