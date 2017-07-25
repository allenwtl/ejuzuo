<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>我的收藏</title>
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
        <div class="sections havaCheckedAll">
          <div class="title_box">
            <div class="right_edit_box pr10">
              <a href="javascript:;" class="edit_btn right_btn js_edit_btn">编辑</a>
              <div class="edit_toolbox">
                <a href="javascript:;" class="checkedall_btn right_btn checked_box checkedAll"><label for="">全选</label></a><a href="javascript:;" class="delete_btn right_btn" id="deleteFavorite">删除</a><a href="javascript:;" class="cancel_btn right_btn js_cancel_btn">取消</a>
              </div>
            </div>
            <span class="txt">我的收藏</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="edit_wrap havaCheckedAll">
                <ul class="edit_list clearfix" id="favoriteList">

                </ul>
              </div>
            </div>
          </div>
          <!-- 分页 -->
          <div class="pages mt10 mb20 clearfix" id="pageList">
            <ul id="pagtiona" class="pages_list fr">

            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<input id="totalCount" value="${totalCount}" type="hidden"/>
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{? it.length == 0 }}
  {{??}}
    {{~it:value:index}}
    <li class="appendTo">
      <div class="edit_ablock bindCheckedAll checked_box favoriteDelete" >
        <a href="{{=value.url}}" target="_blank">
        <img src="${resUrl}{{=value.coverImg}}" alt="" style="width: 315px; height: 315px;">
        <label for="" data="{{=value.id}}"><i></i></label>
        <div class="btmtxt_box"><span class="left_txt">{{=value.designerType}}</span><span class="right_txt">{{=value.designerType}}姓名：{{=value.designerName}}</span></div>
        </a>
      </div>
    </li>
    {{~}}
  {{?}}
</script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/favorite/favorite.js"></script>
</body>
</html>