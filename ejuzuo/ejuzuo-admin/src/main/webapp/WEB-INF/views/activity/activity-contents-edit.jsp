<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="activity" scope="request"/>

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
		<li class="active">&nbsp;活动</li>
		<li class="active">&nbsp;编辑</li>
	</ol>

	<div class="col-sm-12 col-md-12 col-lg-12">

		<form class="tab-pane form-horizontal" id="form-edit"
			action="/activity/edit" method="post" autocomplete="off">
			<input type="hidden" id="jq-domain-image" value="${domainImage }"/>
			<input type="hidden" id="jq-domain-path" value="img/activity" >
			<input type="hidden" id="jq-domain-relatedType" value="2" >
			
			<input type="hidden" name="id" value="${activityInfo.id}"/>
			<div class="form-group">
				<label class="col-sm-2 control-label">活动类别</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" name="category" required >
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
						<select class="form-control" name="city" required >
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
						<input type="text" class="form-control" name="title" value="${activityInfo.title }" required/>
					</p>
				</div>
				<label class="col-sm-2 control-label">是否有效</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${activityInfo.status == 1 }">有效</c:if>
						<c:if test="${activityInfo.status == 0 }">无效</c:if>
					</p>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>封面图<span class="text-danger">（建议383*211）</span>
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<span class="input-group-addon" id="basic-addon1"><c:out value="${domainImage }"/></span>
					  	<input type="text" class="form-control jq-val-upload-img" id="form-update" name="thumbnail" 
					  		data-type="update" 
							placeholder="relative path of image" value="${activityInfo.thumbnail }" readonly required />
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
					<div class="thumbnail ${empty activityInfo.thumbnail?'hidden':''}" id="jq-upload-thumbnail-update">
						<img src="${empty activityInfo.thumbnail?'':domainImage}
							${empty activityInfo.thumbnail?'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7':activityInfo.thumbnail}" 
							id="jq-upload-thumbnail-img-update" >
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>报名时间
				</label>
				<div class="col-sm-4">
					<div class="input-group date" id="filter-enrollBeginDate-group">
		                <input type="text" class="form-control" size="10" name="enrollBeginTime" value="<fmt:formatDate value='${activityInfo.enrollBeginTime.time }' pattern='YYYY-MM-dd HH:mm:ss'/>" placeholder="开始时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
				</div>
				<div class="col-sm-4">
					<div class="input-group date" id="filter-enrollEndDate-group">
		                <input type="text" class="form-control" size="10" name="enrollEndTime" value="<fmt:formatDate value='${activityInfo.enrollEndTime.time }' pattern='YYYY-MM-dd HH:mm:ss'/>" placeholder="结束时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
	            </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>举办时间
				</label>
				<div class="col-sm-4">
					<div class="input-group date" id="filter-holdBeginDate-group">
		                <input type="text" class="form-control" size="10" name="holdBeginTime" value="<fmt:formatDate value='${activityInfo.holdBeginTime.time }' pattern='YYYY-MM-dd HH:mm:ss'/>" placeholder="开始时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
				</div>
				<div class="col-sm-4">
					<div class="input-group date" id="filter-holdEndDate-group">
		                <input type="text" class="form-control" size="10" name="holdEndTime" value="<fmt:formatDate value='${activityInfo.holdEndTime.time }' pattern='YYYY-MM-dd HH:mm:ss'/>" placeholder="结束时间"/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
	            </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>简介
				</label>
				<div class="col-sm-8">
					<textarea rows="5" cols="" class="form-control" name="brief" required><c:out value="${activityInfo.brief }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					备注
				</label>
				<div class="col-sm-8">
					<textarea rows="5" cols="" class="form-control" name="remark" ><c:out value="${activityInfo.remark }"/></textarea>
				</div>
			</div>
			
			
			<div class="form-group">
				<label class="col-sm-2 control-label" ">
					<span class="text-danger">*</span>内容
				</label>
				<div class="col-sm-8">
					<textarea name="content" id="form-content" rows="10" required><c:out value="${activityInfo.content }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="submit" class="btn btn-info" value="更新" id="form-btn-release-update" data-loading-text="更新...">
					<c:if test="${activityInfo.editStatus == 0 }">
						<input type="submit" class="btn btn-warning" id="form-btn-release" value="发布" data-loading-text="发布中...">
					</c:if>
					<a role="button" class="btn btn-default" href="/activity">返回</a>
					<a role="button" target="_blank" class="btn btn-primary" href="http://www.ejuzuo.com/activity/detail/${activityInfo.id }" >预览</a>
				</div>
			</div>
		</form>
	</div>
</body>
<footer-scripts> 
<script src="/resources/scripts/activity/activity-contents-edit.js"></script> </footer-scripts>
</html>
