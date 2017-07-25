define([ 'jquery', 'btable-core', 'btable-resizable' ], function($) {
	// append required CSS rules , fix td padding from colResizable
	$("head").append("<style type='text/css'>.bootstrap-table .JColResizer td {padding-left: 8px !important; padding-right: 8px !important;} .bootstrap-table .table-condensed .JColResizer td {padding-left: 5px !important; padding-right: 5px !important;}</style>");
	$.extend($.fn.bootstrapTable.defaults, {classes: 'table table-hover table-condensed'});
});