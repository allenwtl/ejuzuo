package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
@SuppressWarnings("serial")
public class MemberVipLogTransType extends BaseType {
    private MemberVipLogTransType(Integer index, String description) {
        super(index, description);
    }

    public static final MemberVipLogTransType qita = new MemberVipLogTransType(0, "其它");

    public static final MemberVipLogTransType zanzhu = new MemberVipLogTransType(1, "赞助");

    public static final MemberVipLogTransType fenxiang = new MemberVipLogTransType(2, "分享");

    public static final MemberVipLogTransType xitongzengsong = new MemberVipLogTransType(3, "系统赠送");

    public static final MemberVipLogTransType yichangchuli = new MemberVipLogTransType(4, "异常处理");

    public static List<MemberVipLogTransType> getAllList(){
        return getAll(MemberVipLogTransType.class);
    }

    public static MemberVipLogTransType findByIndex(Integer index){
        return valueOf(MemberVipLogTransType.class, index);
    }

}
