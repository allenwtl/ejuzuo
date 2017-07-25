<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>上传作品</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp" %>

<div class="user_content">
  <div class="main">
    <div class="clearfix">

      <%@include file="/common/memberCenterLeft.jsp" %>

      <div class="right_box" id="partRight">
        <div class="sections">
          <div class="title_box">
            <span class="txt">作品管理</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="safe_center">
                <div class="tabWrap">
                  <div class="tabContBox">
                    <div class="tabCont">
                      <div class="pt155">
                        <div class="safe_form_wrap">
                          <div class="findpassword">
                            <div class="txt_rq_box">
                              <div class="iconbox"><i class="gou_icon"></i></div>
                              <p class="txt_rq">
                                您目前的申请认证状态为：${status}
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
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

</body>
</html>

