<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="designer" scope="request"/>
<c:set var="nav_second" value="designer-work" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>设计师作品</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;设计师作品</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
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
					<label class="sr-only" for="filter-merchantName">作品名称</label>
					<input type="text" class="form-control" placeholder="作品名称" id="name">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-editStatus">编辑状态</label>
					<select class="form-control" id="editStatus">
						<option value>--编辑状态--</option>
						<option value="0">暂存</option>
						<option value="1">发布</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="status">
						<option value>--状态--</option>
						<option value="0">无效</option>
						<option value="1">有效</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-startTime">上传时间</label>
					<div class="input-group date" id="filter-beginUploadTime-group">
		                <input type="text" class="form-control" size="10" id="beginUploadTime" placeholder="上传时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					-
					<label class="sr-only" for="filter-endTime">上传时间</label>
					<div class="input-group date" id="filter-endUploadTime-group">
		                <input type="text" class="form-control" size="10" id="endUploadTime" placeholder="上传时间"/>
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
		<input type="hidden" id="styles" value='${styles }'>
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="DESIGNER:WORK:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DESIGNER:WORK:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DESIGNER:WORK:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/designer/work/designer-work-index.js"></script>
</footer-scripts>
</html>