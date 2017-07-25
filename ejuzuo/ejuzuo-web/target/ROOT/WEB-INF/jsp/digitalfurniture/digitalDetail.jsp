<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="el" uri="ejuzuo.com/el-common" %>
<% request.setAttribute("resUrl", "http://r.ejuzuo.com"); %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>${title}</title>
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/global.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/common.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/pages.css">
  <link rel="stylesheet" href="http://r.ejuzuo.com/styles/swiper-3.3.1.min.css">
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="http://r.ejuzuo.com/plugin/doT/1.0.1/doT.min.js"></script>
  <style type="text/css">
    .swiper-slide-active  {
      padding: 0;
      border: 1px solid #F00;
    }
  </style>
</head>
<body>
<%@include file="/common/memberCenterHeader.jsp"%>
<%@include file="/common/menu.jsp"%>

<div class="content">
  <div class="main">
    <div>
      <div class="common_block mt20">
        <div class="pl10 pt10">
          <div class="produce_detail_wrap clearfix">
            <div class="fl">
              <div class="produce_detail">
                <div class="produce_pic_wrap">
                  <div class="bigpicbox"><img src="" class="blockImg" alt="" id="bigImg"></div>
                  <div class="simallpicbox pt10 swiper-container">
                    <ul  class="simallpic_list clearfix swiper-wrapper" style="width: 800px"  id="imgselect"  >
                    <%--<ul class="simallpic_list clearfix" id="imgselect"  >--%>
                      <c:forEach items="${el:getJSONArray(data.pictures)}" var="pic">
                        <li  class="swiper-slide">
                          <img src="http://r.ejuzuo.com${pic.img205100}" class="blockImg" alt="" title="${pic.intro}" data="http://r.ejuzuo.com${pic.img850478}">
                          <div class="btmtxt_box">
                            <span class="left_txt">${pic.intro}</span>
                          </div>
                        </li>
                      </c:forEach>
                    </ul>
                    <%--<a class="simallpic_prev" href="javascript:;">prev</a>--%>
                    <%--<a class="simallpic_next" href="javascript:;">next</a>--%>
                    <div class="swiper-button-prev"></div>
                    <div class="swiper-button-next"></div>
                    <div class="swiper-scrollbar"></div>
                  </div>
                </div>
                <div class="produce_intro_table mt10">
                  <table width="100%">
                    <tr class="greybg">
                      <td width="40%"><div class="pl20">单件名称</div></td>
                      <td width="30%"><div class="pl20">产品尺寸</div></td>
                      <td width="15%"><div class="pl20">材质</div></td>
                      <td width="15%"><div class="pl20">价格（元）</div></td>
                    </tr> 
                    <c:forEach items="${el:getJSONArray(data.specification)}" var="item">
	                    <tr>
	                        <td><div class="pl20">
	                        <c:choose>
	                        <c:when test="${not empty item.relateId && item.relateId != ''}">
	                        	<a target="_blank" href="/digital/digitalDetail/${item.relateId }">${item.name}</a>
	                        </c:when>
	                        <c:otherwise>
	                        	${item.name}
	                        </c:otherwise>
	                        </c:choose>
	                        </div></td>
	                        <td><div class="pl20">${item.size}</div></td>
	                        <td><div class="pl20">${item.material}</div></td>
	                        <td><div class="pl20">${item.price}</div></td>
                        </tr>
                      </c:forEach>
                    <%--<tr class="greybg">
                      <td><div class="pl20"><strong>合计</strong></div></td>
                      <td><div class="pl20"></div></td>
                    </tr>--%>
                  </table>
                </div>
                <div class="produce_mg_box pt20">
                 <c:forEach items="${el:getJSONArray(data.corporation)}" var="item">
                 <h3 class="title">
                 	<c:choose>
                      <c:when test="${not empty item.id && item.id != ''}">
                      	<a target="_blank" href="/newsInfo/detail/${item.id }">[${item.name}] ${item.val}</a>
                      </c:when>
                      <c:otherwise>
                      	[${item.name}] ${item.val}	
                      </c:otherwise>
                      </c:choose>
				</h3>
                 </c:forEach>
                 <%-- <c:if test="${not empty el:getJSONValue(data.corporation,'公司/品牌名称')}">
                  <h3 class="title">[公司/品牌名称] ${el:getJSONValue(data.corporation, "公司/品牌名称")}</h3>
                  </c:if>  
                 <c:if test="${not empty el:getJSONValue(data.corporation, '销售电话')}">
                  <p><strong>[销售电话] </strong>${el:getJSONValue(data.corporation, '销售电话')}</p>
                 </c:if>                     
                 <c:if test="${not empty el:getJSONValue(data.corporation,'公司网址')}">
                  <p><strong>[公司网址] </strong>${el:getJSONValue(data.corporation, '公司网址')}</p>
                 </c:if>               
                 <c:if test="${not empty el:getJSONValue(data.corporation, '公司地址')}">
                  <p><strong>[公司地址] </strong>${el:getJSONValue(data.corporation, '公司地址')}</p>
                 </c:if>                  
                 <c:if test="${not empty el:getJSONValue(data.corporation, '公司简介')}">
                  <p><strong>[公司简介] </strong>${el:getJSONValue(data.corporation, '公司简介')}</p>
                 </c:if> --%>                  
                </div>
              </div>
            </div>
            <div class="fr">
              <div class="related_recom">
                <div class="title_box">
                  <a href="/digital/digitalList/0/0/0/0/${data.type}" class="more" target="_blank">更多</a>
                  <span class="titletxt">其它品牌推荐</span>
                </div>
                <div class="pr10 pt10">
                  <ul class="produce_list produce_280 clearfix">
                    <c:forEach items="${otherBrand}" var="item">
                      <li>
                        <a href="/digital/digitalDetail/${item.id}">
                          <img src="http://r.ejuzuo.com${item.thumbnail}" alt="">
                          <div class="jb_pos_box">
                            <p class="nametxt">${item.name}</p>
                            <p class="intro_txt">${item.styleVo}</p>
                          </div>
                        </a>
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </div>
            </div>
          </div><!-- produce_detail_wrap END-->
          <div class="produce_download mt10 clearfix">
            <div class="down_data">
              <div class="data_ablock"><span>文件大小：${data.sizeMB}</span><span>所需积分：${data.pointPrice}分</span></div>
              <div class="data_ablock"><span>下载次数：${data.downCount}次</span><span>浏览人次：${data.viewCount}次</span></div>
            </div>
            <a href="javascript:void(0)" class="bigbtn" id="addBuyCar">加入购买清单</a>
            <a href="javascript:void(0)" class="bigbtn ${!empty data.fileId  ?'':'disabled'}" id="downloadNow" data="${data.id}">立即下载</a>
          </div>
          <div class="getbyit_box clearfix">
            <div class="collection_box"><a href="javascript:;" class="collect_icon js_collect_icon ${data.favorite ? 'on':''}" id="collect" data-type="0" data-id="${data.id}"></a>收藏数：<span id="like">${data.favoriteCount}</span></div>
            <div class="share_box fl">
              <div class="bshare-custom">
                <div class="bsPromo bsPromo2"></div>
                <a title="分享到微信" class="bshare-weixin" href="javascript:void(0);"></a>
                <a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
                <a title="分享到QQ空间" class="bshare-qzone" href="javascript:void(0);"></a>
                <a title="分享到QQ好友" class="bshare-qqim" href="javascript:void(0);"></a>
                <a title="分享到腾讯微博" class="bshare-qqmb"></a>
                <a title="分享到网易微博" class="bshare-neteasemb"></a>
                <a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="common_block mt20">
        <div class="title_box">
          <span class="titletxt">配套商品</span>
        </div>
        <div class="pl10 pr10 pt20">
          <ul class="produce_list produce_280 clearfix">
            <c:forEach items="${matchGood}" var="item">
              <li>
                <a href="/digital/digitalDetail/${item.id}"><img src="http://r.ejuzuo.com${item.thumbnail}" alt=""></a>
                <div class="produce_introbtm">
                  <p class="produce_u_name ellipTxt">${item.name}</p>
                  <p class="produce_name ellipTxt">${item.styleVo}</p>
                </div>
              </li>
            </c:forEach>
          </ul>
        </div>
      </div>
      <div class="common_block mt20">
        <div class="title_box">
          <span class="titletxt">配套饰品</span>
        </div>
        <div class="pl10 pr10 pt20">
          <ul class="produce_list produce_280 clearfix">
            <c:forEach items="${matchAccessory}" var="item">
              <li>
                <a href="/digital/digitalDetail/${item.id}"><img src="http://r.ejuzuo.com${item.thumbnail}" alt=""></a>
                <div class="produce_introbtm">
                  <p class="produce_u_name ellipTxt">${item.name}</p>
                  <p class="produce_name ellipTxt">${item.styleVo}</p>
                </div>
              </li>
            </c:forEach>
          </ul>
        </div>
      </div>
      <div class="common_block mt20">
        <div class="title_box">
          <a href="/digital/digitalList/0/0/0/${data.brand}/${data.type}" class="more" target="_blank">查看全部</a>
          <span class="titletxt">同一品牌其它推荐</span>
        </div>
        <div class="pl10 pr10 pt20">
          <ul class="produce_list produce_280 clearfix">
            <c:forEach items="${sameBrand}" var="item">
              <li>
                <a href="/digital/digitalDetail/${item.id}"><img src="http://r.ejuzuo.com${item.thumbnail}" alt=""></a>
                <div class="produce_introbtm">
                  <p class="produce_u_name ellipTxt">${item.name}</p>
                  <p class="produce_name ellipTxt">${item.styleVo}</p>
                </div>
              </li>
            </c:forEach>
          </ul>
        </div>
      </div>

      <!-- 评论 -->
      <div class="common_block mt20">
        <div class="pl20 pr20 pt20">
          <div class="comment">
            <c:if test="${requestScope.memberInCache != null}">
            <p class="user_txt">账号：<a href="/member/center" class="nick_txt">${requestScope.memberInCache.nickName}</a>
              <a href="javascript:void(0);" id="logOut_comment" class="exit_txt">退出</a>
            </p>
            </c:if>
            <div class="comment_input_box">
              <c:if test="${requestScope.memberInCache != null}">
                <div class="user_imgbox">
                  <c:choose>
                    <c:when test="${requestScope.memberInCache.designer}">
                      <i class="user_icon"></i>
                      <a href="/designer/detail/${requestScope.memberInCache.designerId}"><img src="http://r.ejuzuo.com${el:toJSONObject(requestScope.memberInCache.profileImg).pic4343}" alt=""></a>
                    </c:when>
                    <c:otherwise>
                      <a href="javascript:void(0)">
                        <img src="http://r.ejuzuo.com${el:toJSONObject(requestScope.memberInCache.profileImg).pic4343}" alt="">
                      </a>
                    </c:otherwise>
                  </c:choose>
                </div>
              </c:if>
              <div class="right_box">
                <div class="text_inbox">
                  <a href="javascript:void(0)" class="comment_btn" id="wantComment">我要评论</a>
                  <textarea name="" id="commentContent"></textarea>
                </div>
                <%--<div class="moji_box"><a href="#" class="moji_btn"></a></div>--%>
              </div>
            </div>
            <div class="comment_titletxt" id="commentCount">收到全部评论${totalCount}条</div>
            <ul class="comment_info_list" id="commentInfo">

            </ul>
          </div>
        </div>
      </div>
      <!-- 评论END-->
    </div>
    <!-- 分页 -->
    <div class="pages mt10 mb20 clearfix">
      <ul id="pagtiona" class="pages_list fr" >

      </ul>
    </div><!-- 分页 -->
  </div>
