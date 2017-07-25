<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="el" uri="ejuzuo.com/el-common" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta property="qc:admins" content="3040544604652525763757" />
    <title>e巨作首页</title>
    <link rel="stylesheet" href="${resUrl}/styles/global.css">
    <link rel="stylesheet" href="${resUrl}/styles/common.css?v=20160808000">
    <link rel="stylesheet" href="${resUrl}/styles/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="${resUrl}/styles/pages.css">
    <style> 
	div.test
	{
	white-space:nowrap; 
	width:15em; 
	overflow:hidden; 
	}
	</style>
    <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp" %>
<%@include file="/common/menu.jsp"%>
<!-- promo -->
<%@include file="/static/adspace/20160523144911975.html"%>
<!-- promo END-->

<div class="content">
    <div class="main pt20">
        <!-- 右侧广告 -->
        <%@include file="/static/adspace/20160523144233729.html"%>
        <!-- 右侧广告 END-->
        <div>
            <%@include file="/static/adspace/20160522144019199.html"%>
            <div class="common_block mt20 ">
                <div class="title_box">
                    <a href="/digital/index/4" class="more" target="_blank">更多</a>
                    <span class="titletxt">品牌馆</span>
                </div>
                <div class="pl10 pr10 pt20 related_recom_digitalIndex">
                    <ul class="produce_list produce_280 clearfix">
                        <c:forEach items="${digital}" var="item">
                            <li>
                                <a href="/digital/digitalDetail/${item.id}">
                                    <img src="${resUrl}${item.thumbnail}" alt="">
                                    <div class="jb_pos_box">
                                        <p class="intro_txt">${item.brandName}</p>
                                        <p class="nametxt">${item.name}</p>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <%--定制馆--%>
            <div class="common_block mt20 ">
                <div class="title_box">
                    <a href="/digital/index/2" class="more" target="_blank">更多</a>
                    <span class="titletxt">定制馆</span>
                </div>
                <div class="pl10 pr10 pt20 related_recom_digitalIndex">
                    <ul class="produce_list produce_280 clearfix">
                        <c:forEach items="${digitalSpecial}" var="item">
                            <li>
                                <a href="/digital/digitalDetail/${item.id}">
                                    <img src="${resUrl}${item.thumbnail}" alt="">
                                    <div class="jb_pos_box">
                                        <p class="intro_txt">${item.brandName}</p>
                                        <p class="nametxt">${item.name}</p>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <%--进口馆--%>
            <div class="common_block mt20 ">
                <div class="title_box">
                    <a href="/digital/index/3" class="more" target="_blank">更多</a>
                    <span class="titletxt">进口馆</span>
                </div>
                <div class="pl10 pr10 pt20 related_recom_digitalIndex">
                    <ul class="produce_list produce_280 clearfix">
                        <c:forEach items="${digitalImprove}" var="item">
                            <li>
                                <a href="/digital/digitalDetail/${item.id}">
                                    <img src="${resUrl}${item.thumbnail}" alt="">
                                    <div class="jb_pos_box">
                                        <p class="intro_txt">${item.brandName}</p>
                                        <p class="nametxt">${item.name}</p>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>


            <%--家居尾品汇--%>
            <div class="common_block mt20 ">
                <div class="title_box">
                    <a href="/digital/index/1" class="more" target="_blank">更多</a>
                    <span class="titletxt">家居尾品汇</span>
                </div>
                <div class="pl10 pr10 pt20 related_recom_digitalIndex">
                    <ul class="produce_list produce_280 clearfix">
                        <c:forEach items="${digitalEnd}" var="item">
                            <li>
                                <a href="/digital/digitalDetail/${item.id}">
                                    <img src="${resUrl}${item.thumbnail}" alt="">
                                    <div class="jb_pos_box">
                                        <p class="intro_txt">${item.brandName}</p>
                                        <p class="nametxt">${item.name}</p>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <%@include file="/static/adspace/20160522144310454.html"%>
            <!-- 设计公司推荐 -->
            <div class="common_block mt20">
                <div class="title_box">
                    <a href="/designerWork/indexList/1/0/0" class="more" target="_blank">更多</a>
                    <span class="titletxt">设计公司推荐</span>
                </div>
                <div class="produce_wrap pl10 pr10 pt20">
                    <ul class="produce_list produce_280 clearfix">
                        <c:forEach items="${companyList}" var="item" varStatus="status">
                            <c:if test="${status.index <=3}">
                                <li>
                                    <a href="/designer/detail/${item.id}">
                                        <img src="${resUrl}${el:toJSONObject(item.coverImg).pic280280}" alt="">
                                        <div class="gradient_txtbox">
                                            <span class="left_txt">${item.name}</span>
                                            <span class="right_txt">
                                                <c:choose>
                                                    <c:when test="${item.designerType == 3}">个人设计师</c:when>
                                                    <c:when test="${item.designerType == 1}"> 设计公司 </c:when>
                                                    <c:otherwise>装修公司</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
