package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.vo.MemberPointLogVO;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public interface MemberPointLogService {

    ModelResult<MemberPointLog> save(MemberPointLog memberPointLog);

    ModelResult<MemberPointLog> queryById(Integer id);

    ModelResult<DataPage<MemberPointLog>> queryByPage(MemberPointLogOption option , DataPage<MemberPointLog> dataPage);

    ModelResult<Integer> count(MemberPointLogOption option );

    ModelResult<DataPage<MemberPointLogVO>> queryVOByPage(MemberPointLogOption option , DataPage<MemberPointLogVO> dataPage);

    ModelResult<Boolean> sendPoint(MemberPointLog memberPointLog);

}
