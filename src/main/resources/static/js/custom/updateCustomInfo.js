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
		$.ajax({
	          url: '/api/v1/custom/getCustomType',
	          type: 'GET',
	          dataType: 'json',
	          data: null,
	          success: function (data) {
	        	 if(data.code==0){
	        		 var datas = data.data;
	        		 var optionString = "";
					 for(var i=0;i<datas.length;i++){
						 optionString += '<option value="'+ datas[i].customTypeId +'">' + datas[i].customLevel + '</option>'
		             }
					 $("#customerSort").append(optionString);
	        	  }else{
	        		  $("#customerSort").append(data.data);
	        	  }
	        	 form.render();
	          }
		  });
		  // 客户性质下拉框
		  $.ajax({
			  url: '/api/v1/custom/getCustomCharacter',
	          type: 'GET',
	          dataType: 'json',
	          data: null,
	          success: function (data) {
	        	  if(data.code==0){
	         		 var datas = data.data;
	         		 var optionString = "";
	 				 for(var i=0;i<datas.length;i++){
	 					 optionString += '<option value="'+ datas[i].customCharacterId +'">' + datas[i].customCharacterContent + '</option>'
	 	             }
	 				 $("#customerQuale").append(optionString);
	         	  }else{
	         		  $("#customerQuale").append(data.data);
	         	  }
	         	 form.render();
	          }
		  });
		  // 客户分区下拉框
		  $.ajax({
			  url: '/api/v1/custom/getCustomZone',
	          type: 'GET',
	          dataType: 'json',
	          data: null,
	          success: function (data) {
	        	  if(data.code==0){
	         		 var datas = data.data;
	         		 var optionString = "";
	 				 for(var i=0;i<datas.length;i++){
	 					 optionString += '<option value="'+ datas[i].customZoneId +'">' + datas[i].zoneAdress + '</option>'
	 	             }
	 				 $("#customerZone").append(optionString);
	         	  }else{
	         		  $("#customerZone").append(data.data);
	         	  }
	         	 form.render();
	          }
		  });
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
								$("#"+key).val(customData[key]);
							}else if(key=="customerQuale"){
								$("#"+key).val(customData[key]);
							}else if(key=="customerZone"){
								$("#"+key).val(customData[key]);
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
	
  // 数据提交
  form.on("submit(customSubmit)",function(data){
	  var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
	  $.ajax({
		  url: '/api/v1/custom/updateCustom',
          type: 'POST',
          dataType: 'json',
          data: data.field,
          success: function (data) {
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