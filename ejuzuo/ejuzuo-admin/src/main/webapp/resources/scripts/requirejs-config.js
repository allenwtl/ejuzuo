document.currentScript = document.currentScript || (function() {
	var scripts = document.getElementsByTagName('script');
	return scripts[scripts.length - 1];
})();

var requirejsAppEnv = document.currentScript.getAttribute('data-env');
var requirejsResHost = requirejsAppEnv === 'product' ? '//r.ejuzuo.com' : '//r.ejuzuo.com';

/**
 * RequireJS main configuration file load before RequireJS
 */
require.config({
	baseUrl : '/resources',
	deps : ['base'],
	paths : {
		'require-css': [requirejsResHost + '/plugins/require-css/0.1.8/css.min',
		                '//cdn.bootcss.com/require-css/0.1.8/css.min'],
		'jquery': [requirejsResHost + '/plugins/jquery/jquery-2.2.1.min',
		           '//cdn.bootcss.com/jquery/2.2.1/jquery.min'],
		'bootstrap': [requirejsResHost + '/plugins/bootstrap/3.3.6/js/bootstrap.min',
		              '//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min'],
		'doT': [requirejsResHost + '/plugins/doT/1.0.3/doT.min',
		        '//cdn.bootcss.com/dot/1.0.3/doT'],
		'moment': [requirejsResHost + '/plugins/moment.js/2.11.1/moment-with-locales.min',
		           '//cdn.bootcss.com/moment.js/2.11.1/moment-with-locales.min'],
		'pace': [requirejsResHost + '/plugins/pace/1.0.2/pace.min',
		         '//cdn.bootcss.com/pace/1.0.2/pace.min'],
		'jquery.slimscroll': [requirejsResHost + '/plugins/jquery-slimscroll/1.3.7/jquery.slimscroll.min',
		                      '//cdn.bootcss.com/jQuery-slimScroll/1.3.7/jquery.slimscroll.min'],
		'pnotify': [requirejsResHost + '/plugins/pnotify/3.0.0/pnotify',
		            '//cdn.bootcss.com/pnotify/3.0.0/pnotify.min'],
		'pnotify.buttons': [requirejsResHost + '/plugins/pnotify/3.0.0/pnotify.buttons',
		                    '//cdn.bootcss.com/pnotify/3.0.0/pnotify.buttons.min'],
		
		'select2': [requirejsResHost + '/plugins/select2/4.0.1/js/select2.min',
		            '//cdn.bootcss.com/select2/4.0.1/js/select2.min'],
		'chosen': '//cdn.bootcss.com/chosen/1.4.2/chosen.jquery.min',
		'chosen-style': '//cdn.bootcss.com/chosen/1.4.2/chosen.min',
		
		'bootstrap-dialog': [requirejsResHost + '/plugins/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min',
		                     '//cdn.bootcss.com/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min'],
		'bootstrap-datetimepicker': [requirejsResHost + '/plugins/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min',
		                             '//cdn.bootcss.com/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min'],
		
		'btable': 'scripts/btable',
		'btable-style': [requirejsResHost + '/plugins/bootstrap-table/1.10.0/bootstrap-table.min',
		                 '//cdn.bootcss.com/bootstrap-table/1.10.0/bootstrap-table.min'],
		'btable-core': [requirejsResHost + '/plugins/bootstrap-table/1.10.0/bootstrap-table.min',
		                '//cdn.bootcss.com/bootstrap-table/1.10.0/bootstrap-table.min'],
		'btable-resizable': [requirejsResHost + '/plugins/bootstrap-table/1.10.0/extensions/resizable/bootstrap-table-resizable.min',
		                     '//cdn.bootcss.com/bootstrap-table/1.10.0/extensions/resizable/bootstrap-table-resizable.min'],
		'btable-colResizable': [requirejsResHost + '/plugins/bootstrap-table/btable-colResizable-1.5.source',
		                        'vendor/bootstrap-table/btable-colResizable-1.5.source', 
		                        '//rawgit.com/wenzhixin/colResizable/master/source/colResizable-1.5.source'],
		'bootstrap-table': 'vendor/bootstrap-table/bootstrap-table-all',
		'bootstrap-table-style': '//cdn.bootcss.com/bootstrap-table/1.8.1/bootstrap-table.min',
		
		'spectrum': [requirejsResHost + '/plugins/spectrum/1.8.0/spectrum.min',
		             '//cdn.bootcss.com/spectrum/1.8.0/spectrum.min'],
		'spectrum-css': [requirejsResHost + '/plugins/spectrum/1.8.0/spectrum.min',
		                 '//cdn.bootcss.com/spectrum/1.8.0/spectrum.min'],
		
		'bootstrap-colorpicker': [requirejsResHost + '/plugins/bootstrap-colorpicker/2.3.0/js/bootstrap-colorpicker.min',
 		                          '//cdn.bootcss.com/bootstrap-colorpicker/2.3.0/js/bootstrap-colorpicker.min'],
		'ckeditor': [requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
 		             '//cdn.ckeditor.com/4.5.6/full-all/ckeditor'],
 		
        'ztree': [requirejsResHost + '/plugins/ztree/3.5.22/js/jquery.ztree.all.min',
                 requirejsResHost + '/plugins/ztree/3.5.18/jquery.ztree.all-3.5.min',
	          'vendor/ztree/3.5.18/jquery.ztree.all-3.5.min'],
        'jquery.form': [requirejsResHost + '/plugins/jquery-form/jquery.form-3.51.0',
                        'vendor/jquery-form/jquery.form-3.51.0'],
        'codemirror': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
 		             'vendor/codemirror/5.1.0/codemirror.min'],
        'codemirror-htmlmixed': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
  		             'vendor/codemirror/5.1.0/mode/htmlmixed/htmlmixed.min'],
       'codemirror-javascript': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
      		         'vendor/codemirror/5.1.0/mode/javascript/javascript.min'],
       'codemirror-xml': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
    		         'vendor/codemirror/5.1.0/mode/xml/xml.min'],
       'codemirror-css': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
                     'vendor/codemirror/5.1.0/mode/css/css.min'],
	   'kindeditor': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
	                 'vendor/kindeditor/4.1.10/kindeditor-all-min'],
	   'kindeditor.lang': [//requirejsResHost + '/plugins/ckeditor/4.5.6/full-all/ckeditor',
	                 'vendor/kindeditor/4.1.10/lang/zh_CN'],              
 		             
		'base': 'scripts/base',
		'base-page': 'scripts/base-page',
		'base-validation': 'scripts/base-validation',
		'PNotify': 'scripts/PNotify',
		'ajaxfileupload': 'scripts/ajaxfileupload',
		'cmsUploadImg': 'scripts/file/upload-img',
		'uploadArchive': 'scripts/file/upload-archive'
	},
	shim : {
		'bootstrap' : {
			deps : [ 'jquery' ],
			exports : 'bootstrap'
		},
		'jquery.slimscroll' : {
			deps : [ 'jquery' ]
		},
		'bootstrap-dialog' : {
			deps : [ 'bootstrap' ]
		},
		'btable-core' : {
			deps : [ 'require-css!btable-style', 'bootstrap'  ]
		},
		'btable-colResizable': {
			deps : [ 'jquery' ]
		},
		'btable-resizable': {
			deps : [ 'btable-colResizable', 'btable-core' ]
		},
		'bootstrap-table' : {
			deps : [ 'require-css!bootstrap-table-style', 'bootstrap' ]
		},
		'bootstrap-colorpicker' : {
			deps : [ 'bootstrap' ]
		},
		'spectrum' : {
			deps : [ 'require-css!spectrum-css', 'jquery' ]
		},
		'ckeditor': {
			deps : [ 'jquery' ],
			exports : 'CKEDITOR'
		},
		'ztree': {
			deps : [ 'jquery' ]
		},
		'ajaxfileupload' : {
			deps : [ 'jquery' ]
		},
		'codemirror': {
			deps : [ 'jquery' ]
		},
		'codemirror-htmlmixed': {
			deps : [ 'jquery' ]
		},
		'codemirror-javascript': {
			deps : [ 'jquery' ]
		},
		'codemirror-css': {
			deps : [ 'jquery' ]
		},
		'codemirror-xml': {
			deps : [ 'jquery' ]
		},
		'kindeditor': {
			deps : [ 'jquery' ]
		},
		'kindeditor.lang': {
			deps : [ 'jquery' ]
		},
		'chosen' : {
			deps : [ 'require-css!chosen-style', 'jquery' ]
		}
	},
	config: {
        'moment' : {
            noGlobal: true
        }
    }
});
