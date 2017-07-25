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
  <link href="http://r.ejuzuo.com/plugin/jquery-uploadify/css/uploadify.css" type="text/css" rel="stylesheet">
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
            <form id="worksForm" action="/designerWork/updateWork" method="post">
              <input type="hidden" value="${data.id}" name="id"/>
              <div class="contheight_box whitebg">
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
                              <img src="http://r.ejuzuo.com${data.coverImg}">
                            </div>
                            <div class="upload_input">
                              <input name="coverImg" value="${data.coverImg}"  type="hidden" />
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
                        <div  style="margin-right:78px; padding:25px 0 20px; border-top:1px solid #ddd;" class="deleteDiv">
                          <div class="clearfix ele_wrap">
                            <span class="more_cg_addbtn"><input type="button" value="增加" class="add btn"></span>
                            <span class="more_cg_addbtn"><input type="button" value="删除该作品" class="delete btn"></span>
                          </div>

                          <div class="clearfix">
                            <div class="pt15 mb20 clearfix" style="float:left; width:340px;">
                              <span class="typename_box">*上传图片</span>
                              <div class="label_wrap">
                                <div class="clearfix">
                                  <div class="right_box">
                                    <input type="file" class="uploadify uploadify-input" id="uploadifyFile_${status.index}">
                                    <div class="upload_picshowbox">
                                      <img src="http://r.ejuzuo.com${item.img}">
                                    </div>
                                    <div class="upload_input">
                                      <input type="hidden" value="${item.img}" name="img" />
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="pt15 mb20 clearfix" style="float:left; margin-left:15px; width:470px;">
                              <span class="typename_box">*作品描述</span>
                              <div class="label_wrap">
                                <textarea name="intro" required="" aria-required="true" style="width:350px; height:189px;"><c:out value="${item.intro}" /></textarea>
                              </div>
                            </div>
                          </div>
                        </div>
                      </c:forEach>
                    </div>
                  </div>
                </div>
                <input type="hidden" id="business" value="work"/>
                <input name="style" id="adeptStylesHidden" type="hidden" value="${data.style}" />
                <div class="authentication_btn_box">
                  <a href="javascript:void(0);" class="btn" id="workSubmit" style="margin-left: 180px;">提交</a>
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
  <div  style="margin-right:78px; padding:25px 0 20px; border-top:1px solid #ddd;" class="deleteDiv">
    <div class="clearfix ele_wrap">
      <span class="more_cg_addbtn"><input type="button" value="增加" class="add btn"></span>
      <span class="more_cg_addbtn"><input type="button" value="删除该作品" class="delete btn"></span>
    </div>
    <div class="clearfix">
      <div class="pt15 mb20 clearfix" style="float:left; width:340px;">
        <span class="typename_box">*上传图片</span>
        <div class="label_wrap">
          <div class="clearfix">
            <div class="right_box">
              <input type="file"  class="uploadify uploadify-input">
              <div class="upload_picshowbox"></div>
              <div class="upload_input"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="pt15 mb20 clearfix" style="float:left; margin-left:15px; width:470px;">
        <span class="typename_box">*作品描述</span>
        <div class="label_wrap">
          <textarea name="intro" required="" aria-required="true" style="width:350px; height:189px;"></textarea>
        </div>
      </div>
    </div>
  </div>
</script>

<script type="text/javascript" src="http://r.ejuzuo.com/js/util/cookieUtil.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-uploadify/script/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/designerwork/designerUpdateWorks.js"></script>
</body>
</html>
