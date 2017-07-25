package com.ejuzuo.common.vo;

import java.io.Serializable;

/**
 * Created by tianlun.wu on 2016/4/24 0024.
 */
public class CommentVO implements Serializable{

    private static final long serialVersionUID = -3897596043540797685L;

    private Integer id ;

    private Integer memberId;

    private String profileImg;

    private boolean designer;

    private String nickName;

    private String content ;

    private String dateBefore ;

    private Integer masked;

    public Integer getMasked() {
        return masked;
    }

    public void setMasked(Integer masked) {
        this.masked = masked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public boolean isDesigner() {
        return designer;
    }

    public void setDesigner(boolean designer) {
        this.designer = designer;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(String dateBefore) {
        this.dateBefore = dateBefore;
    }
}
