<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="digitalFurniture" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>数字家居</title>
<link href="/resources/vendor/ztree/3.5.18/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;数字家居</li>
	</ol>
	
	<div class="col-sm-4 col-md-3 col-lg-2" style="padding-top: 10px;">
		<ul id="ztree-categories" class="well ztree"></ul>
	</div>
	
	<div class="col-sm-8 col-md-9 col-lg-10">
		<input type="hidden" id="styles" value='${styles }'>
		<input type="hidden" id="spaceCategorys" value='${spaceCategorys }'>
		<input type="hidden" id="spaceSmallCategorys" value='${spaceSmallCategorys }'>
		<input type="hidden" id="brands" value='${brands }'>
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<input type="hidden" id="filter-brand" value=""/>
				<!-- <div class="form-group form-group-sm">
					<label class="sr-only" for="filter-id">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div> -->
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-name">名称</label>
					<input type="text" class="form-control" placeholder="名称" id="filter-name">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-type">家居类型</label>
					<select class="form-control" id="filter-type">
						<option value="4">品牌馆</option>
						<option value="2">定制馆</option>
						<option value="3">进口馆</option>
						<option value="1">家居尾品汇</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-digitalType">类型</label>
					<select class="form-control" id="filter-digitalType">
						<option value>--全部类型--</option>
						<option value="1">家具</option>
						<option value="2">灯具</option>
						<option value="3">饰品</option>
						<option value="4">挂画</option>
						<option value="5">地毯窗帘</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-spaceCategory">空间大类</label>
					<select class="form-control" id="filter-spaceCategory">
					<option value>--空间大类--</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-spaceSmallCategory">空间小类</label>
					<select class="form-control" id="filter-spaceSmallCategory">
					<option value>--空间小类--</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-style">风格</label>
					<select class="form-control" id="filter-style">
						<option value>--风格--</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-shelfStatus">上架状态</label>
					<select class="form-control" id="filter-shelfStatus">
						<option value>--上架状态--</option>
						<option value="0">未上架</option>
						<option value="1">已上架</option>
						<option value="2">已下架</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value>--状态--</option>
						<option value="0">无效</option>
						<option value="1">有效</option>
					</select>
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<shiro:hasPermission name="DIGITAL:ADD">
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" disabled >
					新增
				</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="DIGITAL:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DIGITAL:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DIGITAL:SHANGJIA">
	<input type="hidden" id="hasPermOfShangjia" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DIGITAL:XIAJIA">
	<input type="hidden" id="hasPermOfXiajia" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DIGITAL:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DIGITAL:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="DIGITAL:DOWNLOAD">
	<input type="hidden" id="hasPermOfDownload" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/digitalFurniture/digitalFurniture-index.js"></script>
</footer-scripts>
</html>