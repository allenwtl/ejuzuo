<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl", "http://r.ejuzuo.com"); %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>购物车清单</title>
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
            <span class="txt">购物车</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="shopping_cart">
                <div class="p_table_box havaCheckedAll">
                  <table width="100%">
                    <thead>
                    <tr>
                      <th width="5%">

                      </th>
                      <th width="50%">
                        <div class="casemsgth">项目名称</div>
                      </th>
                      <th width="25%">品牌</th>
                      <th width="20%">
                        <div class="operate_box">操作</div>
                      </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="appendTo">
                      <td colspan="4">
                                         购物车中没有数据
                      </td>
                    </tr>
                    <tr  id="trTbody" style="display: none">
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

<input value="${data.totalCount}" type="hidden" id="totalCount"/>
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{? it.totalCount == 0 }}
  {{??}}
      {{~it.data :value:index}}
        <tr class="appendTo">
          <td>
            <div class="checked_box checkitem" style="display: none" goodsId="{{=value.goodsId}}">
              <label for="" data="{{=value.id}}" data-goodsId="{{=value.goodsId}}" data-p="{{=value.digitalFurniture.pointPrice}}"><i></i></label>
            </div>
          </td>
          <td>
            <div class="case_msg clearfix">
              <div class="left_imgbox"><a href="/digital/digitalDetail/{{=value.digitalFurniture.id}}" ><img src="http://r.ejuzuo.com{{=value.digitalFurniture.thumbnail}}" alt=""></a></div>
              <div class="right_txtbox">
                <p class="casename"><a href="/digital/digitalDetail/{{=value.digitalFurniture.id}}" >{{=value.digitalFurniture.name }}</a></p>
                <!--p class="caseintro">A系列家具案例</p-->
              </div>
            </div>
          </td>
          <td>
            <div class="reckon_box">
              <span class="num">{{=value.brandName}}</span>
            </div>
          </td>
          <td>
            <div class="operate_box">
              <a href="javascript:void(0);" class="delete_btn deleteItem" data="{{=value.id}}">删除</a>
            </div>
          </td>
        </tr>
      {{~}}
    <tr class="appendTo">
      <td>
        <%--<a href="javascript:void(0);" class="delete_btn" id="batchDelete">批量删除</a>--%>
      </td>
      <td colspan="3">
        <%--<div class="statement_box">--%>
          <%--<p class="statement_txt">{{= it.curPageCount}}件模型总计：<strong id="totalMoney">0</strong>分</p>--%>
          <%--<div class="statement_btn_box"><a href="javascript:void(0);" class="statement_btn" id="settle">结&nbsp;&nbsp;&nbsp;&nbsp;算</a></div>--%>
        <%--</div>--%>
        <div class="statement_box">
          <%--<p class="statement_txt">{{= it.curPageCount}}件模型总计：<strong id="totalMoney">0</strong>分</p>--%>
          <div class="statement_btn_box"><a href="javascript:void(0);" class="statement_btn" id="createExcel">生成excel文件</a></div>
        </div>
      </td>
    </tr>
  {{?}}
</script>

<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/buycar/buycar.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-multidownload/jquery-multidownload.js"></script>
<%--<script type="text/javascript" src="http://r.ejuzuo.com/js/download/download.js"></script>--%>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
</body>
</html>
