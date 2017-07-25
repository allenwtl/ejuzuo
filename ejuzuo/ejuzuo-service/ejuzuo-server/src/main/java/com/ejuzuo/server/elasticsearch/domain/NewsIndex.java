package com.ejuzuo.server.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.domain.type.ObjectType;

@Document(indexName = "content", type = "newsIndex")
public class NewsIndex extends BaseIndex{

	public NewsIndex() {
	}

	public NewsIndex(NewsInfo newsInfo) {
		this.setId(newsInfo.getId());
		this.setObjectType(ObjectType.zixun.getIndex());
		this.setTitle(newsInfo.getTitle());
		this.setBrief(newsInfo.getBrief());
		this.setContent(newsInfo.getContent());
		this.setCreateTime(newsInfo.getCreateTime());
		this.setPublishTime(newsInfo.getPublishTime());
		this.setCoverImg(newsInfo.getThumbnail());
	}
}
