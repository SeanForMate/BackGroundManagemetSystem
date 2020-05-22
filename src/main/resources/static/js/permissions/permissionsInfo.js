layui.use([ 'table', 'form', 'layer', 'jquery' ], function() {
    var table = layui.table,
        form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 初始化参数和方法调用
    showOneLevelMenuData();

    // 表格数据初始化
    table.render({
        elem: '#permissionsList'
        ,url:'api/v1/permissions/selectByPageIndexAndOther'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'firstMenuEnglishName', title:'一级菜单英文', align: 'center'}
            ,{field:'firstMenuName', title:'一级菜单中文', align: 'center'}
            ,{field:'firstMenuIcon', title:'一级菜单图标', align: 'center',templet: function(d){
                return "<i class=\"ok-icon\">"+d.firstMenuIcon+"</i>"
             }}
            ,{field:'secondMenuEnglishName', title:'二级菜单英文', align: 'center',templet: function(d){
                if(d.secondMenuEnglishName != null && d.secondMenuEnglishName != ""){
                    return d.secondMenuEnglishName;
                }
                return "";
             }}
            ,{field:'secondMenuName', title:'二级菜单中文', align: 'center',templet: function(d){
                if(d.secondMenuName != null && d.secondMenuName != ""){
                    return d.secondMenuName;
                }
                return "";
             }}
            ,{field:'secondMenuIcon', title:'二级菜单图标', align: 'center',templet: function(d){
                if(d.secondMenuIcon != null && d.secondMenuIcon !=""){
                    return "<i class=\"ok-icon\">"+d.secondMenuIcon+"</i>";
                }
                return "";
             }}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200, align: 'center'}
        ]]
        ,page: true
    });

    // 渲染一级菜单
    function showOneLevelMenuData(){
        $.ajax({
            url: "api/v1/permissions/selectFirstLevelMenu",
            type: "GET",
            data: null,
            dataType:"json",
            success: function(res){
                var firstLevelMenu = $("#firstMenuId");
                var contentText = "";
                if(res.code==0){
                    var data = res.data;
                    for(var i=0;i<data.length;i++){
                        contentText += "<option value=\""+data[i].firstMenuId+"\">"+data[i].firstMenuName+"</option>"
                    }
                }else{
                    contentText = "<option value=\"-1\">"+data+"</option>"
                }
                firstLevelMenu.append(contentText);
                form.render();
            }
        });
    }

    // 渲染二级菜单
    function showTwoLevelMenuData(firstMenuId){
        var secondLevelMenu = $("#secondMenuId");
        secondLevelMenu.html("");
        if(firstMenuId!=-1){
            $.ajax({
                url: "api/v1/permissions/selectSecondLevelMenu",
                type: "GET",
                data: {"firstMenuId":firstMenuId},
                dataType:"json",
                success: function(res){
                    var contentText = "";
                    var data = res.data;
                    if(res.code==0){
                        for(var i=0;i<data.length;i++){
                            console.log(data[i].secondMenuId);
                            console.log(data[i].secondMenuName);
                            if(data[i].secondMenuId != null && data[i].secondMenuId != undefined){
                                contentText += "<option value=\""+data[i].secondMenuId+"\">"+data[i].secondMenuName+"</option>";
                            }else{
                                contentText += "<option value=\"-1\">暂无数据</option>";
                            }
                        }
                    }else{
                        contentText = "<option value=\"-1\">"+data+"</option>";
                    }
                    secondLevelMenu.append(contentText);
                    form.render();
                }
            });
        }else{
            secondLevelMenu.append("<option value=\"-1\">请选择二级菜单</option>")
        }
        form.render();
    }


    // 监听一级菜单选择事件
    form.on("select(firstMenu)",function(data){
        // 将一级菜单选中的值获取二级菜单列表
        showTwoLevelMenuData(data.value);
    });

    // 监听二级菜单
    form.on("select(secondMenu)",function(data){
        if(data.value!=-1){
            table.reload("permissionsList", {
                where: {
                    secondMenuId: data.value
                },
                page : {
                    curr : 1
                    // 重新从第 1 页开始
                }
            }, 'data');
        }
    });

    // 头工具栏事件
    table.on('toolbar(permissionsList)', function(obj){
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
    table.on('tool(permissionsList)', function(obj) {
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