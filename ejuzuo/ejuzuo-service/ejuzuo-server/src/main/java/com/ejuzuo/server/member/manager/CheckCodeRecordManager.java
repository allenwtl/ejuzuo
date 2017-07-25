package com.ejuzuo.server.member.manager;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.CheckCodeRecord;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.option.CheckCodeRecordOption;

import java.util.Date;
import java.util.List;

public interface CheckCodeRecordManager {

	public abstract int countCheckCodeByCheckType(String destNo,
			CheckCodeRecordDestType destType, Integer memberId, CheckCodeRecordCheckType checkType,
			Date fromDate);

	public abstract List<CheckCodeRecord> queryCheckCodeListByCheckType(
			String destNo, CheckCodeRecordDestType destType, CheckCodeRecordCheckType checkType);

	public abstract void insert(CheckCodeRecord record);

	public abstract void updateVerifyStatus(CheckCodeRecord record);

	public abstract DataPage<CheckCodeRecord> queryPage(DataPage<CheckCodeRecord> dataPage, CheckCodeRecordOption qVo);

}