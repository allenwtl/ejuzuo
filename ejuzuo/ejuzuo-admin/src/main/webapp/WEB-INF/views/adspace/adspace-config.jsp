<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="nav_first" value="ad-block" scope="request"/>
<c:set var="nav_second" value="ad" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>配置 - 广告位 - 广告位管理 - CMS后台管理系统</title>
<link rel="stylesheet" href="/resources/vendor/codemirror/5.1.0/codemirror.min.css">
<link rel="stylesheet" href="/resources/vendor/codemirror/5.1.0/theme/monokai.min.css">
<link rel="stylesheet" href="/resources/vendor/codemirror/5.1.0/addon/display/fullscreen.min.css">
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">广告位管理</li>
		<li class="active"><a href="/ad-block/ad/">广告位</a></li>
		<li class="active">配置</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
	
		<form class="form-horizontal" id="form-config" action="/ad-block/ad/${adBlock.id }/conf/"
			method="post" novalidate="novalidate" autocomplete="off">
			
			<div class="form-group">
				<label class="col-sm-2 control-label">帮助代码</label>
				<div class="col-sm-8">
					<textarea class="hidden" id="form-helpCode-def"><c:out value="${adSpace.tip }" /></textarea>
					<textarea class="form-control" rows="10" style="height:500px;" name="tip" id="form-helpCode"><c:out value="${adSpace.tip }" /></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" id="form-submit" value="更新"/>
					<input type="reset" class="btn btn-default" id="form-reset" value="重置"/>
				</div>
			</div>
		</form>
	</div>
</body>

<footer-scripts>
	
</footer-scripts>

</html>
