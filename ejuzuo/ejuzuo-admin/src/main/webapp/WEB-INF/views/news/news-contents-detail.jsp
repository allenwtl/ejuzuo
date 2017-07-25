<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="news" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>详情 - 内容管理</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;新闻资讯</li>
		<li class="active">&nbsp;详情</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">类别</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${newsInfo.category == 1 }">
							<c:out value="网站信息"/>
						</c:if>
						<c:if test="${newsInfo.category == 0 }">
							<c:out value="新闻资讯"/>
						</c:if>
						<c:if test="${newsInfo.category == 2 }">
							<c:out value="品牌介绍"/>
						</c:if>
					</p>
				</div>
				<label class="col-sm-2 control-label">标题</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${newsInfo.title }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">来源</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${newsInfo.source }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">发布人</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${newsInfo.publisher }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">发布时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${newsInfo.publishTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">是否有效</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${newsInfo.status == 1 }">有效</c:if>
						<c:if test="${newsInfo.status == 0 }">无效</c:if>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">编辑状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${newsInfo.editStatus == 1 }">发布</c:if>
						<c:if test="${newsInfo.editStatus == 0 }">暂存</c:if>
					</p>
				</div>
				<label class="col-sm-2 control-label">浏览次数</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${newsInfo.viewCount }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${newsInfo.createTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">更新时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${newsInfo.updateTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">简介</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${newsInfo.brief }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">备注</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly ><c:out value="${newsInfo.remark }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">标题图片</label>
				<div class="col-sm-8">
					<div class="thumbnail">
						<img src="${domainNewsImage }${newsInfo.thumbnail }" alt="标题图片">
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">内容</label>
				<div class="col-sm-8">
					<textarea id="jq-detail-content" rows="10" readonly><c:out value="${newsInfo.content }"/></textarea>
				</div>
			</div>
		</div>
	</div>
	
</body>
<footer-scripts>
<script src="/resources/scripts/news/news-contents-detail.js"></script>
</footer-scripts>
</html>
