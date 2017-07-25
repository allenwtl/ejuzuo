package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/5/24 0024.
 */
public class ThirdType extends BaseType {
    protected ThirdType(Integer index, String description) {
        super(index, description);
    }

    public static final ThirdType  QQ = new ThirdType(0, "qq");
    public static final ThirdType  WEICAT =  new ThirdType(1, "微信");

    public static ThirdType findByIndex(Integer index){
        return valueOf(ThirdType.class, index);
    }

}
