<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="smsRecord" scope="request"/>
<c:set var="nav_second" value="smsRecord" scope="request"/>

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
					<label class="sr-only">手机号</label>
					<input type="text" class="form-control" placeholder="手机号" id="filter-mobile">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">发送状态</label>
					<select class="form-control" id="filter-sendStatus">
						<option value="" >--发送状态--</option>
						<option value="0">失败</option>
						<option value="1">成功</option>
						<option value="2">发送中</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">号码类型</label>
					<select class="form-control" id="filter-mobileType">
						<option value="" >--号码类型--</option>
						<option value="0">其他</option>
						<option value="1">移动</option>
						<option value="2">联通</option>
						<option value="3">电信</option>
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
<script type="text/javascript" src="/resources/scripts/sms/smsRecord-index.js"></script>
</footer-scripts>
</html>