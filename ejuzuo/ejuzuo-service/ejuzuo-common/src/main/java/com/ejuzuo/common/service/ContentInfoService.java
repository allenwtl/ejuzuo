package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;

public interface ContentInfoService {

	public ModelResult<ContentInfo> queryById(Integer id);
	
	public ModelResult<ContentInfo> query(ContentInfoRelatedType relatedType,Integer relatedId);
	
	public ModelResult<Integer> insert(ContentInfo obj);
}
