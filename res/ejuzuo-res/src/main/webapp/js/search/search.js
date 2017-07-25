;(function($,win,doc){

    $(document).ready(function () {


        var totalCount = $('#totalCount').val();
        if(totalCount > 0){
            $('#searchPage').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:8,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var type= $('.js_select_value').attr('data');
                    var word = $('#keyword').val();
                    var options ={
                        url:'/search/ajaxList',
                        data:{pageNo:num,pageSize:8,objectType:type,keyword:word},
                        success:function(data){
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#result').empty().append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }

    });

})(jQuery, window, document);
