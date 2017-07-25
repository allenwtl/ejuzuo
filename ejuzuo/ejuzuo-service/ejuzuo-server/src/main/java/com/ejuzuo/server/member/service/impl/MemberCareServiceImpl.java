package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.service.MemberCareService;
import com.ejuzuo.server.member.manager.MemberCareManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/4/26 0026.
 */

@Service("memberCareService")
public class MemberCareServiceImpl implements MemberCareService {

    @Resource
    private MemberCareManager memberCareManager ;

    @Override
    public ModelResult<Integer> follow(MemberCare memberCare) {
        return memberCareManager.follow(memberCare);
    }

    @Override
    public ModelResult<Integer> count(MemberCare option) {
        return memberCareManager.count(option);
    }

    @Override
    public PageResult<MemberCare> queryPage(MemberCare option, DataPage<MemberCare> dataPage) {
        return memberCareManager.queryPage(option, dataPage);
    }
}
