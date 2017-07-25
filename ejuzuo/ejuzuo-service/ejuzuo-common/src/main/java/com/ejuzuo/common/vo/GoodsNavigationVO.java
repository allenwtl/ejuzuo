package com.ejuzuo.common.vo;

import com.alibaba.fastjson.JSONArray;
import com.ejuzuo.common.domain.CodeValue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/16 0016.
 */
public class GoodsNavigationVO implements Serializable {

    private static final long serialVersionUID = -9069698612143889046L;

    private String bz ;

    private String valueCode ;

    private String valueName ;

    private List<CodeValue> subList ;

    private Integer totalCount ;

    private String extension ;

    public JSONArray getExtension() {
        return JSONArray.parseArray(extension);
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public List<CodeValue> getSubList() {
        return subList;
    }

    public void setSubList(List<CodeValue> subList) {
        this.subList = subList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
