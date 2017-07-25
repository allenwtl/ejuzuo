<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="el" uri="ejuzuo.com/el-common"%>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>设计师作品详情</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/swiper-3.3.1.min.css">
  <link rel="stylesheet" href="${resUrl}/styles/pages.css">

  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp" %>

<%@include file="/common/menu.jsp"%>

<div class="content">
  <div class="main">
    <div class="pt20 mb20">

      <!-- 新闻资讯详情-->
      <div class="common_block">
        <div class="detail_wrap">
          <div class="top_box">
            <h1 class="title">${data.name}</h1>
            <p class="txt">作品分类：${data.styleVo}</p>
            <p class="txt">版权信息：“设计素材”栏目内，站内会员所分享的全部“资源／素材”，仅供学习与参考，版权为原作者所有。</p>
          </div>
          <div class="clearfix">
            <div class="left_box">
              <div class="cont_introbox">
                <div>
                  <c:forEach items="${el:getJSONArray(data.content)}" var="item">
                    <img src="${resUrl}${item.img}" alt="">
                    <p>${item.intro}</p>
                  </c:forEach>
                </div>
                <div class="getbyit_box clearfix">
                  <%--<a href="#" class="right_dlbtn">立即下载</a>--%>
                  <div class="collection_box"><a href="javascript:;" class="collect_icon js_collect_icon ${data.favorite ?'on':''}" data-type="1" data-id="${data.id}" id="collect"></a>收藏数：<span id="like">${data.like}</span></div>
                  <div class="share_box fl">
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
            <div class="right_box">
              <div class="desiner_box">
                <div class="clearfix">
                  <div class="imgbox">
                    <i class="user_icon"></i>
                    <a href="/designer/detailByMemberId/${data.memberId}"><img src="${resUrl}${data.designerImg}" alt=""></a>
                  </div>
                  <div class="txtbox">
                    <p class="nametxt"><a href="/designer/detailByMemberId/${data.memberId}">${data.designerName}</a></p>
                    <p class="intotxt">${data.address}-${data.designerType}</p>
                  </div>
                </div>
                <div class="xgdatdabox">作品数量：<span class="num1">${data.workCount}</span>粉丝：<span class="num2">${data.followerCount}</span>浏览：<span class="num3">${data.personViewCount}</span></div>
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
<script type="text/javascript" src="${resUrl}/js/designerwork/designerworkDetail.js"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
<script type="text/javascript">
  var img = '<img src="/designerWork/viewCount/${data.id}?q='+Math.random()*1000+' " style="display: none;">';
  var $Img = $(img);
  $('body').append($Img);
</script>
</body>
</html>