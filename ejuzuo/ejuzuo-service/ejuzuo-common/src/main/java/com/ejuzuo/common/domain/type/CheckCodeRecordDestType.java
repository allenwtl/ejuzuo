package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class CheckCodeRecordDestType extends BaseType {
	public CheckCodeRecordDestType(Integer index, String description) {
		super(index, description);
	}

	public static CheckCodeRecordDestType mobile = new CheckCodeRecordDestType(0, "手机");
	public static CheckCodeRecordDestType email = new CheckCodeRecordDestType(1, "邮箱");
}