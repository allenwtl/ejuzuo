<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>个人作品列表</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/user_style.css">
  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>

</head>
<body>

<%@include file="/common/memberCenterHeader.jsp" %>
<div class="user_content">
  <div class="main">
    <div class="clearfix">
      <%@include file="/common/memberCenterLeft.jsp" %>
      <div class="right_box" id="partRight">
        <div class="sections">
          <div class="title_box">
            <span class="txt">作品列表</span>
          </div>
          <div style="margin-top:10px; font-size:14px; line-height:30px;" class="clearfix">
            <a href="/designerWork/uploadWork" style="color:#222;" class="fr mr10">上传作品</a>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="mydownload">
                <div class="p_table_box havaCheckedAll">
                  <table width="100%">
                    <thead>
                    <tr>
                      <th width="20%">
                        <div class="casemsgth">作品名称</div>
                      </th>
                      <th width="15%">
                        <div class="casemsgth">风格类型</div>
                      </th>
                      <th width="10%">
                        <div class="casemsgth">浏览量</div>
                      </th>
                      <th width="10%">
                        <div class="casemsgth">编辑状态</div>
                      </th>
                      <th width="20%">
                        <div class="casemsgth">创建时间</div>
                      </th>
                      <th width="25%">
                        <div class="operate_box">操作</div>
                      </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="trTbody">
                      <td colspan="8">
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

<%@include file="/common/memberCenterFooter.jsp" %>
<script type="text/template" id="dotTemplate">
  {{? it.length == 0 }}
  {{?? }}
  {{~it :value:index }}
  <tr class="appendTo">
<%--    <td>
      <div class="checked_box bindCheckedAll checkitem">
        <label for="" data="{{=value.id}}"><i></i></label>
      </div>
    </td>--%>
    <td>
      <div class="case_msg clearfix">
        <div class="left_imgbox"><a href="/designerWork/detail/{{=value.id}}" target="_blank"><img src="${resUrl}{{=value.coverImg}}" alt="" style="margin-left: 10px;"></a></div>
        <div class="right_txtbox">
          <p class="casename" style="font-size: 12px">{{= value.name }}</p>
          <p class="caseintro"></p>
        </div>
      </div>
    </td>
    <td>
      <div class="reckon_box">
        <span class="num" style="font-size: 12px">
          {{= value.styleVo}}
        </span>
      </div>
    </td>
    <td>
      <div class="reckon_box">
        <span class="num">
          {{= value.viewCount}}
        </span>
      </div>
    </td>
    <td>
      <div class="times">
        <span class="num">
          {{? value.editStatus == 1}}
            已发布
          {{??}}
            未发布
          {{?}}
        </span>
      </div>
    </td>
    <td>
      <div class="times">{{=value.uploadTime}}</div>
    </td>
    <td>
      <div class="operate_box">
        <a href="javascript:void(0);" class="delete_btn downloadFile deleteItem" data="{{=value.id}}">删除</a>
        <a href="javascript:void(0);" class="delete_btn downloadFile deployItem" data="{{=value.id}}">
          {{? value.editStatus == 1}}
          暂停
          {{??}}
          发布
          {{?}}
        </a>
        <a href="javascript:void(0);" class="delete_btn downloadFile updateItem" data="{{=value.id}}">修改</a>
      </div>
    </td>
  </tr>
  {{~}}
  {{?}}
</script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-multidownload/jquery-multidownload.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/designerwork/designerworkList.js"></script>

</body>
</html>