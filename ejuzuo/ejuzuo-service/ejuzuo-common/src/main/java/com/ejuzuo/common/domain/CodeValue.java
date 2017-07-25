package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by tianlun.wu on 2016/4/5 0005.
 */
public class CodeValue implements Serializable {

    private static final long serialVersionUID = -8966939511235818781L;
    
    private String collectionCode; // 集合代码[10:空间, 20:空间小类，11:风格，12:热门城市，13:职业身份，14:公司规模，15活动类别]

    private String valueCode ;

    private String valueName ;

    private String parentCode ;

    private String extension;

    /**
     * @see com.ejuzuo.common.constants.Status
     */
    private Integer status ;

    private Integer orderNo ;

    private Calendar createTime = Calendar.getInstance();

    private Calendar updateTime ;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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
