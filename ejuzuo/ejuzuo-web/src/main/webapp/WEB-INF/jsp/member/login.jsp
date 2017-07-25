<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <link rel="stylesheet" href="${resUrl}/styles/global.css">
    <link rel="stylesheet" href="${resUrl}/styles/common.css">
    <link rel="stylesheet" href="${resUrl}/styles/pages.css">
    <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
    <script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" charset="utf-8"></script>
</head>
<body class="platforms">

<div class="platforms_wrap clearfix">
    <%@include file="/common/loginLeft.jsp"%>
    <div class="right_box">
        <div class="setheight">
            <div class="form_wrap">
                <form id="loginForm" action="/login/in" method="post">
                <div class="ele_wrap">
                    <div class="input_block mandatory mb20">
                        <span class="star">*</span>
                        <input type="text" class="w350" name="account" placeholder="邮箱或者手机">
                    </div>
                    <div class="input_block mandatory mb40">
                        <span class="star">*</span>
                        <input type="password" name="password" class="w350" placeholder="密码">
                    </div>
                    <div class="btn_block">
                        <a href="javascript:void(0);" class="block_btn btn" id="login">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
                    </div>
                    <p class="right_txtbox"><a href="/member/forgetPwd/index" class="forget_psw">忘记密码</a></p>
                </div>
                </form>
            </div>
        </div>

        <div class="otherLogType">
            <a href="/qq/login/index">
                <i class="qq_icon"></i>
                <span>QQ账号登录</span>
            </a>
            <a href="/weixin/login/wx/index">
                <i class="wx_icon"></i>
                <span>微信账号登录</span>
            </a>
        </div>
    </div>
</div>

<script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/member/login.js" ></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript">
    $("#qq_login_btn").click(function(){
        var option = {
            appId:'101320160',
            redirectURI:'http://www.ejuzuo.com/third/qq/callback'
        };
        QC.Login.showPopup(option);
    });
</script>
</body>
</html>