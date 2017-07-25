<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="activity" scope="request"/>
<c:set var="nav_second" value="activity-enroll" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>活动报名</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;活动报名</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">活动ID</label>
					<input type="text" class="form-control" value="${activityId }" placeholder="活动ID" id="filter-activityId">
				</div>
				<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">报名时间</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="报名时间"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">报名时间</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" placeholder="报名时间"/>
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
<script type="text/javascript" src="/resources/scripts/activity/enroll/activityEnroll-index.js"></script>
</footer-scripts>
</html>