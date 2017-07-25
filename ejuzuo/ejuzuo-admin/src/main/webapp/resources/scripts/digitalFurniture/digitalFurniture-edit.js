/**
 * 
 */
require(['jquery', 'base', 'doT', 'PNotify','cmsUploadImg','uploadArchive', 'select2', 'bootstrap-colorpicker'], 
		function($, base, doT, PNotify, cmsUploadImg, uploadArchive) {
	
	'use strict';
	
	/**图示*/
	var domainImage = $('#jq-domain-image').val();
	var pictureJson = JSON.parse($('#pictures').val());
	var str = '';
	var i = $('#picturesTable').children().length + 1;
	for(var j=0; j<pictureJson.length; j++,i++){
		str += '<tr>'
		+'<input type="hidden" value="'+i+'"/>'
		+'<td>'
		+'<textarea rows="3" cols="" id="intro-'+i+'" class="form-control" placeholder="图片描述">'+pictureJson[j].intro+'</textarea>'
		+'</td>'
		+'<td>'
		+'<div class="input-group">'
		+'<input type="hidden" class="form-control jq-val-upload-img" id="form-'+i+'"'
		+'data-type="'+i+'"'
		+'placeholder="relative path of image" value="'+pictureJson[j].img+'" readonly required />'
		+'<input type="text" id="form-'+i+'-205100" value="'+pictureJson[j].img205100+'" hidden />'
		+'<input type="text" id="form-'+i+'-850478" value="'+pictureJson[j].img850478+'" hidden />'
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
		+'<div class="thumbnail" id="jq-upload-thumbnail-'+i+'">'
		+'<img src="'+domainImage+pictureJson[j].img+'"'
		+'id="jq-upload-thumbnail-img-'+i+'" >'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<a role="button" class="btn btn-danger" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:">删除</a>'
		+'</td>'
		+'</tr>';
	}
	$("#picturesTable").append(str);
	
	$('#addimage').on('click', function () {
		var i;
		if($("#picturesTable tr:last input").val() == undefined){
			i = 1;
		}else{
			i = Number($("#picturesTable tr:last input").val()) + 1;
		}
		var i = $('#picturesTable').children().length + 1;
		$('#picturesTable').append('<tr>'
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
	
	/**规格*/
	var str = '';
	if($('#specification').val() != null && $('#specification').val() != '' && $('#specification').val() != 'undefined'){
		var specificationJson = JSON.parse($('#specification').val());
		for(var i=0; i<specificationJson.length; i++){
			var name = '';
			if(specificationJson[i].name != null && specificationJson[i].name != 'undefined'){
				name = specificationJson[i].name;
			}
		    var size = '';
		    if(specificationJson[i].size != null && specificationJson[i].size != 'undefined'){
		    	size = specificationJson[i].size;
			}
		    var material = '';
		    if(specificationJson[i].material != null && specificationJson[i].material != 'undefined'){
		    	material = specificationJson[i].material;
			}
		    var price = '';
		    if(specificationJson[i].price != null && specificationJson[i].price != 'undefined'){
		    	price = specificationJson[i].price;
			}
		    var relateId = '';
		    if(specificationJson[i].relateId != null && specificationJson[i].relateId != 'undefined'){
		    	relateId = specificationJson[i].relateId;
			}
			str += '<tr>'
				+'<td><input name="specificationName" type="text" placeholder="单位名称" value="'+name+'" class="form-control"/></td>'
				+'<td><input name="specificationValue" type="text" placeholder="产品尺寸" value="'+size+'" class="form-control"/></td>'
				+'<td><input name="specificationMaterial" type="text" placeholder="材质" value="'+material+'" class="form-control"/></td>'
				+'<td><input name="specificationPrice" type="text" placeholder="价格（元）" value="'+price+'" class="form-control"/></td>'
				+'<td><input name="specificationRelateId" type="text" placeholder="关联产品ID" value="'+relateId+'" class="form-control"/></td>'
				+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
				+'</tr>';
		}
	}
	$("#specificationTable").append(str);
	
	$('#addSpecification').on('click', function () {
		$('#specificationTable').append('<tr>'
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
	if(!($('#corporation').val() == '' || $('#corporation').val() == null)){
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
	if(str == ""){
		str += '<tr>'
			+'<td><input name="corporationName" type="text" value="公司/品牌名称" class="form-control"/></td>'
			+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
			+'<td><input name="corporationId" type="text" placeholder="关联公司资讯ID" class="form-control"/></td>'
//			+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
			+'</tr>';
	}
	$("#corporationTable").append(str);
	
//	$('#addCorporation').on('click', function () {
//		$('#corporationTable').append('<tr>'
//		+'<td><input name="corporationName" type="text" placeholder="公司/品牌名称" class="form-control"/></td>'
//		+'<td><input name="corporationValue" type="text" class="form-control"/></td>'
//		+'<td><input name="corporationId" type="text" placeholder="关联公司资讯ID" class="form-control"/></td>'
//		+'<td><a class="detail-icon" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)" href="javascript:"><i class="glyphicon glyphicon-minus icon-minus"></i></a></td>'
//		+'</tr>');
//	});
	
	/**附件名称*/
	if($('#form-archive-insert').val() != null && $('#form-archive-insert').val() != ''){
		$.ajax({
			url : '/file/viewJson',
			type : 'get',
			data : 'id='+$('#form-archive-insert').val(),
			dataType: 'json',
			error : function() {
				new PNotify({
					title : '请求失败, 请检查网络情况. ',
					type : 'error'
				});
			},
			success : function(response) {
				$('#form-archive-name').val(response.fileName);
			}
		});
	}
	
	var btn = 0;
	var url = '';
	var $btnSaveDraft = $('#form-btn-release-update');
	var formUpdate = document.getElementById('form-digitalFurniture-update');
	
	$btnSaveDraft.on('click', function(){
		btn = 1;
		url = '/digital_furniture/edit';
	});
	
	function resetBtn() {
		if (btn === 1) {
			$btnSaveDraft.button('reset');
		} else if (btn === 2) {
			$btnSaveDraft.prop('disabled', false);
		}
	}
	$('#form-digitalFurniture-update').on('submit', function(e){
		e.preventDefault();
		if (btn === 1) {
			$btnSaveDraft.button('loading');
		} else if (btn === 2) {
			$btnSaveDraft.prop('disabled', true);
		} else {
			return false;
		}
		var isValid = formUpdate.checkValidity();
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
	    $('#specificationTable tr td input').each(function(){
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
    				obj["relateId"] = relateId;
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
	    $('#picturesTable tr').each(function(){
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
					title: '更新异常',
					text: msg,
					type: 'error'
				});
				
				resetBtn();
			},
			success: function(resp) {
				if (resp.success) {
					new PNotify({
						title: '更新成功',
						type: 'success'
					});
					if (btn === 1) {
//						$btnSaveDraft.val('更新完成');
						window.location.href = "/digital_furniture";
					}
				} else {
					new PNotify({
						title: '更新失败',
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