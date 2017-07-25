package com.ejuzuo.common.domain;

import com.ejuzuo.common.domain.type.DesignerStatus;

import java.io.Serializable;
import java.util.Calendar;

public class Designer implements Serializable {

    private static final long serialVersionUID = 8978570778458973165L;

    private Integer id;

    private Integer memberId; // 关联用户ID

    private String name; // 真实姓名/公司名称

    private String coverImg; // 封面图片

    /**
     * @see com.ejuzuo.common.domain.type.DesignerType
     */
    private Integer designerType; // 设计性质[1:设计公司，2:装修公司,3:个人设计师]

    private String adeptStyles; // 擅长风格，对应代码集合11，多个逗号分隔。

    private Integer provice; // 省

    private Integer city; // 市

    private String address; // 详细地址

    private String qq; // qq号

    private String contact; // 联系人

    private String contactMobile; // 联系人手机

    private String regNo; // 公司注册号

    private String career; // 职业,对应代码集合13

    private String corporation; // 公司法人

    private Integer companySize; // 公司规模，对应代码集合14

    private Integer regFund; // 注册资金

    private String licenseImg; // 营业执照图片路径

    private String brief; // 个人或公司简介

    private String classWorks; // 代表作品

    private String designIdea; // 设计理念

    private String experience; // 项目经验

    private String styleIntro; // 风格介绍

    private String remark; // 备注
    
    private Integer workId; // 作业作品ID
    
    /**
     * @see DesignerStatus
     */
    private Integer status; // 状态[0暂存，1待审核，2审核通过，3审核退回]

    private String reason; // 退回原因

    private Integer viewCount ;

    private String verifior; // 审核人
    
    private Calendar verifyTime; // 审核时间
    
    private Integer homed; // 推荐到首页[0否，1是]

    private Calendar createTime; // 创建时间

    private Calendar updateTime; // 修改时间

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }

    public Integer getDesignerType() {
        return designerType;
    }

    public void setDesignerType(Integer designerType) {
        this.designerType = designerType;
    }

    public String getAdeptStyles() {
        return adeptStyles;
    }

    public void setAdeptStyles(String adeptStyles) {
        this.adeptStyles = adeptStyles == null ? null : adeptStyles.trim();
    }

    public Integer getProvice() {
        return provice;
    }

    public void setProvice(Integer provice) {
        this.provice = provice;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo == null ? null : regNo.trim();
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation == null ? null : corporation.trim();
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public Integer getRegFund() {
        return regFund;
    }

    public void setRegFund(Integer regFund) {
        this.regFund = regFund;
    }

    public String getLicenseImg() {
        return licenseImg;
    }

    public void setLicenseImg(String licenseImg) {
        this.licenseImg = licenseImg == null ? null : licenseImg.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getClassWorks() {
        return classWorks;
    }

    public void setClassWorks(String classWorks) {
        this.classWorks = classWorks == null ? null : classWorks.trim();
    }

    public String getDesignIdea() {
        return designIdea;
    }

    public void setDesignIdea(String designIdea) {
        this.designIdea = designIdea == null ? null : designIdea.trim();
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience == null ? null : experience.trim();
    }

    public String getStyleIntro() {
        return styleIntro;
    }

    public void setStyleIntro(String styleIntro) {
        this.styleIntro = styleIntro == null ? null : styleIntro.trim();
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Calendar getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Calendar verifyTime) {
        this.verifyTime = verifyTime;
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

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getVerifior() {
		return verifior;
	}

	public void setVerifior(String verifior) {
		this.verifior = verifior;
	}

	public Integer getHomed() {
		return homed;
	}

	public void setHomed(Integer homed) {
		this.homed = homed;
	}
}