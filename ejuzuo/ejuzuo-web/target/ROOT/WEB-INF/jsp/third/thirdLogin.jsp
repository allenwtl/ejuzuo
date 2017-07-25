<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>第三方登录授权页面</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>

</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>
这是第三方登录页面
<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" >
  var result = QC.Login.check();
  if(result){
    QC.Login.getMe(function(openId, accessToken){
      var option = {
        url:"/third/save/0/"+openId,
        success:function(data){
          if(data.code == 222){
            window.location.href="/index";
          }
        }
      };
      $.ejuzuo.ejuzuoPost(option);
    });
  }
</script>
</body>
</html>
