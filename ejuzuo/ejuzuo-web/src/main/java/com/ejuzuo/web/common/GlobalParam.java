package com.ejuzuo.web.common;


/**
 * Created by tianlun.wu on 2016/3/29 0029.
 */

public class GlobalParam {

    private String prefix ;
    private String suffix ;
    private String fileRootPublic;
    private String fileRootPrivate;
    //用户登录 缓存有效时间 单位是秒
    private Integer memberLoginExpireTime;
    //用户登录 cookie有效时间  单位是秒
    private Integer memberLoginCookieTime;
    //cookie中 用户登录对应的 key 名称
    private String uuidCookie="uuid";
    //cookie中 验证码对应的 key 名称
    public static final String validateCode = "yzm";
    //cookie中 成功登录之后调到的页面 key 名称
    public static final String uri="uri";
    //cache中 防止重复提交 key 名称
    public static final String avoidSubmit="uuid-cftj";
    //cookie中 购物车唯一标识的key
    public static final String buyCar="buy-car-uuid";
    //下载excel存放的路径
    private String downLoadExcel ="";

    public String getFileRootPublic() {
        return fileRootPublic;
    }

    public void setFileRootPublic(String fileRootPublic) {
        this.fileRootPublic = fileRootPublic;
    }

    public String getFileRootPrivate() {
        return fileRootPrivate;
    }

    public void setFileRootPrivate(String fileRootPrivate) {
        this.fileRootPrivate = fileRootPrivate;
    }

    public String getUuidCookie() {
        return uuidCookie;
    }

    public void setUuidCookie(String uuidCookie) {
        this.uuidCookie = uuidCookie;
    }

    public Integer getMemberLoginCookieTime() {
        return memberLoginCookieTime;
    }

    public void setMemberLoginCookieTime(Integer memberLoginCookieTime) {
        this.memberLoginCookieTime = memberLoginCookieTime;
    }

    public Integer getMemberLoginExpireTime() {
        return memberLoginExpireTime;
    }

    public void setMemberLoginExpireTime(Integer memberLoginExpireTime) {
        this.memberLoginExpireTime = memberLoginExpireTime;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getDownLoadExcel() {
        return downLoadExcel;
    }

    public void setDownLoadExcel(String downLoadExcel) {
        this.downLoadExcel = downLoadExcel;
    }
}
