package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/3/30 0030.
 */
public class MemberLockType extends BaseType {
    public MemberLockType(Integer index, String description) {
        super(index, description);
    }

    public static MemberLockType unLock = new MemberLockType(0, "未锁定");
    public static MemberLockType locked = new MemberLockType(1, "已锁定");
}
