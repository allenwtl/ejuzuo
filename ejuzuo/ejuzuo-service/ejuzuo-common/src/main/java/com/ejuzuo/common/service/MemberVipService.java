package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberVip;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public interface MemberVipService {

    ModelResult<MemberVip> queryByMemberId(Integer memberId);

	PageResult<MemberVip> queryPage(DataPage<MemberVip> dataPage, MemberVip qVo);

    /**
     * 有值返回 就是vip
     * @param memberId
     * @return
     */
    ModelResult<MemberVip> judgeVipByMemberId(Integer memberId);

    ModelResult<MemberVip> saveMemberVip(MemberVip memberVip);

    /**
     * 调整VIP月数
     * @param id
     * @param period
     * @param updator
     * @return
     */
	BaseResult update(Integer id, Integer period, String updator);
}
