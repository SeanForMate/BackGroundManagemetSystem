layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
	var table = layui.table,
	form = layui.form,
	layer = layui.layer,
	$ = layui.$;
	
	// 表格数据初始化
	table.render({
	    elem: '#commodityList'
	    ,url:'api/v1/commodity/selectByPageIndexAndOther'
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,cols: [[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'commodityBatchNo', title:'订货号', width:200, align: 'center'}
	      ,{field:'commodityName', title:'商品名称',align: 'center'}
	      ,{field:'commodityPriceUnit', title:'价格单位', width:100, align: 'center'}
	      ,{field:'commodityPrice', title:'商品价格', width:100, align: 'center'}
	      ,{field:'commodityUnit', title:'商品单位', width:100, align: 'center'}
	      ,{field:'commoditySize', title:'商品规格', width:200, align: 'center'}
	      ,{field:'commodityWrap', title:'商品包装', width:100, align: 'center'}
	      ,{field:'commodityOrigin', title:'商品产地', width:100, align: 'center'}
	      ,{field:'commodityOrigin', title:'供应商', align: 'center'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200, align: 'center'}
	    ]]
	    ,page: true
	});
	
	// 头工具栏事件
	table.on('toolbar(commodityList)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'deleteChooseCommodity':
	        var data = checkStatus.data;
	        var commodityId = [];
	        for(var i=0;i<data.length;i++){
	        	commodityId.push(data[i].commodityId);
	        }
	        // 根据选中的行删除
	        $.ajax({
				url : '/api/v1/commodity/deleteCommodity',
				type : 'POST',
			    traditional: true,
				dataType : 'json',
				data : {"commodityId":commodityId},
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
	      break;
	      case 'addCommodity':
	        // 跳转到添加页面
	      break;
	      case 'isAll':
	        layer.msg(checkStatus.isAll ? '全选': '未全选');
	      break;
	      
	      //自定义头工具栏右侧图标 - 提示
	      case 'LAYTABLE_TIPS':
	        layer.alert('这是工具栏右侧自定义的一个图标按钮');
	      break;
	    };
	});
	
	// 初始化方法调用
	initData();

	// 渲染数据
	function initData() {
		// 客户分类下拉框
		$.ajax({
			url : '/api/v1/vendor/selectAll',
			type : 'GET',
			dataType : 'json',
			data : null,
			success : function(data) {
				if (data.code == 0) {
					var datas = data.data;
					var optionString = "";
					for (var i = 0; i < datas.length; i++) {
						optionString += '<option value="'
								+ datas[i].vendorId + '">'
								+ datas[i].vendorName + '</option>'
					}
					$("#vendor").append(optionString);
				} else {
					$("#vendor").append(data.data);
				}
				form.render();
			}
		});
	}
	
	// 点击查看商品点击事件
	$(".searchCommodity").click(function(){
		// 获取参数
		var commodityName = $("#commodityName").val();
		var commodityReferred = $("#commodityReferred").val();
		var vendor_Id = $("#vendor").val();
		table.reload("commodityList", {
			page : {
				curr : 1 // 重新从第 1 页开始
			},
			where : {
				"commodityName" : commodityName,
				"commodityReferred" : commodityReferred,
				"vendor_Id" : vendor_Id
			}
		}, 'data')
	});
	
});