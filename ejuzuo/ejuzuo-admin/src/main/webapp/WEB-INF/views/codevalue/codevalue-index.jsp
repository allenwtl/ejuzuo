<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="codevalue" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>码表</title>
<link href="/resources/vendor/ztree/3.5.18/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;码表</li>
	</ol>
	<div class="col-sm-4 col-md-3 col-lg-2" style="padding-top: 10px;">
		<ul id="ztree-categories" class="well ztree"></ul>
	</div>
	<div class="col-sm-8 col-md-9 col-lg-10">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<input type="hidden" id="filter-collectionCode" value="">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-valueCode">值代码</label>
					<input type="text" class="form-control" placeholder="值代码" id="filter-valueCode">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-valueName">值名称<</label>
					<input type="text" class="form-control" placeholder="值名称" id="filter-valueName">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--状态--</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</div>
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				&nbsp;
				<shiro:hasPermission name="CODEVALUE:ADD">
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" data-toggle="modal" disabled data-target="#addModal">
					新增
				</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
		<input type="hidden" value='${brands }' id="brands">
		
	<shiro:hasPermission name="CODEVALUE:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="CODEVALUE:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="CODEVALUE:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	</div>
	
	<!-- 新增彈出層 -->
	<div class="modal fade bs-example-modal-lg" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<!-- head -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增</h4>
				</div>
				<!-- head -->
				<!-- body -->
				<div class="modal-body">
					<form class="form-horizontal" id="createForm" method="POST" enctype="multipart/form-data">
						<div class="form-group">
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>集合代码</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" id="add-collectionCode" name="collectionCode" required readonly>
							</div>
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>值代码</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" name="valueCode" required>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>值名称</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" name="valueName" required>
							</div>
							<label for="title" class="control-label col-sm-2">父代码</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" name="parentCode">
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>排序</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" required name="orderNo" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" value="0" >
							</div>
							<label class="col-sm-2 control-label" for="form-add-status"><span class="text-danger">*</span>状态</label>
							<div class="col-sm-4">
								<select class="form-control" name="status" required>
									<option value="1">有效</option>
									<option value="0">无效</option>
								</select>
							</div>
						</div>
						<div class="form-group" id="create-brandsDiv"></div>
					</form>
				</div>
				<!-- body -->
				<!-- footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveButton">保存</button>
				</div>
				<!-- footer -->
			</div>
		</div>
	</div>
	<!-- 新增弹出层 end-->
	
	<!-- 修改弹出层 -->
	<div class="modal fade bs-example-modal-lg" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<!-- head -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" myModalLabel">修改</h4>
				</div>
				<!-- head -->
				<!-- body -->
				<div class="modal-body">
					<form class="form-horizontal" id="updateForm" method="POST" enctype="multipart/form-data" autocomplete="off">
						<div class="form-group">
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>集合代码</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" id="collectionCode" name="collectionCode" required readonly>
							</div>
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>值代码</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" id="valueCode" required readonly name="valueCode">
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>值名称</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" id="valueName" required name="valueName">
							</div>
							<label for="title" class="control-label col-sm-2">父代码</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" id="parentCode" name="parentCode">
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2"><span class="text-danger">*</span>排序</label>
							<div class=" col-sm-4">
								<input type="text" class="form-control" id="orderNo" required name="orderNo" value="0" >
							</div>
							<label class="col-sm-2 control-label" for="form-add-status"><span class="text-danger">*</span>状态</label>
							<div class="col-sm-4">
								<select class="form-control" id="status" name="status" required>
									<option value="1">有效</option>
									<option value="0">无效</option>
								</select>
							</div>
						</div>
						<div class="form-group" id="update-brandsDiv"></div>
					</form>
				</div>
				<!-- body -->
				<!-- footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateButton">修改</button>
				</div>
				<!-- footer -->
			</div>
		</div>
	</div>
	<!-- 修改弹出层end -->
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/codevalue/codevalue-index.js"></script>
</footer-scripts>
</html>