<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="designer" scope="request"/>
<c:set var="nav_second" value="designer" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>详情 - 设计师</title>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;设计师</li>
		<li class="active">&nbsp;详情</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="form-horizontal">
			<input type="hidden" id="jq-domain-image" value="${domainImage }">
			<input type="hidden" id="coverImg" value='${designer.coverImg }'>
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
				<label class="col-sm-2 control-label">设计师作品</label>
				<div class="col-sm-8">
					<p class="form-control-static">
						<a href="/designer/work/${designerWork.id }/detail" >${designerWork.name }</a>&nbsp;
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
								<img src="/designer/queryLicenseImg?path=${designer.licenseImg }" alt="营业执照">
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
				<label class="col-sm-2 control-label">状态</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:if test="${designer.status == 0}">
							暂存
						</c:if>
						<c:if test="${designer.status == 1}">
							待审核
						</c:if>
						<c:if test="${designer.status == 2}">
							审核通过
						</c:if>
						<c:if test="${designer.status == 3}">
							审核退回
						</c:if>
					</p>
				</div>
				<c:if test="${designer.status == 3 }">
					<label class="col-sm-2 control-label">退回原因</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designer.reason }"/>
					</p>
				</div>
				</c:if>
			</div>
			<c:if test="${designer.status == 3 || designer.status == 2 }">
			<div class="form-group">
				<label class="col-sm-2 control-label">审核人</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<c:out value="${designer.verifior }"/>
					</p>
				</div>
				<label class="col-sm-2 control-label">审核时间</label>
				<div class="col-sm-3">
					<p class="form-control-static">
						<fmt:formatDate type="both" value="${designer.verifyTime.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
				</div>
			</div>
			</c:if>
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
				<label class="col-sm-2 control-label">备注</label>
				<div class="col-sm-8">
					<textarea class="form-control disabled" rows="5" readonly><c:out value="${designer.remark }"/></textarea>
				</div>
			</div>
		</div>
	</div>
</body>
<footer-scripts> 
<script src="/resources/scripts/designer/designer-detail.js"></script> 
</footer-scripts>
</html>
