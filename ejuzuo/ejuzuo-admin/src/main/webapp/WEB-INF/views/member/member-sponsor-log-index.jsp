<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member-sponsor-log" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户赞助</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户赞助</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">用户ID</label>
					<input type="text" class="form-control" placeholder="用户ID" id="filter-memberId">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">用户账号</label>
					<input type="text" class="form-control" placeholder="用户账号" id="filter-account">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">赞助类型</label>
					<select class="form-control" id="filter-sponsorType">
						<option value="" >--赞助类型--</option>
						<option value="0">49元</option>
						<option value="1">99元</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">支付方式</label>
					<select class="form-control" id="filter-paymentMethod">
						<option value="" >--支付方式--</option>
						<option value="0">支付宝支付</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">我方支付订单号</label>
					<input type="text" class="form-control" placeholder="我方支付订单号" id="filter-orderNo">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">支付平台订单号</label>
					<input type="text" class="form-control" placeholder="支付平台订单号" id="filter-payOrderNo">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">支付状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--支付状态--</option>
						<option value="0">待支付</option>
						<option value="1">支付成功</option>
						<option value="2">支付失败</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">支付日期</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="开始日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">支付日期</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" placeholder="结束日期"/>
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
		<label class="col-sm-2 control-label">支付平台请求信息</label>
		<div class="col-sm-4">
			<textarea class="form-control" rows="1" readonly>{{!it.requestInfo || ''}}</textarea>
		</div>
		<label class="col-sm-2 control-label">支付平台响应信息</label>
		<div class="col-sm-4">
			<textarea class="form-control" rows="1" readonly>{{!it.responseInfo || ''}}</textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">过期时间</label>
		<div class="col-sm-4">
			<p class="form-control-static">
				{{!it.expire || ''}}
			</p>
		</div>
		<label class="col-sm-2 control-label">响应时间</label>
		<div class="col-sm-4">
			<p class="form-control-static">
				{{!it.payTime || ''}}
			</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">更新时间</label>
		<div class="col-sm-4">
			<p class="form-control-static">
				{{!it.updateTime || ''}}
			</p>
		</div>
		<label class="col-sm-2 control-label">备注</label>
		<div class="col-sm-4">
			<textarea class="form-control" rows="1" readonly>{{!it.remark || ''}}</textarea>
		</div>
	</div>
</div>
</script>
<script type="text/javascript" src="/resources/scripts/member/member-sponsor-log-index.js"></script>
</footer-scripts>
</html>