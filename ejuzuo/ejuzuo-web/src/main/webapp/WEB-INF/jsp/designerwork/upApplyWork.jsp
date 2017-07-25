<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="el" uri="ejuzuo.com/el-common"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>上传作品修改</title>
  <link rel="stylesheet" href="${resUrl}/styles/global.css">
  <link rel="stylesheet" href="${resUrl}/styles/common.css">
  <link rel="stylesheet" href="${resUrl}/styles/user_style.css">
  <link href="${resUrl}/plugin/jquery-uploadify/css/uploadify.css" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="${resUrl}/plugin/jquery/jquery-1.7.2.min.js"></script>
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
            <span class="txt">账户设置</span>
          </div>
          <div class="midcont_box">
            <form id="updateWorkForm" action="/designer/updateWork" method="post">
              <div class="contheight_box whitebg">
                <div class="title_box">
                  <div class="right_step"><span class="finished">1.基本信息</span><span>></span><span>2.手机认证</span><span>></span><span>3.联系方式</span><span>></span><span>4.上传作品</span><span>></span><span>5.等待审核</span></div>
                  <span class="txt">高级用户设计师认证</span>
                </div>
                <div class="authentication_box">
                  <div class="youckbox">
                    <div class="pt15 mb20 clearfix">
                      <span class="typename_box">*作品名称</span>
                      <div class="label_wrap">
                        <div class="clearfix">
                          <div class="right_box">
                            <div class="nick_input_box">
                              <input type="text" class="w340" placeholder="请输入作品名称" name="name" value="${data.name}">
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="pt15 mb20 clearfix">
                      <span class="typename_box">*封面图片</span>
                      <div class="label_wrap">
                        <div class="clearfix">
                          <div class="right_box">
                            <input type="file" class="uploadify-input" id="uploadifyCover">
                            <div class="upload_picshowbox">
                              <img src="${data.coverImg}">
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="checked_box pt15 clearfix">
                      <span class="typename_box">*作品风格</span>
                      <div class="label_wrap" id="fengge">
                        <c:forEach items="${fengge}" var="item">
                          <label for="" data="${item.valueCode}"><i></i>${item.valueName}</label>
                        </c:forEach>
                      </div>
                    </div>
                    <div class="pt15 mb20 clearfix">
                      <span class="typename_box">*作品简介</span>
                      <div class="label_wrap">
                        <textarea name="brief" required>${data.brief}</textarea>
                      </div>
                    </div>
                    <div id="uploadFile">
                      <c:forEach items="${el:getJSONArray(data.content)}" var="item" varStatus="status">
                      <div>
                          <input type="button" value="增加" class="add"/>
                          <input type="button" value="删除该作品" class="delete" />
                        <div class="pt15 mb20 clearfix">
                          <span class="typename_box">*上传图片</span>
                          <div class="label_wrap">
                            <div class="clearfix">
                              <div class="right_box">
                                <input type="file" class="uploadify-input" id="uploadifyFile${status.index}">
                                <div class="upload_picshowbox">
                                  <img src="${item.img}">
                                </div>
                                <div class="upload_input">
                                  <input name="img" type="hidden" value="${item.img}"/>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="pt15 mb20 clearfix">
                          <span class="typename_box">*作品描述</span>
                          <div class="label_wrap">
                            <textarea name="intro" required>${item.intro}</textarea>
                          </div>
                        </div>
                      </div>
                      </c:forEach>
                    </div>
                  </div>
                </div>
                <input type="hidden" id="business" value="work"/>
                <input name="style" id="adeptStylesHidden" type="hidden" value="${data.style}"/>
                <div class="authentication_btn_box">
                  <a href="javascript:void(0);" class="btn" id="updateWork">提交修改</a>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>
<script  type="text/template" id="dotTemplate">
  <div>
    <input type="button" value="增加" class="add"/>
    <input type="button" value="删除该作品" class="delete" />
    <div class="pt15 mb20 clearfix">
      <span class="typename_box">*上传图片</span>
      <div class="label_wrap">
        <div class="clearfix">
          <div class="right_box">
            <input type="file"  class="uploadify-input">
            <div class="upload_picshowbox"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="pt15 mb20 clearfix">
      <span class="typename_box">*作品描述</span>
      <div class="label_wrap">
        <textarea name="intro" required></textarea>
      </div>
    </div>
  </div>
</script>


<script type="text/javascript" src="${resUrl}/js/util/cookieUtil.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-uploadify/script/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/designerwork/updateDesignerWork.js"></script>
</body>
</html>
