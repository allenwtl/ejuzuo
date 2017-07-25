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
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/smsRecord/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'id',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var id = $("#filter-id").val();
			if (!!id) {
				params.id = id;
			}
			var mobile = $("#filter-mobile").val();
			if (!!mobile) {
				params.mobile = mobile;
			}
			var sendStatus = $("#filter-sendStatus").val();
			if (!!sendStatus) {
				params.sendStatus = sendStatus;
			}
			var mobileType = $("#filter-mobileType").val();
			if (!!mobileType) {
				params.mobileType = mobileType;
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
	        field: 'mobile',
	        title: '手机号'
	    },
	    {
	        field: 'sendStatus',
	        title: '状态',
	        formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-danger">失败</span>',
					'<span class="label label-success">成功</span>',
					'<span class="label label-warning">发送中</span>'
				];
	    		if (!!value.index || value.index === 0) {
	    			return status[value.index];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'mobileType',
	        title: '类型',
	        formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-danger">其他</span>',
					'<span class="label label-warning">移动</span>',
					'<span class="label label-success">联通</span>',
					'<span class="label label-info">电信</span>'
				];
	    		if (!!value.index || value.index === 0) {
	    			return status[value.index];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'content',
	        title: '内容'
	    },
	    {
	        field: 'gateway',
	        title: '短信网关',
	        formatter: function(value, row, index) {
	    		if (value == 1) {
	    			return '<span class="label label-success">谐和</span>';
	    		}
	    	}
	    },
	    {
	        field: 'sendDate',
	        title: '发送时间',
	    },
	    {
	        field: 'memberId',
	        title: '用户ID',
	    }, 
	    {
	        field: 'source',
	        title: '来源',
	    },
	    {
	        field: 'errorMsg',
	        title: '错误信息',
	    },
	    {
	        field: 'createDate',
	        title: '创建时间',
	    }
	    ]
	});
});