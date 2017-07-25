/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	'use strict';

	var $filterSubmit = $('#filter-submit');
	
	$filterSubmit.on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	var pageDetailTpl = doT.template($("#dot-page-detail").html());
	var tplPageComment = doT.template($("#dot-page-comment").html());
	$('#page').bootstrapTable({
		url: '/msg/record/page',
		sidePagination: 'server',
		pagination: true,
		pageList: [20, 30, 50,100],
		pageSize: 20,
		sortable: true,
		sortOrder : 'desc',
        sortName: 'id',
        toolbar: '#page-toolbar',
        detailView: true,
        detailFormatter: function(index, row) {
        	return pageDetailTpl(row);
        },
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
			var title = $("#filter-title").val();
			if (!!title) {
				params.title = title;
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
	        title: '操作',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	var str = '';
	        	if($("#hasPermOfEnable").val() === 'true'){
	        	if(row.status !== 1){
	        		str += 
	 	    			'<button type="button" class="btn btn-xs btn-success jq-page-opt-enable" data-id="'+row.id+'">'+
	 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;启用'+
	 					'</button>';
	    		}
	        	}
	        	if($("#hasPermOfDisable").val() === 'true'){
	    		if(row.status === 1){
	    			str += 
	 	    			'<button type="button" class="btn btn-xs btn-danger jq-page-opt-disable" data-id="'+row.id+'">'+
	 						'<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;禁用'+
	 					'</button>';
	    		}
	        	}
	        	if($("#hasPermOfUpdate").val() === 'true'){
        		str += '<button class="btn btn-primary btn-xs update" type="button" data-id="'+row.id+'" >修改</button>';
	        	}
        		return str;
	        }
	    },
	    {
	        field: 'id',
	        title: 'ID',
	        sortable: true
	    },
	    {
	        field: 'msgType',
	        title: '消息类型',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="label label-success">系统消息</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-info">个人消息</span>';
	        	}
	        }
	    },
	    {
	        field: 'memberId',
	        title: '用户ID'
	    },
	    {
	        field: 'title',
	        title: '标题'
	    },
	    {
	    	field: 'content',
	    	title: '内容',
	    	cellStyle: function(value, row, index) {
	    		return {
	    			classes: 'content-cell'
	    		}
	    	},
	    	formatter: function(value, row, index) {
	    		return tplPageComment({content: value});
	    	}
	    }, 
	    {
	        field: 'status',
	        title: '状态',
	        halign: 'left',
	        align: 'center',
	        formatter: function(value, row, index) {
	        	if(value == 0){
	        		return '<span class="label label-danger">无效</span>';
	        	}else if(value == 1){
	        		return '<span class="label label-success">有效</span>';
	        	}
	        }
	    },
	    {
	        field: 'createdDate',
	        title: '创建时间',
	    },
	    {
	        field: 'updatedDate',
	        title: '更新时间',
	    }
	    ]
	});
	
	$('#btn-add').on('click', function(){
		window.location.href = "/msg/record/addPage"
	});
	
	$('#memberIdDiv').hide();
	$('input[name="msgType"]').on('click', function(){
		if($("input[name='msgType']:checked").val() == 1){
			$('#memberIdDiv').show();
		}else{
			$('#memberIdDiv').hide();
		}
	});
	
	var id;
	$('#page').on('click','.update', function(){
		id = $(this).data("id");
		window.location.href = "/msg/record/"+id+"/update"
	});
	
	// 禁用
	$("#page").on("click", ".jq-page-opt-disable", function() {
		var _self = $(this);
		var id = _self.data("id");
		
		var url = '/msg/record/' + id + '/disable';
		var title = '禁用';
		var text = '';
		
		base.executePageSimpleAjaxAction(url, title, text);
	});
	
	// 启用
	$("#page").on("click", ".jq-page-opt-enable", function() {
		var _self = $(this);
		var id = _self.data("id");
		var url = '/msg/record/' + id + '/enable';
		var title = '启用';
		var text = '';
		base.executePageSimpleAjaxAction(url, title, text);
	});
});