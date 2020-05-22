layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table
	form = layui.form, 
	element = layui.element, 
	layer = layui.layer,
	$ = layui.$;
	
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
			url : '/api/v1/vendor/searchVendor',
			type : 'GET',
			dataType : 'json',
			data : array,
			success : function(data) {
				if (data.code == 0) {
					var vendorData = data.data;
					for(var key in vendorData){
						if($("#"+key).length>0){
							$("#"+key).val(vendorData[key]);
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