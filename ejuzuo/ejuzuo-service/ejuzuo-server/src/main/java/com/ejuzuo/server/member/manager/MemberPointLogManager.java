package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.vo.MemberPointLogVO;

import java.util.List;


public interface MemberPointLogManager {
    int deleteById(Integer id);

    MemberPointLog save(MemberPointLog record);

    MemberPointLog selectById(Integer id);

    int updateById(MemberPointLog record);

    ModelResult<List<MemberPointLog>> queryList(MemberPointLogOption option);

    ModelResult<DataPage<MemberPointLog>> queryByPage(MemberPointLogOption option , DataPage<MemberPointLog> dataPage);

    ModelResult<DataPage<MemberPointLogVO>> queryVOByPage(MemberPointLogOption option , DataPage<MemberPointLogVO> dataPage);

    ModelResult<Integer> count(MemberPointLogOption option );

}