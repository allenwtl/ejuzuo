/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'btable' ], function($, base, doT, PNotify){
	
	'use strict';
	
	// 初始化/绑定事件 分页过滤条件 时间区间
	base.initSimpleFilterDateRangePicker();
	
	function isParamsNotEmpty() {
		var userName = $("#filter-userName").val();
		var action = $("#filter-action").val();
		var status = $("#filter-status").val();
		var beginDate = $("#filter-beginDate").val();
		var endDate = $("#filter-endDate").val();
		
		if (!!userName || !!action || !!status || !!beginDate || !!endDate) {
			return true;
		} else {
			return false;
		}
	}
	
	var pageInited = false;
	var $filterSubmit = $('#filter-submit');
	$filterSubmit.on('click', function () {
		
		$filterSubmit.button('loading');
		
		if (!isParamsNotEmpty()) {
			new PNotify({
				title : '过滤条件不能为空',
				type : 'warn'
			});
			$filterSubmit.button('reset');
			return;
		}
		
		if (!pageInited) {
			initPage();
			pageInited = true;
		} else {
			var options = $('#page').bootstrapTable('getOptions');
			if (options.totalRows === 0) {
				$('#page').bootstrapTable('refresh');
			} else {
				$('#page').bootstrapTable('selectPage', 1);
			}
		}
    });
	
	function initPage() {
		
		$('#page-tip').hide();
	
		$('#page').bootstrapTable({
			url: '/system/user-log/page',
			sidePagination: 'server',
			pagination: true,
			pageList: [20, 30, 50],
			pageSize: 30,
			sortable: true,
			sortName: 'log_date',
	        sortOrder: 'desc',
	        toolbar: '#page-toolbar',
	        onLoadSuccess: function(data) {
	        	$filterSubmit.button('reset');
	        },
	        onLoadError: function(status) {
	        	$filterSubmit.button('reset');
	        },
	        queryParams: function(params) {
	        	
	        	var userName = $("#filter-userName").val();
				if (!!userName) {
					params.userName = userName;
				}

				var action = $("#filter-action").val();
				if (!!action) {
					params.action = action;
				}

				var status = $("#filter-status").val();
				if (!!status) {
					params.status = status;
				}
				
				var beginDate = $("#filter-beginDate").val();
				if (!!beginDate) {
					params.beginDate = beginDate + " 00:00:00";
				}
	
				var endDate = $("#filter-endDate").val();
				if (!!endDate) {
					params.endDate = endDate + " 00:00:00";
				}
				
				return params;
	        },
		    columns: [{
		        field: 'id',
		        title: 'ID',
		        sortable: true,
		        sortName: 'id'
		    }, {
		        field: 'userName',
		        title: '操作用户'
		    }, {
		        field: 'action',
		        title: '操作描述'
		    }, {
		        field: 'status',
		        title: '返回状态'
		    }, {
		        field: 'uri',
		        title: '请求URI'
		    }, {
		        field: 'logDate',
		        title: '操作时间',
		        sortable: true,
		        sortName: 'log_date'
		    }, {
		    	field: 'id',
		        title: '操作',
		        formatter: function(value, row, index) {
		        	return '<button type="button" class="btn btn-xs btn-default jq-page-opt-detail" data-id="'+value+'">'+
								'<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>&nbsp;详情'+
							'</button>';
		        }
		    }]
		});
	}
	
	//分页定义
//	var page = $.fn.AdminPage({
//		doTListSelector : "#dot-list",
//		url : "/system/user-log/page",
//		cacheKey : "system.user-log",
//		dataFormat : function(json) {
//			return json;
//		},
//		getParam: function() {
//			var data = {};
//			
//			var userName = $("#filter-userName").val();
//			if (!!userName) {
//				data.userName = userName;
//			}
//
//			var action = $("#filter-action").val();
//			if (!!action) {
//				data.action = action;
//			}
//
//			var status = $("#filter-status").val();
//			if (!!status) {
//				data.status = status;
//			}
//
//			var beginDate = $("#filter-beginDate").val();
//			if (!!beginDate) {
//				// data.beginDate = beginDate.replace(/[\/]/g, "-") + " 00:00:00";
//				data.beginDate = beginDate + " 00:00:00";
//			}
//
//			var endDate = $("#filter-endDate").val();
//			if (!!endDate) {
//				// data.endDate = endDate.replace(/[\/]/g, "-") + " 00:00:00";
//				data.endDate = endDate + " 00:00:00";
//			}
//
//			return data;
//		},
//		initFilter: function(data) {
//			if (!data) {
//				return;
//			}
//
//			if (!!data.userName) {
//				$("#filter-userName").val(data.userName);
//			}
//
//			if (!!data.action) {
//				$("#filter-action").val(data.action);
//			}
//
//			if (!!data.status) {
//				$("#filter-status").val(data.status);
//			}
//
//			if (!!data.beginDate) {
//				// $("#filter-beginDate").val(data.beginDate.substring(0,10).replace(/[-]/g, "/"));
//				$("#filter-beginDate").val(data.beginDate.substring(0, 10));
//			}
//
//			if (!!data.endDate) {
//				// $("#filter-endDate").val(data.endDate.substring(0,10).replace(/[-]/g, "/"));
//				$("#filter-endDate").val(data.endDate.substring(0, 10));
//			}
//			
//		}
//	});
	
	// 详情
	var listOfLog = [];
	var logDetailTpl = doT.template($("#dot-detail").html());
	$("#page").on("click", ".jq-page-opt-detail", function() {
		var _self = $(this);
		var id = _self.data("id");
		var item = listOfLog[id];
		
		if (!!item) {
			$("#model-detail-body").html(logDetailTpl(item));
			$('#model-detail').modal({
				backdrop : false
			});
		} else {
			// ajax call
			$.ajax({
				url : "/system/user-log/" + id,
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
						listOfLog[id] = response.data;
						item = response.data;
						$("#model-detail-body").html(logDetailTpl(item));
						$('#model-detail').modal({
							backdrop : false
						});
					}
				}
			});
		}
		
	});
	
});