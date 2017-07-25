package com.ejuzuo.server.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.domain.type.ObjectType;

@Document(indexName = "content", type = "designerWorkIndex")
public class DesignerWorkIndex extends BaseIndex{

	public DesignerWorkIndex() {
	}

	public DesignerWorkIndex(DesignerWork designerWork) {
		this.setId(designerWork.getId());
		this.setObjectType(ObjectType.shejizuopin.getIndex());
		this.setTitle(designerWork.getName());
		this.setBrief(designerWork.getBrief());
		JSONArray jArray = JSONArray.parseArray(designerWork.getContent());
		String content = "";
		for (Object object : jArray) {
			JSONObject jo = (JSONObject) object;
			content += jo.get("intro")+" ";
		}
		this.setContent(content);
		this.setCreateTime(designerWork.getCreateTime());
		this.setPublishTime(designerWork.getUploadTime());
		this.setCoverImg(designerWork.getCoverImg());
	}
}
