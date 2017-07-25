package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.vo.MemberVipLogVO;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public interface MemberVipLogService {

	PageResult<MemberVipLog> queryPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo);

	PageResult<MemberVipLogVO> queryVOPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo);

	ModelResult<Integer> count(MemberVipLog qVo);

	ModelResult<MemberVipLog> save(MemberVipLog obj);

}
