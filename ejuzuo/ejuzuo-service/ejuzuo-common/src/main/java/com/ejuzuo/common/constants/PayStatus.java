package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/12 0012.
 */
public class PayStatus extends BaseType {

    public PayStatus(Integer index, String description) {
        super(index, description);
    }


    public static final PayStatus UN_PAY =  new PayStatus(0, "未支付");

    public static final PayStatus PAY_SUCCESS =  new PayStatus(1, "支付成功");

    public static final PayStatus PAY_FAIL = new PayStatus(2, "支付失败");

    public static PayStatus findByIndex(Integer index){
        return valueOf(PayStatus.class, index);
    }

    public static List<PayStatus> getAllList(){
        return getAll(PayStatus.class);
    }

}
