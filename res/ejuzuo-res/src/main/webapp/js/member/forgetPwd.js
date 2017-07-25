(function($,win,doc){
    $(document).ready(function () {

        $('#image-code').attr("src", "/captcha/captcha-image?q="+Math.floor(Math.random()*100));

        var nextStepFlag = true ;

        $('#nextStep').click(function(){
            if(!nextStepFlag){
                return ;
            }
            $('#findPwdForm').submit();
        });

        $('#findPwdForm').validate({
            rules:{
                account:'required',
                code:'required'
            },
            submitHandler:function(form){
                nextStepFlag = false;
                var url = '/member/forgetPwd/validate/'+$('#codeType').val()+'/'+$('#validateCode').val();
                var option= {
                    url:url,
                    success:function(data){
                        nextStepFlag = true ;
                        if(data.code == 222){
                            win.location.href="/member/toValidateCode";
                            return ;
                        }
                        webAlert({
                            title:'温馨提示',
                            content:data.msg,
                            button:[
                                {
                                    name:'关闭'
                                }
                            ]
                        });
                    }
                };
                $(form).ajaxSubmit(option);
            }
        });

        var sendCodeFlag = true ;
        $('#sendCode').click(function(){
            if(!sendCodeFlag){
                return ;
            }
            sendCodeFlag = false ;
            var options = {
                codeType: $('#codeType').val(),
                code:$('#account').val(),
                businessType:4,
                success:function(data){
                    sendCodeFlag = true ;
                    webAlert({
                        title:'温馨提示',
                        content:data.msg,
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuo.sendCode(options);
        });

        $('#findPwdType').click(function(){
            var type = $(this).attr('type');
            if(type == 'mobile'){
                $(this).attr('type', 'email').text('用邮箱找回');
                $('#codeType').val('').val('mobile');
                $('#account').attr('placeholder', '请输入您注册时填写的手机号');
            } else if(type == 'email'){
                $(this).attr('type', 'mobile').text('用手机找回');
                $('#codeType').val('').val('email');
                $('#account').attr('placeholder', '请输入您注册时填写的邮箱');
            }
        });

        $('#resetImageCode').click(function(){
            $('#image-code').hide().attr("src", "/captcha/captcha-image?q="+Math.floor(Math.random()*100)).fadeIn();
        });


        var sureFlag = true ;

        $('#sure').click(function(){
            if(!sureFlag){
                return ;
            }
            sureFlag = false ;
            var validateCode = $('#systemCode').val();
            if($.ejuzuo.isBlank(validateCode)){
                sureFlag = true ;
                webAlert({
                    title:'温馨提示',
                    content:'验证码不能为空',
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                return ;
            }

            var options = {
                codeType: $('#type').val(), // email or mobile  给手机还是邮箱发送验证
                businessType: 4,  //业务类型  参照实体类 CheckCodeRecordCheckType
                validateCode: validateCode, //验证码
                code:$('#account').val(),
                success:function(data){
                    sureFlag = true ;
                    if(data.code == 444){
                        webAlert({
                            title:'温馨提示',
                            content:data.msg,
                            button:[
                                {
                                    name:'关闭'
                                }
                            ]
                        });
                        return ;
                    }
                    win.location.href="/member/toresetpwd/"+data.token;
                }
            };
            $.ejuzuo.checkCode(options);
        });

        var resetPwdFlag = true;

        $('#resetPwd').click(function(){
            if(!resetPwdFlag){
                return ;
            }

            $('#resetForm').submit();
        });

        $('#resetForm').validate({
            rules:{
                firPwd:'required',
                secPwd:'required'
            },
            submitHandler:function(form){
                resetPwdFlag = false;
                $(form).ajaxSubmit(function(data){
                    resetPwdFlag = true;
                    if(data.code == 222){
                        webAlert({
                            title:'温馨提示',
                            content:data.msg,
                            button:[
                                {
                                    name:'去登录',
                                    callback:function(){
                                        this.close();
                                        win.location.href='/login/toLogin';
                                    }
                                }
                            ]
                        });
                        win.location.href='/login/toLogin';
                        return ;
                    }
                    webAlert({
                        title:'温馨提示',
                        content:data.msg,
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                    return ;
                });
            }
        });

    });

})(jQuery, window, document);