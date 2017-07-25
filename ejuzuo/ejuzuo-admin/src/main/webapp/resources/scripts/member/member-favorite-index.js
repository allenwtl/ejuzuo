/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/member/favorite/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'create_time',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			var objectType = $("#filter-objectType").val();
			if (!!objectType) {
				params.objectType = objectType;
			}
			var objectId = $("#filter-objectId").val();
			if (!!objectId) {
				params.objectId = objectId;
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
	        field: 'objectType',
	        title: '对象类型',
	        halign: 'left',
	    	align: 'center',
	    	formatter: function(value, row, index) {
	    		var type = [
					'数字家居',
					'设计作品',
					'活动',
					'资讯',
					'设计师',
					'充值订单'
				];
	    		if (!!value || value === 0) {
	    			return type[value];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'objectId',
	        title: '对象ID',
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    },
	    ]
	});
});