package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.option.MemberPointOption;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public interface MemberPointService {

    ModelResult<Boolean> deleteById(Integer id);

    ModelResult<MemberPoint> save(MemberPoint record);

    ModelResult<MemberPoint> selectById(Integer id);

    ModelResult<MemberPoint> selectByMemberId(Integer memberId);

    ModelResult<Boolean> updateById(MemberPoint record);

	PageResult<MemberPoint> queryPage(DataPage<MemberPoint> dataPage, MemberPointOption qVo);

    ModelResult<Integer> count(MemberPointOption qVo);

    /**
     * @param id
     * @param change
     * @return
     */
	ModelResult<Integer> updateBalance(Integer id,Integer memberId, Integer change);

}
