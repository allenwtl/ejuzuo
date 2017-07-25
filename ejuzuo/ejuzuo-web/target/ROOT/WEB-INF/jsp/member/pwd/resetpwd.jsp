<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>重置密码</title>
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
                <form id="resetForm" action="/member/resetpwd" method="post">
                    <input type="hidden" value="${token}" name="token">
                    <div class="ele_wrap pt100">
                        <div class="input_block mb20">
                            <span class="star">*</span>
                            <input type="password" class="w350" placeholder="设置密码" name="firPwd" id="firPwd">
                        </div>
                        <div class="input_block mb20">
                            <span class="star">*</span>
                            <input type="password" class="w350" placeholder="确认密码" name="secPwd" id="secPwd">
                        </div>
                        <div class="btn_block">
                            <a href="javascript:void(0);" class="block_btn btn" id="resetPwd">确认修改</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/member/forgetPwd.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
</body>
</html>
