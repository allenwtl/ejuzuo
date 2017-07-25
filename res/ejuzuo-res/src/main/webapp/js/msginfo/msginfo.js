;
(function ($, win, doc) {

    $(document).ready(function () {

        var bindEvent = function(){
            $('.js_show_all_btn').click(function(){
                //$(this).toggleClass('hide_hide').parent().siblings('.msg_cont').toggleClass('showall_txt');
                $(this).toggleClass('hide').parent().prev().toggleClass('showall_txt');
                var isHide = $(this).hasClass('hide');
                console.info(isHide);
                if( isHide ){
                    var msgId = $(this).parents('li.appendTo').attr("data");
                    var isRead = $(this).parents('li.appendTo').attr("data-read");
                    console.info(isRead);
                    if(isRead == 'true'){
                        return ;
                    }
                    var options = {
                        url:"/info/click/"+msgId,
                        success:function(data){
                            $(this).parents('li.appendTo').attr("data-read", "true");
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        };

        var totalCount = $('#totalCount').val();
        if(totalCount >0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:5,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var options ={
                        url:'/info/ajaxList',
                        data:{pageNo:num,pageSize:5},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#infoList').append(dataHtml);

                            bindEvent();
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });

        }


    });
})(jQuery, window, document);