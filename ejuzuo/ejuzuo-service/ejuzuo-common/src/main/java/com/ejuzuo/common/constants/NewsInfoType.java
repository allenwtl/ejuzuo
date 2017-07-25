package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/5/3 0003.
 */
public class NewsInfoType extends BaseType {
    protected NewsInfoType(Integer index, String description) {
        super(index, description);
    }

    public static NewsInfoType latest = new NewsInfoType(1, "最新的");

    public static NewsInfoType hotest = new NewsInfoType(2, "热门的");

    public static NewsInfoType findByIndex(int index){
        return valueOf(NewsInfoType.class, index);
    }

}
