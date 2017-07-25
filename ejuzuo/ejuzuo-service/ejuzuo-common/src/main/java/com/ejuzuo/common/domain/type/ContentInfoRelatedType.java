package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class ContentInfoRelatedType extends BaseType {
	public ContentInfoRelatedType(Integer index, String description) {
		super(index, description);
	}
	public static ContentInfoRelatedType NEWS = new ContentInfoRelatedType(0, "资讯");
	public static ContentInfoRelatedType ACTIVITY = new ContentInfoRelatedType(1, "活动");
	
}