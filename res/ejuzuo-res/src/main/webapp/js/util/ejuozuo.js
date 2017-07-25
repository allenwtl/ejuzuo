;
(function ($, win, doc) {

/*    var itemSelectType = 0;
    $('.item-select').click(function(){
        itemSelectType = $(this).attr('data');
    });*/

    $('#search').click(function(){
        var keyword = $('#keyword').val();
        var itemSelectType = $('.js_select_value').attr("data");
        if(!!keyword){
            var url = "/search/list?objectType="+itemSelectType+"&keyword="+$('#keyword').val();
            win.location.href= encodeURI(url);
        }
    });


    $.extend({
        ejuzuo: function () {
        }
    });

    $.ejuzuo.ejuzuoPost = function (options) {
        $.post(options.url, options.data, function (data) {
            if (data.code == 500) {
                showLogin();
                //window.location.href = "/login/toLogin";
                return;
            }
            options.success(data);
        }, options.dataType).fail(function() {
            options.fail();
        });
    };

    $.ejuzuo.ejuzuoGetJSON = function (options) {
        $.getJSON(options.url, options.data, function (data) {
            if (data.code == 500) {
                //window.location.href = "/login/toLogin";
                showLogin();
                return;
            }
            options.success(data);
        }, "json");
    };


    $.ejuzuo.multiDownload = function (options) {
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
            $(".downloadFile").click();
        });
    };


    $.ejuzuo.getProvince = function (options) {
        $.getJSON(options.url, function (data) {
            options.success(data);
        });
    };


    $.ejuzuo.encodeHTML = function (a) {
        return String(a)
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#39;");
    };

    //发送验证码
    $.ejuzuo.sendCode = function (options) {
        var settings = $.extend({
            codeType: 'email', // email or mobile  给手机还是邮箱发送验证
            businessType: 0,  //业务类型  参照实体类 CheckCodeRecordCheckType
            code: '',
            success:function(){}
        }, options);

        var queryOption = {};
        if (settings.codeType == "mobile" && (settings.businessType == 0 || settings.businessType == 2 || settings.businessType == 4)) {
            queryOption.url = "/checkCodeRecord/sendSms/" + settings.businessType;
            queryOption.data = {mobile: settings.code};
        } else if (settings.codeType == "mobile" && settings.businessType == 5) {
            queryOption.url = "/checkCodeRecord/sendCode/mobile";
        } else if (settings.codeType == "email" && (settings.businessType == 0 || settings.businessType == 3 || settings.businessType == 4)) {
            queryOption.url = "/checkCodeRecord/sendEmail/" + settings.businessType;
            queryOption.data = {email: settings.code};
        } else if(settings.codeType == "email" && settings.businessType == 5){
            queryOption.url = "/checkCodeRecord/sendCode/email";
        }

        queryOption.success = settings.success;
        queryOption.fail=settings.fail ;
        $.ejuzuo.ejuzuoPost(queryOption);
    };

    $.ejuzuo.checkCode = function(options){
        var settings = $.extend({
            codeType: 'email', // email or mobile  给手机还是邮箱发送验证
            businessType: 0,  //业务类型  参照实体类 CheckCodeRecordCheckType
            code: '',//邮箱或者手机号码
            validateCode:'', //验证码
            success:function(){}
        }, options);

        var queryOption = {};
        if (settings.codeType == "mobile") {
            queryOption.url = "/checkCodeRecord/checkSms/" + settings.businessType;
            queryOption.data = {code:settings.validateCode, mobile:settings.code};
        } else if (settings.codeType == "email") {
            queryOption.url = "/checkCodeRecord/validateEmail/"+ settings.businessType+"/"+settings.validateCode;
            queryOption.data = {email:settings.code};
        }
        queryOption.success = settings.success;
        $.ejuzuo.ejuzuoPost(queryOption);
    };

    //关注或者取消
    $.ejuzuo.follow = function(){
        $('#follow').click(function(){
            var objectId = $('#memberId').val();
            var objectType = $(this).attr('data');
            var that = this ;
            var options ={
                url:"/care/follow/"+objectId+"/"+objectType,
                success:function(data){
                    if(data.code == 222){
                        if($(that).hasClass("disabled")){
                            $(that).removeClass("disabled");
                            $(that).text("+关注");
                        } else {
                            $(that).addClass("disabled");
                            $(that).text("-已关注");
                        }
                        $('#fensi').text('粉丝：'+ data.model);
                    } else {
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
                },
                fail:function(){
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
    };

    //收藏或者取消
    $.ejuzuo.collect = function(){
        $('#collect').click(function(){
            var that  =this ;
            var objectId = $(this).attr("data-id");
            var objectType = $(this).attr("data-type");
            var options ={
                url:"/favorite/save/"+objectType+"/"+objectId,
                success:function(data){
                	if (data.code == 222) {
                    	$('#like').text(data.model);
                        $(that).toggleClass('on');
                    } else {
                        webAlert({
                            title: '温馨提示',
                            content: '错误:' + data.msg,
                            button: [
                                {
                                    name: '关闭'
                                }
                            ]
                        });                    	
                    }
                },
                fail:function(){
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
    };



    $.ejuzuo.validateEmail = function (email) {
        return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(email);
    };

    $.ejuzuo.validateMobile = function (mobile) {
        return /^1[3|4|5|7|8]\d{9}$/.test(mobile);
    };

    $.ejuzuo.validateMobileOrEmail = function (value) {
        return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value) || /^1[3|4|5|7|8]\d{9}$/.test(value);
    };

    $.ejuzuo.isBlank = function (value) {
        return !value || !$.trim(value);
    };

    $.ejuzuo.getByteCount = function (value) {
        return value == null ? 0 : (value.length + value.replace(/[\u0000-\u00ff]/g, '').length);
    };

    $.ejuzuo.isRangeLength = function (value, min, max, isByte) {
        var length = !!isByte ? $.ejuzuo.getByteCount(value) : value.length;
        return (length >= min && length <= max);
    };
    $.ejuzuo.isCertNo = function (value) {
        if ($.ejuzuo.isBlank(value)) {
            return false;
        }

        if (!hd.validate.isRangeLength(value, 15, 18, false)) {
            return false;
        }

        var reg_15 = /\d{15}/;
        var reg_18 = /\d{17}([0-9]{1}|x|X)/;
        var monthPerDays = new Array("31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31");
        value = value.toLowerCase();

        if (!( (value.length == 15) ? reg_15.test(value) : reg_18.test(value) )) {
            return false;
        }

        birthDate = value.length == 15 ? "19" + value.substr(6, 6) : value.substr(6, 8);
        year = birthDate.substr(0, 4);
        if (birthDate.substr(4, 1) == '0')
            month = birthDate.substr(5, 1);
        else
            month = birthDate.substr(4, 2);
        if (birthDate.substr(6, 1) == '0')
            day = birthDate.substr(7, 1);
        else
            day = birthDate.substr(6, 2);
        dd = parseInt(day);
        mm = parseInt(month);
        yy = parseInt(year);
        days = new Date();
        gdate = days.getDate();
        gmonth = days.getMonth();
        gyear18 = days.getFullYear() - 18;
        if (mm > 12 || mm < 1 || dd > 31 || dd < 1) {
            return false;
        }
        if (year % 100 != 0) {
            if (year % 4 == 0)
                monthPerDays[1] = 29;
        } else {
            if (year % 400 == 0)
                monthPerDays[1] = 29;
        }
        if (monthPerDays[mm - 1] < dd) {
            return false;
        }

        if (value.length == 18) {
            var arTemp = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var num = 0;
            var proof;
            for (var i = 0; i < 17; i++) {
                num = num + value.substr(i, 1) * arTemp[i];
            }
            num = num % 11;
            switch (num) {
                case 0:
                    proof = '1';
                    break;
                case 1:
                    proof = '0';
                    break;
                case 2:
                    proof = 'x';
                    break;
                case 3:
                    proof = '9';
                    break;
                case 4:
                    proof = '8';
                    break;
                case 5:
                    proof = '7';
                    break;
                case 6:
                    proof = '6';
                    break;
                case 7:
                    proof = '5';
                    break;
                case 8:
                    proof = '4';
                    break;
                case 9:
                    proof = '3';
                    break;
                case 10:
                    proof = '2';
                    break;
            }
            if (value.substr(17, 1) != proof) {
                return false;
            }
        }

        mm = mm - 1;
        var prevTS18 = new Date(gyear18, gmonth, gdate, 0, 0, 0);
        var ageTs = new Date(yy, mm, dd, 0, 0, 0);
        if (prevTS18 < ageTs) {
            return false;
        }
        return true;
    };











})(jQuery, window, document);