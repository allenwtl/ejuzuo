package com.ejuzuo.server.member.manager;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.SmsRecord;
import com.ejuzuo.common.option.SmsRecordOption;

public interface SmsRecordManager {
	
	public BaseResult sendSmsRecord(String mobile, Integer memberId, String content);

	public DataPage<SmsRecord> queryPage(DataPage<SmsRecord> dataPage, SmsRecordOption qVo);

}
