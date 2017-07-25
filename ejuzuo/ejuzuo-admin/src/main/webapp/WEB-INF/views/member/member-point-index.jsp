<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member-point" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户积分 - 用户管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户管理</li>
		<li class="active">&nbsp;用户积分</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<form id="page-toolbar" class="form-inline" role="form" autocomplete="off" style="line-height:34px;">
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-memberId">用户ID</label>
				<input type="number" class="form-control" id="filter-memberId" placeholder="用户ID"/>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">开始日期</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="开始日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">结束日期</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" placeholder="结束日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
			</div>
			
			<button type="button" class="btn btn-primary btn-sm" id="filter-submit" data-loading-text="查找中...">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
			</button>
			
		</form>
		
		<table id="page"></table>
		<p class="bg-warning text-center page-warning" id="page-tip">请设置条件进行查询</p>
	</div>
	<shiro:hasPermission name="MEMBER:POINT:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/member/member-point-index.js"></script>
</footer-scripts>
</html>