<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="table-responsive">
	<table class="table table-bordered table-condensed table-striped table-hover table-responsed">
	{{=it.table}}
	</table>
</div>
<div class="row pagination-bar">
	<div class="col-sm-6 col-md-3 form-inline">

		<div class="input-group input-group-sm">
			<span class="input-group-btn">
				<button type="button" class="btn btn-default pagination-bar-refresh">
					<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
				</button>
			</span>
			<select class="form-control pagination-bar-size">
					
				{{? it.pageSize == it.sizes[0]}}
					<option value="{{=it.sizes[0]}}" selected>{{=it.sizes[0]}}</option>
				{{??}}
					<option value="{{=it.sizes[0]}}">{{=it.sizes[0]}}</option>
				{{?}}
				{{? it.pageSize == it.sizes[1]}}
					<option value="{{=it.sizes[1]}}" selected>{{=it.sizes[1]}}</option>
				{{??}}
					<option value="{{=it.sizes[1]}}">{{=it.sizes[1]}}</option>
				{{?}}
				{{? it.pageSize == it.sizes[2]}}
					<option value="{{=it.sizes[2]}}" selected>{{=it.sizes[2]}}</option>
				{{??}}
					<option value="{{=it.sizes[2]}}">{{=it.sizes[2]}}</option>
				{{?}}
				
			</select> <span class="input-group-addon">/ {{=it.total}} 条记录</span>
		</div>
		<!-- /input-group -->

	</div>
	
	<div class="col-sm-6 col-md-3 col-md-push-6 form-inline">
		<div class="input-group input-group-sm pull-right">
			<span class="input-group-btn">
				<button type="button" class="btn btn-default pagination-bar-jump">Go!</button>
			</span>
			<input type="number" class="form-control pagination-bar-jump-num" value="{{=it.pageNo}}" min="1" max="{{=it.totalPage}}" pattern="\d{{{=it.totalPage.toString().length}}}"/>
			<span class="input-group-addon">/ {{=it.totalPage}} 页</span>
		</div>
		<!-- /input-group -->
	</div>

	<nav class="col-sm-12 col-md-6 col-md-pull-3 text-center">
		<ul class="pagination pagination-sm" style="margin: 0;">
			{{? it.pageNo === 1}}
				<li class="disabled"><a href="javascript:;"><span aria-hidden="true">First</span><span class="sr-only">First</span></a></li>
				<li class="disabled"><a href="javascript:;"><span aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a></li>
			{{??}}
				<li class="pagination-bar-btn" data-no="1"><a href="#"><span aria-hidden="true">First</span><span class="sr-only">First</span></a></li>
				<li class="pagination-bar-btn" data-no="{{=it.pageNo - 1}}"><a href="#"><span aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a></li>
			{{?}}
			
			{{? it.totalPage <= 9}}
				{{ var begin = 1; var end = it.totalPage;}}
			{{?? it.totalPage > 9 && it.pageNo < 6 }}
				{{ var begin = 1; var end = 9;}}
			{{?? it.totalPage > 9 && it.pageNo >= (it.totalPage - 4) }}
				{{ var begin = (it.totalPage - 8); var end = it.totalPage;}}
			{{??}}
				{{ var begin = (it.pageNo - 4); var end = (it.pageNo + 4);}}
			{{?}}
			
			{{ for (var i = begin; i <= end; i++) { }}
				{{? i == it.pageNo }}
					<li class="active"><a href="javascript:;">{{=i}} <span class="sr-only">(current)</span></a></li>
				{{??}}
					<li class="pagination-bar-btn" data-no="{{=i}}"><a href="#">{{=i}}</a></li>
				{{?}}
			{{ } }}
			
					
			{{? it.pageNo === it.totalPage }}
				<li class="disabled"><a href="javascript:;"><span aria-hidden="true">&raquo;</span><span class="sr-only">Next</span></a></li>
				<li class="disabled"><a href="javascript:;"><span aria-hidden="true">Last</span><span class="sr-only">Last</span></a></li>
			{{??}}
				<li class="pagination-bar-btn" data-no="{{=it.pageNo + 1}}"><a href="#"><span aria-hidden="true">&raquo;</span><span class="sr-only">Next</span></a></li>
				<li class="pagination-bar-btn" data-no="{{=it.totalPage}}"><a href="#"><span aria-hidden="true">Last</span><span class="sr-only">Last</span></a></li>
			{{?}}
			
		</ul>
	</nav>

	<div class="clearfix"></div>
</div>