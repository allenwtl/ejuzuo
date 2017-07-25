/**
 * 
 */
require([ 'jquery', 'base', 'doT', 'PNotify', 'bootstrap-dialog','bootstrap-datetimepicker', 'btable', 'jquery.form' ], 
		function($, base, doT, PNotify, BootstrapDialog){
	
	'use strict';
	
	$('#filter-submit').on('click', function () {
		$('#page').bootstrapTable('selectPage', 1);
    });
	
	$('#page').bootstrapTable({
		url: '/adspace/page',
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
	        title: '广告位名称'
	    },
	    {
	        field: 'pageCode',
	        title: '位置',
	        formatter: function(value, row, index) {
	    		var status = [
					'跑马灯广告',
					'首页右边推广图',
					'首页广告位1',
					'首页广告位2',
					'首页广告位3'
				];
	    		if (!!value || value === 0) {
	    			return status[value];
	    		} else {
	    			return null;
	    		}
	    	}
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
	        field: 'filePath',
	        title: '文件路径',
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
	        	if($("#hasPermOfInfo").val() === 'true'){
			        str += '<a class="btn btn-xs btn-default" href="/adspace/'+value+'/detail/" role="button">'+
					'<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>&nbsp;详情'+
					'</a>';
	        	}
//			        str += '<a class="btn btn-xs btn-info" href="/adspace/'+value+'/conf/" role="button">'+
//					'<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>&nbsp;配置'+
//					'</a>';
	        	if($("#hasPermOfUpdate").val() === 'true'){
			        str += '<a class="btn btn-xs btn-primary" href="/adspace/'+value+'/edit/" role="button">'+
					'<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>&nbsp;编辑'+
					'</a>';
	        	}
			        return str;
	        }
	    }
	    ]
	});
	
});