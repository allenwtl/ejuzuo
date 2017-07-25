<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="user-oper-log" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户操作日志 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li class="active">&nbsp;用户操作日志</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<form id="page-toolbar" class="form-inline" role="form" autocomplete="off" style="line-height:34px;">
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-operType">操作类型</label>
				<select class="form-control" id="filter-operType" name="operType">
					<option value="">--操作类型--</option>
					<c:forEach items="${operTypes }" var="item">
						<option value="${item.index }"><c:out value="${item.description }"/></option>
					</c:forEach>
				</select>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-account">账号</label>
				<input type="text" class="form-control" id="filter-account" name="account" placeholder="账号"/>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">开始日期</label>
				<div class="input-group date" id="filter-beginDate-group">
					<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="开始日期" 
	                	value="<fmt:formatDate value="${now }" pattern="yyyy-MM-dd"/>"/>
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
	
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/system/user-oper-log.js"></script>
</footer-scripts>
</html>
