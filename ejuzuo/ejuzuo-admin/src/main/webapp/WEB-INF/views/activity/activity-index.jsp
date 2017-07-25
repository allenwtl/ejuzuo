<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="activity" scope="request"/>
<c:set var="nav_second" value="activity" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<title>活动</title>
<link href="/resources/vendor/ztree/3.5.18/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;活动</li>
	</ol>
	
	<div class="col-sm-4 col-md-3 col-lg-2" style="padding-top: 10px;">
		<ul id="ztree-categories" class="well ztree"></ul>
	</div>
	
	<div class="col-sm-8 col-md-9 col-lg-10">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<input type="hidden" id="filter-category" value=""/>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">标题</label>
					<input type="text" class="form-control" placeholder="标题" id="title">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">发布人</label>
					<input type="text" class="form-control" placeholder="发布人" id="publisher">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">活动城市</label>
					<select class="form-control" id="city">
						<option value>--活动城市--</option>
						<c:forEach var="city" items="${citys }" varStatus="cityStatus">
							<option value="${city.valueCode }">${city.valueName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">编辑状态</label>
					<select class="form-control" id="editStatus">
						<option value>--编辑状态--</option>
						<option value="0">暂存</option>
						<option value="1">发布</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-beginDate">举办时间</label>
					<div class="input-group date" id="filter-beginDate-group">
		                <input type="text" class="form-control" size="10" id="holdBeginDate" placeholder="举办时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					-
					<label class="sr-only" for="filter-endDate">举办时间</label>
					<div class="input-group date" id="filter-endDate-group">
		                <input type="text" class="form-control" size="10" id="holdEndDate" placeholder="举办时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<shiro:hasPermission name="ACTIVITY:ADD">
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" disabled >
					新增
				</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="ACTIVITY:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="ACTIVITY:FABU">
	<input type="hidden" id="hasPermOfFabu" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="ACTIVITY:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="ACTIVITY:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="ACTIVITY:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="ACTIVITY:ENROLL:INFO">
	<input type="hidden" id="hasPermOfEnroll" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/activity/activity-index.js"></script>
</footer-scripts>
</html>