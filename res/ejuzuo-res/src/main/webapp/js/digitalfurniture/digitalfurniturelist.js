;
(function ($, win, doc) {

    $(document).ready(function () {

        $('.js_showhide_btn').click(function () {
            if ($(this).text() == "更多") {
                $(this).text("收起");
            } else {
                $(this).text("更多");
            }
            $(this).parents('.js_right_screen').toggleClass('show_right_screen');
        })

        //浮动块
        var t = $('.js_setfixed').offset().top;
        $(window).scroll(function (event) {
            var x = $(window).scrollTop();
            if (x > t) {
                $('.js_setfixed').addClass('fixed_begin');
            } else {
                $('.js_setfixed').removeClass('fixed_begin');
            }
        });


        $("#brand").find("span").click(function(){
            $("#brand").find("span").removeClass('on');
            $(this).addClass('on');
        });


        $('.clickQuery span').click(function () {

            var smallSpace = $('#smallSpace span.on').attr('data');
            if ($(this).parent('div').attr('id') == 'bigSpace') {
                smallSpace = 0;
            }

            var bigSpace = $('#bigSpace span.on').attr('data');
            if (bigSpace == 0) {
                smallSpace = 0;
            }

            var style = $('#style span.on').attr('data');
            var brand = $('#brand span.on').attr('data');
            //var brand = $(this).attr('data');
            var digitalType = $("#digitalType span.on").attr('data');
            var url = '/digital/digitalList/' + bigSpace + '/' + smallSpace + '/' + style + '/' + brand + '/'+digitalType;

            win.location.href = url;
        });


        var totalCount = $('#totalCount').val();

        if( totalCount > 0){

            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize: 12,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var data = {
                        pageNo: num,
                        pageSize: 12,
                        area: $('#areaSelect span.on').attr('data'),
                        style: $('#styleSelect span.on').attr('data'),
                        type: $('#type').val()
                    }

                    var bigSpace = $('#bigSpace span.on').attr('data');
                    var smallSpace = $('#smallSpace span.on').attr('data');
                    if (bigSpace == 0) {
                        smallSpace = 0;
                    }

                    var style = $('#style span.on').attr('data');
                    var brand = $('#brand span.on').attr('data');
                    var digitalType = $("#digitalType span.on").attr('data');

                    var options = {
                        url: '/digital/digitalAjaxList/' + bigSpace + '/' + smallSpace + '/' + style + '/' + brand+'/'+digitalType,
                        data: data,
                        success: function (data) {
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
                            $('#produceList').append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        } else {

        }



    });

})(jQuery, window, document);