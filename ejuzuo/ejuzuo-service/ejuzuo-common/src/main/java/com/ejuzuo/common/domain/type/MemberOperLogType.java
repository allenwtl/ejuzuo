package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/4/12 0012.
 */
public class MemberOperLogType extends BaseType {

    private MemberOperLogType(Integer index, String description) {
        super(index, description);
    }

    public static final MemberOperLogType LOGIN           = new MemberOperLogType(0, "登录");
    public static final MemberOperLogType DOWNLOAD        = new MemberOperLogType(1, "下载");
    public static final MemberOperLogType SHARE           = new MemberOperLogType(2, "分享");
    public static final MemberOperLogType APPLY_DESIGNER  = new MemberOperLogType(3, "申请认证");
    public static final MemberOperLogType DONATE_ONE      = new MemberOperLogType(4, "赞助49元");
    public static final MemberOperLogType DONATE_TWO      = new MemberOperLogType(5, "赞助99元");
    public static final MemberOperLogType UPLOAD_WORK     = new MemberOperLogType(6, "设计师上传作品");
    public static final MemberOperLogType REGISTE         = new MemberOperLogType(7, "注册");
    public static final MemberOperLogType MOBILE_EMAIL    = new MemberOperLogType(8, "完善邮箱和手机号码");
    public static final MemberOperLogType UPDATE_PASSWORD = new MemberOperLogType(9, "修改密码");
    public static final MemberOperLogType FIND_PASSWORD   = new MemberOperLogType(10, "找回密码");
    public static final MemberOperLogType CHARGE          = new MemberOperLogType(11, "充值");
}
