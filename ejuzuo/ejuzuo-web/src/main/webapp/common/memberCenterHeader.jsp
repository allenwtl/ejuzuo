<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" charset="utf-8"></script>
<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
<div class="topWrapper">
  <div class="welcome">
    <div class="main">
      <div class="cs_tel">客服电话：400-800-8758&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客服QQ：3543723054&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工作时间：周一至周六9:00-18:00</div>
      <div>欢迎您到来巨作数字家居平台!</div>
    </div>
  </div><!-- welcome END-->
  <div class="header">
    <div class="main">
      <div class="left_box">
        <a href="/index" class="logo"><img src="${resUrl}/images/logo.png" class="blockImg" alt=""></a>
      </div>
      <div class="right_box">
        <div class="search_box">
          <div class="screen_select js_screen_select">
            <div class="select_value js_select_value" data="${type== null ? '111':type}">${content == null ? "全部":content}</div>
            <ul class="select_list">
              <li><a href="javascript:;" class="item-select" data="111">全部</a></li>
              <li><a href="javascript:;" class="item-select" data="0">商品</a></li>
              <li><a href="javascript:;" class="item-select" data="4">设计师</a></li>
              <li><a href="javascript:;" class="item-select" data="1">设计作品</a></li>
              <li><a href="javascript:;" class="item-select" data="2">活动</a></li>
              <li><a href="javascript:;" class="item-select" data="3">新闻资讯</a></li>
            </ul>
          </div>
          <input type="text" class="search_cont" placeholder="请输入您要查找的内容" value="${keyword}" id="keyword">
          <a href="javascript:;" class="search_btn" id="search">搜索</a>
        </div><!--search_box END-->
        <div class="login_statusBox">
          <!-- 未登录 -->
          <c:choose>
            <c:when test="${requestScope== null or requestScope.memberInCache == null}">
              <div class="login_statusCont">
                <div><a href="javascript:showReg();" class="link_txt">注册</a><span class="fgline">|</span><a href="/login/toLogin" class="link_txt">登录</a><a <%--id="qq_login_btn"--%> href="/qq/login/index" class="link_txt login_style login_qq ml30"><i></i>QQ登录</a><a href="/weixin/login/wx/index" class="link_txt login_style login_wx ml20"><i></i>微信登录</a></div>
              </div>
            </c:when>
            <c:otherwise>
              <div class="login_statusCont">
                <div>
                  您好，
                  <a href="javascript:void(0);" class="nick_txt ellipTxt">${requestScope.memberInCache.nickName}!</a>
                  <c:if test="${ not requestScope.memberInCache.vip}">
                    <i class="level_icon level1"></i>
                    <a href="/donate/index" class="hqvip_btn">获取VIP</a>
                  </c:if>
                  <a href="/member/center" class="nick_txt ellipTxt" style="padding-left: 10px;">用户中心</a>|
                  <a href="javascript:void(0);" class="nick_txt ellipTxt" id="logOut">退出</a>
                </div>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div><!--header END-->
</div>
<script type="text/javascript" src="${resUrl}/js/util/cookieUtil.js"></script>
<script type="text/javascript">
  $('#logOut').click(function(){
    var url = "/login/asyncOut";
    $.getJSON( url,function(){
      cookieUtil.delCookie("uuid");
      window.location.reload();
    });
  });
</script>
