<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="member" scope="request"/>
<c:set var="nav_second" value="member" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户信息 - 用户管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;用户管理</li>
		<li class="active">&nbsp;用户信息</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form id="page-toolbar" class="form-inline" role="form" autocomplete="off" style="line-height:34px;">
			<input type="hidden" value="${domainImage }" id="domainImage">
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-id">ID</label>
				<input type="number" class="form-control" id="filter-id" placeholder="ID"/>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-account">账号</label>
				<input type="text" class="form-control" id="filter-account" placeholder="账号"/>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-nickName">昵称</label>
				<input type="text" class="form-control" id="filter-nickName" placeholder="昵称"/>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-verified">认证</label>
				<select class="form-control" id="filter-verified">
					<option value="" selected="selected">--认证--</option>
					<option value="0">未认证</option>
					<option value="1">已认证</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-locked">锁定</label>
				<select class="form-control" id="filter-locked">
					<option value="" selected="selected">--锁定--</option>
					<option value="0">未锁定</option>
					<option value="1">已锁定</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-status">状态</label>
				<select class="form-control" id="filter-status">
					<option value="" selected="selected">--状态--</option>
					<option value="1">正常的</option>
					<option value="0">禁用的</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-activeStatus">激活状态</label>
				<select class="form-control" id="filter-activeStatus">
					<option value="" selected="selected">--激活状态--</option>
					<option value="1">已激活</option>
					<option value="0">待激活</option>
				</select>
			</div>
			<div class="form-group form-group-sm">
				<label class="sr-only" for="filter-beginDate">注册开始日期</label>
				<div class="input-group date" id="filter-beginDate-group">
	                <input type="text" class="form-control" size="10" id="filter-beginDate" placeholder="注册开始日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
				-
				<label class="sr-only" for="filter-endDate">注册结束日期</label>
				<div class="input-group date" id="filter-endDate-group">
	                <input type="text" class="form-control" size="10" id="filter-endDate" placeholder="注册结束日期"/>
	                <span class="input-group-addon">
	                	<span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
			</div>
			<shiro:hasPermission name="MEMBER:BASE:INFO">
			<button type="button" class="btn btn-sm btn-primary" id="filter-submit" data-loading-text="查找中...">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
			</button>
			</shiro:hasPermission>
		</form>
		
		<table id="page"></table>
		<p class="bg-warning text-center page-warning" id="page-tip">请设置条件进行查询</p>
		
	</div>
	<shiro:hasPermission name="MEMBER:BASE:INFO">
	<input type="hidden" id="hasPermOfInfo" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="MEMBER:BASE:LOCK">
	<input type="hidden" id="hasPermOfLock" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="MEMBER:BASE:UNLOCK">
	<input type="hidden" id="hasPermOfUnlock" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="MEMBER:BASE:ENABLE">
	<input type="hidden" id="hasPermOfEnable" value="true"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="MEMBER:BASE:DISABLE">
	<input type="hidden" id="hasPermOfDisble" value="true"/>
	</shiro:hasPermission>
	
	<!-- Modal -->
	<div class="modal fade" id="model-detail" tabindex="-1" role="dialog"
		aria-labelledby="model-detail-Label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="model-detail-Label">详情</h4>
				</div>
				<div class="modal-body" id="model-detail-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
				</div>
			</div>
		</div>
	</div>
	
</body>
<footer-scripts>
	
	<script type="text/template-dot" id="dot-detail">
	<div class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">用户ID</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.id}}</p>
			</div>
			<label class="col-sm-2 control-label">用户账号</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.account}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">用户昵称</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.nickName || '-'}}</p>
			</div>
			<label class="col-sm-2 control-label">手机</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.mobile || '-'}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">封面图片</label>
			<div class="col-sm-8">
				<div class="thumbnail">
					<img src="{{=it.profileImg || 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7'}}" alt="封面图片">
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">邮箱</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.email || '-'}}</p>
			</div>
			<label class="col-sm-2 control-label">注册时间</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.registerTime || '-'}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">激活状态</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.activeStatus}}</p>
			</div>
			<label class="col-sm-2 control-label">状态</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.status}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">锁定状态</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.locked}}</p>
			</div>
			<label class="col-sm-2 control-label">是否认证</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.verified}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">注册时间</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.createTime}}</p>
			</div>
			<label class="col-sm-2 control-label">更新时间</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.updateTime}}</p>
			</div>
		</div>
	</div>
	</script>
<script type="text/javascript" src="/resources/scripts/member/member-index.js"></script>
</footer-scripts>
</html>