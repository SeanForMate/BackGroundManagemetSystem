layui.use([ 'form', 'layer', 'jquery' ], function() {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 初始化方法调用
    showRoleData();

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

    // 渲染角色信息
    function showRoleData(){
        var array = new Array();  //存放分割后的字符串
        array = urlArgs();
        $.ajax({
            url: "api/v1/role/searchRole",
            type: "GET",
            dataType: 'json',
            data: array,
            success: function (data) {
                if(data.code==0){
                    var roleData = data.data;
                    for(var key in roleData){
                        if(key=="roleRemark"){
                            $("#roleRemark").html(roleData[key]);
                        }else{
                            $("input[name="+key+"]").val(roleData[key]);
                        }
                    }
                }else{
                    layer.msg(data.data,{icon:2})
                }
                form.render();
            }
        });
    }

    // 表单提交
    form.on("submit(submitBtn)",function(data){
        var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "api/v1/role/updateRole",
            type: "PUT",
            contentType:"application/json",//设置请求参数类型为json字符串
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (res) {
                if(res.code==0){
                    setTimeout(function(){
                        parent.layer.close(index);
                        top.layer.msg(res.data,{icon: 1});
                        layer.closeAll("iframe");
                        // 刷新父页面
                        parent.location.reload();
                    },500);
                }else{
                    layer.close(index);
                    layer.msg(res.data,{icon: 2});
                }
            }
        });
        return false;
    });


});