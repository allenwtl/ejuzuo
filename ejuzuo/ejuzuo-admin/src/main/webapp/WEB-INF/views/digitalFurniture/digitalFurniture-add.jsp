<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="nav_first" value="digitalFurniture" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>新增 - 数字家居</title>
<link href="//cdn.bootcss.com/bootstrap-colorpicker/2.3.0/css/bootstrap-colorpicker.min.css" rel="stylesheet">
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;数字家居</li>
		<li class="active">&nbsp;新增</li>
	</ol>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form class="tab-pane form-horizontal" id="form-add" 
					action="/digital_furniture/add"
					method="post" autocomplete="off">
			<input type="hidden" id="jq-domain-image" value="${domainImage }"/>
			<input type="hidden" id="jq-domain-path" value="img/digital_furniture" >
			<input type="hidden" id="jq-attachment-path" value="attachment/digital_furniture" >
			<input type="hidden" id="jq-domain-relatedType" value="3" >
			<input type="hidden" id="spaceSmallCategorys" value='${spaceSmallCategorys }'>
			<input type="hidden" id="corporation" value='${brand.corporation }'>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>产品类型
				</label>
				<div class="col-sm-3">
					<select class="form-control" name="type" id="type" required>
						<option value=""></option>
						<option value="4"><c:out value="品牌馆家居" /></option>
						<option value="2"><c:out value="定制馆" /></option>
						<option value="3"><c:out value="进口馆" /></option>
						<option value="1"><c:out value="家居尾品汇" /></option>
					</select>
				</div>
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>家具类型
				</label>
				<div class="col-sm-3">
					<select class="form-control" name="digitalType" id="digitalType">
						<option value=""></option>
						<option value="1"><c:out value="家具" /></option>
						<option value="2"><c:out value="灯具" /></option>
						<option value="3"><c:out value="饰品" /></option>
						<option value="4"><c:out value="挂画" /></option>
						<option value="5"><c:out value="地毯窗帘" /></option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>品牌
				</label>
				<div class="col-sm-3">
					<input type="hidden" name="brand" value="${brand.id }"><c:out value="${brand.name }" />
				</div>
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>产品名称
				</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name" required/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>空间大类
				</label>
				<div class="col-sm-3">
					<select class="form-control" name="spaceCategory" id="spaceCategory" required>
						<option value=""></option>
						<c:forEach items="${spaceCategorys }" var="spaceCategory">
							<option value="${spaceCategory.valueCode }"><c:out value="${spaceCategory.valueName}" /></option>
						</c:forEach>
					</select>
				</div>
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>空间小类
				</label>
				<div class="col-sm-3" id="spaceSmallCategory">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					<span class="text-danger">*</span>封面图<span class="text-danger">（建议288*288）</span>
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<input type="hidden" class="form-control jq-val-upload-img" id="form-insert" name="thumbnail" 
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
					<span class="text-danger">*</span>风格
				</label>
				<div class="col-sm-3">
					<select class="form-control" name="style" required>
						<option value=""></option>
						<c:forEach items="${styles }" var="style">
							<option value="${style.valueCode }"><c:out value="${style.valueName}" /></option>
						</c:forEach>
					</select>
				</div>
				<label class="col-sm-2 control-label">
					参考价格（元）
				</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="refPrice" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					积分价格
				</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="pointPrice" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					图示<span class="text-danger">（建议850*478）</span>
					<a class="detail-icon" id="addimage" href="javascript:"><i class="glyphicon glyphicon-plus icon-plus"></i></a>
				</label>
				<div class="col-sm-8" >
					<table class="table table-bordered" id="imagegroup">
						<tr>
							<input type="hidden" value="1"/>
							<td>
								<textarea rows="3" cols="" id="intro-1" class="form-control" placeholder="图片描述"></textarea>
							</td>
							<td>
								<div class="input-group">
								  	<input type="hidden" class="form-control jq-val-upload-img" id="form-1"
								  		data-type="1" 
										placeholder="relative path of image" readonly required />
									<input type="text" id="form-1-205100" hidden />
									<input type="text" id="form-1-850478" hidden />
								</div>
								<input type="file" class="jq-file-upload-img"
									id="form-file-1"
									data-btn="form-btn-1" 
									data-clear="form-btn-clear-1"/>
								<input type="button" class="btn btn-sm btn-primary jq-btn-upload-img" id="form-btn-1" value="上传" 
									data-loading-text="上传中..." 
									data-field="form-1" 
									data-file="form-file-1" 
									data-content="true"
									data-form="form-upload-1" disabled>
								<input type="button" class="btn btn-sm btn-default jq-btn-clear-upload-img" id="form-btn-clear-1" value="清除" 
									data-loading-text="清除中..."
									data-field="form-1" 
									data-file="form-file-1" 
									data-btn="form-btn-1" disabled>
								<div class="thumbnail hidden" id="jq-upload-thumbnail-1">
									<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" 
										id="jq-upload-thumbnail-img-1" >
							    </div>
							</td>
							<td>
								<a role="button" class="btn btn-danger" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:">删除</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">
					规格
					<a class="detail-icon" id="addSpecification" href="javascript:"><i class="glyphicon glyphicon-plus icon-plus"></i></a>
				</label>
				<div class="col-sm-8">
					<table class="table table-bordered" id="specificationGroup">
						<tr>
						<td><input name="specificationName" type="text" placeholder="单位名称" class="form-control"/></td>
						<td><input name="specificationValue" type="text" placeholder="产品尺寸" class="form-control"/></td>
						<td><input name="specificationMaterial" type="text" placeholder="材质" class="form-control"/></td>
						<td><input name="specificationPrice" type="text" placeholder="价格（元）" class="form-control"/></td>
						<td><input name="specificationRelateId" type="text" placeholder="关联产品ID" class="form-control"/></td>
						<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>
						</tr>
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
					上传附件
				</label>
				<div class="col-sm-8">
					<div class="input-group">
					  	<span class="input-group-addon">附件ID：</span>
					  	<input type="text" class="form-control jq-val-upload-archive" id="form-archive-insert" name="fileId"
					  		data-type="insert" readonly />
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
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="submit" class="btn btn-info" id="form-btn-save-draft" name="saveDraft" value="保存" data-loading-text="保存中...">
					<a role="button" class="btn btn-default" href="/digital_furniture">返回</a>
				</div>
			</div>
		</form>
	</div>
</body>
<footer-scripts>
<script src="/resources/scripts/digitalFurniture/digitalFurniture-add.js"></script>
</footer-scripts>
</html>
