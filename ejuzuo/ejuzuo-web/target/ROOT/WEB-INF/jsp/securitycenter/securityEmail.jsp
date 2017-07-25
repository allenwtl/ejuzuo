<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>安全中心</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>

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
            <span class="txt">个人中心</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="safe_center">
                <div class="tabWrap">
                  <div class="tabBtnBox clearfix">
                    <a href="/security/index/mobile" class="tabBtn">手机认证</a>
                    <a href="/security/index/email" class="tabBtn on">邮箱认证</a>
                    <a href="/security/toUpdatePassword" class="tabBtn">修改密码</a>
                  </div>
                  <div class="tabContBox">
                    <div class="tabCont" style="min-height: 600px">
                      <div class="pt155">
                        <div class="safe_form_wrap">
                          <c:if test="${!email}">
                          <div class="ele_wrap">
                            <div class="input_block mandatory mb20">
                              <span class="star">*</span>
                              <input type="email" class="w230" placeholder="请输入邮箱" id="email">
                              <a href="javascript:void(0)" class="btn" id="sendEmailCode" style="width: 70px;">发送验证码</a>
                            </div>
                            <div class="input_block mandatory mb20">
                              <span class="star">*</span>
                              <input type="text" class="w350" placeholder="请输入验证码" id="emailCode">
                            </div>
                            <div class="btn_block">
                              <a href="javascript:void(0)" class="block_btn btn" id="authEmail">提交认证</a>
                            </div>
                          </div>
                          </c:if>
                          <c:if test="${email}">
                            <div class="findpassword">
                              <div class="txt_rq_box">
                                <div class="iconbox"><i class="gou_icon"></i></div>
                                <p class="txt_rq">
                                  邮箱已经认证
                                </p>
                              </div>
                            </div>
                          </c:if>
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
    </div>
  </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/security/security.js"></script>
</body>
</html>