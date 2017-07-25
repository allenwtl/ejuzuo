<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>邮箱修改</title>
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
                    <a href="/member/showInfo" class="tabBtn">个人信息修改</a>
                    <a href="/member/toUpdateME/mobile" class="tabBtn">手机号码修改</a>
                    <a href="/member/toUpdateME/email" class="tabBtn on">邮箱修改</a>
                  </div>
                  <div class="tabContBox">
                    <div class="tabCont">
                      <div style="padding-left:85px; padding-top:30px; font-size:16px; color:#666; min-height: 400px;">
                        <div class="safe_form_wrap" style="margin-left:0; margin-top:40px;">
                          <form id="updateMEForm" action="/member/updateME/${type}" method="post">
                            <div class="ele_wrap">
                              <div class="input_block mandatory mb20">
                                <span class="star">*</span>
                                <input type="text" class="w350" name="oldStr" placeholder="请输入旧邮箱" required>
                              </div>
                              <div class="input_block mandatory mb20">
                                <span class="star">*</span>
                                <input type="text" id="newStr" name="newStr" class="w230" placeholder="请输入新邮箱" required>
                                <a href="javascript:void(0);" class="btn" id="sendCode" style="width: 70px;" data="email">发送验证码</a>
                              </div>
                              <div class="input_block mandatory mb20">
                                <span class="star">*</span>
                                <input type="text" class="w350" name="code" placeholder="请输入验证码" required>
                              </div>
                              <div class="btn_block">
                                <a href="javascript:void(0);" class="block_btn btn" id="submitUpdate">提交更新</a>
                              </div>
                            </div>
                          </form>
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
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/member/updateMemberInfo.js"></script>
</body>
</html>