/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify','bootstrap-dialog', 'base-page', 'btable' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/member/vip/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'create_time',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
        	var viped = $("#filter-viped").val();
			if (!!viped) {
				params.viped = viped;
			}
			
			var memberId = $("#filter-memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
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
			    		str += '<button class="btn btn-danger btn-xs update" type="button" data-id="'+value+'" data-value="'+row.memberId+'" >调整</button>';
			    		return str;
			    	}
			    }
			},
		    {
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
		        field: 'vip',
		        title: '是否VIP',
		        formatter: function(value, row, index) {
		        	if(value){
		        		return '<span class="label label-success">是VIP</span>';
		        	}else{
		        		return '<span class="label label-default">非VIP</span>';
		        	}
		        }
		    },
		    {
		        field: 'startTime',
		        title: '开始时间',
		        formatter: function(value, row, index) {
		        	if(value == null || value == ''){
		        		return '';
		        	}
		        	return value.split(" ")[0];
		        }
		    },
		    {
		        field: 'endTime',
		        title: '结束时间',
		        formatter: function(value, row, index) {
		        	if(value == null || value == ''){
		        		return '';
		        	}
		        	return value.split(" ")[0];
		        }
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
		        field: 'updateTime',
		        title: '更新时间',
		        halign: 'left',
		    	align: 'center'
		    },
		    {
		        field: 'updator',
		        title: '更新者'
		    },
		    {
		        field: 'remark',
		        title: '备注'
		    },
		    ]
        });
	
	$('#page').on('click', '.update', function(){
		var id = $(this).data("id");
		var memberId = $(this).data("value");
		var str = '<form class="form-horizontal" id="updateMemberPoint" >'+
					'<div class="form-group">'+
						'<label class="col-sm-2 control-label">调整月数</label>'+
							'<div class="col-sm-8">'+
								'<input type="number" class="form-control" id="form-update-period" required placeholder="如3或-1等"/>'+
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
            title: 'VIP调整',
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
                		period : $('#form-update-period').val()
                	};
                	if($('#form-update-remark').val() != null && $('#form-update-remark').val() != '' ){
                		data.remark = $('#form-update-remark').val();
                	}
                	$.ajax({
        				url : '/member/vip/update',
        				type : "post",
        				data : data,
        				error : function() {
        					new PNotify({
        						title : "VIP调整异常！",
        						type : "error"
        					});
        				},
        				success : function(response) {
        					if (response.success) {
        						new PNotify({
	        						title : "VIP调整成功！",
	        						type : "success"
	        					});
        						dialogRef.close();
        						$('#page').bootstrapTable('refresh');
        					} else {
        						new PNotify({
            						title : "VIP调整失败！",
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