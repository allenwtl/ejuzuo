package com.ejuzuo.server.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.type.ObjectType;

@Document(indexName = "content", type = "digitalFurnitureIndex")
public class DigitalFurnitureIndex extends BaseIndex{


	public DigitalFurnitureIndex() {
	}

	public DigitalFurnitureIndex(DigitalFurniture digitalFurniture) {
		this.setId(digitalFurniture.getId());
		this.setObjectType(ObjectType.shuzijiaju.getIndex());
		this.setTitle(digitalFurniture.getName());
		
		String content = "";
		JSONArray picArray = JSONArray.parseArray(digitalFurniture.getPictures());
		if(picArray != null){
			for (Object object : picArray) {
				JSONObject jo = (JSONObject) object;
				content += jo.get("intro")+" ";
			}
		}
		
		JSONArray corporationArray = JSONArray.parseArray(digitalFurniture.getCorporation());
		if(corporationArray != null){
			for (Object object : corporationArray) {
				JSONObject jo = (JSONObject) object;
				content += jo.get("name")+":";
				content += jo.get("val")+" ";
			}
		}
//		JSONObject corporationJsonObject = JSONObject.parseObject(digitalFurniture.getCorporation());
//		Set<Entry<String, Object>> set = corporationJsonObject.entrySet();
//		for (Entry<String, Object> entry : set) {
//			content += entry.getKey()+":";
//			content += entry.getValue()+" ";
//		}
		this.setContent(content);
		this.setCreateTime(digitalFurniture.getCreateTime());
		this.setPublishTime(digitalFurniture.getUpdateTime());
		this.setCoverImg(digitalFurniture.getThumbnail());
	}
}
