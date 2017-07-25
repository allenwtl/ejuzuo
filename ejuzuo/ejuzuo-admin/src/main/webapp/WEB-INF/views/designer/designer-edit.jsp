<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="designer" scope="request"/>
<c:set var="nav_second" value="designer" scope="request"/>uest" />

<!DOCTYPE html>
<html>
<head>
<title>审核 - 设计师</title>
<link
	href="//cdn.bootcss.com/bootstrap-colorpicker/2.3.0/css/bootstrap-colorpicker.min.css"
	rel="stylesheet">
</head>
<body>

	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;设计师</li>
		<li class="active">&nbsp;审核</li>
	</ol>

	<div class="col-sm-12 col-md-12 col-lg-12">

		<div class="form-horizontal">
			<input type="hidden" id="jq-domain-image" value="${domainImage }">
			<input type="hidden" id="coverImg" value='${designer.coverImg }'>
			<input type="hidden" id="id" value="${designer.id}"/>
			<input type="hidden" id="memberId" value="${designer.memberId }"/>
			<div class="form-group">
				<label class="col-sm-2 control-label">设计师类别</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:choose>
							<c:when test="${designer.designerType == 3 }">个人设计师</c:when>
							<c:when test="${designer.designerType == 1 }">设计公司</c:when>
							<c:when test="${designer.designerType == 2 }">装修公司</c:when>
						</c:choose>
					</p>
				</div>
				<label class="col-sm-2 control-label">用户ID</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designer.memberId }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<c:choose>
					<c:when test="${designer.designerType == 3 }">
						<label class="col-sm-2 control-label">真实姓名</label>
					</c:when>
					<c:otherwise>
						<label class="col-sm-2 control-label">公司名称</label>
					</c:otherwise>
				</c:choose>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designer.name }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">擅长风格</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${adeptStyle}"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">封面图片</label>
				<div class="col-sm-8">
					<div class="thumbnail" id="thumbnailDiv">
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">省</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${province }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">市</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${city }"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">详细地址</label>
				<div class="col-sm-8">
					<p class="form-control-static">
						<c:out value="${designer.address }"/>
					</p>
				</div>
			</div>
			<c:choose>
				<c:when test="${designer.designerType == 3 }">
					<div class="form-group">
						<label class="col-sm-2 control-label">qq号</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:out value="${designer.qq }"/>
							</p>
						</div>
						<label class="col-sm-2 control-label">职业</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:forEach items="${carrers }" var="carrer" >
									<c:if test="${designer.career == carrer.valueCode }">
										<c:out value="${carrer.valueName }"/>
									</c:if>
								</c:forEach>
							</p>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<label class="col-sm-2 control-label">联系人</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:out value="${designer.contact }"/>
							</p>
						</div>
						<label class="col-sm-2 control-label">联系人手机</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:out value="${designer.contactMobile }"/>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司法人</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:out value="${designer.corporation }"/>
							</p>
						</div>
						<label class="col-sm-2 control-label">公司规模</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:forEach items="${companySizes }" var="companySize">
									<c:if test="${designer.companySize==companySize.valueCode }">
										<c:out value="${companySize.valueName }"/>
									</c:if>
								</c:forEach>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">注册资金</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<c:out value="${designer.regFund }"/>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">营业执照</label>
						<div class="col-sm-8">
							<div class="thumbnail">
								<img src="${domainImage }${designer.licenseImg }" alt="营业执照">
						    </div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="form-group">
				<c:choose>
				<c:when test="${designer.designerType == 3 }">
				<label class="col-sm-2 control-label">个人简介</label>
				</c:when>
				<c:otherwise>
				<label class="col-sm-2 control-label">公司简介</label>
				</c:otherwise>
				</c:choose>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designer.brief }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">代表作品</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designer.classWorks }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">设计理念</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designer.designIdea }"/></textarea>
				</div>
			</div>
			<c:if test="${designer.designerType != 3 }">
			<div class="form-group">
				<label class="col-sm-2 control-label">项目经验</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designer.experience }"/></textarea>
				</div>
			</div>
			</c:if>
			<div class="form-group">
				<label class="col-sm-2 control-label">风格介绍</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designer.styleIntro }"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">认证作品</label>
				<div class="col-sm-8">
					<c:if test="${not empty designer.workId }">
					<a href="/designer/work/${designer.workId }/detail">认证作品</a>
					</c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${designer.createTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">修改时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${designer.updateTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<input type="button" class="btn btn-primary" id="jq-verify-pass" value="通过" data-loading-text="通过执行中..."/>
					<input type="button" class="btn btn-warning" id="jq-verify-back" value="退回" data-loading-text="退回执行中..."/>
					<a href="/designer" class="btn btn-default">返回</a>
				</div>
			</div>
		</div>
	</div>
</body>
<footer-scripts> 
	<script type="text/template-dot" id="dot-reason">
	<form class="form-horizontal" id="form-verify-back" method="post" novalidate="novalidate" autocomplete="off">
		<div class="form-group" id="form-verify-group-back-reason">
			<label class="col-sm-2 control-label" for="form-verify-back-reason">退回原因</label>
			<div class="col-sm-8">
				<textarea class="form-control" rows="3" id="form-verify-back-reason" required maxlength="30"></textarea>
			</div>
		</div>
	</form>
	</script>
<script src="/resources/scripts/designer/designer-edit.js"></script> 
</footer-scripts>
</html>
