package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

public class SellClient extends BaseType {
	
	private static final long serialVersionUID = -6665527503970158864L;
	public static SellClient WEB = new SellClient(1, "WEB应用");
	public static SellClient WAP = new SellClient(2, "WAP应用");	
	public static SellClient PHONE_ANDROID = new SellClient(3, "android客户端");
	public static SellClient PHONE_IOS = new SellClient(4, "IOS客户端");
	public static SellClient ADMIN = new SellClient(5, "admin应用");
	

	private SellClient() {
		super(null, null);
	}

	private SellClient(int index, String description) {
		super(index, description);
	}

}
