package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class PromoRecord implements Serializable{

    private static final long serialVersionUID = -1731513036850875306L;

    private Integer id;

    private Integer memberId;

    private String promoCode; // 推广代码

    private Integer status; // 状态[0:未处理，1:已处理]

    private Integer viewCount = 0; // 点击次数

    private String promoInfo; // 发起的json，包括ip, referer, user_agent

    private String callbackInfo; // 首次有效点击json, 包括ip, referer, user_agent

    private Calendar createTime;

    private Calendar updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode == null ? null : promoCode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getPromoInfo() {
        return promoInfo;
    }

    public void setPromoInfo(String promoInfo) {
        this.promoInfo = promoInfo == null ? null : promoInfo.trim();
    }

    public String getCallbackInfo() {
        return callbackInfo;
    }

    public void setCallbackInfo(String callbackInfo) {
        this.callbackInfo = callbackInfo == null ? null : callbackInfo.trim();
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
}