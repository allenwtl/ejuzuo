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
		
		if($("#updateSystemMsgForm").validate().form()){
			$('#updateSystemMsgForm').attr("action", "/msg/record/update");
			$('#updateSystemMsgForm').ajaxSubmit({
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
						title : '更新异常！',
						type : 'error'
					});
	            }
			});
		}
	});
	
});