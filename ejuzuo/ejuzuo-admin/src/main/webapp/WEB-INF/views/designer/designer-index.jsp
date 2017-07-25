<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="designer" scope="request"/>
<c:set var="nav_second" value="designer" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>设计师</title>
<link href="/resources/vendor/ztree/3.5.18/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<style type="text/css">
.overflow-cell {
	max-width: 350px; overflow: hidden; text-overflow: ellipsis;
}
</style>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;设计师</li>
	</ol>
	
	<div class="col-sm-4 col-md-3 col-lg-2" style="padding-top: 10px;">
		<ul id="ztree-categories" class="well ztree"></ul>
	</div>
	
	<div class="col-sm-8 col-md-9 col-lg-10">
		<input type="hidden" value='${lstTree }' id="lstTree">
		<input type="hidden" value='${styles }' id="styles">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<input type="hidden" id="filter-designerType" value=""/>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">名称</label>
					<input type="text" class="form-control" placeholder="名称" id="name">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="status">
						<option value>--状态--</option>
						<option value="0">暂存</option>
						<option value="1">待审核</option>
						<option value="2">审核通过</option>
						<option value="3">审核退回</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-startTime">创建时间</label>
					<div class="input-group date" id="filter-startTime-group">
		                <input type="text" class="form-control" size="10" id="startTime" placeholder="创建时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					-
					<label class="sr-only" for="filter-endTime">创建时间</label>
					<div class="input-group date" id="filter-endTime-group">
		                <input type="text" class="form-control" size="10" id="endTime" placeholder="创建时间"/>
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

	<shiro:hasPermission name="DESIGNER:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DESIGNER:APPROVE">
	<input type="hidden" id="hasPermOfApprove" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/designer/designer-index.js"></script>
</footer-scripts>
</html>