;(function($,win,doc){

    $(document).ready(function () {



        var totalCount = $('#totalCount').val();
        if(totalCount >0 ){
            $("#trTbody").show();
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:5,
                visiblePages: 15,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var options ={
                        url:'/pointLog/list',
                        data:{pageNo:num,pageSize:5},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
                            $('#trTbody').before(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }
    });

})(jQuery, window, document);
