package com.ejuzuo.server.admin.manager.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.AdminUserLog;
import com.ejuzuo.server.admin.manager.AdminUserLogManager;

@Repository
public class AdminUserLogManagerImpl implements AdminUserLogManager {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminUserLogManagerImpl.class);
	
	@Autowired
    protected GenericDao dao;

	@Override
	public AdminUserLog insert(AdminUserLog obj) {
		
		/*long id = dao.generateSequence("AdminUserLogMapper.generateSequence");
		obj.setId(id);*/
		if (obj == null) {
			return null;
		}
		
		obj.setLogDate(Calendar.getInstance());
		int affected = dao.insertAndReturnAffectedCount("AdminUserLogMapper.insert", obj);
		
		if (affected > 0) {
			return obj;
		} else {
			return null;
		}
	}

	@Override
	public AdminUserLog query(Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		AdminUserLog obj = dao.queryUnique("AdminUserLogMapper.query", map);
		return obj;
		
	}

	@Override
	public DataPage<AdminUserLog> queryPage(DataPage<AdminUserLog> dataPage, AdminUserLog qObj, Calendar beginDate, Calendar endDate) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		
		if (qObj.getStatus() != null) {
			map.put("status", qObj.getStatus());
		}
		if (StringUtils.isNotBlank(qObj.getAction())) {
			map.put("action", "%" + qObj.getAction() + "%");
		}
		if (StringUtils.isNotBlank(qObj.getUserName())) {
			map.put("userName", "%" + qObj.getUserName() + "%");
		}
		if (StringUtils.isNotBlank(qObj.getUri())) {
			map.put("uri", "%" + qObj.getUri() + "%");
		}
		
		if (beginDate != null) {
			beginDate.set(Calendar.HOUR_OF_DAY, 0);
			beginDate.set(Calendar.MINUTE, 0);
			beginDate.set(Calendar.SECOND, 0);
			beginDate.set(Calendar.MILLISECOND, 0);
			map.put("beginDate", beginDate);
		}
		if (endDate != null) {
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			map.put("endDate", endDate);
		}
		
		dataPage = dao.queryPage("AdminUserLogMapper.countPage", "AdminUserLogMapper.queryPage", map, dataPage);
		return dataPage;
	}

}
