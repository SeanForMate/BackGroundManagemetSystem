layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table,
		form = layui.form,
		layer = layui.layer,
		$ = layui.$;
	
	// 初始化方法调用
	showRoleData();
	
	// 表格数据初始化
	table.render({
	    elem: '#userList'
	    ,url:'api/v1/user/selectByPageIndexAndOther'
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,cols: [[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'userName', title:'账号', align: 'center'}
	      ,{field:'role', title:'权限', align: 'center',templet: function(d){
	          return d.role.roleName;
	      }}
	      ,{field:'realName', title:'姓名', align: 'center'}
	      ,{field:'sex', title:'性别', align: 'center',templet: function(d){
	    	  var sexContext = "";
	    	  if(d.sex==1){
	    		  sexContext = "男";
	    	  }else{
	    		  sexContext = "女";
	    	  }
	          return sexContext;
	      }}
	      ,{field:'linkPhone', title:'联系电话', align: 'center'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200, align: 'center'}
	    ]]
	    ,page: true
	});
	
	// 渲染用户权限信息
	function showRoleData(){
		$.ajax({
			url: "api/v1/role/selectRole",
			type: "GET",
			dataType: 'json',
	        data: null,
	        success: function (data) {
	        	if(data.code==0){
	        		var roleData = data.data;
	        		var roleContext = "";
	        		for(var i=0;i<roleData.length;i++){
	        			roleContext += "<option value=\""+roleData[i].roleId+"\">"+roleData[i].roleName+"</option>"
	        		}
	        		$("#roleId").append(roleContext);
	        	}else{
	        		layer.msg(data.msg,{icon:2})
	        	}
	        	form.render();
	        }
		});
	}
	
	// 查找用户点击事件
	$("#searchUser").click(function(){
		// 获取输入框的值
		var realName = $("#realName").val();
		var roleId = $("#roleId").val();
		var sex = $("#sex").val();
		table.reload('userList', {
			  where: {"realName":realName,"roleId":roleId,"sex":sex},
			  page: { curr: 1}
		});
	});
	
	// 头工具栏事件
	table.on('toolbar(userList)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'deleteChooseUser':
	        var data = checkStatus.data;
	        var userId = [];
	        for(var i=0;i<data.length;i++){
	        	userId.push(data[i].userId);
	        }
	        if(userId.length>0){
	        	 // 根据选中的行删除
		        layer.confirm('确定删除选中的数据吗？', function(index) {
					$.ajax({
						url : 'api/v1/user/deleteUser',
						type : 'POST',
					    traditional: true,
						data: {"userId":userId},
						dataType : 'json',
						success : function(data) {
							if (data.code == 0) {
								layer.msg("删除成功",{icon:1});
								table.reload("userList", {
									page : {
										curr : 1
									// 重新从第 1 页开始
									}
								}, 'data')
							} else {
								layer.msg(data.data,{icon:2});
							}
						}
					});
					layer.close(index);
				});
	        }else{
	        	layer.msg("请选择需要删除的数据！",{icon:2});
	        }
	        break;
	      case 'addUser':
	        // 跳转到添加页面
	  		var index = layer.open({
	  			title : "添加用户信息",
	  			type : 2,
	  			area : [ "600px", "550px" ],
	  			content : 'addUserInfo',
	  			success : function(layero, index) {
	  				setTimeout(function() {
	  					layui.layer.tips('点击此处关闭',
	  							'.layui-layer-setwin .layui-layer-close', {
	  								tips : 3
	  							});
	  				}, 500)
	  			}
	  		});
	  		break;
	    };
	});
	
	//监听工具条
	table.on('tool(userList)', function(obj) {
		var data = obj.data;
		if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var arry = [];
				arry.push(data.userId);
				$.ajax({
					url : '/api/v1/user/deleteUser',
					type : 'POST',
				    traditional: true,
					dataType : 'json',
					data : {"userId":arry},
					success : function(data) {
						if (data.code == 0) {
							layer.msg("删除成功",{icon:1});
							table.reload("userList", {
								page : {
									curr : 1
								// 重新从第 1 页开始
								}
							}, 'data')
						} else {
							layer.msg(data.data,{icon:2});
						}
					}
				});
				layer.close(index);
			});
		} else if (obj.event === 'edit') {
			// 打开编辑用户信息页面
			var index = layer.open({
				title : "修改用户信息",
				type : 2,
				area : [ "800px", "550px" ],
				content : 'updateUserInfo?userId='+data.userId,
				success : function(layero, index) {
					setTimeout(function() {
						layui.layer.tips('点击此处关闭',
								'.layui-layer-setwin .layui-layer-close', {
									tips : 3
								});
					}, 500)
				}
			});
		}
	});
});