$.validator.addMethod( "phoneCN", function( phone_number, element ) {
    return this.optional( element ) || ( /^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$/).test( phone_number);
}, "手机号码的格式错误" );