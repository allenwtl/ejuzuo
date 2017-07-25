package com.ejuzuo.server.member.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.service.MemberVipLogService;
import com.ejuzuo.common.vo.MemberVipLogVO;
import com.ejuzuo.server.member.manager.MemberVipLogManager;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Service("memberVipLogService")
public class MemberVipLogServiceImpl implements MemberVipLogService {

    @Resource
    private MemberVipLogManager memberVipLogManager ;

	@Override
	public PageResult<MemberVipLog> queryPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo) {
		PageResult<MemberVipLog> result = new PageResult<MemberVipLog>();
		dataPage = memberVipLogManager.queryPage(dataPage,qVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public PageResult<MemberVipLogVO> queryVOPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo) {
		PageResult<MemberVipLogVO> result = new PageResult<>();
		result.setPage(memberVipLogManager.queryVOPage(dataPage, qVo));
		return result;
	}

	@Override
	public ModelResult<Integer> count(MemberVipLog qVo) {

		return memberVipLogManager.count(qVo);
	}

	@Override
	public ModelResult<MemberVipLog> save(MemberVipLog obj) {
		ModelResult<MemberVipLog> result = new ModelResult<>();
		MemberVipLog memberVipLog = memberVipLogManager.save(obj);
		result.setModel(memberVipLog);
		return result;
	}


}
