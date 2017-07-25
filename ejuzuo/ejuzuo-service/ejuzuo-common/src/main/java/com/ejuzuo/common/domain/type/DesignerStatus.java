package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by allen on 2016/4/3.
 */
public class DesignerStatus extends BaseType {
    public DesignerStatus(Integer index, String description) {
        super(index, description);
    }

    public static final DesignerStatus zancun = new DesignerStatus(0, "暂存");
    public static final DesignerStatus daishenhe = new DesignerStatus(1, "待审核");
    public static final DesignerStatus shenhetongguo = new DesignerStatus(2, "审核通过");
    public static final DesignerStatus shenhetuihui = new DesignerStatus(3, "审核退回");

    public static DesignerStatus findByIndex(Integer index){
        return valueOf(DesignerStatus.class, index);
    }

}
