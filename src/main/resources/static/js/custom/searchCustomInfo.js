layui.use(['layer', 'form', 'element','laydate','jquery'], function(){
	var form=layui.form,
	layer = layui.layer
	,element = layui.element
	,laydate = layui.laydate
	,$ = layui.jquery;
	
	// 初始化方法
	initData();
	
	// 获取URL的参数
	function urlArgs(){
	    var args = {};
	    var query = location.search.substring(1);
	    var pairs = query.split("&");
	    for(var i = 0;i < pairs.length; i++){
	        var pos = pairs[i].indexOf("=");
	        if(pos == -1) continue;
	        var name = pairs[i].substring(0, pos);
	        var value = pairs[i].substring(pos + 1);
	        value = decodeURIComponent(value);
	        args[name] = value;
	    }
	    return args;
	}
	
	// 渲染数据
	function initData(){
		var array = new Array();  //存放分割后的字符串 
		array = urlArgs();
		$.ajax({
			url : '/api/v1/custom/searchCustom',
			type : 'GET',
			dataType : 'json',
			data : array,
			success : function(data) {
				if (data.code == 0) {
					var customData = data.data;
					for(var key in customData){
						if($("#"+key).length>0){
							if(key=="customerSort"){
								$("#"+key).append("<option value=\""+customData["customerSort"]+"\">"+customData["customLevel"]+"</option>");
							}else if(key=="customerQuale"){
								$("#"+key).append("<option value=\""+customData["customerQuale"]+"\">"+customData["customCharacterContent"]+"</option>");
							}else if(key=="customerZone"){
								$("#"+key).append("<option value=\""+customData["customerZone"]+"\">"+customData["zoneAdress"]+"</option>");
							}else{
								$("#"+key).val(customData[key]);
							}
						}
					}
					form.render();
				} else {
					layer.msg(data.data,{icon:2});
				}
			}
		});
	}
	
});