package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.exception.common.MsgCode;
import com.ejuzuo.common.option.MemberOption;

import java.util.List;

public interface MemberManager {

    ModelResult<MsgCode> deleteById(Integer id);

    ModelResult<Member> save(Member member);

    ModelResult<Boolean> updateById(Member member);

    ModelResult<Member> queryByAccount(String account);

    ModelResult<Member> queryByMemberId(Integer memberId);

    ModelResult<List<Member>> queryByOption(MemberOption option);

    ModelResult<Boolean> judgeEmailOrMobileIsExist(String code, CheckCodeRecordDestType destType);

    ModelResult<Member> queryById(Integer id);

    PageResult<Member> queryByPage(MemberOption option , DataPage<Member> dataPage);

	ModelResult<Boolean> update(Integer id, Integer locked, Integer status);

	List<Member> querybyIds(List<Integer> memberIds);
}