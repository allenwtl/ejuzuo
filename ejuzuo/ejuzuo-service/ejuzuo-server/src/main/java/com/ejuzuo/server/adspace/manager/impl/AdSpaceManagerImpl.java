package com.ejuzuo.server.adspace.manager.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.AdSpace;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.adspace.manager.AdSpaceManager;

@Repository
public class AdSpaceManagerImpl implements AdSpaceManager {

	@Resource(name = "dao")
    private GenericDao dao;
	
	@Override
	public Integer insert(AdSpace obj) {
		return dao.insertAndReturnAffectedCount("AdSpaceMapper.insert", obj);
	}

	@Override
	public Integer update(AdSpace obj) {
		return dao.updateByObj("AdSpaceMapper.update", obj);
	}

	@Override
	public AdSpace queryById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return dao.queryUnique("AdSpaceMapper.queryById", map);
	}

	@Override
	public DataPage<AdSpace> queryPage(DataPage<AdSpace> dataPage, AdSpace qVo) {
		Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		return dao.queryPage("AdSpaceMapper.countPage", "AdSpaceMapper.queryPage", map, dataPage);
	}

}
