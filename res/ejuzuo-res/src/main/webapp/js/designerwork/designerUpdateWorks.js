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
        'formData': { 'index': '7' ,'uuid':cookieUtil.getCookie("uuid")},
        //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
        //'queueID': 'fileQueue',
        //选择文件后自动上传
        'fileSizeLimit':'1024KB',
        'auto': true,
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
                $imgInput = $("<input type='hidden' name='coverImg' value='"+$data.path.pic280280+"'/>");
            } else {
                $imgInput = $("<input type='hidden' name='img' value='"+$data.path.pic280280+"'/>");
            }
            $(this.button[0]).parent("div").nextAll(".upload_input").empty().append($imgInput);
            var img = "<img src='${resUrl}"+$data.path.pic280280+"'/>";
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

        $('.delete').unbind('click').click(function(){
            $(this).parents('div.deleteDiv').remove();
        });

        $('.add').unbind('click').click(function(){
            var tmpl = document.getElementById('dotTemplate').innerHTML;
            $('#uploadFile').append(tmpl);
            $('.uploadify-input').last().attr("id", "uploadify-input_"+new Date().getMilliseconds())
            bindClickEvent();

            $(".uploadify-input").uploadify(uploadifyOption);
        });
    };


    //默认选中作品风格
    function selectStyle(){
        var style  = $("#adeptStylesHidden").val();
        var adeptArray = style.split(",");
        for(var i= 0; i<adeptArray.length; i++){
            $('#fengge label').each(function(){
                if($(this).attr('data') == adeptArray[i]){
                    $(this).addClass('checked');
                }
            });
        }
    }


    $(document).ready(function () {

        selectStyle();

        $('.sl_box').click(function(event){
            event.stopPropagation();
            $(this).toggleClass('show');
        });
        $('.sl_list li').click(function(){
            $(this).parents('.sl_box').find('.sl_value').text($(this).text());
        });
        $(document).bind("click",function(e){
            var target = $(e.target);
            if(target.closest(".sl_box").length == 0){
                $(".sl_box").removeClass('show');
            }
        });

        bindClickEvent();


        var workSubmitFlag = true ;
        $('#workSubmit').click(function(){
            if(!workSubmitFlag){
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

            $('#worksForm').submit();
        });


        $.validator.setDefaults({
            submitHandler: function(form) {
                workSubmitFlag = false ;
                $(form).ajaxSubmit(function(data){
                    workSubmitFlag = true ;
                    if(data.code == 222){
                        var business = $('#business').val();
                        if(business == "apply"){
                            win.location.href= "/designer/afterWorkIndex";
                        }else {
                            win.location.reload();
                        }
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
                });
            }
        });

        $('#worksForm').validate();



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


        $(".uploadify-input").uploadify(uploadifyOption);

    });

})(jQuery, window, document);