package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.option.MemberSponsorLogOption;

/**
 * Created by tianlun.wu on 2016/5/11 0011.
 */
public interface MemberSponsorLogService {

    public ModelResult<MemberSponsorLog> save(MemberSponsorLog memberSponsorLog);

    public ModelResult<MemberSponsorLog> queryByChargeNo(String chargeNo);

    public ModelResult<MemberSponsorLog> updateByCharge(MemberSponsorLog memberSponsorLog);

	public PageResult<MemberSponsorLog> queryPage(MemberSponsorLogOption qVo, DataPage<MemberSponsorLog> dataPage);


}
