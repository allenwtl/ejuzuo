package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberCare;

import java.util.List;


public interface MemberCareManager {
    ModelResult<Boolean> deleteById(Integer id);

    ModelResult<MemberCare> save(MemberCare record);

    ModelResult<MemberCare> selectById(Integer id);

    ModelResult<Integer> follow(MemberCare memberCare);

    ModelResult<List<MemberCare>> queryList(MemberCare memberCare);

    ModelResult<Boolean> updateById(MemberCare record);

    PageResult<MemberCare> queryPage(MemberCare memberCare, DataPage<MemberCare> dataPage);

    ModelResult<Integer> count(MemberCare memberCare);
}