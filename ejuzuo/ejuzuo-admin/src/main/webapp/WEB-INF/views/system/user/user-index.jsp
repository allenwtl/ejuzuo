<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="user" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>用户管理 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li class="active">&nbsp;用户管理</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<div class="panel panel-default">
			<form class="panel-body form-inline" autocomplete="off" style="margin-bottom:0;">
			
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" selected="selected">--状态--</option>
						<option value="1">正常的</option>
						<option value="2">禁用的</option>
						<option value="3">离职的</option>
					</select>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-name">姓名</label>
					<input type="text" class="form-control" id="filter-name" placeholder="姓名"/>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-account">账号</label>
					<input type="text" class="form-control" id="filter-account" placeholder="账号"/>
				</div>
				
				<button type="button" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				
				<shiro:hasPermission name="SYSTEM:USER:ADD">
				<a class="btn btn-primary btn-sm" href="/system/user/add" role="button">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增
				</a>
				</shiro:hasPermission>
				
			</form>
		</div>
		
	</div>
	
	<div class="col-sm-12 pagination-wrapper" id="page"></div>
	
</body>
<footer-scripts>
<script type="text/template-dot" id="dot-list">
	<thead>
		<tr>
			<th>#</th>
			<th>账号</th>
			<th>姓名</th>
			<th>昵称</th>
			<th>角色</th>
			<th>状态</th>
			<th>更新时间</th>
			<th>更新人员</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		{{? it.list.length < 1}}
		<tr><td align="center" colspan="10">暂无数据</td></tr>
		{{??}}
		{{~it.list :item:index}}
			<tr>
				<td>{{=index + 1}}</td>
				<td align="right">{{=item.account}}</td>
				<td align="left">{{=item.name}}</td>
				<td align="center">{{=item.nickname || '-'}}</td>
				<td align="center">{{=item.roleNames || '普通用户'}}</td>
				<td align="center">{{=it.status[item.status]}}</td>
				<td align="center">{{=item.updatedDate}}</td>
				<td align="center">{{=item.updatedUser}}</td>
				<td align="center">
					<div class="btn-group btn-group-xs" role="group" aria-label="optional">
						<button type="button" class="btn btn-default btn-xs jq-page-opt-detail" data-id="{{=item.id}}">
							<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>&nbsp;详情
						</button>
						<shiro:hasPermission name="SYSTEM:USER:EDIT">
						<a class="btn btn-primary" href="/system/user/{{=item.id}}/edit" role="button">
							<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;编辑
						</a>
						{{? item.status == 1}}
						<button type="button" class="btn btn-danger btn-xs jq-page-opt-diabled" data-id="{{=item.id}}" data-name="{{=item.name}}">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;禁用
						</button>
						{{?? item.status == 2}}
						<button type="button" class="btn btn-success btn-xs jq-page-opt-enabled" data-id="{{=item.id}}" data-name="{{=item.name}}">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;启用
						</button>
						{{?}}
						</shiro:hasPermission>
					</div>
				</td>
			</tr>
		{{~ }}
		{{?}}
	</tbody>
	</script>
	
	<script type="text/template-dot" id="dot-detail">
	<div class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">账号</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.account}}</p>
			</div>
			<label class="col-sm-2 control-label">状态</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.statusTpl[it.status]}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">姓名</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.name}}</p>
			</div>
			<label class="col-sm-2 control-label">昵称</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.nickname || '-'}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">拥有角色</label>
			<div class="col-sm-10">
				<p class="form-control-static">{{=it.roleNames || '普通用户'}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">创建人员</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.createdUser}}</p>
			</div>
			<label class="col-sm-2 control-label">创建时间</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.createdDate}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">更新人员</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.updatedUser}}</p>
			</div>
			<label class="col-sm-2 control-label">更新时间</label>
			<div class="col-sm-4">
				<p class="form-control-static">{{=it.updatedDate}}</p>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">备注</label>
			<div class="col-sm-10">
				<textarea class="form-control" rows="5" readonly>{{=it.remark || ''}}</textarea>
			</div>
		</div>
	</div>
	</script>
<script src="/resources/scripts/system/user/user-index.js"></script>
</footer-scripts>
</html>
