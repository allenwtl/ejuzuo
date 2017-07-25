package com.ejuzuo.common.vo;

import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberPointLog;

/**
 * Created by tianlun.wu on 2016/4/26 0026.
 */
public class MemberPointLogVO extends MemberPointLog {

    private String showAmount;

    private String showTime ;

    private String projectName;

    public String getProjectName() {
        return MemberPointLogTransType.findByIndex(getTransType()).getDescription();
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getShowAmount() {
        return showAmount;
    }

    public void setShowAmount(String showAmount) {
        this.showAmount = showAmount;
    }
}
