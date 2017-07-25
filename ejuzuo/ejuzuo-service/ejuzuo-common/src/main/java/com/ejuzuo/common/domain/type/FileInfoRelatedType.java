package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class FileInfoRelatedType extends BaseType{
	
	public FileInfoRelatedType(Integer index, String description) {
		super(index, description);
	}
	public static FileInfoRelatedType kong = new FileInfoRelatedType(0, "空");
	public static FileInfoRelatedType xinwen = new FileInfoRelatedType(1, "新闻资讯");
	public static FileInfoRelatedType huodong = new FileInfoRelatedType(2, "活动");
	public static FileInfoRelatedType shuzijiaju = new FileInfoRelatedType(3, "数字家居");
	public static FileInfoRelatedType pinpai = new FileInfoRelatedType(4, "品牌");
	public static FileInfoRelatedType shejizuopin = new FileInfoRelatedType(5, "设计作品");

}
