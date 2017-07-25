;(function($,win,doc){

    $(document).ready(function () {

        $('#smsForm').validate({
            rules:{
                mobile:{
                    required:true,
                    phoneCN:true
                },
                nickName:'required',
                password:'required',
                secPassword:'required',
                code:'required'
            },
            submitHandler:function(form){

                mobileRegisterFlag = false;

                $(form).ajaxSubmit(function(data){

                    mobileRegisterFlag = true ;

                    if(data.code == 222){
                        webAlert({
                            title:'温馨提示',
                            content:'恭喜，注册成功，赶紧去完善资料成为认证设计师！',
                            width:200,
                            button:[
                                {
                                    name:'完善设计师信息',
                                    callback:function(){
                                        this.close();
                                        //win.location.href ="/login/toLogin";
                                        win.location.href ="/designer/setting";
                                    }
                                }
                            ]
                        });
                        return ;
                    } else {
                        webAlert({
                            title:'温馨提示',
                            content:data.msg,
                            width:200,
                            button:[
                                {
                                    name:'关闭'
                                }
                            ]
                        });
                    }
                });
            }
        });

        var mobileRegisterFlag = true ;

        $('#mobileRegister').click(function(){
            if(!mobileRegisterFlag){
                return;
            }
            $('#smsForm').submit();
        });



        $('#emailForm').validate({
            rules:{
                email:{
                    required:true,
                    email:true
                },
                nickName:'required',
                password:'required',
                secPassword:'required',
                code:'required'
            },
            submitHandler:function(form){

                emailRegisterFlag = false;

                $(form).ajaxSubmit(function(data){

                    emailRegisterFlag = true ;

                    if(data.code == 222){
                        webAlert({
                            title:'温馨提示',
                            content:'恭喜，注册成功，赶紧去完善资料成为认证设计师！',
                            width:200,
                            button:[
                                {
                                    name:'完善设计师信息',
                                    callback:function(){
                                        this.close();
                                       // win.location.href ="/login/toLogin";
                                        win.location.href ="/designer/setting";
                                    }
                                }
                            ]
                        });
                        return ;
                    } else {
	                    webAlert({
	                        title:'温馨提示',
	                        content:data.msg,
	                        width:200,
	                        button:[
	                            {
	                                name:'关闭'
	                            }
	                        ]
	                    });
                    }  
	            });
            }

        });



        var emailRegisterFlag = true;
        $('#emailRegister').click(function(){
            if(!emailRegisterFlag){
                return;
            }
            $('#emailForm').submit();
        });



        var flagMobile = true ;
        $("#mobileButton").click(function(){
            var timelimit = $(this).attr("timelimit");
            if(timelimit == "on"){
                return ;
            }

            if(!flagMobile){
                return ;
            }
            flagMobile = false ;
            var mobile = $('#mobile').val();
            if(!$.ejuzuo.validateMobile(mobile)){
                webAlert({
                    title:'温馨提示',
                    content:'手机号码格式错误',
                    width:200,
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                flagMobile = true;
                $("#mobile").focus();
                return;
            }
            var options = {
                codeType:'mobile',
                code:$('#mobile').val(),
                businessType:0,
                success:function(data){
                    flagMobile = true ;
                    countdown();
                    webAlert({
                        title:'温馨提示',
                        content:data.msg,
                        width:200,
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                }
            }
            $.ejuzuo.sendCode(options);
        });

        var flagEmail = true ;
        $('#emailButton').click(function(){
            var timelimit = $(this).attr("timelimit");
            if(timelimit == "on"){
                return ;
            }
            if(!flagEmail){
                return ;
            }
            flagEmail = false ;
            var email = $('#email').val();
            if(!$.ejuzuo.validateEmail(email)){
                webAlert({
                    title:'温馨提示',
                    content:'邮箱格式错误',
                    width:200,
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                flagEmail = true;
                $("#email").focus();
                return;
            }

            var options = {
                codeType:'email',
                code:email,
                businessType:0,
                success:function(data){
                    countdown();
                    flagEmail = true ;
                    webAlert({
                        title:'温馨提示',
                        content:data.msg,
                        width:200,
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                },
                fail:function(){
                    flagEmail = true ;
                    webAlert({
                        title:'温馨提示',
                        content:'发送失败',
                        width:200,
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                }
            }
            $.ejuzuo.sendCode(options);
        });

    });

})(jQuery, window, document);
