package com.ejuzuo.common.vo;

import com.ejuzuo.common.domain.DesignerWork;

/**
 * Created by tianlun.wu on 2016/4/21 0021.
 */
public class DesignerWorkVO extends DesignerWork {
	
	private static final long serialVersionUID = 5410493853696938198L;

	//风格中文
    private String styleVo ;

    //设计师名称
    private String designerName ;

    //住址
    private String address ;

    //评论数量
    private Integer commentCount;

    //用户图片地址
    private String designerImg;

    //收藏
    private Integer like ;

    //作品数量
    private Integer workCount ;

    //粉丝
    private Integer followerCount ;

    //个人浏览量
    private Integer personViewCount;

    private String designerType ;

    private boolean favorite = false ;

    public String getDesignerType() {
        return designerType;
    }

    public void setDesignerType(String designerType) {
        this.designerType = designerType;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Integer getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Integer workCount) {
        this.workCount = workCount;
    }

    public String getDesignerImg() {
        return designerImg;
    }

    public void setDesignerImg(String designerImg) {
        this.designerImg = designerImg;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getStyleVo() {
        return styleVo;
    }

    public void setStyleVo(String styleVo) {
        this.styleVo = styleVo;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPersonViewCount() {
        return personViewCount;
    }

    public void setPersonViewCount(Integer personViewCount) {
        this.personViewCount = personViewCount;
    }

//	public Integer getDesignerId() {
//		return designerId;
//	}
//
//	public void setDesignerId(Integer designerId) {
//		this.designerId = designerId;
//	}
}
