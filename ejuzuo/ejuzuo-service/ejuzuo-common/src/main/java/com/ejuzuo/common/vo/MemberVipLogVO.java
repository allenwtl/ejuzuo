package com.ejuzuo.common.vo;

import java.io.Serializable;

/**
 * Created by tianlun.wu on 2016/5/26 0026.
 */
public class MemberVipLogVO implements Serializable {

    private static final long serialVersionUID = -6967770667022534353L;

    //记录修改时间
    private String updateTime;

    private String remark ;

    //给用户添加的有效时间
    private String addExpireTime ;

    private String operAccount ;

    //原因
    private String reason ;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddExpireTime() {
        return addExpireTime;
    }

    public void setAddExpireTime(String addExpireTime) {
        this.addExpireTime = addExpireTime;
    }

    public String getOperAccount() {
        return operAccount;
    }

    public void setOperAccount(String operAccount) {
        this.operAccount = operAccount;
    }
}
