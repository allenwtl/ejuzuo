package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

/**
 * Created by allen on 2016/4/3.
 */
public class Status extends BaseType {
	private static final long serialVersionUID = -6683450927741874490L;
	
	public Status(Integer index, String description) {
        super(index, description);
    }
	
    public static final Status UN_STATUS = new Status(0, "无效");
    public static final Status STATUS = new Status(1, "有效");

}
