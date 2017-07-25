package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/3/31 0031.
 */
public class ImageBusinessType extends BaseType {
	private static final long serialVersionUID = -1046527487632569004L;

	public ImageBusinessType(Integer index, String description) {
        super(index, description);
    }

    //用户图像
    public static final ImageBusinessType memberImg = new ImageBusinessType(1, "member");
    //设计师或者设计公司封面图片
    public static final ImageBusinessType designerCoverImg = new ImageBusinessType(6, "designer");
    //设计师或者公司的作品封面图片
    public static final ImageBusinessType designerWorkCoverImg = new ImageBusinessType(7 , "designerwork");
    //活动
    public static final ImageBusinessType activity = new ImageBusinessType(2, "activity");
    //咨询
    public static final ImageBusinessType news = new ImageBusinessType(3, "news");
    //工程
    public static final ImageBusinessType project = new ImageBusinessType(4, "project");
    //数字家居
    public static final ImageBusinessType digitalFurniture = new ImageBusinessType(5, "digitalfurniture");

    //其它的
    public static final ImageBusinessType others = new ImageBusinessType(0 , "others");

    public static ImageBusinessType getByIndex(Integer index){
        return valueOf(ImageBusinessType.class, index);
    }
}
