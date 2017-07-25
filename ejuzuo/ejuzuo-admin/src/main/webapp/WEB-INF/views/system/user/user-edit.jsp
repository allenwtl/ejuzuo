<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="user" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>编辑 - 用户管理 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li><a href="/system/user">&nbsp;用户管理</a></li>
		<li class="active">&nbsp;编辑</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
	
		<div role="tabpanel">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist" id="user-edit-tabs" style="margin-bottom:15px;">
				<li role="presentation" class="active">
					<a role="tab" data-toggle="tab" href="#user-edit-edit" aria-controls="user-edit-edit">编辑用户信息</a>
				</li>
				<li role="presentation">
					<a role="tab" data-toggle="tab" href="#user-edit-pwd" aria-controls="user-edit-pwd">修改用户密码</a>
				</li>
			</ul>
			
			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="user-edit-edit">
					
					<form class="form-horizontal" id="form-edit" action="/system/user/${user.id }/edit"
						method="post" novalidate="novalidate" autocomplete="off">
									
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-edit-account">账号</label>
							<div class="col-sm-4">
								<input type="hidden" id="form-edit-default-account" value="${user.account }"/>
								<input type="text" class="form-control" name="account" id="form-edit-account" 
									value="${user.account }" placeholder="e.g. ${empty user.account ? 'aihua.tan' : user.account }" maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-edit-name">姓名</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="name" id="form-edit-name" 
									value="${user.name }" placeholder="姓名 e.g. ${empty user.name ? '谢明媚' : user.name }" maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-edit-nickname">昵称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="nickname" id="form-edit-nickname" 
									value="${user.nickname }" placeholder="昵称" maxlength="20"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-edit-roles">角色</label>
							<div class="col-sm-8">
								<input type="hidden" id="form-edit-default-roles" value="${user.roles }"/>
								<select class="form-control" id="form-edit-roles" name="roles" multiple>
									<%-- <optgroup label="管理员">
										<option value="1" ${userRoles }>超级管理员</option>
									</optgroup>
									<optgroup label="其他">
									</optgroup> --%>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">状态</label>
							<div class="col-sm-8">
								<div class="radio" style="padding-top:3px;">
				  					<label class="radio-inline" style="padding-top:3px;">
				    					<input type="radio" name="status" id="form-status-1" value="1" ${user.status eq 1 ? 'checked' : '' }> 正常的
				  					</label>
									<label class="radio-inline" style="padding-top:3px;">
				  						<input type="radio" name="status" id="form-status-2" value="2" ${user.status eq 2 ? 'checked' : '' }> 禁用的
									</label>
									<label class="radio-inline" style="padding-top:3px;">
				  						<input type="radio" name="status" id="form-status-3" value="3" ${user.status eq 3 ? 'checked' : '' }> 离职的
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-edit-remark">备注</label>
							<div class="col-sm-8">
								<textarea class="form-control" rows="3" name="remark" id="form-edit-remark"><c:out value="${user.remark }"/></textarea>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" class="btn btn-primary" id="form-edit-submit" value="更新" data-loading-text="更新中..."/>
								<input type="reset" class="btn btn-default" id="form-edit-reset" value="重置"/>
							</div>
						</div>
					</form>
				
				</div>
				
				<div role="tabpanel" class="tab-pane" id="user-edit-pwd">
					
					<form class="form-horizontal" id="form-pwd" action="/system/user/${user.id }/change-password"
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
							<label class="col-sm-2 control-label" for="form-pwd-password">新密码</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="password" id="form-pwd-password" 
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
	<script type="text/template-dot" id="doT-roles">
	{{~it :item}}
		<option value="{{=item.id}}" {{? item.selected}}selected{{?}}>{{=item.roleName}}</option>
	{{~ }}
	</script>
