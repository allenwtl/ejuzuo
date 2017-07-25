package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class DownloadInfo implements Serializable{
    private static final long serialVersionUID = -2315940682239205438L;
    private Integer id;

    private Integer memberId;

    /**
     * 商品ID,即数字家居ID
     */
    private Integer goodsId;
    
    /**
     * 对应 表 file_info 中的id
     */
    private Integer fileId;

    /**
     * 购买时积分价格
     */
    private BigDecimal pointPrice;

    /**
     * 购买时实际支付价格
     */
    private BigDecimal actualPrice;

    private String remark;

    /**
     * 是否有效
     * @see com.ejuzuo.common.constants.Status
     */
    private Integer status;

    /**
     * 支付状态
     * @see com.ejuzuo.common.domain.type.DownloadPayStatus
     */
    private Integer payStatus;

    private Calendar payTime;

    private Calendar expire;

    private Calendar createTime;

    private Calendar updateTime;
    
    /**冗余，展示用*/
    private String nickName; // 用户昵称
    private String account; // 用户账号
    private String goodsName; // 商品名称
    private String fileName; // 文件名称

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
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

    public BigDecimal getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(BigDecimal pointPrice) {
        this.pointPrice = pointPrice;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Calendar getPayTime() {
        return payTime;
    }

    public void setPayTime(Calendar payTime) {
        this.payTime = payTime;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getExpire() {
        return expire;
    }

    public void setExpire(Calendar expire) {
        this.expire = expire;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}