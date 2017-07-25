package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class SmsRecordSendStatus extends BaseType {
	public SmsRecordSendStatus(Integer index, String description) {
		super(index, description);
	}

	public static SmsRecordSendStatus fail = new SmsRecordSendStatus(0, "失败");
	public static SmsRecordSendStatus succeed = new SmsRecordSendStatus(1, "成功");
	public static SmsRecordSendStatus sending = new SmsRecordSendStatus(2, "发送中");
}