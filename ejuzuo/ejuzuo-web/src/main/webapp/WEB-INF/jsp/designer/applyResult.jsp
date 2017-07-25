<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>申请设计师</title>
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
            <span class="txt">账户设置</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="safe_form_wrap" style="margin-left:0; margin-top:40px;">
                <div class="findpassword">
                  <div class="txt_rq_box">
                    <div class="iconbox"><i class="gou_icon"></i></div>
                    <p class="txt_rq">
                      <c:if test="${status ==0}">
                        <a>申请认证</a>
                      </c:if>
                      <c:if test="${status ==1}">
                        <a>正在审核中</a>
                      </c:if>
                    </p>
                  </div>
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


</body>
</html>