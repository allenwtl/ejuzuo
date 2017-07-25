;(function($,win,doc){

    $(document).ready(function () {


        var totalCount = $('#totalCount').val();
        if(totalCount >0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:8,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var uri= $('.news_type_btn.on').attr('data');
                    var options ={
                        url:'/newsInfo/ajaxList/'+uri,
                        data:{pageNo:num,pageSize:8},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#newsInfoList').empty().append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }

    });

})(jQuery, window, document);
