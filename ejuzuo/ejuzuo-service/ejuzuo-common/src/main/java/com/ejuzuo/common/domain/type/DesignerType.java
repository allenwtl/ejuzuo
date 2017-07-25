package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

import java.util.List;

/**
 * Created by allen on 2016/4/3.
 */
public class DesignerType extends BaseType {
	
	private static final long serialVersionUID = 6601789072816785761L;

	public DesignerType(Integer index, String description) {
        super(index, description);
    }
    public static final DesignerType personal = new DesignerType(3, "个人设计师");
    public static final DesignerType shejigongsi = new DesignerType(1, "设计公司");
    public static final DesignerType zhuangxiugongsi = new DesignerType(2, "装修公司");

    public static List<DesignerType> getAllList(){
        return getAll(DesignerType.class);
    }

    public static DesignerType findByIndex(Integer index){
        return valueOf(DesignerType.class, index);
    }
}
