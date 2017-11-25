$(function(){
	$("#login").click(loginActive);
	$("#count").blur(checkName);
	$("#password").blur(checkPassword);
	//验证注册
	$("#regist_button").click(registActive);
	$("#regist_username").blur(checkRegistName);
	$("#regist_password").blur(checkRegistPassword);
	$("#final_password").blur(checkConfirm);
});
function registActive(){
	var name=$("#regist_username").val().trim();
	var nick=$("#nickname").val().trim();
	var password=$("#regist_password").val().trim();
	var confirm=$("#final_password").val().trim();
	var n=checkRegistName()+checkRegistPassword()+checkConfirm();
	if(n!=3){
		return;
	}
	//创建一个对象
	var data={
			name:name,
			nick:nick,
			password:password,
			confirm:confirm
	};
	//发送给服务器
	$.ajax({
		url:"user/regist.do",
		type:"post",
		dataType:"json",
		data:data,
		success:function(msg){
			if(msg.state==0){
				//返回登录界面
				$("#back").click();
				var name=msg.data.name;
				$("#count").val(name);
				$("#password").focus();
				$("#regist_username").val(" ");
				$("#nickname").val(" ");
				$("#regist_password").val("");
				$("#final_password").val("");
			}else{
				var result=msg.message;
				if(msg.state==4){
					$("#regist_username").next().show().find("span").html(result);
				}else if(msg.state==3){
					$("#regist_password").next().show().find("span").html(result);
				}else{
					alert(result);
				}
			}
		},
		error:function(e){
			
		}
	});
}
function checkRegistName(){
	var name=$("#regist_username").val().trim();
	var rule=/^\w{4,10}$/;
	if(!rule.test(name)){
		$("#regist_username").next().show().find("span").html("4~8个字符！");
		return false;
	}else{
		$("#regist_username").next().hide();
		return true;
	}
}
function checkRegistPassword(){
	var password=$("#regist_password").val().trim();
	var rule=/^\w{6,10}$/;
	if(!rule.test(password)){
		$("#regist_password").next().show().find("span").html("6~10个字符！");
		return false;
	}else{
		$("#regist_password").next().hide().find("span").empty();
		return true;
	}
}
function checkConfirm(){
	var password=$("#regist_password").val();
	var confirm=$("#final_password").val();
	if(password&&confirm==password){
		$("#final_password").next().hide();
		return true;
	}else{
		$("#final_password").next().show().find("span").html("确认密码不一致!");
	}
}

function checkPassword(){
	var password=$("#password").val();
	var rule=/^\w{6,10}$/;
	if(!rule.test(password)){
		$("#password").next().html("6~10个字符！");
		return false;
	}else{
		$("#password").next().empty();
		return true;
	}
}
function checkName(){
	var name=$("#count").val();
	var rule=/^\w{4,10}$/;
	if(!rule.test(name)){
		$("#count").next().html("4~8个字符！");
		return false;
	}else{
		$("#count").next().empty();
		return true;
	}
}
function loginActive(){
	//获取用户输入的用户名和密码
	var name=$("#count").val();
	var password=$("#password").val();
	var n=checkName()+checkPassword();
	if(n!=2){
		return;
	}
	//data对象中key的属性名要与服务器控制器的参数名一致！
	var data={
			"name":name,
			"password":password
	}
	//发送到服务器端
	$.ajax({
		url:'user/login.do',
		data:data,
		type:'post',
		dataType:'json',
		success:function(msg){
			if(msg.state==0){
				//登陆成功！
				//var str="userId="+msg.data.id;
				//document.cookie=str;
				
				location.href="edit.html";
			}else{
				var result=msg.message;
				if(msg.state==2){
					$("#count").next().html(result);
				}else if(msg.state==3){
					$("#password").next().html(result);
				}else{
					alert(result);
				}
			}
		},
		error:function(){
			alert("通讯失败！");
		}
	});
}