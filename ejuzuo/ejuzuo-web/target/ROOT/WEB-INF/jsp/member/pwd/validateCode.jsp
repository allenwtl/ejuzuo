<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>输入验证码</title>
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/pages.css">
    <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
</head>
<body class="platforms">
<div class="platforms_wrap clearfix">
    <%@include file="/common/loginLeft.jsp"%>
    <div class="right_box">
        <div class="findpassword">
            <h2 class="title">找回密码</h2>
            <div class="form_wrap">
                <div class="ele_wrap pt100">
                    <input type="hidden" value="${type}" id="type"/>
                    <div class="mb20">
                        <span class="star">验证码已经发送到您的${type eq "mobile" ? "手机中":"邮箱里"}</span>
                    </div>
                    <div class="input_block mb20">
                        <span class="star">*</span>
                        <input type="text" class="w350" value="${account}" readonly="readonly" id="account">
                    </div>
                    <div class="input_block mb20">
                        <span class="star">*</span>
                        <input type="text" class="w350" placeholder="请输入验证码" id="systemCode">
                    </div>
                    <div class="btn_block">
                        <a href="javascript:void(0);" class="block_btn btn" id="sure">下一步，安全验证</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/member/forgetPwd.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
</body>
</html>