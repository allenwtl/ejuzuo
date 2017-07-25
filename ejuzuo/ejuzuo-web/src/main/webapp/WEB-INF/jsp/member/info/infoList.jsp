<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>系统消息</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/user_style.css">
  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
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
            <span class="txt">系统消息</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="system_msg_box">
                <ul class="system_msg_list" id="infoList">

                </ul>
              </div>
            </div>

          </div>
          <!-- 分页 -->
          <div class="pages mt10 mb20 clearfix">
            <ul id="pagtiona" class="pages_list fr">

            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<input value="${totalCount}" type="hidden" id="totalCount"/>
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{? it.totalCount == 0 }}
  {{??}}
  {{~it :value:index}}
  <li  class="appendTo" data="{{=value.id}}" data-read="{{=value.read}}" style="font-size: 14px;">
    {{=value.content}}
    <p class="times">
      {{=value.timeVO}}
    </p>
  </li>
  {{~}}
  {{?}}
</script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/msginfo/msginfo.js"></script>

</body>
</html>