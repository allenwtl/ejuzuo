package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.CheckCodeRecord;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.option.CheckCodeRecordOption;
import com.ejuzuo.common.vo.UserOperationParam;

public interface CheckCodeRecordService {
	
	public ModelResult<String> sendSmsCheckCode(String mobile, CheckCodeRecordCheckType checkType, Member member, UserOperationParam userParam);
		
	public ModelResult<String>  checkSmsCheckCode(String checkcode, String mobile, CheckCodeRecordCheckType checkType, Member member);	

	public ModelResult<String> sendEmailCheckCode(String email, CheckCodeRecordCheckType checkType, Member member, UserOperationParam userParam);
	
	public ModelResult<String> checkEmailCheckCode(String checkCode, String email, CheckCodeRecordCheckType checkType, Member member);

	public PageResult<CheckCodeRecord> queryPage(DataPage<CheckCodeRecord> dataPage, CheckCodeRecordOption qVo);

}
