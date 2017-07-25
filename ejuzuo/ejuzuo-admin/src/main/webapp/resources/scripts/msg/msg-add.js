/**
 * 
 */
require([ 'jquery', 'base', 'doT','ckeditor', 'PNotify', 'bootstrap-dialog', 'btable', 'jquery.form' ], 
		function($, base, doT, CKEDITOR, PNotify, BootstrapDialog){
	'use strict';

	var editor = CKEDITOR.replace('form-content', {
		filebrowserImageBrowseUrl: '/file/ckeditor/image-viewer?path=img/ckeditor',
		filebrowserImageUploadUrl: '/file/ckeditor/upload-image?path=img/ckeditor'
	});
	
	$('#memberIdDiv').hide();
	$('input[name="msgType"]').on('click', function(){
		if($("input[name='msgType']:checked").val() == 1){
			$('#memberIdDiv').show();
		}else{
			$('#memberIdDiv').hide();
		}
	});
	
	$('#saveButton').on('click', function(){
		var contentTxt = editor.getData();
		if (!contentTxt || (contentTxt = contentTxt.trim()) == '') {
			new PNotify({
				title: '内容为空请填写',
				type: 'warning'
			});
			return false;
		}
		$('#form-content').val(contentTxt);
		
		if($("#addSystemMsgForm").validate().form()){
			$('#addSystemMsgForm').attr("action", "/msg/record/add");
			$('#addSystemMsgForm').ajaxSubmit({
				datatype: 'json',
	            success: function (map) {
	            	if(map.success){
	            		window.location.href = "/msg/record";
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
	
});