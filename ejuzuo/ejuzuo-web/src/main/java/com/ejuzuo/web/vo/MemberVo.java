package com.ejuzuo.web.vo;

import com.ejuzuo.common.domain.Member;

import java.math.BigDecimal;

/**
 * Created by tianlun.wu on 2016/4/14 0014.
 */
public class MemberVo extends Member{

    //true 为是 false 为不是
    private boolean vip ;

    private boolean designer;

    private Integer designerId ;

    private BigDecimal balance;

    private boolean third ;


    public Integer getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Integer designerId) {
        this.designerId = designerId;
    }

    public boolean isThird() {
        return third;
    }

    public void setThird(boolean third) {
        this.third = third;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isDesigner() {
        return designer;
    }

    public void setDesigner(boolean designer) {
        this.designer = designer;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
