/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','cmsUploadImg', 'bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog,cmsUploadImg){
	
	'use strict';
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/brand/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'create_time',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var id = $("#filter-id").val();
			if (!!id) {
				params.id = id;
			}
			var name = $("#filter-name").val();
			if (!!name) {
				params.name = name;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
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
	        field: 'name',
	        title: '品牌名称'
	    },
	    {
	        field: 'logoImg',
	        title: '图片地址',
	    },{
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
	        field: 'preferred',
	        title: '优先品牌',
	        formatter: function(value, row, index) {
	        	if(value != null && value == 1){
	        		return '<div class="checkbox"><label><input type="checkbox" class="changePreferred" checked="checked" data-value="0" data-id="'+row.id+'" ></label></div>';
	        	}
	        	return '<div class="checkbox"><label><input type="checkbox" class="changePreferred" data-value="1" data-id="'+row.id+'" ></label></div>';
	        }
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    },
	    {
	        field: 'updateTime',
	        title: '更新时间',
	    },
	    {
	        field: 'id',
	        title: '操作',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	var str = '';
	        	if($("#hasPermOfUpdate").val() === 'true'){
	        		str += '<button class="btn btn-primary btn-xs update" type="button" data-id="'+value+'" >修改</button>';
	        	}
	        	if(row.status !== 1){
	        		if($("#hasPermOfEnable").val() === 'true'){
	        			str += 
		 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-id="'+row.id+'" >'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
		 					'</button>';
	        		}
		    		}
		    		if(row.status === 1){
		    			if($("#hasPermOfDisable").val() === 'true'){
		    			str += 
		 	    			'<button type="button" class="btn btn-xs btn-warning jq-page-opt-disable" data-id="'+row.id+'" >'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
		 					'</button>';
		    			}
		    		}
		    		/*str += '<button class="btn btn-xs btn-danger delete" data-id="' + value + '" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';*/
	        		return str;
	        }
	    }
	    ]
	});
	
	var viewId;
	$('#page').on('click','.update', function(){
		viewId = $(this).data("id");
		$('#updateModal').modal('show');
	});
	//点击修改的时候触发
	var domainImage = $('#jq-domain-image').val();
	$('#updateModal').on('show.bs.modal', function(event) {
		$('#updateForm').resetForm();
		$.ajax({
			url : '/brand/viewJson',
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
				$("#id").val(response.id);
				$("#status").val(response.status);
				$("#name").val(response.name);
				$("#remark").val(response.remark);
				if(response.logoImg != null && response.logoImg != ""){
					$("#form-update").val(response.logoImg);
					var src = domainImage + response.logoImg;
					$('#jq-upload-thumbnail-img-update').attr('src', src);
					$('#jq-upload-thumbnail-update').removeClass('hidden');
				}
			}
		});
	});
	
	$('#preferred').on('change',function(){
		if($('#preferred').is(":checked")){
			$('#preferred').val(1);
		}else{
			$('#preferred').val(0);
		}
	});
	
	$('#saveButton').on('click', function(){
		if($("#createForm").validate().form()){
			$('#createForm').attr("action", "/brand/add");
			$('#createForm').ajaxSubmit({
				datatype: 'json',
	            success: function (map) {
	            	if(map.success){
	            		$('#addModal').modal('hide');
		            	$('#page').bootstrapTable('refresh');
		            	$('#createForm').resetForm();
	            	}else{
	            		new PNotify({
							title : map.msg,
							type : 'error'
						});
	            	}
	            },
	            error: function (xhr) {
	            	new PNotify({
						title : '新增异常！',
						type : 'error'
					});
	            }
			});
		}
	});
	
	
	$('#updateButton').on('click', function(){
		if($("#updateForm").validate().form()){
			$('#updateForm').attr("action", "/brand/update");
			
			$('#updateForm').ajaxSubmit({
				datatype: 'json',
	            success: function (map) {
	            	$('#updateModal').modal('hide');
	            	$('#page').bootstrapTable('refresh');
	            },
	            error: function (xhr) {
	            	new PNotify({
						title : '修改异常！',
						type : 'error'
					});
	            }
			});
		}
	});
	
//	$('#page').on('click','.delete', function(){
//		if(confirm("确定要删除吗？")){
//			$.ajax({
//				url : '/brand/delete',
//				type : 'get',
//				data : 'id='+$(this).data("id"),
//				dataType : 'json',
//				error : function() {
//					new PNotify({
//						title : '请求失败, 请检查网络情况. ',
//						type : 'error'
//					});
//				},
//				success: function (map) {
//	            	if(map.success){
//		            	$('#page').bootstrapTable('refresh');
//		            	new PNotify({
//							title : '删除成功',
//							type : 'success'
//						});
//	            	}else{
//	            		new PNotify({
//							title : map.msg,
//							type : 'error'
//						});
//	            	}
//	            },
//			});
//		}
//	});
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var url = '/brand/' + id + '/disable';
		var title = '禁用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/brand/' + id + '/enable';
		var title = '启用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
	$('#page').on('change','.changePreferred',function(){
		$.ajax({
			url : '/brand/changePreferred',
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
	
	cmsUploadImg.init();
});