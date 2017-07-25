<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="left_box">
  <div class="logo_box"><a href="/index"><img src="${resUrl}/images/user/user_logo.png" class="blockImg" alt=""></a></div>
  <ul class="nemu_list">
    <li class="${menu eq 'index' ? 'active':''}"><a href="/index">返回首页</a></li>
    <li class="${menu eq 'login' ? 'active':''}"><a href="/login/toLogin">登录</a></li>
    <li class="${menu eq 'register' ? 'active':''}"><a href="javascript:showReg();">免费注册</a></li>
  </ul>
</div>
