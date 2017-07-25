<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="user" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>新增 - 用户管理 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li><a href="/system/user">&nbsp;用户管理</a></li>
		<li class="active">&nbsp;新增</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<form class="form-horizontal" id="form-add" action="/system/user/add"
			method="post" novalidate="novalidate" autocomplete="off">
						
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-account">账号</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="account" id="form-account" value="" placeholder="e.g. aihua.tan" maxlength="20"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-password">密码</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="password" id="form-password" value="" placeholder="默认使用 111111" pattern="[A-Za-z0-9]{6,18}" maxlength="18"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-name">姓名</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="name" id="form-name" value="" placeholder="姓名" maxlength="20"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-nickname">昵称</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="nickname" id="form-nickname" value="" placeholder="昵称" maxlength="20"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">角色</label>
				<div class="col-sm-8">
					<select class="form-control" id="form-roles" name="roles" multiple required>
						<!-- <optgroup label="管理员">
							<option value="1">超级管理员</option>
						</optgroup>
						<optgroup label="其他">
						</optgroup> -->
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">状态</label>
				<div class="col-sm-8">
					<div class="radio" style="padding-top:3px;">
	  					<label class="radio-inline" style="padding-top:3px;">
	    					<input type="radio" name="status" id="form-status-1" value="1" checked> 正常的
	  					</label>
						<label class="radio-inline" style="padding-top:3px;">
	  						<input type="radio" name="status" id="form-status-2" value="2"> 禁用的
						</label>
						<label class="radio-inline" style="padding-top:3px;">
	  						<input type="radio" name="status" id="form-status-3" value="3"> 离职的
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">备注</label>
				<div class="col-sm-8">
					<textarea class="form-control" rows="3" name="remark" id="form-remark"></textarea>
				</div>
			</div>			
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" id="form-submit" value="新增" data-loading-text="新增中..."/>
					<input type="reset" class="btn btn-default" id="form-reset" value="重置"/>
				</div>
			</div>
		</form>
	
	</div>

</body>
<footer-scripts>
	<script type="text/template-dot" id="doT-roles">
	{{~it :item}}
		<option value="{{=item.id}}">{{=item.roleName}}</option>
	{{~ }}
	</script>
<script>
require([ 'jquery', 'base', 'doT', 'PNotify', 'select2' ], function($, base, doT, PNotify){

	'use strict';
	
	var RolesSelect = {
		data : undefined,
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
		renderSelect : function() {
			var _self = this;
			
			if (_self.data.length <= 0) {
				new PNotify({
					title : "角色数据未加载完整！",
					type : 'warn'
				});
			}
			
			var options = _self.templateFn(_self.data);
			$("#form-roles").html(options);
			
			$("#form-roles").select2({
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

			var val = $("#form-roles").val();
			
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
	
	/* $("#form-roles").select2({
		placeholder: "选择角色",
		allowClear: true
	}); */
	
	var $form = $("#form-add");
	var $form_submit = $("#form-submit");
	$form.validate({
		rules : {
			account : {
				required : true,
				maxlength : 20,
				notBlank : true,
				remote : '/system/user/account-conflict'
			},
			password : {
				required : false,
				rangelength: [6, 18]
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
				remote : '/system/user/workNum-conflict'
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
			data.account = $("#form-account").val();
			data.password = $("#form-password").val();
			data.name = $("#form-name").val();
			data.nickname = $("#form-nickname").val();
			data.status = $("input[name='status']:checked").val();
			data.remark = $("#form-remark").val();
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
			
			console.log(data);

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
				    	title: '新增用户异常',
				    	text: "发生异常，提交失败！",
				    	type: 'error'
				    });
				},
				success : function(response) {
					if (response.success) {
						
					    new PNotify({
					    	title: '新增用户成功',
					    	text: '请检查一下更新后的信息是否有效.',
					    	type: 'success'
					    });
						
					    window.location.href = "/system/user";
						
					} else {
						
						new PNotify({
					    	title: '新增用户失败',
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
	
});
</script>
</footer-scripts>
</html>
