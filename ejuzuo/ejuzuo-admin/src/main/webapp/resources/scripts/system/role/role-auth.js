/**
 * 
 */
require([ 'jquery', 'PNotify' ], function($, PNotify){
	
	'use strict';
	
	var roleId = $('#jq-role-id').val();
	var rolePerms = [];
	
	$.ajax({
		url: '/system/role/'+roleId+'/auth-perms',
		type : "get",
		dataType: "json",
		complete : function() {
		},
		error : function() {
			new PNotify({
				title : "获取角色权限数据异常！",
				type : 'error'
			});
		},
		success : function(response) {
			if (response.success) {
				
				$.each(response.data, function(idx, obj){
					rolePerms.push(obj.permCode);
				});
				
				var checkboxs = $('#jq-role-auth-table input[type="checkbox"]');
				
				$.each(checkboxs, function(idx, obj){
					obj.checked = false;
					
					if ($.inArray(obj.value, rolePerms) >= 0) {
						console.log(obj);
						obj.checked = true;
					}
				});
				
			} else {
				new PNotify({
					title : "获取角色权限数据失败！",
					text : response.msg || '',
					type : 'error'
				});
			}
		}
	});
	
	$('#jq-role-auth-submit').on('click', function(){
		var checkboxs = $('#jq-role-auth-table input[type="checkbox"]:checked');
		var checked = [];
		$.each(checkboxs, function(idx, obj){
			if (obj.checked) {
				checked.push(obj.value);
			}
		});
		
		$.ajax({
			url: '/system/role/'+roleId+'/auth',
			type : "post",
			dataType: "json",
			data: {'perms':checked},
			complete : function() {
			},
			error : function() {
				new PNotify({
					title : "更新角色权限异常！",
					type : 'error'
				});
			},
			success : function(response) {
				if (response.success) {
					
					new PNotify({
						title : "更新角色权限成功",
						type : 'success'
					});
					
					window.location.href = '/system/role';
					
				} else {
					new PNotify({
						title : "更新角色权限失败！",
						text : response.msg || '',
						type : 'error'
					});
				}
			}
		});
	});
	
});