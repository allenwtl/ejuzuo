require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','ztree','btable'], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	var $page = $('#page');
	var oldChannelId = undefined;
	// 生成左侧栏目树
	var categoryNodes = JSON.parse($('#lstTree').val());
	$.fn.zTree.init($('#ztree-categories'), {
		data: {
			keep: {
				parent: true
			},
			simpleData: {
				enable: true
			}
		},
		view: {
			selectedMulti: false
		},
		callback: {
			onClick: function(event, treeId, treeNode, clickFlag) {
				if (clickFlag === 1 && oldChannelId === treeNode.id) {
					return ;
				}
				
				if (clickFlag === 1 && treeNode.id !== 100) {
					$('#filter-designerType').val(treeNode.id);
					oldChannelId = treeNode.id;
				} else {
					$('#filter-designerType').val('');
					oldChannelId = undefined;
				}
				
				$page.bootstrapTable('selectPage', 1);
			}
		}
	}, categoryNodes);
	
	$('#filter-startTime-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$('#filter-endTime-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$("#filter-startTime-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-endTime-group').data("DateTimePicker").minDate(false);;
		} else {
			e.date.hour(0).minute(0).second(0).millisecond(0);
			$('#filter-endTime-group').data("DateTimePicker").minDate(e.date);
		}
	});
	$("#filter-endTime-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-startTime-group').data("DateTimePicker").maxDate(false);
		} else {
			e.date.hour(23).minute(59).second(59).millisecond(999);
			$('#filter-startTime-group').data("DateTimePicker").maxDate(e.date);
		}
	});
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	var stylesJson = JSON.parse($('#styles').val());
	$('#page').bootstrapTable({
		url: '/designer/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [10, 20, 30],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'id',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var id = $("#id").val();
			if (!!id) {
				params.id = id;
			}
			var memberId = $("#memberId").val();
			if (!!memberId) {
				params.memberId = memberId;
			}
			var name = $("#name").val();
			if (!!name) {
				params.name = name;
			}
			var designerType = $("#filter-designerType").val();
			if (!!designerType) {
				params.designerType = designerType;
			}
			var status = $("#status").val();
			if (!!status) {
				params.status = status;
			}
			var startTime = $("#startTime").val();
			if (!!startTime) {
				params.startTime = startTime;
			}
			var endTime = $("#endTime").val();
			if (!!endTime) {
				params.endTime = endTime;
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
		    	if($("#hasPermOfInfo").val() === 'true'){
		    	var html = '<a role="button" class="btn btn-xs btn-default" href="/designer/' + row.id + '/detail">详情</a>';
		    	}
		    	if($("#hasPermOfApprove").val() === 'true'){
		    	if(row.status == 1){
		    		html += '<a role="button" class="btn btn-xs btn-warning" href="/designer/' + row.id + '/approval">审核</a>';
		    	}
		    	}
			return html;
		    }
		},
        {
	        field: 'id',
	        title: 'ID',
	    }, 
	    {
	        field: 'memberId',
	        title: '用户ID'
	    },
	    {
	        field: 'name',
	        title: '名称'
	    },
	    {
	        field: 'designerType',
	        title: '类别',
	        formatter: function(value, row, index) {
	        	if(value == 3){
	        		return '<span class="label label-success">个人设计师</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-success">设计公司</span>';
	        	}else if(value == 2){
	        		return '<span class="label label-success">装修公司</span>';
	        	}
	        }
	    },
	    {
	        field: 'adeptStyles',
	        title: '擅长风格',
	        cellStyle: function(value, row, index) {
	    		return {
	    			classes: 'overflow-cell'
	    		}
	    	},
	        formatter: function(value, row, index) {
	        	var result = '';
	        	var str=value.split(",");      
	        	    for (var i=0;i<str.length ;i++ ){   
	        	        for(var j=0;j<stylesJson.length ;j++ ){
	        	        	if(stylesJson[j].valueCode == str[i]){
	        	        		result += " "+stylesJson[j].valueName;
	        	        		break;
	        	        	}
	        	        }
	        	    }
	        	return '<span title="'+result+'">'+result+'</span>';;
	        }
	    },
	    {
	        field: 'homed',
	        title: '推荐首页',
	        formatter: function(value, row, index) {
	        	if(value != null && value == 1){
	        		return '<div class="checkbox"><label><input type="checkbox" class="changeHomed" checked="checked" data-value="0" data-id="'+row.id+'" ></label></div>';
	        	}
	        	return '<div class="checkbox"><label><input type="checkbox" class="changeHomed" data-value="1" data-id="'+row.id+'" ></label></div>';
	        }
	    },
	    {
	        field: 'status',
	        title: '状态',
	        formatter: function(value, row, index) {//0暂存，1待审核，2审核通过，3审核退回
	        	if(value == 0){
	        		return '<span class="label label-default">暂存</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-warning">待审核</span>';
	        	}else if(value == 2){
	        		return '<span class="label label-success">审核通过</span>';
	        	}else if(value == 3){
	        		return '<span class="label label-danger">审核退回</span>';
	        	}
	        }
	    },
	    {
	        field: 'verifior',
	        title: '审核人',
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    }
	    ]
	});
	
	$('#page').on('change','.changeHomed',function(){
		$.ajax({
			url : '/designer/changeHomed',
			type : 'get',
			data : 'value='+$(this).data("value")+'&id='+$(this).data("id"),
			dataType : 'json',
			error : function() {
				new PNotify({
					title : '请求失败, 请检查网络情况. ',
					type : 'error'
				});
			},
			success : function(response) {
				if(response){
					$('#page').bootstrapTable('refresh');
					new PNotify({
						title : '设置成功',
						type : 'success'
					});
				}else{
					$('#page').bootstrapTable('refresh');
					new PNotify({
						title : response.msg,
						type : 'error'
					});
				}
			}
		});
	});
});