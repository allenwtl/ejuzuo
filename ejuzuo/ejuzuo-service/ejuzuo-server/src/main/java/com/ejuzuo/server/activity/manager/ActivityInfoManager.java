package com.ejuzuo.server.activity.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.vo.ActivityInfoVO;

public interface ActivityInfoManager {

	ActivityInfo save(ActivityInfo record);

    ActivityInfo queryById(Integer id);
    
    ActivityInfo queryWithContent(Integer id);

    ActivityInfoVO queryVOById(Integer id);

    ActivityInfo update(ActivityInfo record);

	DataPage<ActivityInfo> queryPage(DataPage<ActivityInfo> dataPage, ActivityInfoOption option);

    DataPage<ActivityInfoVO> queryVOPage(DataPage<ActivityInfoVO> dataPage, ActivityInfoOption option);

    ModelResult<Integer> count(ActivityInfoOption option);

	Integer updateViewCount(Integer id);

	Boolean updateStatus(Integer id, Integer status);
}