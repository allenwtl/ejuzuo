<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>我的下载</title>
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
                        <span class="txt">我的下载</span>
                    </div>
                    <div class="midcont_box">
                        <div class="contheight_box whitebg">
                            <div class="mydownload">
                                <div class="p_table_box havaCheckedAll">
                                    <table width="100%">
                                        <thead>
                                            <tr>
                                               <th width="5%">
                                                </th>
                                                <th width="45%">
                                                    <div class="casemsgth">数字家居</div>
                                                </th>
                                                <th width="15%">
                                                    <div class="casemsgth">积分花费（分）</div>
                                                </th>
                                                <th width="15%">
                                                    <div class="casemsgth">有效时间</div>
                                                </th>
                                                <th width="20%">
                                                    <div class="operate_box">操作</div>
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        <tr class="appendTo">
                                            <td colspan="5">暂无记录</td>
                                        </tr>

                                        <tr id="trTbody" style="display: none">
                                            <td colspan="5">
                                                <div class="pages mt10 mb20 clearfix">
                                                    <ul id="pagtiona" class="pages_list fr">

                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <%--<div class="deleteall_box"><a href="javascript:void(0);" class="delete_allbtn" id="downloadAll">批量下载</a></div>--%>
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
            <td>
                <div class="checked_box bindCheckedAll checkitem">
                    <label for="" data="{{=value.id}}"><i></i></label>
                </div>
            </td>
            <td>
                <div class="case_msg clearfix">
                    <div class="left_imgbox">
					<a href="/digital/digitalDetail/{{=value.digitalFurniture.id}}" >
						<img src="${resUrl}{{=value.digitalFurniture.thumbnail}}" alt="">
					</a>
					</div>
                    <div class="right_txtbox">
                        <p class="casename"><a href="/digital/digitalDetail/{{=value.digitalFurniture.id}}" >{{= value.digitalFurniture.name }}</a></p>
                        <p class="caseintro"></p>
                    </div>
                </div>
            </td>
            <td>
                <div class="reckon_box">
                    <span class="num">{{=value.pointPrice}}</span>
                </div>
            </td>
            <td>
                <div class="times">{{=value.expireTime}}</div>
            </td>
            <td>
                <div class="operate_box">
                    <a href="javascript:void(0);" class="delete_btn downloadFile" data="{{=value.id}}">下载</a>
                </div>
            </td>
        </tr>
        {{~}}
<%--        <tr class="appendTo">
            <td colspan="4">
                <div class="deleteall_box">
                <a href="javascript:void(0);" class="delete_allbtn" id="downloadAll">批量下载</a>
                </div>
            </td>
        </tr>--%>
    {{?}}
</script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-multidownload/jquery-multidownload.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/download/download.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
</body>
</html>