package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/15 0015.
 */
public class MemberVipType extends BaseType {

	private static final long serialVersionUID = -850280261536352566L;

	private MemberVipType(Integer index, String description) {
        super(index, description);
    }
    public static final MemberVipType NO_VIP = new MemberVipType(0, "不是vip");
    public static final MemberVipType IS_VIP = new MemberVipType(1, "是vip");

    public static List<MemberVipType> getAllList(){
        return getAll(MemberVipType.class);
    }

    public static MemberVipType findByIndex(Integer index){
        return valueOf(MemberVipType.class, index);
    }
}
