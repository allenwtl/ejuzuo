<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>新闻资讯</title>
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
<%@include file="/static/adspace/20160712171741750.html"%>
<div class="content">
  <div class="main">
    <div class="pt20">

      <!-- 最新活动-->
      <div class="common_block">
        <div class="news_dttitle">
          <ul class="news_type_btn_list clearfix">
            <li><a href="/newsInfo/list/0" class="news_type_btn ${type== 0 ? 'on':''}" data="0">全部</a></li>
            <li><a href="/newsInfo/list/1" class="news_type_btn ${type== 1 ? 'on':''}" data="1">最新资讯</a></li>
            <li><a href="/newsInfo/list/2" class="news_type_btn ${type== 2 ? 'on':''}" data="2">热门资讯</a></li>
          </ul>
          <span class="txt">新闻资讯</span>
        </div>
        <div class="greybg">
          <ul class="news_list clearfix" id="newsInfoList">

          </ul>
        </div>
      </div><!-- 最新活动 END-->
      <!-- 分页 -->
      <div class="pages mt10 mb20 clearfix">
        <ul id="pagtiona" class="pages_list fr">

        </ul>
      </div>
    </div>
  </div>
</div>

<input id="totalCount" type="hidden" value="${totalCount}" />
<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/template" id="dotTemplate">
  {{? it.length == 0 }}
  {{??}}
  {{~it :value:index}}
  <li class="appendTo">
    <div class="news_box clearfix">
      <a href="/newsInfo/detail/{{=value.id}}" class="left_imgbox"><img src="${resUrl}/{{=value.thumbnail}}" alt=""></a>
      <div class="right_txtbox">
        <p class="title"><a href="/newsInfo/detail/{{=value.id}}">{{=value.title}}</a></p>
        <p class="txtintro"><a href="/newsInfo/detail/{{=value.id}}">{{=value.brief}}</a></p>
        <p class="info"><span class="time">{{=value.timeVO}}</span>发布人：<a href="javascript:void(0);">{{=value.publisher}}</a></p>
      </div>
    </div>
  </li>
  {{~}}
  {{?}}
</script>

<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/newsinfo/newsInfoList.js"></script>

</body>
</html>