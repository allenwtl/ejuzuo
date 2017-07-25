package com.ejuzuo.admin.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONArray;

@SuppressWarnings("serial")
public class Page<T> implements Serializable {

	private Integer offset;
	private Integer limit;
	private String sort;
	private String order;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public DataPage<T> toDataPage() {
		DataPage<T> dataPage = new DataPage<T>();
		
		dataPage.setPageSize(this.limit);
		dataPage.setPageNo(this.offset / this.limit + 1);
		dataPage.setOrderBy(this.sort);
		dataPage.setOrder(this.order);
		
		return dataPage;
	}
	
	public static <T> Map<String, Object> returnEmptyPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", 0);
		map.put("rows", new JSONArray());
		return map;
	}
	
	public static <T> Map<String, Object> returnPage(DataPage<T> dataPage) {
		if (dataPage == null) {
			return returnEmptyPage();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", dataPage.getTotalCount());
		map.put("rows", dataPage.getDataList());
		return map;
 	}

}
