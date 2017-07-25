/**
 * 
 */
require([ 'jquery', 'base', 'PNotify', 'btable' ], function($, base, PNotify){
	
	'use strict';
	
	// 初始化/绑定事件 分页过滤条件 时间区间
	base.initSimpleFilterDateRangePicker();
	
	function isParamsNotEmpty() {
		var operType = $("#filter-operType").val();
		var account = $("#filter-account").val();
		var beginDate = $("#filter-beginDate").val();
		var endDate = $("#filter-endDate").val();
		
		if (!!operType || !!account || !!beginDate || !!endDate) {
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
			url: '/system/user-oper-log/page',
			sidePagination: 'server',
			pagination: true,
			pageList: [20, 30, 50],
			pageSize: 30,
			sortable: true,
			sortName: 'created_date',
	        sortOrder: 'desc',
	        toolbar: '#page-toolbar',
	        onLoadSuccess: function(data) {
	        	$filterSubmit.button('reset');
	        },
	        onLoadError: function(status) {
	        	$filterSubmit.button('reset');
	        },
	        queryParams: function(params) {
	        	
	        	var operType = $("#filter-operType").val();
				if (!!operType) {
					params.operType = operType;
				}
				var account = $("#filter-account").val();
				if (!!account) {
					params.account = account;
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
		        field: 'operType',
		        title: '操作类型',
		        formatter: function(value, row, index) {
		        	if (!!value) {
		        		return value.description;
		        	} else {
		        		return null;
		        	}
		        }
		    }, {
		        field: 'account',
		        title: '操作账号'
		    }, {
		        field: 'remark',
		        title: '操作备注',
		        formatter: function(value, row, index) {
		        	if (!!value) {
		        		return JSON.stringify(value);
		        	} else {
		        		return null;
		        	}
		        }
		    }, {
		        field: 'createdDate',
		        title: '操作时间',
		        sortable: true,
		        sortName: 'created_date'
		    }]
		});
	}
	
});