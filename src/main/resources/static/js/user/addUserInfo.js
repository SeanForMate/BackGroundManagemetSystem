layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table,
		form = layui.form,
		layer = layui.layer,
		$ = layui.$;
	
	var userNameFlag = false;
	// 初始化方法调用
	showRoleData();
	
	// 渲染用户权限信息
	function showRoleData(){
		$.ajax({
			url: "api/v1/role/select",
			type: "GET",
			dataType: 'json',
	        data: null,
	        success: function (data) {
	        	if(data.code==0){
	        		var roleData = data.data;
	        		var roleContext = "";
	        		for(var i=0;i<roleData.length;i++){
	        			if(roleData[i].roleName=="普通"){
	        				roleContext += "<option value=\""+roleData[i].roleId+"\" selected=\"selected\">"+roleData[i].roleName+"</option>";
	        			}else{
	        				roleContext += "<option value=\""+roleData[i].roleId+"\">"+roleData[i].roleName+"</option>";
	        			}
	        		}
	        		$("#roleId").append(roleContext);
	        	}else{
	        		layer.msg(data.msg,{icon:2})
	        	}
	        	form.render();
	        }
		});
	}
	
	// 判断账号是否重复
	$("#userName").blur(function(){
		var userName = $(this).val();
		if(userName!=""){
			// 对比数据库内的账号
			$.ajax({
				url: "api/v1/user/userNameVerify",
				type: "GET",
				data: {"userName":userName},
				dataType: "json",
				success: function(data){
					var content = "";
					if(data.code==0){
						userNameFlag = true;
						content = "<span style=\"color: #01AAED;\">"+data.msg+"</span>";
					}else{
						content = "<span style=\"color: red;\">"+data.msg+"</span>";
					}
					$("#tripText").html(content);
				}
			});
		}else{
			$("#tripText").html("<span style=\"color: red;\">账号不能为空！</span>");
		}
	});
	
	// 表单验证
	form.verify({
		userName : function(value){
			if(value==""){
				return "账号不能为空！";
			}else if(!window.top.numberAndLetters.test(value)){
				return "不能出现汉字和空格以及特殊符号！";
			}else if(value.length<4 || value.length>16){
				return "账号长度要在4-16长度之内！";
			}
		},
		linkPhone: function(value){
			if(value==""){
				return "电话号码不能为空！";
			}else if(!window.top.phoneVerify.test(value)){
				return "电话号码格式错误！";
			}
		}
	});
	
	// 用户信息提交
	form.on("submit(userSubmit)",function(data){
		if(userNameFlag){
			var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
			$.ajax({
				url: "api/v1/user/insertUser",
				type: "POST",
				data: data.field,
				dataType: 'json',
				success: function (reData) {
					if(reData.code==0){
						setTimeout(function(){
		        			parent.layer.close(index);
		      	            top.layer.msg("添加成功！",{icon: 1});
		      	            layer.closeAll("iframe");
		      	            // 刷新父页面
		      	            parent.location.reload();
		      	        },500);
					}else{
						layer.close(index);
		         		layer.msg(data.data,{icon: 2});
					}
				}
			});
		}
		return false;
	});
	
});