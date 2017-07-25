/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify','bootstrap-dialog', 'btable' ], 
		function($, base, doT, PNotify,BootstrapDialog){
	
	'use strict';
	var $page = $('#page');
	// 初始化/绑定事件 分页过滤条件 时间区间
	base.initSimpleFilterDateRangePicker();
	
	function isParamsNotEmpty() {
		var memberId = $("#filter-memberId").val();
		var beginDate = $("#filter-beginDate").val();
		var endDate = $("#filter-endDate").val();
		
		if (!!memberId || !!beginDate || !!endDate) {
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
			url: '/member/point/page',
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
	        	
	        	var memberId = $("#filter-memberId").val();
				if (!!memberId) {
					params.memberId = memberId;
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
		        title: '操作',
		        halign: 'left',
		        align: 'center',
		        formatter: function(value, row, index) {
		        	if($("#hasPermOfUpdate").val() === 'true'){
		        	var str = '';
		        		str += '<button class="btn btn-primary btn-xs update" type="button" data-id="'+value+'" data-value="'+row.memberId+'" >调整</button>';
		        		return str;
		        	}
		        }
		    },
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
		        field: 'balance',
		        title: '积分余额'
		    },{
		        field: 'remark',
		        title: '备注'
		    },{
		        field: 'createTime',
		        title: '创建时间',
		        halign: 'left',
		    	align: 'center',
		    },{
		        field: 'updateTime',
		        title: '更新时间',
		        halign: 'left',
		    	align: 'center',
		    }
		    ]
		});
	}
	
	$('#page').on('click', '.update', function(){
		var id = $(this).data("id");
		var memberId = $(this).data("value");
		var str = '<form class="form-horizontal" id="updateMemberPoint" >'+
					'<div class="form-group">'+
						'<label class="col-sm-2 control-label">调整额度</label>'+
							'<div class="col-sm-8">'+
								'<input type="number" class="form-control" id="form-update-amount" required placeholder="如11或-33等"/>'+
							'</div>'+
						'</div>'+
						'<div class="form-group">'+
							'<label class="col-sm-2 control-label">备注</label>'+
							'<div class="col-sm-8">'+
								'<textarea row="2" class="form-control" id="form-update-remark" maxlength="200"></textarea>'+
							'</div>'+
					  '</div>'+
					'</form>';
		BootstrapDialog.show({
            title: '积分调整',
            message: $(str),
            buttons: [{
                label: '确定',
                cssClass: 'btn-primary',
                hotkey: 13,
                action: function(dialogRef) {
                	if (!$('#updateMemberPoint').valid()) {
	                    return ;
                    }
                	var data = {
                		id : id,
                		memberId : memberId,
                		amount : $('#form-update-amount').val()
                	};
                	if($('#form-update-remark').val() != null && $('#form-update-remark').val() != '' ){
                		data.remark = $('#form-update-remark').val();
                	}
                	$.ajax({
        				url : '/member/point/update',
        				type : "post",
        				data : data,
        				error : function() {
        					new PNotify({
        						title : "积分调整异常！",
        						type : "error"
        					});
        				},
        				success : function(response) {
        					if (response.success) {
        						new PNotify({
	        						title : "积分调整成功！",
	        						type : "success"
	        					});
        						dialogRef.close();
        						$('#page').bootstrapTable('refresh');
        					} else {
        						new PNotify({
            						title : "积分调整失败！",
            						text : response.msg || "",
            						type : "error"
            					});
        					}
        				}
        			});
                }
            }]
        });
	});
	
	
});