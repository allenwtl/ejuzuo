package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.vo.MemberVipLogVO;

public interface MemberVipLogManager {
    int deleteById(Integer id);

    MemberVipLog save(MemberVipLog record);

    MemberVipLog selectById(Integer id);

    int updateById(MemberVipLog record);

	DataPage<MemberVipLog> queryPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo);

    DataPage<MemberVipLogVO> queryVOPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo);

    ModelResult<Integer> count(MemberVipLog qVo);
}