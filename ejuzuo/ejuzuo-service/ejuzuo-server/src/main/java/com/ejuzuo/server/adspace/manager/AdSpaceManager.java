package com.ejuzuo.server.adspace.manager;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.AdSpace;

public interface AdSpaceManager {

	public Integer insert(AdSpace obj);
	
	public Integer update(AdSpace obj);
	
	public AdSpace queryById(Integer id);

	public DataPage<AdSpace> queryPage(DataPage<AdSpace> dataPage, AdSpace qVo);
	
	
}
