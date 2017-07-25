;(function($, win, doc){

    $(document).ready(function(){

        $('.sl_box').click(function(event){
            event.stopPropagation();
            $(this).toggleClass('show');
        });
        $('.province_sl_list li').click(function(){
            $(this).parents('.sl_box').find('.sl_value').text($(this).text());
            var province =  $(this).attr("data");
            $('#proviceHidden').val(province);
            provinceSelect(province);
        });


        $(document).bind("click",function(e){
            var target = $(e.target);
            if(target.closest(".sl_box").length == 0){
                $(".sl_box").removeClass('show');
            }
        });


        $('#zhiye label').click(function(){
            var id = $(this).attr("data");
            $('#careerHidden').val(id);
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

        ////擅长风格
        //$('.checked_box label').live("click",function(){
        //    $(this).toggleClass('checked');
        //});

        var saveNextFlag = true ;
        $('#saveNext').click(function(){
            if(!saveNextFlag){
                return ;
            }

            if(!validateParam()){
                return ;
            }

            webAlert({
                title:'温馨提示',
                content:"确定暂存！",
                button:[
                    {
                        name: '确定',
                        callback:function(){
                            $("#statusId").val(0);
                            $('#applyDesigner').submit();
                        }
                    } , {
                        name: '取消',
                        callback:function(){
                            this.close();
                        }
                    }
                ]
            });

        });


        // $.validator.setDefaults({
        //     submitHandler: function(form) {
        //         saveNextFlag = false ;
        //         $(form).ajaxSubmit(function(data){
        //             saveNextFlag = true ;


        //             webAlert({
        //                 title:'温馨提示',
        //                 content:data.msg,
        //                 button:[
        //                     {
        //                         name:'关闭'
        //                     }
        //                 ]
        //             });
        //         });
        //     }
        // });



        var submitFlag = true ;
        $('#submit').click(function(){
            if(!submitFlag){
                return ;
            }

            if(!validateParam()){
                return ;
            }

            $('#applyDesigner').submit();
        });


        $.validator.setDefaults({
            submitHandler: function(form) {
                submitFlag = false ;
                $(form).ajaxSubmit(function(data){
                    submitFlag = true ;

                    var statusId =  $("#statusId").val();

                    if(data.code == 222){
                        //win.location.href= "/designer/toworks/3";
                        if(statusId == "1"){
                            webAlert({
                                title:'温馨提示',
                                content:"提交审核成功",
                                button:[
                                    {
                                        name: '上传个人作品',
                                        callback:function(){
                                            win.location.href= "/designer/toworks/3";
                                        }
                                    } , {
                                        name: '取消',
                                        callback:function(){                                        	
                                        	this.close();
                                        	win.location.href= "/designer/setting";
                                        }
                                    }
                                ]
                            });
                            return ;
                        }

                        win.location.href= "/designer/setting";
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


        function validateParam(){
            var adeptStylesHidden = $("#adeptStylesHidden").val();
            if( $.ejuzuo.isBlank(adeptStylesHidden) ){
                webAlert({
                    title:'温馨提示',
                    content:'请选择擅长的风格',
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                return false;
            }

            var careerHidden = $("#careerHidden").val();
            if($.ejuzuo.isBlank(careerHidden)){
                webAlert({
                    title:'温馨提示',
                    content:'请选择职业的身份',
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                return false;
            }
            return true ;
        }





        $('#applyDesigner').validate({});


        var uploadifyOption = {
            //指定swf文件
            'swf': '/plugin/jquery-uploadify/script/uploadify.swf',
            //后台处理的页面
            'uploader': '/file/upload;jsessionid='+$("#sessionIdInput").val(),
            //按钮显示的文字
            'buttonText': '上传封面图片',
            //显示的高度和宽度，默认 height 30；width 120
            //'height': 15,
            //'width': 80,
            //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
            //在浏览窗口底部的文件类型下拉菜单中显示的文本
            'fileTypeDesc': 'Image Files',
            //允许上传的文件后缀
            'fileTypeExts': '*.gif; *.jpg; *.png',
            //发送给后台的其他参数通过formData指定
            'formData': { 'index': '6' , 'uuid':cookieUtil.getCookie("uuid")},
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
            'onUploadSuccess':function(file, data, response){
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

                var src = "${resUrl}"+$data.path.picold;
                var $img = $("<img src='"+src+"'/>");
                $(this.button[0]).parent("div").nextAll(".upload_picshowbox").empty().append($img);
                $("#filePath").val(JSON.stringify($data.path));
            },
            'onSelectError':function(file, errorCode, errorMsg){
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

        $("#uploadify_fm").uploadify(uploadifyOption);



        function provinceSelect(province){
            var options= {
                url:"/area/city/"+province,
                success:function(data){
                    if(data.code == 444){
                        $("#city").empty();
                        return ;
                    }

                    var html = "";
                    var model = data.model;
                    for (var i = 0; i < model.length; i++) {
                        html = html + "<li data=" + model[i].id + ">" + model[i].name + "</li>";
                    }
                    $("#city").empty().append(html);
                    $('.county_sl_list .sl_value').text('');
                    $('#cityHidden').val();

                    $('.county_sl_list li').click(function(){
                        $(this).parents('.sl_box').find('.sl_value').text($(this).text());
                        $('#cityHidden').val($(this).attr("data"));
                    });
                }
            };
            $.ejuzuo.getProvince(options);
        }

    });

})(jQuery, window, document);