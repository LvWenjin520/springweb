

function cambiar_login() {
    document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_login";  
	document.querySelector('.cont_form_login').style.display = "block";
	document.querySelector('.cont_form_sign_up').style.opacity = "0";               
	setTimeout(function(){  document.querySelector('.cont_form_login').style.opacity = "1"; },400);  
	setTimeout(function(){    
	document.querySelector('.cont_form_sign_up').style.display = "none";
	},200); 
	warningMsg();
}

function cambiar_sign_up(at) {
    document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_sign_up";
    document.querySelector('.cont_form_sign_up').style.display = "block";
    document.querySelector('.cont_form_login').style.opacity = "0";
	setTimeout(function(){  document.querySelector('.cont_form_sign_up').style.opacity = "1";
	},100);  
	setTimeout(function(){   document.querySelector('.cont_form_login').style.display = "none";
	},400);
	//注册事件
	signupBtn();
}    


function ocultar_login_sign_up() {
	document.querySelector('.cont_forms').className = "cont_forms";  
	document.querySelector('.cont_form_sign_up').style.opacity = "0";               
	document.querySelector('.cont_form_login').style.opacity = "0"; 
	setTimeout(function(){
	document.querySelector('.cont_form_sign_up').style.display = "none";
	document.querySelector('.cont_form_login').style.display = "none";
	},500);  
}

//登录提示消息
function warningMsg(){
	var loginBtns = document.getElementsByClassName("btn_login");
	loginBtns[1].onclick=function(){
	var loginMsg = document.getElementsByClassName("login_msg");
		if(loginMsg[0].value == ""){
			alert("请输入用户名");
		}else if(loginMsg[0].value != "" && loginMsg[1].value == ""){
			alert("请输入密码");
		}else if(loginMsg[0].value == "" && loginMsg[1].value != ""){
			alert("请输入用户名");
		}else{
			$.ajax({
				type:"POST",
				url:"toLogin",
				data:{
					userName:loginMsg[0].value,
					passWord:loginMsg[1].value
				},
				cache:false,  //清理缓存
				async:true,
				success:function(result){
					if(result.flag == "success"){
						alert(result.flag);
						window.location.href="../"+result.path;
					}else{
						alert(result.flag);
					}
				}
			});
		}
	}
}

//注册按钮事件
function signupBtn(){
	var loginBtns = document.getElementsByClassName("btn_sign_up");
	loginBtns[1].onclick = function(){
		//注册信息
		var signupMsgs = document.getElementsByClassName("signupMsgs");
		if(signupMsgs[0].value == ""){
			alert("请输入用户名");
		}else if(signupMsgs[0].value != "" && signupMsgs[1].value == ""){
			alert("请输入密码");
		}else if(signupMsgs[0].value != "" && signupMsgs[1].value != "" && signupMsgs[1].value != signupMsgs[2].value){
			alert("请确认密码");
		}else if(signupMsgs[2].value == ""){
			alert("请确认密码");
		}else{
			$.ajax({
				type:"POST",
				url:"../signup/toSignUp",
				data:{
					username:signupMsgs[0].value,
					password:signupMsgs[1].value
				},
				cache:false,  //清理缓存
				async:true,
				success:function(result){
					if(result.flag == "注册成功"){
						alert("注册成功");
						//清理输入框中的数据
						signupMsgs[0].value = "";
						signupMsgs[1].value = "";
						signupMsgs[2].value = "";
					}else if(result.flag == "用户名重复"){
						alert("用户名重复");
						//清理输入框中的数据
						signupMsgs[0].value = "";
						signupMsgs[1].value = "";
						signupMsgs[2].value = "";
					}else{
						alert("系统异常，请联系管理员");
					}
				}
			});
		}
	}
}






