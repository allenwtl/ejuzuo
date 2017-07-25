package com.ejuzuo.server.activity.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ActivityEnroll;
import com.ejuzuo.common.option.ActivityEnrollOption;
import com.ejuzuo.common.service.ActivityEnrollService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.server.activity.manager.ActivityEnrollManager;

@Service("activityEnrollService")
public class ActivityEnrollServiceImpl implements ActivityEnrollService{

	@Resource
	private ActivityEnrollManager activityEnrollManager;
	@Resource
	private MemberService memberService;
	
	@Override
	public ModelResult<Integer> save(ActivityEnroll obj) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		Integer affected = activityEnrollManager.save(obj);
		result.setModel(affected);
		return result;
	}

	@Override
	public ModelResult<Integer> update(ActivityEnroll obj) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		Integer affected = activityEnrollManager.update(obj);
		result.setModel(affected);
		return result;
	}

	@Override
	public ModelResult<ActivityEnroll> queryById(Integer id) {
		ModelResult<ActivityEnroll> result = new ModelResult<ActivityEnroll>();
		ActivityEnroll obj = activityEnrollManager.queryById(id);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<Boolean> signUp(ActivityEnrollOption option) {
		//普通会员不能参与活动。
		boolean canSignUp = false;
		if(memberService.isDesingerByMemberId(option.getMemberId())){
			canSignUp = true;		
		}
		if (!canSignUp) {
			if(memberService.isVipByMemberId(option.getMemberId())) {
				canSignUp = true;
			}
		}
		if(!canSignUp) {
			//普通会员不能参与活动！
			return new ModelResult<Boolean>(Boolean.FALSE).withError("error", "您好！普通会员暂时没有参加活动的权限，请及时通过完善个人验证/公司验证资料升级成为高级会员或者成为VIP会员使用参加活动的权限！");
		}
		return activityEnrollManager.signUp(option);
	}

	@Override
	public ModelResult<ActivityEnroll> queryByMemberIdAndActivityId(Integer activityId, Integer memberId) {

		return activityEnrollManager.queryByMemberIdAndActivityId(activityId, memberId);
	}

	@Override
	public PageResult<ActivityEnroll> queryPage(DataPage<ActivityEnroll> dataPage, ActivityEnrollOption option) {
		PageResult<ActivityEnroll> result = new PageResult<ActivityEnroll>();
		dataPage = activityEnrollManager.queryPage(dataPage,option);
		result.setPage(dataPage);
		return result;
	}

	
}
