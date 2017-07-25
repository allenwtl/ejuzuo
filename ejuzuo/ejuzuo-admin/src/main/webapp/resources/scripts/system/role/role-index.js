/**
 * 
 */
require([ 'jquery', 'doT', 'PNotify', 'bootstrap-dialog', 'base-page' ], 
		function($, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	//分页定义
	var page = $.fn.AdminPage({
		doTListSelector : "#dot-list",
		url : "/system/role/page",
		cacheKey : "system.role",
		dataFormat : function(json) {
			
			json.status = {
				'1' : '<span class="label label-success">可用的</span>',
				'2' : '<span class="label label-danger">禁用的</span>'
			};
			
			return json;
		},
		getParam: function() {
			var data = {};
			
			var roleName = $("#filter-roleName").val();
			if (!!roleName) {
				data.roleName = roleName;
			}

			var status = $("#filter-status").val();
			if (!!status) {
				data.status = status;
			}

			return data;
		},
		initFilter: function(data) {
			if (!data) {
				return;
			}

			if (!!data.roleName) {
				$("#filter-roleName").val(data.roleName);
			}

			if (!!data.status) {
				$("#filter-status").val(data.status);
			}

		}
	});
	
	
	var addTpl = doT.template($("#dot-add").html());
	$("#filter-add").on("click", function(){
		BootstrapDialog.show({
            title: "新增角色",
            message: addTpl,
            onshown: function(dialogRef) {
            	// 绑定 validation
                $("#form-add").validate({
                	rules : {
                		roleName : {
        					required : true,
        					notBlank : true,
        					remote : '/system/role/name-conflict'
        				},
        				roleCode : {
        					required : false,
        					notBlank : true
        				},
        				status : {
        					required : true
        				},
        				remark : {
        					maxlength : 100
        				}
            		},
            		messages : {
            			roleName : {
            				remote : '角色名称冲突, 请重新确认'
            			}
            		},
            		invalidHandler: function(event, validator) {
            			dialogRef.enableButtons(true);
	                    dialogRef.setClosable(true);
            		},
            		submitHandler : function(form) {
            			
        				var data = {};
        				data.roleName = $("#form-add-roleName").val();
        				data.roleCode = $("#form-add-roleCode").val();
        				data.remark = $("#form-add-remark").val();
        				
        				if ($('#form-add-status-1').is(':checked')) {
        					data.status = 1;
        				} else {
        					data.status = 2;
        				}

            			$.ajax({
            				url : '/system/role/add',
            				type : "post",
            				data : data,
            				complete : function() {
            					dialogRef.enableButtons(true);
			                    dialogRef.setClosable(true);
            				},
            				error : function() {
            					new PNotify({
            						title : "新增角色["+data.roleName+"]异常！",
            						type : "error"
            					});
            				},
            				success : function(response) {
            					if (response.success) {
            						new PNotify({
		        						title : "新增角色["+data.roleName+"]成功！",
		        						type : "success"
		        					});
            						page.rerender();
            						dialogRef.close();
            					} else {
            						new PNotify({
	            						title : "新增角色["+data.roleName+"]失败！",
	            						text : response.msg || "",
	            						type : "error"
	            					});
            					}
            				}
            			});
            		}
            	});
            },
            buttons: [{
                label: ' 新增',
                cssClass: 'btn-primary',
                icon: 'glyphicon glyphicon-add',
                action: function(dialogRef) {
                	dialogRef.enableButtons(false);
                    dialogRef.setClosable(false);
                	$("#form-add").submit();
                }
            }, {
                label: '取消',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
	});
	
	
	var editTpl = doT.template($("#dot-edit").html());
	$("#page").on("click", ".jq-page-opt-edit", function() {
		
		var _self = $(this);
		var id = _self.data("id");
		var name = _self.data("name");
		
		$.ajax({
			url : "/system/role/" + id,
			type : "GET",
			dataType : "json",
			complete : function() {
			},
			error : function(jqXHR, textStatus, errorThrown) {
				new PNotify({
					title : "获取["+name+"]详细信息异常！",
					text : jqXHR.responseJSON.msg,
					type : "error"
				});
				
			},
			success : function(response) {
				if (!response.success) {
					new PNotify({
						title : "获取["+name+"]详细信息失败！",
						text : response.msg || "",
						type : "error"
					});
				} else {
					
					BootstrapDialog.show({
			            title: "更新角色",
			            message: editTpl(response.data),
			            onshown: function(dialogRef) {
			            	// 绑定 validation
			                $("#form-edit").validate({
			                	rules : {
			                		roleName : {
			        					required : true,
			        					notBlank : true,
			        					remote : {
			        						url : '/system/role/name-conflict',
			        						data : {
			        							'defaultRoleName' : $("#form-edit-defaultRoleName").val()
			        						}
			        					}
			        				},
			        				roleCode : {
			        					required : false,
			        					notBlank : true
			        				},
			        				status : {
			        					required : true
			        				},
			        				remark : {
			        					maxlength : 100
			        				}
			            		},
			            		messages : {
			            			roleName : {
			            				remote : '角色名称冲突, 请重新确认'
			            			}
			            		},
			            		invalidHandler: function(event, validator) {
			            			console.log("ininin");
			            			dialogRef.enableButtons(true);
				                    dialogRef.setClosable(true);
			            		},
			            		submitHandler : function(form) {
			            			
			        				var data = {};
			        				data.roleName = $("#form-edit-roleName").val();
			        				data.roleCode = $("#form-edit-roleCode").val();
			        				data.remark = $("#form-edit-remark").val();
			        				
			        				if ($('#form-edit-status-1').is(':checked')) {
			        					data.status = 1;
			        				} else {
			        					data.status = 2;
			        				}

			            			$.ajax({
			            				url : '/system/role/'+id+'/edit',
			            				type : "post",
			            				data : data,
			            				complete : function() {
			            					dialogRef.enableButtons(true);
						                    dialogRef.setClosable(true);
			            				},
			            				error : function() {
			            					new PNotify({
			            						title : "编辑角色["+data.roleName+"]异常！",
			            						type : "error"
			            					});
			            				},
			            				success : function(response) {
			            					if (response.success) {
			            						new PNotify({
					        						title : "编辑角色["+data.roleName+"]成功！",
					        						type : "success"
					        					});
			            						page.rerender();
			            						dialogRef.close();
			            					} else {
			            						new PNotify({
				            						title : "编辑角色["+data.roleName+"]失败！",
				            						text : response.msg || "",
				            						type : "error"
				            					});
			            					}
			            				}
			            			});
			            		}
			            	});
			            },
			            buttons: [{
			                label: ' 更新',
			                cssClass: 'btn-primary',
			                icon: 'glyphicon glyphicon-edit',
			                action: function(dialogRef) {
			                	dialogRef.enableButtons(false);
			                    dialogRef.setClosable(false);
			                	$("#form-edit").submit();
			                }
			            }, {
			                label: '取消',
			                action: function(dialogItself){
			                    dialogItself.close();
			                }
			            }]
			        });
					
				}
			}
		});
		
		
	});
	
	
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-diabled", function() {
		var _self = $(this);
		var id = _self.data("id");
		var name = _self.data("name");

		$.ajax({
			url : "/system/role/" + id + "/disabled",
			type : "POST",
			dataType : "json",
			complete : function() {
				page.rerender();
			},
			error : function() {
				new PNotify({
					title : "禁用["+name+"]异常！",
					type : "error"
				});
			},
			success : function(response) {
				if (!response.success) {
					new PNotify({
						title : "禁用["+name+"]异常！",
						text : response.msg,
						type : "error"
					});
				} else {
					new PNotify({
						title : "禁用["+name+"]成功",
						type : "success"
					});
				}
			}
		});
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enabled", function() {
		var _self = $(this);
		var id = _self.data("id");
		var name = _self.data("name");

		$.ajax({
			url : "/system/role/" + id + "/enabled",
			type : "POST",
			dataType : "json",
			complete : function() {
				page.rerender();
			},
			error : function() {
				new PNotify({
					title : "启用["+name+"]异常！",
					type : "error"
				});
			},
			success : function(response) {
				if (!response.success) {
					new PNotify({
						title : "启用["+name+"]异常！",
						text : response.msg,
						type : "error"
					});
				} else {
					new PNotify({
						title : "启用["+name+"]成功",
						type : "success"
					});
				}
			}
		});
	});
	
});