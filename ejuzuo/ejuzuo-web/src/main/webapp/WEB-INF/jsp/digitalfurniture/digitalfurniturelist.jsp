<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>数字家居列表页</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/pages.css">

  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>

<%@include file="/common/menu.jsp"%>

<div class="content">
  <div class="main">
    <!-- 右侧广告 -->
    <%@include file="/static/adspace/20160523144233729.html"%>
    <!-- 右侧广告 END-->
    <div>
      <!-- screen_groups -->
      <div class="screen_groups screen_groups_produce mt20">
        <div class="mb15">
          <div class="right_screen h_ej_type clickQuery" id="digitalType">
            <label>大类：</label>
            <c:forEach items="${digitalTypeList}" var="item">
              <span data="${item.index}" class="${digitalType == item.index ? 'on':''}">${item.description}</span>
            </c:forEach>
          </div>
        </div>
        <div class="mb15">
          <div class="right_screen h_ej_type clickQuery" id="bigSpace">
            <label>空间：</label>
            <span class="${bigSpace == 0 ?'on':''}" data="0">全部</span>
            <c:forEach items="${bigSpaceList}" var="item">
              <span data="${item.valueCode}" class="${bigSpace == item.valueCode ? 'on':''}">${item.valueName}</span>
            </c:forEach>
          </div>
          <div class="ej_type_box right_screen clickQuery" id="smallSpace">
            <span class="${smallSpace == 0 ?'on':''}" data="0">全部</span>
            <c:forEach items="${smallSpaceList}" var="item">
              <span data="${item.valueCode}" class="${smallSpace == item.valueCode ? 'on':''}">${item.valueName}</span>
            </c:forEach>
          </div>
        </div>
        <div class="mb15">
          <div class="right_screen clickQuery" id="style">
            <label>风格：</label>
            <span class="${style == 0 ?'on':''}" data="0">全部</span>
            <c:forEach items="${styleList}" var="item">
              <span data="${item.valueCode}" class="${style == item.valueCode ? 'on':''}">${item.valueName}</span>
            </c:forEach>
          </div>
        </div>
        <div>
          <div class="right_screen showmore_screen js_right_screen clickQuery show_right_screen" id="brand">
            <label>品牌：</label>
            <span class="${brand == 0 ?'on':''}" data="0">全部</span>
            <div style="float: right;width: 1020px;">
              <c:forEach items="${brandList}" var="item" varStatus="idx" >
                <span class="pro_imgbox ${brand == item.id ? 'on':''}" data="${item.id}">
                  <img src="${resUrl}${item.logoImg}">
                </span>
              </c:forEach>
            </div>
            <%--<a href="javascript:;" class="js_showhide_btn">更多</a>--%>
            <%--<!-- >div class="hidebox clickQuery" style="margin-left:5px" -->--%>
             <%--<c:forEach items="${brandList}" var="item" begin="10" varStatus="idx" >--%>
              <%--<span class="pro_imgbox ${brand == item.id ? 'on':''}" style="${idx.index == 10 ? 'margin-left:10px':''}" data="${item.id}">                --%>
                <%--<img src="${resUrl}${item.logoImg}">--%>
              <%--</span>--%>
              <%--</c:forEach> --%>
             <%--<!--  --/div-->--%>
          </div>
        </div>
      </div><!-- screen_groups END-->
      <div class="common_block mt20">
        <div class="pl10 pr10 pt10">
          <c:choose>
            <c:when test="${totalCount == 0}">
              <div style="min-height: 50px;background: #fff;text-align: center;line-height: 50px;font-size: 16px;" class="appendTo">暂无记录</div>
            </c:when>
            <c:otherwise>
              <ul class="produce_list produce_280 clearfix" id="produceList">

              </ul>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <!-- 分页 -->
      <div class="pages mt10 mb20 clearfix">
        <ul id="pagtiona" class="pages_list fr" >

        </ul>
      </div><!-- 分页 -->
    </div>
  </div>
</div>
<input type="hidden" id="totalCount" value="${totalCount}"/>
<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/template" id="dotTemplate">
  {{? it.length == 0 }}
  {{??}}
  {{~it:value:index }}
  <li class="appendTo">
    <a href="/digital/digitalDetail/{{=value.id}}"><img src="${resUrl}{{=value.thumbnail}}" alt=""></a>
    <div class="produce_introbtm">
      <p class="produce_u_name ellipTxt">{{=value.name}}</p>
      <!--p class="produce_name ellipTxt">A系列家具案例</p-->
    </div>
  </li>
  {{~}}
  {{?}}
</script>

<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/digitalfurniture/digitalfurniturelist.js"></script>
</body>
</html>