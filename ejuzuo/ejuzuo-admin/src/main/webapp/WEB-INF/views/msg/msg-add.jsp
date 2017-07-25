<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="nav_first" value="msg" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>新增 - 系统消息</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统消息</li>
		<li class="active">&nbsp;新增</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form class="tab-pane form-horizontal" id="addSystemMsgForm" method="post" autocomplete="off">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-add-status">消息类型</label>
				<div class="col-sm-4">
					<input type="radio" name="msgType" value="0" checked required>系统消息&nbsp;&nbsp;
					<input type="radio" name="msgType" value="1">个人消息
				</div>
				<div id="memberIdDiv">
					<label for="title" class="control-label col-sm-2">用户ID</label>
					<div class=" col-sm-4">
						<input type="number" class="form-control" required name="memberId" maxlength="20">
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="title" class="control-label col-sm-2">标题</label>
				<div class=" col-sm-4">
					<input type="text" class="form-control" name="title" maxlength="20">
				</div>
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>状态
				</label>
				<div class="col-sm-4">
					<select class="form-control" name="status" required>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="title" class="control-label col-sm-2">内容</label>
				<div class=" col-sm-8">
					<textarea name="content" id="form-content" rows="3" required></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="button" class="btn btn-info" id="saveButton" value="保存">
					<a role="button" class="btn btn-default" href="/msg/record">返回</a>
				</div>
			</div>
		</form>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/msg/msg-add.js"></script>
</footer-scripts>
</html>
