layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table
	form = layui.form, 
	element = layui.element, 
	layer = layui.layer,
	$ = layui.$;
	
	// 查询供应商信息按钮点击事件
	$("#searchVendor").click(function() {
		// 获取数据
		var vendorName = $("#vendorName").val();
		var vendorAbbreviation = $("#vendorAbbreviation").val();
		var linkMan = $("#linkMan").val()
		table.reload("vendorList", {
			page : {
				curr : 1
			// 重新从第 1 页开始
			},
			where : {
				"vendorName" : vendorName,
				"vendorAbbreviation" : vendorAbbreviation,
				"linkMan" : linkMan
			},
			done : function(res,curr,count){
				console.log(curr);
			}
		}, 'data')
	});
	
	// 表格数据初始化
	table.render({
	    elem: '#vendorList'
	    ,url:'api/v1/vendor/selectByPageIndexAndOther'
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,cols: [[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'vendorName', title:'供应商名称',align: 'center'}
	      ,{field:'vendorAbbreviation', title:'供应商简称', width:150, align: 'center'}
	      ,{field:'telephone', title:'公司电话', width:150, align: 'center'}
	      ,{field:'linkMan', title:'联系人',  width:150, align: 'center'}
	      ,{field:'linkPhone', title:'联系电话', width:150, align: 'center'}
	      ,{field:'address', title:'地址', align: 'center'}
	      ,{field:'email', title:'邮箱', width:200, align: 'center'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200, align: 'center'}
	    ]]
	    ,page: true
	});
	
	//头工具栏事件
	  table.on('toolbar(vendorList)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'deleteChooseVendor':
	        var data = checkStatus.data;
	        var vendorId = [];
	        for(var i=0;i<data.length;i++){
	        	vendorId.push(data[i].vendorId);
	        }
	        if(vendorId.length>0){
	        	// 根据选中的行删除
		        layer.confirm('确定删除选中的数据吗？', function(index) {
					$.ajax({
						url : 'api/v1/vendor/deleteVendor',
						type : 'POST',
					    traditional: true,
						dataType : 'json',
						data : {"vendorId":vendorId},
						success : function(data) {
							if (data.code == 0) {
								layer.msg("删除成功",{icon:1});
								table.reload("vendorList", {
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
	      case 'addVendor':
	        // 跳转到添加页面
	  		var index = layer.open({
	  			title : "添加供应商信息",
	  			type : 2,
	  			area : [ "1000px", "750px" ],
	  			content : 'addVendorInfo',
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
	table.on('tool(vendorList)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			// 打开查看供应商信息页面
			var index = layer.open({
				title : "查看供应商信息",
				type : 2,
				area : [ "1400px", "700px" ],
				content : 'searchVendorInfo?vendorId='+data.vendorId,
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
				arry.push(data.vendorId);
				$.ajax({
					url : '/api/v1/vendor/deleteVendor',
					type : 'POST',
				    traditional: true,
					dataType : 'json',
					data : {"vendorId":arry},
					success : function(data) {
						if (data.code == 0) {
							layer.msg("删除成功",{icon:1});
							table.reload("vendorList", {
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
			// 打开编辑供应商信息页面
			var index = layer.open({
				title : "编辑供应商信息",
				type : 2,
				area : [ "1400px", "750px" ],
				content : 'updateVendorInfo?vendorId='+data.vendorId,
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