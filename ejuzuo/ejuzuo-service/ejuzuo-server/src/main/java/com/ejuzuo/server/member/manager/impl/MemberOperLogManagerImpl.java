package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.MemberOperLog;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.option.MemberOperLogOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.server.member.manager.MemberOperLogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/4/12 0012.
 */

@Repository
public class MemberOperLogManagerImpl implements MemberOperLogManager {

    private static final Logger logger = LoggerFactory.getLogger(MemberOperLogManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public ModelResult<Boolean> save(MemberOperLog memberOperLog) {
        memberOperLog.setCreateTime(Calendar.getInstance());
        try {
            int size = dao.insertAndReturnAffectedCount("MemberOperLogMapper.save", memberOperLog);
            if (size == 1) {
                return new ModelResult<>(Boolean.TRUE);
            }
            return new ModelResult<>(Boolean.FALSE);
        } catch (Exception e) {
            logger.error("MemberOperLog.save 保存报错:{}", e);
            return new ModelResult<>().withError("exception", e.getMessage());
        }
    }

    @Override
    public ModelResult<Integer> queryOperLogToday(Integer memberId, MemberOperLogType memberOperLogType) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("operType", memberOperLogType.getIndex());
        param.put("startTime", DateUtil.getTheDayZero() );
        param.put("endTime", DateUtil.getTheDayMidnight());
        List<MemberOperLog> memberOperLogList = dao.queryList("MemberOperLogMapper.queryOperLogToday", param);
        if(memberOperLogList == null){
            return new ModelResult<>(0);
        }
        return new ModelResult<>(memberOperLogList.size());
    }

	@Override
	public PageResult<MemberOperLog> queryPage(DataPage<MemberOperLog> dataPage, MemberOperLogOption qVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		if (qVo != null) {
			if (qVo.getMemberId() != null) {
				map.put("memberId", qVo.getMemberId());
			}
			if (qVo.getOperType() != null) {
				map.put("operType", qVo.getOperType());
			}
			Calendar startTime = qVo.getStartTime();
			Calendar endTime = qVo.getEndTime();
			if (startTime != null) {
				startTime.set(Calendar.HOUR_OF_DAY, 0);
				startTime.set(Calendar.MINUTE, 0);
				startTime.set(Calendar.SECOND, 0);
				startTime.set(Calendar.MILLISECOND, 0);
				map.put("startTime", startTime);
			}
			if (endTime != null) {
				endTime.set(Calendar.HOUR_OF_DAY, 23);
				endTime.set(Calendar.MINUTE, 59);
				endTime.set(Calendar.SECOND, 59);
				endTime.set(Calendar.MILLISECOND, 999);
				map.put("endTime", endTime);
			}
		}
		dataPage = dao.queryPage("MemberOperLogMapper.countPage", "MemberOperLogMapper.queryPage", map, dataPage);
		PageResult<MemberOperLog> result = new PageResult<MemberOperLog>();
		result.setPage(dataPage);
		return result;
	}
}
