package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.vo.ActivityInfoVO;

public interface ActivityInfoService {

	public ModelResult<ActivityInfo> save(ActivityInfo obj);
	
	public ModelResult<Integer> updateViewCount(Integer id);
	
	public ModelResult<ActivityInfo> queryById(Integer id);
	
	public ModelResult<ActivityInfo> queryWithContent(Integer id);

	public ModelResult<ActivityInfoVO> queryVOById(Integer id);

	public PageResult<ActivityInfo> queryPage(DataPage<ActivityInfo> dataPage, ActivityInfoOption option);

	public ModelResult<ActivityInfo> add(ActivityInfo obj, String content);

	public BaseResult updateWithContent(ActivityInfo obj);

	PageResult<ActivityInfoVO>  queryVOPage(DataPage<ActivityInfoVO> dataPage, ActivityInfoOption option);

	ModelResult<Integer> count(ActivityInfoOption option);

	public ModelResult<Boolean> updateStatus(Integer id, Integer status);
	
}
