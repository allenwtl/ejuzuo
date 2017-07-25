<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="nav_first" value="news" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>编辑 - 内容管理</title>
<link
	href="//cdn.bootcss.com/bootstrap-colorpicker/2.3.0/css/bootstrap-colorpicker.min.css"
	rel="stylesheet">
</head>
<body>

	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;新闻资讯</li>
		<li class="active">&nbsp;编辑</li>
	</ol>

	<div class="col-sm-12 col-md-12 col-lg-12">

		<form class="tab-pane form-horizontal" id="form-edit"
			action="/news/edit" method="post" autocomplete="off">
			<input type="hidden" id="jq-domain-image" value="${domainNewsImage }"/>
			<input type="hidden" id="jq-domain-path" value="img/news" >
			<input type="hidden" id="jq-domain-relatedType" value="1" >
			
			<input type="hidden" name="id" value="${newsInfo.id}"/>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>类别
				</label>
				<div class="col-sm-8">
					<c:choose>
						<c:when test="${newsInfo.category == 0 }">
							<p class="form-control-static">
								新闻资讯
							</p>
						</c:when>
						<c:when test="${newsInfo.category == 1 }">
							<p class="form-control-static">
								网站信息
							</p>
						</c:when>
						<c:otherwise>
							<p class="form-control-static">
								品牌介绍
							</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>标题
				</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="title" value="${newsInfo.title }" required/>
				</div>
			</div>
			<c:if test="${newsInfo.category == 0 }">
			<div class="form-group" id="thumbnailDiv">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>标题图片<span class="text-danger">（建议160*120）</span>
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<span class="input-group-addon" id="basic-addon1"><c:out value="${domainNewsImage }"/></span>
					  	<input type="text" class="form-control jq-val-upload-img" id="form-update" name="thumbnail" 
					  		data-type="update" 
							placeholder="relative path of image" value="${newsInfo.thumbnail }" readonly required />
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
					<div class="thumbnail ${empty newsInfo.thumbnail?'hidden':''}" id="jq-upload-thumbnail-update">
						<img src="${empty newsInfo.thumbnail?'':domainNewsImage}
							${empty newsInfo.thumbnail?'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7':newsInfo.thumbnail}" 
							id="jq-upload-thumbnail-img-update" >
				    </div>
				</div>
			</div>
			</c:if>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					资讯来源
				</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" value="${newsInfo.source }" name="source"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"">
					<span class="text-danger">*</span>简介
				</label>
				<div class="col-sm-8">
					<textarea rows="5" cols="" class="form-control" name="brief" required><c:out value="${newsInfo.brief }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					备注
				</label>
				<div class="col-sm-8">
					<textarea rows="5" cols="" class="form-control" name="remark" ><c:out value="${newsInfo.remark }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" ">
					<span class="text-danger">*</span>内容
				</label>
				<div class="col-sm-8">
					<textarea name="content" id="form-content" rows="10" required><c:out value="${newsInfo.content }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="submit" class="btn btn-info" value="更新" id="form-btn-release-update" data-loading-text="更新...">
					<c:if test="${newsInfo.editStatus == 0 }">
						<input type="submit" class="btn btn-warning" id="form-btn-release" value="发布" data-loading-text="发布中...">
					</c:if>
					<a role="button" class="btn btn-default" href="/news">返回</a>
					<a role="button" target="_blank" class="btn btn-primary" href="http://www.ejuzuo.com/newsInfo/detail/${newsInfo.id }" >预览</a>
				</div>
			</div>
			
		</form>
	</div>
</body>
<footer-scripts> 
<script src="/resources/scripts/news/news-contents-edit.js"></script> </footer-scripts>
</html>
