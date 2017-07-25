/**
 * 
 */
require(['jquery', 'base', 'doT', 'PNotify','cmsUploadImg','uploadArchive','select2', 'bootstrap-colorpicker'], 
		function($, base, doT, PNotify,cmsUploadImg,uploadArchive) {
	
	'use strict';
	var spaceSmallCategorysJson = JSON.parse($('#spaceSmallCategorys').val());
	var domainImage = $('#jq-domain-image').val();
	$('#spaceCategory').on('change', function(){
		var spaceCategory = $('#spaceCategory option:selected').val();
		var str = '';
		for(var i=0; i<spaceSmallCategorysJson.length; i++){
			if(spaceSmallCategorysJson[i].parentCode == spaceCategory){
			str += '<input type="checkbox" name="spaceSmallCategory" value="'+spaceSmallCategorysJson[i].valueCode+'">'+spaceSmallCategorysJson[i].valueName+'&nbsp;&nbsp;';
			}
		}
		$('#spaceSmallCategory').html(str);
	});
	$('#addimage').on('click', function () {
		var i;
		if($("#imagegroup tr:last input").val() == undefined){
			i = 1;
		}else{
			i = Number($("#imagegroup tr:last input").val()) + 1;
		}
		$('#imagegroup').append('<tr>'
						+'<input type="hidden" value="'+i+'"/>'
						+'<td>'
						+'<textarea rows="3" cols="" id="intro-'+i+'" class="form-control" placeholder="图片描述"></textarea>'
						+'</td>'
						+'<td>'
						+'<div class="input-group">'
						+'<input type="hidden" class="form-control jq-val-upload-img" id="form-'+i+'"'
						+'data-type="'+i+'"'
						+'placeholder="relative path of image" readonly required />'
						+'<input type="text" id="form-'+i+'-205100" hidden />'
						+'<input type="text" id="form-'+i+'-850478" hidden />'
						+'</div>'
						+'<input type="file" class="jq-file-upload-img"'
						+'id="form-file-'+i+'"'
						+'data-btn="form-btn-'+i+'" '
						+'data-clear="form-btn-clear-'+i+'"/>'
						+'<input type="button" class="btn btn-sm btn-primary jq-btn-upload-img" id="form-btn-'+i+'" value="上传" '
						+'data-loading-text="上传中..." '
						+'data-field="form-'+i+'" '
						+'data-file="form-file-'+i+'" '
						+'data-content="true" ' // 数字家居内容标记
						+'data-form="form-upload-'+i+'" disabled>'
						+'<input type="button" class="btn btn-sm btn-default jq-btn-clear-upload-img" id="form-btn-clear-'+i+'" value="清除"' 
						+'data-loading-text="清除中..."'
						+'data-field="form-'+i+'"'
						+'data-file="form-file-'+i+'"'
						+'data-btn="form-btn-'+i+'" disabled>'
						+'<div class="thumbnail hidden" id="jq-upload-thumbnail-'+i+'">'
						+'<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"'
						+'id="jq-upload-thumbnail-img-'+i+'" >'
						+'</div>'
						+'</td>'
						+'<td>'
						+'<a role="button" class="btn btn-danger" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:">删除</a>'
						+'</td>'
						+'</tr>');
		// 解绑、再重新绑定
		$('.jq-val-upload-img').unbind();
		$('.jq-file-upload-img').unbind();
		$('.jq-btn-clear-upload-img').unbind();
		$('.jq-btn-upload-img').unbind();
		cmsUploadImg.init();
	});
	
	$('#addSpecification').on('click', function () {
		$('#specificationGroup').append('<tr>'
		+'<td><input name="specificationName" type="text" placeholder="单位名称" class="form-control"/></td>'
		+'<td><input name="specificationValue" type="text" placeholder="产品尺寸" class="form-control"/></td>'
		+'<td><input name="specificationMaterial" type="text" placeholder="材质" class="form-control"/></td>'
		+'<td><input name="specificationPrice" type="text" placeholder="价格（元）" class="form-control"/></td>'
		+'<td><input name="specificationRelateId" type="text" placeholder="关联产品ID" class="form-control"/></td>'
		+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
		+'</tr>');
	});
	
	/**公司*/
	var str = '';
	if($('#corporation').val() == null || $('#corporation').val() == ''){
		str += '<tr>'
			+'<td><input name="corporationName" type="text" value="公司/品牌名称"  class="form-control"/></td>'
			+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
			+'<td><input name="corporationId" type="text" placeholder="关联公司资讯ID" class="form-control"/></td>'
//			+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
			+'</tr>'
//			+'<tr>'
//			+'<td><input name="corporationName" type="text" value="介绍"  class="form-control"/></td>'
//			+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
//			+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
//			+'</tr>'
//			+'<tr>'
//			+'<td><input name="corporationName" type="text" value="联系方式"  class="form-control"/></td>'
//			+'<td><textarea id="corporationValue" rows="3" cols="" class="form-control"></textarea></td>'
//			+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
//			+'</tr>'
//			+'<tr>'
//			+'<td><input name="corporationName" type="text" value="公司地址"  class="form-control"/></td>'
//			+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
//			+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
//			+'</tr>'
//			+'<tr>'
//			+'<td><input name="corporationName" type="text" value="公司网站"  class="form-control"/></td>'
//			+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
//			+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
//			+'</tr>';
	}else{
		var corporationJson = JSON.parse($('#corporation').val());
		for(var i=0; i<corporationJson.length; i++){
			var name = '';
			if(corporationJson[i].name != null && corporationJson[i].name != 'undefined'){
				name = corporationJson[i].name;
			}
		    var val = '';
		    if(corporationJson[i].val != null && corporationJson[i].val != 'undefined'){
		    	val = corporationJson[i].val;
			}
		    var id = '';
		    if(corporationJson[i].id != null && corporationJson[i].id != 'undefined'){
		    	id = corporationJson[i].id;
			}
			str += '<tr>'
				+'<td><input name="corporationName" type="text" value="'+name+'" class="form-control"/></td>'
				+'<td><input name="corporationValue" type="text" value="'+val+'" class="form-control"/></td>'
				+'<td><input name="corporationId" type="text" placeholder="关联公司资讯ID" value="'+id+'" class="form-control"/></td>'
//				+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
				+'</tr>';
		}
	}
	
	$("#corporationTable").append(str);
	
	$('#addCorporation').on('click', function () {
		$('#corporationTable').append('<tr>'
		+'<td><input name="corporationName" type="text" class="form-control"/></td>'
		+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
		+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
		+'</tr>');
	});
	
	var btn = 0;
	var url = '';
	var $btnSaveDraft = $('#form-btn-save-draft');
	var formAdd = document.getElementById('form-add');
	
	$btnSaveDraft.on('click', function(){
		btn = 1;
		url = '/digital_furniture/add';
	});
	
	function resetBtn() {
		if (btn === 1) {
			$btnSaveDraft.button('reset');
		} else if (btn === 2) {
			$btnSaveDraft.prop('disabled', false);
		}
	}
	
	$('#form-add').on('submit', function(e){
		e.preventDefault();
		if (btn === 1) {
			$btnSaveDraft.button('loading');
		} else if (btn === 2) {
			$btnSaveDraft.prop('disabled', true);
		} else {
			return false;
		}
		var isValid = formAdd.checkValidity();
		if (!isValid) {
			resetBtn();
			return false;
		}
		var data = {};
		var dataArr = $(this).serializeArray();
	    $.each(dataArr, function() {
	        if (data[this.name] !== undefined) {
	            if (!data[this.name].push) {
	            	data[this.name] = [data[this.name]];
	            }
	            data[this.name].push(this.value || '');
	        } else {
	        	data[this.name] = this.value || '';
	        }
	    });
	    
	    /**获取规格*/
	    var specificationJson = [];
	    var key = '';
	    var size = '';
	    var material = '';
	    var price = '';
	    var relateId = '';
	    var flag = 0;
	    $('#specificationGroup tr td input').each(function(){
	    	if(flag == 0){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			key = $(this).val();
	    		}else {
	    			key = '';
	    		}
	    		flag = 1;
	    	}else if(flag == 1){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			size = $(this).val();
	    		}else{
	    			size = '';
	    		}
	    		flag = 2;
	    	}else if(flag == 2){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			material = $(this).val();
	    		}else{
	    			material = '';
	    		}
	    		flag = 3;
	    	}else if(flag == 3){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			price = $(this).val();
	    		}else{
	    			price = '';
	    		}
	    		flag = 4;
	    	}else if(flag == 4){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			relateId = $(this).val();
	    		}else{
	    			relateId = '';
	    		}
	    		if(key != ''){
    				var obj = {};
    				obj["name"] = key;
    				obj["size"] = size;
    				obj["material"] = material;
    				obj["price"] = price;
    				obj["relateId"] = material;
    				specificationJson.push(obj);
    			}
	    		flag = 0;
	    	}
	    });
	    data.specification = JSON.stringify(specificationJson);
	    
	    /**获取公司信息*/
	    var corporationJson = [];
	    var name = '';
	    var val = '';
	    var id = '';
	    var flag = 0;
	    $('#corporationTable tr td input').each(function(){
	    	if(flag == 0){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			name = $(this).val();
	    		}else {
	    			name = '';
	    		}
	    		flag = 1;
	    	}else if(flag == 1){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			val = $(this).val();
	    		}else{
	    			val = '';
	    		}
	    		flag = 2;
	    	}else if(flag == 2){
	    		if(!($(this).val() == null || $(this).val() == '')){
	    			id = $(this).val();
	    		}else{
	    			id = '';
	    		}
	    		if(name != ''){
    				var obj = {};
    				obj["name"] = name;
    				obj["val"] = val;
    				obj["id"] = id;
    				corporationJson.push(obj);
    			}
	    		flag = 0;
	    	}
	    });
