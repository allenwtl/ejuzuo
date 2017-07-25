package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ActivityEnroll;
import com.ejuzuo.common.option.ActivityEnrollOption;

public interface ActivityEnrollService {

	public ModelResult<Integer> save(ActivityEnroll obj);
	
	public ModelResult<Integer> update(ActivityEnroll obj);
	
	public ModelResult<ActivityEnroll> queryById(Integer id);

	ModelResult<Boolean> signUp(ActivityEnrollOption option);

	ModelResult<ActivityEnroll> queryByMemberIdAndActivityId(Integer activityId, Integer memberId);
	
	public PageResult<ActivityEnroll> queryPage(DataPage<ActivityEnroll> dataPage, ActivityEnrollOption option);
	
}
