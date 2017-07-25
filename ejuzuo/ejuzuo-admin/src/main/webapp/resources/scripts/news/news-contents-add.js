/**
 * 
 */
require(['jquery', 'base', 'doT', 'ckeditor', 'PNotify','cmsUploadImg','select2', 'bootstrap-colorpicker'], 
		function($, base, doT, CKEDITOR, PNotify,cmsUploadImg) {
	
	'use strict';
	
	var editor = CKEDITOR.replace('form-content', {
		filebrowserImageBrowseUrl: '/file/ckeditor/image-viewer?path=img/ckeditor',
		filebrowserImageUploadUrl: '/file/ckeditor/upload-image?path=img/ckeditor'
	});
	
	$('#category').on('change',function(){
		var select = $('#category option:selected') .val();
		if(select == 1){
			$('#thumbnailDiv').hide();
		}else if(select == 0){
			$('#thumbnailDiv').show();
		}
	});
	
	// The "change" event is fired whenever a change is made in the editor.
	var btn = 0;
	var url = '';
	var $btnSaveDraft = $('#form-btn-save-draft');
	var formAdd = document.getElementById('form-add');
	
	$btnSaveDraft.on('click', function(){
		btn = 1;
		url = '/news/add';
	});
	
	function resetBtn() {
		if (btn === 1) {
			$btnSaveDraft.button('reset');
		} else if (btn === 2) {
			$btnSaveDraft.prop('disabled', false);
		}
	}
	
	$('#form-add').on('submit', function(e){
		
		e.preventDefault();
		
		if (btn === 1) {
			$btnSaveDraft.button('loading');
		} else if (btn === 2) {
			$btnSaveDraft.prop('disabled', true);
		} else {
			return false;
		}
		
		var contentTxt = editor.getData();
		if (!contentTxt || (contentTxt = contentTxt.trim()) == '') {
			new PNotify({
				title: '内容为空请填写',
				type: 'warning'
			});
			resetBtn();
			return false;
		}
		$('#form-content').val(contentTxt);
		
		var isValid = formAdd.checkValidity();
		if (!isValid) {
			resetBtn();
			return false;
		}
		
//		var data = new FormData($('#form-add')[0]);
//		console.log($( this ).serialize());
		var data = {};
	    // 已经不大记得这是拿来做啥的了
		var dataArr = $(this).serializeArray();
	    $.each(dataArr, function() {
	        if (data[this.name] !== undefined) {
	            if (!data[this.name].push) {
	            	data[this.name] = [data[this.name]];
	            }
	            data[this.name].push(this.value || '');
	        } else {
	        	data[this.name] = this.value || '';
	        }
	    });
	    
	    $.ajax({
	    	url: url,
			type: 'POST',
			data: data,
			dataType: "json",
			complete: function() {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var msg = '';
				if (!!jqXHR.responseJSON) {
					msg = jqXHR.responseJSON.msg
				}
				new PNotify({
					title: '内容提交异常',
					text: msg,
					type: 'error'
				});
				
				resetBtn();
			},
			success: function(resp) {
				if (resp.success) {
					new PNotify({
						title: '内容提交成功',
						type: 'success'
					});
					
					if (btn === 1) {
						$btnSaveDraft.val('保存完成');
					}
				} else {
					new PNotify({
						title: '内容提交失败',
						text: resp.msg || '',
						type: 'error'
					});
					
					resetBtn();
				}
			}
	    });
	});
	
	cmsUploadImg.init();
});