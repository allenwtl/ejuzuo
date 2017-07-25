(function() {
	'use strict';
	
	/*!
	 * IE10 viewport hack for Surface/desktop Windows 8 bug
	 * Copyright 2014 Twitter, Inc.
	 * Licensed under the Creative Commons Attribution 3.0 Unported License. For
	 * details, see http://creativecommons.org/licenses/by/3.0/.
	 */

	// See the Getting Started docs for more information:
	// http://getbootstrap.com/getting-started/#support-ie10-width
	if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
		var msViewportStyle = document.createElement('style');
		msViewportStyle.appendChild(document.createTextNode('@-ms-viewport{width:auto!important}'));
		document.querySelector('head').appendChild(msViewportStyle);
	}

	require.config({
		baseUrl : '/resources',
		paths : {
			jquery : '//cdn.bootcss.com/jquery/2.1.4/jquery.min',
			bootstrap : '//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min'
		},
		shim : {
			bootstrap : {
				deps : [ 'jquery' ],
				exports : 'bootstrap'
			}
		}
	});
	require([ 'jquery', 'bootstrap' ], function($, _bootstrap) {

	});
})(this);