package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
@SuppressWarnings("serial")
public class ObjectType extends BaseType {

    private ObjectType(Integer index, String description) {
        super(index, description);
    }

    public static final ObjectType shuzijiaju = new ObjectType(0,"商品");

    public static final ObjectType shejizuopin = new ObjectType(1, "设计作品");

    public static final ObjectType huodong = new ObjectType(2, "活动");

    public static final ObjectType zixun = new ObjectType(3, "资讯");

    public static final ObjectType shejishi = new ObjectType(4, "设计师");

    public static final ObjectType charge = new ObjectType(5, "充值订单");
    
    public static final ObjectType share = new ObjectType(6, "分享记录");

    public static ObjectType findByIndex(Integer index){
        return valueOf(ObjectType.class, index);
    }


}
