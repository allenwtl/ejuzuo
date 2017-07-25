<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="news" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>新闻资讯</title>
</head>
<body>
		<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;新闻资讯</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only">新闻类别</label>
					<select class="form-control" id="category">
						<option value>--新闻类别--</option>
						<option value="0">新闻资讯</option>
						<option value="1">网站信息</option>
						<option value="2">品牌介绍</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">标题</label>
					<input type="text" class="form-control" placeholder="标题" id="title">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">发布人</label>
					<input type="text" class="form-control" placeholder="发布人" id="publisher">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">编辑状态</label>
					<select class="form-control" id="editStatus">
						<option value>--编辑状态--</option>
						<option value="0">暂存</option>
						<option value="1">发布</option>
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
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-beginDate">发布时间</label>
					<div class="input-group date" id="filter-publishBeginDate-group">
		                <input type="text" class="form-control" size="10" id="publishBeginDate" placeholder="发布时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					-
					<label class="sr-only" for="filter-endDate">发布时间</label>
					<div class="input-group date" id="filter-publishEndDate-group">
		                <input type="text" class="form-control" size="10" id="publishEndDate" placeholder="发布时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" >
					新增
				</button>
			</div>
		</div>
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="NEWS:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="NEWS:FABU">
	<input type="hidden" id="hasPermOfFabu" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="NEWS:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="NEWS:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="NEWS:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/news/news-index.js"></script>
</footer-scripts>
</html>