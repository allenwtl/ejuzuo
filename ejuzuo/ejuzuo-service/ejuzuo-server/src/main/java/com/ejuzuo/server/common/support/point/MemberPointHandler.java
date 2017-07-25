package com.ejuzuo.server.common.support.point;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.member.manager.MemberManager;
import com.ejuzuo.server.member.manager.MemberOperLogManager;
import com.ejuzuo.server.member.manager.MemberPointLogManager;
import com.ejuzuo.server.member.manager.MemberPointManager;
import com.ejuzuo.server.member.manager.MemberVipManager;

/**
 * Created by tianlun.wu on 2016/5/5 0005.
 */

@Service
public class MemberPointHandler {

    private static final Logger logger = LoggerFactory.getLogger(MemberPointHandler.class);


    @Resource
    private MemberOperLogManager memberOperLogManager;

    @Resource
    private MemberPointManager memberPointManager;

    @Resource
    private MemberPointLogManager memberPointLogManager;

    @Resource
    private MemberManager memberManager;

    @Resource
    private DesignerManager designerManager;

    @Resource
    private MemberVipManager memberVipManager ;
    
    @Resource
    private MemberService memberService;


    public ModelResult<Boolean> sendPointAfterRegister(Integer memberId) {
        MemberPoint memberPoint = memberPointManager.queryByMemberId(memberId);
        if (memberPoint == null) {
            memberPoint = new MemberPoint();
            memberPoint.setMemberId(memberId);
            memberPoint.setBalance(0);
            memberPoint.setRemark("初始化钱包");
            memberPointManager.save(memberPoint);
        }
        MemberPointLogTransType memberPointLogTransType = MemberPointLogTransType.REGISTER ;
        memberPoint.setBalance(memberPoint.getBalance() + memberPointLogTransType.getPoint());
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setAmount(memberPointLogTransType.getPoint());
        memberPointLog.setTransType(memberPointLogTransType.getIndex());
        memberPointLog.setRemark(memberPointLogTransType.getDescription());
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointManager.addPoint(memberPoint, memberPointLog);
        logger.info("用户[{}] 注册赠送积分:[{}]", memberPoint.getMemberId(), memberPointLogTransType.getPoint());
        return new ModelResult<>(Boolean.TRUE);
    }


    //暂时登录不送积分。
    public ModelResult<Boolean> sendPointAfterLogin(Integer memberId) {

        int size = memberOperLogManager.queryOperLogToday(memberId, MemberOperLogType.LOGIN).getModel();
        if (size > 1) {
            //不赠送积分
            logger.info("用户:[{}],今天登录次数超过1次，不赠送登录积分", memberId);
            return new ModelResult<>(Boolean.FALSE);
        }

        sendPoint(memberId, MemberPointLogTransType.LOGIN);

        return new ModelResult<>(Boolean.TRUE);
    }


    public ModelResult<Boolean> sendPointAfterApplyDesigner(Integer memberId) {

        Designer designer = designerManager.queryByMemberId(memberId).getModel();
        if (designer.getStatus() != DesignerStatus.shenhetongguo.getIndex()) {
            logger.info("用户:[{}]还未成功申请认证", memberId);
            return new ModelResult<>().withError("exception", "用户还未成功申请认证");
        }

        sendPoint(memberId, MemberPointLogTransType.APPLY_DESIGNER);

        return new ModelResult<>(Boolean.TRUE);
    }


    public ModelResult<Boolean> sendPointAfterUploadWork(Integer memberId, Integer relatedId) {
    	Integer amount = 0; //普通会员		
		if(memberService.isDesingerByMemberId(memberId)){
			amount = 5; //高级会员送5积分	
		}
		if(memberService.isVipByMemberId(memberId)) {
			amount = 10; //vip会员送10积分
		}
		if(amount == 0) {
			logger.info("普通会员上传作品不能送积分！作品ID:[{}]", relatedId);
			return new ModelResult<Boolean>(Boolean.FALSE).withError("error", "普通会员上传作品不能送积分！");
		}
    	    	
        MemberPointLogOption memberPointLogOption = new MemberPointLogOption();
        memberPointLogOption.setMemberId(memberId);
        memberPointLogOption.setTransType(MemberPointLogTransType.UPLOAD_WORK.getIndex());
        memberPointLogOption.setStartTime(DateUtil.getTheDayZero());
        memberPointLogOption.setEndTime(DateUtil.getTheDayMidnight());
        int size = memberPointLogManager.count(memberPointLogOption).getModel();
        if (size >= 10) {
            logger.info("用户:[{}] 上传作品超过了10次，不送积分", memberId);
            return new ModelResult<>().withError("exception", "用户上传作品超过了10次，不送积分。");
        }

        MemberPoint memberPoint = memberPointManager.queryByMemberId(memberId);
        memberPoint.setBalance(memberPoint.getBalance() + amount);
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointLog.setAmount(amount);
        memberPointLog.setTransType(MemberPointLogTransType.UPLOAD_WORK.getIndex());
        memberPointLog.setRemark(MemberPointLogTransType.UPLOAD_WORK.getDescription());
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointLog.setRelatedId(relatedId);
        memberPointLog.setRelatedType(ObjectType.shejizuopin.getIndex());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointManager.addPoint(memberPoint, memberPointLog);

        return new ModelResult<>(Boolean.TRUE);
    }


