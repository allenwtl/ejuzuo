<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="nav_first" value="news" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>新增 - 内容管理</title>
<link href="//cdn.bootcss.com/bootstrap-colorpicker/2.3.0/css/bootstrap-colorpicker.min.css" rel="stylesheet">
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;新闻资讯</li>
		<li class="active">&nbsp;新增</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<form class="tab-pane form-horizontal" id="form-add" 
					action="/news/add"
					method="post" autocomplete="off">
			
			<input type="hidden" id="jq-domain-image" value="${domainNewsImage }"/>
			<input type="hidden" id="jq-domain-path" value="img/news" >
			<input type="hidden" id="jq-domain-relatedType" value="1" >
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>类别
				</label>
				<div class="col-sm-8">
					<select class="form-control" name="category" id="category" required>
						<option value="0">新闻资讯</option>
						<option value="1">网站信息</option>
						<option value="2">品牌介绍</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>标题
				</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="title" required/>
				</div>
			</div>
			<div class="form-group" id="thumbnailDiv">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>标题图片<span class="text-danger">（建议160*120）</span>
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<span class="input-group-addon" id="basic-addon1"><c:out value="${domainNewsImage }"/></span>
					  	<input type="text" class="form-control jq-val-upload-img" id="form-insert" name="thumbnail" 
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
				<label class="col-sm-2 control-label">
					资讯来源
				</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="source"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>简介
				</label>
				<div class="col-sm-8">
					<textarea rows="5" cols="" class="form-control" name="brief" required></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					备注
				</label>
				<div class="col-sm-8">
					<textarea rows="5" cols="" class="form-control" name="remark" ></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>内容
				</label>
				<div class="col-sm-8">
					<textarea name="content" id="form-content" rows="10" required></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="submit" class="btn btn-info" id="form-btn-save-draft" name="saveDraft" value="保存" data-loading-text="暂存中...">
					<a role="button" class="btn btn-default" href="/news">返回</a>
				</div>
			</div>
		</form>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/news/news-contents-add.js"></script>
</footer-scripts>
</html>
