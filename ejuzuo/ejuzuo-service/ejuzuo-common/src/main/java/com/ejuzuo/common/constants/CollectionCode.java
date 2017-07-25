package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by allen on 2016/4/3.
 */
public class CollectionCode extends BaseType {
    public CollectionCode(Integer index, String description) {
        super(index, description);
    }


    public static final CollectionCode kongjian = new CollectionCode(10, "空间");
    public static final CollectionCode kongjianxiaolei = new CollectionCode(20, "空间小类");
    public static final CollectionCode fengge = new CollectionCode(11, "风格");
    public static final CollectionCode remenchengshi = new CollectionCode(12, "热门城市");
    public static final CollectionCode zhiyeshenfen = new CollectionCode(13, "职业身份");
    public static final CollectionCode gongsiguimo = new CollectionCode(14, "公司规模");
    public static final CollectionCode huodongleibei = new CollectionCode(15, "活动类别");

    public List<CollectionCode> getAllList() {
        return getAll(CollectionCode.class);
    }

    public CollectionCode findByIndex(Integer index) {
        return valueOf(CollectionCode.class, index);
    }

}
