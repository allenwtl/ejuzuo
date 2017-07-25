<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="nav_first" value="digitalFurniture" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>编辑 - 数字家居</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;数字家居</li>
		<li class="active">&nbsp;编辑</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form class="tab-pane form-horizontal" id="form-digitalFurniture-update" 
					action="digital_furniture/update"
					method="post" autocomplete="off">
		<input type="hidden" id="jq-domain-image" value="${domainImage }">
		<input type="hidden" id="jq-domain-path" value="img/digital_furniture" >
		<input type="hidden" id="jq-attachment-path" value="attachment/digital_furniture" >
		<input type="hidden" id="jq-domain-relatedType" value="3" >
		<input type="hidden" id="pictures" value='${furniture.pictures }'>
		<input type="hidden" id="specification" value='${furniture.specification }'>
		<input type="hidden" id="corporation" value='${furniture.corporation }'>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">家居类型</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" name="digitalType" >
							<option value=""></option>
							<c:choose>
								<c:when test="${furniture.digitalType == 1}"><option selected="selected" value="1"><c:out value="家具" /></option></c:when>
								<c:otherwise><option value="1"><c:out value="家具" /></option></c:otherwise>	
							</c:choose>
							<c:choose>
								<c:when test="${furniture.digitalType == 2}"><option selected="selected" value="2"><c:out value="灯具" /></option></c:when>
								<c:otherwise><option value="2"><c:out value="灯具" /></option></c:otherwise>	
							</c:choose>
							<c:choose>
								<c:when test="${furniture.digitalType == 3}"><option selected="selected" value="3"><c:out value="饰品" /></option></c:when>
								<c:otherwise><option value="3"><c:out value="饰品" /></option></c:otherwise>	
							</c:choose>
							<c:choose>
								<c:when test="${furniture.digitalType == 4}"><option selected="selected" value="4"><c:out value="挂画" /></option></c:when>
								<c:otherwise><option value="4"><c:out value="挂画" /></option></c:otherwise>	
							</c:choose>
							<c:choose>
								<c:when test="${furniture.digitalType == 5}"><option selected="selected" value="5"><c:out value="地毯窗帘" /></option></c:when>
								<c:otherwise><option value="5"><c:out value="地毯窗帘" /></option></c:otherwise>	
							</c:choose>
						</select>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">品牌</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:forEach items="${brands }" var="brand">
						<c:choose>
							<c:when test="${furniture.brand == brand.id}"><input type="hidden" name="brand" value="${furniture.brand }"><c:out value="${brand.name }" /></c:when>
						</c:choose>
						</c:forEach>
					</p>
				</div>
				<label class="col-sm-2 control-label">产品名称</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<input type="text" class="form-control" name="name" value="${furniture.name }" required/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<input type="hidden" name="id" value="${furniture.id }">
				<label class="col-sm-2 control-label">空间大类</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:forEach items="${spaceCategorys }" var="spaceCategory" >
							<c:if test="${furniture.spaceCategory == spaceCategory.valueCode }"><c:out value="${spaceCategory.valueName}" /></c:if>
						</c:forEach>
					</p>
				</div>
				<label class="col-sm-2 control-label">空间小类</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:forEach items="${spaceSmallCategorys }" var="spaceSmallCategory">
						<c:set var="isCheck" value="0"></c:set>
						<c:forEach items="${fn:split(furniture.spaceSmallCategory, ',')}" var="fsmall">
							<c:if test="${fsmall == spaceSmallCategory.valueCode }">
								<c:set var="isCheck" value="1"></c:set>
							</c:if>
						</c:forEach>
							<input type="checkbox" name="spaceSmallCategory" <c:if test="${isCheck==1}"> checked='checked'</c:if> value="${spaceSmallCategory.valueCode}">${spaceSmallCategory.valueName}&nbsp;&nbsp;
						</c:forEach>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">积分价格</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<input type="text" class="form-control" name="pointPrice" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" value="${furniture.pointPrice }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">参考价格（元）</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<input type="text" class="form-control" name="refPrice" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" value="${furniture.refPrice }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">风格</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" name="style" >
							<c:forEach items="${styles }" var="style">
							<c:choose>
								<c:when test="${furniture.style == style.valueCode}"><option selected="selected" value="${style.valueCode }"><c:out value="${style.valueName }" /></option></c:when>
								<c:otherwise><option value="${style.valueCode }"><c:out value="${style.valueName}" /></option></c:otherwise>	
							</c:choose>
							</c:forEach>
						</select>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>封面图<span class="text-danger">（建议288*288）</span>
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<span class="input-group-addon" id="basic-addon1"><c:out value="${domainImage }"/></span>
					  	<input type="text" class="form-control jq-val-upload-img" id="form-update" name="thumbnail" 
					  		data-type="update" 
							placeholder="relative path of image" value="${furniture.thumbnail }" readonly required />
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
					<div class="thumbnail ${empty furniture.thumbnail?'hidden':''}" id="jq-upload-thumbnail-update">
						<img src="${empty furniture.thumbnail?'':domainImage}
							${empty furniture.thumbnail?'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7':furniture.thumbnail}" 
							id="jq-upload-thumbnail-img-update" >
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					图示<span class="text-danger">（建议850*478）</span>
					<a class="detail-icon" id="addimage" href="javascript:"><i class="glyphicon glyphicon-plus icon-plus"></i></a>&nbsp;
				</label>
				<div class="col-sm-8">
					<table id="picturesTable" class="table table-bordered">
					</table>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					规格
					<a class="detail-icon" id="addSpecification" href="javascript:"><i class="glyphicon glyphicon-plus icon-plus"></i></a>&nbsp;
				</label>
				<div class="col-sm-8">
					<table id="specificationTable">
					</table>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
				公司
				<!-- <a class="detail-icon" id="addCorporation" href="javascript:"><i class="glyphicon glyphicon-plus icon-plus"></i></a> -->
				</label>
				<div class="col-sm-8">
					<table class="table table-bordered" id="corporationTable">
					</table>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					附件
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<span class="input-group-addon">附件ID：</span>
					  	<input type="text" class="form-control jq-val-upload-archive" id="form-archive-insert" name="fileId"
					  		value="${furniture.fileId }" data-type="insert" readonly />
					  	<span class="input-group-addon">附件名称：</span>
					  	<input type="text" class="form-control" id="form-archive-name"
					  		data-type="insert" readonly />
					</div>
					<input type="file" class="jq-file-upload-archive"
						id="form-archive-file-insert"
						data-btn="form-archive-btn-insert" 
						data-clear="form-archive-btn-clear-insert"/>
					<input type="button" class="btn btn-sm btn-primary jq-btn-upload-archive" id="form-archive-btn-insert" value="上传" 
						data-loading-text="上传中..." 
						data-field="form-archive-insert" 
						data-file="form-archive-file-insert" 
						data-form="form-upload-insert" disabled>
					<input type="button" class="btn btn-sm btn-default jq-btn-clear-upload-archive" id="form-archive-btn-clear-insert" value="清除" 
						data-loading-text="清除中..." 
						data-field="form-archive-insert" 
						data-file="form-archive-file-insert" 
						data-btn="form-archive-btn-insert" disabled>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">是否有效</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" name="status" >
							<option value="0" <c:if test="${furniture.status == 0 }">selected</c:if> >无效</option>
							<option value="1" <c:if test="${furniture.status == 1 }">selected</c:if> >有效</option>
						</select>
					</p>
				</div>
				<label class="col-sm-2 control-label">上架状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<select class="form-control" name="shelfStatus" >
							<option value="1" <c:if test="${furniture.shelfStatus == 1 }">selected</c:if> >已上架</option>
							<option value="0" <c:if test="${furniture.shelfStatus == 0 }">selected</c:if> >未上架</option>
							<option value="2" <c:if test="${furniture.shelfStatus == 2 }">selected</c:if> >已下架</option>
						</select>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="submit" class="btn btn-info" value="更新" id="form-btn-release-update" data-loading-text="更新...">
					<a role="button" class="btn btn-default" href="/digital_furniture">返回</a>
					<a role="button" target="_blank" class="btn btn-primary" href="http://www.ejuzuo.com/digital/digitalDetail/${furniture.id }" >预览</a>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/digitalFurniture/digitalFurniture-edit.js"></script>
</footer-scripts>
</html>
