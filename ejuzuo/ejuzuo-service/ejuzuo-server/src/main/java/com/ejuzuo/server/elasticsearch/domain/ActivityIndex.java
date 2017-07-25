package com.ejuzuo.server.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.common.domain.type.ObjectType;

@Document(indexName = "content", type = "activityIndex")
public class ActivityIndex extends BaseIndex{


	public ActivityIndex() {
	}

	public ActivityIndex(ActivityInfo activityInfo) {
		this.setId(activityInfo.getId());
		this.setObjectType(ObjectType.huodong.getIndex());
		this.setTitle(activityInfo.getTitle());
		this.setBrief(activityInfo.getBrief());
		this.setContent(activityInfo.getContent());
		this.setCreateTime(activityInfo.getCreateTime());
		this.setPublishTime(activityInfo.getPublishTime());
		this.setCoverImg(activityInfo.getThumbnail());
	}
	
}
