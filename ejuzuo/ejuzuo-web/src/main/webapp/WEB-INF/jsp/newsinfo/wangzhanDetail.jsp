<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>新闻资讯详情</title>
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

<div class="content">
  <div class="main">
    <div class="pt20 mb20">

      <!-- 新闻资讯详情-->
      <div class="common_block">
        <div class="detail_wrap">
          <div class="top_box">
            <h1 class="title">${newsInfo.title}</h1>
            <p class="txt">
              <span class="mr20">发布人：${newsInfo.publisher}</span>
              <span class="mr20">发布时间：<fmt:formatDate value="${newsInfo.publishTime.time}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></span>
              <span class="mr20">浏览量：${newsInfo.viewCount}</span>
            </p>
          </div>
          <div class="clearfix">
            <div class="">
              <div class="cont_introbox">
                <div>
                  ${data.content}
                </div>
              </div>
              <div class="pl10 pr30 pb20">
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
            </div>
          </div>
        </div>
      </div><!-- 新闻资讯详情 END-->
    </div>
  </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript">
  var img = '<img src="/newsInfo/viewCount/${newsId}?q='+Math.random()*1000+' " style="display: none;">';
  var $Img = $(img);
  $('body').append($Img);
</script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
</body>
</html>