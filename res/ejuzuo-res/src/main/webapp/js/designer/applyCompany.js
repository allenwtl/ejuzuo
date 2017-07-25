;(function($, win, doc){
    $(function(){

        $(document).bind("click",function(e){
            var target = $(e.target);
            if(target.closest(".sl_box").length == 0){
                $(".sl_box").removeClass('show');
            }
        });

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

        //规模大小选择
        $('.sl_list li').click(function(){
            $(this).parents('.sl_box').find('.sl_value').text($(this).text());
            var data = $(this).attr('data');
            $('#companySizeHidden').val(data);
        });
        //擅长风格
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

        //公司性质
        $('#companyType label').click(function(){
            var data = $(this).attr('data');
            $('#designerTypeHidden').val(data);
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
                content:"确定暂存吗？",
                button:[
                    {
                        name: '确定',
                        callback:function(){
                            $("#statusId").val(0);
                            $('#applyCompany').submit();
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


        $('#submit').click(function(){
            if(!saveNextFlag){
                return ;
            }

            if(!validateParam()){
                return ;
            }


            $('#applyCompany').submit();
        });





        $.validator.setDefaults({
            submitHandler: function(form) {
                saveNextFlag = false ;
                $(form).ajaxSubmit(function(data){
                    saveNextFlag = true ;

                    var statusId =  $("#statusId").val();

                    if(data.code == 222){
                        if(statusId == 1 ){
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


        $('#applyCompany').validate();



        function validateParam(){
            var designerTypeHidden = $("#designerTypeHidden").val();
            if($.ejuzuo.isBlank(designerTypeHidden)){
                webAlert({
                    title:'温馨提示',
                    content:'请选择公司的类型',
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
                return false;
            }

            var adeptStylesHidden = $("#adeptStylesHidden").val();
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
                return false;
            }

            return true ;
        }







        function getUploadifyOption( url, text, index, callback){
            return {
                //指定swf文件
                'swf': '/plugin/jquery-uploadify/script/uploadify.swf',
                //后台处理的页面
                'uploader': url,
                //按钮显示的文字
                'buttonText': text,
                //显示的高度和宽度，默认 height 30；width 120
                //'height': 15,
                //'width': 80,
                //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
                //在浏览窗口底部的文件类型下拉菜单中显示的文本
                'fileTypeDesc': 'Image Files',
                //允许上传的文件后缀
                'fileTypeExts': '*.gif; *.jpg; *.png',
                //发送给后台的其他参数通过formData指定
                'formData': { 'index': index, 'uuid':cookieUtil.getCookie("uuid") },
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
                    callback($data, this);
/*                    var src = "${resUrl}"+$data.path;
                    var $img = $("<img src='"+src+"'/>");
                    $(this.wrapper[0]).siblings("div.upload_picshowbox").empty().append($img);*/
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
        }


        $("#uploadify_yy").uploadify(getUploadifyOption("/file/uploadP","上传营业图片", 0, function($data, that){
//            var src = "${resUrl}"+$data.path;
//            var $img = $("<img src='"+src+"'/>");
            var $img = $("<img src='/designer/queryLicenseImg?path="+$data.path+"'/>");
            $("#licenseImgHidden").val($data.path);
            $(that.button[0]).parent("div").nextAll(".upload_picshowbox").empty().append($img);
        }));

        $("#uploadify_fm").uploadify(getUploadifyOption("/file/upload","上传封面图片", 6, function($data, that){
            var src = "${resUrl}"+$data.path.picold;
            var $img = $("<img src='"+src+"'/>");
            $("#filePath").val(JSON.stringify($data.path));
            $(that.button[0]).parent("div").nextAll(".upload_picshowbox").empty().append($img);
        }));


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