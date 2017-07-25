;(function(win, doc){

    var cookieUtil = {
        delCookie:function(name){
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval=this.getCookie(name);
            if(cval!=null)
                document.cookie= name + "="+cval+";expires="+exp.toGMTString();
        },

        getCookie:function(name){
            var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

            if(arr=document.cookie.match(reg))

                return unescape(arr[2]);
            else
                return null;
        }
    };

    win.cookieUtil = cookieUtil;

})(window, document);