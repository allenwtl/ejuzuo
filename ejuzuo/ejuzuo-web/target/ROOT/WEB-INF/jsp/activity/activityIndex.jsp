<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>活动</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css?v=20160728000">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/swiper-3.3.1.min.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/pages.css">
    <style> 
	div.test
	{
	white-space:nowrap; 
	width:15em; 
	overflow:hidden; 
	}
	</style>
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>
<%@include file="/common/menu.jsp"%>
<%@include file="/static/adspace/20160712173220597.html"%>
<div class="content">
  <div class="main">
    <div class="pt20">
      <!-- screen_groups -->
      <div class="screen_groups screen_groups_ac">
        <div class="mb15">
          <div class="right_screen clickQuery">
            <label>活动类别：</label>
            <span class="${activityType == null || activityType ==0 ? 'on':''}" data="0">全部</span>
            <c:forEach items="${huodongleibei}" var="item">
              <span class="${item.valueCode == activityType ? 'on':''}" data="${item.valueCode}">${item.valueName}</span>
            </c:forEach>
          </div>
        </div>
        <div class="mb15">
          <div class="right_screen clickQuery"><label>活动地区：</label>
            <span class="${area == null || area ==0 ? 'on':''}" data="0">全部</span>
            <c:forEach items="${areaList}" var="item">
              <span class="${item.valueCode == area ? 'on':''}" data="${item.valueCode}">${item.valueName}</span>
            </c:forEach>
          </div>
        </div>
        <div>
          <div class="right_screen clickQuery">
            <label>活动时间：</label>
            <span class="${time == null || time ==0 ? 'on':''}" data="0">全部</span>
            <c:forEach items="${timeList}" var="item">
              <span class="${item.index == time ? 'on':''}" data="${item.index}">${item.description}</span>
            </c:forEach>
          </div>
        </div>
      </div><!-- screen_groups END-->
      <!-- 最新活动-->
      <div class="common_block">
        <div class="greybg">
          <ul class="new_activity_list clearfix" id="activity">

          </ul>
        </div>
      </div><!-- 最新活动 END-->
      <!-- 分页 -->
      <div class="pages mt10 mb20">
        <ul id="pagtiona" class="pages_list clearfix">

        </ul>
      </div>
    </div>
  </div>
</div>


<input type="hidden" value="${totalCount}" id="totalCount">

<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/template" id="dotTemplate">
  {{? it.length == 0 }}
  {{??}}
  {{~it :value:index}}
  <li class="appendTo">
    <div class="new_activity_box">
      <a href="/activity/detail/{{=value.id}}" target="_blank">
        <div class="pic_box">
          <img src="http://r.ejuzuo.com/{{=value.thumbnail}}" class="blockImg" alt="">
          <div class="gradient_txtbox">
            <span class="left_txt"><i class="pos_icon"></i>{{=value.cityVO}}</span>
            <span class="right_txt"><i class="times_icon"></i>{{=value.time}}</span>
          </div>
        </div>
      </a>
      <div class="txtdes_box">
        <div class="newstitle">
        {{if( value.enrollStatus == 0 ){ }}
        <span class="status_btn_un">未开始</span>
        {{ }else if(value.enrollStatus == 1){ }}
        <span class="status_btn">报名中</span>
        {{ }else{ }}
		<span class="status_btn_un">已截止</span>
		{{ } }}
        <div class="test" style="text-overflow:ellipsis;"><a href="/activity/detail/{{=value.id}}">{{=value.title}}</a></div></div>
        <div class="txtinto"><a href="/activity/detail/{{=value.id}}">{{=value.brief}}</a></div>
      </div>
    </div>
  </li>
  {{~}}
  {{?}}
</script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/activity/activityInfo.js"></script>
</body>
</html>