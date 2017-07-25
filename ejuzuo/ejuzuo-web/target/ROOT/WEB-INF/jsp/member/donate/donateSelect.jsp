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
              <div class="sponsor_dt_box">
                <div class="sponsor_way_box clearfix">
                  <div class="sponsor_way fl ${type==4 ? 'checked':''}" data="4">
                    <h2 class="title">赞助巨作49元<br>可获得6个月VIP</h2>
                    <p class="intro">获得400积分注册成功后分享再送200积分</p>
                    <div class="checked_icon"></div>
                  </div>
                  <div class="sponsor_way fr ${type==5 ? 'checked':''}" data="5">
                    <h2 class="title">赞助巨作99元<br>可获得18个月VIP</h2>
                    <p class="intro">获得1000积分注册成功后分享再送200积分</p>
                    <div class="checked_icon"></div>
                  </div>
                </div>
                <p class="txt">选择赞助方式：</p>
                <div class="sponsor_style clearfix"><a href="javascript:;" class="sponsor_style_btn"><img src="http://r.ejuzuo.com/images/user/alpay.png" alt=""><i class="ckicon"></i></a></div>
                <div class="btn_box"><a href="javascript:void(0);" id="sponsorDonate" class="sponsor_btn">立即赞助</a></div>
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
<script type="text/javascript">
  $(function(){
    $('.sponsor_way_box .sponsor_way').click(function(){
      $(this).addClass('checked').siblings().removeClass('checked');
    })

    $("#sponsorDonate").click(function(){
      var type = $("div.sponsor_way").filter(".checked").attr("data");
      window.location.href = "/payment/charge/"+type ;
    });

  })
</script>
</body>
</html>