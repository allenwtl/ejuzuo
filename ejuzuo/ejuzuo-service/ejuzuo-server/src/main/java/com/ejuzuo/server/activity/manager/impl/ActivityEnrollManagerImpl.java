package com.ejuzuo.server.activity.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.ActivityEnroll;
import com.ejuzuo.common.option.ActivityEnrollOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.activity.manager.ActivityEnrollManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ActivityEnrollManagerImpl implements ActivityEnrollManager {

	private static final Logger log = LoggerFactory.getLogger(ActivityEnrollManagerImpl.class);

	@Autowired
	private GenericDao dao;
	
	@Override
	public int save(ActivityEnroll obj) {
		obj.setCreateTime(Calendar.getInstance());
		obj.setEnrollTime(Calendar.getInstance());
		return dao.insertAndReturnAffectedCount("ActivityEnrollMapper.insert", obj);
	}

	@Override
	public ActivityEnroll queryById(Integer id) {
		return dao.queryUnique("ActivityEnrollMapper.queryById", id.longValue());
	}

	@Override
	public int update(ActivityEnroll obj) {
		return dao.updateByObj("ActivityEnrollMapper.update", obj);
	}

	@Override
	public ModelResult<Boolean> signUp(ActivityEnrollOption option) {
		List<ActivityEnroll> activityEnrollList = dao.queryList("ActivityEnrollMapper.selectList", JSONUtils.object2MapSpecail(option));
		if (activityEnrollList==null || activityEnrollList.isEmpty()){
			ActivityEnroll activityEnroll = new ActivityEnroll();
			activityEnroll.setMemberId(option.getMemberId());
			activityEnroll.setActivityId(option.getActivityId());

			save(activityEnroll);
			return new ModelResult<>(Boolean.TRUE);
		}
		log.info("用户[{}] 活动:[{}] 参加了报名", option.getMemberId(), option.getActivityId());
		return new ModelResult<>(Boolean.FALSE).withError("error", "你已经报名了");
	}

	@Override
	public ModelResult<ActivityEnroll> queryByMemberIdAndActivityId(Integer activityId, Integer memberId) {
		Map<String,Object> param = new HashMap<>();
		param.put("activityId", activityId);
		param.put("memberId", memberId);
		return new ModelResult<>(dao.queryUnique("ActivityEnrollMapper.selectList", param));
	}

	@Override
	public DataPage<ActivityEnroll> queryPage(DataPage<ActivityEnroll> dataPage, ActivityEnrollOption option) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (option != null) {
			if (option.getId() != null) {
				map.put("id", option.getId());
			}
			if (option.getMemberId() != null) {
				map.put("memberId", option.getMemberId());
			}
			if (option.getActivityId() != null) {
				map.put("activityId", option.getActivityId());
			}
			Calendar enrollBeiginTime = option.getEnrollBeiginTime();
			Calendar enrollEndTime = option.getEnrollEndTime();
			if (enrollBeiginTime != null) {
				enrollBeiginTime.set(Calendar.HOUR_OF_DAY, 0);
				enrollBeiginTime.set(Calendar.MINUTE, 0);
				enrollBeiginTime.set(Calendar.SECOND, 0);
				enrollBeiginTime.set(Calendar.MILLISECOND, 0);
				map.put("enrollBeiginTime", enrollBeiginTime);
			}
			if (enrollEndTime != null) {
				enrollEndTime.set(Calendar.HOUR_OF_DAY, 23);
				enrollEndTime.set(Calendar.MINUTE, 59);
				enrollEndTime.set(Calendar.SECOND, 59);
				enrollEndTime.set(Calendar.MILLISECOND, 999);
				map.put("enrollEndTime", enrollEndTime);
			}
		}
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		return dao.queryPage("ActivityEnrollMapper.countPage", "ActivityEnrollMapper.queryPage", map, dataPage);
	}

}
