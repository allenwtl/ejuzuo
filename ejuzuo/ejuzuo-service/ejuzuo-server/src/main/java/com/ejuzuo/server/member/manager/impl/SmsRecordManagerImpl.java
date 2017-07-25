package com.ejuzuo.server.member.manager.impl;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.SmsRecord;
import com.ejuzuo.common.domain.type.SmsRecordMobileType;
import com.ejuzuo.common.domain.type.SmsRecordSendStatus;
import com.ejuzuo.common.option.SmsRecordOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.util.MobileUtils;
import com.ejuzuo.server.common.support.sms.Sms;
import com.ejuzuo.server.common.support.sms.SmsChannel;
import com.ejuzuo.server.member.manager.SmsRecordManager;

@Repository
public class SmsRecordManagerImpl implements SmsRecordManager {
	
	private final static Logger logger = LoggerFactory.getLogger(SmsRecordManagerImpl.class);
	
	@Autowired
    protected GenericDao dao;	
	
	@Autowired
	private SmsChannel smsChannel;

	@Override
	public BaseResult sendSmsRecord(String mobile, Integer memberId, String content) {
		BaseResult result = new BaseResult();
		if (!MobileUtils.isMobile(mobile)) {
			return result.withError("exception","手机号格式不对");
		}
		if (StringUtils.isEmpty(content) || content.length() > 70) {
			return result.withError("exception", "内容长度错误");
		}		
		SmsRecord record = new SmsRecord();
		record.setMobile(mobile);
		record.setContent(content);		
		record.setGateway(1); //谐和通道
		record.setCreateDate(DateUtil.getTimestamp());
		record.setSendDate(DateUtil.getTimestamp());
		record.setMobileType(SmsRecordMobileType.valueOf(SmsRecordMobileType.class, MobileUtils.mobileNoType(mobile)));	
		record.setSendStatus(SmsRecordSendStatus.sending);
		dao.insertAndReturnAffectedCount("SmsRecordMapper.save",record);
		logger.info("sms record:" + record);
		
		//调用通道发送校验码
		BaseResult r = smsChannel.sendMessage(new Sms(mobile,content));
		if (r.isSuccess()) {
			record.setErrorMsg(null);
			record.setSendStatus(SmsRecordSendStatus.succeed);			
		} else {
			record.setSendStatus(SmsRecordSendStatus.fail);	
			String errorMsg = r.getErrorMsg();
			if (!StringUtils.isEmpty(errorMsg)  && errorMsg.length()>50 ) {
				errorMsg = errorMsg.substring(0,50);
			}
			record.setErrorMsg(errorMsg);
		}		
		dao.updateByObj("SmsRecordMapper.updateSmsRecordStatus", record);
		
		//发送结果
		if (record.getSendStatus() == SmsRecordSendStatus.succeed) {
			return result;
		} else {
			return result.withError("exception", record.getErrorMsg());
		}		
	}

	@Override
	public DataPage<SmsRecord> queryPage(DataPage<SmsRecord> dataPage, SmsRecordOption qVo) {
		Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		Calendar beginDate = qVo.getBeginDate();
		Calendar endDate = qVo.getEndDate();
		if (beginDate != null) {
			beginDate.set(Calendar.HOUR_OF_DAY, 0);
			beginDate.set(Calendar.MINUTE, 0);
			beginDate.set(Calendar.SECOND, 0);
			beginDate.set(Calendar.MILLISECOND, 0);
			map.put("beginDate", beginDate);
		}
		if (endDate != null) {
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			map.put("endDate", endDate);
		}
		return dao.queryPage("SmsRecordMapper.countPage", "SmsRecordMapper.queryPage", map, dataPage);
	}
	
	
}
