<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="smsRecord" scope="request"/>
<c:set var="nav_second" value="checkCode" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>短信</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;短信</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">操作类型</label>
					<select class="form-control" id="filter-checkType">
						<option value="" >--操作类型--</option>
						<option value="0">注册</option>
						<option value="1">用户激活</option>
						<option value="2">验证手机</option>
						<option value="3">验证邮箱</option>
						<option value="4">找回密码</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">目标类型</label>
					<select class="form-control" id="filter-destType">
						<option value="" >--目标类型--</option>
						<option value="0">短信</option>
						<option value="1">邮箱</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">目标地址</label>
					<input type="text" class="form-control" placeholder="目标地址" id="filter-destNo">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--状态--</option>
						<option value="0">待验证</option>
						<option value="1">已验证</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">发送时间</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="发送时间"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">发送时间</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" placeholder="发送时间"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
			</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
			</div>
		</div>
		<table id="page"></table>
	</div>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/sms/checkCode-index.js"></script>
</footer-scripts>
</html>