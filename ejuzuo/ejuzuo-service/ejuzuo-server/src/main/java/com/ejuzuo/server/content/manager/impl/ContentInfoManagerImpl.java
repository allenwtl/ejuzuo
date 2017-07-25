package com.ejuzuo.server.content.manager.impl;

import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;
import com.ejuzuo.server.content.manager.ContentInfoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ContentInfoManagerImpl implements ContentInfoManager {

	@Autowired
	private GenericDao dao;
	
	@Override
	public ContentInfo queryById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return dao.queryUnique("ContentInfoMapper.queryById", map);
	}

	@Override
	public ContentInfo query(ContentInfoRelatedType relatedType, Integer relatedId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relatedType", relatedType.getIndex());
		map.put("relatedId", relatedId);
		return dao.queryUnique("ContentInfoMapper.query", map);
	}

	@Override
	public Integer insert(ContentInfo obj) {
		return dao.insertAndReturnAffectedCount("ContentInfoMapper.save", obj);
	}

	@Override
	public Integer update(ContentInfo obj) {
		return dao.updateByObj("ContentInfoMapper.update", obj);
	}

	@Override
	public Integer updateByRelate(ContentInfo obj) {
		return dao.updateByObj("ContentInfoMapper.updateByRelate", obj);
	}

}
