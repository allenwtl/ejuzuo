package com.ejuzuo.common.util;

/**
 * Created by tianlun.wu on 2016/5/17 0017.
 */
public class MemcachedKeyUtil {


    //导航栏-全部类型集合
    public static final String ALL_GOODS_TYPE_LIST = "allGoodsTypeList";

    //品牌集合
    public static final String ALL_BRAND_LIST = "allBrandList";

    //前端首页新闻资讯
    public static final String FRONT_INDEX_NEWS_INFO = "frontIndexNewsInfo";

    //前端首页最新活动
    public static final String FRONT_IDNEX_ACTIVITY_LATEST = "frontIndexActivityLatest";

    //前端首页数字家居
    //当后台管理员 对合作品牌进行 优选操作  和 数字家居进行 增加和删除 操作时删除这个缓存
    public static final String FRONT_INDEX_DF = "frontIndexDF";

    //数字家具详情页面-其它品牌推荐
    public static final String DIGITAL_DETAIL_OTHER_BRAND = "digitalDetailOtherBrand";

    //数字家具详情页面-同一品牌推荐
    public static final String DIGITAL_DETAIL_SAME_BRAND = "digitalDetailSameBrand";

}
