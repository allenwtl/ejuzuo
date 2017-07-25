package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdSpace;

public interface AdSpaceService {

	PageResult<AdSpace> queryPage(DataPage<AdSpace> dataPage, AdSpace qVo);

	ModelResult<Integer> add(AdSpace obj);
	
	ModelResult<Integer> update(AdSpace obj);
	
	ModelResult<AdSpace> queryById(Integer id);
}
