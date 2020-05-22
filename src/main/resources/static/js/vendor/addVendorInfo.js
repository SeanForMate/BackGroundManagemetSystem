layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table
	form = layui.form, 
	element = layui.element, 
	layer = layui.layer,
	$ = layui.$;
	
	
	form.on("submit(vendorSubmit)",function(data){
		var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
		$.ajax({
			url: "api/v1/vendor/insertVendor",
			type: "POST",
			data: data.field,
			dataType: "json",
			success: function(data){
				if(data.code==0){
	        		  setTimeout(function(){
	        			parent.layer.close(index);
	      	            top.layer.msg("添加成功！",{icon: 1});
	      	            layer.closeAll("iframe");
	      	            // 刷新父页面
	      	            parent.location.reload();
	      	        },500);
	         	  }else{
	         		 top.layer.close(index);
	         		 top.layer.msg(data.data,{icon: 2});
	         	  }
			}
		});
		return false;
	});
	
	
});