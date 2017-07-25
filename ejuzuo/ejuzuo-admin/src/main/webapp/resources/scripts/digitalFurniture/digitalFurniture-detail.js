require(['jquery', 'base'], function($, base) {
	
	'use strict';
	
	/**图示*/
	var domainImage = $('#jq-domain-image').val();
	var pictureJson = JSON.parse($('#pictures').val());
	var str = '';
	for(var i=0; i<pictureJson.length; i++){
		str += '<input type="text" class="form-control" placeholder="描述" readonly value="'+pictureJson[i].intro+'"/>'
				+'<div class="thumbnail">'
				+'<img src="'+domainImage+pictureJson[i].img+'" alt="缩略图">'
				+'</div>';
	}
	$("#picturesDiv").append(str);
	
	/**规格*/
	var str = '';
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
			+'<td>'+name+'</td>'
			+'<td>'+size+'</td>'
			+'<td>'+material+'</td>'
			+'<td>'+price+'</td>'
			+'<td>'+relateId+'</td>'
			+'</tr>';
	}
	$("#specificationTable").append(str);
	
	/**公司*/
	var str = '';
	var corporationJson = JSON.parse($('#corporation').val());
	for(var key in corporationJson){
		str += '<tr><td>'+key+'</td><td>'+corporationJson[key]+'</td></tr>'
	}
	$("#corporationTable").append(str);
	
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
				$('#form-archive-name').html(response.fileName);
			}
		});
	}
	
});