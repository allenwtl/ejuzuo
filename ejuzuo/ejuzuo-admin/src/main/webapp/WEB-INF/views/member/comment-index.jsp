<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="comment" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>评论管理</title>
<style type="text/css">
.content-cell {
	max-width: 350px; overflow: hidden; text-overflow: ellipsis;
}
</style>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;评论管理</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
	
		<form id="page-toolbar" class="form-inline" role="form" autocomplete="off" style="line-height:34px;">
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-id">评论ID</label>
				<input type="text" class="form-control" name="id" id="filter-id" placeholder="评论ID">
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-objectId">对象类型</label>
				<select class="form-control" id="filter-objectId">
					<option value="">--对象类型--</option>
					<option value="0">数字家居</option>
					<option value="1">设计作品</option>
					<option value="2">活动</option>
					<option value="3">资讯</option>
					<option value="4">设计师</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-objectId">对象ID</label>
				<input type="text" class="form-control" name="objectId" id="filter-objectId" placeholder="对象ID">
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-userName">用户ID</label>
				<input type="text" class="form-control" name="memberId" id="filter-memberId" placeholder="用户ID">
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-status">状态</label>
				<select class="form-control" id="filter-status" name="status">
					<option value="">--状态--</option>
					<option value="0">无效</option>
					<option value="1">有效</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-masked">是否屏蔽</label>
				<select class="form-control" id="filter-masked" name="masked">
					<option value="">--是否屏蔽--</option>
					<option value="0">未屏蔽</option>
					<option value="1">已屏蔽</option>
				</select>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">开始日期</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" name="beginDate" placeholder="开始日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">结束日期</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" name="endDate" placeholder="结束日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
			</div>
			<button type="button" class="btn btn-sm btn-primary" id="filter-submit">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
			</button>
		</form>
		<table id="page"></table>
	<shiro:hasPermission name="COMMENT:MASKED">
	<input type="hidden" id="hasPermOfMasked" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="COMMENT:UNMASKED">
	<input type="hidden" id="hasPermOfUnmasked" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="COMMENT:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="COMMENT:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	</div>
</body>
<footer-scripts>
<script type="text/template-dot" id="dot-page-detail">
	<div class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">评论内容</label>
			<div class="col-sm-8">
				<textarea class="form-control" rows="3" readonly>{{!it.comment || ''}}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">备注</label>
			<div class="col-sm-8">
				<textarea class="form-control" rows="1" readonly>{{!it.remark || ''}}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">更新者</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" readonly value="{{!it.updator || ''}}"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">更新时间</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" readonly value="{{!it.updateTime || ''}}"/>
			</div>
		</div>
	</div>
</script>
<script type="text/template-dot" id="dot-page-comment">
{{!it.comment}}
</script>
<script src="/resources/scripts/member/comment-index.js"></script>
</footer-scripts>
</html>
