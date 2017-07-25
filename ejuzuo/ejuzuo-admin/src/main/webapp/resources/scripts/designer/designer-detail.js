/**
 * 
 */
require([ 'jquery', 'bootstrap-dialog' ], function($, BootstrapDialog){
	
	'use strict';
	var domainImage = $('#jq-domain-image').val();
	var coverImgJson = JSON.parse($('#coverImg').val());
	var str = '<img src="'+domainImage+coverImgJson.pic280280+'" alt="封面图片">';
	$('#thumbnailDiv').append(str);

});