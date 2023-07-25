	//登录页图片切换
	var currentindex=1;
	
	$(function(){
		$("#flash").css("background-image","url("+$("#flash1").attr("bgUrl")+")");//设置banner背景颜色名称 这里的flash就是div的ID
	});		

	
	function changeflash(i) {	
		currentindex=i;
		for (j=1;j<=2;j++){//此处的2代表你想要添加的幻灯片的数量与下面的5相呼应
			if (j==i){
				$("#flash"+j).fadeIn("normal");
				$("#flash"+j).css("display","block");
				// $("#flash"+j).css("opacity",1);
				$("#f"+j).removeClass();
				$("#f"+j).attr("class","dq");
				//alert("#f"+j+$("#f"+j).attr("class"))
				$("#flash").css("background-image","url("+$("#flash"+j).attr("bgUrl")+")");
			}else{
				$("#flash"+j).css("display","none");
				// $("#flash"+j).css("opacity",0);
				$("#f"+j).removeClass();
				$("#f"+j).attr("class","no");
			}
		}
	}
	function startAm(){
		timerID = setInterval("timer_tick()",2000);//8000代表间隔时间设置
	}
	function stopAm(){
		clearInterval(timerID);
	}
	function timer_tick() {
		currentindex = currentindex >=2 ? 1 : currentindex + 1;//此处的5代表幻灯片循环遍历的次数
		changeflash(currentindex);
	}
	$(document).ready(function(){
		$(".flash_bar div").mouseover(function(){
			stopAm();
		}).mouseout(function(){
			startAm();
		});
		startAm();

		//获取当前页面的cookie对象
		getCookie("user");
	});
	
	// input
	$(function(){
		//鼠标焦点
		$(":input.user").focus(function(){
			$(this).addClass("userhover");                          
		}).blur(function(){
			$(this).removeClass("userhover")
		});
		$(":input.pwd").focus(function(){
			$(this).addClass("pwdhover");                          
		}).blur(function(){
			$(this).removeClass("pwdhover")
		});
		
		//输入用户名
		$(".user").focus(function(){
			var username = $(this).val();
			if(username == ''){
			   $(this).val('');
			}
		 });
		 $(".user").blur(function(){
			var username = $(this).val();
			if(username == ''){
			   $(this).val('');
			}
		 });
		 
		 //输入密码
		 $(".pwd").focus(function(){
			var password = $(this).val();
			if(password == ''){
			   $(this).val('');
			}
		 });
		 $(".pwd").blur(function(){
			var password = $(this).val();
			if(password == ''){
			   $(this).val('');
			}
		 });
	
	});

	//登录页面  用户点击登录  提交事件
	//对用户的账号和密码进行验证
	//1.账号密码不能为空
	//2.通过正则表达式进行非法字符过滤
	function checkLogin(){
		//清空提示信息
		$("#Msg").html("");

		var userName= $("#userName").val().trim();
		var userPwd = $("#userPwd").val().trim();

		//1.账号密码不能为空
		if(isEmpty(userName)){
			//提示用户不能为空
			$("#Msg").html("用户名不能为空");
			return;
		}

		//判断是否是用户名长度  8-16位
		// if(!(userName.length >=8 && userName.length<=16)){
		// 	$("#Msg").html("用户名长度为8-16位");
		// 	return;
		// }

		//用户名只能是字母数字 下划线 减号
		// var upattern = /^[a-zA-Z0-9_]{8,16}$/;
		//alert(upattern.test(userName));

		// if(!upattern.test(userName)){
		// 	$("#Msg").html("用户名必须是字母数字下划线");
		// 	return;
		// }

		//密码不能为空
		if(isEmpty(userPwd)){
			$("#Msg").html("密码不能为空");
			return;
		}

		//密码长度  8-16位
		// if(!(userPwd.length >=8 && userPwd.length<=16)){
		// 	$("#Msg").html("密码长度为8-16位");
		// 	return;
		// }

		//密码至少一个大写字母  一个小写字母  一个数字  组成 8-16位
		// var pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/;
		// if(!pwdPattern.test(userPwd)){
		// 	$("#Msg").html("密码至少一个大写/小写/数字");
		// 	return;
		// }

		//复选框  记住密码   获取是否checked  为true把属性value改为remember
		//为false则属性默认为 forget
		var check = $("#savePassword").prop("checked");
		if(check){
			$("#savePassword").attr("value","remember");
		}else {
			$("#savePassword").attr("value","forget");
		}

		//全部通过  提交表单
		$("#loginForm").submit();
	}

	//点击重置按钮的响应事件
	function formReset(){
		$("#Msg").html("");
		$("#savePassword").attr("checked",false);
		$("#userName").val("");
		$("#userPwd").val("");
		$("#loginForm").reset();
	}

	//获取当前页面的cookie值
	function getCookie(cookieName){
		var cookies = document.cookie;
		var cookiesList = cookies.split(";");

		for(var i=0;i<cookiesList.length;i++){
			//alert(cookiesList[i]);
			//alert(cookieName);
			var values = cookiesList[i].split("=");
			if(values[0] == 'user'){
				//values[1]进行分割
				var res = values[1].split("-");
				//把结果res[0]  写入到 $("#userName")
				$("#userName").val(res[0]);
				//把结果res[1]  写入到 $("#userPwd")
				$("#userPwd").val(res[1]);
				//把记住我  选中
				$("#savePassword").prop("checked",true);
			}
		}

	}





