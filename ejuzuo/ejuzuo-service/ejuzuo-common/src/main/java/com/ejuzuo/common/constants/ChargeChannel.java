package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * 充值渠道
 * Created by tianlun.wu on 2016/5/9 0009.
 */
public class ChargeChannel extends BaseType {

    private static final long serialVersionUID = -5564682573645073930L;

    protected ChargeChannel(Integer index, String description) {
        super(index, description);
    }

    public static ChargeChannel zfb = new ChargeChannel(1, "支付宝");
    public static ChargeChannel wx = new ChargeChannel(2, "微信");
    public static List<ChargeChannel> getAllList() {
        return getAll(ChargeChannel.class);
    }
    public static  ChargeChannel valueOf(Integer index){
        return valueOf(ChargeChannel.class, index);
    }
}
