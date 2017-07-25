package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

@SuppressWarnings("serial")
public class MemberVerified extends BaseType {
    public MemberVerified(Integer index, String description) {
        super(index, description);
    }


    public static MemberVerified noVerified = new MemberVerified(0, "普通会员");
    public static MemberVerified verified = new MemberVerified(1, "认证会员");

}