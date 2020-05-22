layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table,
		form = layui.form,
		layer = layui.layer,
		$ = layui.$;
	
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
	        			roleContext += "<option value=\""+roleData[i].roleId+"\">"+roleData[i].roleName+"</option>";
	        		}
	        		$("#roleId").append(roleContext);
	        		// 渲染用户信息
	        		showUserData();
	        	}else{
	        		layer.msg(data.msg,{icon:2})
	        	}
	        	form.render();
	        }
		});
	}
	
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
	        			if(key=="role_Id"){
	        				$("#roleId option[value="+userData[key]+"]").attr("selected","true");
	        			}else if(key=="sex"){
	        				$("input[name="+key+"][value="+userData[key]+"]").attr("checked","checked");
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
	
	// 重置密码按钮点击事件
	$("#resetPasswordBtn").click(function(){
		// 将密码重置为123456
		$("input[name=password]").val("123456");
		$(".trips").text("（已重置，请修改保存）");
	});
	
	// 表单验证
	form.verify({
		linkPhone: function(value){
			if(value==""){
				return "电话号码不能为空！";
			}else if(!window.top.phoneVerify.test(value)){
				return "电话号码格式错误！";
			}
		}
	});
	
	// 用户信息表单修改
	form.on("submit(userSubmit)",function(data){
		var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
		$.ajax({
			url: "api/v1/user/updateUser",
			type: "PUT",
			contentType: "application/json",//设置请求参数类型为json字符串
			data: JSON.stringify(data.field),
			dataType: 'json',
			success: function (reData) {
				if(reData.code==0){
					setTimeout(function(){
	        			parent.layer.close(index);
	      	            top.layer.msg("修改成功！",{icon: 1});
	      	            layer.closeAll("iframe");
	      	            // 刷新父页面
	      	            parent.location.reload();
	      	        },500);
				}else{
					layer.close(index);
	         		layer.msg(reData.msg,{icon: 2});
				}
			}
		});
		return false;
	});
	
});