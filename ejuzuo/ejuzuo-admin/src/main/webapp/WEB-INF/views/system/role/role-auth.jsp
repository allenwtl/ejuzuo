<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="role" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>授权 - 角色管理 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li><a href="/system/role">&nbsp;角色管理</a></li>
		<li class="active">&nbsp;授权</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12 table-responsive">
		
		<input type="hidden" id="jq-role-id" value="${role.id }">
		<h2><c:out value="${role.roleName }"/></h2>
		<hr/>
		
		<table class="table table-bordered table-responsed" id="jq-role-auth-table">
			<tbody>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="SYSTEM:MENU"> 系统管理</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="SYSTEM:ROLE:INFO"> 角色管理</label>
							<label><input type="checkbox" value="SYSTEM:ROLE:ADD"> 角色新增</label>
							<label><input type="checkbox" value="SYSTEM:ROLE:EDIT"> 角色编辑</label>
							<label><input type="checkbox" value="SYSTEM:ROLE:AUTH"> 角色授权</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="SYSTEM:USER:INFO"> 管理员管理</label>
							<label><input type="checkbox" value="SYSTEM:USER:ADD"> 管理员新增</label>
							<label><input type="checkbox" value="SYSTEM:USER:EDIT"> 管理员编辑</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="SYSTEM:USEROPERLOG"> 管理员操作日志</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="MEMBER:MENU"> 用户管理</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="MEMBER:BASE:INFO">用户信息</label>&nbsp;&nbsp;
							<label><input type="checkbox" value="MEMBER:BASE:LOCK">用户锁定</label>&nbsp;&nbsp;
							<label><input type="checkbox" value="MEMBER:BASE:UNLOCK">用户解锁</label>&nbsp;&nbsp;
							<label><input type="checkbox" value="MEMBER:BASE:ENABLE">用户启用</label>&nbsp;&nbsp;
							<label><input type="checkbox" value="MEMBER:BASE:DISABLE">用户禁用</label>&nbsp;&nbsp;
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="MEMBER:THIRD:INFO"> 第三方用户信息</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="MEMBER:POINT:INFO">用户积分</label>&nbsp;&nbsp;
							<label><input type="checkbox" value="MEMBER:POINT:UPDATE">用户积分调整</label>&nbsp;&nbsp;
							<label><input type="checkbox" value="MEMBER:POINT:INFO:LOG">用户积分日志</label>&nbsp;&nbsp;
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="MEMBER:VIP:INFO"> 用户VIP</label>
							<label><input type="checkbox" value="MEMBER:VIP:UPDATE"> 用户VIP调整</label>
							<label><input type="checkbox" value="MEMBER:VIP:INFO:LOG"> 用户VIP日志</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="MEMBER:OPER:LOG:INFO"> 用户操作日志</label>
							<label><input type="checkbox" value="MEMBER:PROMORECORD:LOG:INFO"> 用户推广记录</label>
							<label><input type="checkbox" value="MEMBER:CARE:LOG:INFO"> 用户关注</label>
							<label><input type="checkbox" value="MEMBER:SPONSOR:LOG:INFO"> 用户赞助</label>
							<label><input type="checkbox" value="MEMBER:FAVORITE:LOG:INFO"> 用户收藏</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="DESIGNER:MENU"> 设计师</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="DESIGNER:INFO"> 设计师</label>
							<label><input type="checkbox" value="DESIGNER:APPROVE"> 设计师审核</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="DESIGNER:WORK:INFO"> 设计师作品</label>
							<label><input type="checkbox" value="DESIGNER:WORK:DISABLE"> 设计师作品禁用</label>
							<label><input type="checkbox" value="DESIGNER:WORK:ENABLE"> 设计师作品启用</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="NEWS:MENU"> 新闻资讯</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="NEWS:INFO"> 新闻资讯详情</label>
							<label><input type="checkbox" value="NEWS:FABU"> 新闻资讯发布</label>
							<label><input type="checkbox" value="NEWS:UPDATE"> 新闻资讯修改</label>
							<label><input type="checkbox" value="NEWS:DISABLE"> 新闻资讯禁用</label>
							<label><input type="checkbox" value="NEWS:ENABLE"> 新闻资讯启用</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="ACTIVITY:MENU"> 活动</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="ACTIVITY:INFO"> 活动详情</label>
							<label><input type="checkbox" value="ACTIVITY:ADD"> 活动新增</label>
							<label><input type="checkbox" value="ACTIVITY:FABU"> 活动发布</label>
							<label><input type="checkbox" value="ACTIVITY:UPDATE"> 活动修改</label>
							<label><input type="checkbox" value="ACTIVITY:DISABLE"> 活动禁用</label>
							<label><input type="checkbox" value="ACTIVITY:ENABLE"> 活动启用</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="ACTIVITY:ENROLL:INFO"> 活动报名</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="BRAND:MENU"> 合作品牌</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="BRAND:ADD"> 合作品牌新增</label>
							<label><input type="checkbox" value="BRAND:UPDATE"> 合作品牌修改</label>
							<label><input type="checkbox" value="BRAND:ENABLE"> 合作品牌启用</label>
							<label><input type="checkbox" value="BRAND:DISABLE"> 合作品牌禁用</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="DIGITAL:MENU"> 数字家居</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="DIGITAL:INFO"> 数字家居详情</label>
							<label><input type="checkbox" value="DIGITAL:SHANGJIA"> 数字家居上架</label>
							<label><input type="checkbox" value="DIGITAL:XIAJIA"> 数字家居下架</label>
							<label><input type="checkbox" value="DIGITAL:ADD"> 数字家居新增</label>
							<label><input type="checkbox" value="DIGITAL:UPDATE"> 数字家居更新</label>
							<label><input type="checkbox" value="DIGITAL:ENABLE"> 数字家居启用</label>
							<label><input type="checkbox" value="DIGITAL:DISABLE"> 数字家居禁用</label>
							<label><input type="checkbox" value="DIGITAL:DOWNLOAD"> 数字家居下载</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="FILE:MENU"> 文件管理</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="FILE:INFO"> 详情</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="SMSRECORD:MENU"> 短信验证码</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="CHECKCODE:INFO"> 验证码管理</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="SMSRECORD:INFO"> 短信记录</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="ADSPACE:MENU"> 广告位</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="ADSPACE:IFNO"> 广告位详情</label>
							<label><input type="checkbox" value="ADSPACE:ADD"> 广告位新增</label>
							<label><input type="checkbox" value="ADSPACE:UPDATE"> 广告位更新</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="CODEVALUE:MENU"> 码表</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="CODEVALUE:ADD"> 码表新增</label>
							<label><input type="checkbox" value="CODEVALUE:UPDATE"> 码表修改</label>
							<label><input type="checkbox" value="CODEVALUE:ENABLE"> 码表启用</label>
							<label><input type="checkbox" value="CODEVALUE:DISABLE"> 码表禁用</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="COMMENT:MENU"> 评论管理</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="COMMENT:MASKED"> 评论屏蔽</label>
							<label><input type="checkbox" value="COMMENT:UNMASKED"> 评论解屏</label>
							<label><input type="checkbox" value="COMMENT:DISABLE"> 评论禁用</label>
							<label><input type="checkbox" value="COMMENT:ENABLE"> 评论启用</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="MSG:RECORD:MENU"> 系统消息</label>
					</th>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" value="MSG:RECORD:ADD"> 系统消息新增</label>
							<label><input type="checkbox" value="MSG:RECORD:UPDATE"> 系统消息修改</label>
							<label><input type="checkbox" value="MSG:RECORD:ENABLE"> 系统消息启用</label>
							<label><input type="checkbox" value="MSG:RECORD:DISABLE"> 系统消息禁用</label>
						</div>
					</td>
				</tr>
				<tr>
					<th width="150px">
						<label><input type="checkbox" value="DOWNLOAD:MENU"> 下载管理</label>
					</th>
					<td>
						<div class="checkbox">
							<!-- <label><input type="checkbox" value="DESIGNER:INFO"> 设计师</label> -->
						</div>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<th></th>
					<td>
						<input type="button" class="btn btn-primary" id="jq-role-auth-submit" value="更新" data-loading-text="更新中..."/>
						<a href="/system/role" class="btn btn-default">返回</a>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
</body>
<footer-scripts>
<script type="text/javascript" src="/resources/scripts/system/role/role-auth.js"></script>
</footer-scripts>
</html>
