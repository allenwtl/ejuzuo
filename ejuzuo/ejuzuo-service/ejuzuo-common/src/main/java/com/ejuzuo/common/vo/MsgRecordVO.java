package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.MsgRecord;
import com.ejuzuo.common.util.DateUtil;

/**
 * Created by tianlun.wu on 2016/5/7 0007.
 */
public class MsgRecordVO extends MsgRecord {

    private String timeVO ;

    public String getTimeVO() {
        return DateUtil.dateToString(getCreatedDate().getTime(), "yyyy-MM-dd HH:mm");
    }

    public void setTimeVO(String timeVO) {
        this.timeVO = timeVO;
    }
}
