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
        var ds_cpn_reco_mySwiper = new Swiper('.js_ds_cpn_reco .swiper-container', {
            autoplay: 5000,//可选选项，自动滑动
            loop : true,
            slidesPerView : 4,
            slidesPerGroup : 4,
            autoplayDisableOnInteraction : false,
            pagination : '.swiper-pagination',
            paginationClickable :true
        })

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













    });
})(jQuery, window, document);