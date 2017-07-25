package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class NewsInfoCategory extends BaseType {
	public NewsInfoCategory(Integer index, String description) {
		super(index, description);
	}
	public static NewsInfoCategory xinwenzixun = new NewsInfoCategory(0, "新闻资讯");
	
	public static NewsInfoCategory wangzhanxinxi = new NewsInfoCategory(1, "网站信息");
	
	public static NewsInfoCategory pinpaijieshao = new NewsInfoCategory(2, "品牌介绍");
}