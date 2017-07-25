package com.ejuzuo.server.activity.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ActivityEnroll;
import com.ejuzuo.common.option.ActivityEnrollOption;

public interface ActivityEnrollManager {

	int save(ActivityEnroll obj);

    ActivityEnroll queryById(Integer id);

    int update(ActivityEnroll obj);

    ModelResult<Boolean> signUp(ActivityEnrollOption option);

    ModelResult<ActivityEnroll> queryByMemberIdAndActivityId(Integer activityId, Integer memberId);

	DataPage<ActivityEnroll> queryPage(DataPage<ActivityEnroll> dataPage, ActivityEnrollOption option);
    
}