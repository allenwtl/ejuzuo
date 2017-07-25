;
(function ($, win, doc) {
    function idToValue() {
        var province = $('#proviceHidden').val();
        $('.province_sl_list li').each(function () {
            if ($(this).attr('data') == province) {
                $(this).parents('.sl_box').find('.sl_value').text($(this).text());
            }
        });

        var city  = $('#cityHidden').val();
        $('.county_sl_list li').each(function () {
            if ($(this).attr('data') == city) {
                $(this).parents('.sl_box').find('.sl_value').text($(this).text());
            }
        });

        var companySize =  $('#companySizeHidden').val();
        $('.guimo_sl_list li').each(function(){
            if ($(this).attr('data') == companySize) {
                $(this).parents('.sl_box').find('.sl_value').text($(this).text());
            }
        });

        var designerType = $('#designerTypeHidden').val();
        $('#companyType label').each(function(){
            if($(this).attr('data') == designerType){
               $(this).addClass("checked");
            }
        });

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

        idToValue();

        $(document).bind("click", function (e) {
            var target = $(e.target);
            if (target.closest(".sl_box").length == 0) {
                $(".sl_box").removeClass('show');
            }
        });

        $('.sl_box').click(function (event) {
            event.stopPropagation();
            $(this).toggleClass('show');
        });


        $('.province_sl_list li').click(function () {
            $(this).parents('.sl_box').find('.sl_value').text($(this).text());
            var province = $(this).attr("data");
            $('#proviceHidden').val(province);
            provinceSelect(province);
        });


        //规模大小选择
        $('.guimo_sl_list li').click(function(){
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



        var updateInfoFlag = true ;
        $('#updateInfo').click(function(){
            if(!updateInfoFlag){
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

            $('#updateCompany').submit();
        });

        $.validator.setDefaults({
            submitHandler: function(form) {
                updateInfoFlag = false ;
                $(form).ajaxSubmit(function(data){
                    updateInfoFlag = true ;
                    if(data.code == 222){
                        if(data.doType){
                            webAlert({
                                title:'温馨提示',
                                content:data.doType,
                                button:[
                                    {
                                        name:'关闭',
                                        callback:function(){
                                            window.location.reload();
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

        $('#updateCompany').validate();




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
                'formData': { 'index': index ,'uuid':cookieUtil.getCookie("uuid") },
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



        function provinceSelect(province) {
            var options = {
                url: "/area/city/" + province,
                success: function (data) {
                    if (data.code == 444) {
                        $("#city").empty();
                        return;
                    }

                    var html = "";
                    var model = data.model;
                    for (var i = 0; i < model.length; i++) {
                        html = html + "<li data=" + model[i].id + ">" + model[i].name + "</li>";
                    }
                    $("#city").empty().append(html);


                    $('.county_sl_list li').click(function () {
                        $(this).parents('.sl_box').find('.sl_value').text($(this).text());
                        $('#cityHidden').val($(this).attr("data"));
                    });
                }
            };
            $.ejuzuo.getProvince(options);
        }

    });
})(jQuery, window, document);