package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public  class CheckCodeRecordCheckType extends BaseType {
	public CheckCodeRecordCheckType(Integer index, String description) {
		super(index, description);
	}

	public static CheckCodeRecordCheckType register = new CheckCodeRecordCheckType(0, "注册");
	public static CheckCodeRecordCheckType activiate = new CheckCodeRecordCheckType(1, "用户激活");
	public static CheckCodeRecordCheckType authMobile = new CheckCodeRecordCheckType(2, "验证手机");
	public static CheckCodeRecordCheckType authEmail = new CheckCodeRecordCheckType(3, "验证邮箱");
	public static CheckCodeRecordCheckType findPwd = new CheckCodeRecordCheckType(4, "找回密码");
	public static CheckCodeRecordCheckType updatePwd = new CheckCodeRecordCheckType(5, "修改密码");

	public static CheckCodeRecordCheckType findByIndex(Integer index){
		return valueOf(CheckCodeRecordCheckType.class, index);
	}
	
	public static void main(String[] args) {
		if (CheckCodeRecordCheckType.findPwd == CheckCodeRecordCheckType.findPwd) {
			System.out.println("ok");
		}
	}
}