package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * 充值方式
 * Created by tianlun.wu on 2016/5/10 0010.
 */
public class ChargeWay extends BaseType{


    protected ChargeWay(Integer index, String description) {
        super(index, description);
    }


    public static ChargeWay zfb_sdk = new ChargeWay(1, "支付宝SDK");
    public static ChargeWay zfb_wap = new ChargeWay(2, "支付宝WAP");
    public static ChargeWay zfb_web = new ChargeWay(3, "支付宝WEB");

    public static List<ChargeWay> getAllList() {
        return getAll(ChargeWay.class);
    }
    public static  ChargeWay valueOf(Integer index){
        return valueOf(ChargeWay.class, index);
    }

}
