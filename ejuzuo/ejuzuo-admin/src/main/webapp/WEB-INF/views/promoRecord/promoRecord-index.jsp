<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member-promoRecord" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户推广记录</title>
<style type="text/css">
.content-cell {
	max-width: 350px; overflow: hidden; text-overflow: ellipsis;
}
</style>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户推广记录</li>
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
					<label class="sr-only" for="filter-merchantName">推广代码</label>
					<input type="text" class="form-control" placeholder="推广代码" id="filter-promoCode">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--状态--</option>
						<option value="1">已处理</option>
						<option value="0">未处理</option>
					</select>
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
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
			</div>
		</div>
		<table id="page"></table>
	</div>
</body>
<footer-scripts>
<script type="text/template-dot" id="dot-page-detail">
<div class="form-horizontal">
	<div class="form-group">
		<label class="col-sm-2 control-label">发起信息</label>
		<div class="col-sm-8">
			<textarea class="form-control" rows="3" readonly>{{!it.promoInfo || ''}}</textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">点击信息</label>
		<div class="col-sm-8">
			<textarea class="form-control" rows="3" readonly>{{!it.callbackInfo || ''}}</textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">更新时间</label>
		<div class="col-sm-8">
			<p class="form-control-static">
				{{!it.updateTime || ''}}
			</p>
		</div>
	</div>
</div>
</script>
<script type="text/template-dot" id="dot-page-promoInfo">
{{!it.promoInfo}}
</script>
<script type="text/template-dot" id="dot-page-callbackInfo">
{{!it.callbackInfo}}
</script>
<script type="text/javascript" src="/resources/scripts/promoRecord/promoRecord-index.js"></script>
</footer-scripts>
</html>