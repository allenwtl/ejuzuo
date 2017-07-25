<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>邮箱注册</title>
    <link rel="stylesheet" href="${resUrl}/styles/global.css">
    <link rel="stylesheet" href="${resUrl}/styles/common.css">
    <link rel="stylesheet" href="${resUrl}/styles/pages.css">

    <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" charset="utf-8"></script>
    <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
</head>
<body class="platforms">
<div class="platforms_wrap clearfix">
    <%@include file="/common/loginLeft.jsp"%>
    <div class="right_box">
        <div class="setheight">
            <div class="form_wrap">
                <div class="mb10 tar"><a class="c999" href="/member/toRegister/mobile" style="font-size: 16px;">用手机注册</a></div>
                <form action="/member/emailRegister" method="post" id="emailForm">
                    <div class="ele_wrap" id="emailDiv">
                        <div class="input_block mandatory mb20">
                            <span class="star">*</span>
                            <input type="email" class="w350" placeholder="邮箱" id="email" name="email">
                        </div>
                        <div class="input_block mandatory mb20">
                            <span class="star">*</span>
                            <input type="text" class="w350" id="emailNickName" name="nickName" placeholder="昵称，4-16个字符，字母／中文／数字／下划线">
                        </div>
                        <div class="input_block mandatory mb20">
                            <span class="star">*</span>
                            <input type="password" class="w350" placeholder="设置密码" id="firEmailPassword" name="password">
                        </div>
                        <div class="input_block mandatory mb20">
                            <span class="star">*</span>
                            <input type="password" class="w350" placeholder="确认密码" id="secEmailPassword" name="secPassword">
                        </div>
                        <div class="v_cord_box clearfix">
                            <div class="v_cord">
                                <div class="input_block v_success mb20">
                                    <span class="star">*</span>
                                    <input type="text" name="code" class="w180" placeholder="验证码" id="checkCode">
                                    <div class="v_rs_box v_success_box">
                                        <span class="v_txt" style="font-size:12px;" id="showTimeLimit"></span>
                                    </div>
                                </div>
                            </div>
                            <a href="javascript:void(0);" id="emailButton" class="sendv_vord_btn">发送邮件</a>
                        </div>
                        <div class="xieyi_box checked_box mb40">
                            <label class="checked" for=""><i></i>我已阅读并接受 <a href="/member/copyright" target="_blank">版权声明</a> 和 <a href="/member/privacy" target="_blank">隐私保护</a> 条款</label>
                        </div>
                        <div class="btn_block">
                            <a href="javascript:void(0);" class="block_btn btn" id="emailRegister">免费注册</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="otherLogType">
            <!--  a href="/qq/login/index">
                <i class="qq_icon"></i>
                <span>QQ账号注册</span>
            </a>
            <a href="/weixin/login/wx/index">
                <i class="wx_icon"></i>
                <span>微信账号注册</span>
            </a-->            
            	<p style="margin-top:15px;color:red">温馨提示：还差一步才能成为设计师/设计公司哦！请继续完善个人高级认证/公司认证的资料。</p>
        </div>
    </div>
</div>

<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/member/register.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" >
    $('#qq_login_btn_dialog').unbind().click(function(){
        var option = {
            appId:'101320160',
            redirectURI:'http://www.ejuzuo.com/third/qq/callback'
        };
        QC.Login.showPopup(option);
    });


    var timeLimit = 0;
    var time ;
    function countdown (){
        timeLimit = 60 ;
        time = setInterval("invorke()", 1000);
        $("#emailButton").attr("timelimit", "on");
    }
    function invorke(){
        var $showTimeLimit = $("#showTimeLimit");
        if( timeLimit < 0 ){
            clearInterval(time);
            $showTimeLimit.text("已经超时, 请重新发送");
            $("#emailButton").removeAttr("timelimit");
            return ;
        }
        $showTimeLimit.text(timeLimit +"秒");
        timeLimit = timeLimit - 1;
    }
</script>
</body>
</html>


