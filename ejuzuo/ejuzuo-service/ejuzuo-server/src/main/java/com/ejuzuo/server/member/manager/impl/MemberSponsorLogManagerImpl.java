package com.ejuzuo.server.member.manager.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.PayStatus;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.option.MemberSponsorLogOption;
import com.ejuzuo.common.service.MsgRecordService;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.common.support.point.MemberPointHandler;
import com.ejuzuo.server.member.manager.MemberManager;
import com.ejuzuo.server.member.manager.MemberSponsorLogManager;
import com.ejuzuo.server.member.manager.SmsRecordManager;
import com.ejuzuo.server.msg.manager.MsgRecordManager;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class MemberSponsorLogManagerImpl implements MemberSponsorLogManager {


    private static final Logger logger = LoggerFactory.getLogger(MemberSponsorLogManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;

    @Resource
    private MemberPointHandler memberPointHandler;
    
    @Resource 
    private SmsRecordManager smsRecordManager;
    
    @Resource
    private MemberManager memberManager;
    
    @Resource
    private MsgRecordManager msgRecordManager;

    @Resource
    private TaskExecutor taskExecutor;

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        int size = dao.updateByObj("MemberSponsorLogMapper.deleteById", id);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public MemberSponsorLog save(MemberSponsorLog record) {
        record.setStatus(PayStatus.UN_PAY.getIndex());
        record.setCreateTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("MemberSponsorLogMapper.save", record) ;
        return record;
    }

    @Override
    public MemberSponsorLog selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("MemberSponsorLogMapper.selectById", param);
    }

    @Override
    public MemberSponsorLog updateById(MemberSponsorLog record) {
        record.setUpdateTime(Calendar.getInstance());
        dao.updateByObj("MemberSponsorLogMapper.updateById", record);
        return record;
    }

    @Override
    public MemberSponsorLog queryByChargeNo(String chargeNo) {
        return dao.queryUnique("MemberSponsorLogMapper.queryByChargeNo", chargeNo);
    }

    @Override
    public ModelResult<MemberSponsorLog> updateByCharge(MemberSponsorLog memberSponsorLog) {

        memberSponsorLog.setUpdateTime(Calendar.getInstance());
        MemberPointLogTransType memberPointLogTransType = MemberPointLogTransType.findByIndex(memberSponsorLog.getSponsorType());
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    logger.info("用户:[{}] [{}]  更新充值订单:[{}]状态", memberSponsorLog.getMemberId(),
                        memberPointLogTransType.getDescription() , memberSponsorLog.getOrderNo());
                    int result = dao.updateByObj("MemberSponsorLogMapper.updateAfterCharge", memberSponsorLog);
                    if( result == 1){
                        logger.info("用户:[{}] [{}]  更新充值订单:[{}] 状态成功 给用户赠送积分",
                            memberSponsorLog.getMemberId(),  memberPointLogTransType.getDescription() , memberSponsorLog.getOrderNo());
                        memberPointHandler.sendPointAfterDonate(memberSponsorLog.getMemberId(), memberPointLogTransType, memberSponsorLog.getId());
                    } else {
                        logger.info("用户:[{}] [{}]  更新充值订单:[{}]状态不成功",
                            memberSponsorLog.getMemberId(), memberPointLogTransType.getDescription(), memberSponsorLog.getOrderNo());
                    }
                }
            });
        } catch (Exception e) {
            logger.error("修改赠送订单", e);
            return new ModelResult<>().withError("exception", "修改赠送订单异常");
        }

        //发送短信给用户告知VIP情况
        asynSendSmsRecord(memberSponsorLog);
        
        return new ModelResult<>(memberSponsorLog);
    }
    
    
    
    //发送短信告知VIP情况
    private void asynSendSmsRecord(MemberSponsorLog memberSponsorLog) {    	
		taskExecutor.execute(new Runnable(){
			@Override
			public void run() {
		    	Member member = null;
		    	ModelResult<Member> model = memberManager.queryByMemberId(memberSponsorLog.getMemberId());
		    	if (model.isSuccess()) {
		    		member = model.getModel();
		    	}    	
		    	if (member != null) { 
		    		Integer month = 0;
		    		String msgContent = null;
		    		if(memberSponsorLog.getSponsorType() ==4) {
		    			month = 6;
		    			msgContent = "您好！您已成功赞助巨作网，恭喜您升级成为巨作网的VIP会员，平台赠送您6个月VIP和400积分，请注意查看后台积分记录！";
		    		} else if (memberSponsorLog.getSponsorType() ==5) {
		    			month = 12;
		    			msgContent = "您好！您已成功赞助巨作网，恭喜您升级成为巨作网的VIP会员，平台赠送您18个月VIP和1000积分，请注意查看后台积分记录！";
		    		}		
		    		String content = "尊敬的用户，你的VIP期限已经延长" + month + "个月。";	
		    		
		    		
		    		//发送手机短信
		    		BaseResult br = smsRecordManager.sendSmsRecord(member.getMobile(), memberSponsorLog.getMemberId(), content); 
		    		if (!br.isSuccess()) {
		    			logger.error("发送VIP告知短信失败[memberId:{}]:{}", memberSponsorLog.getMemberId(), br.getErrorMsg());
		    		}
		    		
		    		//发送个人消息
	                logger.info("memberId [{}] 赞助成功，发送个人消息", memberSponsorLog.getMemberId());
	                msgRecordManager.addPersonMsg(memberSponsorLog.getId(), msgContent);  		    		
		    	}				
			}   
			
		});	
    }
    

	@Override
	public PageResult<MemberSponsorLog> queryPage(MemberSponsorLogOption qVo, DataPage<MemberSponsorLog> dataPage) {
		Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		if (qVo != null) {
            Calendar startTime = qVo.getStartTime();
            Calendar endTime = qVo.getEndTime();
            if (startTime != null) {
                startTime.set(Calendar.HOUR_OF_DAY, 0);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.SECOND, 0);
                startTime.set(Calendar.MILLISECOND, 0);
                map.put("startTime", startTime);
            }
            if (endTime != null) {
                endTime.set(Calendar.HOUR_OF_DAY, 23);
                endTime.set(Calendar.MINUTE, 59);
                endTime.set(Calendar.SECOND, 59);
                endTime.set(Calendar.MILLISECOND, 999);
                map.put("endTime", endTime);
            }
        }
		dataPage = dao.queryPage(
				"MemberSponsorLogMapper.countPage", 
				"MemberSponsorLogMapper.queryPage", map, dataPage);
		PageResult<MemberSponsorLog> result = new PageResult<MemberSponsorLog>();
		result.setPage(dataPage);
		return result;
	}
}
