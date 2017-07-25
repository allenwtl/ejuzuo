<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<title>图片查看器 - CMS - 我去投资 后台</title>

<link rel="icon" type="image/x-icon" href="/resources/favicon.ico">
<link rel="shortcut icon" href="/resources/favicon.ico">

<link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/pace/1.0.2/themes/blue/pace-theme-minimal.css" rel="stylesheet">

<style type="text/css">
/* Sticky footer styles
-------------------------------------------------- */
/* html {
  position: relative;
  min-height: 100%;
} */
body {
  /* Margin bottom by footer height */
  margin-bottom: 72px;
}

.dont-break-out {
  /* These are technically the same, but use both */
  overflow-wrap: break-word;
  word-wrap: break-word;

  -ms-word-break: break-all;
  /* This is the dangerous one in WebKit, as it breaks things wherever */
  word-break: break-all;
  /* Instead use this non-standard one: */
  word-break: break-word;

  /* Adds a hyphen where the word breaks, if supported (No Blink) */
  -ms-hyphens: auto;
  -moz-hyphens: auto;
  -webkit-hyphens: auto;
  hyphens: auto;
}
</style>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>

	<div class="container">
		<h1 class="page-header"><c:out value="${date }"/></h1>
		
		<c:if test="${empty imageUrls }">
		<p class="lead">当前日期对应的目录未创建或者没有图片</p>
		</c:if>
		
		<c:if test="${not empty imageUrls }">
		<div class="row">
			<c:set var="defaultImageSrc" value="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI0MiAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjwhLS0KU291cmNlIFVSTDogaG9sZGVyLmpzLzEwMCV4MjAwCkNyZWF0ZWQgd2l0aCBIb2xkZXIuanMgMi42LjAuCkxlYXJuIG1vcmUgYXQgaHR0cDovL2hvbGRlcmpzLmNvbQooYykgMjAxMi0yMDE1IEl2YW4gTWFsb3BpbnNreSAtIGh0dHA6Ly9pbXNreS5jbwotLT48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPjwhW0NEQVRBWyNob2xkZXJfMTUwNTlmOWRjNWYgdGV4dCB7IGZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMnB0IH0gXV0+PC9zdHlsZT48L2RlZnM+PGcgaWQ9ImhvbGRlcl8xNTA1OWY5ZGM1ZiI+PHJlY3Qgd2lkdGg9IjI0MiIgaGVpZ2h0PSIyMDAiIGZpbGw9IiNFRUVFRUUiLz48Zz48dGV4dCB4PSI4OS44NTkzNzUiIHk9IjEwNS4xIj4yNDJ4MjAwPC90ZXh0PjwvZz48L2c+PC9zdmc+"/>
			
			<c:forEach items="${imageUrls }" var="imageUrl">
			<div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="${imageUrl }" alt="100%x200"
						src="${imageUrl }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<!-- <h3>Thumbnail label</h3> -->
						<p class="dont-break-out">
							<a href="${imageUrl }" target="_blank"><c:out value="${imageUrl }"/></a>
						</p>
						<p><a href="#" class="btn btn-primary jq-btn-select" role="button" data-url="${imageUrl }">选择</a></p>
					</div>
				</div>
			</div>
			</c:forEach>
			<%-- <div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="holder.js/100%x200" alt="100%x200"
						src="${defaultImageSrc }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary" role="button">Button</a> <a
								href="#" class="btn btn-default" role="button">Button</a>
						</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="holder.js/100%x200" alt="100%x200"
						src="${defaultImageSrc }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary" role="button">Button</a> <a
								href="#" class="btn btn-default" role="button">Button</a>
						</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="holder.js/100%x200" alt="100%x200"
						src="${defaultImageSrc }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary" role="button">Button</a> <a
								href="#" class="btn btn-default" role="button">Button</a>
						</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="holder.js/100%x200" alt="100%x200"
						src="${defaultImageSrc }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary" role="button">Button</a> <a
								href="#" class="btn btn-default" role="button">Button</a>
						</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="holder.js/100%x200" alt="100%x200"
						src="${defaultImageSrc }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary" role="button">Button</a> <a
								href="#" class="btn btn-default" role="button">Button</a>
						</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-4">
				<div class="thumbnail">
					<img data-src="holder.js/100%x200" alt="100%x200"
						src="${defaultImageSrc }" data-holder-rendered="true"
						style="height: 200px; width: 100%; display: block;">
					<div class="caption">
						<h3>Thumbnail label</h3>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a href="#" class="btn btn-primary" role="button">Button</a> <a
								href="#" class="btn btn-default" role="button">Button</a>
						</p>
					</div>
				</div>
			</div> --%>
		</div>
		</c:if>
	</div>

	<footer class="navbar navbar-fixed-bottom" style="background-color: #f5f5f5;">
		<div class="container">
			<!-- <p class="text-muted">Place sticky footer content here.</p> -->
			<nav>
				<ul class="pager">
					<li class="previous">
						<a href="/news/ckeditor/image-viewer?CKEditor=${CKEditor }&CKEditorFuncNum=${CKEditorFuncNum }&langCode=${langCode }&date=${datePrevious }">
							<span aria-hidden="true">&larr;</span> 上一天
						</a>
					</li>
					
					<c:choose>
						<c:when test="${empty dateNext }">
							<li class="next disabled">
								<a href="#">下一天 <span aria-hidden="true">&rarr;</span></a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="next">
								<a href="/news/ckeditor/image-viewer?CKEditor=${CKEditor }&CKEditorFuncNum=${CKEditorFuncNum }&langCode=${langCode }&date=${dateNext }">
									下一天 <span aria-hidden="true">&rarr;</span>
								</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</div>
	</footer>
	
	<script src="http://cdn.bootcss.com/require.js/2.1.20/require.min.js"></script>
	<script src="/resources/scripts/requirejs-config.js"></script>
	<script src="/resources/scripts/news/news-ckeditor-image-viewer.js"></script>
</body>
</html>
