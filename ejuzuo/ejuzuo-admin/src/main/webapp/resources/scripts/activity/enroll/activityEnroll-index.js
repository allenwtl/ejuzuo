/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
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
		url: '/activity/enroll/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'enroll_time',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var id = $("#filter-id").val();
			if (!!id) {
				params.id = id;
			}
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			var activityId = $("#filter-activityId").val();
			if (!!activityId) {
				params.activityId = activityId;
			}
			var beginDate = $("#filter-beginDate").val();
			if (!!beginDate) {
				params.enrollBeiginTime = beginDate;
			}
			var endDate = $("#filter-endDate").val();
			if (!!endDate) {
				params.enrollEndTime = endDate;
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
	        field: 'nickName',
	        title: '用户昵称'
	    },
	    {
	        field: 'account',
	        title: '用户账号'
	    },
	    {
	        field: 'activityId',
	        title: '活动ID',
	    },
	    {
	        field: 'enrollTime',
	        title: '报名时间',
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    },
	    {
	        field: 'id',
	        title: '操作',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	var str = '';
//	        		str += '<button class="btn btn-primary btn-xs update" type="button" data-id="'+value+'" >修改</button>';
//	        		str += '&nbsp;<button class="btn btn-danger btn-xs delete" type="button" data-id="'+value+'" >删除</button>';
		        return str;
	        }
	    }
	    ]
	});
});