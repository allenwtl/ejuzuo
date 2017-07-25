<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="user-log" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户行为日志 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li class="active">&nbsp;用户行为日志</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<form id="page-toolbar" class="form-inline" role="form" autocomplete="off" style="line-height:34px;">
			
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-userName">姓名</label>
				<input type="text" class="form-control" id="filter-userName" placeholder="姓名"/>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-action">操作描述</label>
				<input type="text" class="form-control" id="filter-action" placeholder="操作描述"/>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-status">返回状态</label>
				<select class="form-control" id="filter-status">
					<option value="" selected="selected">--返回状态--</option>
					<option value="NORMAL">正常返回</option>
					<option value="FAILED">失败返回</option>
					<option value="EXCEPTION">异常返回</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">开始日期</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="开始日期"/>
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
	
	<!-- Modal -->
	<div class="modal fade" id="model-detail" tabindex="-1" role="dialog"
		aria-labelledby="model-detail-Label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="model-detail-Label">详情</h4>
				</div>
				<div class="modal-body" id="model-detail-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
				</div>
			</div>
		</div>
	</div>
	
</body>
<footer-scripts>
	<script type="text/template-dot" id="dot-detail">
	<div class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">操作用户</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.userName}}</p>
			</div>
			<label class="col-sm-2 control-label">返回状态</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.status}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">操作描述</label>
			<div class="col-sm-10">
				<p class="form-control-static">{{=it.action}}</p>
			</div>
			<label class="col-sm-2 control-label">操作时间</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.logDate}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">操作URI</label>
			<div class="col-sm-10">
				<p class="form-control-static">{{=it.uri}}</p>
			</div>
			
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">操作参数</label>
			<div class="col-sm-10">
				<textarea class="form-control" rows="5" readonly>{{=it.params || '-'}}</textarea>
			</div>
		</div>
	</div>
	</script>
<script type="text/javascript" src="/resources/scripts/system/user-log.js"></script>
</footer-scripts>
</html>
