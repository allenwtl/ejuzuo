package com.ejuzuo.server.admin.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.AdminRolePerm;
import com.ejuzuo.server.admin.manager.AdminRolePermManager;

@Repository
public class AdminRolePermManagerImpl implements AdminRolePermManager {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminRolePermManagerImpl.class);
	
	@Autowired
    protected GenericDao dao;

	@Override
	public AdminRolePerm insert(AdminRolePerm obj) {
		
		if (obj == null) {
			return null;
		}
		
		int affected = dao.insertAndReturnAffectedCount("AdminRolePermMapper.insert", obj);
		
		if (affected > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Override
	public int delete(Long roleId) {
		
		if (roleId == null) {
			return 0;
		}

		int affected = dao.updateByObj("AdminRolePermMapper.delete", roleId);
		return affected;

	}

	@Override
	public List<AdminRolePerm> query(Long roleId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		
		List<AdminRolePerm> list = dao.queryList("AdminRolePermMapper.query", map);
		return list;
	}

}
