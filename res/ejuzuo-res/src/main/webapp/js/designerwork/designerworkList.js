;
(function ($, win, doc) {

    //给修改按钮绑定事件
    function bindUpdateEvent(){
        $(".updateItem").click(function(){
            var id = $(this).attr("data");
            win.location.href = "/designerWork/update/"+id;
        });
    }


    //发布执行
    function deployAction(that){
        var id = $(that).attr("data");
        var option= {
            url:"/designerWork/deploy/"+id,
            success:function(data){
                if(data.code == 222){
                    win.location.reload();
                    return;
                }
                webAlert({
                    title:'温馨提示',
                    content: data.msg,
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
            },
            fail:function(){
                webAlert({
                    title:'温馨提示',
                    content: '系统错误',
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
            }
        };
        $.ejuzuo.ejuzuoPost(option);
    }

    //给发布按钮绑定事件
    function bindDeployEvent(){
        $('.deployItem').click(function(){
            var that = this ;
            webAlert({
                title:'温馨提示',
                content:'确认要修改发布状态吗',
                width:160,
                button:[
                    {
                        name:'确认',
                        callback:function(){
                            deployAction(that);
                        }
                    }, {
                        name:'取消',
                        callback:function(){
                            this.close();
                        }
                    }
                ]
            });
        });
    }



    //删除执行
    function deleteAction(that){
        var id = $(that).attr("data");
        var option = {
            url:"/designerWork/deleteById/"+id,
            success:function(data){
                if(data.code == 222){
                    win.location.reload();
                    return;
                }
                webAlert({
                    title:'温馨提示',
                    content: data.msg,
                    button:[
                        {
                            name:'关闭'
                        }
                    ]
                });
            }
        };

        $.ejuzuo.ejuzuoPost(option);
    }

    //给删除按钮绑定事件
    function bindDeleteEvent(){
        $('.deleteItem').click(function(){
            var that = this ;
            webAlert({
                title:'温馨提示',
                content:'确认删除吗',
                width:160,
                button:[
                    {
                        name:'确认',
                        callback:function(){
                            deleteAction(that);
                        }
                    }, {
                        name:'取消',
                        callback:function(){
                            this.close();
                        }
                    }
                ]
            });
        });
    }

    //分页空间
    function pageList(){
        var totalCount = $('#totalCount').val();
        if(totalCount >0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:5,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var options ={
                        url:'/designerWork/ajaxList',
                        data:{pageNo:num,pageSize:5},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#trTbody').before(dataHtml);

                            bindDeleteEvent();
                            bindDeployEvent();
                            bindUpdateEvent();
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }
    }

    $(document).ready(function () {
        pageList();
    });

})(jQuery, window, document);