<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>${title}</title>
  <meta charset="UTF-8">
  <title>Document</title>
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

<!-- promo -->
<%@include file="/static/adspace/20160523144911975.html"%>
<!-- promo END-->

<div class="content">
  <div class="main">
    <!-- 右侧广告 -->
    <%@include file="/static/adspace/20160523144233729.html"%>
    <!-- 右侧广告 END-->
    <!-- 左侧定位图 -->
    <div class="float_left_anchor js_setfixed">
      <div class="anchor_box">
        <ul class="anchor_list">
          <li class="active"><a href="#js_pr_anchor1" class="all_type_icon all_type_icon_kt"></a><span class="txt">客厅</span></li>
          <li><a href="#js_pr_anchor2" class="all_type_icon all_type_icon_ct"></a><span class="txt">餐厅</span></li>
          <li><a href="#js_pr_anchor3" class="all_type_icon all_type_icon_sf"></a><span class="txt">书房</span></li>
          <li><a href="#js_pr_anchor4" class="all_type_icon all_type_icon_ws"></a><span class="txt">卧室</span></li>
          <li><a href="#js_pr_anchor5" class="all_type_icon all_type_icon_etf"></a><span class="txt">儿童房</span></li>
          <li><a href="#js_pr_anchor6" class="all_type_icon all_type_icon_mt"></a><span class="txt">门厅</span></li>
          <li><a href="#js_pr_anchor7" class="all_type_icon all_type_icon_jdjj"></a><span class="txt">酒店家具</span></li>
          <li><a href="#js_pr_anchor8" class="all_type_icon all_type_icon_bgjj"></a><span class="txt">办公家具</span></li>
          <li><a href="#js_pr_anchor9" class="all_type_icon all_type_icon_cwyp"></a><span class="txt">固装</span></li>
          <li><a href="#js_pr_anchor10" class="all_type_icon all_type_icon_dszm"></a><span class="txt">灯饰照明</span></li>
          <li><a href="#js_pr_anchor11" class="all_type_icon all_type_icon_shipin"></a><span class="txt">饰品</span></li>
          <%--<li><a href="#js_pr_anchor7" class="all_type_icon all_type_icon_cwyp"></a><span class="txt">厨卫用品</span></li>--%>
          <li><a href="#js_pr_anchor12" class="all_type_icon all_type_icon_qita"></a><span class="txt">其它</span></li>
        </ul>
      </div>
    </div>
    <!-- 左侧定位图 END-->
    <div class=""></div>
    <div>
      <div class="common_block js_pr_anchor1 mt20" id="js_pr_anchor1">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${ketingkjxl}" var="item">
              <a href="" data="${item.valueCode}" data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">客厅</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${keting}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor2 mt20" id="js_pr_anchor2">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${cantingkjxl}" var="item">
              <a href="" data="${item.valueCode}"  data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">餐厅</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${canting}" var="item">
              <li>
                <a href="/digital/digitalDetail/${item.id}">
                  <img src="${resUrl}${item.thumbnail}" alt="">
                  <div class="jb_pos_box">

                  </div>
                </a>
                <div class="produce_introbtm">
                  <p class="produce_u_name ellipTxt">${item.name}</p>
                  <!-- >p class="produce_name ellipTxt">A系列家具案例</p -->
                </div>
              </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor3 mt20" id="js_pr_anchor3">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${shufangkjxl}" var="item">
              <a href="" data="${item.valueCode}"  data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">书房</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${shufang}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor4 mt20" id="js_pr_anchor4">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${woshikjxl}" var="item">
              <a href="" data="${item.valueCode}"  data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">卧室</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${woshi}" var="item">
              <li>
                <a href="/digital/digitalDetail/${item.id}">
                  <img src="${resUrl}${item.thumbnail}" alt="">
                  <div class="jb_pos_box">

                  </div>
                </a>
                <div class="produce_introbtm">
                  <p class="produce_u_name ellipTxt">${item.name}</p>
                  <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                </div>
              </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor5 mt20" id="js_pr_anchor5">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${childrenkjxl}" var="item">
              <a href="" data="${item.valueCode}" data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">儿童房</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${children}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor6 mt20" id="js_pr_anchor6">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${mentingkjxl}" var="item">
              <a href="" data="${item.valueCode}" class="digitalCommon"  data-type="${digitalType}">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">门厅</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${menting}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor7 mt20" id="js_pr_anchor7">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${jiudiankjxl}" var="item">
              <a href="" data="${item.valueCode}"  data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">酒店家具</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${jiudian}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>

      <div class="common_block js_pr_anchor8 mt20" id="js_pr_anchor8">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${bangongkjxl}" var="item">
              <a href="" data="${item.valueCode}"  data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">办公家具</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${bangong}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <%--<div class="common_block js_pr_anchor7 mt20" id="js_pr_anchor7">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${chuweikjxl}" var="item">
              <a href="" data="${item.valueCode}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">厨卫用品</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${chuwei}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>--%>
      <div class="common_block js_pr_anchor9 mt20" id="js_pr_anchor9">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${guzhuangkjxl}" var="item">
              <a href="" data="${item.valueCode}"  data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">固装</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${guzhuang}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor10 mt20" id="js_pr_anchor10">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${dengshikjxl}" var="item">
              <a href="" data="${item.valueCode}" data-type="${digitalType}" class="digitalCommon">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">灯饰照明</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${dengshi}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor11 mt20" id="js_pr_anchor11">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${shipinkjxl}" var="item">
              <a href="" data="${item.valueCode}" class="digitalCommon"  data-type="${digitalType}">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">饰品</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${shipin}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="common_block js_pr_anchor12 mt20" id="js_pr_anchor12">
        <div class="title_box">
          <div class="right_link">
            <c:forEach items="${qitakjxl}" var="item">
              <a href="" data="${item.valueCode}" class="digitalCommon"  data-type="${digitalType}">${item.valueName}</a>
            </c:forEach>
          </div>
          <span class="titletxt">其它</span>
        </div>
        <div class="">
          <div class="pl10 pr10 pt20">
            <ul class="produce_list produce_280 clearfix">
              <c:forEach items="${qita}" var="item">
                <li>
                  <a href="/digital/digitalDetail/${item.id}">
                    <img src="${resUrl}${item.thumbnail}" alt="">
                    <div class="jb_pos_box">

                    </div>
                  </a>
                  <div class="produce_introbtm">
                    <p class="produce_u_name ellipTxt">${item.name}</p>
                    <!--p class="produce_name ellipTxt">A系列家具案例</p-->
                  </div>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<input id="totalCount" value="${totalCount}" type="hidden"/>

<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/javascript" src="${resUrl}/plugin/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/js/digitalfurniture/digitalfurniture.js"></script>
</body>
</html>