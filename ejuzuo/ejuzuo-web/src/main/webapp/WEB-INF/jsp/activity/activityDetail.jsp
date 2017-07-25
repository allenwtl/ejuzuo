<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>活动详情</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/swiper-3.3.1.min.css">
  <link rel="stylesheet" href="${resUrl}/styles/pages.css">
  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>
<%@include file="/common/menu.jsp"%>
<%@include file="/static/adspace/20160712173220597.html"%>
<div class="content">
  <div class="main">
    <div class="">

      <!-- 活动详情-->
      <div class="common_block">
        <p class="txt" style="padding-left: 20px;font-size: 16px;padding-top: 30px;line-height: 1;">
          <span class="mr20">发布人：${data.publisher}</span>
          <span class="mr20">发布时间：<fmt:formatDate value="${data.publishTime.time}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></span>
          <span class="mr20">浏览量：${data.viewCount}</span>
        </p>
        <div class="ac_intro_box">
          <div class="ac_rule">
            <h2 class="title">活动简介</h2>
            <p>${data.brief}</p>
            <h2 class="title">活动形式</h2>
            <p>线上报名 + 线上投票 + 线下颁奖</p>
            <h2 class="title">活动时间</h2>
            <p>${data.time}</p>
            <h2 class="title">活动地点（线下）</h2>
            <p>${data.cityVO}</p>
            ${contentInfo.content}
          </div>
          <div class="pl30 pr30 pt30">
            <div class="bshare-custom">
              <div class="bsPromo bsPromo2"></div>
              <a title="分享到微信" class="bshare-weixin" href="javascript:void(0);"></a>
              <a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
              <a title="分享到QQ空间" class="bshare-qzone" href="javascript:void(0);"></a>
              <a title="分享到QQ好友" class="bshare-qqim" href="javascript:void(0);"></a>
              <a title="分享到腾讯微博" class="bshare-qqmb"></a>
              <a title="分享到网易微博" class="bshare-neteasemb"></a>
              <a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a>
            </div>
          </div>
          <div class="btm_box">
            <a href="javascript:void(0);" class="bm_btn" id="signUp" data="${sign}">${sign ? "已经报名":"我要报名"}</a>"${data.title}"邀请函
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<input type="hidden" value="${data.id}" id="activityId"/>
<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/activity/activityDetail.js"></script>
<script type="text/javascript">
  var img = '<img src="/activity/viewCount/${data.id}?q='+Math.random()*1000+' " style="display: none;">';
  var $Img = $(img);
  $('body').append($Img);
</script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
</body>
</html>