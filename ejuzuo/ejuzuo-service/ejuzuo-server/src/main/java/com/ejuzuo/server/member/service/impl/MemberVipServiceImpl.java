package com.ejuzuo.server.member.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberVip;
import com.ejuzuo.common.service.MemberVipService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.server.member.manager.MemberVipManager;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Service("memberVipService")
public class MemberVipServiceImpl implements MemberVipService {

    @Resource
    private MemberVipManager memberVipManager;

	@Override
	public ModelResult<MemberVip> queryByMemberId(Integer memberId) {
		return new ModelResult<>(memberVipManager.selectByMemberId(memberId));
	}

	@Override
	public PageResult<MemberVip> queryPage(DataPage<MemberVip> dataPage, MemberVip qVo) {
		PageResult<MemberVip> result = new PageResult<MemberVip>();
		dataPage = memberVipManager.queryPage(dataPage,qVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<MemberVip> judgeVipByMemberId(Integer memberId) {
		return new ModelResult<>(memberVipManager.judgeVipByMemberId(memberId));
	}

	@Override
	public ModelResult<MemberVip> saveMemberVip(MemberVip memberVip) {
		return memberVipManager.saveVipWithLog(memberVip);
	}

	@Override
	public BaseResult update(Integer id, Integer period, String updator) {
		Calendar nowTime = Calendar.getInstance();
		MemberVip memberVip = memberVipManager.selectById(id);
		MemberVip tempVip = new MemberVip();
		tempVip.setId(id);
		tempVip.setUpdator(updator);
		tempVip.setUpdateTime(Calendar.getInstance());
		tempVip.setViped(1);
		if (memberVip.getEndTime().compareTo(nowTime) < 0) {
            tempVip.setStartTime(DateUtil.getTheDayZero());
            tempVip.setEndTime(DateUtil.add(DateUtil.getTheDayMidnight(), Calendar.MONTH, period));
        } else if (memberVip.getStartTime().compareTo(nowTime) < 0) {
            tempVip.setEndTime(DateUtil.add(memberVip.getEndTime(), Calendar.MONTH, period));
        }
		int affected = memberVipManager.updateById(tempVip);
		if (affected > 0) {
			return new BaseResult();
		}
		return new BaseResult().withError("vip update error","VIP调整失败");
	}


}
