package com.ejuzuo.common.constants;

import java.util.List;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
@SuppressWarnings("serial")
public class MemberPointLogTransType extends BaseType {
    private Integer point ;

    private MemberPointLogTransType(Integer index, String description) {
        super(index, description);
    }

    public MemberPointLogTransType(Integer index, String description, Integer point) {
        super(index, description);
        this.point = point;
    }

    public static final MemberPointLogTransType LOGIN           = new MemberPointLogTransType(0, "登录", 5); //暂时屏蔽不送积分
    public static final MemberPointLogTransType DOWNLOAD        = new MemberPointLogTransType(1, "下载");
    public static final MemberPointLogTransType SHARE           = new MemberPointLogTransType(2, "分享", 50);
    public static final MemberPointLogTransType APPLY_DESIGNER  = new MemberPointLogTransType(3, "认证设计师", 50);
    public static final MemberPointLogTransType DONATE_ONE      = new MemberPointLogTransType(4, "赞助49元", 400);
    public static final MemberPointLogTransType DONATE_TWO      = new MemberPointLogTransType(5, "赞助99元", 1000);
    public static final MemberPointLogTransType UPLOAD_WORK     = new MemberPointLogTransType(6, "上传成功案例", 10);//普通会员没，高级会员5分，VIP会员10分。
    public static final MemberPointLogTransType REGISTER         = new MemberPointLogTransType(7, "注册", 50);
    public static final MemberPointLogTransType MOBILE_EMAIL    = new MemberPointLogTransType(8, "完善邮箱和手机号码", 10); //暂时屏蔽，不送积分
    public static final MemberPointLogTransType DEAL_ERROR    = new MemberPointLogTransType(9, "异常调整", null);
    
    public static List<MemberPointLogTransType> getAllList() {
        return getAll(MemberPointLogTransType.class);
    }

    public static MemberPointLogTransType findByIndex(Integer index) {
        return valueOf(MemberPointLogTransType.class, index);
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
