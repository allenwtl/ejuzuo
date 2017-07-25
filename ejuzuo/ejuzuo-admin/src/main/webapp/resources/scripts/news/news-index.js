/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	$('#filter-beginDate-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$('#filter-endDate-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$('#filter-publishBeginDate-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$('#filter-publishEndDate-group').datetimepicker({
		format : "YYYY-MM-DD",
		locale : 'zh-cn',
		showTodayButton : true,
		showClear : true,
		showClose : true
	});
	$("#filter-beginDate-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-endDate-group').data("DateTimePicker").minDate(false);;
		} else {
			e.date.hour(0).minute(0).second(0).millisecond(0);
			$('#filter-endDate-group').data("DateTimePicker").minDate(e.date);
		}
	});
	$("#filter-endDate-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-beginDate-group').data("DateTimePicker").maxDate(false);
		} else {
			e.date.hour(23).minute(59).second(59).millisecond(999);
			$('#filter-beginDate-group').data("DateTimePicker").maxDate(e.date);
		}
	});
	
	$("#filter-publishBeginDate-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-publishEndDate-group').data("DateTimePicker").minDate(false);;
		} else {
			e.date.hour(0).minute(0).second(0).millisecond(0);
			$('#filter-publishEndDate-group').data("DateTimePicker").minDate(e.date);
		}
	});
	$("#filter-publishEndDate-group").on("dp.change", function(e) {
		if (!e.date) {
			$('#filter-publishBeginDate-group').data("DateTimePicker").maxDate(false);
		} else {
			e.date.hour(23).minute(59).second(59).millisecond(999);
			$('#filter-publishBeginDate-group').data("DateTimePicker").maxDate(e.date);
		}
	});
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/news/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [10, 20, 30],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'id',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
        	var category = $("#category").val();
			if (!!category) {
				params.category = category;
			}
			var id = $("#id").val();
			if (!!id) {
				params.id = id;
			}
			var title = $("#title").val();
			if (!!title) {
				params.title = title;
			}
			var publisher = $("#publisher").val();
			if (!!publisher) {
				params.publisher = publisher;
			}
			var editStatus = $("#editStatus").val();
			if (!!editStatus) {
				params.editStatus = editStatus;
			}
			var beginDate = $("#beginDate").val();
			if (!!beginDate) {
				params.beginDate = beginDate;
			}
			var endDate = $("#endDate").val();
			if (!!endDate) {
				params.endDate = endDate;
			}
			var publishBeginDate = $("#publishBeginDate").val();
			if (!!publishBeginDate) {
				params.publishBeginDate = publishBeginDate;
			}
			var publishEndDate = $("#publishEndDate").val();
			if (!!publishEndDate) {
				params.publishEndDate = publishEndDate;
			}
			return params;
        },
	    columns: [
        {
	        field: 'id',
	        title: 'ID',
	    },
	    {
	        field: 'category',
	        title: '类别',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="label label-success">新闻资讯</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-info">网站信息</span>';
	        	}else if(value == 2){
	        		return '<span class="label label-default">品牌介绍</span>';
	        	}
	        }
	    },
	    {
	        field: 'title',
	        title: '标题'
	    },
	    {
	        field: 'editStatus',
	        title: '编辑状态',
	        formatter: function(value, row, index) {
	        	if(value == 1){
	        		return '<span class="label label-success">发布</span>';
	        	}else if(value == 0){
	        		return '<span class="label label-default">暂存</span>';
	        	}
	        }
	    },
	    {
	        field: 'publisher',
	        title: '发布人',
	    },
	    {
	        field: 'status',
	        title: '状态',
	        formatter: function(value, row, index) {
	        	if(value == 1){
	        		return '<span class="label label-success">有效</span>';
	        	}else if(value == 0){
	        		return '<span class="label label-danger">无效</span>';
	        	}
	        }
	    }, 
	    {
	        field: 'createTime',
	        title: '创建时间'
	    },
	    {
	        field: 'id',
	        title: '操作',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	var html = '';
	        	if($("#hasPermOfInfo").val() === 'true'){
		        	html = '<a role="button" class="btn btn-xs btn-default" href="/news/' + row.id + '/detail">详情</a>';
	        	}
	        	if(row.editStatus == 0){
	        		if($("#hasPermOfFabu").val() === 'true'){
	    				html += '<a role="button" class="btn btn-xs btn-warning" href="/news/' + row.id + '/edit">发布</a>';
	        		}
	        		}else if(row.editStatus == 1){
	        			if($("#hasPermOfUpdate").val() === 'true'){
	    				html += '<a role="button" class="btn btn-xs btn-primary" href="/news/' + row.id + '/edit">修改</a>';
	        			}
	        		}
	    			if(row.status !== 1){
	    				if($("#hasPermOfEnable").val() === 'true'){
		    			 html += 
		 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-id="'+row.id+'" >'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
		 					'</button>';
	    				}
		    		}
		    		if(row.status === 1){
		    			if($("#hasPermOfDisable").val() === 'true'){
		    			 html += 
		 	    			'<button type="button" class="btn btn-xs btn-danger jq-page-opt-disable" data-id="'+row.id+'" >'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
		 					'</button>';
		    			}
		    		}
		    		html += '<a role="button" target="_blank" class="btn btn-xs btn-primary" href="http://www.ejuzuo.com/newsInfo/detail/'+row.id+'">预览</a>';
	    		return html;
	        }
	    }
	    ]
	});
	
	$('#btn-add').on('click', function(){
		window.location.href = "/news/addPage"
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var url = '/news/' + id + '/disable';
		var title = '禁用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/news/' + id + '/enable';
		var title = '启用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
});