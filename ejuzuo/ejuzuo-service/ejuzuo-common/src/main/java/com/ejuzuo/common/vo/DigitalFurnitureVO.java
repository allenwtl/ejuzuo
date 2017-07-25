package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.DigitalFurniture;


public class DigitalFurnitureVO extends DigitalFurniture {
    private static final long serialVersionUID = -1195055781388884540L;

    private String styleVo;

    private String sizeMB ;

    private String brandName;

    private int favoriteCount;

    private boolean favorite = false;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getSizeMB() {
        return sizeMB;
    }

    public void setSizeMB(String sizeMB) {
        this.sizeMB = sizeMB;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getStyleVo() {
        return styleVo;
    }

    public void setStyleVo(String styleVo) {
        this.styleVo = styleVo;
    }
}
