<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>忘记密码</title>
    <link rel="stylesheet" href="${resUrl}/styles/global.css">
    <link rel="stylesheet" href="${resUrl}/styles/common.css">
    <link rel="stylesheet" href="${resUrl}/styles/pages.css">

    <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
    <style type="text/css">
        .k_yzm_img {
            display: inline-block;
            vertical-align: middle;
            border: 1px solid #ccc;
            height: 46px;
            width: 110px;
            margin-left: 10px;
        }
        .k_yzm_link {
            display: inline-block;
            vertical-align: middle;
            margin-left: 5px;
        }
    </style>
</head>
<body class="platforms">
<div class="platforms_wrap clearfix">
    <%@include file="/common/loginLeft.jsp"%>
    <div class="right_box">
        <div class="findpassword">
            <h2 class="title">找回密码</h2>
            <div class="form_wrap">
                <form id="findPwdForm" method="post">
                    <div class="ele_wrap pt100">
                        <input value="email" type="hidden" id="codeType"/>
                        <div class="mb10 tar">
                            <a class="c999" href="javascript:void(0);" id="findPwdType" type="mobile" style="font-size: 16px;">用手机找回</a></div>
                        <div class="input_block mb20">
                            <span class="star">*</span>
                            <input type="text" class="w350" placeholder="请输入您注册时填写的邮箱" id="account" name="account">
                        </div>
                        <div class="v_cord_box clearfix">
                            <div class="v_cord_box clearfix">
                                <div class="v_cord" style="width:180px;">
                                    <div class="input_block v_success mb20">
                                        <span class="star">*</span>
                                        <input type="text" class="w130" placeholder="验证码" id="validateCode" name="code">
                                    </div>
                                </div>
                                <img id="image-code" class="k_yzm_img"> <a href="javascript:;" class="k_yzm_link" id="resetImageCode">看不清，换一张</a>
                            </div>
                        </div>
                        <div class="btn_block">
                            <a href="javascript:void(0);" class="block_btn btn" id="nextStep">下一步，安全验证</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/member/forgetPwd.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
</body>
</html>