package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.SmsRecord;
import com.ejuzuo.common.option.SmsRecordOption;

public interface SmsRecordService {

	PageResult<SmsRecord> queryPage(DataPage<SmsRecord> dataPage, SmsRecordOption qVo);

	public BaseResult sendSmsRecord(Integer memberId, String content);
}
