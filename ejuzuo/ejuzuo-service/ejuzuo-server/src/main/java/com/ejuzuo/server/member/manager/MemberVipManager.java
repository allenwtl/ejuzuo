package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberVip;

public interface MemberVipManager {
    int deleteById(Integer id);

    MemberVip save(MemberVip record);

    MemberVip selectById(Integer id);

    ModelResult<MemberVip> saveVipWithLog(MemberVip memberVip);

    MemberVip selectByMemberId(Integer memberId);

    MemberVip judgeVipByMemberId(Integer memberId);

    MemberVip updateVipTime(Integer memberId, String updateAccount, MemberPointLogTransType memberPointLogTransType);

    int updateById(MemberVip record);

	DataPage<MemberVip> queryPage(DataPage<MemberVip> dataPage, MemberVip qVo);
}