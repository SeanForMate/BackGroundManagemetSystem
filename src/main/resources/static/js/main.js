// 获取系统时间
var newDate = '';
getLangDate();
// 值小于10时，在前面补0
function dateFilter(date){
    if(date < 10){return "0"+date;}
    return date;
}
function getLangDate(){
    var dateObj = new Date(); //表示当前系统时间的Date对象
    var year = dateObj.getFullYear(); //当前系统时间的完整年份值
    var month = dateObj.getMonth()+1; //当前系统时间的月份值
    var date = dateObj.getDate(); //当前系统时间的月份中的日
    var day = dateObj.getDay(); //当前系统时间中的星期值
    var weeks = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var week = weeks[day]; //根据星期值，从数组中获取对应的星期字符串
    var hour = dateObj.getHours(); //当前系统时间的小时值
    var minute = dateObj.getMinutes(); //当前系统时间的分钟值
    var second = dateObj.getSeconds(); //当前系统时间的秒钟值
    var timeValue = "" +((hour >= 12) ? (hour >= 18) ? "晚上" : "下午" : "上午" ); //当前时间属于上午、晚上还是下午
    newDate = dateFilter(year)+"年"+dateFilter(month)+"月"+dateFilter(date)+"日 "+" "+dateFilter(hour)+":"+dateFilter(minute)+":"+dateFilter(second);
    document.getElementById("nowTime").innerHTML = "亲爱的"+window.localStorage.getItem("realName")+"，"+timeValue+"好！ 欢迎使用后台管理系统。当前时间为： "+newDate+"　"+week;
    setTimeout("getLangDate()",1000);
}

layui.use(['form','element','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        element = layui.element;
        $ = layui.jquery;
    // 上次登录时间【此处应该从接口获取，实际使用中请自行更换】
    document.getElementById("lastTime").innerHTML = "<marquee width=\"100%\" behavior=\"scroll\" direction=\"eft\" align=\"middle\">上一次登录时间："+window.localStorage.getItem('lastLoginDateTime')+"</marquee>";
    // icon动画
    $(".panel a").hover(function(){
        $(this).find(".layui-anim").addClass("layui-anim-scaleSpring");
    },function(){
        $(this).find(".layui-anim").removeClass("layui-anim-scaleSpring");
    })
    $(".panel a").click(function(){
        parent.addTab($(this));
    })
    
    // 填充数据方法
    function fillParameter(data){
        // 判断字段数据是否存在
        function nullData(data){
            if(data == '' || data == "undefined"){
                return "未定义";
            }else{
                return data;
            }
        }
        $(".version").text(nullData(data.version));      			// 当前版本
        $(".author").text(nullData(data.author));        			// 开发作者
        $(".homePage").text(nullData(data.homePage));    // 网站首页
        $(".server").text(nullData(data.server));       			// 服务器环境
        $(".dataBase").text(nullData(data.dataBase));			//数据库版本
        $(".maxUpload").text(nullData(data.maxUpload));  //最大上传限制
        $(".userRights").text(nullData(data.userRights));		//当前用户权限
    }

    // 最新文章列表
    $.get("static/json/newsList.json",function(data){
        var hotNewsHtml = '';
        for(var i=0;i<9;i++){
            hotNewsHtml += '<tr>'
                +'<td align="left"><a href="javascript:;"> '+data.data[i].newsName+'</a></td>'
                +'<td>'+data.data[i].newsTime.substring(0,10)+'</td>'
                +'</tr>';
        }
        $(".hot_news").html(hotNewsHtml);
        $(".userAll span").text(data.length);
    })
    
    $(".layui-carousel-ind ul li").hover(function(){
    	// 当前的选中，其他的取消
        $(this).addClass("layui-this").siblings().removeClass("layui-this");
    })
    
    $(".layui-carousel-ind ul li").mouseover(function(){
    	if($(this).index()==0){
    		$(this).parent().parent().prev().children(":first").addClass("layui-this");
    		$(this).parent().parent().prev().children(":last").removeClass("layui-this");
    	}else{
    		$(this).parent().parent().prev().children(":last").addClass("layui-this");
    		$(this).parent().parent().prev().children(":first").removeClass("layui-this");
    	}
    })
    
    
    var myChart = echarts.init(document.getElementById('dataShow'));
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['出货量','入库量','销售金额']
        },
        xAxis: [
            {
                type: 'category',
                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '量数',
                min: 0,
                max: 100,
                interval: 10,
                axisLabel: {
                    formatter: '{value} 万'
                }
            },
            {
                type: 'value',
                name: '金额',
                min: 0,
                max: 1000,
                interval: 100,
                axisLabel: {
                    formatter: '{value} 万/￥'
                }
            }
        ],
        series: [
            {
                name:'出货量',
                type:'bar',
                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6, 62.2, 32.6, 20.0, 6.4, 3.3]
            },
            {
                name:'入库量',
                type:'bar',
                data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 75.6, 82.2, 48.7, 18.8, 6.0, 2.3]
            },
            {
                name:'销售金额',
                type:'line',
                yAxisIndex: 1,
                data:[40, 100, 150, 250, 330, 850, 703, 934, 530, 265, 120, 62]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

})
