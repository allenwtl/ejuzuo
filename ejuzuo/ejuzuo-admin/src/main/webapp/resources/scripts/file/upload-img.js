define('cmsUploadImg', 
		['jquery', 'bootstrap', 'PNotify'], 
		function($, Bootstrap, PNotify){
	
	return {
		init: function() {
			
			var domainImage = $('#jq-domain-image').val();
			var path = $("#jq-domain-path").val();
			var relatedType = $("#jq-domain-relatedType").val();
			
			$('.jq-val-upload-img').on('change', function(){
				var $this = $(this);
				var type = $this.data('type');
				if ($this.val().length > 0) {
					var src = domainImage + $this.val();
					$('#jq-upload-thumbnail-img-'+type).attr('src', src);
					$('#jq-upload-thumbnail-'+type).removeClass('hidden');
				} else {
					$('#jq-upload-thumbnail-'+type).addClass('hidden');
				}
			});
			
			$('.jq-file-upload-img').on('change', function(){
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
			
			$('.jq-btn-clear-upload-img').on('click', function(){
				var $this = $(this);
				
				var fileId = $this.data('file');
				var $file = $('#' + fileId);
				
				$file.val('');
				$file.trigger('change');
			});
			
			$('.jq-btn-upload-img').on('click', function(){
				var $this = $(this);
				$this.button('loading');
				var fileId = $this.data('file');
				var fieldId = $this.data('field');
				
				var $file = $('#' + fileId);
				var $field = $('#' + fieldId);
				if ($file.val().length <= 0) {
					new PNotify({
						title: '请选择一张需要上传的图片',
						type: 'warning'
					});
					$this.button('reset');
					return;
				}
				
				var data = new FormData();
				data.append('image', $file[0].files[0]);
				data.append('path', path);
				data.append('relatedType',relatedType);
				/**如果是数字家居的内容，还要传多一个标记*/
				if(relatedType == 3){
					var content = $this.data('content');
					if(content){
						data.append('content', true);
					}
				}
				var opts = {
					url: '/file/upload-image',
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
							title: '图片上传异常',
							type: 'error'
						});
					},
					success: function(resp) {
						if (resp.success) {
							new PNotify({
								title: '图片上传成功',
								text: resp.fileName || '',
								type: 'success'
							});
							if(content){
								$('#' + fieldId + '-205100').val(resp.pic205100);
								$('#' + fieldId + '-850478').val(resp.pic850478);
								$field.val(resp.pic205100);
							}else{
								$field.val(resp.path);
							}
							$file.val('');
							$field.trigger('change');
							$file.trigger('change');
						} else {
							new PNotify({
								title: '图片上传失败',
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