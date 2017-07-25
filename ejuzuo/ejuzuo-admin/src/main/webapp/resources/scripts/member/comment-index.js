/**
 * 
 */
require(['jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'btable'], 
		function($, base, doT, PNotify, BootstrapDialog) {
	
	'use strict';
	
	// 初始化/绑定事件 分页过滤条件 时间区间
	base.initSimpleFilterDateRangePicker();
	
	var $filterSubmit = $('#filter-submit');
	var $page = $('#page');
	var $btnAdd = $('#btn-add');
	
	// 分页
	function isParamsNotEmpty() {
		var objectId = $("#filter-objectId").val();
		var id = $("#filter-id").val();
		var userName = $("#filter-userName").val();
		var status = $("#filter-status").val();
		var beginDate = $("#filter-beginDate").val();
		var endDate = $("#filter-endDate").val();
		
		if (!!objectId || !!id || !!userName || !!status || !!beginDate || !!endDate) {
			return true;
		} else {
			return false;
		}
	}
	
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
		
		var options = $page.bootstrapTable('getOptions');
		if (options.totalRows === 0) {
			$page.bootstrapTable('refresh');
		} else {
			$page.bootstrapTable('selectPage', 1);
		}
    });
	
	var pageDetailTpl = doT.template($("#dot-page-detail").html());
	var tplPageComment = doT.template($("#dot-page-comment").html());
	$page.bootstrapTable({
		url: '/comment/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [10, 20, 30],
		pageSize: 20,
		resizable: true,
		sortable: true,
		sortName: 'id',
        sortOrder: 'desc',
        toolbar: '#page-toolbar',
        uniqueId: 'id',
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
        	
        	// 这种方式排序有点蛋疼
        	params.sort = {
        		id: 'id'
    	    }[params.sort];
        	
        	var objectId = $("#filter-objectId").val();
			if (!!objectId) {
				params.objectId = objectId;
			}
			
			var objectType = $("#filter-objectType").val();
			if (!!objectType) {
				params.objectType = objectType;
			}
        	
			var id = $("#filter-id").val();
			if (!!id) {
				params.id = id;
			}
			
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			
			var masked = $("#filter-masked").val();
			if (!!masked) {
				params.masked = masked;
			}
			
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
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
	    columns: [{
	    	title: '操作',
	    	formatter: function(value, row, index) {
	    		var html = '';
	    		if($("#hasPermOfMasked").val() === 'true'){
	    		if(row.masked === 0){
	    			html += 
	 	    			'<button type="button" class="btn btn-xs btn-warning jq-page-opt-masked" data-id="'+row.id+'">'+
	 						'<span class="fa fa-lock" aria-hidden="true"></span>&nbsp;屏蔽'+
	 					'</button>';
	    		}
	    		}
	    		if($("#hasPermOfUnmasked").val() === 'true'){
	    		if(row.masked !== 0){
	    			 html += 
	 	    			'<button type="button" class="btn btn-xs btn-info jq-page-opt-unmasked" data-id="'+row.id+'">'+
	 						'<span class="fa fa-unlock" aria-hidden="true"></span>&nbsp;解屏'+
	 					'</button>';
	    		}
	    		}
	    		if($("#hasPermOfEnable").val() === 'true'){
	    		if(row.status !== 1){
	    			 html += 
	 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-id="'+row.id+'">'+
	 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
	 					'</button>';
	    		}
	    		}
	    		if($("#hasPermOfDisable").val() === 'true'){
	    		if(row.status === 1){
	    			 html += 
	 	    			'<button type="button" class="btn btn-xs btn-danger jq-page-opt-disable" data-id="'+row.id+'">'+
	 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
	 					'</button>';
	    		}
	    		}
	    		return html;
	    	}
	    }, {
	        field: 'id',
	        title: 'ID',
	        sortable: true
	    }, {
	        field: 'memberId',
	        title: '会员ID'
	    }, {
	    	field: 'comment',
	    	title: '内容',
	    	cellStyle: function(value, row, index) {
	    		return {
	    			classes: 'content-cell'
	    		}
	    	},
	    	formatter: function(value, row, index) {
	    		return tplPageComment({comment: value});
	    	}
	    }, {
	        field: 'objectType',
	        title: '对象类型',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	var types = ["数字家居", "设计作品", "活动", "资讯", "设计师"];
	        	return types[value];
	        }
	    }, {
	        field: 'objectId',
	        title: '对象ID'
	    }, {
	        field: 'status',
	        title: '状态',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="text-danger">无效</span>';
	        	}else if(value == 1){
	        		return '<span class="text-success">有效</span>';
	        	}
	        }
	    }, {
	        field: 'masked',
	        title: '屏蔽',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="text-success">未屏蔽</span>';
	        	}else if(value == 1){
	        		return '<span class="text-danger">已屏蔽</span>';
	        	}
	        }
	    },{
	        field: 'createTime',
	        title: '评论时间',
	        halign: 'left',
	    	align: 'center',
	        sortable: true
	    }
	    ]
	});
	
	// 屏蔽
	$("#page").on("click", ".jq-page-opt-masked", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/comment/' + id + '/masked';
		var title = '屏蔽';
		var text = '';
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 解屏
	$("#page").on("click", ".jq-page-opt-unmasked", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/comment/' + id + '/unmasked';
		var title = '解屏';
		var text = '';
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/comment/' + id + '/disable';
		var title = '禁用';
		var text = '';
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/comment/' + id + '/enable';
		var title = '启用';
		var text = '';
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
});