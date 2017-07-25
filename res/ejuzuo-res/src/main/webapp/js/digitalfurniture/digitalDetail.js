;
(function ($, win, doc) {

    function pay(that){
        var goodsId = $('#objectId').val();
        var url = "/file/downloadNow/"+goodsId;
        var options = {
            url:url,
            success:function(data){
                if(data.code ==222){
                    var downloadId = data.model;
                    var options = {
                        url: "/file/downloadValid/" + downloadId,
                        success: function (data) {
                            if (data.code == 444) {
                                webAlert({
                                    title: '温馨提示',
                                    content: data.msg,
                                    button: [
                                        {
                                            name: '关闭'
                                        }
                                    ]
                                });

                                downloadNowFlag = true ;
                                return;
                            }
                            $(that).attr("href", data.model);
                            $(that).multiDownload();

                            downloadNowFlag = true ;
                        },
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                    return ;
                }
                webAlert({
                    title: '温馨提示',
                    content: data.msg,
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
            } ,
            fail:function(){
                webAlert({
                    title: '温馨提示',
                    content: '系统异常',
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
            }
        };

        $.ejuzuo.ejuzuoPost(options);
    }


    function initFirstImg(){
        var firstImg = $("#imgselect").find("li:first-child img");
        $("#bigImg").attr("src","").attr("src", firstImg.attr("data"));
    }


    $(document).ready(function () {

        initFirstImg();

        $('#wantComment').click(function(){
            var commentContent = $.ejuzuo.encodeHTML($('#commentContent').val());
            if($.ejuzuo.isBlank(commentContent)){
                webAlert({
                    title: '温馨提示',
                    content: '评论不能为空',
                    button: [
                        {
                            name: '关闭'
                        }
                    ]
                });
                return ;
            }

            var objectId = $('#objectId').val();
            var url = "/comment/addComment/"+objectId+"/0";

            var options={
                url:url,
                data:{content:commentContent},
                success:function(data){
                        webAlert({
                            title: '温馨提示',
                            content: data.msg,
                            button: [
                                {
                                    name: '关闭'
                                }
                            ]
                        });
                    if(data.code ==222){
                        win.location.reload();
                    }
                }
            };

            $.ejuzuo.ejuzuoPost(options);

        });


        $('#addBuyCar').click(function(){
            var objectId = $('#objectId').val();
            var url= "/buyCar/addCar/"+objectId;
            var options = {
                url:url,
                success:function(data){
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
                fail:function(){
                    webAlert({
                        title: '温馨提示',
                        content: '系统异常',
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };

            $.ejuzuo.ejuzuoPost(options);
        });

        var downloadNowFlag = true ;
        $('#downloadNow').click(function(e){
            e.preventDefault();

            if($(this).hasClass("disabled")){
                return ;
            }

            if(!downloadNowFlag){
                return ;
            }
            downloadNowFlag = false ;

            var that = this ;
            var digitalId = $(that).attr("data");
            var option = {
                url:'/digital/available/'+digitalId,
                success:function(data){
                    var msg = "本次下载需要积分:"+data.price+",你当前积分余额是:"+data.balance+","
                    if(data.buy == true || data.buy == "true"){
                        msg = msg + " 是否继续？";
                        webAlert({
                            title: '温馨提示',
                            content: msg,
                            closeFn:function(){
                                downloadNowFlag = true ;
                            },
                            button: [
                                {
                                    name: '确认',
                                    callback:function(){
                                        pay(that);
                                    }
                                },
                                {
                                    name: '取消',
                                    callback:function(){
                                        this.close();
                                    }
                                }
                            ]
                        });
                    } else {
                        msg = msg + "积分不足";
                        webAlert({
                            title: '温馨提示',
                            content: msg,
                            button: [
                                {
                                    name: '去充值',
                                    callback:function(){
                                        win.location.href = "/donate/index";
                                    }
                                },
                                {
                                    name: '取消',
                                    callback:function(){
                                        this.close();
                                        downloadNowFlag = true ;
                                    }
                                }
                            ]
                        });
                    }

                },
                fail:function(){
                    webAlert({
                        title: '温馨提示',
                        content: '系统错误,请联系客服！',
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };

            $.ejuzuo.ejuzuoPost(option);
        });


        //选小图 显示大图
        // $("#imgselect img").click(function(){
        //     var imgSrc = $(this).attr("data");
        //     $("#bigImg").attr("src","").attr("src", imgSrc);
        // });

        var totalCount = $('#totalCount').val();

        if(totalCount > 0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:10,
                visiblePages: 10,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var objectId = $('#objectId').val();
                    var options ={
                        url:'/comment/list/'+objectId+'/0',
                        data:{
                            pageNo:num,
                            pageSize:10,
                        },
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
                            $('#commentInfo').append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        } else {
            $("#commentCount").empty();
        }

        $.ejuzuo.collect();
    });

})(jQuery, window, document);