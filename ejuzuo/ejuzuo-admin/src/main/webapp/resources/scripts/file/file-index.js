/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	var resUrl = "http://r.ejuzuo.com/";
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
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/file/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'update_time',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var id = $("#filter-id").val();
			if (!!id) {
				params.id = id;
			}
			var fileName = $("#filter-fileName").val();
			if (!!fileName) {
				params.fileName = fileName;
			}
			var ext = $("#filter-ext").val();
			if (!!ext) {
				params.ext = ext;
			}
			var relatedType = $("#filter-relatedType").val();
			if (!!relatedType) {
				params.relatedType = relatedType;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			var uploader = $("#filter-uploader").val();
			if (!!uploader) {
				params.uploader = uploader;
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
	    columns: [
         {
	        field: 'id',
	        title: 'ID',
	        sortable: true
	    }, 
	    {
	        field: 'fileName',
	        title: '文件名'
	    },
	    {
	        field: 'size',
	        title: '文件大小[byte]',
	    },
	    {
	        field: 'ext',
	        title: '扩展名',
	    },
//	    {
//	        field: 'path',
//	        title: '路径',
//	    },
	    {
	        field: 'uploader',
	        title: '上传者',
	    },
	    {
	        field: 'uploadTime',
	        title: '上传时间',
	    },
	    {
	        field: 'relatedType',
	        title: '关联类型',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '无';
	        	}else if(value == 1){
	        		return '内容';
	        	}else if(value == 2){
	        		return '数字家居';
	        	}else if(value == 1){
	        		return '设计作品';
	        	}
	        }
	    },
	    {
	        field: 'relatedId',
	        title: '关联ID',
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
	        field: 'id',
	        title: '操作',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	var str = '';
	        	if($("#hasPermOfInfo").val() === 'true'){
	        		str += '<button class="btn btn-primary btn-xs detail" type="button" data-id="'+value+'" >详情</button>';
	        	}
	        		/*str += '<a role="button" class="btn btn-xs btn-info" href="/file/' + row.id + '/download">下载</a>';*/
	        		return str;
	        }
	    }
	    ]
	});
	
	var logDetailTpl = doT.template($('#dot-detail').html());
	$('#page').on('click','.detail', function(){
		var viewId = $(this).data("id");
		$('#updateForm').resetForm();
		$.ajax({
			url : '/file/viewJson',
			type : 'get',
			data : 'id='+viewId,
			dataType: 'json',
			error : function() {
				new PNotify({
					title : '请求失败, 请检查网络情况. ',
					type : 'error'
				});
			},
			success : function(response) {
				var jsonExt = {};
				jsonExt.status = [
	  				'<span class="label label-danger">无效/span>',
	  				'<span class="label label-success">有效</span>'
	  			];
				jsonExt.relatedType = [
   	  				'<span class="label label-success">无</span>',
	  				'<span class="label label-success">内容</span>',
	  				'<span class="label label-success">数字家居</span>',
	  				'<span class="label label-success">设计作品</span>'
	  			];
				response.status = jsonExt.status[response.status];
				response.relatedType = jsonExt.relatedType[response.relatedType];
				/**如果是图片，就显示出来*/
				response.hidden = 'hidden'; // 默认隐藏图片
				if(response.path.split(".")[1]=='jpg'||response.path.split(".")[1]=='jpeg'||response.path.split(".")[1]=='png'||response.path.split(".")[1]=='gif'||response.path.split(".")[1]=='bmp'||response.path.split(".")[1]=='webp'||response.path.split(".")[1]=='bpg'){
					response.image = resUrl + response.path; 
					response.hidden = '';
				}
				$("#model-detail-body").html(logDetailTpl(response));
				$('#detailModal').modal('show');
			}
		});
	});
});