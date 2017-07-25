define('uploadArchive', 
		['jquery', 'bootstrap', 'PNotify'], 
		function($, Bootstrap, PNotify){
	
	return {
		init: function() {
			var path = $("#jq-attachment-path").val();
			var relatedType = $("#jq-domain-relatedType").val();
			$('.jq-file-upload-archive').on('change', function(){
				var $this = $(this);
				var btnId = $this.data('btn');
				var btnClearId = $this.data('clear');
				var $btn = $('#' + btnId);
				var $btnClear = $('#' + btnClearId);
				
				if ($this.val().length > 0) {
					$btn.prop('disabled', false);
					$btnClear.prop('disabled', false);
				} else {
					$btn.prop('disabled', true);
					$btnClear.prop('disabled', true);
				}
			});
			
			$('.jq-btn-clear-upload-archive').on('click', function(){
				var $this = $(this);
				
				var fileId = $this.data('file');
				var $file = $('#' + fileId);
				
				$file.val('');
				$('#form-archive-name').val('');
				$file.trigger('change');
			});
			
			$('.jq-btn-upload-archive').on('click', function(){
				var $this = $(this);
				$this.button('loading');
				var fileId = $this.data('file');
				var fieldId = $this.data('field');
				
				var $file = $('#' + fileId);
				var $field = $('#' + fieldId);
				
				if ($file.val().length <= 0) {
					new PNotify({
						title: '请选择一张需要上传的文件',
						type: 'warning'
					});
					$this.button('reset');
					return;
				}
				
				var data = new FormData();
				data.append('archive', $file[0].files[0]);
				data.append('path', path);
				data.append('relatedType',relatedType);
				var opts = {
					url: '/file/upload-archive',
					type: 'POST',
					data: data,
					dataType: "json",
				    cache: false,
				    contentType: false,
				    processData: false,
					complete: function() {
						$this.button('reset');
						if ($file.val().length <= 0) {
							function disabledBtn(dom) {
							    return function() {
							    	dom.prop('disabled', true);
							    }
							} 
							setTimeout(disabledBtn($this), 1);
						}
					},
					error: function() {
						new PNotify({
							title: '文件上传异常',
							type: 'error'
						});
					},
					success: function(resp) {
						if (resp.success) {
							new PNotify({
								title: '文件上传成功',
								text: resp.fileName || '',
								type: 'success'
							});
							$field.val(resp.fileId);
							$('#form-archive-name').val(resp.fileName);
							$file.val('');
							$field.trigger('change');
							$file.trigger('change');
						} else {
							new PNotify({
								title: '文件上传失败',
								text: resp.msg || '',
								type: 'error'
							});
						}
					}
				};
				if (data.fake) {
				    opts.xhr = function() { var xhr = jQuery.ajaxSettings.xhr(); xhr.send = xhr.sendAsBinary; return xhr; }
				    opts.contentType = "multipart/form-data; boundary="+data.boundary;
				    opts.data = data.toString();
				}
				$.ajax(opts);
			});
		}
	};
});