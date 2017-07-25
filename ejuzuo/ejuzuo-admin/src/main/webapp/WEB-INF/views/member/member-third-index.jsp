<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member-third" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>第三方用户信息</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;第三方用户信息</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-memberId">用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-thirdType">第三方</label>
					<select class="form-control" id="filter-thirdType">
						<option value="" >--第三方--</option>
						<option value="1">微信</option>
						<option value="0">QQ</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-thirdId">第三方ID</label>
					<input type="text" class="form-control" placeholder="第三方ID" id="filter-thirdId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-beginDate">创建时间</label>
					<div class="input-group date" id="filter-beginDate-group">
		                <input type="text" class="form-control" size="10" id="beginDate" placeholder="创建时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					-
					<label class="sr-only" for="filter-endDate">创建时间</label>
					<div class="input-group date" id="filter-endDate-group">
		                <input type="text" class="form-control" size="10" id="endDate" placeholder="创建时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
				</div>
				<shiro:hasPermission name="MEMBER:THIRD:INFO">
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
	</div>
	
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/member/member-third-index.js"></script>
</footer-scripts>
</html>