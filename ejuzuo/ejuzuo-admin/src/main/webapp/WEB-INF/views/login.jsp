<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="appEnv" scope="application"><spring:eval expression="@applicationProperties.getProperty('appEnv')" /></c:set>
<c:set var="appResHost" scope="application"><c:out value="${appEnv eq 'product' ? '//r.ejuzuo.com' : '//r.ejuzuo.com' }"/></c:set>

<!DOCTYPE html>
<!--[if lt IE 7]>		<html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>			<html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>			<html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->	<html lang="en" class="no-js"> <!--<![endif]-->
<head>
<meta charset="utf-8">
<!-- 设置360浏览器默认使用chrome内核渲染 -->
<meta name="renderer" content="webkit">
<!-- 设置IE浏览器默认使用最高IE版本渲染 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>登录 - 巨作 后台</title>

<link rel="icon" type="image/x-icon" href="/resources/favicon.ico">
<link rel="shortcut icon" href="/resources/favicon.ico">

<!-- <link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="${appResHost }/plugins/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

<style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 350px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox,
.form-signin .input-group {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
/* .form-signin input[type="email"] { */
#inputAccount {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
#addon-captcha {
	cursor: pointer;
}
</style>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="http://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>

	<div class="container">
		<form class="form-signin" id="form-signin" action="/login" method="post">
			<h2 class="form-signin-heading">巨作 后台</h2>
			
			<label class="sr-only" for="inputAccount">账号</label>
			<input type="text" class="form-control" id="inputAccount" name="account" placeholder="账号" 
				value="${account }" maxlength="20" required autofocus autocomplete="off">
			
			<label class="sr-only" for="inputPassword">密码</label>
			<input type="password" class="form-control" id="inputPassword" name="password" placeholder="密码" 
				maxlength="16" title="请输入6-16的密码" required autocomplete="off">
			
			<div class="input-group">
				<input type="text" class="form-control" id="inputCaptcha" name="captcha" placeholder="验证码" 
					maxlength="4" required autocomplete="off" aria-describedby="addon-captcha">
				
				<span class="input-group-addon" id="addon-captcha">
					<jsp:useBean id="now" class="java.util.Date" />
					<img alt="验证码" src="/captcha.jpg?t=${now }" id="img-captcha" width="134px" height="30px">
				</span>
			</div>
			
			<c:if test="${not empty error}">
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>Error!</strong> <c:out value="${error }"/>
			</div>
			</c:if>
			
			<button class="btn btn-lg btn-primary btn-block" type="submit" id="btn-submit" data-loading-text="登录中...">登录</button>
		</form>
	</div> <!-- /container -->

	<!-- <script src="http://cdn.bootcss.com/require.js/2.1.22/require.min.js"></script> -->
	<script src="${appResHost }/plugins/require.js/2.1.22/require.min.js"></script>
	<script type="text/javascript">
		(function() {
			'use strict';
			
			/*!
			 * IE10 viewport hack for Surface/desktop Windows 8 bug
			 * Copyright 2014 Twitter, Inc.
			 * Licensed under the Creative Commons Attribution 3.0 Unported License. For
			 * details, see http://creativecommons.org/licenses/by/3.0/.
			 */

			// See the Getting Started docs for more information:
			// http://getbootstrap.com/getting-started/#support-ie10-width
			if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
				var msViewportStyle = document.createElement('style');
				msViewportStyle.appendChild(document.createTextNode('@-ms-viewport{width:auto!important}'));
				document.querySelector('head').appendChild(msViewportStyle);
			}

			require.config({
				baseUrl : '/resources',
				paths : {
					'jquery': ['${appResHost }/plugins/jquery/jquery-2.2.0.min',
					           '//cdn.bootcss.com/jquery/2.2.0/jquery.min'],
					'bootstrap': ['${appResHost }/plugins/bootstrap/3.3.6/js/bootstrap.min',
					              '//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min']
				},
				shim : {
					bootstrap : {
						deps : [ 'jquery' ],
						exports : 'bootstrap'
					}
				}
			});
			require([ 'jquery', 'bootstrap' ], function($, _bootstrap) {

				$("#addon-captcha").on("click", function() {
					$("#img-captcha").attr("src", "/captcha.jpg?t=" + new Date());
				});

				$("#form-signin").on('submit', function() {
					$(".alert-danger").alert('close');
					$("#btn-submit").button('loading');
				});

				setTimeout(function() {
					$(".alert-danger").alert('close');
				}, 10000);
			});
		})(this);
	</script>
</body>
</html>
