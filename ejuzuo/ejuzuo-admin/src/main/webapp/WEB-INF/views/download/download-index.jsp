<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="download" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>下载管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;下载管理</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">"用户ID"</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">文件ID</label>
					<input type="text" class="form-control" placeholder="文件ID" id="filter-fileId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">ID</label>
					<input type="text" class="form-control" placeholder="商品ID" id="filter-goodsId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--状态--</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-payStatus">支付状态</label>
					<select class="form-control" id="filter-payStatus">
						<option value="" >--支付状态--</option>
						<option value="1">已支付</option>
						<option value="0">未支付</option>
					</select>
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
			<label class="col-sm-2 control-label">用户昵称</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" readonly value="{{!it.nickName || ''}}"/>
			</div>
			<label class="col-sm-2 control-label">文件名称</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" readonly value="{{!it.fileName || ''}}"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">创建时间</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" readonly value="{{!it.createTime || ''}}"/>
			</div>
			<label class="col-sm-2 control-label">更新时间</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" readonly value="{{!it.updateTime || ''}}"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">备注</label>
			<div class="col-sm-3">
				<textarea class="form-control" rows="1" readonly>{{!it.remark || ''}}</textarea>
			</div>
		</div>
	</div>
</script>
<script type="text/javascript" src="/resources/scripts/download/download-index.js"></script>
</footer-scripts>
</html>