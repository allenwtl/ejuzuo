/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'btable' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	// 初始化/绑定事件 分页过滤条件 时间区间
	base.initSimpleFilterDateRangePicker();
	
	
	function isParamsNotEmpty() {
		var id = $("#filter-id").val();
		var account = $("#filter-account").val();
		var nickName = $("#filter-nickName").val();
		var verified = $("#filter-verified").val();
		var locked = $("#filter-locked").val();
		var status = $("#filter-status").val();
		var activeStatus = $("#filter-activeStatus").val();
		var beginDate = $("#filter-beginDate").val();
		var endDate = $("#filter-endDate").val();
		
		if (!!id || !!account || !!nickName || !!verified 
				|| !!locked || !!status || !!activeStatus 
				|| !!beginDate || !!endDate) {
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
			url: '/member/page',
			sidePagination: 'server',
			pagination: true,
			pageList: [10, 20, 30],
			pageSize: 20,
			sortable: true,
			sortName: 'create_time',
	        sortOrder: 'desc',
	        toolbar: '#page-toolbar',
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
	        	
				var account = $("#filter-account").val();
				if (!!account) {
					params.account = account;
				}
				
				var nickName = $("#filter-nickName").val();
				if (!!nickName) {
					params.nickName = nickName;
				}
				
				var verified = $("#filter-verified").val();
				if (!!verified) {
					params.verified = verified;
				}
				
				var locked = $("#filter-locked").val();
				if (!!locked) {
					params.locked = locked;
				}
				
				var status = $("#filter-status").val();
				if (!!status) {
					params.status = status;
				}
				
				var activeStatus = $("#filter-activeStatus").val();
				if (!!activeStatus) {
					params.activeStatus = activeStatus;
				}
				
				// 根据唯一条件过滤掉部分条件
				if (!id && !account && !name) {
					var beginDate = $("#filter-beginDate").val();
					if (!!beginDate) {
						params.beginDate = beginDate;
					}

					var endDate = $("#filter-endDate").val();
					if (!!endDate) {
						params.endDate = endDate;
					}
				}
				
				return params;
	        },
		    columns: [{
		        field: 'id',
		        title: 'ID',
		        sortable: true
		    }, {
		        field: 'account',
		        title: '账号'
		    },{
		        field: 'nickName',
		        title: '昵称'
		    }, {
		    	field: 'verified',
		    	title: '认证',
		    	halign: 'left',
		    	align: 'center',
		    	formatter: function(value, row, index) {
		    		var verified = [
						'<span class="label label-warning">未认证</span>',
						'<span class="label label-success">已认证</span>'
					];
		    		if (!!value || value === 0) {
		    			return verified[value];
		    		} else {
		    			return null;
		    		}
		    	}
		    }, {
		    	field: 'locked',
		    	title: '锁定',
		    	halign: 'left',
		    	align: 'center',
		    	formatter: function(value, row, index) {
		    		var locked = [
						'<span class="label label-success">未锁定</span>',
						'<span class="label label-danger">已锁定</span>'
					];
		    		if (!!value || value === 0) {
		    			return locked[value];
		    		} else {
		    			return null;
		    		}
		    	}
		    }, {
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
		    },{
		        field: 'registerTime',
		        title: '注册时间',
		        halign: 'left',
		    	align: 'center',
		        sortable: true,
		        sortName: 'register_time'
		    }, {
		    	field: 'id',
		    	title: '操作',
		    	formatter: function(value, row, index) {
		    		
		    		if($("#hasPermOfInfo").val() === 'true'){
		    		var html = 
		    			'<button type="button" class="btn btn-xs btn-default jq-page-opt-detail" data-id="'+row.id+'">'+
							'<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>&nbsp;详情'+
						'</button>';
		    		}
		    		
		    		if($("#hasPermOfLock").val() === 'true'){
		    		if(row.locked === 0){
		    			html += 
		 	    			'<button type="button" class="btn btn-xs btn-warning jq-page-opt-lock" data-id="'+row.id+'" data-account="'+row.account+'">'+
		 						'<span class="fa fa-lock" aria-hidden="true"></span>&nbsp;锁定'+
		 					'</button>';
		    		}
		    		}
		    		if($("#hasPermOfUnlock").val() === 'true'){
		    		if(row.locked !== 0){
		    			 html += 
		 	    			'<button type="button" class="btn btn-xs btn-info jq-page-opt-unlock" data-id="'+row.id+'" data-account="'+row.account+'">'+
		 						'<span class="fa fa-unlock" aria-hidden="true"></span>&nbsp;解锁'+
		 					'</button>';
		    		}
		    		}
		    		if($("#hasPermOfEnable").val() === 'true'){
		    		if(row.status !== 1){
		    			 html += 
		 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-id="'+row.id+'" data-account="'+row.account+'">'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
		 					'</button>';
		    		}
		    		}
		    		if($("#hasPermOfDisble").val() === 'true'){
		    		if(row.status === 1){
		    			 html += 
		 	    			'<button type="button" class="btn btn-xs btn-danger jq-page-opt-disable" data-id="'+row.id+'" data-account="'+row.account+'">'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
		 					'</button>';
		    		}
		    		}
		    		return html;
		    	}
		    }]
		});
	}
	
	var logDetailTpl = doT.template($('#dot-detail').html());
	$('#page').on('click', '.jq-page-opt-detail', function() {
		var _self = $(this);
		var id = _self.data("id");
		
		// ajax call
		$.ajax({
			url : '/member/' + id + '/info',
			type : 'GET',
			dataType : 'json',
			complete : function() {
			},
			error : function(jqXHR) {
				new PNotify({
					title: '获取基础信息异常！',
					text: jqXHR.responseJSON.msg || '',
					type: 'error'
				});
			},
			success : function(response) {
				if (!response.success) {
					new PNotify({
						title: '获取基础信息失败！',
						text: response.msg || '',
						type: 'error'
					});
				} else {
					var jsonExt = {};
					jsonExt.verified = [
		 				'<span class="label label-warning">未认证</span>',
		 				'<span class="label label-success">已认证</span>'
		 			];
		 			
					jsonExt.locked = [
		  				'<span class="label label-success">未锁定</span>',
		  				'<span class="label label-danger">已锁定</span>'
		  			];
		 			
					jsonExt.status = [
		  				'<span class="label label-danger">无效/span>',
		  				'<span class="label label-success">有效</span>'
		  			];
		 			
					
					var item = response.data;
					item.verified = jsonExt.verified[item.verified];
					item.locked = jsonExt.locked[item.locked];
					item.status = jsonExt.status[item.status];
					if(item.profileImg != null && item.profileImg != ''){
						item.profileImg = $('#domainImage').val() + item.profileImg;
					}else{
						item.profileImg = null;
					}
					$("#model-detail-body").html(logDetailTpl(item));
					$('#model-detail').modal();
				}
			}
		});
		
	});
	
	// 锁定
	$("#page").on("click", ".jq-page-opt-lock", function() {
		var _self = $(this);
		var id = _self.data("id");
		var account = _self.data("account");
		
		var url = '/member/' + id + '/lock';
		var title = '锁定用户账号';
		var text = '账号: ' + account;
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 解锁
	$("#page").on("click", ".jq-page-opt-unlock", function() {
		var _self = $(this);
		var id = _self.data("id");
		var account = _self.data("account");
		
		var url = '/member/' + id + '/unlock';
		var title = '解锁用户账号';
		var text = '账号: ' + account;
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var account = _self.data("account");
		
		var url = '/member/' + id + '/disable';
		var title = '禁用用户账号';
		var text = '账号: ' + account;
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var account = _self.data("account");
		
		var url = '/member/' + id + '/enable';
		var title = '启用用户账号';
		var text = '账号: ' + account;
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
});