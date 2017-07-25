(function($,win,doc){

    $(document).ready(function () {

        var options = {
            type : "POST",
            dataType : "json",
            success :  function(data){
                if(data.code == 222){
                    window.location.href="/login/toLogin";
                    return ;
                }
                alert(data.msg);
            }
        };

        $('#mobileUpdatePwd').ajaxForm(options);

        $("#emailUpdatePwd").ajaxForm(options);


        $("#updateType").change(function(){
            var updateType = $(this).val();
            if(updateType == 1){
                $("#email").show();
                $("#mobile").hide();
                return ;
            }
            if(updateType == 2){
                $("#email").hide();
                $("#mobile").show();
                return ;
            }
        });

        $(".checkCode").click(function(){
            var dataType = $(this).attr("dataType");
            var options = {
                codeType:$(this).attr("dataType"),
                businessType:5,
                success:function(data){
                    alert(data.msg);
                }
            };
            $.ejuzuo.sendCode(options);
        });
    });

})(jQuery, window, document);