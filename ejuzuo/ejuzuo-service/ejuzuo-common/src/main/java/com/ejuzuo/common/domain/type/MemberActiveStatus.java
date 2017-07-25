package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class MemberActiveStatus extends BaseType {
	public MemberActiveStatus(Integer index, String description) {
		super(index, description);
	}

	public static MemberActiveStatus unActive = new MemberActiveStatus(0, "待激活");
	public static MemberActiveStatus actived = new MemberActiveStatus(1, "已激活");
}