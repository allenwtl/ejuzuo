package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class Brand implements Serializable {
    private static final long serialVersionUID = -4402683477360167452L;
    
    private Integer id;

    private String name; // 品牌名称

    private String logoImg; // logo图片

    private String remark;

    /**
     * @see com.ejuzuo.common.constants.Status
      */
    private Integer status; // 状态[0无效，1有效]
    
    private Integer preferred; // 优先品牌[0否，1是]
    
    private String corporation; // 公司JSON: key为企业，介绍，联系方式，公司网站，公司地址

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

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg == null ? null : logoImg.trim();
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
    public Integer getPreferred() {
		return preferred;
	}

	public void setPreferred(Integer preferred) {
		this.preferred = preferred;
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
    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation == null ? null : corporation.trim();
    }
}