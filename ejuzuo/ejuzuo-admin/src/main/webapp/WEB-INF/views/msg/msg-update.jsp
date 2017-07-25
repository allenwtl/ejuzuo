<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="nav_first" value="msg" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>修改 - 系统消息</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统消息</li>
		<li class="active">&nbsp;修改</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form class="tab-pane form-horizontal" id="updateSystemMsgForm" method="post" autocomplete="off">
			<input type="hidden" value="${msgRecord.id}" name="id">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-add-status">消息类型</label>
				<div class="col-sm-4">
					<p class="form-control-static">
						<c:if test="${msgRecord.msgType == 1 }">个人消息</c:if>
						<c:if test="${msgRecord.msgType == 0 }">系统消息</c:if>
					</p>
				</div>
				<div id="memberIdDiv">
					<label for="title" class="control-label col-sm-2">用户ID</label>
					<div class=" col-sm-4">
						<p class="form-control-static">
							<c:out value="${msgRecord.memberId }"/>
						</p>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="title" class="control-label col-sm-2">标题</label>
				<div class=" col-sm-4">
					<input type="text" class="form-control" name="title" value="${msgRecord.title }" maxlength="20">
				</div>
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>状态
				</label>
				<div class="col-sm-4">
					<p class="form-control-static">
						<c:if test="${msgRecord.status == 1 }">有效</c:if>
						<c:if test="${msgRecord.status == 0 }">无效</c:if>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label for="title" class="control-label col-sm-2">内容</label>
				<div class=" col-sm-8">
					<textarea name="content" id="form-content" rows="3" required><c:out value="${msgRecord.content }"/></textarea>
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
<script src="/resources/scripts/msg/msg-update.js"></script>
</footer-scripts>
</html>
