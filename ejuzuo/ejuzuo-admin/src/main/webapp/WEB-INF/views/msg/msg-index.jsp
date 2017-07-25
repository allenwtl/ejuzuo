<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="msg" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>消息- 消息管理</title>
<style type="text/css">
.content-cell {
	max-width: 150px; overflow: hidden; text-overflow: ellipsis;
}
</style>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;消息管理</li>
		<li class="active">&nbsp;系统消息</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">消息ID</label>
					<input type="text" class="form-control" placeholder="消息ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">标题</label>
					<input type="text" class="form-control" placeholder="标题" id="filter-title">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status" name="status">
						<option value="">--状态--</option>
						<option value="0">无效</option>
						<option value="1">有效</option>
					</select>
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<shiro:hasPermission name="MSG:RECORD:ADD">
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" >
					新增
				</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
	<shiro:hasPermission name="MSG:RECORD:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="MSG:RECORD:ENABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="MSG:RECORD:DISABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	</div>
</body>
<footer-scripts>
<script type="text/template-dot" id="dot-page-detail">
<div class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">内容</label>
			<div class="col-sm-4">
				<textarea class="form-control" rows="3" readonly>{{!it.content || ''}}</textarea>
			</div>
		</div>
	</div>
</script>
<script type="text/template-dot" id="dot-page-comment">
{{!it.content}}
</script>
<script type="text/javascript" src="/resources/scripts/msg/msg-index.js"></script>
</footer-scripts>
</html>