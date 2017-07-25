<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>赞助巨作</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
</head>
<body>

<%@include file="/common/memberCenterHeader.jsp"%>

<div class="user_content">
  <div class="main">
    <div class="clearfix">

      <%@include file="/common/memberCenterLeft.jsp"%>

      <div class="right_box" id="partRight">
        <div class="sections">
          <div class="title_box">
            <span class="txt">赞助巨作</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg" style="min-height: 640px;">
              <div class="sponsor_box clearfix">
                <div class="sponsor_way sponsor_way1">
                  <h2 class="titletxt">赞助巨作49元<!-- <br>可获得6个月VIP --></h2>
                  <p class="intro">（VIP特权时间6个月同时获得400积分）</p>
                  <div class="btnbox"><a href="javascript:void(0);" class="sponsor_btn" data="4">立即赞助</a></div>
                </div>
                <div class="sponsor_way sponsor_way2">
                  <h2 class="titletxt">赞助巨作99元<!-- <br>可获得18个月VIP --></h2>
                  <p class="intro">（VIP特权时间18个月同时获得1000积分）</p>
                  <div class="btnbox"><a href="javascript:void(0);" class="sponsor_btn" data="5">立即赞助</a></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


<%@include file="/common/memberCenterFooter.jsp"%>


<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/donate/donateIndex.js"></script>
</body>
</html>
