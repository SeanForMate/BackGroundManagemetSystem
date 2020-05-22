layui.use([ 'table', 'layer', 'jquery' ], function() {
    var table = layui.table,
        layer = layui.layer,
        $ = layui.$;

    // 表格数据初始化
    table.render({
        elem: '#roleList'
        ,url:'api/v1/role/selectByPageIndex'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'roleName', title:'角色名称', align: 'center'}
            ,{field:'roleRemark', title:'角色备注', align: 'center'}
            ,{field:'createDateTime', title:'创建时间', align: 'center'}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200, align: 'center'}
        ]]
        ,page: true
    });

    // 头工具栏事件
    table.on('toolbar(roleList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'deleteChoose':
                var data = checkStatus.data;
                var roleId = [];
                for(var i=0;i<data.length;i++){
                    roleId.push(data[i].roleId);
                }
                if(roleId.length>0){
                    // 根据选中的行删除
                    layer.confirm('确定删除选中的数据吗？', function(index) {
                        $.ajax({
                            url : 'api/v1/role/deleteRole',
                            type : 'DELETE',
                            contentType:"application/json",//设置请求参数类型为json字符串
                            data: JSON.stringify(roleId),
                            dataType : 'json',
                            success : function(data) {
                                if (data.code == 0) {
                                    layer.msg("删除成功",{icon:1});
                                    table.reload("roleList", {
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
            case 'add':
                // 跳转到添加页面
                var index = layer.open({
                    title : "添加角色信息",
                    type : 2,
                    area : [ "500px", "400px" ],
                    content : 'addRoleInfo',
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

    // 监听工具条
    table.on('tool(roleList)', function(obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function(index) {
                var roleData = [];
                roleData.push(data.roleId);
                $.ajax({
                    url : 'api/v1/role/deleteRole',
                    type : 'DELETE',
                    dataType : 'json',
                    contentType: "application/json",
                    data : JSON.stringify(roleData),
                    success : function(data) {
                        if (data.code == 0) {
                            layer.msg("删除成功",{icon:1});
                            table.reload("roleList", {
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
            // 打开编辑角色信息页面
            var index = layer.open({
                title : "修改角色信息",
                type : 2,
                area : [ "500px", "400px" ],
                content : 'updateRoleInfo?roleId='+data.roleId,
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