    public ModelResult<Boolean> sendPointAfterShare(Integer memberId, Integer relatedId) {
    	Integer amount = 0; //普通会员		
		if(memberService.isDesingerByMemberId(memberId)){
			amount = 50; //高级会员送50积分	
		}
		if(memberService.isVipByMemberId(memberId)) {
			amount = 80; //vip会员送80积分
		}
		if(amount == 0) {
			logger.info("普通会员分享没有积分送！分享ID:[{}]", relatedId);
			return new ModelResult<Boolean>(Boolean.FALSE).withError("error", "普通会员分享没有积分送！");
		}
    	
        MemberPointLogOption memberPointLogOption = new MemberPointLogOption();
        memberPointLogOption.setMemberId(memberId);
        memberPointLogOption.setTransType(MemberPointLogTransType.SHARE.getIndex());
        memberPointLogOption.setStartTime(DateUtil.getTheDayZero());
        memberPointLogOption.setEndTime(DateUtil.getTheDayMidnight());
        int size = memberPointLogManager.count(memberPointLogOption).getModel();
        if (size >= 5) {
            logger.info("用户:[{}] 今天获取分享的积分次数超过 5次", memberId);
            return new ModelResult<>().withError("exception", "今天获取分享的积分次数超过 5次");
        }

        
        MemberPoint memberPoint = memberPointManager.queryByMemberId(memberId);
        memberPoint.setBalance(memberPoint.getBalance() + amount);
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setAmount(amount);
        memberPointLog.setTransType(MemberPointLogTransType.SHARE.getIndex());
        memberPointLog.setRelatedType(ObjectType.share.getIndex());
        memberPointLog.setRelatedId(relatedId);
        memberPointLog.setRemark("分享所获取的积分");
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointManager.addPoint(memberPoint, memberPointLog);

        return new ModelResult<>(Boolean.TRUE);

    }


    public ModelResult<Boolean> sendPointAfterEM(Integer memberId) {

        Member member = memberManager.queryByMemberId(memberId).getModel();

        if (StringUtils.isNotBlank(member.getEmail()) && StringUtils.isNotBlank(member.getMobile())) {
            sendPoint(memberId, MemberPointLogTransType.MOBILE_EMAIL);
        }

        return new ModelResult<>(Boolean.TRUE);
    }


    public ModelResult<Boolean> sendPointAfterDonate(Integer memberId, MemberPointLogTransType memberPointLogTransType, Integer chargeId){
        MemberPoint memberPoint = memberPointManager.queryByMemberId(memberId);
        memberPoint.setBalance(memberPoint.getBalance() + memberPointLogTransType.getPoint());

        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setAmount(memberPointLogTransType.getPoint());
        memberPointLog.setTransType(memberPointLogTransType.getIndex());
        memberPointLog.setRelatedId(chargeId);
        memberPointLog.setRelatedType(ObjectType.charge.getIndex());
        memberPointLog.setRemark(memberPointLogTransType.getDescription());
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointManager.addPoint(memberPoint, memberPointLog);

        memberVipManager.updateVipTime(memberId, memberPointLog.getCreator(), memberPointLogTransType);

        return new ModelResult<>(Boolean.TRUE);
    }


    private void sendPoint(Integer memberId, MemberPointLogTransType memberPointLogTransType) {
        MemberPoint memberPoint = memberPointManager.queryByMemberId(memberId);
        memberPoint.setBalance(memberPoint.getBalance() + memberPointLogTransType.getPoint());
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setAmount(memberPointLogTransType.getPoint());
        memberPointLog.setTransType(memberPointLogTransType.getIndex());
        memberPointLog.setRemark(memberPointLogTransType.getDescription());
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointManager.addPoint(memberPoint, memberPointLog);
    }

}
