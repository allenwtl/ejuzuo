package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by tianlun.wu on 2017/1/7.
 */
public class DigitalType extends BaseType {
    private static final long serialVersionUID = -3286555376561949088L;

    public DigitalType(Integer index, String description) {
        super(index, description);
    }

    public static final DigitalType  ALL = new DigitalType(0, "全部");

    public static final DigitalType  QUALITY_GOODS = new DigitalType(4, "品牌馆");

    public static final DigitalType   END_PRODUCT = new DigitalType(1, "家居尾品汇");

    public static final DigitalType SPECIAL_DO  = new DigitalType(2, "定制馆");

    public static final DigitalType IMPORT_PAVILION  = new DigitalType(3, "进口馆");

    public static DigitalType fromIndex(Integer index){
        return valueOf(DigitalType.class, index);
    }


    public static List<DigitalType> getAll(){
        return getAll(DigitalType.class);
    }

}
