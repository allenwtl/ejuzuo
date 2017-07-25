;
(function ($, win, doc) {

    $(document).ready(function () {

        var saveUpdateFlag = true;
        $('#saveUpdate').click(function(){
            if(!saveUpdateFlag){
                return;
            }
            $('#updateForm').submit();
        });

        $('#updateForm').validate({
            rules:{
                profileImg:'required',
                nickName:'required'
            },
            submitHandler:function(form){

                saveUpdateFlag = false;

                $(form).ajaxSubmit(function(data){

                    saveUpdateFlag = true ;

                    /*if(data.code == 222){
                        win.location.href = "/login/toLogin";
                        return ;
                    }*/
                    webAlert({
                        title:'温馨提示',
                        content:data.msg,
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                });
            }
        });


        $('#sendCode').click(function(){
            var type = $(this).attr("data");
            var businessType = null ;
            if (type == "mobile"){
                businessType =2;
            } else {
                businessType =3;
            }
            var options = {
                codeType:$(this).attr("data"),
                businessType:businessType,
                code:$('#newStr').val(),
                success:function(data){
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


        var submitUpdateFlag = true ;
        $('#submitUpdate').click(function(){
            if(!submitUpdateFlag){
                return ;
            }
            $('#updateMEForm').submit();
        });


        $('#updateMEForm').validate(
            {
                submitHandler: function(form) {
                    submitUpdateFlag = false ;
                    $(form).ajaxSubmit(function(data){
                        submitUpdateFlag = true ;

                        webAlert({
                            title:'温馨提示',
                            content:data.msg,
                            button:[
                                {
                                    name:'关闭'
                                }
                            ]
                        });
                    });
                }
            }
        );



        var uploadifyOption = {
            //指定swf文件
            'swf': '/plugin/jquery-uploadify/script/uploadify.swf',
            //后台处理的页面
            'uploader': '/file/upload;jsessionid='+$("#sessionIdInput").val(),
            //按钮显示的文字
            'buttonText': '上传图片',
            //显示的高度和宽度，默认 height 30；width 120
            //'height': 15,
            //'width': 80,
            //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
            //在浏览窗口底部的文件类型下拉菜单中显示的文本
            'fileTypeDesc': 'Image Files',
            //允许上传的文件后缀
            'fileTypeExts': '*.gif; *.jpg; *.png',
            //发送给后台的其他参数通过formData指定
            'formData': { 'index': '1' ,'uuid':cookieUtil.getCookie("uuid")},
            //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
            //'queueID': 'fileQueue',
            //选择文件后自动上传
            'auto': true,
            //设置为true将允许多文件上传
            'multi': true,
            'fileObjName': 'file',
            //设置文件上传成功之后的回调函数
            'onUploadSuccess':function(file,data, response){
                var $data = $.parseJSON(data);
                if($data.code != "200"){
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
                var src = '${resUrl}'+$data.path.picold;

                var $imgInput = $("<input type='hidden' name='profileImg' value='"+ JSON.stringify($data.path)+"'/>");
                $(this.button[0]).parent("div").nextAll(".upload_input").empty().append($imgInput);
                var img = "<img src='"+src+"'/>";
                $(this.button[0]).parent("div").nextAll(".upload_picshowbox").empty().append(img);
            }
        };

        if($.fn.uploadify != undefined){
            $("#uploadify").uploadify(uploadifyOption);
        }

    });

})(jQuery, window, document);
