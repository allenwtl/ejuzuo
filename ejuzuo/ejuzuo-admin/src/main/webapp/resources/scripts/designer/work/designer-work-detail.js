require([ 'jquery'], 
		function($){
	
	'use strict';
	// 解析风格
	var stylesJson = JSON.parse($('#styles').val());
	var designerWorkStyles = $('#designerWorkStyles').val();
	var result = '';
	var str=designerWorkStyles.split(",");      
    for (var i=0;i<str.length ;i++ ){   
        for(var j=0;j<stylesJson.length ;j++ ){
        	if(stylesJson[j].valueCode == str[i]){
        		result += " "+stylesJson[j].valueName;
        		break;
        	}
        }
    }
    $("#detail-style").html(result);
    // 解析内容
    var contentJson = JSON.parse($('#content').val());
    var domainImage = $("#domainImage").val();
    var str = '';
    for(var i=0; i<contentJson.length; i++){
    	str += '<img width="400" src="'+domainImage+contentJson[i].img+'">';
    	str += '<p>'+contentJson[i].intro+'</p>';
    }
    $("#contentDiv").html(str);
});