<script>
require([ 'jquery', 'base', 'doT', 'PNotify', 'select2' ], function($, base, doT, PNotify){
	
	'use strict';
	
	var RolesSelect = {
		data : undefined,
		defaultSelectedIds : $('#form-edit-default-roles').val(),
		selectedIds : [],
		templateFn : doT.template($("#doT-roles").html()),
		initDefaultData : function() {
			
			var _self = this;
			
			$.ajax({
				url: "/system/user/role-all",
				type : "get",
				dataType: "json",
				complete : function() {
				},
				error : function() {
					new PNotify({
						title : "获取角色数据异常！",
						type : 'error'
					});
				},
				success : function(response) {
					if (response.success) {
						_self.data = response.data;
					} else {
						new PNotify({
							title : "获取角色数据失败！",
							text : response.msg || '',
							type : 'error'
						});
					}
				}
			});
			
		},
		parseSelectedIds : function() {
			// 将默认值转为数值数组
			if (!this.defaultSelectedIds || this.defaultSelectedIds === "") {
				this.selectedIds = [];
				return ;
			}
			var tmp = this.defaultSelectedIds;
			this.selectedIds = tmp.replace("[", "").replace("]", "").split(",");
		},
		renderSelect : function() {
			var _self = this;
			
			if (_self.data.length <= 0) {
				new PNotify({
					title : "角色数据未加载完整！",
					type : 'warn'
				});
			}
			
			_self.parseSelectedIds();
			
			// 确定 selected 值，供模板使用
			var tmpIds = _self.selectedIds;
			var tmpIdsLength = tmpIds.length;
			console.log(tmpIdsLength);
			if (tmpIdsLength > 0) {
				$.each(_self.data, function(idx, obj){
					
					obj.selected = false;
					
					for (var i = 0; i < tmpIdsLength; i++) {
						
						if (obj.id === +tmpIds[i]) {
							obj.selected = true;
						}
					}
				});
			}
			
			var options = _self.templateFn(_self.data);
			$("#form-edit-roles").html(options);
			
			$("#form-edit-roles").select2({
				placeholder: "选择角色",
				allowClear: true
			});
		},
		getSelectedName : function(id) {
			var rtn;
			$.each(this.data, function(idx, obj){
				if (obj.id === id) {
					rtn = obj.roleName;
					return;
				}
			});
			return rtn;
		},
		getSubmitData : function() {
			var _self = this;

			var val = $("#form-edit-roles").val();
			
			if (!val) {
				return {ids:'', names:''};
			}

			var tmpSelectedIds = [], tmpSelectedNames = [];

			$.each(val, function(idx, value){
				tmpSelectedIds.push(+value);
			});
			tmpSelectedIds = tmpSelectedIds.sort();

			$.each(tmpSelectedIds, function(idx, value){
				var name = _self.getSelectedName(value);
				tmpSelectedNames.push(name);
			});

			return {ids:tmpSelectedIds.join(","), names:tmpSelectedNames.join(",")};

		}
	
	};
	
	RolesSelect.initDefaultData();
	setTimeout(function(){
		RolesSelect.renderSelect();
	}, 200);
	
	var $form = $("#form-edit");
	var $form_submit = $("#form-edit-submit");
	$form.validate({
		rules : {
			account : {
				required : true,
				maxlength : 20,
				notBlank : true,
				remote : {
					url : '/system/user/account-conflict',
					data : {
						defaultAccount : $("#form-edit-default-account").val()
					}
				}
			},
			name : {
				required : true,
				maxlength : 20,
				notBlank : true
			},
			nickName : {
				maxlength : 20
			},
			status : {
				required : true
			},
			remark : {
				maxlength : 200
			},
			
			workNum : {
				maxlength : 10,
				remote : {
					url : '/system/user/workNum-conflict',
					data : {
						defaultWorkNum : $("#form-default-workNum").val()
					}
				}
			} 
		},
		messages : {
			account : {
				remote : '账号冲突, 请重新确认'
			},			
			workNum : {
				remote: function(workNum, element) {
					var accountTem = '';
					$.ajax({
					url: "/system/user/query-account?workNum=" + workNum,
						type : "get",
						async: false,
						complete : function() {
						},
						error : function() {
							// 忽略
						},
						success : function(account) {
							accountTem  = account;
						}
					});
					
					if (accountTem !== '') {
						return '工号已经绑定了账号' + accountTem + ', 请重新确认'
					} else {
						return '工号已经绑定了账号, 请重新确认'
					}
				}
			}
		},
		submitHandler : function(form) {
			
			$form_submit.button('loading');
			
			var data = {};
			data.account = $("#form-edit-account").val();
			data.name = $("#form-edit-name").val();
			data.nickname = $("#form-edit-nickname").val();
			data.status = $("input[name='status']:checked").val();
			data.remark = $("#form-edit-remark").val();
			data.addUserSeat = $("#form-crm-checkbox").val();
			if (data.addUserSeat === "true") {
				data.workNum = $("#form-workNum").val();		
				data.defaultExtension = $("#form-defaultExtension").val();		
				data.defaultQueue = $("#form-defaultQueue").val();		
				data.type = $("input[name='type']:checked").val();	
			}
			
			var roles = RolesSelect.getSubmitData();
			data.roles = roles.ids;
			data.roleNames = roles.names;
			
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
				    	title: '更新用户信息异常',
				    	text: "发生异常，提交失败！",
				    	type: 'error'
				    });
				},
				success : function(response) {
					if (response.success) {
						
					    new PNotify({
					    	title: '更新用户信息成功',
					    	text: '请检查一下更新后的信息是否有效.',
					    	type: 'success'
					    });
						
					    window.location.href = "/system/user";
						
					} else {
						
						new PNotify({
					    	title: '更新用户信息失败',
					    	text: response.msg || "发生异常，提交失败！",
					    	type: 'error'
					    });
						
					}
				}
			});
			
			return false;
		}
	});
	
	$("#form-crm-checkbox").on('click', function () {
		
		if ($(this).is(":checked")) {
			$("#crmDiv").show();
			$(this).val(true)
		} else {
			$("#crmDiv").hide();
			$(this).val(false)
		}
    });
	
	
	
	
	var $formPwd = $("#form-pwd");
	var $formPwdSubmit = $("#form-pwd-submit");
	$formPwd.validate({
		rules : {
			password : {
				required : false,
				rangelength: [6, 20]
			}
		},
		submitHandler : function(form) {
			
			$formPwdSubmit.button('loading');
			
			$.ajax({
				url : $formPwd.attr("action"),
				type : "post",
				data : {
					"password" : $("#form-pwd-password").val()
				},
				complete : function() {
					// Stop loading
					$formPwdSubmit.button('reset');
				},
				error : function() {
					new PNotify({
				    	title: '更新用户密码异常',
				    	text: "发生异常，提交失败！",
				    	type: 'error'
				    });
				},
				success : function(response) {
					if (response.success) {
						
					    new PNotify({
					    	title: '更新用户密码成功',
					    	text: '请检查一下更新后的密码是否有效.',
					    	type: 'success'
					    });
						
					    window.location.href = "/system/user";
						
					} else {
						
						new PNotify({
					    	title: '更新用户密码失败',
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