//	    var corporationJson = {};
//	    var key = '';
//	    var flag = 0;
//	    $('#corporationTable tr td input').each(function(){
//	    	if(flag == 0){
//	    		if(!($(this).val() == null || $(this).val() == '')){
//	    			key = $(this).val();
//	    		}else {
//	    			key = '';
//	    		}
//	    		flag = 1;
//	    	}else {
//	    		if(key != '' && !($(this).val() == null || $(this).val() == '')){
//	    			corporationJson[key] = $(this).val();
//	    		}else{
//	    			key = '';
//	    		}
//	    		flag = 0;
//	    	}
//	    });
	    data.corporation = JSON.stringify(corporationJson);
	    
	    /**获取图示*/
	    var picturesJson = [];
	    $('#imagegroup tr').each(function(){
	    	var i = $(this).children().val();
	    	if($('#form-'+i).val() != ""){
	    		var obj = {};
		    	obj["intro"] = $('#intro-'+i).val();
		    	obj["img"] = $('#form-'+i).val();
		    	obj["img205100"] = $('#form-'+i+'-205100').val();
		    	obj["img850478"] = $('#form-'+i+'-850478').val();
		    	picturesJson.push(obj);
	    	}
	    });
	    data.pictures = JSON.stringify(picturesJson);
	    
	    /**获取空间小类*/
	    var smallArr = [];
	    $("input[name='spaceSmallCategory']:checked").each(function(index,item){
	    	smallArr.push($(this).val());
		});
	    data.spaceSmallCategory = smallArr.join(",");
	    
	    $.ajax({
	    	url: url,
			type: 'POST',
			data: data,
			dataType: "json",
			complete: function() {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var msg = '';
				if (!!jqXHR.responseJSON) {
					msg = jqXHR.responseJSON.msg
				}
				new PNotify({
					title: '提交异常',
					text: msg,
					type: 'error'
				});
				
				resetBtn();
			},
			success: function(resp) {
				if (resp.success) {
					new PNotify({
						title: '内容提交成功',
						type: 'success'
					});
					
					if (btn === 1) {
						$btnSaveDraft.val('保存完成');
					}
				} else {
					new PNotify({
						title: '提交失败',
						text: resp.msg || '',
						type: 'error'
					});
					resetBtn();
				}
			}
	    });
	});
	
	cmsUploadImg.init();
	uploadArchive.init();
});