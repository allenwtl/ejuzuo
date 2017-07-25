package com.ejuzuo.server.member.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.CheckCodeRecord;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.CheckCodeRecordStatus;
import com.ejuzuo.common.option.CheckCodeRecordOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.member.manager.CheckCodeRecordManager;

@Repository
public class CheckCodeRecordManagerImpl implements CheckCodeRecordManager {
	
	private final static Logger logger = LoggerFactory.getLogger(CheckCodeRecordManagerImpl.class);
	
	@Autowired
    protected GenericDao dao;

	@Override
	public int countCheckCodeByCheckType(String destNo, CheckCodeRecordDestType destType,
			Integer memberId, CheckCodeRecordCheckType checkType, Date fromDate) {			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("destNo", destNo);
		map.put("destType", destType);
		map.put("memberId", memberId);
		map.put("checkType", checkType);
		map.put("fromDate", fromDate);		
		int count = dao.queryCount("CheckCodeRecordMapper.countCheckCodeByCheckType", map);
		return count;
	}


	@Override
	public List<CheckCodeRecord> queryCheckCodeListByCheckType(
			String destNo, CheckCodeRecordDestType destType, CheckCodeRecordCheckType checkType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("destNo", destNo);
		map.put("destType", destType);
		map.put("checkType", checkType);		
		return dao.queryList("CheckCodeRecordMapper.queryCheckCodeListByCheckType", map);
	}


	@Override
	public void insert(CheckCodeRecord record) {
		record.setVerifyCount(1);
		record.setStatus(CheckCodeRecordStatus.notUsed);
		record.setCreateTime(Calendar.getInstance());
		record.setUpdateTime(Calendar.getInstance());
		record.setSendTime(Calendar.getInstance());
		dao.insertAndSetupId("CheckCodeRecordMapper.save", record);
	}


	@Override
	public void updateVerifyStatus(CheckCodeRecord record) {
		dao.insertAndSetupId("CheckCodeRecordMapper.updateVerifyStatus", record);
	}


	@Override
	public DataPage<CheckCodeRecord> queryPage(DataPage<CheckCodeRecord> dataPage, CheckCodeRecordOption qVo) {
		Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		Calendar beginDate = qVo.getBeginDate();
		Calendar endDate = qVo.getEndDate();
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
		return dao.queryPage("CheckCodeRecordMapper.countPage", "CheckCodeRecordMapper.queryPage", map, dataPage);
	}

}
