package com.ejuzuo.web.common;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/29 0029.
 */
public class ActivityTime extends BaseType{

    protected ActivityTime(Integer index, String description) {
        super(index, description);
    }


    public static ActivityTime today = new ActivityTime(1, "今天");

    public static ActivityTime oneWeek = new ActivityTime(2, "一周");

    public static ActivityTime twoWeek = new ActivityTime(3, "二周");

    public static ActivityTime oneMonth = new ActivityTime(4, "一月");

    public static List<ActivityTime> getAllList(){
        return getAll(ActivityTime.class);
    }

    public static ActivityTime findByIndex(Integer index){
        return valueOf(ActivityTime.class, index);
    }

}
