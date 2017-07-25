<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>设计师作品</title>
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/pages.css">

    <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>
<%@include file="/common/menu.jsp"%>
<%@include file="/static/adspace/20160712171741750.html"%>
<div class="content">
    <div class="main">
        <div class="pt20">
            <!-- screen_groups -->
            <div class="screen_groups screen_groups_ac">
                <div class="mb15">
                    <div class="right_screen" id="areaSelect"><label>地区选择：</label>
                        <span data="0" <c:if test="${area == 0}">class="on"</c:if> >全部</span>
                    <c:forEach items="${hotcity}" var="item">
                        <span data="${item.valueCode}"  <c:if test="${area== item.valueCode}">class="on"</c:if> >${item.valueName}</span>
                    </c:forEach>
                    </div>
                </div>
                <div>
                    <div class="right_screen" id="styleSelect"><label>设计风格：</label>
                        <span data="0" <c:if test="${style == 0}">class="on"</c:if> >全部</span>
                        <c:forEach items="${fengge}" var="item">
                            <span data="${item.valueCode}" <c:if test="${style== item.valueCode}">class="on"</c:if> >${item.valueName}</span>
                        </c:forEach>
                    </div>
                </div>
            </div><!-- screen_groups END-->
            <!-- 设计师列表-->
            <div class="common_block mt20">
                <div class="greybg">
                    <ul class="designer_list clearfix">

                    </ul>
                </div>
            </div><!-- 设计师列表 END-->
            <!-- 分页 -->
            <div class="pages mt10 mb20 clearfix">
                <ul id="pagtiona" class="pages_list fr">

                </ul>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="totalCount" value="${totalCount}"/>
<input type="hidden" id="type" value="${type}"/>
<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/template" id="dotTemplate">
    {{? it.length == 0 }}
    {{??}}
        {{~it:value:index}}
        <li class="appendTo">
            <div class="imgbox">
                <a href="/designerWork/detail/{{=value.id}}"><img src="http://r.ejuzuo.com{{=value.coverImg}}" class="blockImg" alt=""></a>
            </div>
            <div class="user_box clearfix">
                <div class="userpic_box">
                    <i class="user_icon"></i>
                    <a href="/designer/detail/{{=value.memberId}}"><img src="http://r.ejuzuo.com{{=value.designerImg}}" alt=""></a>
                </div>
                <div class="user_txt">
                    <p class="txt1"><a href="/designer/detail/{{=value.memberId}}">{{=value.designerName}}</a><span>{{=value.styleVo}}</span></p>
                    <p class="txt2">{{=value.address}}<span class="ml10">案例数量：{{=value.workCount}}</span></p>
                </div>
            </div>
        </li>
        {{~}}
    {{?}}
</script>


<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/designerwork/designerwork.js"></script>
</body>
</html>