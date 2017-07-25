;
(function ($, win, doc) {

    //初始化判断 修改密码的途径
    function initupdatePwdType (){
        var type = $('#type').val();
        if (type == "mobile") {
            $('#updatePwdType').text('用邮箱修改密码');
            $('#updatePwdType').attr('data', 'mobile');
            $('#type').val('mobile');
            $('#contanier_text').val($('#mobile_str').val());
        } else if (type == "email") {
            $('#updatePwdType').text('用手机修改密码');
            $('#updatePwdType').attr('data', 'email');
            $('#type').val('email');
            $('#contanier_text').val($('#email_str').val());
        }
        return;
    }


    $(document).ready(function () {
        initupdatePwdType();

        var sendMobileCodeFlag = true;
        $('#sendMobileCode').click(function () {
            if (!sendMobileCodeFlag) {
                return;
            }
            var mobile = $('#mobile').val();
            if ($.ejuzuo.isBlank(mobile) || !$.ejuzuo.validateMobile(mobile)) {
                webAlert({
                    title: '温馨提示',
                    content: "请输入格式正确的电话号码",
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
                return;
            }

            sendMobileCodeFlag = false;
            var options = {
                codeType: 'mobile', // email or mobile  给手机还是邮箱发送验证
                businessType: 2,  //业务类型  参照实体类 CheckCodeRecordCheckType
                code: mobile,
                success: function (data) {
                    sendMobileCodeFlag = true;
                    if (data.code == 222) {
                        webAlert({
                            title: '温馨提示',
                            content: "发送成功",
                            button: [
                                {
                                    name: '关闭'
                                }
                            ]
                        });
                        return;
                    }
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuo.sendCode(options);
        });

        var authMobileFlag = true;
        $('#authMobile').click(function () {
            if (!authMobileFlag) {
                return;
            }
            authMobileFlag = false;
            var mobileCode = $('#mobileCode').val();

            if ($.ejuzuo.isBlank(mobileCode)) {
                webAlert({
                    title: '温馨提示',
                    content: "请输入验证码",
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
                return;
            }

            var options = {
                codeType: 'mobile', // email or mobile  给手机还是邮箱发送验证
                businessType: 2,  //业务类型  参照实体类 CheckCodeRecordCheckType
                code: $('#mobile').val(),//邮箱或者手机号码
                validateCode: mobileCode, //验证码
                success: function (data) {
                    authMobileFlag = true;
                    if (data.code == 222) {
                        win.location.reload();
                        return;
                    }
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuo.checkCode(options);

        });


        //-------------------邮箱发送验证码
        var sendEmailCodeFlag = true;
        $('#sendEmailCode').click(function () {
            if (!sendEmailCodeFlag) {
                return;
            }

            sendEmailCodeFlag = false;

            var email = $('#email').val();
            if ($.ejuzuo.isBlank(email) || !$.ejuzuo.validateEmail(email)) {
                sendEmailCodeFlag = true;
                webAlert({
                    title: '温馨提示',
                    content: "请输入格式正确的电话号码",
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
                return;
            }

            var options = {
                codeType: 'email', // email or mobile  给手机还是邮箱发送验证
                businessType: 3,  //业务类型  参照实体类 CheckCodeRecordCheckType
                code: email,
                success: function (data) {
                    sendEmailCodeFlag = true;
                    if (data.code == 222) {
                        webAlert({
                            title: '温馨提示',
                            content: "发送成功",
                            button: [
                                {
                                    name: '关闭'
                                }
                            ]
                        });
                        return;
                    }
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuo.sendCode(options);
        });


        var authEmailFlag = true ;
        $('#authEmail').click(function(){
            if (!authEmailFlag) {
                return;
            }
            authEmailFlag = false;
            var emailCode = $('#emailCode').val();

            if ($.ejuzuo.isBlank(emailCode)) {
                webAlert({
                    title: '温馨提示',
                    content: "请输入验证码",
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
                return;
            }

            var options = {
                codeType: 'email', // email or mobile  给手机还是邮箱发送验证
                businessType: 3,  //业务类型  参照实体类 CheckCodeRecordCheckType
                code: $('#email').val(),//邮箱或者手机号码
                validateCode: emailCode, //验证码
                success: function (data) {
                    authEmailFlag = true;
                    if (data.code == 222) {
                        win.location.reload();
                        return;
                    }
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuo.checkCode(options);
        });


        //--------修改密码
        $('#updatePwdType').click(function () {
            var type = $(this).attr("data");
            if (type == "mobile") {
                var contanier_text = $('#mobile_str').val();
                if(!!contanier_text){
                    webAlert({
                        title: '温馨提示',
                        content: '未认证邮箱',
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                    return ;
                }
                $(this).text('用邮箱修改密码');
                $(this).attr('data', 'email');
                $('#type').val('mobile');
                $('#contanier_text').val(contanier_text);
            } else if (type == "email") {
                var contanier_text = $('#email_str').val();
                if(!!contanier_text){
                    webAlert({
                        title: '温馨提示',
                        content: '未认证手机号码',
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                    return ;
                }
                $(this).text('用手机修改密码');
                $(this).attr('data', 'mobile');
                $('#type').val('email');
                $('#contanier_text').val(contanier_text);
            }
            return;
        });

        //发送验证码按钮
        var sendCodeFlag = true;
        $("#sendCode").click(function () {
            if (!sendCodeFlag) {
                return;
            }
            sendCodeFlag = false;
            var type = $('#type').val();
            var options = {
                codeType: type, // email or mobile  给手机还是邮箱发送验证
                businessType: 5,  //业务类型  参照实体类 CheckCodeRecordCheckType
                code: '',
                success: function (data) {
                    sendCodeFlag = true;

                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuo.sendCode(options);
        });


        $('#updatePwd').validate({
            rules: {
                code: 'required',
                oldPwd: 'required',
                firPwd: 'required',
                secPwd: 'required'
            },
            submitHandler: function (form) {
                pwdSaveFlag = false;
                $(form).ajaxSubmit(function (data) {
                    pwdSaveFlag = true;
                    if (data.code == 222) {
                        win.location.reload();
                        return;
                    }
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                });
            }
        });

        var pwdSaveFlag = true;
        $('#pwdSave').click(function () {
            if (!pwdSaveFlag) {
                return;
            }

            $('#updatePwd').submit();
        });

    });
})(jQuery, window, document);