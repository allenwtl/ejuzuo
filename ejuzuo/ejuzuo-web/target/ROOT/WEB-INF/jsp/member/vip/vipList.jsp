<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>vip记录</title>
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
            <span class="txt">VIP记录</span>
          </div>
          <div style="margin-top:10px; font-size:16px; line-height:30px;">
            最新VIP有效时间：<fmt:formatDate pattern="yyyy-MM-dd" value="${vip.startTime.time}" ></fmt:formatDate> 到 <fmt:formatDate pattern="yyyy-MM-dd" value="${vip.endTime.time}" ></fmt:formatDate>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg" style="min-height:590px">
              <div class="score_record">
                <div class="p_table_box havaCheckedAll">
                  <table width="100%">
                    <thead>
                    <tr>
                      <th width="30%">
                        <div class="casemsgth">项目名称</div>
                      </th>
                      <th width="30%">
                        <div class="casemsgth">时间</div>
                      </th>
                      <th width="30%">
                        <div class="casemsgth">修改时间</div>
                      </th>
                    </tr>
                    </thead>
                    <tbody>


                      <tr  id="trTbody" >
                        <td colspan="4">
                          <div class="pages mt10 mb20 clearfix">
                            <ul id="pagtiona" class="pages_list fr">

                            </ul>
                          </div>
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


<input value="${totalCount}" type="hidden" id="totalCount"/>
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/vip/viplist.js"></script>
<script type="text/template" id="dotTemplate">
  {{? it.totalCount == 0 }}
  {{??}}
  {{~it :value:index}}
  <tr class="appendTo">
    <td>
      <div class="casenametxt">{{=value.reason}}</div>
    </td>
    <td>
      <div class="casenametxt">
        {{=value.addExpireTime}}
      </div>
    </td>
    <td>
      <div class="times">{{=value.updateTime}}</div>
    </td>
<%--    <td>
      <div class="casenametxt">{{=value.operAccount}}</div>
    </td>--%>
  </tr>
  {{~}}
  {{?}}
</script>
</body>
</html>