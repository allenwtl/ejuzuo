<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="el" uri="ejuzuo.com/el-common" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>设计师详情</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/swiper-3.3.1.min.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/pages.css">

  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>
<%@include file="/common/menu.jsp"%>

<div class="content">
  <div class="main">
    <div class="">
      <!-- 设计师详情-->
      <div class="common_block mt20">
        <div class="designer_intro_box clearfix">
          <div class="left_picbox"><img src="http://r.ejuzuo.com${el:toJSONObject(data.coverImg).pic500500}" class="blockImg" alt=""></div>
          <div class="right_txtbox">
            <div class="right_btn"><a href="javascript:void(0);" id="follow" data="4" class="${data.follow ? 'disabled':''}">${data.follow ? '-已关注':'+关注'}</a></div>
            <div class="showmore_wrap">
              <div class="designer_msg_box">
                <p class="designer_namebox">${data.name}<i class="user_icon"></i></p>
                <ul class="designer_introlist">
                  <li>类型：
                    <c:if test="${data.designerType == 3}">
                      个人设计师
                    </c:if>
                    <c:if test="${data.designerType == 1}">
                      设计公司
                    </c:if>
                    <c:if test="${data.designerType == 2}">
                      装修公司
                    </c:if>
                  </li>
                  <li>城市：${data.area}</li>
                </ul>
              </div>
              <div class="designer_ex_list_wrap">
                <ul class="designer_ex_list">
                  <li>
                    <p class="title">[${data.designerType == 3 ? "个人简历":"公司简历"}]</p>
                    <p class="c_txt">${data.brief}</p>
                  </li>
                  <li>
                    <p class="title">[代表作品]</p>
                    <p class="c_txt">${data.classWorks}</p>
                  </li>
                  <li>
                    <p class="title">[设计理念]</p>
                    <p class="c_txt">${data.designIdea}</p>
                  </li>
                  <li>
                    <p class="title">[风格介绍]</p>
                    <p class="c_txt">${data.styleIntro}</p>
                  </li>
                  <li>
                    <p class="title">[项目经验]</p>
                    <p class="c_txt">${data.experience}</p>
                  </li>
                </ul>
              </div>
            </div>
            <div class="designer_data_box">
              <a href="javascript:;" class="more_dt js_more_dt">更多详情>></a>
              <span>作品数量：${data.workCount}</span><span id="fensi">粉丝：${data.follower}</span><span>预览：${data.viewCount}</span>
            </div>
          </div>
        </div><!--designer_intro_box END-->
      </div>
      <div class="common_block mt20">
        <div class="title_box">
          <span class="titletxt">案例展示</span>
        </div>
        <div class="greybg">
          <ul class="case_list deisgner_case clearfix" id="designerWork">

          </ul>
        </div>
      </div>

      <!-- 设计师详情 END-->
      <!-- 分页 -->
      <div class="pages mt10 mb20 clearfix">
        <ul id="pagtiona" class="pages_list fr" >

        </ul>
      </div><!-- 分页 -->
    </div>
  </div>
</div>

<input type="hidden" id="totalCount" value="${data.workCount}">
<input type="hidden" id="memberId" value="${data.memberId}">
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{? it.length == 0 }}
  {{??}}
    {{~it:value:index}}
    <li class="appendTo">
      <a href="/designerWork/detail/{{=value.id}}" class="case_block" target="_blank">
        <img src="http://r.ejuzuo.com{{=value.coverImg}}" alt="">
        <div class="jb_pos_box">
          <p class="txtintro">{{=value.name}}</p>
          <div class="data_box">
            <span class="com_box"><i class="com_icon"></i>{{=value.commentCount}}</span>
            <span class="view_box"><i class="view_icon"></i>{{=value.viewCount}}</span>
            <span class="fav_box"><i class="dfav_icon"></i>{{=value.like}}</span>
          </div>
        </div>
      </a>
    </li>
    {{~}}
  {{?}}
</script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/designer/designerDetail.js"></script>
<script type="text/javascript">
  var img = '<img src="/designer/viewCount/${data.id}?q='+Math.random()*1000+' " style="display: none;">';
  var $Img = $(img);
  $('body').append($Img);
</script>
</body>
</html>