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
import com.ejuzuo.common.domain.AdminUserOperLog;
import com.ejuzuo.server.admin.manager.AdminUserOperLogManager;

@Repository
public class AdminUserOperLogManagerImpl implements AdminUserOperLogManager {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(AdminUserOperLogManagerImpl.class);
	
	@Autowired
    protected GenericDao dao;

	@Override
	public boolean insert(AdminUserOperLog obj) {
		
		if (obj == null) {
			return false;
		}
		
		int affected = dao.insertAndReturnAffectedCount("AdminUserOperLogMapper.insert", obj);
		return affected > 0;
	}

	@Override
	public DataPage<AdminUserOperLog> queryPage(
			DataPage<AdminUserOperLog> dataPage, 
			AdminUserOperLog qVo, 
			Calendar beginDate, 
			Calendar endDate) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		
		if (qVo != null) {
			if (qVo.getOperType() != null) {
				map.put("operType", qVo.getOperType());
			}
			if (StringUtils.isNotBlank(qVo.getAccount())) {
				map.put("account", qVo.getAccount());
			}
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
		
		dataPage = dao.queryPage("AdminUserOperLogMapper.countPage", "AdminUserOperLogMapper.queryPage", map, dataPage);
		return dataPage;
	}

}
