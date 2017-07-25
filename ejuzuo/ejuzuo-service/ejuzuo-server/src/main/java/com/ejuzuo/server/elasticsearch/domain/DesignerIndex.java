package com.ejuzuo.server.elasticsearch.domain;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.annotations.Document;

import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.type.ObjectType;

@Document(indexName = "content", type = "designerIndex")
public class DesignerIndex extends BaseIndex{

	public DesignerIndex() {
	}

	public DesignerIndex(Designer designer) {
		this.setId(designer.getId());
		this.setObjectType(ObjectType.shejishi.getIndex());
		this.setTitle(designer.getName());
		this.setBrief(designer.getBrief());
		
		if (StringUtils.isNotBlank(designer.getClassWorks())) {
			this.setContent(this.getContent() + "代表作品：" + designer.getClassWorks()+" ");
		}
		if (StringUtils.isNotBlank(designer.getDesignIdea())) {
			this.setContent(this.getContent() + "设计理念：" + designer.getDesignIdea()+" ");
		}
		if (StringUtils.isNotBlank(designer.getExperience())) {
			this.setContent(this.getContent() + "项目经验：" + designer.getExperience()+" ");
		}
		if (StringUtils.isNotBlank(designer.getStyleIntro())) {
			this.setContent(this.getContent() + "风格介绍：" + designer.getStyleIntro()+" ");
		}
		this.setCreateTime(designer.getCreateTime());
		this.setPublishTime(designer.getVerifyTime());

		if ( StringUtils.isNotEmpty(designer.getCoverImg()) ){
			JSONObject jsonObject = JSONObject.parseObject(designer.getCoverImg()) ;
			this.setCoverImg(jsonObject.getString("picold"));
		} else {
			this.setCoverImg("/images/notfound.jpg");
		}
	}
}
