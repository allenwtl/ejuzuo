package com.ejuzuo.server.admin.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.AdminRole;
import com.ejuzuo.server.admin.manager.AdminRoleManager;

@Repository
public class AdminRoleManagerImpl implements AdminRoleManager {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminRoleManagerImpl.class);
	
	@Autowired
    protected GenericDao dao;

	@Override
	public AdminRole insert(AdminRole obj) {
		
		/*long id = dao.generateSequence("AdminRoleMapper.generateSequence");
		obj.setId(id);*/
		
		int affected = dao.insertAndReturnAffectedCount("AdminRoleMapper.insert", obj);
		
		if (affected > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Override
	public AdminRole update(AdminRole obj) {

		int affected = dao.updateByObj("AdminRoleMapper.update", obj);
		
		if (affected > 0) {
			return this.query(obj.getId());
		} else {
			return null;
		}

	}

	@Override
	public AdminRole query(Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		AdminRole obj = dao.queryUnique("AdminRoleMapper.query", map);
		return obj;
		
	}
	
	@Override
	public AdminRole query(String roleName) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", roleName);
		
		AdminRole obj = dao.queryUnique("AdminRoleMapper.query", map);
		return obj;
		
	}
	
	@Override
	public List<AdminRole> query(List<Long> ids) {
		
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<AdminRole>();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		
		List<AdminRole> list = dao.queryList("AdminRoleMapper.queryByIds", map);
		return list;
	}
	
	@Override
	public List<AdminRole> queryAll() {
		
		List<AdminRole> list = dao.queryList("AdminRoleMapper.queryAll");
		return list;
	}

	@Override
	public DataPage<AdminRole> queryPage(DataPage<AdminRole> dataPage, AdminRole qObj) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startIndex", dataPage.getStartIndex());
//		map.put("endIndex", dataPage.getEndIndex());
		map.put("offset", dataPage.getPageSize());
		
		if (StringUtils.isNotBlank(qObj.getRoleName())) {
			map.put("roleName", "%" + qObj.getRoleName() + "%");
		}
		if (qObj.getStatus() != null) {
			map.put("status", qObj.getStatus());
		}
		
		if (dataPage.isNeedTotalCount()) {
			int totalCount = dao.queryCount("AdminRoleMapper.countPage", map);
			dataPage.setTotalCount(totalCount);
		}
		if (dataPage.isNeedData()) {
			List<AdminRole> list = dao.queryList("AdminRoleMapper.queryPage", map);
			dataPage.setDataList(list);
		}
		
		return dataPage;
	}

}
