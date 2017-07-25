package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.option.MemberSponsorLogOption;
import com.ejuzuo.common.service.MemberSponsorLogService;
import com.ejuzuo.server.member.manager.MemberSponsorLogManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/5/11 0011.
 */

@Service("memberSponsorLogService")
public class MemberSponsorLogServiceImpl implements MemberSponsorLogService {

    @Resource
    private MemberSponsorLogManager memberSponsorLogManager;

    @Override
    public ModelResult<MemberSponsorLog> save(MemberSponsorLog memberSponsorLog) {
        return new ModelResult<>(memberSponsorLogManager.save(memberSponsorLog));
    }

    @Override
    public ModelResult<MemberSponsorLog> queryByChargeNo(String chargeNo) {
        return new ModelResult<>(memberSponsorLogManager.queryByChargeNo(chargeNo));
    }

    @Override
    public ModelResult<MemberSponsorLog> updateByCharge(MemberSponsorLog memberSponsorLog) {
        return memberSponsorLogManager.updateByCharge(memberSponsorLog);
    }

	@Override
	public PageResult<MemberSponsorLog> queryPage(MemberSponsorLogOption qVo, DataPage<MemberSponsorLog> dataPage) {
		return memberSponsorLogManager.queryPage(qVo,dataPage);
	}
}
