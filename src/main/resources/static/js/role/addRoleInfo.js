layui.use([ 'form', 'layer', 'jquery' ], function() {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 表单提交
    form.on("submit(submitBtn)",function(data){
        var index = parent.layer.msg('数据提交中，请稍候！',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "api/v1/role/insertRole",
            type: "POST",
            data: data.field,
            dataType: 'json',
            success: function (res) {
                if(res.code==0){
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
        return false;
    });


});