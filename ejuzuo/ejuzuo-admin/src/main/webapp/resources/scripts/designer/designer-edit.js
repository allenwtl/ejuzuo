/**
 * 
 */
require([ 'jquery', 'bootstrap-dialog', 'doT', 'PNotify' ], function($, BootstrapDialog, doT, PNotify){
	
	'use strict';

	var domainImage = $('#jq-domain-image').val();
	if($('#coverImg').val() != null && $('#coverImg').val() != ''){
		var coverImgJson = JSON.parse($('#coverImg').val());
		var str = '<img src="'+domainImage+coverImgJson.pic280280+'" alt="封面图片">';
		$('#thumbnailDiv').append(str);
	}
	var id = $('#id').val();
	var memberId = $('#memberId').val();
$('#jq-verify-pass').on('click', function(){
	var str = '<form class="form-horizontal" >'+
		'<div class="form-group">'+
			'<div class="form-group">'+
				'<label class="col-sm-2 control-label">备注</label>'+
				'<div class="col-sm-8">'+
					'<textarea row="2" class="form-control" id="form-update-remark" maxlength="200"></textarea>'+
				'</div>'+
		  '</div>'+
		'</form>';
		BootstrapDialog.show({
            title: '备注',
            message: $(str),
            buttons: [{
                label: '确定',
                cssClass: 'btn-primary',
                hotkey: 13,
                action: function(dialogRef) {
                	var data = {};
            		data.id = id;
            		data.memberId = memberId;
            		data.remark = $('#form-update-remark').val();
            		data.status = 2;
            		$.ajax({
            			url : '/designer/approval',
            			type : "post",
            			data : data,
            			error : function() {
            				new PNotify({
            					title : "审核通过异常！",
            					type : "error"
            				});
            			},
            			success : function(response) {
            				if (response.success) {
            					new PNotify({
            						title : "审核通过成功！",
            						type : "success"
            					});
            					window.location.href = '/designer';
            				} else {
            					new PNotify({
            						title : "审核通过失败！",
            						text : response.msg || "",
            						type : "error"
            					});
            				}
            			}
            		});
                }
            }]
        });
		
	});
	
var reasonTpl = doT.template($("#dot-reason").html());
$('#jq-verify-back').on('click', function(){
		BootstrapDialog.show({
            title: "审核退回-填写退回理由",
            message: reasonTpl,
            onshown: function(dialogRef) {
            	
            	var $reason = $('#form-verify-back-reason');
            	$reason.on('change', function(){
            		$('#form-verify-group-back-reason').removeClass('has-error');
            	});
            },
            buttons: [{
                label: ' 确认退回',
                cssClass: 'btn-primary',
                icon: 'glyphicon glyphicon-add',
                action: function(dialogRef) {
                	dialogRef.enableButtons(false);
                    dialogRef.setClosable(false);
                  
                    var $reason = $('#form-verify-back-reason');
                    var $reasonGroup = $('#form-verify-group-back-reason');
                    var reason = $.trim($reason.val());
                    if (!reason) {
                    	$reasonGroup.addClass('has-error');
                    	dialogRef.enableButtons(true);
                        dialogRef.setClosable(true);
                        return ;
                    }
                    
                    var data = {};
                    data.reason = reason;
            		var remark = $('#remark').val();
            		data.id = id;
            		data.memberId = memberId;
            		data.remark = remark;
            		data.status = 3;
                    $.ajax({
        				url : '/designer/approval',
        				type : "post",
        				data : data,
        				complete : function() {
        					dialogRef.enableButtons(true);
		                    dialogRef.setClosable(true);
        				},
        				error : function() {
        					new PNotify({
        						title : "审核退回异常！",
        						type : "error"
        					});
        				},
        				success : function(response) {
        					if (response.success) {
        						new PNotify({
	        						title : "审核退回成功！",
	        						type : "success"
	        					});
        						dialogRef.close();
        						window.location.href = '/designer';
        					} else {
        						new PNotify({
            						title : "审核退回失败！",
            						text : response.msg || "",
            						type : "error"
            					});
        					}
        				}
        			});
                    
                }
            }, {
                label: '取消',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
		
	});

});