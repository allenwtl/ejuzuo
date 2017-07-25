package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberOperLog;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.option.MemberOperLogOption;
import com.ejuzuo.common.service.MemberOperLogService;
import com.ejuzuo.server.member.manager.MemberOperLogManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/4/12 0012.
 */
@Service("memberOperLogService")
public class MemberOperLogServiceImpl implements MemberOperLogService {

    @Resource
    private MemberOperLogManager memberOperLogManager ;

    @Override
    public ModelResult<Boolean> save(MemberOperLog memberOperLog) {
        return memberOperLogManager.save(memberOperLog);
    }

    @Override
    public ModelResult<Integer> queryOperLogToday(Integer memberId,  MemberOperLogType memberOperLogType) {
        return memberOperLogManager.queryOperLogToday(memberId, memberOperLogType);
    }

	@Override
	public PageResult<MemberOperLog> queryPage(DataPage<MemberOperLog> dataPage, MemberOperLogOption qVo) {
		return memberOperLogManager.queryPage(dataPage,qVo);
	}
}
