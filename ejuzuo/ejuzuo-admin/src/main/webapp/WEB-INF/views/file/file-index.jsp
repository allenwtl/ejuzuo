<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="file" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>文件</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;文件</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only">ID</label>
					<input type="text" class="form-control" placeholder="ID" id="filter-id">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">文件名</label>
					<input type="text" class="form-control" placeholder="文件名" id="filter-fileName">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">扩展名</label>
					<input type="text" class="form-control" placeholder="扩展名" id="filter-ext">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">关联类型</label>
					<select class="form-control" id="filter-relatedType">
						<option value="" >--关联类型--</option>
						<option value="0">无</option>
						<option value="1">内容</option>
						<option value="2">数字家居</option>
						<option value="3">设计作品</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" >--状态--</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only">上传者</label>
					<input type="text" class="form-control" placeholder="上传者" id="filter-uploader">
				</div>
				<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">上传日期</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="上传日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">上传日期</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" placeholder="上传日期"/>
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
	<shiro:hasPermission name="FILE:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<!-- 详情 -->
	<div class="modal fade bs-example-modal-lg" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<!-- head -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" myModalLabel">详情</h4>
				</div>
				<div class="modal-body" id="model-detail-body">
				</div>
				<!-- body -->
				<!-- footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
				<!-- footer -->
			</div>
		</div>
	</div>
</body>
<footer-scripts>
<script type="text/template-dot" id="dot-detail">
	<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-2">文件名</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.fileName}}</p>
							</div>
							<label class="control-label col-sm-2">文件大小</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.size}}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">路径</label>
							<div class=" col-sm-10">
								<p class="form-control-static">{{=it.path}}</p>
								<div class="thumbnail {{!it.hidden || ''}}">
									<img src="{{!it.image || 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7'}}">
				    			</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">扩展名</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.ext}}</p>
							</div>
							<label class="control-label col-sm-2">状态</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.status}}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">上传者</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.uploader}}</p>
							</div>
							<label class="control-label col-sm-2">上传时间</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.uploadTime}}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">关联类型</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.relatedType}}</p>
							</div>
							<label class="control-label col-sm-2">关联ID</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.relatedId}}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">创建时间</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.createTime}}</p>
							</div>
							<label class="control-label col-sm-2">修改时间</label>
							<div class=" col-sm-4">
								<p class="form-control-static">{{=it.updateTime}}</p>
							</div>
						</div>
					</div>
</script>
<script type="text/javascript" src="/resources/scripts/file/file-index.js"></script>
</footer-scripts>
</html>