;
(function ($, win, doc) {
    $(document).ready(function () {

        var deleteItem = function(id){
            var options = {
                url: '/buyCar/remove/' + id,
                success: function (data) {
                    if (data.code == 222) {
                        win.location.reload();
                        return;
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
                },
                fail: function () {
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
        };

        var selectAll = function (){
            var isHasChecked = $(this).hasClass("checked");
            if(isHasChecked){
                $('.checkitem label').removeClass('checked');
            }else {
                $('.checkitem label').addClass('checked');
            }
        };

        var batchDelete = function(){
            var str = "";
            $('.checkitem label.checked').each(function(){
                var id = $(this).attr('data');
                str= str + id +",";
            });
            str = str.substring(0, str.length -1);
            var options = {
                url: '/buyCar/removeList',
                data:{
                    ids:str
                },
                success: function (data) {
                    if (data.code == 222) {
                        win.location.reload();
                        return;
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
                },
                fail: function () {
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
        };

        var settle = function (){
            var id = [];
            $(".checkitem label.checked").each(function(){
                id.push(parseInt($(this).attr("data-goodsId")));
            });

            if(id.length == 0){
                webAlert({
                    title: '温馨提示',
                    content: "请选择商品",
                    button: [
                        {
                            name: '确认',
                            callback:function(){
                                this.close();
                            }
                        }
                    ]
                });
                return ;
            }
            var options = {
                url: '/buyCar/settle',
                data:{
                    ids:id.join(",")
                },
                success: function (data) {
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '确认',
                                callback:function(){
                                    win.location.href="/downloadInfo/index";
                                }
                            }
                        ]
                    });
                },
                fail: function () {
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
        };


        function bindClickEvent(){
            $('.deleteItem').click(function(){
                var id = $(this).attr('data');
                webAlert({
                    width:200,
                    height:60,
                    title: '温馨提示',
                    content: "确认删除吗？",
                    button: [
                        {
                            name: '确认',
                            callback:function(){
                                deleteItem(id);
                            }
                        },{
                            name: '取消',
                            callback:function(){
                                this.close();
                            }
                        }
                    ]
                });
            });
            $('#selectAll').unbind().click(selectAll);
            $('#batchDelete').click(batchDelete);

            $('.checked_box label').click(function(){
                $(this).toggleClass('checked');

                var price = 0 ;
                $(".checkitem label.checked").each(function(){
                    price = price + parseInt($(this).attr("data-p"));
                });
                $('#totalMoney').text(price);
            });

            $("#settle").click(function(){
                webAlert({
                    width:200,
                    height:60,
                    title: '温馨提示',
                    content: "确认支付",
                    button: [
                        {
                            name: '确认',
                            callback:function(){
                                settle();
                            }
                        },{
                            name: '取消',
                            callback:function(){
                                this.close();
                            }
                        }
                    ]
                });
            });

            $(".main #createExcel").click(function(e){
                var idArray = [];
                $(".checkitem").each(function(){
                   var $this = $(this);
                   idArray.push($this.attr("goodsId"));
                });

                if(idArray.length == 0){
                    webAlert({
                        title: '温馨提示',
                        content: '页面没有数据，请刷新',
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                    return ;
                }
                e.preventDefault();
                $(this).attr("href", "/file/downloadExcel/"+idArray.join(","));
                $(this).multiDownload();
            });
        }


        var totalCount = $('#totalCount').val();
        if(totalCount >0){
            $("#trTbody").show();
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:5,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var options ={
                        url:'/buyCar/listCar',
                        data:{pageNo:num,pageSize:5},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#trTbody').before(dataHtml);

                            bindClickEvent();
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }


    });
})(jQuery, window, document);