<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl", "http://r.ejuzuo.com"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我是设计师</title>
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/plugin/jquery-uploadify/css/uploadify.css"  >
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
                        <span class="txt">我是设计师-个人高级认证</span>
                    </div>
                    <div class="midcont_box">
                        <div class="contheight_box whitebg">
                            <form action="/designer/update" id="updateDesigner" method="post">
                                <div class="authentication_box">
                                    <div class="mb50">
                                        <p class="intro_txt">
                                            <c:if test="${data.status == 0 && empty data.workId}">
                                                您的作品信息还未提交，请点击<span>添加作品</span>按钮<br>
                                            </c:if>
                                            <c:if test="${data.status == 1}">
                                                您提交的身份信息正在审核中<br>
                                                您的身份信息仅供<span>巨作</span>认证您的真实身份使用，
                                            </c:if>
                                            <c:if test="${data.status == 2 }">
                                                您提交的身份信息已经审核通过<br>
                                                您的身份信息仅供<span>巨作</span>认证您的真实身份使用，<a href="/designer/detail/${data.id}" target="_blank">我的设计师主页</a>
                                            </c:if>
                                            <c:if test="${data.status == 3 }">
                                                您提交的身份信息审核不通过,原因是:${data.reason}<br>
                                                请修改后,再次提交审核<br>
                                                您的身份信息仅供<span>巨作</span>认证您的真实身份使用，
                                            </c:if>
                                        </p>
                                    </div>
                                    <div class="clearfix">
                                        <div class="left_box">
                                            <div class="ele_wrap">
                                                <div class="input_block mandatory mb20">
                                                    <span class="star">*</span>
                                                    <input type="text" class="w340" placeholder="QQ" name="qq" value="${data.qq}"
                                                           required <c:if test="${data.status == 1 || data.status == 2}">disabled</c:if> >
                                                </div>
                                            </div>
                                            <div class="ele_wrap">
                                                <div class="input_block mandatory select_box mb20 ">
                                                    <span class="star">*</span>
                                                    <input type="text" class="w70" disabled placeholder="地址">
                                                    <div class="province_sl_box sl_box">
                                                        <div class="sl_value">省</div>
                                                        <i class="icon"></i>
                                                        <div class="sl_list_wrap">
                                                            <ul class="province_sl_list">
                                                                <c:forEach items="${areaList}" var="item">
                                                                    <li data="${item.id}">${item.name}</li>
                                                                </c:forEach>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="county_sl_box sl_box">
                                                    <div class="sl_value">市</div>
                                                    <i class="icon"></i>
                                                    <div class="sl_list_wrap">
                                                        <ul class="county_sl_list" id="city">
                                                            <c:forEach items="${city}" var="item">
                                                                <li data="${item.id}">${item.name}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="right_box">
                                            <div class="ele_wrap">
                                                <div class="input_block  mandatory  mb20">
                                                    <span class="star">*</span>
                                                    <input type="text" class="w340" placeholder="姓名" name="name"  value="${data.name}"
                                                           required <c:if test="${data.status == 1 || data.status == 2}">disabled</c:if> >
                                                </div>
                                            </div>
                                            <div class="ele_wrap">
                                                <div class="input_block mandatory mb20">
                                                    <span class="star">*</span>
                                                    <input type="text" class="w340" placeholder="详细地址(区/县)" name="address" value="${data.address}"
                                                           required <c:if test="${data.status == 1 || data.status == 2}">disabled</c:if> >
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="youckbox clearfix">
                                        <div class="right_box">
                                            <span class="typename_box">*封面图片</span>
                                            <div class="label_wrap">
                                                <input type="file" id="uploadify">
                                                <div class="upload_picshowbox">
                                                    <img src="http://r.ejuzuo.com${el:toJSONObject(data.coverImg).picold}">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="youckbox">
                                        <div class="radio_box clearfix">
                                            <span class="typename_box">*职业身份</span>
                                            <div class="label_wrap" id="zhiye">
                                                <c:forEach items="${zhiye}" var="item">
                                                    <label for="" data="${item.valueCode}" ><i></i>${item.valueName}</label>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="checked_box pt15 clearfix">
                                            <span class="typename_box">*擅长风格</span>
                                            <div class="label_wrap" id="fengge">
                                                <c:forEach items="${fengge}" var="item">
                                                    <label for="" data="${item.valueCode}"><i></i>${item.valueName}</label>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="pt15 mb20 clearfix">
                                            <span class="typename_box">*个人简介</span>
                                            <div class="label_wrap">
                                                <textarea name="brief" required ><c:out value="${data.brief}" /></textarea>
                                            </div>
                                        </div>
                                        <div class="pt15 clearfix">
                                            <span class="typename_box">设计理念</span>
                                            <div class="label_wrap">
                                                <textarea name="designIdea"><c:out value="${data.designIdea}" /></textarea>
                                            </div>
                                        </div>
                                        <div class="pt15 clearfix">
                                            <span class="typename_box">风格介绍</span>
                                            <div class="label_wrap">
                                                <textarea name="styleIntro"><c:out value="${data.styleIntro}" /></textarea>
                                            </div>
                                        </div>
                                        <div class="pt15 mb20 clearfix">
                                            <span class="typename_box">代表作品</span>
                                            <div class="label_wrap">
                                                <textarea name="classWorks"><c:out value="${data.classWorks}" /></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <input name="provice" id="proviceHidden" type="hidden" value="${data.provice}" />
                                <input name="city" id="cityHidden" type="hidden" value="${data.city}" />
                                <input name="coverImg" id="filePath" type="hidden" value='${data.coverImg}'/>
                                <input name="career" id="careerHidden" type="hidden" value="${data.career}" />
                                <input name="adeptStyles" id="adeptStylesHidden" type="hidden" value="${data.adeptStyles}"/>
                                <input name="designerType" type="hidden" value="3">
                                <div class="authentication_btn_box clearfix">
                                    <c:choose>
                                        <c:when test="${not empty data.workId}">
                                            <a href="/designerWork/update/${data.workId}" class="btn fl" style="width:200px; margin-left:110px">查看提交的作品</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/designer/toworks/3" class="btn fl" style="width:200px; margin-left:110px">添加作品</a>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${data.status == 1}">
                                        <!--审核中-->
                                        <a href="javascript:void(0);" class="btn fl ml20" style="width:200px">审核中，不能修改</a>
                                    </c:if>
                                    <c:if test="${data.status == 2 || data.status == 0}">
                                        <!--审核通过-->
                                        <a href="javascript:void(0);" class="btn fl ml20" id="updateInfo" style="width:200px">提交</a>
                                    </c:if>
                                    <c:if test="${data.status == 3}">
                                        <!--审核通过-->
                                        <a href="javascript:void(0);" class="btn fl ml20" id="updateInfo" style="width:200px">重新提交审核</a>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/cookieUtil.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-uploadify/script/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/designer/updateDesigner.js?v=1"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/validate.js"></script>
</body>
</html>