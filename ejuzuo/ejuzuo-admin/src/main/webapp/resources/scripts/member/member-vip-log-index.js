/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'base-page', 'btable' ], 
		function($, base, doT, PNotify){
	
	'use strict';
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/member/vip/log/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'create_time',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
        	var transType = $("#filter-transType").val();
			if (!!transType) {
				params.transType = transType;
			}
			
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			return params;
        },
		    columns: [{
		        field: 'id',
		        title: 'ID',
		        sortable: true
		    }, {
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
		        field: 'transType',
		        title: '类型',
		        formatter: function(value, row, index) {
		        	var transType = [
     					'其他',
     					'赞助',
     					'分享',
     					'系统赠送',
     					'异常处理'
     				];
     	    		if (!!value || value === 0) {
     	    			return transType[value];
     	    		} else {
     	    			return null;
     	    		}
		        }
		    },
		    {
		        field: 'period',
		        title: '期限[月]'
		    },
		    {
		        field: 'relatedType',
		        title: '关联类型',
		        formatter: function(value, row, index) {
		        	var relatedType = [
     					'其他',
     					'赞助',
     					'分享'
     				];
     	    		if (!!value || value === 0) {
     	    			return relatedType[value];
     	    		} else {
     	    			return null;
     	    		}
		        }
		    },
		    {
		        field: 'relatedId',
		        title: '关联ID'
		    },
		    {
		        field: 'createTime',
		        title: '创建时间',
		        halign: 'left',
		    	align: 'center'
		    },
		    {
		        field: 'creator',
		        title: '创建者'
		    },
		    {
		        field: 'remark',
		        title: '备注'
		    },
		    ]
		});
});