<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>个人中心</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp" %>

<div class="user_content">
  <div class="main">
    <div class="clearfix">
      <%@include file="/common/memberCenterLeft.jsp" %>
      <div class="right_box" id="partRight">
        <div class="sections">
          <div class="title_box">
            <span class="txt">个人中心</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box">
              <div class="menu_link_wrap" style="padding-top: 0px;">
                <ul class="menu_link_block clearfix">
                  <li><a href="/member/showInfo" class="bdt"><i class="menu_icon zhsz_icon"></i>用户信息</a></li>
                  <li><a href="/designer/setting" class="bdt bdl"><i class="menu_icon zhsz_icon"></i>我是设计师</a></li>
                  <li><a href="/designerWork/index" class="bdt bdl"><i class="menu_icon zpgl_icon"></i>作品管理</a></li>
                  <li><a href="/donate/index" class="bdt"><i class="menu_icon zzjz_icon"></i>赞助巨作</a></li>
                  <li><a href="/security/index/mobile" class="bdt bdl"><i class="menu_icon znzx_icon"></i>安全中心</a></li>
                  <li><a href="/buyCar/index" class="bdt bdl"><i class="menu_icon znzx_icon"></i>购物车</a></li>
                  <li><a href="/downloadInfo/index" class="bdt"><i class="menu_icon wdxz_icon"></i>我的下载</a></li>
                  <li><a href="/favorite/list" class="bdt bdl"><i class="menu_icon wdsc_icon"></i>我的收藏</a></li>
                  <li><a href="/share/index" class="bdt bdl"><i class="menu_icon fx_icon"></i>分享</a></li>
                  <li><a href="/pointLog/index" class="bdt bdb"><i class="menu_icon vipjl_icon"></i>积分记录</a></li>
                  <li><a href="/info/index" class="bdt bdb bdl"><i class="menu_icon znzx_icon"></i>系统消息</a></li>
                  <li><a href="/privilege/index" class="bdt bdb bdl"><i class="menu_icon yhtq_icon"></i>用户特权</a></li>
                  <li><a href="/memberVipLog/index" class="bdt"><i class="menu_icon zzjz_icon"></i>vip记录</a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

</body>
</html>