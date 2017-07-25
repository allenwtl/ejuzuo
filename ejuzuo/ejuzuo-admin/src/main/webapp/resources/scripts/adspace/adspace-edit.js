/**
 * 
 */
require(['jquery', 'base','PNotify','codemirror','codemirror-htmlmixed','kindeditor','kindeditor.lang'], 
		function($, base,PNotify,codemirror) {
	
	'use strict';
	
	var TipCodeMirrorEditor = codemirror.fromTextArea(document.getElementById('tip-codemirror'), {
	    mode: "text/javascript",
	    mode: "htmlmixed",
		theme: "monokai",
		lineNumbers: true,
		lineWrapping: false,
		styleActiveLine: true,
		height: '500px',
		readOnly: false,
		autofocus: true,
		extraKeys: {
			"F11": function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc": function(cm) {
				if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
			}
		}
	}); 
	
	var CodeMirrorEditor = codemirror.fromTextArea(document.getElementById('editor-codemirror'), {
	    mode: "text/javascript",
	    mode: "htmlmixed",
		theme: "monokai",
		lineNumbers: true,
		lineWrapping: false,
		styleActiveLine: true,
		height: '500px',
		readOnly: false,
		autofocus: true,
		extraKeys: {
			"F11": function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc": function(cm) {
				if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
			}
		}
	});
	
	// KindEditor 全局设置
	KindEditor.options.items = [
        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
        'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
        'anchor', 'link', 'unlink', '|', 'about'
	];
	//是否开启过滤模式
	var editor = KindEditor.create("#editor-kindeditor", {
		urlType : '',
		filterMode: false,
		minHeight: 350,
		height: "auto",
		uploadJson : '/file/kindeditor/upload-image',
		fileManagerJson : '/file/kindeditor/image-viewer',
		allowFileManager : true
	});
	
	$("#codemirror-tap").on('click',function(){
		CodeMirrorEditor.setValue(editor.html());
		CodeMirrorEditor.focus();
	});
	$("#kindeditor-tap").on('click',function(){
		editor.html(CodeMirrorEditor.getValue());
	});
	
	var url = '';
	var formEdit = document.getElementById('form-edit');
	$('#form-btn-update').on('click', function(){
		url = '/adspace/edit';
	});
	
	function resetBtn() {
		$('#form-btn-update').button('reset');
	}
	$('#form-edit').on('submit', function(e){
		e.preventDefault();
		$('#form-btn-update').button('loading');
		
		var isValid = formEdit.checkValidity();
		if (!isValid) {
			resetBtn();
			return false;
		}
		
		var data = {};
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
				new PNotify({
					title: '内容提交异常',
					text: jqXHR.responseJSON.msg || '',
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
					$('#form-btn-update').val('更新完成');
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
	
});