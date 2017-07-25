;(function($,win,doc){
    $(document).ready(function () {

        var signUpFlag = true;
        $('#signUp').click(function () {
            var sign = $(this).attr("data");
            if(sign){
                return ;
            }

            if (!signUpFlag) {
                return;
            }
            signUpFlag = false;
            var options = {
                url: '/activity/signUp/' + $('#activityId').val(),
                success: function (data) {
                    signUpFlag = true;
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                },
                fail: function () {
                    signUpFlag = true;
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
            $.ejuzuo.ejuzuoGetJSON(options);
        });

    });
})(jQuery, window, document);
