<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="system" scope="request"/>
<c:set var="nav_second" value="role" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>角色管理 - 系统管理</title>
</head>
<body>
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<a href="/">Dashboard</a></li>
		<li class="active">&nbsp;系统管理</li>
		<li class="active">&nbsp;角色管理</li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		
		<div class="panel panel-default">
			<form class="panel-body form-inline" autocomplete="off" style="margin-bottom:0;">
			
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-roleName">角色名称</label>
					<input type="text" class="form-control" id="filter-roleName" placeholder="角色名称"/>
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-status">状态</label>
					<select class="form-control" id="filter-status">
						<option value="" selected="selected">--状态--</option>
						<option value="1">可用的</option>
						<option value="2">禁用的</option>
					</select>
				</div>
				
				<button type="button" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
				
				<shiro:hasPermission name="SYSTEM:ROLE:ADD">
				<button type="button" class="btn btn-primary btn-sm" id="filter-add">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增
				</button>
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
			<th>角色名称</th>
			<th>角色代码</th>
			<th>状态</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		{{? it.list.length < 1}}
		<tr><td align="center" colspan="6">暂无数据</td></tr>
		{{??}}
		{{~it.list :item:index}}
			<tr>
				<td>{{=index + 1}}</td>
				<td align="left">{{=item.roleName}}</td>
				<td align="left">{{=item.roleCode}}</td>
				<td align="center">{{=it.status[item.status]}}</td>
				<td align="left">{{=item.remark || '-'}}</td>
				<td align="center">
					<div class="btn-group btn-group-xs" role="group" aria-label="optional">
						<shiro:hasPermission name="SYSTEM:ROLE:EDIT">
						<button type="button" class="btn btn-primary btn-xs jq-page-opt-edit" data-id="{{=item.id}}" data-name="{{=item.roleName}}">
							<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;编辑
						</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="SYSTEM:ROLE:EDIT">
						<a role="button" class="btn btn-info btn-xs" href="/system/role/{{=item.id}}/auth">
							<span class="glyphicon glyphicon-check" aria-hidden="true"></span>&nbsp;授权
						</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="SYSTEM:ROLE:EDIT">
						{{? item.status == 1}}
						<button type="button" class="btn btn-danger btn-xs jq-page-opt-diabled" data-id="{{=item.id}}" data-name="{{=item.roleName}}">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;禁用
						</button>
						{{?? item.status == 2}}
						<button type="button" class="btn btn-success btn-xs jq-page-opt-enabled" data-id="{{=item.id}}" data-name="{{=item.roleName}}">
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

	<script type="text/template-dot" id="dot-add">
	<form class="form-horizontal" id="form-add" method="post" novalidate="novalidate" autocomplete="off">
		<div class="form-group">
			<label class="col-sm-2 control-label" for="form-add-roleName">角色名称</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="form-add-roleName" name="roleName" placeholder="角色名称" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="form-add-roleCode">角色代码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="form-add-roleCode" name="roleCode" 
					placeholder="角色代码 e.g. ADMIN:SUPER" pattern="[A-Za-z0-9:]+" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">状态</label>
			<div class="col-sm-6">
				<div class="radio" style="padding-top:3px;">
	  				<label class="radio-inline" style="padding-top:3px;">
	    				<input type="radio" name="status" id="form-add-status-1" value="1" checked> 可用的
	  				</label>
					<label class="radio-inline" style="padding-top:3px;">
	  					<input type="radio" name="status" id="form-add-status-2" value="2"> 禁用的
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="form-add-remark">备注</label>
			<div class="col-sm-8">
				<textarea class="form-control" rows="3" name="remark" id="form-add-remark"></textarea>
			</div>
		</div>
	</form>
	</script>

	<script type="text/template-dot" id="dot-edit">
	<form class="form-horizontal" id="form-edit" method="post" novalidate="novalidate" autocomplete="off">
		<div class="form-group">
			<label class="col-sm-2 control-label" for="form-edit-roleName">角色名称</label>
			<div class="col-sm-6">
				<input type="hidden" id="form-edit-defaultRoleName" value="{{=it.roleName}}"/>
				<input type="text" class="form-control" id="form-edit-roleName" name="roleName" value="{{=it.roleName}}" 
					placeholder="角色名称 e.g. {{=it.roleName }}" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="form-add-roleCode">角色代码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="form-edit-roleCode" name="roleCode" value="{{=it.roleCode}}" 
					placeholder="角色代码  e.g. {{=it.roleCode }}" pattern="[A-Za-z0-9:]+" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">状态</label>
			<div class="col-sm-6">
				<div class="radio" style="padding-top:3px;">
	  				<label class="radio-inline" style="padding-top:3px;">
	    				<input type="radio" name="status" id="form-edit-status-1" value="1" {{? it.status == 1}}checked{{?}}> 可用的
	  				</label>
					<label class="radio-inline" style="padding-top:3px;">
	  					<input type="radio" name="status" id="form-edit-status-2" value="2" {{? it.status == 2}}checked{{?}}> 禁用的
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="form-add-remark">备注</label>
			<div class="col-sm-8">
				<textarea class="form-control" rows="3" name="remark" id="form-edit-remark">{{=it.remark || ''}}</textarea>
			</div>
		</div>
	</form>
	</script>
<script type="text/javascript" src="/resources/scripts/system/role/role-index.js"></script>
</footer-scripts>
</html>
