<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="appEnv" scope="application"><spring:eval expression="@applicationProperties.getProperty('appEnv')" /></c:set>
<c:set var="appResHost" scope="application"><c:out value="${appEnv eq 'product' ? '//r.ejuzuo.com' : '//r.ejuzuo.com' }"/></c:set>

<!DOCTYPE html>
<!--[if lt IE 7]>		<html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>			<html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>			<html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->	<html lang="en" class="no-js"> <!--<![endif]-->
<head>
<meta charset="utf-8">
<!-- 设置360浏览器默认使用chrome内核渲染 -->
<meta name="renderer" content="webkit">
<!-- 设置IE浏览器默认使用最高IE版本渲染 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><sitemesh:write property='title'/> - 巨作 后台</title>

<link rel="preconnect" href="http://cdn.bootcss.com">
<link rel="preconnect" href="${appResHost }">

<link rel="icon" type="image/x-icon" href="/resources/favicon.ico">
<link rel="shortcut icon" href="/resources/favicon.ico">

<link href="/resources/vendor/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link href="${appResHost }/plugins/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link href="${appResHost }/plugins/pace/1.0.2/themes/blue/pace-theme-minimal.css" rel="stylesheet">
<link href="${appResHost }/plugins/pnotify/3.0.0/pnotify.css" rel="stylesheet">
<link href="${appResHost }/plugins/pnotify/3.0.0/pnotify.buttons.css" rel="stylesheet">
<link href="${appResHost }/plugins/select2/4.0.1/css/select2.min.css" rel="stylesheet">
<link href="${appResHost }/plugins/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="${appResHost }/plugins/bootstrap3-dialog/1.34.9/css/bootstrap-dialog.min.css" rel="stylesheet">

<link href="/resources/styles/base.css" rel="stylesheet">

<sitemesh:write property='head'/>

