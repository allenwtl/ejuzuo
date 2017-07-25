;
(function ($, win, doc) {




    $(document).ready(function () {

        var selectAll = function (){
            var isHasChecked = $(this).hasClass("checked");
            if(isHasChecked){
                $('.checkitem label').removeClass('checked');
            }else {
                $('.checkitem label').addClass('checked');
            }
        };


        var download = function (){
            $(".downloadFile").bind("click", function (e) {
                var that = this;
                e.preventDefault();
                var fileId = $(that).attr("data");
                var options = {
                    url: "/file/downloadValid/" + fileId,
                    success: function (data) {
                        if (data.code == 444) {
                            alert(data.msg);
                            return;
                        }
                        $(that).attr("href", data.model);
                        $(that).multiDownload();
                    },
                };
                $.ejuzuo.ejuzuoGetJSON(options);
            });

            $("#downloadAll").click(function (e) {
                e.preventDefault();
                $(".downloadFile").each(function(){
                    var label = $(this).parents('tr').children('td:first-child').find('label');
                    if(label.hasClass('checked')){
                        $(this).click();
                    }
                });
            });
        };


        function bindClickEvent(){
            download();

            $('#selectAll').unbind().click(selectAll);

            $('.checked_box label').click(function(){
                $(this).toggleClass('checked');
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
                        url:'/downloadInfo/queryList',
                        data:{pageNo:num,pageSize:5},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
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