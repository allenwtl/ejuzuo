<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>用户信息修改</title>
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
    <link href="http://r.ejuzuo.com/plugin/jquery-uploadify/css/uploadify.css" type="text/css" rel="stylesheet"/>
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
                        <span class="txt">个人中心</span>
                    </div>
                    <div class="midcont_box">
                        <div class="contheight_box whitebg">
                            <div class="safe_center">
                                <div class="tabWrap">
                                    <div class="tabBtnBox clearfix">
                                        <a href="/member/showInfo" class="tabBtn  on">个人信息修改</a>
                                        <a href="/member/toUpdateME/mobile" class="tabBtn">手机号码修改</a>
                                        <a href="/member/toUpdateME/email" class="tabBtn">邮箱修改</a>
                                    </div>
                                    <div class="tabContBox">
                                        <div class="tabCont">
                                            <div>
                                                <form id="updateForm" action="/member/updateMemberInfo" method="post">
                                                <div class="authentication_box" style="min-height: 0px;">
                                                    <div class="youckbox">
                                                        <div class="pt15 mb20 clearfix">
                                                            <span class="typename_box">*修改头像</span>
                                                            <div class="label_wrap">
                                                                <div class="clearfix">
                                                                    <div class="right_box">
                                                                        <input type="file" id="uploadify" accept="image/*">
                                                                        <div class="upload_picshowbox">
                                                                            <img src="http://r.ejuzuo.com${el:toJSONObject(requestScope.memberInCache.profileImg).picold}">
                                                                        </div>
                                                                        <div class="upload_input">
                                                                            <input type="hidden" name="profileImg" value="<c:out value="${member.profileImg}" escapeXml="true"/>" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="pt15 mb20 clearfix">
                                                            <span class="typename_box">*修改昵称</span>
                                                            <div class="label_wrap">
                                                                <div class="clearfix">
                                                                    <div class="right_box">
                                                                        <div class="nick_input_box">
                                                                            <input type="text" class="w340" name="nickName" placeholder="请输入新的昵称" value="${member.nickName}" required>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="authentication_btn_box">
                                                    <a href="javascript:void(0);" class="btn" id="saveUpdate" style="margin-left: 180px;">保存修改</a>
                                                </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="http://r.ejuzuo.com/js/util/cookieUtil.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-uploadify/script/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>

<script type="text/javascript" src="http://r.ejuzuo.com/js/member/updateMemberInfo.js"></script>
</body>
</html>
