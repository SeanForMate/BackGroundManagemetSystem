layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;
    
    // 登录按钮
    form.on("submit(login)",function(data){
        $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        var $loginbtn =  $(this);
        $.ajax({
	          url: '/loginForm',
	          type: 'POST',
	          dataType: 'json',
	          data: data.field,
	          success: function (data) {
	        	 if(data.code==0){
	        		 // 将用户信息添加到
	        		  var user = data.data;
	        		  window.localStorage.setItem('userId', user.userId);
	        		  window.localStorage.setItem('userName', user.userName);
	        		  window.localStorage.setItem('realName', user.realName);
	        		  window.localStorage.setItem('lastLoginDateTime', user.lastLoginDateTime);
	        		  // 登录成功跳转到首页
	        		  window.location.href = "/index";
	        	  }else{
	        		  layer.msg(data.msg,{icon:2});
		        		 setTimeout(function(){
		        			 $loginbtn.text("登录").removeAttr("disabled").removeClass("layui-disabled");
		        	     },1000);
	        	  }
	          }
	    });
        return false;
    })

    // 表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
