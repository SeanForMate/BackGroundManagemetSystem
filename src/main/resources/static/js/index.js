// 全局正则表达式
var phoneVerify = /^1[3456789]\d{9}$/;	// 电话验证正则表达式
var numberAndLetters = /^[A-Za-z0-9]+$/;	// 数字和字母
var otherWindow = "";
var musicFlag = false;
var musicName = "";
var musicPath = "";
var musicImage = "static/images/face.jpg";
var musicDataList = [{name: 'name', artist: 'artist', url: 'url.mp3', cover: 'cover.jpg'}];
//初始化数据
$(function(){
	// 页面显示用户昵称
	$(".realName").text(window.localStorage.getItem('realName'));
	// 页面皮肤主题渲染
	$.ajax({
		url: "api/v1/skinTheme/selectSkinThemeById",
		type: "GET",
		dataType: "json",
		data: null,
		success: function(data){
			if(data.code==0){
				var skinTheme = data.data;
				// logo颜色
				$(".okadmin-logo").css("background",skinTheme.logoColor);
				// 顶部颜色
				$(".layui-header").css("background",skinTheme.topColor);
				$(".hideMenu").css("background",skinTheme.topColor);
				// 左部颜色
				$(".layui-side").css("background",skinTheme.leftColor);
			}
		}
	});
    // 获取音乐数据
    /*$.ajax({
        url: "api/v1/music/selectMusicByUserId",
        type: "GET",
        data: null,
        dataType: "json",
        success: function(data){
            if(data.code == 0){
                var musicData = data.data;
                for(var i=0;i<musicData.length;i++){
                    musicDataList.push(musicData[i]);
                }
            }
        }
    });*/
});

