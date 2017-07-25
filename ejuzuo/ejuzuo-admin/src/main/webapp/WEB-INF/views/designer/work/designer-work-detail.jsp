<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="designer" scope="request"/>
<c:set var="nav_second" value="designer-work" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>详情 - 设计师作品</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;设计师作品</li>
		<li class="active">&nbsp;详情</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="form-horizontal">
			<input type="hidden" id="styles" value='${styles }'>
			<input type="hidden" id="designerWorkStyles" value='${designerWork.style }'>
			<input type="hidden" id="domainImage" value="${domainImage }">
			<input type="hidden" id="content" value='${designerWork.content }'>
			<div class="form-group">
				<label class="col-sm-2 control-label">作品ID</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designerWork.id }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">用户ID</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designerWork.memberId }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">作品名称</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designerWork.name }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">风格</label>
				<div class="col-sm-3">
					<p class="form-control-static" id="detail-style">
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">封面图片</label>
				<div class="col-sm-8">
					<div class="thumbnail">
						<img src="${domainImage }${designerWork.coverImg }" alt="封面图片">
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">浏览量</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designerWork.viewCount }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">上传时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${designerWork.uploadTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">编辑状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:choose>
							<c:when test="${designerWork.editStatus==0 }">暂存</c:when>
							<c:when test="${designerWork.editStatus==1 }">发布</c:when>
						</c:choose>
					</p>
				</div>
				<label class="col-sm-2 control-label">状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:choose>
							<c:when test="${designerWork.status==0 }">无效</c:when>
							<c:when test="${designerWork.status==1 }">有效</c:when>
						</c:choose>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${designerWork.createTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">修改时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${designerWork.updateTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">简介</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designerWork.brief }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" >内容</label>
				<div class="col-sm-8" id="contentDiv">
				</div>
			</div>
		</div>
	</div>
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/designer/work/designer-work-detail.js"></script>
</footer-scripts>
</html>
