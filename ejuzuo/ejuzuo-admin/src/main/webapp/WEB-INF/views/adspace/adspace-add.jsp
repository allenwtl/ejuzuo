<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="adspace" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>新增 - 广告位</title>

</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;广告位</li>
		<li class="active">&nbsp;新增</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="form-horizontal">
			<form class="form-group" id="form-add" action="/adspace/add/"
			method="post" novalidate="novalidate" autocomplete="off">
			<div class="form-group">
				<label class="col-sm-2 control-label">页面位置</label>
				<div class="col-sm-8">
					<select class="form-control" name="pageCode" required>
						<option value=""></option>
						<c:forEach items="${pageCodeTypes }" var="pageCodeType">
							<option value="${pageCodeType.index }"><c:out value="${pageCodeType.description}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">广告位名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="name" required/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">帮助代码</label>
				<div class="col-sm-8">
					<textarea class="form-control" rows="10" name="tip" ></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" id="form-submit" value="新增"/>
					<a role="button" class="btn btn-default" href="/adspace">返回</a>
					<!-- <input type="reset" class="btn btn-default" id="form-reset" value="重置"/> -->
				</div>
			</div>
		</form>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/adspace/adspace-add.js"></script>
</footer-scripts>
</html>