<!--                     <div class="ds_reco produce_list js_ds_cpn_reco">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">

                            </div>
                            <div class="swiper-pagination"></div>
                        </div>
                    </div> --> 
                    <c:if test="${companyList.size() > 4}">
                        <div  class="marquee" style="position:relative;width:1160px; overflow:hidden;" id="companyMarquee">
                            <ul class="produce_list produce_280 clearfix js_scroll_pmd_ul">
                                <c:forEach items="${companyList}" var="item" varStatus="status">
                                    <c:if test="${status.index > 3}">
                                        <li>
                                            <a href="/designer/detail/${item.id}">
                                                <img src="${resUrl}${el:toJSONObject(item.coverImg).pic280280}" alt="">
                                                <div class="gradient_txtbox">
                                                    <span class="left_txt">${item.name}</span>
                                                    <span class="right_txt">
                                                        <c:choose>
		                                                    <c:when test="${item.designerType == 3}">个人设计师</c:when>
		                                                    <c:when test="${item.designerType == 1}">设计公司</c:when>
		                                                    <c:otherwise>装修公司</c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </div>
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>    
                </div>
            </div><!-- 设计公司推荐 END -->
            <!-- 设计师推荐 -->
            <div class="common_block mt20">
                <div class="title_box">
                    <a href="/designer/queryDesigner/3/0/0" class="more" target="_blank">更多</a>
                    <span class="titletxt">设计师推荐</span>
                </div>
                <div class="produce_wrap pl10 pr10 pt20">
                    <ul class="produce_list produce_280 clearfix">
                        <c:forEach items="${personList}" var="item" varStatus="status">
                            <c:if test="${status.index <=3}">
                                <li>
                                    <a href="/designer/detail/${item.id}">
                                        <img src="${resUrl}${el:toJSONObject(item.coverImg).pic280280}" alt="" style="height: 280px;">
                                        <div class="gradient_txtbox">
                                            <span class="left_txt">${item.name}</span>
                                            <span class="right_txt">
                                                <c:choose>
		                                        <c:when test="${item.designerType == 3}">个人设计师</c:when>
		                                        <c:when test="${item.designerType == 1}">设计公司 </c:when>
		                                        <c:otherwise>装修公司</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                    <c:if test="${personList.size() > 4}">
                        <div  class="marquee" style="position:relative;width:1160px; overflow:hidden;" id="personMarquee">
                            <ul class="produce_list produce_280 clearfix js_scroll_pmd_ul">
                                <c:forEach items="${personList}" var="item" varStatus="status">
                                    <c:if test="${status.index > 3}">
                                        <li>
                                            <a href="/designer/detail/${item.id}" style="height: 280px;">
                                                <img src="${resUrl}${el:toJSONObject(item.coverImg).pic280280}" alt="">
                                                <div class="gradient_txtbox">
                                                    <span class="left_txt">${item.name}</span>
                                                    <span class="right_txt">
                                                        <c:choose>
					                                        <c:when test="${item.designerType == 3}">个人设计师</c:when>
					                                        <c:when test="${item.designerType == 1}">设计公司 </c:when>
					                                        <c:otherwise>装修公司</c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </div>
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </div><!-- 设计师推荐 END -->
            <%--<div class="ad1 mt20"><a href="#" target="_blank"><img src="${resUrl}/images/img/ad2.jpg" class="blockImg" alt=""></a></div>--%>
            <%@include file="/static/adspace/20160522144356074.html"%>
            <!-- 最新活动-->
            <%--<div class="common_block mt20">--%>
                <%--<div class="title_box">--%>
                    <%--<a href="/activity/index" class="more" target="_blank">更多</a>--%>
                    <%--<span class="titletxt">最新活动</span>--%>
                <%--</div>--%>
                <%--<div class="greybg">--%>
                    <%--<ul class="new_activity_list clearfix">--%>
                        <%--<c:forEach items="${activityInfo}" var="item">--%>
                            <%--<li>--%>
                                <%--<div class="new_activity_box">--%>
                                    <%--<a href="/activity/detail/${item.id}" target="_blank">--%>
                                        <%--<div class="pic_box">--%>
                                            <%--<img src="${resUrl}${item.thumbnail}" class="blockImg" alt="">--%>
                                            <%--<div class="gradient_txtbox">--%>
                                                <%--<span class="left_txt"><i class="pos_icon"></i>${item.cityVO}</span>--%>
                                                <%--<span class="right_txt"><i class="times_icon"></i>${item.time}</span>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                    <%--<div class="txtdes_box">--%>
                                        <%--<div class="newstitle">--%>
                                        	<%--<c:if test="${item.enrollStatus == 0}">--%>
                                        		<%--<span class="status_btn_un">未开始</span>--%>
                                        	<%--</c:if>--%>
                                        	<%--<c:if test="${item.enrollStatus == 1}">--%>
                                        		<%--<span class="status_btn">报名中</span>--%>
                                        	<%--</c:if>--%>
                                        	<%--<c:if test="${item.enrollStatus == 2}">--%>
                                        		<%--<span class="status_btn_un">已截止</span>--%>
                                        	<%--</c:if>--%>
                                        	<%--<div class="test" style="text-overflow:ellipsis;"><a href="/activity/detail/${item.id}">${item.title}</a></div>--%>
                                        <%--</div>--%>
                                        <%--<div class="txtinto"><a href="javascript:void(0)">${item.brief}</a></div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</li>--%>
                        <%--</c:forEach>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div><!-- 最新活动 END-->--%>
            <!-- 新闻资讯-->
            <div class="common_block mt20">
                <div class="title_box">
                    <a href="/newsInfo/index" class="more" target="_blank">更多</a>
                    <span class="titletxt">新闻资讯</span>
                </div>
                <div class="greybg">
                    <ul class="news_list clearfix">
                        <c:forEach items="${news}" var="item">
                            <li>
                                <div class="news_box clearfix">
                                    <a href="/newsInfo/detail/${item.id}" class="left_imgbox"><img src="${resUrl}${item.thumbnail}" alt=""></a>
                                    <div class="right_txtbox">
                                        <p class="title"><a href="/newsInfo/detail/${item.id}">${item.title }</a></p>
                                        <p class="txtintro"><a href="/newsInfo/detail/${item.id}">${item.brief}</a></p>
                                        <p class="info"><span class="time"><fmt:formatDate value="${item.publishTime.time}" pattern="yyyy年MM月dd日 HH:mm"/></span>发布人：<a href="javascript:void(0);">${item.publisher}</a></p>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div><!-- 新闻资讯 END-->
            <!-- 新闻资讯-->
            <div class="common_block mt20 mb20">
                <div class="title_box">
                    <%--<a href="javascript:void(0);" class="more" target="_blank">更多</a>--%>
                    <span class="titletxt">合作品牌</span>
                </div>
                <ul class="brand_list clearfix">
                    <c:forEach items="${brand}" var="item">
                        <li><a href="/digital/digitalList/0/0/0/${item.id}/0" class="brand_box"><img src="${resUrl}${item.logoImg}" alt=""></a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="${resUrl}/plugin/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-marquee/jquery.marquee.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/index/index.js"></script>
