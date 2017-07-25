;
(function ($, win, doc) {

    $(document).ready(function () {

        $('.sponsor_btn').click(function(){
            var donateType = $(this).attr('data');
            if(donateType != 4 && donateType !=5){
                donateType = 4;
            }
            win.location.href ="/donate/type/"+donateType;
        });


    });

})(jQuery, window, document);