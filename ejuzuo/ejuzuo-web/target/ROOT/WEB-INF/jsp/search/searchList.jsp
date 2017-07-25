<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>搜索结果页面</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/swiper-3.3.1.min.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/pages.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>

</head>
<body style="background:#fff;">

<%@include file="/common/memberCenterHeader.jsp"%>
<!--search resultbox-->
<div class="search_result pt20">
  <div class="main">
    <div class="result-list">
      <div class="car-screen">
        <span id="searchcount">找到约${totalCount}条结果</span>
      </div>
      <div id="result">

      </div>
    </div>
    <div class="pages mt10 mb20 clearfix">
      <ul class="pages_list fr" id="searchPage">

      </ul>
    </div>
  </div>
</div>
<input type="hidden" id="totalCount" value="${totalCount}"/>

<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{?it.length ==0 }}
    <div class="noresult" style="display:none">
      <h4>抱歉，没有找到和“<em class="searchkeyword">${keyword}</em>”相关的结果。</h4>
    </div>
  {{??}}
    {{~it :value:index}}
    <div class="result clearfix mb15">
      <div class="search-result-img">
        <img src="http://r.ejuzuo.com{{=value.coverImg}}"/>
      </div>
      <dl class="result-list-box">
        <dt><a href="{{=value.url}}" target="_blank">{{=value.title}}</a></dt>
        <dd><p>{{=value.content}}</p></dd>
        <div class="msg_info"><span class="times">{{=value.createTime}}</span><span class="styletxt">{{=value.objectType}}</span></div>
      </dl>
    </div>
    {{~}}
  {{?}}
</script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/search/search.js"></script>
</body>
</html>