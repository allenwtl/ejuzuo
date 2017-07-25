package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.DigitalFurniture;

import java.io.Serializable;

/**
 * Created by tianlun.wu on 2016/4/15 0015.
 */
public class ShoppingCartVO implements Serializable {

    private static final long serialVersionUID = 2100600743482578702L;

    private Integer id;

    private Integer memberId;

    private Integer goodsId ;

    private DigitalFurniture digitalFurniture ;

    private String brandName ;


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

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

    public DigitalFurniture getDigitalFurniture() {
        return digitalFurniture;
    }

    public void setDigitalFurniture(DigitalFurniture digitalFurniture) {
        this.digitalFurniture = digitalFurniture;
    }
}
