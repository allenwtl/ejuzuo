package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

public class PageCodeType extends BaseType {

    private static final long serialVersionUID = 1L;
	public static final PageCodeType MARQUEE = new PageCodeType(0,"跑马灯广告");
	public static final PageCodeType INDEX_RIGHT = new PageCodeType(1,"首页右边推广图");
	public static final PageCodeType INDEX_1 = new PageCodeType(2,"首页广告位1");
	public static final PageCodeType INDEX_2 = new PageCodeType(3,"首页广告位2");
	public static final PageCodeType INDEX_3 = new PageCodeType(4,"首页广告位3");

	private PageCodeType(Integer index, String description) {
        super(index, description);
    }
    public static PageCodeType findByIndex(Integer index){
        return valueOf(PageCodeType.class, index);
    }
}
