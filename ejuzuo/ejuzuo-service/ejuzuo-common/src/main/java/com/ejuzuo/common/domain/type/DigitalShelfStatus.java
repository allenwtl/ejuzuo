package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by allen on 2016/4/23.
 */
public class DigitalShelfStatus extends BaseType {
    private DigitalShelfStatus(Integer index, String description) {
        super(index, description);
    }


    public static final DigitalShelfStatus NO_SALES = new DigitalShelfStatus(0, "未上架");

    public static final DigitalShelfStatus SALES = new DigitalShelfStatus(1, "上架");

    public static final DigitalShelfStatus DOWN_SALES = new DigitalShelfStatus(2, "下架");

}
