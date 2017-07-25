package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.option.MemberPointOption;
import com.ejuzuo.common.service.MemberPointService;
import com.ejuzuo.server.member.manager.MemberPointManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Service("memberPointService")
public class MemberPointServiceImpl implements MemberPointService {

    @Resource
    private MemberPointManager memberPointManager;

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        memberPointManager.deleteById(id);
        return new ModelResult<>(Boolean.TRUE);
    }

    @Override
    public ModelResult<MemberPoint> save(MemberPoint record) {
        return new ModelResult<>(memberPointManager.save(record));
    }

    @Override
    public ModelResult<MemberPoint> selectById(Integer id) {
        return new ModelResult<>(memberPointManager.selectById(id));
    }

    @Override
    public ModelResult<MemberPoint> selectByMemberId(Integer memberId) {
        return new ModelResult<>(memberPointManager.queryByMemberId(memberId));
    }

    @Override
    public ModelResult<Boolean> updateById(MemberPoint record) {
        int result = memberPointManager.updateById(record);
        if(result ==1){
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>().withError("exception", "更新错误");
    }

	@Override
	public PageResult<MemberPoint> queryPage(DataPage<MemberPoint> dataPage, MemberPointOption qVo) {
		return memberPointManager.queryPage(dataPage,qVo);
	}

    @Override
    public ModelResult<Integer> count(MemberPointOption qVo) {
        return memberPointManager.count(qVo);
    }

	@Override
	public ModelResult<Integer> updateBalance(Integer id, Integer memberId, Integer change) {
		return memberPointManager.updateBalance(id, memberId, change);
	}
}
