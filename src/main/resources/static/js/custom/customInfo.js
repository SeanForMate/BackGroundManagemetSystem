layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table
	form = layui.form, 
	element = layui.element, 
	layer = layui.layer,
	$ = layui.$;

	// 初始化方法调用
	initData();

	// 渲染数据
	function initData() {
		// 客户分类下拉框
		$.ajax({
			url : 'api/v1/custom/getCustomType',
			type : 'GET',
			dataType : 'json',
			data : null,
			success : function(data) {
				if (data.code == 0) {
					var datas = data.data;
					var optionString = "";
					for (var i = 0; i < datas.length; i++) {
						optionString += '<option value="'
								+ datas[i].customTypeId + '">'
								+ datas[i].customLevel + '</option>'
					}
					$("#customLevel").append(optionString);
				} else {
					$("#customLevel").append(data.data);
				}
				form.render();
			}
		});
		// 客户性质下拉框
		$.ajax({
			url : 'api/v1/custom/getCustomCharacter',
			type : 'GET',
			dataType : 'json',
			data : null,
			success : function(data) {
				if (data.code == 0) {
					var datas = data.data;
					var optionString = "";
					for (var i = 0; i < datas.length; i++) {
						optionString += '<option value="'
								+ datas[i].customCharacterId + '">'
								+ datas[i].customCharacterContent + '</option>'
					}
					$("#customCharacterContent").append(optionString);
				} else {
					$("#customCharacterContent").append(data.data);
				}
				form.render();
			}
		});
		// 客户分区下拉框
		$.ajax({
			url : 'api/v1/custom/getCustomZone',
			type : 'GET',
			dataType : 'json',
			data : null,
			success : function(data) {
				if (data.code == 0) {
					var datas = data.data;
					var optionString = "";
					for (var i = 0; i < datas.length; i++) {
						optionString += '<option value="'
								+ datas[i].customZoneId + '">'
								+ datas[i].zoneAdress + '</option>'
					}
					$("#zoneAdress").append(optionString);
				} else {
					$("#zoneAdress").append(data.data);
				}
				form.render();
			}
		});
	}

	// 查询客户信息按钮点击事件
	$("#searchCustom").click(function() {
		// 获取数据
		var pyCode = $("#pyCode").val();
		var customerName = $("#customerName").val();
		var companyName = $("#companyName").val();
		var linkMan = $("#linkMan").val()
		var customerSort = $("#customLevel").val();
		var customerQuale = $("#customCharacterContent").val();
		var customerZone = $("#zoneAdress").val();
		table.reload("customList", {
			page : {
				curr : 1
			// 重新从第 1 页开始
			},
			where : {
				"pyCode" : pyCode,
				"customerName" : customerName,
				"companyName" : companyName,
				"linkMan" : linkMan,
				"customerSort" : customerSort,
				"customerQuale" : customerQuale,
				"customerZone" : customerZone
			}
		}, 'data')
	});
	
	
	// 表格数据初始化
	table.render({
	    elem: '#customList'
	    ,url:'api/v1/custom/selectByPageIndexAndOther'
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,cols: [[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'pyCode', title:'助记码', width:150, align: 'center'}
	      ,{field:'customerName', title:'客户简称', width:150, align: 'center'}
	      ,{field:'companyName', title:'公司名称', align: 'center'}
	      ,{field:'companyPhone', title:'公司电话', width:150, align: 'center'}
	      ,{field:'linkMan', title:'联系人', width:150, align: 'center'}
	      ,{field:'linkPhone', title:'联系电话', width:150, align: 'center'}
	      ,{field:'customLevel', title:'客户分类', width:100, align: 'center'}
	      ,{field:'customCharacterContent', title:'客户性质', width:200, align: 'center'}
	      ,{field:'zoneAdress', title:'客户分区', width:100, align: 'center'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200, align: 'center'}
	    ]]
	    ,page: true
	});
	
	//头工具栏事件
	table.on('toolbar(customList)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'deleteChooseCustom':
	        var data = checkStatus.data;
	        var customId = [];
	        for(var i=0;i<data.length;i++){
	        	customId.push(data[i].customerId);
	        }
	        if(customId.length>0){
	        	 // 根据选中的行删除
		        layer.confirm('确定删除选中的数据吗？', function(index) {
					$.ajax({
						url : 'api/v1/custom/deleteCustom',
						type : 'POST',
					    traditional: true,
						dataType : 'json',
						data : {"customId":customId},
						success : function(data) {
							if (data.code == 0) {
								layer.msg("删除成功",{icon:1});
								table.reload("customList", {
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
	      case 'addCustom':
	        // 跳转到添加页面
	  		var index = layer.open({
	  			title : "添加客户信息",
	  			type : 2,
	  			area : [ "1400px", "750px" ],
	  			content : 'addCustomInfo',
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
	table.on('tool(customList)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			// 打开查看客户信息页面
			var index = layer.open({
				title : "查看客户信息",
				type : 2,
				area : [ "1400px", "750px" ],
				content : 'searchCustomInfo?customId='+data.customerId,
				success : function(layero, index) {
					setTimeout(function() {
						layui.layer.tips('点击此处关闭',
								'.layui-layer-setwin .layui-layer-close', {
									tips : 3
								});
					}, 500)
				}
			});
		} else if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var arry = [];
				arry.push(data.customerId);
				$.ajax({
					url : '/api/v1/custom/deleteCustom',
					type : 'POST',
				    traditional: true,
					dataType : 'json',
					data : {"customId":arry},
					success : function(data) {
						if (data.code == 0) {
							layer.msg("删除成功",{icon:1});
							table.reload("customList", {
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
			// 打开编辑客户信息页面
			var index = layer.open({
				title : "编辑客户信息",
				type : 2,
				area : [ "1400px", "750px" ],
				content : 'updateCustomInfo?customId='+data.customerId,
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