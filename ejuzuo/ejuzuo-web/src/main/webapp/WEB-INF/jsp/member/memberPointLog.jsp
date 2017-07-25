<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>积分记录</title>
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
            <span class="txt">积分记录</span>
          </div>
          <div style="margin-top:10px; font-size:16px; line-height:30px;">
            你的当前有效积分为：${data.balance}
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg" style="min-height:641px">
              <div class="score_record">
                <div class="p_table_box havaCheckedAll">
                  <table width="100%">
                    <thead>
                    <tr>
                      <th width="25%">
                        <div class="casemsgth">项目名称</div>
                      </th>
                      <th width="25%">
                          <div class="casemsgth">积分变化</div>
                      </th>
                      <th width="25%">
                          <div class="casemsgth">积分余额</div>
                      </th>
                      <th width="25%">
                        <div class="casemsgth">创建时间</div>
                      </th>
                    </tr>
                    </thead>
                    <tbody >
                    <tr class="appendTo">
                      <td colspan="4">暂无记录</td>
                    </tr>
                    <tr id="trTbody" style="display:none;">
                      <td colspan="4">
                          <!-- 分页 -->
                          <div class="pages mt10 mb20 clearfix">
                              <ul id="pagtiona" class="pages_list fr" >

                              </ul>
                          </div><!-- 分页 -->
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<input id="totalCount" value="${totalCount}" type="hidden"/>
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{ if(it.length == 0){ }}
  {{ } else { }}
  {{ for( var i =0 ;i< it.length; i++){ }}
  <tr class="appendTo">
    <td>
        <div class="casenametxt">{{=it[i].projectName}}</div>
    </td>
    <td>
      <div class="reckon_box">
        {{=it[i].showAmount}}
      </div>
    </td>
    <td>
      <div class="reckon_box">
        <span class="num">{{=it[i].pointBalance}}</span>分
      </div>
    </td>
    <td>
      <div class="times">{{=it[i].showTime}}</div>
    </td>
  </tr>
  {{ } }}
  {{ } }}
</script>
<script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/member/point.js"></script>
</body>
</html>