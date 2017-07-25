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
	$('#page').bootstrapTable({
		url: '/member/sponsor/log/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
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
			var name = $("#filter-name").val();
			if (!!name) {
				params.name = name;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			var beginDate = $("#filter-beginDate").val();
			if (!!beginDate) {
				params.startTime = beginDate;
			}
			var endDate = $("#filter-endDate").val();
			if (!!endDate) {
				params.endTime = endDate;
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
	        field: 'account',
	        title: '用户账号',
	    },
	    {
	        field: 'sponsorType',
	        title: '赞助类型',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="label label-info">49元</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-success">99元</span>';
	        	}
	        }
	    },
	    {
	        field: 'amount',
	        title: '支付金额(分)',
	    },
	    {
	        field: 'paymentMethod',
	        title: '支付方式',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="label label-info">支付宝</span>';
	        	}
	        }
	    },
	    {
	        field: 'fee',
	        title: '手续费',
	    },
	    {
	        field: 'orderNo',
	        title: '支付订单号',
	    },
	    {
	        field: 'payOrderNo',
	        title: '支付平台订单号',
	    },
	    {
	    	field: 'status',
	    	title: '支付状态',
	    	halign: 'left',
	    	align: 'center',
	    	formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-warning">待支付</span>',
					'<span class="label label-success">支付成功</span>',
					'<span class="label label-danger">支付失败</span>'
				];
	    		if (!!value || value === 0) {
	    			return status[value];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    }
	    ]
	});
	
});