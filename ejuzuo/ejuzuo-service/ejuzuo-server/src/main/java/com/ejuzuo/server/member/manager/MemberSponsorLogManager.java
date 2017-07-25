package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.option.MemberSponsorLogOption;


public interface MemberSponsorLogManager {
    ModelResult<Boolean> deleteById(Integer id);

    MemberSponsorLog save(MemberSponsorLog record);

    MemberSponsorLog selectById(Integer id);

    MemberSponsorLog updateById(MemberSponsorLog record);

    MemberSponsorLog queryByChargeNo(String chargeNo);

    ModelResult<MemberSponsorLog> updateByCharge(MemberSponsorLog memberSponsorLog);

	PageResult<MemberSponsorLog> queryPage(MemberSponsorLogOption qVo, DataPage<MemberSponsorLog> dataPage);
}