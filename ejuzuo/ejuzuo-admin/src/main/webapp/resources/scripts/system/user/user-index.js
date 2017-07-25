/**
 * 
 */
require([ 'jquery', 'doT', 'PNotify', 'bootstrap-dialog', 'base-page' ], function($, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	//分页定义
	var page = $.fn.AdminPage({
		doTListSelector : "#dot-list",
		url : "/system/user/page",
		cacheKey : "system.user",
		dataFormat : function(json) {
			json.status = {
				"1" : '<span class="label label-success">正常的</span>',
				"2" : '<span class="label label-danger">禁用的</span>',
				"3" : '<span class="label label-warning">离职的</span>'
			};
			return json;
		},
		getParam: function() {
			var data = {};
			
			var status = $("#filter-status").val();
			if (!!status) {
				data.status = +status;
			}

			var name = $("#filter-name").val();
			if (!!name) {
				data.name = name;
			}

			var account = $("#filter-account").val();
			if (!!account) {
				data.account = account;
			}

			return data;
		},
		initFilter: function(data) {
			if (!data) {
				return;
			}

			if (!!data.status) {
				$("#filter-status").val(data.status);
			}

			if (!!data.name) {
				$("#filter-name").val(data.name);
			}

			if (!!data.account) {
				$("#filter-account").val(data.account);
			}
		}
	});
	
	// 详情
	var userDetailTpl = doT.template($("#dot-detail").html());
	$("#page").on("click", ".jq-page-opt-detail", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		// ajax call
		$.ajax({
			url : "/system/user/" + id,
			type : "GET",
			dataType : "json",
			complete : function() {
			},
			error : function() {
				new PNotify({
					title : '获取详细信息异常！',
					type : 'error'
				});
			},
			success : function(response) {
				if (!response.success) {
					new PNotify({
						title : "获取详细信息失败！",
						text : response.msg || "",
						type : 'error'
					});
				} else {
					
					response.data.statusTpl = {
						"1" : '<span class="label label-success">正常的</span>',
						"2" : '<span class="label label-danger">禁用的</span>',
						"3" : '<span class="label label-warning">离职的</span>'
					};
					
					BootstrapDialog.show({
			            title: '用户信息详情',
			            message: userDetailTpl(response.data)
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
			url : "/system/user/" + id + "/disabled",
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
						title: "禁用["+name+"]成功",
						type: "success"
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
			url : "/system/user/" + id + "/enabled",
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
						title: "启用["+name+"]成功",
						type: "success",
						buttons: {
							closer: true
						}
					});
				}
			}
		});
	});
	
});