<script type="text/javascript">
    var promo_mySwiper = new Swiper('.js_promo .swiper-container', {
        autoplay: 5000,//可选选项，自动滑动
        loop : true,
        autoplayDisableOnInteraction : false,
        effect : 'fade',
        fade: {
            crossFade: true
        },
        pagination : '.swiper-pagination',
        paginationClickable :true
    });
    var ds_cpn_reco_mySwiper = new Swiper('.js_ds_cpn_reco .swiper-container', {
        autoplay: 5000,//可选选项，自动滑动
        loop : true,
        slidesPerView : 4,
        slidesPerGroup : 4,
        autoplayDisableOnInteraction : false,
        pagination : '.swiper-pagination',
        paginationClickable :true
    })

    //浮动块
    var t = $('.js_setfixed').offset().top;
    $(window).scroll(function(event) {
        var x = $(window).scrollTop();
        if(x > t){
            $('.js_setfixed').addClass('fixed_begin');
        }else{
            $('.js_setfixed').removeClass('fixed_begin');
        }
    });

    //滚动 companyMarquee
    $("#companyMarquee").marquee({
        direction : "left",
        speed : 30,
        marqueeElem : $("#companyMarquee .js_scroll_pmd_ul")
    });

    $("#personMarquee").marquee({
        direction : "left",
        speed : 30,
        marqueeElem : $("#personMarquee .js_scroll_pmd_ul")
    });
</script>
</body>
</html>