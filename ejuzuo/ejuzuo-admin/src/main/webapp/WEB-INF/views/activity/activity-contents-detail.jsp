<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="activity" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>详情 - 内容管理</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;活动</li>
		<li class="active">&nbsp;详情</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">活动类别</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" disabled >
							<c:forEach items="${categorys }" var="category">
								<c:choose>
									<c:when test="${activityInfo.category == category.valueCode }"><option selected="selected" value="${category.valueCode }"><c:out value="${category.valueName}" /></option></c:when>
									<c:otherwise><option value="${category.valueCode }"><c:out value="${category.valueName}" /></option></c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</p>
				</div>
				<label class="col-sm-2 control-label">活动城市</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" disabled >
							<c:forEach items="${citys }" var="city">
								<c:choose>
									<c:when test="${activityInfo.city == city.valueCode }"><option selected="selected" value="${city.valueCode }"><c:out value="${city.valueName}" /></option></c:when>
									<c:otherwise><option value="${city.valueCode }"><c:out value="${city.valueName}" /></option></c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">活动名称</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${activityInfo.title }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">发布人</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${activityInfo.publisher }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.createTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">发布时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.publishTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">是否有效</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${activityInfo.status == 1 }">有效</c:if>
						<c:if test="${activityInfo.status == 0 }">无效</c:if>
					</p>
				</div>
				<label class="col-sm-2 control-label">编辑状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${activityInfo.editStatus == 1 }">发布</c:if>
						<c:if test="${activityInfo.editStatus == 0 }">暂存</c:if>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">报名开始时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.enrollBeginTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">报名结束时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.enrollEndTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">活动开始时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.holdBeginTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">活动结束时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.holdEndTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">浏览次数</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${activityInfo.viewCount }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">更新时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${activityInfo.updateTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">简介</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${activityInfo.brief }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">备注</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly ><c:out value="${activityInfo.remark }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">缩略图</label>
				<div class="col-sm-8">
					<div class="thumbnail">
						<img src="${domainNewsImage }${activityInfo.thumbnail }" alt="缩略图">
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">内容</label>
				<div class="col-sm-8">
					<textarea id="jq-detail-content" rows="10" readonly><c:out value="${activityInfo.content }"/></textarea>
				</div>
			</div>
		</div>
	</div>
	
</body>
<footer-scripts>
<script src="/resources/scripts/activity/activity-contents-detail.js"></script>
</footer-scripts>
</html>
