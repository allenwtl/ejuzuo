package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.vo.MemberPointLogVO;
import com.ejuzuo.server.common.support.point.MemberPointHandler;
import com.ejuzuo.server.member.manager.MemberPointLogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Service("memberPointLogService")
public class MemberPointLogServiceImpl implements MemberPointLogService {

    @Resource
    private MemberPointHandler memberPointHandler ;

    @Resource
    private MemberPointLogManager memberPointLogManager ;

    @Override
    public ModelResult<MemberPointLog> save(MemberPointLog memberPointLog) {
        return new ModelResult<>(memberPointLogManager.save(memberPointLog));
    }

    @Override
    public ModelResult<MemberPointLog> queryById(Integer id) {
        return new ModelResult<>(memberPointLogManager.selectById(id));
    }

    @Override
    public ModelResult<DataPage<MemberPointLog>> queryByPage(MemberPointLogOption option, DataPage<MemberPointLog> dataPage) {
        return memberPointLogManager.queryByPage(option, dataPage);
    }

    @Override
    public ModelResult<Integer> count(MemberPointLogOption option) {
        return memberPointLogManager.count(option);
    }

    @Override
    public ModelResult<DataPage<MemberPointLogVO>> queryVOByPage(MemberPointLogOption option, DataPage<MemberPointLogVO> dataPage) {
        return memberPointLogManager.queryVOByPage(option, dataPage);
    }

    @Override
    public ModelResult<Boolean> sendPoint(MemberPointLog memberPointLog) {
        if(memberPointLog.getTransType() == MemberPointLogTransType.APPLY_DESIGNER.getIndex()){
            return memberPointHandler.sendPointAfterApplyDesigner(memberPointLog.getMemberId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.DONATE_ONE.getIndex()){
            return memberPointHandler.sendPointAfterDonate(memberPointLog.getMemberId(),
                    MemberPointLogTransType.DONATE_ONE, memberPointLog.getRelatedId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.DONATE_TWO.getIndex()){
            return memberPointHandler.sendPointAfterDonate(memberPointLog.getMemberId(),
                    MemberPointLogTransType.DONATE_TWO, memberPointLog.getRelatedId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.LOGIN.getIndex()){
            return memberPointHandler.sendPointAfterLogin(memberPointLog.getMemberId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.MOBILE_EMAIL.getIndex()){
            return memberPointHandler.sendPointAfterEM(memberPointLog.getMemberId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.SHARE.getIndex()){
            return memberPointHandler.sendPointAfterShare(memberPointLog.getMemberId(), memberPointLog.getRelatedId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.REGISTER.getIndex()){
            return memberPointHandler.sendPointAfterRegister(memberPointLog.getMemberId());
        }

        if(memberPointLog.getTransType() == MemberPointLogTransType.UPLOAD_WORK.getIndex()){
            return memberPointHandler.sendPointAfterUploadWork(memberPointLog.getMemberId(), memberPointLog.getRelatedId());
        }

        return new ModelResult<>(Boolean.TRUE);
    }
}
