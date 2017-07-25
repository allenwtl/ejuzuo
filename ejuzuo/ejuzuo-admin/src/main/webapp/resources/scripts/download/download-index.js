/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	var $filterSubmit = $('#filter-submit');
	$filterSubmit.on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	var pageDetailTpl = doT.template($("#dot-page-detail").html());
	$('#page').bootstrapTable({
		url: '/download/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'id',
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
			var fileId = $("#filter-fileId").val();
			if (!!fileId) {
				params.fileId = fileId;
			}
			var goodsId = $("#filter-goodsId").val();
			if (!!goodsId) {
				params.goodsId = goodsId;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			var payStatus = $("#filter-payStatus").val();
			if (!!payStatus) {
				params.payStatus = payStatus;
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
	        title: '用户账号'
	    },
	    {
	        field: 'goodsId',
	        title: '商品ID',
	    },
	    {
	        field: 'goodsName',
	        title: '商品名称',
	    },
	    {
	        field: 'fileId',
	        title: '文件ID',
	    },
	    {
	        field: 'pointPrice',
	        title: '所需积分',
	    },
	    {
	        field: 'actualPrice',
	        title: '实际积分',
	    },
	    {
	    	field: 'payStatus',
	    	title: '支付状态',
	    	halign: 'left',
	    	align: 'center',
	    	formatter: function(value, row, index) {
	    		var payStatus = [
					'<span class="label label-warning">未支付</span>',
					'<span class="label label-success">已支付</span>'
				];
	    		if (!!value || value === 0) {
	    			return payStatus[value];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	    	field: 'status',
	    	title: '状态',
	    	halign: 'left',
	    	align: 'center',
	    	formatter: function(value, row, index) {
	    		var status = [
					'<span class="label label-danger">无效</span>',
					'<span class="label label-success">有效</span>'
				];
	    		if (!!value || value === 0) {
	    			return status[value];
	    		} else {
	    			return null;
	    		}
	    	}
	    },
	    {
	        field: 'payTime',
	        title: '支付时间',
	    },
	    {
	        field: 'expire',
	        title: '下载有效期',
	    }
	    ]
	});
	
});