</div>

<input type="hidden" id="objectId" value="${data.id}"/>
<input type="hidden" id="totalCount" value="${totalCount}"/>
<%@include file="/common/memberCenterFooter.jsp"%>
<script type="text/template" id="dotTemplate">
  {{? it.length == 0}}
  {{??}}
    {{~it:value:index}}
      <li class="appendTo">
        <div class="user_pic_box">
          <i class="{{? value.designer }}user_icon{{?}} user_icon1"></i>
            <img src="http://r.ejuzuo.com{{=value.profileImg}}" alt="">
        </div>
        <div class="user_c_txt">
          <p class="txtinfo"><span class="times">{{=value.dateBefore}}</span>{{=value.nickName}}</p>
          <p class="cont_txt ellipTxt">
            {{? value.masked == 1 }}
              [内容被屏蔽了]
            {{??}}
            {{= value.content}}
            {{?}}
          </p>
        </div>
      </li>
    {{~}}
  {{?}}
</script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-paging/jqPaginator.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/jquery-multidownload/jquery-multidownload.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/plugin/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/common/common.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/util/ejuozuo.js"></script>
<script type="text/javascript" src="http://r.ejuzuo.com/js/digitalfurniture/digitalDetail.js"></script>

<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
<script type="text/javascript">
  var img = '<img src="/digital/viewCount/${data.id}?q='+Math.random()*1000+' " style="display: none;">';
  var $Img = $(img);
  $('body').append($Img);

  $('#logOut_comment').click(function(){
    var url = "/login/asyncOut";
    $.getJSON( url,function(){
      cookieUtil.delCookie("uuid");
      window.location.reload();
    });
  });


  window.onload=function(){
      var mySwiper = new Swiper ('.swiper-container', {
          //direction: 'vertical',
          loop: false,

          // 如果需要分页器
          //pagination: '.swiper-pagination',

          // 如果需要前进后退按钮
          nextButton: '.swiper-button-next',
          prevButton: '.swiper-button-prev',
          width: 205,

          // 如果需要滚动条
          scrollbar: '.swiper-scrollbar',
          onSlidePrevEnd: function(swiper){
              var preImg = $("ul.swiper-wrapper li.swiper-slide-active img").attr('data');
              $("#bigImg").attr("src", preImg);
          },
          onSlideNextEnd: function(swiper){
              var preImg = $("ul.swiper-wrapper li.swiper-slide-active img").attr('data');
              $("#bigImg").attr("src", preImg);
          }
      });
  };

</script>
</body>
</html>
