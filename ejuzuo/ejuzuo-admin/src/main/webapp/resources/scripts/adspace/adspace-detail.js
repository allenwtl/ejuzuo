/**
 * 
 */
require(['jquery', 'base','codemirror','codemirror-htmlmixed'], function($, base,codemirror) {
	
	'use strict';
	
	var CodeMirrorEditor = codemirror.fromTextArea(document.getElementById('tip-codemirror'), {
	    mode: "text/javascript",
	    mode: "htmlmixed",
		theme: "monokai",
		lineNumbers: true,
		lineWrapping: false,
		styleActiveLine: true,
		height: '500px',
		readOnly: true,
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
		readOnly: true,
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
	
});