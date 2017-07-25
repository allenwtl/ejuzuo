package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.DigitalFurniture;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by tianlun.wu on 2016/4/21 0021.
 */
public class DownloadInfoVO implements Serializable {

    private static final long serialVersionUID = 8347198550366968977L;

    private Integer id;

    private Integer memberId;

    private Integer goodsId ;

    private DigitalFurniture digitalFurniture ;

    /**
     * 对应 表 file_info 中的id
     */
    private Integer fileId;

    private BigDecimal pointPrice;

    private String expireTime;

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public BigDecimal getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(BigDecimal pointPrice) {
        this.pointPrice = pointPrice;
    }
}
