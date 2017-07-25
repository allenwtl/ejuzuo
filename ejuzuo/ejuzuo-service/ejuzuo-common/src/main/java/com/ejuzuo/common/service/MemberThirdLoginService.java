package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberThirdLogin;
import com.ejuzuo.common.option.MemberThirdLoginOption;

public interface MemberThirdLoginService {

	PageResult<MemberThirdLogin> queryPage(DataPage<MemberThirdLogin> dataPage, MemberThirdLoginOption qVo);

	ModelResult<MemberThirdLogin> save(MemberThirdLogin memberThirdLogin);

	ModelResult<MemberThirdLogin> queryByOption(MemberThirdLoginOption option);

}
