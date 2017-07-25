/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'bootstrap-datetimepicker','ztree','btable','jquery.form'], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	var $page = $('#page');
	var oldChannelId = undefined;
	var $btnAdd = $('#btn-add');
	// 生成左侧栏目树
	var categoryNodes = [];
	$.get("/codevalue/getCollectionCode", function(data){
		categoryNodes = data;
		
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
					
					if (clickFlag === 1 && treeNode.id !== 0) {
						$('#filter-collectionCode').val(treeNode.id);
						$btnAdd.removeAttr('disabled');
						oldChannelId = treeNode.id;
					} else {
						$('#filter-collectionCode').val('');
						$btnAdd.attr('disabled', 'disabled');
						oldChannelId = undefined;
					}
					
					$page.bootstrapTable('selectPage', 1);
				}
			}
		}, categoryNodes);
	});
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/codevalue/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'asc',
        sortName: 'order_no',
        toolbar: '#page-toolbar',
        queryParams: function(params) {
			var collectionCode = $("#filter-collectionCode").val();
			if (!!collectionCode) {
				params.collectionCode = collectionCode;
			}
			var valueCode = $("#filter-valueCode").val();
			if (!!valueCode) {
				params.valueCode = valueCode;
			}
			var valueName = $("#filter-valueName").val();
			if (!!valueName) {
				params.valueName = valueName;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			return params;
        },
	    columns: [
         {
	        field: 'collectionCode',
	        title: '集合代码'
	    }, 
	    {
	        field: 'valueCode',
	        title: '值代码'
	    },
	    {
	        field: 'valueName',
	        title: '值名称',
	    },
	    {
	        field: 'parentCode',
	        title: '父代码',
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
	        field: 'orderNo',
	        title: '排序',
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
	        		str += '<button class="btn btn-primary btn-xs update" type="button" data-primary2="'+row.valueCode+'" data-primary1="'+row.collectionCode+'" >修改</button>';
	        	}
	        	if($("#hasPermOfEnable").val() === 'true'){
	        	if(row.status !== 1){
	        			str += 
		 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-primary2="'+row.valueCode+'" data-primary1="'+row.collectionCode+'" >'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
		 					'</button>';
		    		}
	        	}
	        	if($("#hasPermOfDisable").val() === 'true'){
		    		if(row.status === 1){
		    			str += 
		 	    			'<button type="button" class="btn btn-xs btn-warning jq-page-opt-disable" data-primary2="'+row.valueCode+'" data-primary1="'+row.collectionCode+'" >'+
		 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
		 					'</button>';
		    		}
	        	}
	        		return str;
	        }
	    }
	    ]
	});
	
	$('#addModal').on('show.bs.modal', function(event) {
		var collectionCode = $("#filter-collectionCode").val();
		$('#add-collectionCode').val(collectionCode);
		if(collectionCode == "10"){
			var str = "";
			for(var i=0;i<brandJson.length;i++){
				str += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'">'+brandJson[i].name+'</option>';
			}
			$("#create-brandsDiv").html(
				'<label for="title" class="control-label col-sm-2">推广品牌1</label>'+
				'<div class=" col-sm-4">'+
					'<select class="form-control" name="promote-brand">'+
						'<option value=""></option>'+
						str+
					'</select>'+
				'</div>'+
				'<label class="col-sm-2 control-label" for="form-add-status">推广品牌2</label>'+
				'<div class="col-sm-4">'+
					'<select class="form-control" name="promote-brand">'+
						'<option value=""></option>'+
						str+
					'</select>'+
				'</div>'
			);
		}else{
			$("#create-brandsDiv").html("");
		}
	});
	
	var collectionCode;
	var valueCode;
	$('#page').on('click','.update', function(){
		collectionCode = $(this).data("primary1");
		valueCode = $(this).data("primary2");
		$('#updateModal').modal('show');
	});
	//点击修改的时候触发
	var brandJson = JSON.parse($('#brands').val());
	var domainImage = $('#jq-domain-image').val();
	$('#updateModal').on('show.bs.modal', function(event) {
		$('#updateForm').resetForm();
		$.ajax({
			url : '/codevalue/viewJson',
			type : 'get',
			data : 'collectionCode='+collectionCode+'&valueCode='+valueCode,
			dataType: 'json',
			error : function() {
				new PNotify({
					title : '请求失败, 请检查网络情况. ',
					type : 'error'
				});
			},
			success : function(response) {
				$("#status").val(response.status);
				$("#orderNo").val(response.orderNo);
				$("#parentCode").val(response.parentCode);
				$("#collectionCode").val(response.collectionCode);
				$("#valueCode").val(response.valueCode);
				$("#valueName").val(response.valueName);
				if(response.collectionCode == '10'){
					var nowJson = JSON.parse(response.extension);
					var str = "";
					if(nowJson.length >= 1){
						for(var i=0;i<brandJson.length;i++){
							if(nowJson[0].id == brandJson[i].id){
								str += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'" selected >'+brandJson[i].name+'</option>';
							}else{
								str += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'" >'+brandJson[i].name+'</option>';
							}
						}
					}else{
						for(var i=0;i<brandJson.length;i++){
							str += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'" >'+brandJson[i].name+'</option>';
						}
					}
					
					var str1 = "";
					if(nowJson.length == 2){
						for(var i=0;i<brandJson.length;i++){
							if(nowJson[1].id == brandJson[i].id){
								str1 += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'" selected >'+brandJson[i].name+'</option>';
							}else{
								str1 += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'" >'+brandJson[i].name+'</option>';
							}
						}
					}else{
						for(var i=0;i<brandJson.length;i++){
							str1 += '<option value="'+brandJson[i].id+':'+brandJson[i].logoImg+'" >'+brandJson[i].name+'</option>';
						}
					}
					$("#update-brandsDiv").html(
						'<label for="title" class="control-label col-sm-2">推广品牌1</label>'+
						'<div class=" col-sm-4">'+
							'<select class="form-control" name="promote-brand">'+
								'<option value=""></option>'+
								str+
							'</select>'+
						'</div>'+
						'<label class="col-sm-2 control-label" for="form-add-status">推广品牌2</label>'+
						'<div class="col-sm-4">'+
							'<select class="form-control" name="promote-brand">'+
								'<option value=""></option>'+
								str1+
							'</select>'+
						'</div>'
					);
				}else{
					$("#update-brandsDiv").html("");
				}
			}
		});
	});
	$('#saveButton').on('click', function(){
		if($("#createForm").validate().form()){
			$('#createForm').attr("action", "/codevalue/add");
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
		$('#updateForm').attr("action", "/codevalue/update");
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
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var collectionCode = $(this).data("primary1");
		var valueCode = $(this).data("primary2");
		var url = '/codevalue/'+collectionCode+'/'+valueCode+'/disable';
		var title = '禁用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var collectionCode = $(this).data("primary1");
		var valueCode = $(this).data("primary2");
		var url = '/codevalue/'+collectionCode+'/'+valueCode+'/enable';
		var title = '启用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
});