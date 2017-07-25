<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="brand" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>品牌</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;品牌</li>
	</ol>
	<input type="hidden" id="jq-domain-image" value="${domainImage }"/>
	<input type="hidden" id="jq-domain-path" value="img/brand" >
	<input type="hidden" id="jq-domain-relatedType" value="4" >
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">品牌名称</label>
					<input type="text" class="form-control" placeholder="品牌名称" id="filter-name">
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
				<shiro:hasPermission name="BRAND:ADD">
				<button type="button" class="btn btn-primary btn-sm" id="btn-add" data-toggle="modal" data-target="#addModal">
					新增
				</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="page"></table>
	</div>
	<shiro:hasPermission name="BRAND:UPDATE">
	<input type="hidden" id="hasPermOfUpdate" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="BRAND:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="BRAND:DISABLE">
	<input type="hidden" id="hasPermOfDisable" value="true"/>
	</shiro:hasPermission>
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
					<h4 class="modal-title" id="myModalLabel">新增合作品牌</h4>
				</div>
				<!-- head -->
				<!-- body -->
				<div class="modal-body">
					<form class="form-horizontal" id="createForm" method="POST" enctype="multipart/form-data">
						<div class="form-group">
							<label for="title" class="control-label col-sm-2">品牌名称</label>
							<div class=" col-sm-10">
								<input type="text" class="form-control" name="name" maxlength="20" required>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2">图片<span class="text-danger">（建议129*49）</span></label>
							<div class=" col-sm-10">
								<div class="input-group">
							  	<span class="input-group-addon" id="basic-addon1"><c:out value="${domainImage }"/></span>
							  	<input type="text" class="form-control jq-val-upload-img" id="form-insert" name="logoImg" 
							  		data-type="insert" 
									placeholder="relative path of image" readonly required />
								</div>
								<input type="file" class="jq-file-upload-img"
									id="form-file-insert"
									data-btn="form-btn-insert" 
									data-clear="form-btn-clear-insert"/>
								<input type="button" class="btn btn-sm btn-primary jq-btn-upload-img" id="form-btn-insert" value="上传" 
									data-loading-text="上传中..." 
									data-field="form-insert" 
									data-file="form-file-insert" 
									data-form="form-upload-insert" disabled>
								<input type="button" class="btn btn-sm btn-default jq-btn-clear-upload-img" id="form-btn-clear-insert" value="清除" 
									data-loading-text="清除中..." 
									data-field="form-insert" 
									data-file="form-file-insert" 
									data-btn="form-btn-insert" disabled>
								<div class="thumbnail hidden" id="jq-upload-thumbnail-insert">
									<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" 
										id="jq-upload-thumbnail-img-insert" >
							    </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-add-status">状态</label>
							<div class="col-sm-3">
								<select class="form-control" name="status" required>
									<option value="1">有效</option>
									<option value="0">无效</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-add-status">优先品牌</label>
							<div class="col-sm-3">
								<input type="checkbox" name="preferred" id="preferred" value="0">
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2">备注</label>
							<div class=" col-sm-10">
								<input type="text" class="form-control" name="remark">
							</div>
						</div>
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
					<h4 class="modal-title" myModalLabel">修改合作品牌</h4>
				</div>
				<!-- head -->
				<!-- body -->
				<div class="modal-body">
					<form class="form-horizontal" id="updateForm" method="POST" enctype="multipart/form-data">
						<div class="form-group">
							<label for="title" class="control-label col-sm-2">品牌名称</label>
							<div class=" col-sm-10">
								<input type="text" class="form-control" id="name" name="name" maxlength="20" required>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2">图片<span class="text-danger">（建议129*49）</span></label>
							<div class=" col-sm-10">
								<div class="input-group">
								  	<span class="input-group-addon" id="basic-addon1"><c:out value="${domainImage }"/></span>
								  	<input type="text" class="form-control jq-val-upload-img" id="form-update" name="logoImg" 
								  		data-type="update" 
										placeholder="relative path of image" readonly required />
								</div>
								<input type="file" class="jq-file-upload-img"
									id="form-file-update"
									data-btn="form-btn-update" 
									data-clear="form-btn-clear-update"/>
								<input type="button" class="btn btn-sm btn-primary jq-btn-upload-img" id="form-btn-update" value="上传" 
									data-loading-text="上传中..." 
									data-field="form-update" 
									data-file="form-file-update" 
									data-form="form-upload-update" disabled>
								<input type="button" class="btn btn-sm btn-default jq-btn-clear-upload-img" id="form-btn-clear-update" value="清除" 
									data-loading-text="清除中..." 
									data-field="form-update" 
									data-file="form-file-update" 
									data-btn="form-btn-update" disabled>
								<div class="thumbnail hidden" id="jq-upload-thumbnail-update">
									<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" 
										id="jq-upload-thumbnail-img-update" >
							    </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-add-status">状态</label>
							<div class="col-sm-3">
								<select class="form-control" id="status" name="status" required>
									<option value="1">有效</option>
									<option value="0">无效</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-sm-2">备注</label>
							<div class=" col-sm-10">
								<input type="text" class="form-control" id="remark" name="remark">
							</div>
						</div>
						<input type="hidden" name="id" id="id"/> 
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
<script type="text/javascript" src="/resources/scripts/brand/brand-index.js"></script>
</footer-scripts>
</html>