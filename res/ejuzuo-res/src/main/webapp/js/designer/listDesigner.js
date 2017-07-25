;
(function ($, win, doc) {

    $(document).ready(function () {


        $("#designerType span").click(function(){
            var type = $(this).attr("data");
            var style = $('#styleSelect span.on').attr('data');
            var area = $('#areaSelect span.on').attr('data');
            win.location.href= "/designer/queryDesigner/"+type+"/"+area+"/"+style;
        });

        $('#areaSelect span').click(function(){
            var area = $(this).attr('data');
            var style = $('#styleSelect span.on').attr('data');
            var type = $('#designerType span.on').attr('data');
            win.location.href= "/designer/queryDesigner/"+type+"/"+area+"/"+style;
        });

        $('#styleSelect span').click(function(){
            var style = $(this).attr('data');
            var area = $('#areaSelect span.on').attr('data');
            var type = $('#designerType span.on').attr('data');
            win.location.href= "/designer/queryDesigner/"+type+"/"+area+"/"+style;
        });


        var totalCount = $('#totalCount').val();

        if(totalCount > 0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:20,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var data = {
                        pageNo:num,
                        pageSize:20,
                        area:$('#areaSelect span.on').attr('data'),
                        style:$('#styleSelect span.on').attr('data'),
                        type:$('#designerType span.on').attr('data')
                    }


                    var options ={
                        url:'/designer/ajaxDesigner',
                        data:data,
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('.designer_list').append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }

    });

})(jQuery, window, document);