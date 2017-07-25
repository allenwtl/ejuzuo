;
(function ($, win, doc) {

    $(document).ready(function () {

        $('#areaSelect span').click(function(){
            var area = $(this).attr('data');
            var style = $('#styleSelect span.on').attr('data');
            var type = $('#type').val();
            win.location.href= "/designerWork/indexList/"+type+"/"+area+"/"+style;
        });

        $('#styleSelect span').click(function(){
            var style = $(this).attr('data');
            var area = $('#areaSelect span.on').attr('data');
            var type = $('#type').val();
            win.location.href= "/designerWork/indexList/"+type+"/"+area+"/"+style;
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
                        type:$('#type').val()
                    }


                    var options ={
                        url:'/designerWork/list/',
                        data:data,
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
                            $('.designer_list').append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }

    });

})(jQuery, window, document);