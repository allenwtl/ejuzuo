<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="hq" scope="request"/>
<c:set var="nav_second" value="stock-code" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>购物车</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;购物车</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" data-toggle="modal" data-target="#addModal">
					新增
				</button>
			</div>
		</div>
		<table id="page"></table>
	</div>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/member/shoppingcar-index.js"></script>
</footer-scripts>
</html>