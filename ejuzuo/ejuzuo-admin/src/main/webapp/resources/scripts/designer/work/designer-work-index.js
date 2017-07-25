require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','btable'], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	var $page = $('#page');
	
	$('#filter-beginUploadTime-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$('#filter-endUploadTime-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$("#filter-beginUploadTime-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-endUploadTime-group').data("DateTimePicker").minDate(false);;
		} else {
			e.date.hour(0).minute(0).second(0).millisecond(0);
			$('#filter-endUploadTime-group').data("DateTimePicker").minDate(e.date);
		}
	});
	$("#filter-endUploadTime-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-beginUploadTime-group').data("DateTimePicker").maxDate(false);
		} else {
			e.date.hour(23).minute(59).second(59).millisecond(999);
			$('#filter-beginUploadTime-group').data("DateTimePicker").maxDate(e.date);
		}
	});
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	var stylesJson = JSON.parse($('#styles').val());
	$('#page').bootstrapTable({
		url: '/designer/work/page',
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
			var editStatus = $("#editStatus").val();
			if (!!editStatus) {
				params.editStatus = editStatus;
			}
			var status = $("#status").val();
			if (!!status) {
				params.status = status;
			}
			var beginUploadTime = $("#beginUploadTime").val();
			if (!!beginUploadTime) {
				params.beginUploadTime = beginUploadTime;
			}
			var endUploadTime = $("#endUploadTime").val();
			if (!!endUploadTime) {
				params.endUploadTime = endUploadTime;
			}
			return params;
        },
	    columns: [
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
	        title: '作品名称'
	    },
	    {
	        field: 'style',
	        title: '风格',
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
	        	return result;
	        }
	    },
	    {
	        field: 'uploadTime',
	        title: '上传时间'
	    },
	    {
	        field: 'editStatus',
	        title: '编辑状态',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="label label-default">暂存</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-success">发布</span>';
	        	}
	        }
	    },
	    {
	        field: 'status',
	        title: '状态',
	        formatter: function(value, row, index) {//0暂存，1待审核，2审核通过，3审核退回
	        	if(value == 0){
	        		return '<span class="label label-danger">无效</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-success">有效</span>';
	        	}
	        }
	    },
	    {
	        field: 'id',
	        title: '操作',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	if($("#hasPermOfInfo").val() === 'true'){
	        	var html = '<a role="button" class="btn btn-xs btn-default" href="/designer/work/' + row.id + '/detail">详情</a>';
	        	}
	        	if($("#hasPermOfEnable").val() === 'true'){
	        	if(row.status !== 1){
	    			 html += 
	 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-id="'+row.id+'" >'+
	 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
	 					'</button>';
	    		}
	        	}
	        	if($("#hasPermOfDisable").val() === 'true'){
	    		if(row.status === 1){
	    			 html += 
	 	    			'<button type="button" class="btn btn-xs btn-danger jq-page-opt-disable" data-id="'+row.id+'" >'+
	 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
	 					'</button>';
	    		}
	        	}
	    		return html;
	        }
	    }
	    ]
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var url = '/designer/work/' + id + '/disable';
		var title = '禁用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/designer/work/' + id + '/enable';
		var title = '启用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
});