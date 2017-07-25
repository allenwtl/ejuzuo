package com.ejuzuo.common.vo;

import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.Designer;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by allen on 2016/4/26.
 */
public class DesignerVO extends Designer {

    //粉丝
    private Integer follower ;

    //作品数量
    private Integer workCount ;

    //组合地址
    private String area ;

    //关注
    private boolean follow ;

    private String styleVo ;

    private String pic280280;

    private String pic4343 ;

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Integer workCount) {
        this.workCount = workCount;
    }

    public String getPic280280(){
        if(StringUtils.isNotBlank(getCoverImg())){
            return JSONObject.parseObject(getCoverImg()).getString("pic280280");
        }
        return "";
    }

    public String getPic4343(){
        if(StringUtils.isNotBlank(getCoverImg())){
            return JSONObject.parseObject(getCoverImg()).getString("pic4343");
        }
        return "";

    }

    public String getStyleVo() {
        return styleVo;
    }

    public void setStyleVo(String styleVo) {
        this.styleVo = styleVo;
    }
}
