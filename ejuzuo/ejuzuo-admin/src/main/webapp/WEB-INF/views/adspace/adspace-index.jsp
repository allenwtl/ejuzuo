<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="adspace" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>广告位</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;广告位</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">品牌名称</label>
					<input type="text" class="form-control" placeholder="品牌名称" id="filter-name">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--状态--</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<shiro:hasPermission name="ADSPACE:ADD">
				<a class="btn btn-primary btn-sm" href="/adspace/add/" role="button">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新建
				</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="ADSPACE:IFNO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="ADSPACE:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/adspace/adspace-index.js"></script>
</footer-scripts>
</html>