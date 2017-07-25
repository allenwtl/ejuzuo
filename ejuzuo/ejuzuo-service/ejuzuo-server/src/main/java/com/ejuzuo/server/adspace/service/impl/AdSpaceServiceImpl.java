package com.ejuzuo.server.adspace.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdSpace;
import com.ejuzuo.common.service.AdSpaceService;
import com.ejuzuo.server.adspace.manager.AdSpaceManager;

@Service("adSpaceService")
public class AdSpaceServiceImpl implements AdSpaceService{
	
	@Resource
	private AdSpaceManager adSpaceManager;

	@Override
	public PageResult<AdSpace> queryPage(DataPage<AdSpace> dataPage, AdSpace qVo) {
		PageResult<AdSpace> result = new PageResult<AdSpace>();
		dataPage = adSpaceManager.queryPage(dataPage,qVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<Integer> add(AdSpace obj) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		Integer added = adSpaceManager.insert(obj);
		result.setModel(added);
		return result;
	}

	@Override
	public ModelResult<Integer> update(AdSpace obj) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		Integer updated = adSpaceManager.update(obj);
		result.setModel(updated);
		return result;
	}

	@Override
	public ModelResult<AdSpace> queryById(Integer id) {
		ModelResult<AdSpace> result = new ModelResult<AdSpace>();
		AdSpace obj = adSpaceManager.queryById(id);
		result.setModel(obj);
		return result;
	}
	
}
