;
(function ($, win, doc) {

    $(document).ready(function () {
        var promo_mySwiper = new Swiper('.js_promo .swiper-container', {
            autoplay: 5000,//可选选项，自动滑动
            loop : true,
            autoplayDisableOnInteraction : false,
            effect : 'fade',
            fade: {
                crossFade: true
            },
            pagination : '.swiper-pagination',
            paginationClickable :true
        });

        //数字家具页面左侧锚点
        var position = [
            (function(){return $(".js_pr_anchor1").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor2").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor3").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor4").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor5").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor6").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor7").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor8").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor9").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor10").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor11").offset().top - 30 ;}()),
            (function(){return $(".js_pr_anchor12").offset().top - 30 ;}())
        ];

        $(window,document).bind("scroll",function(){
            switchFloatingActive($(document).scrollTop(),position);
        });

        function switchFloatingActive(scrollTop,position){
            for(var i=0,l=position.length; i<l;i++){
                if(position[i]<=scrollTop && scrollTop < position[i+1] || !position[i+1] && scrollTop>=position[i] ){
                    $('.anchor_list li').eq(i).addClass('active');
                    $('.anchor_list li').eq(i).siblings().removeClass('active');
                }
            }
        };

        //浮动块
        var t = $('.js_setfixed').offset().top;
        $(window).scroll(function(event) {
            var x = $(window).scrollTop();
            if(x > t){
                $('.js_setfixed').addClass('fixed_begin');
            }else{
                $('.js_setfixed').removeClass('fixed_begin');
            }
        });



        $('.digitalCommon').each(function(){
           var smallSpace = $(this).attr('data');
           var dataType = $(this).attr("data-type");
            var url = "/digital/digitalList/0/"+smallSpace+"/0/0/"+dataType;
            $(this).attr("href", url);
        });












    });

})(jQuery, window, document);