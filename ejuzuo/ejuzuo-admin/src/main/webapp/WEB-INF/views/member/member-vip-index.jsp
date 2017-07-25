<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member-vip" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>会员 - 用户管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户管理</li>
		<li class="active">&nbsp;会员</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form id="page-toolbar" class="form-inline" role="form" autocomplete="off" style="line-height:34px;">
			<div class="form-group form-group-sm">
				<label class="sr-only">是否VIP</label>
				<select class="form-control" id="filter-viped">
					<option value="" selected="selected">--是否VIP--</option>
					<option value="1"><c:out value="VIP"/></option>
					<option value="0"><c:out value="非VIP"/></option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-memberId">用户ID</label>
				<input type="text" class="form-control" id="filter-memberId" placeholder="用户ID"/>
			</div>
			<button type="button" class="btn btn-primary btn-sm" id="filter-submit" data-loading-text="查找中...">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
			</button>
		</form>
		
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="MEMBER:VIP:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/member/member-vip-index.js"></script>
</footer-scripts>
</html>