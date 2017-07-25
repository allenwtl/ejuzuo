/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	$('#filter-beginDate-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$('#filter-endDate-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$("#filter-beginDate-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-endDate-group').data("DateTimePicker").minDate(false);;
		} else {
			e.date.hour(0).minute(0).second(0).millisecond(0);
			$('#filter-endDate-group').data("DateTimePicker").minDate(e.date);
		}
	});
	$("#filter-endDate-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-beginDate-group').data("DateTimePicker").maxDate(false);
		} else {
			e.date.hour(23).minute(59).second(59).millisecond(999);
			$('#filter-beginDate-group').data("DateTimePicker").maxDate(e.date);
		}
	});
	
	var $filterSubmit = $('#filter-submit');
	
	$filterSubmit.on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	var pageDetailTpl = doT.template($("#dot-page-detail").html());
	var tplPagePromoInfo = doT.template($("#dot-page-promoInfo").html());
	var tplPageCallbackInfo = doT.template($("#dot-page-callbackInfo").html());
	$('#page').bootstrapTable({
		url: '/member/promoRecord/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'create_time',
        toolbar: '#page-toolbar',
        detailView: true,
        detailFormatter: function(index, row) {
        	return pageDetailTpl(row);
        },
        onLoadSuccess: function(data) {
        	$filterSubmit.button('reset');
        },
        onLoadError: function(status) {
        	$filterSubmit.button('reset');
        },
        queryParams: function(params) {
			var id = $("#filter-id").val();
			if (!!id) {
				params.id = id;
			}
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			var promoCode = $("#filter-promoCode").val();
			if (!!promoCode) {
				params.promoCode = promoCode;
			}
			var beginDate = $("#filter-beginDate").val();
			if (!!beginDate) {
				params.beginDate = beginDate;
			}
			var endDate = $("#filter-endDate").val();
			if (!!endDate) {
				params.endDate = endDate;
			}
			return params;
        },
	    columns: [
         {
	        field: 'id',
	        title: 'ID',
	        sortable: true
	    }, 
	    {
	        field: 'memberId',
	        title: '用户ID'
	    },
	    {
	        field: 'promoCode',
	        title: '推广代码',
	    },{
	    	field: 'status',
	    	title: '状态',
	    	halign: 'left',
	    	align: 'center',
	    	formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-danger">未处理</span>',
					'<span class="label label-success">已处理</span>'
				];
	    		if (!!value || value === 0) {
	    			return status[value];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'viewCount',
	        title: '点击次数',
	    },
	    {
	        field: 'promoInfo',
	        title: '发起信息',
	        cellStyle: function(value, row, index) {
	    		return {
	    			classes: 'content-cell'
	    		}
	    	},
	    	formatter: function(value, row, index) {
	    		return tplPagePromoInfo({promoInfo: value});
	    	}
	    },
	    {
	        field: 'callbackInfo',
	        title: '点击信息',
	        cellStyle: function(value, row, index) {
	    		return {
	    			classes: 'content-cell'
	    		}
	    	},
	    	formatter: function(value, row, index) {
	    		return tplPageCallbackInfo({callbackInfo: value});
	    	}
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    }
	    ]
	});
	
});