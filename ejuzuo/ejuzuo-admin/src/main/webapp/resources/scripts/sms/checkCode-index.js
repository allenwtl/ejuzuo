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
		url: '/checkCode/page',
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
			var checkType = $("#filter-checkType").val();
			if (!!checkType) {
				params.checkType = checkType;
			}
			var destType = $("#filter-destType").val();
			if (!!destType) {
				params.destType = destType;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			var destNo = $("#filter-destNo").val();
			if (!!destNo) {
				params.destNo = destNo;
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
	        field: 'checkCode',
	        title: '验证码',
	    }, 
	    {
	        field: 'checkType',
	        title: '操作类型',
	        formatter: function(value, row, index) {
	    		var status = [
					'注册',
					'用户激活',
					'验证手机',
					'验证邮箱',
					'找回密码'
				];
	    		if (!!value.index || value.index === 0) {
	    			return status[value.index];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'destType',
	        title: '目标类型',
	        formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-info">短信</span>',
					'<span class="label label-success">邮箱</span>',
				];
	    		if (!!value.index || value.index === 0) {
	    			return status[value.index];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'destNo',
	        title: '目标地址'
	    },
	    {
	        field: 'status',
	        title: '状态',
	        formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-warning">待验证</span>',
					'<span class="label label-success">已验证</span>',
				];
	    		if (!!value.index || value.index === 0) {
	    			return status[value.index];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'memberId',
	        title: '用户ID',
	    },
	    {
	        field: 'verifyCount',
	        title: '验证次数',
	    },
	    {
	        field: 'sendTime',
	        title: '发送时间'
	    },
	    {
	        field: 'expireTime',
	        title: '有效时间'
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    },
	    {
	        field: 'updateTime',
	        title: '更新时间',
	    }
	    ]
	});
});