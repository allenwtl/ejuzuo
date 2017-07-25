<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl", "http://r.ejuzuo.com"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>申请设计师</title>
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/styles/user_style.css">
    <link rel="stylesheet" href="http://r.ejuzuo.com/plugin/jquery-uploadify/css/uploadify.css"  >
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
                        <span class="txt">我是设计师-个人高级认证</span>
                    </div>
                    <div class="midcont_box">
                        <div class="contheight_box whitebg">
                            <form action="/designer/save" id="applyDesigner" method="post">
                                <div class="authentication_box">
                                    <div class="mt50 mb50">
                                        <p class="intro_txt">您的身份信息仅供<span>巨作</span>认证您的真实身份使用，<br>我们保证不会在任何场合以任何形式泄露您的身份信息，请放心提交。</p>
                                    </div>
                                    <div class="clearfix">
                                        <div class="left_box">
                                            <div class="nick_input_box">
                                                <span class="star">*</span>
                                                <input type="text" class="w340" placeholder="QQ" name="qq" required>
                                            </div>
                                            <div class="ele_wrap mt20 clearfix">
                                                <div class="input_block select_box mb20 ">
                                                    <span class="star">*</span>
                                                    <input type="text" class="w70" disabled placeholder="地址">
                                                    <div class="province_sl_box sl_box">
                                                        <div class="sl_value">省</div>
                                                        <i class="icon"></i>
                                                        <div class="sl_list_wrap">
                                                            <ul class="province_sl_list">
                                                                <c:forEach items="${areaList}" var="item">
                                                                    <li data="${item.id}">${item.name}</li>
                                                                </c:forEach>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="county_sl_box sl_box">
                                                    <div class="sl_value">市</div>
                                                    <i class="icon"></i>
                                                    <div class="sl_list_wrap">
                                                        <ul class="county_sl_list" id="city">

                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="right_box">
                                            <div class="nick_input_box">
                                                <span class="star">*</span>
                                                <input type="text" class="w340" placeholder="姓名" name="name" required>
                                            </div>
                                            <div class="ele_wrap mt20">
                                                <div class="input_block mb20">
                                                    <span class="star">*</span>
                                                    <input type="text" class="w340" placeholder="详细地址(区/县)" name="address" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="youckbox clearfix">
                                        <div class="right_box">
                                            <span class="typename_box">*封面图片</span>
                                            <div class="label_wrap">
                                                <input type="file" id="uploadify_fm">
                                                <div class="upload_picshowbox"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="youckbox">
                                        <div class="radio_box clearfix">
                                            <span class="typename_box">*职业身份</span>
                                            <div class="label_wrap" id="zhiye">
                                                <c:forEach items="${zhiye}" var="item">
                                                    <label for="" data="${item.valueCode}" ><i></i>${item.valueName}</label>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="checked_box pt15 clearfix">
                                            <span class="typename_box">*擅长风格</span>
                                            <div class="label_wrap" id="fengge">
                                                <c:forEach items="${fengge}" var="item">
                                                    <label for="" data="${item.valueCode}"><i></i>${item.valueName}</label>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="pt15 clearfix">
                                            <span class="typename_box">*个人简介</span>
                                            <div class="label_wrap">
                                                <textarea name="brief" required ></textarea>
                                            </div>
                                        </div>
                                        <div class="pt15 clearfix">
                                            <span class="typename_box">设计理念</span>
                                            <div class="label_wrap">
                                                <textarea name="designIdea"></textarea>
                                            </div>
                                        </div>
                                        <div class="pt15 clearfix">
                                            <span class="typename_box">风格介绍</span>
                                            <div class="label_wrap">
                                                <textarea name="styleIntro"></textarea>
                                            </div>
                                        </div>
                                        <%--<div class="pt15 mb20 clearfix">
                                            <span class="typename_box">*项目经验</span>
                                            <div class="label_wrap">
                                                <textarea name="experience" required ></textarea>
                                            </div>
                                        </div>--%>
                                        <div class="pt15 mb20 clearfix">
                                            <span class="typename_box">代表作品</span>
                                            <div class="label_wrap">
                                                <textarea name="classWorks" ></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <input name="provice" id="proviceHidden" type="hidden" />
                            <input name="city" id="cityHidden" type="hidden" />
                            <input name="coverImg" id="filePath" type="hidden" />
                            <input name="career" id="careerHidden" type="hidden" />
                            <input name="adeptStyles" id="adeptStylesHidden" type="hidden" />
                            <input name="designerType" type="hidden" value="3">
                            <input name="status" type="hidden" value="1" id="statusId">
                            <div class="authentication_btn_box clearfix">
                                <a href="javascript:void(0);" class="btn fl" id="saveNext" style="width:200px; margin-left:110px">暂存</a>
                                <a href="javascript:void(0);" class="btn fl ml20" id="submit" style="width:200px;">提交审核</a>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/common/memberCenterFooter.jsp"%>

<script type="text/javascript" src="http://r.ejuzuo.com/js/util/cookieUtil.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-uploadify/script/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/designer/applyDesigner.js?v=1"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/validate.js"></script>
</body>
</html>