package com.ejuzuo.common.domain;

import com.ejuzuo.common.domain.type.DigitalShelfStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class DigitalFurniture implements Serializable {

    private static final long serialVersionUID = 2002030303588178528L;
    
    private Integer id;

    private String name; // 产品名称
    
    private Integer type; // 1家居尾品，2定制家居，3进口管，4品牌家居
    
    private Integer digitalType; // 1家具、2灯具、3饰品、4挂画、5地毯窗帘
    
    private String thumbnail; // 缩略图路径

    private String spaceCategory; // 空间大类,对应代码集合10

    private String spaceSmallCategory; // 空间小类，对应代码集合20

    private String style; // 风格，对应代码集合11

    private Integer brand; // 品牌

    private BigDecimal refPrice; // 参考价格

    private String pictures; // 图示JSON: 格式为[{img:xx, intro:xx}, {img:xx, intro:xx}]

    private String specification; // 规格JSON: key value表格

    private String corporation; // 公司JSON: key为企业，介绍，联系方式，公司网站，公司地址

    private Integer fileId; // 附件ID

    private Integer pointPrice; // 积分价格

    /** @see DigitalShelfStatus */
    private Integer shelfStatus; // 上架状态[0:未上架，1:已上架，2:已下架]
    
    private Calendar shelfTime; //上架时间

    private Integer status; // 状态[0:无效，1:有效]

    private Integer viewCount; // 浏览量

    private Integer downCount; // 下载次数

    private String creator; // 创建者, 管理员账号。

    private Calendar createTime = Calendar.getInstance();

    private Calendar updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpaceCategory() {
        return spaceCategory;
    }

    public void setSpaceCategory(String spaceCategory) {
        this.spaceCategory = spaceCategory == null ? null : spaceCategory.trim();
    }

    public String getSpaceSmallCategory() {
        return spaceSmallCategory;
    }

    public void setSpaceSmallCategory(String spaceSmallCategory) {
        this.spaceSmallCategory = spaceSmallCategory == null ? null : spaceSmallCategory.trim();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public BigDecimal getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(BigDecimal refPrice) {
        this.refPrice = refPrice;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation == null ? null : corporation.trim();
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(Integer pointPrice) {
        this.pointPrice = pointPrice;
    }

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
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

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Calendar getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(Calendar shelfTime) {
		this.shelfTime = shelfTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDigitalType() {
		return digitalType;
	}

	public void setDigitalType(Integer digitalType) {
		this.digitalType = digitalType;
	}
}