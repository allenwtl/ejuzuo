<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="nav_first" value="digitalFurniture" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>详情 - 数字家居</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;数字家居</li>
		<li class="active">&nbsp;详情</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<input type="hidden" id="jq-domain-image" value="${domainImage }">
		<input type="hidden" id="pictures" value='${furniture.pictures }'>
		<input type="hidden" id="specification" value='${furniture.specification }'>
		<input type="hidden" id="corporation" value='${furniture.corporation }'>
		
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">家居类型</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:choose>
							<c:when test="furniture.digitalType == 1">家具</c:when>
							<c:when test="furniture.digitalType == 2">灯具</c:when>
							<c:when test="furniture.digitalType == 3">饰品</c:when>
							<c:when test="furniture.digitalType == 4">挂画</c:when>
							<c:when test="furniture.digitalType == 5">地毯窗帘</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						<c:out value="${furniture.name }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">空间大类</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" disabled >
							<c:forEach items="${spaceCategorys }" var="spaceCategory">
									<c:if test="${furniture.spaceCategory == spaceCategory.valueCode }"><option selected="selected" value="${spaceCategory.valueCode }"><c:out value="${spaceCategory.valueName}" /></option></c:if>
							</c:forEach>
						</select>
					</p>
				</div>
				<label class="col-sm-2 control-label">空间小类</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:forEach items="${fn:split(furniture.spaceSmallCategory, ',')}" var="smallCategory">
							<c:forEach items="${spaceSmallCategorys }" var="spaceSmallCategory">
								<c:if test="${smallCategory == spaceSmallCategory.valueCode }"><c:out value="${spaceSmallCategory.valueName}" /></c:if>
							</c:forEach>
						</c:forEach>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">产品名称</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${furniture.name }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">风格</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:forEach var="style" items="${styles }">
							<c:if test="${furniture.style == style.valueCode}"><c:out value="${style.valueName }"/></c:if>
						</c:forEach>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">品牌</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:forEach var="brand" items="${brands }">
							<c:if test="${furniture.brand == brand.id }"><c:out value="${brand.name }"/></c:if>
						</c:forEach>
					</p>
				</div>
				<label class="col-sm-2 control-label">积分价格</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${furniture.pointPrice }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">封面图</label>
				<div class="col-sm-8">
					<div class="thumbnail">
						<img src="${domainImage }${furniture.thumbnail }" alt="封面图">
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">图示</label>
				<div class="col-sm-8" id="picturesDiv">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">规格</label>
				<div class="col-sm-8">
					<table class="table table-bordered" id="specificationTable">
					</table>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">公司</label>
				<div class="col-sm-8">
					<table class="table table-bordered" id="corporationTable">
					</table>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">附件ID</label>
				<div class="col-sm-3">
					<input type="text" class="form-control jq-val-upload-archive" id="form-archive-insert" 
					  		value="${furniture.fileId }" readonly />
				</div>
				<label class="col-sm-2 control-label">附件名称</label>
				<div class="col-sm-3">
					<p class="form-control-static" id="form-archive-name">
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">参考价格（元）</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${furniture.refPrice }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">浏览量</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${furniture.viewCount }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">是否有效</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${furniture.status == 1 }">有效</c:if>
						<c:if test="${furniture.status == 0 }">无效</c:if>
					</p>
				</div>
				<label class="col-sm-2 control-label">上架状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${furniture.shelfStatus == 1 }">已上架</c:if>
						<c:if test="${furniture.shelfStatus == 0 }">未上架</c:if>
						<c:if test="${furniture.shelfStatus == 2 }">已下架</c:if>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">下载次数</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${furniture.downCount }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">创建者</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${furniture.creator }"/>
					</p>
				</div>
			</div>
	    	<div class="form-group">
				<label class="col-sm-2 control-label">上架时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${furniture.shelfTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${furniture.createTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">更新时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${furniture.updateTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/digitalFurniture/digitalFurniture-detail.js"></script>
</footer-scripts>
</html>
