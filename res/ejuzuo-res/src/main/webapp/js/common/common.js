


$(function(){
	/** 顶部搜索框下拉 **/
	$('.js_screen_select').mouseover(function(event) {
		$(this).addClass('hover');
	});

	$('.js_screen_select').mouseout(function(event) {
		$(this).removeClass('hover');
	});

	$('.js_screen_select li a').click(function(){
		$('.js_select_value').text($(this).text());
		$('.js_select_value').attr("data", $(this).attr("data"));
	});


	//导航栏下拉
/*	var navigation = function(){
		$('.js_pull_list').mouseover(function(){
			$(this).addClass('hover');
		});
		$('.js_pull_list').mouseout(function(event) {
			$(this).removeClass('hover');
		});
		var timer = null;
		$('.alltypeLi').mouseover(function(event) {
			clearTimeout(timer);
			$('.all_type_list').addClass('show');
		});
		$('.alltypeLi').mouseout(function(event){
			timer = setTimeout(function(){
				$('.all_type_list').removeClass('show');
			},1)
		})
		$('.alltype_aLi').mouseover(function(event) {
			clearTimeout(timer);
			$(this).addClass('hover').siblings().removeClass('hover');
		});
		$('.alltype_aLi').mouseout(function(event) {
			event.stopPropagation();
			$(this).removeClass('hover');
			timer = setTimeout(function(){
				$('.all_type_list').removeClass('show');
			},1)
		});
	};*/

	//筛选栏
	$('.right_screen span').click(function(event) {
		$(this).addClass('on').siblings().removeClass('on');
	});

	//收藏按钮
	// $('.js_collect_icon').click(function(){
	// 	$(this).toggleClass('on');
	// })


	$('.radio_box label').click(function(){
		$(this).addClass('checked').siblings().removeClass('checked');
	})
/*	$('.checked_box label').live("click",function(){
		$(this).toggleClass('checked');
	})*/


	//全选
/*	$('.checkedAll').click(function(event) {
		if($(this).children('label').hasClass('checked')){
			$(this).parents('.havaCheckedAll').find('.bindCheckedAll').children('label').addClass('checked');
		}else{
			$(this).parents('.havaCheckedAll').find('.bindCheckedAll').children('label').removeClass('checked');

		}

		$('.bindCheckedAll').click(function(event) {
			$(this).parents('.havaCheckedAll').find('.checkedAll').children('label').removeClass('checked');
		});

	});*/


	//用户中心设置菜单栏高度
	$('#partLeft').outerHeight($('#partRight').outerHeight());



	//弹窗引用
	//showTips();
	//showMsg();
	//showReg();
	//showLogin();

});


/*******************************

 弹窗


 *******************************/

document.write("<script src='${resUrl}\/js\/common\/webdialog.js'><\/script>"); //添加弹窗组件js

//消息框
var msgDL = {
	title: "<div></div>",
	content: "<div class='mydialog_cont mydialog_nomal'>"+
	"<div>"+
	"<h3 class='titletxt'>温馨提示</h3>"+
	"<p class='tipstxt'>你已成为巨作普通会员，<br>系统赠送您50积分</p>"+
	"</div>"+
	"</div>"
};

//提示框
var tipsDL = {
	title: "<div class='dltitle'>温馨提示</div>",
	content:"<div class='mydialog_cont mydialog_tips'>"+
	"<div>"+
	"<h3 class='titletxt'>保存成功</h3>"+
	"<div class='btn_box'><a href='javascript:;' class='closethisdl_btn'>确定</a></div>"+
	"</div>"+
	"</div>"
};

