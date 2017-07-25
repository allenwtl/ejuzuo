package com.ejuzuo.common.service;

import java.util.List;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.vo.ContentSearchResult;

public interface ElasticSearchService {

	/**
	 * 根据指定的关键字查询资讯的标题、内容、摘要，分页查询，默认按照发布时间倒序排序
	 * 
	 * @param keyword
	 * @param dataPage
	 * @return
	 */
	public ModelResult<DataPage<ContentSearchResult>> searchContentByKeyword(String keyword,Integer objectType,List<String> filterField,
			DataPage<ContentSearchResult> dataPage);

}
