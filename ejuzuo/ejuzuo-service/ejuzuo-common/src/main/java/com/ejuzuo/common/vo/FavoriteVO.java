package com.ejuzuo.common.vo;

import java.io.Serializable;

/**
 * Created by tianlun.wu on 2016/4/27 0027.
 */
public class FavoriteVO implements Serializable{

    private static final long serialVersionUID = 4973328709958248902L;

    //收藏品id
    private Integer id;

    private String coverImg ;

    //链接到具体的详情页面的url
    private String url;

    /**
     * 收藏品的类型
     * @see com.ejuzuo.common.domain.type.ObjectType
     */

    private Integer type ;

    private String designerType ;

    private String designerName ;

    public String getDesignerType() {
        return designerType;
    }

    public void setDesignerType(String designerType) {
        this.designerType = designerType;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
