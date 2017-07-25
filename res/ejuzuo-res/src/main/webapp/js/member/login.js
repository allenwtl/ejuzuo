(function($,win,doc){

    $(document).ready(function () {
        $('#loginForm').validate({
            rules:{
                account:'required',
                password:'required'
            },
            submitHandler: function(form) {
                if(!loginFlag){
                    return ;
                }
                loginFlag = false ;
                $(form).ajaxSubmit(function(data){
                    loginFlag = true ;
                    if(data.code == 222){
                        win.location.href="/login/afterLogin";
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


        var loginFlag = true ;
        $('#login').click(function(){
            $('#loginForm').submit();
        });

    });

})(jQuery, window, document);