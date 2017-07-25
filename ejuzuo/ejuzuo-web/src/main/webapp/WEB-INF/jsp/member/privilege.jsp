<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("resUrl","http://r.ejuzuo.com");%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>用户特权</title>
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
            <span class="txt">用户特权</span>
          </div>
          <div class="midcont_box">
            <div class="contheight_box whitebg">
              <div class="user_privilege">
                <table width="100%">
                  <thead>
                  <tr>
                    <th class="bdl0" width="25%">对比项</th>
                    <th width="25%">普通用户</th>
                    <th width="25%">高级用户</th>
                    <th width="25%">VIP用户</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td class="bdl0">
                      <div>
                        <p>每天下载次数</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>5次</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>10次</p>
                        <!-- p class="smalltxt">（分享可获5个积分）</p -->
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>20次</p>
                        <!--p class="smalltxt">（分享可获10个积分）</p-->
                      </div>
                    </td>
                  </tr>
                  <tr class="even">
                    <td class="bdl0">
                      <div>
                        <p>上传成功案例</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>不可以</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                        <p class="smalltxt">（上传可获5个积分）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                        <p class="smalltxt">（上传可获10个积分）</p>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="bdl0">
                      <div style="TEXT-DECORATION: line-through">
                        <p>积分使用云渲染</p>
                      </div>
                    </td>
                    <td>
                      <div style="TEXT-DECORATION: line-through">
                        <p>不可以</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p style="TEXT-DECORATION: line-through">可以</p>
                        <p class="smalltxt" style="TEXT-DECORATION: line-through">（每张效果图15个积分）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p style="TEXT-DECORATION: line-through">可以</p>
                        <p class="smalltxt" style="TEXT-DECORATION: line-through">（每张效果图10个积分）</p>
                      </div>
                    </td>
                  </tr>
                  <tr class="even">
                    <td class="bdl0">
                      <div style="TEXT-DECORATION: line-through">
                        <p>积分购买模型</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p style="TEXT-DECORATION: line-through">可以</p>
                        <p class="smalltxt" style="TEXT-DECORATION: line-through">（单件模型5个积分<br>组合模型10个积分 ）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p style="TEXT-DECORATION: line-through">可以</p>
                        <p class="smalltxt" style="TEXT-DECORATION: line-through">（单件模型3个积分<br>组合模型6个积分 ）</p>
                      </div>
                    </td>
                    <td>
                      <div style="TEXT-DECORATION: line-through">
                        <p>无限下载不需积分</p>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="bdl0">
                      <div>
                        <p>分享推广</p>
                        <p class="smalltxt">（微信 qq 微博 ）</p>
                        <p class="smalltxt" style="color:red">(点击用户中心的“分享”，<br/>分享巨作网，分享模型不计在内)</red></p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>不可以</p>
                        <p class="smalltxt">（完善用户资料可直接升级为<br>高级用户分享并获得积分。)</p>
                        <p class="smalltxt" style="color:red">(非同IP，分享后点击即为有效分享 ）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                        <p class="smalltxt">（每分享一次获得50个积分，<br>累计分享50次后自动升级为VIP<br>会员，可获得49元等级6个月<br>VIP会员使用权限。）</p>
                        <p class="smalltxt" style="color:red">(非同IP，分享后点击即为有效分享 ）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                        <p class="smalltxt">（每分享一次获得80个积分。</p>
                        <p class="smalltxt" style="color:red">(非同IP，分享后点击即为有效分享 ）</p>
                      </div>
                    </td>
                  </tr>
                  <tr class="even">
                    <td class="bdl0">
                      <div>
                        <p>注册送积分</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>50个积分</p>
                        <p class="smalltxt">（完善资料可自动升级为<br>高级会员再送50个积分）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>100个积分</p>
                        <!-- >p class="smalltxt">（注册后分享可再获得100<br>个积分）</p -->
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>赞助巨作49元</p>
                        <p class="smalltxt">（VIP特权时间6个月同时获得400积分<!-- br>注册成功后分享再送200积分 -->）</p>
                      </div>
                      <div style="padding-top:0">
                        <p>赞助巨作99元</p>
                        <p class="smalltxt">（VIP特权时间18个月同时获得1000<!-- br>积分注册成功后分享再送200积分-->）</p>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="bdl0">
                      <div>
                        <p>收藏作品</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>不可以</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                        <p class="smalltxt">（收藏数量200个）</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>无限收藏</p>
                      </div>
                    </td>
                  </tr>
                  <tr class="even">
                    <td class="bdl0">
                      <div>
                        <p>参与站内活动</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>不可以</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                      </div>
                    </td>
                    <td>
                      <div>
                        <p>可以</p>
                      </div>
                    </td>
                  </tr>
                  </tbody>
                </table>
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