;(function($,win,doc){
    $(document).ready(function () {

        $('.clickQuery span').click(function(){
            var uri = '';
            $('.clickQuery span.on').each(function(){
                uri = uri+'/'+$(this).attr('data');
            });

            win.location.href = '/activity/list'+uri;
        });





        var signUpFlag = true ;
        $('#signUp').click(function(){
            if(!signUpFlag){
                return ;
            }
            signUpFlag = false ;
            var options = {
                url:'/activity/signUp/'+$('#activityId').val(),
                success:function(data){
                    signUpFlag = true ;
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                },
                fail:function(){
                    signUpFlag = true ;
                    webAlert({
                        title: '温馨提示',
                        content: data.msg,
                        button: [
                            {
                                name: '关闭'
                            }
                        ]
                    });
                }
            };
            $.ejuzuoGetJSON(options);
        });



        var totalCount = $('#totalCount').val();
        if(totalCount > 0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:9,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var uri = '';
                    $('.clickQuery span.on').each(function(){
                        uri = uri+'/'+$(this).attr('data');
                    });
                    var options ={
                        url:'/activity/ajaxList'+uri,
                        data:{pageNo:num,pageSize:9},
                        success:function(data){
                            $('.appendTo').remove();
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data);
                            $('#activity').empty().append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        }


    });

})(jQuery, window, document);
