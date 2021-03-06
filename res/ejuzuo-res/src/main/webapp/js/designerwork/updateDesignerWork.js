;
(function ($, win, doc) {

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
        'fileSizeLimit':'1024KB',
        //设置为true将允许多文件上传
        'multi': true,
        'fileObjName': 'file',
        //设置文件上传成功之后的回调函数
        'overrideEvents' : ['onDialogClose'],
        'onUploadSuccess':function(file, data){
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
            var id = $(this.button[0]).attr("id").split("-")[0];
            var $imgInput = null;
            if("uploadifyCover" == id){
                $imgInput = $("<input type='hidden' name='coverImg' value='"+$data.path+"'/>");
            } else {
                $imgInput = $("<input type='hidden' name='img' value='"+$data.path+"'/>");
            }
            $(this.button[0]).parent("div").nextAll(".upload_input").empty().append($imgInput);
            var img = "<img src='"+$data.path+"'/>";
            $(this.button[0]).parent("div").nextAll(".upload_picshowbox").empty().append(img);
        },'onSelectError':function(file, errorCode, errorMsg){
            switch(errorCode) {
                case -110:
                    webAlert({
                        title:'温馨提示',
                        content:"文件 ["+file.name+"] 大小不能超过1M",
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                    break;
                case -120:
                    webAlert({
                        title:'温馨提示',
                        content:"文件 ["+file.name+"] 大小异常！",
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                    break;
                case -130:
                    webAlert({
                        title:'温馨提示',
                        content:"文件 ["+file.name+"] 类型不正确！",
                        button:[
                            {
                                name:'关闭'
                            }
                        ]
                    });
                    break;
            }
        }
    };


    var bindClickEvent = function(){

        $('.delete').unbind('click');

        $('.delete').click(function(){
            $(this).parent('div').remove();
        });

        $('.add').unbind('click');
        $('.add').click(function(){
            var tmpl = document.getElementById('dotTemplate').innerHTML;
            $('#uploadFile').append(tmpl);
            var length = $('.uploadify').size()+1;
            $('.uploadify-input').last().attr("id", "uploadify-input_"+length)
            bindClickEvent();

            $(".uploadify-input").uploadify(uploadifyOption);
        });

        $('.checked_box label').click(function(){

            $(this).toggleClass('checked');

            var data = $(this).attr('data');
            var id = '';
            $('#fengge label.checked').each(function(){
                //if(data != $(this).attr('data')){
                //    id = $(this).attr('data')+','+id;
                //}

                id = $(this).attr('data')+','+id;
            });
            //if(!$(this).hasClass('checked')){
            //    id= $(this).attr('data') +','+ id;
            //}

            id = id.substring(0, id.length -1);
            $('#adeptStylesHidden').val(id);
        });
    };



    function idToValue() {
        var adeptStyles = $('#adeptStylesHidden').val();
        var adeptArray = adeptStyles.split(",");
        for(var i= 0; i<adeptArray.length; i++){
            $('#fengge label').each(function(){
                if($(this).attr('data') == adeptArray[i]){
                    $(this).addClass('checked');
                }
            });
        }
    };


    $(function () {

        var updateWorkFlag = true ;
        $('#updateWork').click(function(){
            if(!updateWorkFlag){
                return ;
            }

            var adeptStylesHidden =  $('#adeptStylesHidden').val();

            if($.ejuzuo.isBlank(adeptStylesHidden)){
                webAlert({
                    title:'温馨提示',
                    content:'请选择擅长的风格',
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                return ;
            }

            $('#updateWorkForm').submit();
        });


        $.validator.setDefaults({
            submitHandler: function(form) {
                updateWorkFlag = false ;
                $(form).ajaxSubmit(function(data){
                    updateWorkFlag = true ;

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

        $('#updateWorkForm').validate();

        idToValue();

        bindClickEvent();

        $(".uploadify-input").uploadify(uploadifyOption);
    });
})(jQuery, window, document);