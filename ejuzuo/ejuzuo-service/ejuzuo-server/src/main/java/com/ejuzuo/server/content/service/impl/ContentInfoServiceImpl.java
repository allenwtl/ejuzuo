package com.ejuzuo.server.content.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;
import com.ejuzuo.common.service.ContentInfoService;
import com.ejuzuo.server.content.manager.ContentInfoManager;

@Service("contentInfoService")
public class ContentInfoServiceImpl implements ContentInfoService {

	@Resource
	private ContentInfoManager contentInfoManager;
	@Override
	public ModelResult<ContentInfo> queryById(Integer id) {
		ModelResult<ContentInfo> result = new ModelResult<ContentInfo>();
		ContentInfo obj = contentInfoManager.queryById(id);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<ContentInfo> query(ContentInfoRelatedType relatedType, Integer relatedId) {
		ModelResult<ContentInfo> result = new ModelResult<ContentInfo>();
		ContentInfo obj = contentInfoManager.query(relatedType, relatedId);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<Integer> insert(ContentInfo obj) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		Integer affected = contentInfoManager.insert(obj);
		result.setModel(affected);
		return result;
	}
}
