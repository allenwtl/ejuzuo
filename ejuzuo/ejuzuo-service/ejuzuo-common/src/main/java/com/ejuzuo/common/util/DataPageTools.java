package com.ejuzuo.common.util;

import java.util.HashMap;
import java.util.Map;

import com.aicai.appmodel.page.DataPage;

public class DataPageTools {
	/** 分页常量,分页开始序号 */
	public static final String PAGINATION_START = "start";
	/** 分页常量,页大小 */
	public static final String PAGINATION_OFFSET = "offset";
	
	public static <T> Map<String, Object> toMap(DataPage<T> dataPage){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PAGINATION_START, dataPage.getStartIndex());
		params.put(PAGINATION_OFFSET, dataPage.getPageSize());
		return params;
	}
}