//注册框
var regDL = {
	title: "<div></div>",
	content: "<div class='mydialog_cont mydialog_reg'>"+
	"<div>"+
	"<div class='logobox'></div>"+
	"<p class='webintro'>巨作网中国最大的3D家具模型下载网站，是优秀设计师必不可少的平台</p>"+
	"<div class='regway_box clearfix'>"+
	"<a href='/qq/login/index'><i class='qq_102_icon'></i><span>QQ 账号注册</span></a>"+
	"<a href='/weixin/login/wx/index'><i class='wx_102_icon'></i><span>微信账号注册</span></a>"+
	"<a href='/member/toRegister/mobile'><i class='zc_102_icon'></i><span>注册</span></a>"+
	"</div>"+
	"<div class='xieyibox checked_box'>"+
	"<label class='checked' for=''><i></i>我已阅读并接受 <a href='/member/copyright' target='_blank'>版权声明</a> 和 <a href='/member/privacy' target='_blank'>隐私保护</a> 条款</label>"+
	"</div>"+
	"</div>"+
	"</div>"
};

//登录框

var loginDL = {
	title: "<div class='dltitle'>请先登录或注册</div>",
	content: "<div class='mydialog_cont mydialog_login'>"+
	"<div>"+
	"<div class='login_form_wrap'>"+
	"<div class='ele_wrap'>"+
	"<div class='input_block mb20'>"+
	"<span class='star'>*</span>"+
	"<input type='text' class='w350' placeholder='邮箱｜手机' id='account'>"+
	"</div>"+
	"<div class='input_block mb20'>"+
	"<span class='star'>*</span>"+
	"<input type='password' class='w350' placeholder='密码' id='password'>"+
	"</div>"+
	"<div class='btn_block'>"+
	"<a href='javascript:ajaxLogin();' class='block_btn btn'>登&nbsp;&nbsp;&nbsp;&nbsp;录</a>"+
	"</div>"+
	"<p class='logineretxt'></p>"+
	"</div>"+
	"<div class='loginLink'><a href='/member/forgetPwd/index'>忘记密码</a>|<a href='javascript:showReg();'>免费注册</a></div>"+
	"<div class='otherLogType'>"+
	"<a href='/qq/login/index' >"+
	"<i class='qq_icon'></i>"+
	"<span>QQ账号登录</span>"+
	"</a>"+
	"<a href='/weixin/login/wx/index'>"+
	"<i class='wx_icon'></i>"+
	"<span>微信账号登录</span>"+
	"</a>"+
	"</div>"+
	"</div>"+
	"</div>"+
	"</div>"
};

function showMsg(){
	var dailog_wxts = webAlert({
		drag:false,
		title:msgDL.title,
		padding:0,
		content: msgDL.content
	});
	window.onresize = function(){
		dailog_wxts.position("50%","50%");
	}
}

function showTips(){
	var dailog_wxts = webAlert({
		drag:false,
		title:tipsDL.title,
		padding:0,
		content: tipsDL.content
	});
	$('.closethisdl_btn').click(function(){
		dailog_wxts.close();
	})
	window.onresize = function(){
		dailog_wxts.position("50%","50%");
	}
}
function showReg(){
	/***var dailog_wxts = webAlert({
		drag:false,
		title:regDL.title,
		padding:0,
		fixed:true,
		content: regDL.content
	});
	window.onresize = function(){
		dailog_wxts.position("50%","50%");
	};***/
	
	window.location = "/member/toRegister/mobile";
}
function showLogin(){
	var dailog_wxts = webAlert({
		drag:false,
		title:loginDL.title,
		padding:0,
		fixed:true,
		content: loginDL.content
	});
	$('.loginLink a').click(function(){
		dailog_wxts.close();
	});
	window.onresize = function(){
		dailog_wxts.position("50%","50%");
	}
}

function ajaxLogin(){
	var account = $('#account').val();
	var password = $('#password').val();

	if($.ejuzuo.isBlank(account)){
		$('.logineretxt').text('用户名不能为空');
		return ;
	}

	if($.ejuzuo.isBlank(password)){
		$('.logineretxt').text('密码不能为空');
		return ;
	}
	var options ={
		url:'/login/in',
		data:{
			account:account,
			password:password
		},
		success:function(data){
			if(data.code == 444){
				$('.logineretxt').text(data.msg);
				return ;
			}
			window.location.reload();
		}
	};
	$.ejuzuo.ejuzuoPost(options);
}
