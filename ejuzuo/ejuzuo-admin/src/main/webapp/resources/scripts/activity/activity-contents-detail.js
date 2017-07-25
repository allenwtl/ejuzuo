/**
 * 
 */
require(['jquery', 'base', 'ckeditor'], function($, base, CKEDITOR) {
	
	'use strict';
	
	var editor = CKEDITOR.replace('jq-detail-content', {
		readOnly: true,
//		toolbar: [{ name: 'document', items : [ 'Source', '-', 'Preview' ] }]
	});
});