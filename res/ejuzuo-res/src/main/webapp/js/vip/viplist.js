;
(function ($, win, doc) {



    $(document).ready(function () {






        var totalCount = $('#totalCount').val();
        if(totalCount >0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:15,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var options ={
                        url:'/memberVipLog/ajaxList',
                        data:{pageNo:num,pageSize:15},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#trTbody').before(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        } else {
            var html = '<tr class="appendTo"><td><div class="checked_box checkitem">购物车中没有数据</div></td></tr>';
            $('#trTbody').before(html).remove();
        }

    });
})(jQuery, window, document);