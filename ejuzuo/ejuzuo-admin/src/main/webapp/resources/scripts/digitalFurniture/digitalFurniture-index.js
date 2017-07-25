require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','ztree','btable'], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	var stylesJson = JSON.parse($('#styles').val());
	var spaceCategorysJson = JSON.parse($('#spaceCategorys').val());
	var spaceSmallCategorysJson = JSON.parse($('#spaceSmallCategorys').val());
	var brandsJson = JSON.parse($('#brands').val());
	var $page = $('#page');
	var oldChannelId = undefined;
	var $btnAdd = $('#btn-add');
	// 生成左侧栏目树
	var categoryNodes = [];
	$.get("/digital_furniture/getBrand", function(data){
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
						$('#filter-brand').val(treeNode.id);
						$btnAdd.removeAttr('disabled');
						oldChannelId = treeNode.id;
//						var spaceSmallCategoryOption = "";
//						for(var i=0; i<spaceSmallCategorysJson.length; i++){
//							if(spaceSmallCategorysJson[i].parentCode == oldChannelId){
//								spaceSmallCategoryOption += '<option value="'+spaceSmallCategorysJson[i].valueCode+'">'+spaceSmallCategorysJson[i].valueName+'</option>';
//							}
//						}
//						$('#spaceSmallCategoryDiv').html('<label class="sr-only" for="filter-spaceSmallCategory">空间小类</label>'
//														+'<select class="form-control" id="filter-spaceSmallCategory">'
//														+'<option value>--空间小类--</option>'
//														+spaceSmallCategoryOption
//														+'</select>');
					} else {
						$('#filter-brand').val('');
						$btnAdd.attr('disabled', 'disabled');
						oldChannelId = undefined;
					}
					
					$page.bootstrapTable('selectPage', 1);
				}
			}
		}, categoryNodes);
	});
	
	var spaceCategoryStr = '<option value>--空间大类--</option>'
	for(var i=0; i<spaceCategorysJson.length; i++){
		spaceCategoryStr += '<option value="'+spaceCategorysJson[i].valueCode+'">'+spaceCategorysJson[i].valueName+'</option>';
	}
	$('#filter-spaceCategory').html(spaceCategoryStr);
	
	$('#filter-spaceCategory').on('change',function(){
		var spaceCategory = $('#filter-spaceCategory option:selected').val();
		var str = '<option value>--空间小类--</option>'
		for(var i=0; i<spaceSmallCategorysJson.length; i++){
			if(spaceSmallCategorysJson[i].parentCode == spaceCategory){
			str += '<option value="'+spaceSmallCategorysJson[i].valueCode+'">'+spaceSmallCategorysJson[i].valueName+'</option>';
			}
		}
		$('#filter-spaceSmallCategory').html(str);
	});
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	var styleOption = "";
	for(var i=0; i<stylesJson.length; i++){
		styleOption += '<option value="'+stylesJson[i].valueCode+'">'+stylesJson[i].valueName+'</option>'
	}
	$('#filter-style').append(styleOption);
	
	$('#page').bootstrapTable({
		url: '/digital_furniture/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [10, 20, 30],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'id',
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
			var spaceCategory = $("#filter-spaceCategory").val();
			if (!!spaceCategory) {
				params.spaceCategory = spaceCategory;
			}
			var digitalType = $("#filter-digitalType").val();
			if (!!digitalType) {
				params.digitalType = digitalType;
			}
			var spaceSmallCategory = $("#filter-spaceSmallCategory").val();
			if (!!spaceSmallCategory) {
				params.spaceSmallCategory = spaceSmallCategory;
			}
			var style = $("#filter-style").val();
			if (!!style) {
				params.style = style;
			}
			var brand = $("#filter-brand").val();
			if (!!brand) {
				params.brand = brand;
			}
			var shelfStatus = $("#filter-shelfStatus").val();
			if (!!shelfStatus) {
				params.shelfStatus = shelfStatus;
			}
			var status = $("#filter-status").val();
			if (!!status) {
				params.status = status;
			}
			var type = $("#filter-type").val();
			if (!!type) {
				params.type = type;
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
	        	var html = "";
	        	if($("#hasPermOfInfo").val() === 'true'){
	        	html = '<a role="button" class="btn btn-xs btn-default" href="/digital_furniture/' + row.id + '/detail">详情</a>';
	        	}
	        	if(row.shelfStatus != 1){
	        		if($("#hasPermOfShangjia").val() === 'true'){
    				html += '<a role="button" class="btn btn-xs btn-warning jq-page-shelfStatus" data-id="'+row.id+'" data-value="1" href="javascript:void(0)">上架</a>';
	        		}
	        	}else if(row.shelfStatus == 1){
	        		if($("#hasPermOfXiajia").val() === 'true'){
	        		html += '<a role="button" class="btn btn-xs btn-danger jq-page-shelfStatus" data-id="'+row.id+'" data-value="2" href="javascript:void(0)">下架</a>';
	        		}
	        	}
	        	if($("#hasPermOfUpdate").val() === 'true'){
				html += '<a role="button" class="btn btn-xs btn-primary" href="/digital_furniture/' + row.id + '/edit">编辑</a>';
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
	    		html += '<a role="button" target="_blank" class="btn btn-xs btn-primary" href="http://www.ejuzuo.com/digital/digitalDetail/'+row.id+'">预览</a>';
	    		if($("#hasPermOfDownload").val() === 'true'){
	    		html += '<a role="button" class="btn btn-xs btn-warning" href="/file/'+row.fileId+'/download">下载</a>';
	    		}
	    		return html;
	        }
	    },
	    {
	        field: 'id',
	        title: 'ID',
	    },
	    {
	        field: 'brand',
	        title: '品牌',
	        formatter: function(value, row, index) {
	        	for(var i=0; i<brandsJson.length; i++){
	        		if(brandsJson[i].id == value){
	        			return brandsJson[i].name;
	        		}
	        	}
	        }
	    },
	    {
	        field: 'spaceCategory',
	        title: '空间大类',
	        formatter: function(value, row, index) {
	        	for(var i=0; i<spaceCategorysJson.length; i++){
	        		if(spaceCategorysJson[i].valueCode == value){
	        			return spaceCategorysJson[i].valueName;
	        		}
	        	}
	        }
	    },
	    {
	        field: 'spaceSmallCategory',
	        title: '空间小类',
	        formatter: function(value, row, index) {
	        	var str = '';
	        	var smallCategorys = value.split(",");
	        	for(var j=0;j<smallCategorys.length; j++){
	        		for(var i=0; i<spaceSmallCategorysJson.length; i++){
		        		if(spaceSmallCategorysJson[i].valueCode == smallCategorys[j]){
		        			str += spaceSmallCategorysJson[i].valueName+" ";
		        		}
		        	}
	        	}
	        	return str;
	        }
	    },
	    {
	        field: 'digitalType',
	        title: '类型',
	        formatter: function(value, row, index) {
	        	var str = "";
	        	if(value == 1){
	        		str = '家具';
	        	}else if (value == 2){
	        		str = '灯具';
	        	}else if (value == 3){
	        		str = '饰品';
	        	}else if (value == 4){
	        		str = '挂画';
	        	}else if (value == 5){
	        		str = '地毯窗帘';
	        	}
	        	return str;
	        }
	    }, 
	    {
	        field: 'name',
	        title: '名称'
	    }, 
	    {
	        field: 'style',
	        title: '风格',
	        formatter: function(value, row, index) {
	        	for(var i=0; i<stylesJson.length; i++){
	        		if(stylesJson[i].valueCode == value){
	        			return stylesJson[i].valueName;
	        		}
	        	}
	        }
	    }, 
	    {
	        field: 'pointPrice',
	        title: '积分价格'
	    },
	    {
	        field: 'shelfStatus',
	        title: '上架状态',
	        formatter: function(value, row, index) {
	        	if(value == 1){
	        		return '<span class="label label-success">已上架</span>';
	        	}else if(value == 0){
	        		return '<span class="label label-default">未上架</span>';
	        	}else if(value == 2){
	        		return '<span class="label label-danger">已下架</span>';
	        	}
	        }
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
	        field: 'shelfTime',
	        title: '上架时间',
	    },
	    {
	        field: 'createTime',
	        title: '创建时间',
	    }
	    ]
	});
	
	$("#page").on("click",".jq-page-shelfStatus",function(){
		var _self = $(this);
		var id=_self.data("id");
		var shelfStatus = _self.data("value");
		$.ajax({
			url : '/digital_furniture/' + id +'/'+shelfStatus+'/'+ '/approve',
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
			success : function(map){
				if(map.success){
					new PNotify({
						title: '设置成功！',
						type: 'success'
					});
					$('#page').bootstrapTable('refresh');
				}else{
					new PNotify({
						title: '设置失败！',
						text: map.msg || '',
						type: 'error'
					});
				}
			}
		});
	});
	
	$btnAdd.on('click', function(){
		var brand = $("#filter-brand").val();
		if (!brand) {
			new PNotify({
				title: '请先选择品牌',
				type: 'warning'
			});
		} else {
			window.location.href = '/digital_furniture/addPage?brandId='+brand;
		}
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var url = '/digital_furniture/' + id + '/disable';
		var title = '禁用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/digital_furniture/' + id + '/enable';
		var title = '启用';
		base.executePageSimpleAjaxAction(url, title, '');
	});
	
});