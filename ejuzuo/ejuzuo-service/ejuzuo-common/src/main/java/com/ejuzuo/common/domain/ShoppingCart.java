package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class ShoppingCart implements Serializable{
    private static final long serialVersionUID = -6490742406610874621L;
    
    private Integer id;

    private Integer memberId;

    private Integer goodsId;

    private Calendar createTime ;

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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
}