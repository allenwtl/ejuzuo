package com.ejuzuo.server.content.manager;

import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;

public interface ContentInfoManager {
	
	public ContentInfo queryById(Integer id);
	
	/**
	 * 根据关联类型和关联ID查出内容
	 * @param relatedType
	 * @param relatedId
	 * @return
	 */
	public ContentInfo query(ContentInfoRelatedType relatedType,Integer relatedId);
	
	public Integer insert(ContentInfo obj);

	public Integer update(ContentInfo obj);
	
	public Integer updateByRelate(ContentInfo obj);
}
