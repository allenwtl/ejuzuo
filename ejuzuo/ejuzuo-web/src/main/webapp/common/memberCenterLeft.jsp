<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="el" uri="ejuzuo.com/el-common"%>
<div class="left_box" id="partLeft">
  <div class="menu_box">
    <div class="user_msg_box">
      <div class="user_pic_box"><img src="${resUrl}${el:toJSONObject(requestScope.memberInCache.profileImg).pic7675}" alt=""></div>
      <div class="user_txt_box jb_pos_box">
        <p class="user_name">昵称：${requestScope.memberInCache.nickName}</p>
        <c:if test="${requestScope.memberInCache.vip}">
          <%-- <p>积分：${requestScope.memberInCache.balance}</p> --%>
          <p>等级：VIP</p>
        </c:if>
      </div>
    </div>
    <ul class="menu_list">
      <li class="${menu eq 'center' ? 'active':''}"><a href="/member/center">个人中心</a></li>
      <li class="${menu eq 'memberinfo' ? 'active':''}"><a href="/member/showInfo">用户信息</a></li>
      <li class="${menu eq 'designer' ? 'active':''}"><a href="/designer/setting">我是设计师</a></li>
      <li class="${menu eq 'designerwork' ? 'active':''}"><a href="/designerWork/index">作品管理</a></li>
      <li class="${menu eq 'vip' ? 'active':''}"><a href="/memberVipLog/index">VIP记录</a></li>
      <li class="${menu eq 'donate' ? 'active':''}"><a href="/donate/index">赞助巨作</a></li>
      <li class="${menu eq 'security' ? 'active':''}"><a href="/security/index/mobile">安全中心</a></li>
      <li class="${menu eq 'buyCar' ? 'active':''}"><a href="/buyCar/index">我的购买清单</a></li>
      <li class="${menu eq 'downFile' ? 'active':''}"><a href="/downloadInfo/index">我的下载</a></li>
      <li class="${menu eq 'favorite' ? 'active':''}"><a href="/favorite/list">我的收藏</a></li>
      <li class="${menu eq 'share' ? 'active':''}"><a href="/share/index">分享</a></li>
      <li class="${menu eq 'point' ? 'active':''}"><a href="/pointLog/index">积分记录</a></li>
      <li class="${menu eq 'info' ? 'active':''}"><a href="/info/index">系统消息</a></li>
      <li class="${menu eq 'privilege' ? 'active':''}"><a href="/privilege/index">用户特权</a></li>
    </ul>
  </div>
</div>
