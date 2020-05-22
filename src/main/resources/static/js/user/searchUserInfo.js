layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table,
		form = layui.form,
		layer = layui.layer,
		$ = layui.$;
	
	// 初始化数据
	showUserData();
	
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
	
	function showUserData(){
		var array = new Array();  //存放分割后的字符串 
		array = urlArgs();
		$.ajax({
			url: "api/v1/user/searchUser",
			type: "GET",
			dataType: 'json',
	        data: array,
	        success: function (data) {
	        	if(data.code==0){
	        		var userData = data.data;
	        		for(var key in userData){
	        			if(key=="sex"){
	        				if(userData[key]==1){
	        					$("input[name="+key+"]").val("男");
	        				}else{
	        					$("input[name="+key+"]").val("女");
	        				}
	        			}else if(key=="role"){
	        				$("input[name=roleName]").val(userData[key].roleName);
	        			}else if(key=="skinTheme"){
	        				$("input[name=skinName]").val(userData[key].skinName);
	        			}else{
    		            	$("input[name="+key+"]").val(userData[key]);
    		            }
	        		}
	        	}else{
	        		layer.msg(data.msg,{icon:2})
	        	}
	        	form.render();
	        }
		});
	}
	
});