package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberThirdLogin;
import com.ejuzuo.common.option.MemberThirdLoginOption;


public interface MemberThirdLoginManager {
	
    ModelResult<MemberThirdLogin> save(MemberThirdLogin record);

	PageResult<MemberThirdLogin> queryPage(DataPage<MemberThirdLogin> dataPage, MemberThirdLoginOption qVo);

    ModelResult<MemberThirdLogin> queryByOption(MemberThirdLoginOption option);
    
}