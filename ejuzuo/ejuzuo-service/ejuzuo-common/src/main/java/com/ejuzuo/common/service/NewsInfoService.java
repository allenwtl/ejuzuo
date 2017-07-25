package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.vo.NewsInfoVO;

import java.util.List;

public interface NewsInfoService {

	public ModelResult<NewsInfo> queryById(Integer id);
	
	public ModelResult<NewsInfo> queryWithContent(Integer id);
	
	public ModelResult<NewsInfo> save(NewsInfo obj);
	
	public ModelResult<Integer> updateViewCount(Integer id);
	
	public PageResult<NewsInfo> queryPage(DataPage<NewsInfo> dataPage,NewsInfoOption qVo);
	
	public ModelResult<NewsInfo> add (NewsInfo obj, String content);

	public BaseResult updateWithContent(NewsInfo obj);

	ModelResult<Integer> count(NewsInfoOption qVo);

	PageResult<NewsInfoVO> queryVOPage(DataPage<NewsInfoVO> dataPage, NewsInfoOption qVo);

	public ModelResult<Boolean> updateStatus(Integer id, Integer status);

	/**
	 * 查询 size 条新闻资讯 但不包括 id 按照时间 倒序排列
	 * @param qVo
	 * @param size
	 * @return
	 */
	ModelResult<List<NewsInfo>> queryList(NewsInfoOption qVo, Integer size);

}