</head>
<body class="sidebar-show">
	<header class="navbar navbar-default navbar-fixed-top layout-header" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                
                <a class="navbar-brand cms-navbar-brand" href="/">
                    <span class="cms-navbar-brand-div">
                        <!-- <img height="30" src="http://r.aicaicdn.com/images/public/logo/acLogo150_40.png" alt="Brand"/>
                        <span class="cms-navbar-brand-divider"></span> -->
                        巨作<span class="hidden-xs hidden-sm"> 后台管理系统</span>
                    </span>
                </a>
                
            </div>
            <div id="navbar" class="navbar-collapse collapse">
            
            	<ul class="nav navbar-nav navbar-left">
                	<li id="nav-sidebar-toggle">
                		<a href="#">
                			<i class="fa fa-angle-double-left" id="nav-sidebar-toggle-icon"></i>
                		</a>
                	</li>
                </ul>
                
                <ul class="nav navbar-nav navbar-right">
                   	<li>
                       <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                           <i class="glyphicon glyphicon-user"></i>&nbsp;
                           <c:choose>
                            <c:when test="${not empty SESSION_USER}">
								<c:out value="${SESSION_USER.name}"/>
							</c:when>
							<c:otherwise>
								UNKNOWN
							</c:otherwise>
						</c:choose>&nbsp;
                           <span class="caret"></span>
                       </a>
                       <ul class="dropdown-menu">
                           <li><a href="/user-profile"><i class="glyphicon glyphicon-list-alt"></i> 我的信息</a></li>
                           <li class="divider"></li>
                           <li><a href="/logout"><i class="glyphicon glyphicon-log-out"></i> 退出</a></li>
                       </ul>
					</li>
				</ul>
			</div><!--/.navbar-collapse -->
		</div>
	</header>
	
	<nav class="nav nav-sidebar-wrapper" role="navigation">
		<ul class="nav-sidebar" id="js-nav-sidebar">
                
			<li class="nav-sidebar-toolbar">
			</li>

			<li class="nav-sidebar-dashboard ${nav_first eq 'dashboard' ? 'active' : '' }">
				<a href="/"><i class="nav-sidebar-icon fa fa-fw fa-dashboard"></i>Dashboard</a>
			</li>
			
			<shiro:hasPermission name="SYSTEM:MENU">
			<li class="nav-sidebar-group">
				<a class="${nav_first eq 'system' ? 'active' : 'collapsed' }" 
					data-toggle="collapse" data-parent="#js-nav-sidebar" href="#nav-sidebar-0">
					<i class="nav-sidebar-icon fa fa-fw fa-cogs"></i>系统管理
					<i class="glyphicon arrow"></i>
				</a>
				<ul class="collapse ${nav_first eq 'system' ? 'in' : '' }" 
					aria-expanded="${nav_first eq 'system' ? 'true' : 'false' }" id="nav-sidebar-0">
					<shiro:hasPermission name="SYSTEM:ROLE:INFO">
					<li class="${nav_second eq 'role' ? 'active' : '' }">
						<a href="/system/role"><i class="nav-sidebar-icon fa fa-fw fa-male"></i>角色管理</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SYSTEM:USER:INFO">
					<li class="${nav_second eq 'user' ? 'active' : '' }">
						<a href="/system/user"><i class="nav-sidebar-icon fa fa-fw fa-users"></i>管理员管理</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SYSTEM:USEROPERLOG">
					<li class="${nav_second eq 'user-oper-log' ? 'active' : '' }">
						<a href="/system/user-oper-log"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>管理员操作日志</a>
					</li>
					</shiro:hasPermission>
				</ul>
			</li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="MEMBER:MENU">
			<li class="nav-sidebar-group">
				<a class="${nav_first eq 'member' ? 'active' : 'collapsed' }" 
					data-toggle="collapse" data-parent="#js-nav-sidebar" href="#nav-sidebar-1">
					<i class="nav-sidebar-icon fa fa-fw fa-users"></i>用户管理
					<i class="glyphicon arrow"></i>
				</a>
				<ul class="collapse ${nav_first eq 'member' ? 'in' : '' }" 
					aria-expanded="${nav_first eq 'member' ? 'true' : 'false' }" id="nav-sidebar-1">
					<shiro:hasPermission name="MEMBER:BASE:INFO">
					<li class="${nav_second eq 'member' ? 'active' : '' }">
						<a href="/member"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>用户信息</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:THIRD:INFO">
					<li class="${nav_second eq 'member-third' ? 'active' : '' }">
						<a href="/member/third"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>第三方用户信息</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:POINT:INFO">
					<li class="${nav_second eq 'member-point' ? 'active' : '' }">
						<a href="/member/point"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户积分</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:POINT:INFO:LOG">
					<li class="${nav_second eq 'member-point-log' ? 'active' : '' }">
						<a href="/member/point/log"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户积分日志</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:INFO:LOG">
					<li class="${nav_second eq 'member-log' ? 'active' : '' }">
						<a href="/member/log"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户操作日志</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:VIP:INFO">
					<li class="${nav_second eq 'member-vip' ? 'active' : '' }">
						<a href="/member/vip"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>VIP信息</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:VIP:INFO:LOG">
					<li class="${nav_second eq 'member-vip-log' ? 'active' : '' }">
						<a href="/member/vip/log"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>VIP日志</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:PROMORECORD:LOG:INFO">
					<li class="${nav_second eq 'member-promoRecord' ? 'active' : '' }">
						<a href="/member/promoRecord"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户推广记录</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:CARE:LOG:INFO">
					<li class="${nav_second eq 'member-care' ? 'active' : '' }">
						<a href="/member/care"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户关注</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:SPONSOR:LOG:INFO">
					<li class="${nav_second eq 'member-sponsor-log' ? 'active' : '' }">
						<a href="/member/sponsor/log"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户赞助</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MEMBER:FAVORITE:LOG:INFO">
					<li class="${nav_second eq 'member-favorite' ? 'active' : '' }">
						<a href="/member/favorite"><i class="nav-sidebar-icon fa fa-fw fa-pencil-square-o"></i>用户收藏</a>
					</li>
					</shiro:hasPermission>
				</ul>
			</li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="DESIGNER:MENU">
			<li class="nav-sidebar-group">
				<a class="${nav_first eq 'designer' ? 'active' : 'collapsed' }" 
					data-toggle="collapse" data-parent="#js-nav-sidebar" href="#nav-sidebar-designer">
					<i class="nav-sidebar-icon fa fa-fw fa-users"></i>设计师
					<i class="glyphicon arrow"></i>
				</a>
				<ul class="collapse ${nav_first eq 'designer' ? 'in' : '' }" 
					aria-expanded="${nav_first eq 'designer' ? 'true' : 'false' }" id="nav-sidebar-designer">
					<shiro:hasPermission name="DESIGNER:INFO">
					<li class="${nav_second eq 'designer' ? 'active' : '' }">
						<a href="/designer"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>设计师</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="DESIGNER:WORK:INFO">
					<li class="${nav_second eq 'designer-work' ? 'active' : '' }">
						<a href="/designer/work"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>设计师作品</a>
					</li>
					</shiro:hasPermission>
				</ul>
			</li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="NEWS:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'news' ? 'active' : '' }">
			<a href="/news">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>新闻资讯</span>
				</a>
			</li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="ACTIVITY:MENU">
			<li class="nav-sidebar-group">
				<a class="${nav_first eq 'activity' ? 'active' : 'collapsed' }" 
					data-toggle="collapse" data-parent="#js-nav-sidebar" href="#nav-sidebar-activity">
					<i class="nav-sidebar-icon fa fa-fw fa-users"></i>活动
					<i class="glyphicon arrow"></i>
				</a>
				<ul class="collapse ${nav_first eq 'activity' ? 'in' : '' }" 
					aria-expanded="${nav_first eq 'activity' ? 'true' : 'false' }" id="nav-sidebar-activity">
					<shiro:hasPermission name="ACTIVITY:INFO">
					<li class="${nav_second eq 'activity' ? 'active' : '' }">
						<a href="/activity"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>活动</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="ACTIVITY:ENROLL:INFO">
					<li class="${nav_second eq 'activity-enroll' ? 'active' : '' }">
						<a href="/activity/enroll"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>活动报名</a>
					</li>
					</shiro:hasPermission>
				</ul>
			</li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="BRAND:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'brand' ? 'active' : '' }">
			<a href="/brand">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>合作品牌</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="DIGITAL:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'digitalFurniture' ? 'active' : '' }">
			<a href="/digital_furniture">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>数字家居</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="FILE:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'file' ? 'active' : '' }">
			<a href="/file">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>文件管理</span>
				</a>
			</li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="MEMBER:BASE:INFO">
			<li class="nav-sidebar-group">
				<a class="${nav_first eq 'smsRecord' ? 'active' : 'collapsed' }" 
					data-toggle="collapse" data-parent="#js-nav-sidebar" href="#nav-sidebar-smsRecord">
					<i class="nav-sidebar-icon fa fa-fw fa-users"></i>短信验证码
					<i class="glyphicon arrow"></i>
				</a>
				<ul class="collapse ${nav_first eq 'smsRecord' ? 'in' : '' }" 
					aria-expanded="${nav_first eq 'smsRecord' ? 'true' : 'false' }" id="nav-sidebar-smsRecord">
					
					<li class="${nav_second eq 'checkCode' ? 'active' : '' }">
						<a href="/checkCode"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>验证码管理</a>
					</li>
					<li class="${nav_second eq 'smsRecord' ? 'active' : '' }">
						<a href="/smsRecord"><i class="nav-sidebar-icon fa fa-fw fa-user"></i>短信记录</a>
					</li>
				</ul>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ADSPACE:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'adspace' ? 'active' : '' }">
			<a href="/adspace">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>广告位</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CODEVALUE:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'codevalue' ? 'active' : '' }">
			<a href="/codevalue">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>码表</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="COMMENT:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'comment' ? 'active' : '' }">
			<a href="/comment">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>评论管理</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="MSG:RECORD:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'msg' ? 'active' : '' }">
			<a href="/msg/record">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>系统消息</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="DOWNLOAD:MENU">
			<li class="nav-sidebar-dashboard ${nav_first eq 'download' ? 'active' : '' }">
			<a href="/download">
					<i class="nav-sidebar-icon fa fa-fw fa-cube"></i>
					<span>下载管理</span>
				</a>
			</li>
			</shiro:hasPermission>
			<li style="border-top: 1px solid rgba(0, 0, 0, 0.3);box-shadow: 0px 1px 0px rgba(255, 255, 255, 0.05) inset;">&nbsp;</li>
		</ul>
	</nav>
	
	<div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">

		<div class="row-fluid layout-main">

			<!-- <ol class="breadcrumb">
				<li><a href="#">Home</a></li>
				<li><a href="#">Library</a></li>
				<li class="active">Data</li>
			</ol> -->
			
			<sitemesh:write property='body'/>
			
			<div class="col-sm-12 col-md-12 col-lg-12" style="height: 40px"> </div>
			<footer class="" style="position: fixed; bottom: 0; width: 100%; background-color: #f5f5f5;">
				<p style="margin: 10px 20px">&copy; 2015 - 2016 ejuzuo.com</p>
			</footer>

		</div>
		
	</div>
	
	<script type="text/templdate-dot" id="dot-pagination">
	<jsp:include page="/WEB-INF/views/common/pagination.jsp"/>
	</script>
	
	<script src="${appResHost }/plugins/require.js/2.1.22/require.min.js"></script>
	<script src="/resources/scripts/requirejs-config.js" data-env="${appEnv }"></script>
	<sitemesh:write property='footer-scripts'/>
</body>
</html>