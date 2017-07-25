;(function($, win, doc){

    $(document).ready(function(){

        $.ejuzuo.follow();

        var str = $('.showmore_wrap').html();
        var cpstr = "<div class='show_msgwrap'>"+str+"</div>"
        $('.js_more_dt').click(function(){
            showMore(cpstr);
        });
        function showMore(oString){
            var dailog_wxts = webAlert({
                drag:false,
                title:"<div></div>",
                padding:0,
                content: oString
            });
            window.onresize = function(){
                dailog_wxts.position("50%","50%");
            }
        }


        var totalCount = $('#totalCount').val();
        var memberId = $('#memberId').val();
        if(totalCount >0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:4,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var data = {
                        pageNo:num,
                        pageSize:4,
                        area:$('#areaSelect span.on').attr('data'),
                        style:$('#styleSelect span.on').attr('data'),
                        type:$('#type').val()
                    }


                    var options ={
                        url:'/designerWork/designerList/'+memberId,
                        data:data,
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
                            $('#designerWork').append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }


    });

})(jQuery, window, document);