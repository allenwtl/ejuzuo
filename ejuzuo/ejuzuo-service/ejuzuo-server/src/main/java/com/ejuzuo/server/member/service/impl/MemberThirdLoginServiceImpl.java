package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberThirdLogin;
import com.ejuzuo.common.option.MemberThirdLoginOption;
import com.ejuzuo.common.service.MemberThirdLoginService;
import com.ejuzuo.server.member.manager.MemberThirdLoginManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("memberThirdLoginService")
public class MemberThirdLoginServiceImpl implements MemberThirdLoginService{

	@Resource
	private MemberThirdLoginManager memberThirdLoginManager;

	@Override
	public PageResult<MemberThirdLogin> queryPage(DataPage<MemberThirdLogin> dataPage, MemberThirdLoginOption qVo) {
		return memberThirdLoginManager.queryPage(dataPage,qVo);
	}

	@Override
	public ModelResult<MemberThirdLogin> save(MemberThirdLogin memberThirdLogin) {
		return memberThirdLoginManager.save(memberThirdLogin);
	}

	@Override
	public ModelResult<MemberThirdLogin> queryByOption(MemberThirdLoginOption option) {
		return memberThirdLoginManager.queryByOption(option);
	}

}
