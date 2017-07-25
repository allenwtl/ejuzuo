package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by allen on 2016/4/2.
 */
public class DownloadPayStatus extends BaseType {
    public DownloadPayStatus(Integer index, String description) {
        super(index, description);
    }

    public static final DownloadPayStatus UN_PAY = new DownloadPayStatus(0, "未支付");

    public static final DownloadPayStatus PAYED = new DownloadPayStatus(1, "已支付");

}
