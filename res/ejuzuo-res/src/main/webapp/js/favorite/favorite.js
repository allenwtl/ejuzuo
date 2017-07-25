;(function($,win,doc){
    $(document).ready(function () {

        $('#deleteFavorite').click(function(){
            var ids= [];
            $('.favoriteDelete label').each(function(){
                if($(this).hasClass('checked')){
                    ids.push($(this).attr('data'));
                }
            });

            var options = {
              url :"/favorite/deleteIds",
              data:{ids:ids.join()},
              success:function(data){
                  if(data.code == 222){
                      win.location.reload();
                      return ;
                  }
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
              fail:function(data){
                  webAlert({
                      title: '温馨提示',
                      content: '系统异常',
                      button: [
                          {
                              name: '关闭'
                          }
                      ]
                  });
              }
            };

            $.ejuzuo.ejuzuoGetJSON(options);

        });



        var totalCount = $('#totalCount').val();
        if(totalCount > 0){
            $('#pagtiona').jqPaginator({
                totalCounts: parseInt(totalCount),
                pageSize:6,
                visiblePages: 5,
                currentPage: 1,
                onPageChange: function (num, type) {
                    var options ={
                        url:'/favorite/ajaxList',
                        data:{pageNo:num,pageSize:6},
                        success:function(data){
                            var tmpl = document.getElementById('dotTemplate').innerHTML;
                            var doTtmpl = doT.template(tmpl);
                            var dataHtml = doTtmpl(data.data);
                            $('#favoriteList').empty().append(dataHtml);
                        }
                    };
                    $.ejuzuo.ejuzuoGetJSON(options);
                }
            });
        } else {
        var html = '<div class="safe_form_wrap" style="margin-left:0; margin-top:40px;">'+
                '<div class="findpassword">'+
                '<div class="txt_rq_box">'+
                '<div class="iconbox"></div>'+
                '<p class="txt_rq"> 没有收藏</p> </div> </div></div>';
            $('#favoriteList').append(html);
            $("#pageList").remove();

        }


        $('.sponsor_way_box .sponsor_way').click(function(){
            $(this).addClass('checked').siblings().removeClass('checked');
        })

        $('.js_edit_btn').click(function(event) {
            $(this).hide().siblings('.edit_toolbox').show();
            $('.edit_wrap').addClass('js_edit_status');
        });

        $('.js_cancel_btn').click(function(event) {
            $('.edit_wrap').removeClass('js_edit_status');
            $(this).parent().hide().siblings('.js_edit_btn').show();
        });


        $('.checked_box label').live("click",function(){
            $(this).toggleClass('checked');
        })
    });

})(jQuery, window, document);
