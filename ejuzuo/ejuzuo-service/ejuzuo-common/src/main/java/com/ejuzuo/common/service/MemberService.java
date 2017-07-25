package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.option.MemberOption;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/3/28 0028.
 */

public interface MemberService {
    ModelResult<Member> saveMember(Member member);

    ModelResult<List<Member>> queryByOption(MemberOption option);

    ModelResult<Member> queryByAccount(String account);

    ModelResult<Member> queryByMemberId(Integer memberId);

    ModelResult<Boolean> judgeEmailOrMobileIsExist(String code, CheckCodeRecordDestType destType);

    ModelResult<Boolean> updateById(Member member);

    /**
     * 用户登录之后调用一下
     * @param memberId
     * @return
     */
    ModelResult<Boolean> saveOperLogAndGivePoint(Integer memberId);

	PageResult<Member> queryPage(DataPage<Member> dataPage, MemberOption qVo);

	ModelResult<Boolean> update(Integer id, Integer locked, Integer status);
	
	public boolean isVipByMemberId(Integer memberId);
	
	public boolean isDesingerByMemberId(Integer memberId);
}
