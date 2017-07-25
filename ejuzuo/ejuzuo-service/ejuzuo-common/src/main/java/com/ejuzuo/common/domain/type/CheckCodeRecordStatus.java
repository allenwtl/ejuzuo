package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class CheckCodeRecordStatus extends BaseType {
	public CheckCodeRecordStatus(Integer index, String description) {
		super(index, description);
	}

	public static CheckCodeRecordStatus notUsed = new CheckCodeRecordStatus(0, "未使用");
	public static CheckCodeRecordStatus used = new CheckCodeRecordStatus(1, "已使用");
}