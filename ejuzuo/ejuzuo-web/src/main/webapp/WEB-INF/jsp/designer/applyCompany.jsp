<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("resUrl", "http://r.ejuzuo.com"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>申请设计师</title>
    <link rel="stylesheet" href="${resUrl}/styles/global.css">
    <link rel="stylesheet" href="${resUrl}/styles/common.css">
    <link rel="stylesheet" href="${resUrl}/styles/user_style.css">
    <link rel="stylesheet" href="${resUrl}/plugin/jquery-uploadify/css/uploadify.css">
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
                        <span class="txt">我是设计师-公司高级认证</span>
                    </div>
                    <div class="midcont_box">
                        <form action="/designer/save" method="post" id="applyCompany">
                        <div class="contheight_box whitebg">
                            <div class="authentication_box">
                                <div class="mt50 mb50">
                                    <p class="intro_txt">
                                        您的身份信息仅供<span>巨作</span>认证您的真实身份使用，<br>我们保证不会在任何场合以任何形式泄露您的身份信息，请放心提交。
                                    </p>
                                </div>
                                <div class="clearfix">
                                    <div class="left_box">
                                        <div class="ele_wrap">
                                            <div class="input_block mandatory mb20">
                                                <span class="star">*</span>
                                                <input type="text" class="w340" placeholder="公司名称" name="name" required>
                                            </div>
                                        </div>
                                        <div class="ele_wrap">
                                            <div class="input_block mandatory mb20">
                                                <span class="star">*</span>
                                                <input type="text" class="w340" placeholder="联系人" name="contact" required>
                                            </div>
                                        </div>
                                        <div class="ele_wrap clearfix">
                                            <div class="input_block select_box mandatory mb20 ">
                                                <span class="star">*</span>
                                                <input type="text" class="w70" disabled="" placeholder="收件地址">
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
                                        <div class="ele_wrap clearfix">
                                            <div class="input_block select_box mandatory mb20 ">
                                                <span class="star">*</span>
                                                <input type="text" class="w70" disabled placeholder="公司规模">
                                                <div class="province_sl_box sl_box">
                                                    <div class="sl_value">请选择</div>
                                                    <i class="icon"></i>
                                                    <div class="sl_list_wrap">
                                                        <ul class="sl_list">
                                                            <c:forEach items="${guimo}" var="item">
                                                                <li data="${item.valueCode}">${item.valueName}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="right_box">
                                        <div class="ele_wrap">
                                            <div class="input_block mandatory mb20">
                                                <span class="star">*</span>
                                                <input type="text" class="w340" placeholder="公司法人" name="corporation" required>
                                            </div>
                                        </div>
                                        <div class="ele_wrap">
                                            <div class="input_block mandatory mb20">
                                                <span class="star">*</span>
                                                <input type="tel" class="w340" placeholder="联系人手机号码" name="contactMobile" required>
                                            </div>
                                        </div>
                                        <div class="ele_wrap">
                                            <div class="input_block mandatory mb20">
                                                <span class="star">*</span>
                                                <input type="text" class="w350" placeholder="请填写详细地址" name="address" required>
                                            </div>
                                        </div>
                                        <div class="ele_wrap">
                                            <div class="input_block mandatory mb20">
                                                <span class="star">*</span>
                                                <input type="number" class="w340" placeholder="注册资金" name="regFund" required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="youckbox clearfix">
                                    <div class="right_box">
                                        <span class="typename_box">*营业执照</span>
                                        <div class="label_wrap">
                                            <input type="file" id="uploadify_yy">
                                            <div class="upload_picshowbox"></div>
                                        </div>
                                    </div><div class="right_box">
                                    <span class="typename_box">*封面图片</span>
                                    <div class="label_wrap">
                                        <input type="file" id="uploadify_fm">
                                        <div class="upload_picshowbox"></div>
                                    </div>
                                </div>
                                </div>
                                <div class="youckbox">
                                    <div class="radio_box clearfix">
                                        <span class="typename_box">*公司性质</span>
                                        <div class="label_wrap" id="companyType">
                                            <label for="" data="1"><i></i>设计公司</label><label for="" data="2"><i></i>装饰公司</label>
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
                                        <span class="typename_box">*公司简介</span>

                                        <div class="label_wrap">
                                            <textarea name="brief" required></textarea>
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
                                    <div class="pt15 clearfix">
                                        <span class="typename_box">项目经验</span>
                                        <div class="label_wrap">
                                            <textarea name="experience"></textarea>
                                        </div>
                                    </div>
                                    <div class="pt15 mb20 clearfix">
                                        <span class="typename_box">代表作品</span>

                                        <div class="label_wrap">
                                            <textarea name="classWorks"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <input name="provice" id="proviceHidden" type="hidden" />
                            <input name="city" id="cityHidden" type="hidden" />
                            <input name="companySize" type="hidden" id="companySizeHidden"/>
                            <input name="licenseImg" type="hidden" id="licenseImgHidden"/>
                            <input name="adeptStyles" id="adeptStylesHidden" type="hidden"/>
                            <input name="designerType" type="hidden" value="1" id="designerTypeHidden"/>
                            <input name="coverImg" id="filePath" type="hidden" />
                            <input name="status" type="hidden" value="1" id="statusId">
                            <div class="authentication_btn_box clearfix">
                                <a href="javascript:void(0);" class="btn fl" id="saveNext" style="width:200px; margin-left:110px">暂存</a>
                                <a href="javascript:void(0);" class="btn fl ml20" id="submit" style="width:200px;">提交审核</a>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/common/memberCenterFooter.jsp" %>
<script type="text/javascript" src="${resUrl}/js/util/cookieUtil.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-form/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-uploadify/script/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/common/common.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="${resUrl}/js/designer/applyCompany.js?v=1"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="${resUrl}/plugin/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${resUrl}/js/util/validate.js"></script>
</body>
</html>