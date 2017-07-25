<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
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
            <div class="right_box">
                <div class="sections">
                    <div class="title_box">
                        <span class="txt">个人中心</span>
                    </div>
                    <div class="midcont_box">
                        <div class="contheight_box whitebg">
                            <div class="safe_center">
                                <div class="tabWrap">
                                    <div class="tabBtnBox clearfix">
                                        <a href="/security/index/mobile" class="tabBtn">手机认证</a>
                                        <a href="/security/index/email" class="tabBtn">邮箱认证</a>
                                        <a href="/security/toUpdatePassword" class="tabBtn on">修改密码</a>
                                    </div>
                                    <div class="tabContBox">
                                        <div class="tabCont" style="min-height: 599px;">
                                            <div class=".pt50">
                                                <c:choose>
                                                    <c:when test="${isThird}">
                                                        <div class="findpassword">
                                                            <div class="txt_rq_box">
                                                                <div class="iconbox"><i class="gou_icon"></i></div>
                                                                <p class="txt_rq">
                                                                    第三方登录的不能修改密码
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="safe_form_wrap">
                                                            <form action="/security/updatePassword" method="post" id="updatePwd">
                                                                <input type="hidden" name="type" value="${type}" id="type"/>
                                                                <input type="hidden" value="${mobile}" id="mobile_str"/>
                                                                <input type="hidden" value="${email}" id="email_str"/>
                                                                <div class="ele_wrap">
                                                                    <div class="mb10 tar"><a class="c999" href="javascript:void(0);" id="updatePwdType" data="email" style="font-size: initial;">用邮箱修改密码</a></div>
                                                                    <div class="input_block mandatory mb20">
                                                                        <span class="star">*</span>
                                                                        <input type="text" class="w230" placeholder="请输入新手机号码" value="${mobile}" id="contanier_text" disabled="disabled">
                                                                        <a href="javascript:void(0);" class="btn"  id="sendCode" style="width: 70px;">发送验证码</a>
                                                                    </div>
                                                                    <div class="input_block mandatory mb20">
                                                                        <span class="star">*</span>
                                                                        <input type="text" class="w350" placeholder="验证码" name="code">
                                                                    </div>
                                                                    <div class="input_block mandatory mb20">
                                                                        <span class="star">*</span>
                                                                        <input type="password" class="w350" placeholder="当前密码" name="oldPwd">
                                                                    </div>
                                                                    <div class="input_block mandatory mb20">
                                                                        <span class="star">*</span>
                                                                        <input type="password" class="w350" placeholder="新密码" name="firPwd">
                                                                    </div>
                                                                    <div class="input_block mandatory mb20">
                                                                        <span class="star">*</span>
                                                                        <input type="password" class="w350" placeholder="确认密码" name="secPwd">
                                                                    </div>
                                                                    <div class="btn_block">
                                                                        <a href="javascript:void(0);" class="block_btn btn" id="pwdSave">保存</a>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
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


<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/security/security.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
</body>
</html>