layui.use(['form','element','colorpicker','layer','upload','jquery'],function(){
	var form = layui.form,
		element = layui.element,
		colorpicker = layui.colorpicker,
        layer = parent.layer === undefined ? layui.layer : top.layer,
		upload = layui.upload,
		$ = layui.$;

    var ap = null;

	// 音乐点击事件
	$("#music").click(function () {
		if(musicDataList.length>0 && musicDataList != null){
            if(musicFlag==false){
                // 音乐播放器初始化
                ap = new APlayer({
                    container: document.getElementById('aplayer'),
                    fixed: true,
                    audio: musicDataList
                });
                // 播放
                ap.play();
                musicFlag = true;
            }else{
                // 清除
                ap.destroy();
                ap = null;
                musicFlag = false;
            }
		}else{
			layer.msg("音乐播放器中没有音乐！",{icon: 2});
		}
    });
	
	// 便签点击事件
	$("#messageShow").click(function(){
		var height = ($(window).height()-50)+"px";
		var x = ($(this).offset().left+($(this).width()*2)-300)+"px";
		var content = null;
		var notesText = window.localStorage.getItem("notesText");
		// 判断浏览器本地缓存中是否有便签内容
		if(notesText!="" && notesText!=null){
			content = notesText;
		}else{
			content = "便签中的内容会存储在本地，这样即便你关掉了浏览器，在下次打开时，依然会读取到上一次的记录，是个非常小巧实用的本地备忘录。";
		}
		layer.open({
	        type: 1
	        ,title: ["便签","color:#FFF;background:#FF5722;"]
	        ,offset: ["50px",x]
		    ,shade: 0
		    ,closeBtn: 1
	        ,id: 'msgWindow' //防止重复弹出
	        ,area: ['300px', "200px"]
			,anim: 1
			,resize: false
			,move: false
	        ,content: "<textarea placeholder=\"内容\" style=\"display: block;width: 300px;height: 157px;min-width: 300px;min-height: 157px;line-height: 20px;padding: 10px 20px;border: none;box-sizing: border-box;color: #666;word-wrap: break-word;\">"+content+"</textarea>"
	        ,cancel: function(index, layero){
	        	// 将便签中的内容存到浏览器本地缓存里
	        	window.localStorage.setItem("notesText",$(layero).find("textarea").val());
	        }
	      });
	});
	
	// 切换皮肤
	$("#skin").click(function(){
		var skinWindows = null;
		var height = ($(window).height()-50)+"px";
		var skinData = null;
		/*皮肤主题*/
		var content = "<div id=\"skinTheme\" class=\"layui-layer-content\">"
							  +"<div class=\"layui-card-header\">皮肤主题</div>"
							  +"<div class=\"layui-card-body layadmin-setTheme\">";
		$.ajax({
	          url: 'api/v1/skinTheme/selectSkinTheme',
	          type: 'GET',
	          dataType: 'json',
	          data: null,
	          success: function (data) {
	        	  if(data.code==0){
	        		 skinData = data.data;
	        		 content += "<ul class=\"layadmin-setTheme-color\" style=\"margin:5px;\">"
	        		 // 添加皮肤主题
	        		 for(var i=0;i<skinData.length;i++){
	        			 content += "<li data-index=\""+i+"\" data-alias=\""+skinData[i].skinName+"\" title=\""+skinData[i].skinName+"\">"
	        			 						+"<div class=\"layadmin-setTheme-header\" style=\"background-color: "+skinData[i].topColor+";\"></div>"
	        			 						+"<div class=\"layadmin-setTheme-side\" style=\"background-color: "+skinData[i].leftColor+";\">"
	        			 						+"<div class=\"layadmin-setTheme-logo\" style=\"background-color: "+skinData[i].logoColor+";\"></div>"
	        			 						+"</div> "
	        			 					+"</li>"
	        		 }
	        		 content += "<li data-index=\"-1\"  data-alias=\"添加\" title=\"添加\" style=\"font-size:28px;line-height:50px;text-align:center;color:#000;\">+</li>";
	        		 content += "</ul>"
	        	 }else if(data.code==1){
	        		 content += "<div style=\"height:30px;line-height:30px;font-size:24px;color:red;text-align:center;padding-top:50px;\">"+data.data+"!</div></div>"
	        	 }else{
	        		 content += "<div style=\"height:30px;line-height:30px;font-size:24px;color:red;text-align:center;padding-top:50px;\"><i class=\"layui-icon layui-icon-404\" style=\"font-size: 30px;\"></i>"+data.data+"!</div></div>"
	        	 }
	        	 // 打开皮肤主题窗口
	        	 skinWindows = layer.open({
	 	 	        type: 1
	 	 	        ,title: false
	 	 	        ,offset: "rb"
	 	 	        ,closeBtn: 0
	 	 		    ,shade: 0.1
	 	 		    ,shadeClose: true
	 	 	        ,id: 'skinWindow' //防止重复弹出
	 	 	        ,area: ['310px', height]
	 	 			,anim: 2
	 	 			,resize: false
	 	 			,move: false
	 	 	        ,content: content
	 	 	        ,success: function(layero,index){
	 	 	        	layero.find(".layadmin-setTheme-color li").click(function(){
	 	 	        		// 获取编号
	 	 	        		var skinIndex = $(this).attr("data-index");
	 	 	        		// 判断编号是否大于等于0，如果是就修改，否则就添加皮肤；
	 	 	        		if(skinIndex>=0){
	 	 	        			var skinThemeId = skinData[skinIndex].skinThemeId;
                                // logo颜色
                                $(".okadmin-logo").css("background",skinData[skinIndex].logoColor);
		 	 	        		// 左部颜色
		 						$("#leftBg").css("background",skinData[skinIndex].leftColor);
		 	 	        		// 顶部颜色
		 						$(".layui-header").css("background",skinData[skinIndex].topColor);
		 						$(".hideMenu").css("background",skinData[skinIndex].topColor);
		 						// 修改主题
		 						$.ajax({
		 							url: "api/v1/skinTheme/updateSkinThemeById",
		 							type: "POST",
		 							dataType: "json",
		 							data: {"skinThemeId":skinThemeId},
		 							success: function(data){
		 								if(data.code!=0){
		 									layer.msg(data.msg,{icon:2});
		 								}
		 							}
		 						});
	 	 	        		}else{
	 	 	        			var skinContent = "<div style=\"padding:30px 50px 0 40px;\">" +
																"<div class=\"layui-form-item\">" +
															    	"<label class=\"layui-form-label\">皮肤主题</label>" +
															    	"<div class=\"layui-input-block\">" +
															    		"<input type=\"text\" id=\"skinName\" name=\"title\" lay-verify=\"required\" placeholder=\"请输入皮肤主题名称\" autocomplete=\"off\" class=\"layui-input\"/>" +
															    	"</div>" +
															  	"</div>" +
																"<div class=\"layui-form-item\">" +
																	"<label class=\"layui-form-label\">Logo</label>" +
																	"<div class=\"layui-input-block\">" +
																		"<div id=\"logoColor\"></div>" +
																	"</div>" +
																"</div>" +
							 	 	        					"<div class=\"layui-form-item\">" +
																    "<label class=\"layui-form-label\">顶部</label>" +
																    "<div class=\"layui-input-block\">" +
																    	"<div id=\"topColor\"></div>" +
																    "</div>" +
																"</div>" +
																"<div class=\"layui-form-item\">" +
																    "<label class=\"layui-form-label\">左边</label>" +
																    "<div class=\"layui-input-block\">" +
																    	"<div id=\"leftColor\"></div>" +
																    "</div>" +
																"</div>" +
						 	 	        					"</div>";
	 	 	        			// 添加皮肤弹窗
								var logoChooseColor = null;
	 	 	        			var topChooseColor = null;
	 	 	        			var leftChooseColor = null;
	 	 	        			var colorWindow = layer.open({
	 	 	        				 type: 1
		 	 	   	 	 	        ,title: ["添加皮肤","background:#dddddd"]
		 	 	   	 	 	        ,offset: "auto"
		 	 	   	 	 		    ,shade: 0
		 	 	   	 	 		    ,btn: ['添加', '应用','预览','取消']
	 	 	        			 	,btnAlign: 'c'
		 	 	   	 	 	        ,id: 'addSkinWindow' //防止重复弹出
		 	 	   	 	 	        ,area: ["350px", "300px"]
		 	 	   	 	 			,anim: 3
		 	 	   	 	 			,resize: false
		 	 	   	 	 			,move: false
		 	 	   	 	 	        ,content: skinContent
		 	 	   	 	 	        ,success: function(layero,index){
		 	 	   	 	 	        	// 关闭皮肤主题选择窗口
		 	 	   	 	 	        	layer.close(skinWindows);
										// Logo背景颜色选择器
                                        colorpicker.render({
                                            elem: '#logoColor'  // 绑定元素
                                            ,format: 'rgb'
                                            ,predefine: true
                                            ,alpha: true
                                            ,done: function(color){
                                                logoChooseColor = color;
                                            }
                                        });
					 	 	   	 	 	// 顶部背景颜色选择器
					 	 	   	 	 	colorpicker.render({
				 	 	   	 	 	 	    elem: '#topColor'  // 绑定元素
					 	 	   	 	 	 	,format: 'rgb'
					 	 	   	 	 	    ,predefine: true
					 	 	   	 	 	    ,alpha: true
					 	 	   	 	 	    ,done: function(color){
					 	 	   	 	 	    	topChooseColor = color;
					 	 	   	 	 	    }
				 	 	   	 	 	 	});
				 	 	   	 	 	 	// 左边背景颜色选择器
				 	 	   	 	 	 	colorpicker.render({
				 	 	   	 	 	 	    elem: '#leftColor'  // 绑定元素
				 	 	   	 	 	 	    ,format: 'rgb'
					 	 	   	 	 	    ,predefine: true
					 	 	   	 	 	    ,alpha: true
					 	 	   	 	 	    ,done: function(color){
					 	 	   	 	 	    	leftChooseColor = color;
					 	 	   	 	 	    }
				 	 	   	 	 	 	});
		 	 	   	 	 	        }
		 	 	        			,yes: function(index, layero){
		 	 	        				// 获取主题名称
		 	 	        				var  skinName = $(layero).find("#skinName").val();
		 	 	        			    // 添加按钮的回调
		 	 	        				if(skinName!=null && skinName!="" && logoChooseColor!=null && topChooseColor!=null && leftChooseColor!=null){
		 	 	        					$.ajax({
			 	 	        					url: "api/v1/skinTheme/insertSkinTheme",
			 	 	        					type: "POST",
			 	 	        					dataType: "json",
			 	 	        					data: {"skinName":skinName,"logoColor":logoChooseColor,"topColor":topChooseColor,"leftColor":leftChooseColor},
			 	 	        					success: function(data){
			 	 	        						if(data.code==0){
			 	 	        							layer.close(colorWindow);
			 	 	        						}else{
			 	 	        							layer.msg(data.msg,{icon:2});
			 	 	        						}
			 	 	        					}
			 	 	        				});
		 	 	        				}else{
		 	 	        					layer.msg("颜色不能为空，请选择颜色！",{icon:2});
		 	 	        				}
		 	 	        			  }
		 	 	        			  ,btn2: function(index, layero){
		 	 	        				// 获取主题名称
		 	 	        				var  skinName = $(layero).find("#skinName").val();
		 	 	        			    // 应用按钮的回调
		 	 	        				if(skinName!=null && skinName!="" && topChooseColor!=null && leftChooseColor!=null){
		 	 	        					$.ajax({
			 	 	        					url: "api/v1/skinTheme/insertSkinTheme",
			 	 	        					type: "POST",
			 	 	        					dataType: "json",
			 	 	        					data: {"skinName":skinName,"topColor":topChooseColor,"leftColor":leftChooseColor},
			 	 	        					success: function(data){
			 	 	        						if(data.code==0){
				 	 	        						// 设置左部颜色
				 	 	  	 	 	        			$("#leftBg").css("transition","none 0s ease 0s");
				 	 	  		 						$("#leftBg").css("background",leftChooseColor);
				 	 	  		 	 	        		// 设置顶部颜色
				 	 	  		 						$(".layui-header").css("background",topChooseColor);
				 	 	  		 						$(".hideMenu").css("background",topChooseColor);
				 	 	  		 						// 关闭弹窗
				 	 	  		 						layer.close(colorWindow);
				 	 	  		 						// 加上延迟效果
				 	 	  		 						setTimeout(function(){
				 	 	  		 							$("#leftBg").css({"transition":"all 0.3s ease-in-out","-webkit-transition":"all 0.3s ease-in-out","-o-transition":"all 0.3s ease-in-out","-moz-transition":"all 0.3s ease-in-out"});
				 	 	  		 						},1000);
			 	 	        						}else{
			 	 	        							layer.msg(data.msg,{icon:2});
			 	 	        						}
			 	 	        					}
			 	 	        				});
		 	 	        				}else{
		 	 	        					layer.msg("颜色不能为空，请选择颜色！",{icon:2});
		 	 	        				}
		 	 	        			  }
		 	 	        			  ,btn3: function(index, layero){
		 	 	        			    // 预览按钮的回调
		 	 	        				
		 	 	        				return false;//开启该代码可禁止点击该按钮关闭
		 	 	        			  }
		 	 	        			  ,btn4: function(index, layero){
		 	 	        			    // 取消按钮的回调
		 	 	        				layer.close(colorWindow);
		 	 	        			  }
		 	 	        		});
	 	 	        		}
	 	 	        	})
	 	 	        }
	 	 	      });
	          }
	    });
	});
	
	// 其他功能组件点击事件
	$("#otherShow").click(function(){
        var content = "<div class=\"otherShowWindow\" class=\"layui-layer-content\">"
            				+"<div class=\"layui-card-header\" style=\"background:#1E9FFF;\">其他功能</div>"
            				+"<div class=\"layui-card-body\">"
								+"<div style=\"padding: 10px;\">"
            						+"<div class=\"\"  style=\"font-size: 16px;\">"
										+"<span style=\"display: inline-block;width:80%;text-align: center;\">音乐添加(Add Music)</span><span style=\"display: inline-block;width:20%;text-align: center;\"><i id=\"musicAdd\" class=\"ok-icon ok-icon-add\"  style=\"font-size: 18px;color: #009688;cursor: pointer;\"></i></span>"
									+"</div>"
								+"</div>"
							+"</div>"
						+"</div>";
		var height = ($(window).height()-50)+"px";
        otherWindow = layer.open({
	        type: 1
	        ,title: false
	        ,offset: "rb"
		    ,shade: 0.1
            ,closeBtn: 0
            ,shadeClose: true
	        ,id: 'otherWindow' // 防止重复弹出
	        ,area: ['250px', height]
			,anim: 5
			,resize: false
			,move: false
			,time: 50000	 // 50秒后自动关闭
	        ,content: $("#otherShowWindow")
	        ,yes: function(){
	          layer.closeAll();
	        }
	      });
	});

	// 添加音乐
	$("body").on("click","#musicAdd",function(){
		// 打开新的添加音乐页面
		layer.close(otherWindow);
		var addMusicWindow = layer.open({
			type: "1",
			title: ["添加音乐","background:#009688;color:#FFFFFF;"],
            id: 'musicAddWindow', // 防止重复弹出
            area: ['600px', '350px'],
			closeBtn: 1,
			resize: false,
            anim: 5,
			content: $('#addMusicWindows'),
			success: function(layero, index){
				// 立即提交按钮事件
                layero.find("#submitMusic").on("click",function(){
                	// 判断
                    var musicName = $(layero).find('#musicName').val();
                    var musicArtist = $(layero).find('#musicArtist').val();
                    if(musicArtist == ""){
                        musicArtist = "Artist";
					}
					if(musicName != ""){
						if(musicPath == ""){
							layer.msg("请上传音频文件再提交！！！",{icon: 2});
						}else{
							$.ajax({
								url: "api/v1/music/insertMusicByUserId",
								type: "POST",
								data:{"musicName":musicName,"musicArtist":musicArtist,"musicPath":musicPath},
								dataType:"json",
								success: function(res){
									if(res.code == 0){
										if(ap != null && ap != undefined){
                                            ap.list.add([{
                                                name: musicName,
                                                artist: musicArtist,
                                                url: musicPath,
                                                cover: musicImage
                                            }]);
										}
                                        layer.msg(res.data,{icon:1});
										// 关闭这个弹出
										layer.close(addMusicWindow);
									}else if(res.code == 1){
										layer.msg(res.data,{icon:2});
									}else{
										layer.msg(res.data,{icon:2});
									}
								}
							});
						}
					}else{
                        layer.msg("请输入音乐名字！！！",{icon: 2});
					}
				});
			}
		});
	});

    // 创建一个上传音乐组件
    upload.render({
        elem: "#uploadMusic"
        ,url: "api/v1/music/uploadMusic"
        ,acceptMime: "audio/*"
		,accept: "audio"	// 上传类型
		,exts: "mp3"		// 文件后缀
		,choose: function(obj){
            //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
            obj.preview(function(index, file, result){
                musicName = file.name; //得到文件名
            });
		}
        ,done: function(res, index, upload){ //上传后的回调
			if(res.code == 0){
                musicPath = res.data;
                $("#musicShowText").text(musicName);
			}else if(res.code == 2){
                $("#musicShowText").text(res.data);
			}
        }
        ,error: function(index, upload){
            //上传失败时
            layer.msg("上传失败，请联系管理员！",{icon: 5});
        }
    });
});