<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>分享获取积分</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/user_style.css">
  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>

</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>

<div class="user_content">
  <div class="main">
    <div class="clearfix">
      <%@include file="/common/memberCenterLeft.jsp"%>
      <div class="right_box" id="partRight">
        <div class="sections">
          <div class="title_box">
            <span class="txt">分享</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="pd20">
                <p class="nomal_txt">您可通过QQ，MSN，QQ空间，邮件，论坛，博客等方式，向朋友推荐一下地址</p>
                <div class="shareintro_box pd20">
                  <p class="nomal_txt" id="copyContent">巨作网http://www.ejuzuo.com/share/callback/${code} 终于找到免费下载、带源头厂家的照片级高精3D家居模型的网站啦！</p>
                </div>
                <div class="share_cpbtnbox clearfix">
                  <input type="hidden" value="${code}" id="code"/>
                  <a id="copyButton"  href="javascript:void(0);" class="sharecpbtn" data-clipboard-target="copyContent">点击复制地址并分享</a>
                  <p>温馨提示：</p>
                  <p>1、分享出去的链接，只能给您添加一次积分</p>
                  <p>2、刷新当前页面， 链接会发生变化</p>
                  <p>3、同IP或自己点击无效</p>
                  <p>4、每天最多有5次赠送的机会</p>
                  <p>5、高级会员每次50分，VIP会员每次80分</p>
                  <p>6、普通会员（QQ、微信直接登录的用户）分享不能获取积分，请及时在用户中心的“我是设计师”一栏完善资料，升级成为高级会员或者成为VIP会员使用分享功能并获得积分奖励！</p>                  
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/zeroclipboard-2.1.6/dist/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" >
  var clip = new ZeroClipboard( document.getElementById("copyButton") );
  clip.on("copy", function(){
    var code = $("#code").val();
    var option = {
      url:'/share/saveCode',
      data:{code:code},
      success:function(data){
        if(data.code == 222){
          webAlert({
            title: '温馨提示',
            content: "粘贴成功",
            button: [
              {
                name: '关闭'
              }
            ]
          });
          return;
        } else {
          webAlert({
            title: '温馨提示',
            content: "粘贴失败,请刷新网页",
            button: [
              {
                name: '关闭'
              }
            ]
          });
          return;
        }
      }
    };
    $.ejuzuo.ejuzuoPost(option);
  });
</script>
</body>
</html>