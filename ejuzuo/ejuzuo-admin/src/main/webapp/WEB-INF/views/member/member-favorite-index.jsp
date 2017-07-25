<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member-favorite" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户收藏</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户收藏</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" >用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">对象类型</label>
					<select class="form-control" id="filter-objectType">
						<option value="" >--对象类型--</option>
						<option value="0">数字家居</option>
						<option value="1">设计作品</option>
						<option value="2">活动</option>
						<option value="3">资讯</option>
						<option value="4">设计师</option>
						<option value="5">充值订单</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">对象ID</label>
					<input type="text" class="form-control" placeholder="对象ID" id="filter-objectId">
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
<script type="text/javascript" src="/resources/scripts/member/member-favorite-index.js"></script>
</footer-scripts>
</html>