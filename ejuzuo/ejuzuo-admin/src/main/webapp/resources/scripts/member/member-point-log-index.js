/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'btable' ], 
		function($, base, doT, PNotify){
	
	'use strict';
	
	// 初始化/绑定事件 分页过滤条件 时间区间
	base.initSimpleFilterDateRangePicker();
	
	function isParamsNotEmpty() {
		var memberId = $("#filter-memberId").val();
		var transType = $("#filter-transType").val();
		var beginDate = $("#filter-beginDate").val();
		var endDate = $("#filter-endDate").val();
		
		if (!!memberId || !!transType || !!beginDate || !!endDate) {
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
			url: '/member/point/log/page',
			sidePagination: 'server',
			pagination: true,
			pageList: [10, 20, 30],
			pageSize: 20,
			sortable: true,
			sortName: 'id',
	        sortOrder: 'desc',
	        toolbar: '#page-toolbar',
	        onLoadSuccess: function(data) {
	        	$filterSubmit.button('reset');
	        },
	        onLoadError: function(status) {
	        	$filterSubmit.button('reset');
	        },
	        queryParams: function(params) {
	        	
	        	var memberId = $("#filter-memberId").val();
				if (!!memberId) {
					params.memberId = memberId;
				}
	        	
				var transType = $("#filter-transType").val();
				if (!!transType) {
					params.transType = transType;
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
			    title: 'ID'
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
		        field: 'transType',
		        title: '事务类型',
		        formatter: function(value, row, index) {
		        	if(value == 0){
		        		return '登录';
		        	}else if(value == 1){
		        		return '下载';
		        	}else if(value == 2){
		        		return '分享';
		        	}else if(value == 3){
		        		return '申请认证';
		        	}else if(value == 4){
		        		return '赞助49元';
		        	}else if(value == 5){
		        		return '赞助99元';
		        	}else if(value == 6){
		        		return '设计师上传作品';
		        	}else if(value == 7){
		        		return '注册';
		        	}else if(value == 8){
		        		return '完善邮箱和手机号码';
		        	}else if(value == 9){
		        		return '异常调整';
		        	}
		        }
		    },
		    {
		        field: 'amount',
		        title: '发生金额'
		    },
		    {
		        field: 'pointBalance',
		        title: '余额'
		    },
		    {
		        field: 'relatedType',
		        title: '关联类型'
		    },{
		        field: 'relatedId',
		        title: '关联ID'
		    },
		    {
		        field: 'remark',
		        title: '备注'
		    },
		    {
		        field: 'creator',
		        title: '创建者'
		    },{
		        field: 'createTime',
		        title: '创建时间',
		        halign: 'left',
		    	align: 'center',
		    }]
		});
	}
	
});