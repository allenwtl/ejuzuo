<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="dashboard" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户信息</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<div role="tabpanel">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist" id="profile-info-tabs" style="margin-bottom:15px;">
				<li role="presentation" class="active">
					<a role="tab" data-toggle="tab" href="#profile-info" aria-controls="profile-info">基本信息</a>
				</li>
				<li role="presentation">
					<a role="tab" data-toggle="tab" href="#profile-info-edit" aria-controls="profile-info-edit">修改信息</a>
				</li>
				<li role="presentation">
					<a role="tab" data-toggle="tab" href="#profile-info-pwd" aria-controls="profile-info-pwd">修改密码</a>
				</li>
			</ul>
			
			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="profile-info">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${user.account }"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${user.name }"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">昵称</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="profile-info-nickname">
									<c:out value="${user.nickname }"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">拥有角色</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:choose>
									    <c:when test="${empty user.roleNames }">未分配</c:when>
									    <c:otherwise>
									        <c:out value="${user.roleNames }"/>
									    </c:otherwise>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">用户状态</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:choose>
									    <c:when test="${user.status eq 1 }">正常的</c:when>
									    <c:when test="${user.status eq 2 }">禁用的</c:when>
									    <c:when test="${user.status eq 3 }">离职的</c:when>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">备注</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<textarea class="form-control" rows="3" readonly id="profile-info-memo"><c:out value="${user.remark }" /></textarea>
								</p>
							</div>
						</div>
					</div>
				</div>
				
				<div role="tabpanel" class="tab-pane" id="profile-info-edit">
					
					<form class="form-horizontal" id="form-edit" action="/user-profile/edit"
						method="post" novalidate="novalidate" autocomplete="off">
						
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${user.account }"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:out value="${user.name }"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">昵称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="nickname"
									id="form-nickname" value="${user.nickname }"
									placeholder="${user.nickname }" maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">拥有角色</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:choose>
									    <c:when test="${empty user.roleNames }">未分配</c:when>
									    <c:otherwise>
									        <c:out value="${user.roleNames }"/>
									    </c:otherwise>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">用户状态</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:choose>
									    <c:when test="${user.status eq 1 }">正常的</c:when>
									    <c:when test="${user.status eq 2 }">禁用的</c:when>
									    <c:when test="${user.status eq 3 }">离职的</c:when>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">备注</label>
							<div class="col-sm-10">
								<textarea class="form-control" rows="3" name="remark" id="form-remark"><c:out value="${user.remark }" /></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" class="btn btn-primary" id="form-submit" value="更新" data-loading-text="更新中..."/>
								<input type="reset" class="btn btn-default" id="form-reset" value="重置"/>
							</div>
						</div>
					</form>
				</div>
				
				
				<div role="tabpanel" class="tab-pane" id="profile-info-pwd">
				
					<form class="form-horizontal" id="form-pwd" action="/user-profile/change-password"
						method="post" novalidate="novalidate" autocomplete="off">
						
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-4">
								<p class="form-control-static">
									<c:out value="${user.account }"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-4">
								<p class="form-control-static">
									<c:out value="${user.name }"/>
								</p>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-pwd-oldPassword">旧密码</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" name="oldPassword" id="form-pwd-oldPassword" 
									value="" placeholder="e.g. 111111" maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-pwd-newPassword">新密码</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" name="newPassword" id="form-pwd-newPassword" 
									value="" placeholder="e.g. 111111" maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-pwd-rePassword">新密码</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" name="rePassword" id="form-pwd-rePassword" 
									value="" placeholder="e.g. 111111" maxlength="20"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" class="btn btn-primary" id="form-pwd-submit" value="更新" data-loading-text="更新中..."/>
								<input type="reset" class="btn btn-default" id="form-pwd-reset" value="重置"/>
							</div>
						</div>
						
						
					</form>
				
				</div>

			</div>

		</div>
	
	</div>

</body>
<footer-scripts>
<script>
require([ 'jquery', 'base', 'PNotify' ], function($, base, PNotify){
	
	'use strict';
	
	var $form = $("#form-edit");
	var $form_submit = $("#form-submit");
	$form.validate({
		rules : {
			nickname : {
				maxlength : 20
			},
			remark : {
				maxlength : 200
			}
		},
		submitHandler : function(form) {
			
			$form_submit.button('loading');
			
			var data = {};
			data.nickname = $("#form-nickname").val();
			data.remark = $("#form-remark").val();

			$.ajax({
				url : $form.attr("action"),
				type : "post",
				data : data,
				complete : function() {
					// Stop loading
					$form_submit.button('reset');
				},
				error : function() {
					new PNotify({
				    	title: '更新基本信息异常',
				    	text: "发生异常，提交失败！",
				    	type: 'error'
				    });
				},
				success : function(response) {
					if (response.success) {
						
					    new PNotify({
					    	title: '更新基本信息成功',
					    	text: '请检查一下更新后的信息是否有效.',
					    	type: 'success'
					    });
						
						$("#profile-info-nickname").text(data.nickname);
						$("#profile-info-remark").text(data.memo);
						$('#profile-info-tabs a:first').tab('show');
						
					} else {
						
						new PNotify({
					    	title: '更新基本信息失败',
					    	text: response.msg || "发生异常，提交失败！",
					    	type: 'error'
					    });
						
					}
				}
			});
			
			return false;
		}
	});
	
	
	var $formPwd = $("#form-pwd");
	var $formPwdSubmit = $("#form-pwd-submit");
	$formPwd.validate({
		rules : {
			oldPassword : {
				required : false,
				rangelength: [6, 20]
			},
			newPassword : {
				required : false,
				rangelength: [6, 20]
			},
			rePassword : {
				required : false,
				rangelength: [6, 20],
				equalTo: "#form-pwd-newPassword"
			}
		},
		submitHandler : function(form) {
			
			$formPwdSubmit.button('loading');
			
			var data = {};
			data.oldPassword = $("#form-pwd-oldPassword").val();
			data.newPassword = $("#form-pwd-newPassword").val();
			data.rePassword  = $("#form-pwd-rePassword").val();
			
			$.ajax({
				url : $formPwd.attr("action"),
				type : "post",
				data : data,
				complete : function() {
					// Stop loading
					$formPwdSubmit.button('reset');
				},
				error : function() {
					new PNotify({
				    	title: '修改密码异常',
				    	text: "发生异常，提交失败！",
				    	type: 'error'
				    });
				},
				success : function(response) {
					if (response.success) {
						
					    new PNotify({
					    	title: '修改密码成功',
					    	text: '请检查一下修改后的密码是否有效.',
					    	type: 'success'
					    });
						
					    $formPwd[0].reset();
					    $('#profile-info-tabs a:first').tab('show');
						
					} else {
						
						new PNotify({
					    	title: '修改密码失败',
					    	text: response.msg || "发生异常，提交失败！",
					    	type: 'error'
					    });
						
					}
				}
			});
			
			return false;
		}
	});
});
</script>
</footer-scripts>
</html>
