<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="adspace" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>详情 - 广告位</title>
<link rel="stylesheet" href="/resources/vendor/codemirror/5.1.0/codemirror.min.css">
<link rel="stylesheet" href="/resources/vendor/codemirror/5.1.0/theme/monokai.min.css">
<link rel="stylesheet" href="/resources/vendor/codemirror/5.1.0/addon/display/fullscreen.min.css">

</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;广告位</li>
		<li class="active">&nbsp;详情</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">页面位置</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${adSpace.pageCode }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">广告位名称</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${adSpace.name }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">是否有效</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${adSpace.status == 1 }">有效</c:if>
						<c:if test="${adSpace.status == 0 }">无效</c:if>
					</p>
				</div>
				<label class="col-sm-2 control-label">文件路径</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${adSpace.filePath }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${adSpace.createTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">更新时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${adSpace.updateTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">帮助代码</label>
				<div class="col-sm-10">
					<p class="form-control-static">
						<textarea id="tip-codemirror"><c:out value="${adSpace.tip }"/></textarea>
					</p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2">
					<ul class="nav nav-pills nav-stacked" id="ad-block-editor-tabs">
						<li role="presentation" class="active" data-name="codemirror">
							<a role="tab" data-toggle="tab" href="#editor-wapper-codemirror" aria-controls="editor-wapper-codemirror">CodeMirror</a>
						</li>
						<!-- <li role="presentation" data-name="kindeditor">
							<a role="tab" data-toggle="tab" href="#editor-wapper-kindeditor" aria-controls="editor-wapper-kindeditor">KindEditor</a>
						</li> -->
					</ul>
				</div>
				<div class="col-sm-10">
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="editor-wapper-codemirror">
							<textarea id="editor-codemirror"><c:out value="${adSpace.content }"/></textarea>
						</div>
						<div role="tabpanel" class="tab-pane" id="editor-wapper-kindeditor">
							<textarea id="editor-kindeditor" style="width: 100%;"></textarea>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/adspace/adspace-detail.js"></script>
</footer-scripts>
</html>
