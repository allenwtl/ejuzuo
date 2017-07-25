package com.ejuzuo.server.member.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.SmsRecord;
import com.ejuzuo.common.option.SmsRecordOption;
import com.ejuzuo.common.service.SmsRecordService;
import com.ejuzuo.server.member.manager.MemberManager;
import com.ejuzuo.server.member.manager.SmsRecordManager;

@Service("smsRecordService")
public class SmsRecordServiceImpl implements SmsRecordService {

	private static final Logger logger = LoggerFactory.getLogger(SmsRecordServiceImpl.class);
	@Resource
	private SmsRecordManager smsRecordManager;
	@Resource
	private MemberManager memberManager;
	
	@Override
	public PageResult<SmsRecord> queryPage(DataPage<SmsRecord> dataPage, SmsRecordOption qVo) {
		PageResult<SmsRecord> result = new PageResult<SmsRecord>();
		dataPage = smsRecordManager.queryPage(dataPage,qVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public BaseResult sendSmsRecord(Integer memberId, String content) {
		Member member = memberManager.queryById(memberId).getModel();
		if (member == null || StringUtils.isBlank(member.getMobile())) {
			logger.error("发送短信失败，查询手机号为空，memberId:{}",memberId);
			return new BaseResult().withError("mobile is null", "mobile is null");
		}
		return smsRecordManager.sendSmsRecord(member.getMobile(), memberId, content);
	}
}
