layui.use(['form','layer','jquery'],function(){
  var form = layui.form,
		layer = layui.layer,
		$ = layui.$;
  
  // 初始化方法调用
  initData();
  
  // 渲染数据
  function initData(){
	  // 客户分类下拉框
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
				 $("#customLevel").append(optionString);
        	  }else{
        		  $("#customLevel").append(data.data);
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
 				 $("#customCharacterContent").append(optionString);
         	  }else{
         		  $("#customCharacterContent").append(data.data);
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
 				 $("#zoneAdress").append(optionString);
         	  }else{
         		  $("#zoneAdress").append(data.data);
         	  }
         	 form.render();
          }
	  });
  }
  
  // 数据提交
  form.on("submit(customSubmit)",function(data){
	  var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
	  $.ajax({
		  url: '/api/v1/custom/insertCustom',
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