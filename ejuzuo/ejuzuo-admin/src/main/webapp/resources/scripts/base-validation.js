define([ 'jquery',
         './vendor/jquery-validation/1.15.0/jquery.validate.min', 
         './vendor/jquery-validation/1.15.0/additional-methods.min',
         './vendor/jquery-validation/1.15.0/localization/messages_zh.min'],
         
         function($) {
	
	'use strict';

	$.validator.addMethod("notBlank", function(value, element) {
		var check = false;
		var regex = /^\s+$/ig;

		if ( !regex.test(value) ) {
			check = true;
		}

		return this.optional(element) || check;

	}, "Please enter a correct string but not blank");

	// http://stackoverflow.com/questions/169625/regex-to-check-if-valid-url-that-ends-in-jpg-png-or-gif
	$.validator.addMethod("urlImg", function(value, element) {
		var check = false;
		var regex = /(?:([^:\/?#]+):)?(?:\/\/([^\/?#]*))?([^?#]*\.(?:jpe?g|gif|png|bmp))(?:\?([^#]*))?(?:#(.*))?/;

		if ( regex.test(value) ) {
			check = true;
		}

		return this.optional(element) || check;

	}, "Please enter a correct url of image");
	
	// 表单校验的默认样式
    $.validator.setDefaults({
        errorElement: "span",
        errorClass: "help-block",
        highlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length || element.prop('type') === 'checkbox' || element.prop('type') === 'radio') {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
	
});
