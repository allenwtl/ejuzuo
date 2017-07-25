package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class SmsRecordMobileType extends BaseType {
	public SmsRecordMobileType(Integer index, String description) {
		super(index, description);
	}

	public static SmsRecordMobileType other = new SmsRecordMobileType(0, "其他");
	public static SmsRecordMobileType chinamobile = new SmsRecordMobileType(1, "移动");
	public static SmsRecordMobileType chinatelecom = new SmsRecordMobileType(2, "电信");
	public static SmsRecordMobileType chinaunicom = new SmsRecordMobileType(3, "联通");
}