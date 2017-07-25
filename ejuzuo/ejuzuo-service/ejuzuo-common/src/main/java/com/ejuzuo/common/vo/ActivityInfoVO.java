package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.ActivityInfo;

/**
 * Created by tianlun.wu on 2016/4/26 0026.
 */
public class ActivityInfoVO extends ActivityInfo {

    private String cityVO ;

    private String time ;

    public String getCityVO() {
        return cityVO;
    }

    public void setCityVO(String cityVO) {
        this.cityVO = cityVO;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
