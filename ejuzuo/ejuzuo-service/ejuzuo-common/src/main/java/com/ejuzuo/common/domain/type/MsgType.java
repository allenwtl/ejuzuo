package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class MsgType extends BaseType {

	 private MsgType(Integer index, String description) {
	        super(index, description);
	    }
    public static final MsgType xitong = new MsgType(0,"系统消息");

    public static final MsgType geren = new MsgType(1, "个人消息");
}
