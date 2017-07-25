package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberOperLog;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.option.MemberOperLogOption;

/**
 * Created by tianlun.wu on 2016/4/12 0012.
 */
public interface MemberOperLogService {

    ModelResult<Boolean> save(MemberOperLog memberOperLog);

    ModelResult<Integer> queryOperLogToday(Integer memberId, MemberOperLogType memberOperLogType);

	PageResult<MemberOperLog> queryPage(DataPage<MemberOperLog> dataPage, MemberOperLogOption qVo);

}
