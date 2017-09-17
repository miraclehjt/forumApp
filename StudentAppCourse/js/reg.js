 castapp.init();

 var settings = app.getSettings();
 var regButton = document.getElementById('reg');
 var userIdBox = document.getElementById('userId');
 var userNameBox = document.getElementById('userName');
 var passwordBox = document.getElementById('password');
 var sexBox = document.getElementById('sex');
 var passwordConfirmBox = document.getElementById('password_confirm');
 var telBox = document.getElementById('tel');
 var emailBox = document.getElementById('email');
 //注册用户信息
 function regUserInfo() {
 	regButton.addEventListener('tap', function(event) {
 		var regInfo = {
 			userId: userIdBox.value,
 			password: passwordBox.value,
 			userName: userNameBox.value,
 			sex: sexBox.value,
 			phone: telBox.value,
 			email: emailBox.value
 		};
 		var passwordConfirm = passwordConfirmBox.value;
 		if(passwordConfirm != regInfo.password) {
 			plus.nativeUI.toast('密码两次输入不一致');
 			return;
 		}
 		if(regInfo.userId.length < 6) {
 			plus.nativeUI.toast('用户ID不能少于6个字符');
 			return;
 		}
 		if(regInfo.userName.length < 1) {
 			plus.nativeUI.toast('用户名不能少于1个字符');
 			return;
 		}
 		if(!checkEmail(regInfo.email)) {
 			plus.nativeUI.toast('邮箱地址不合法');
 			return;
 		}
 		if(!checkPhone(regInfo.phone)) {
 			plus.nativeUI.toast('电话号码格式不合法');
 			return;
 		}
 		savaUserMes();
 	});
 }
 //验证邮箱
 function checkEmail(email) {
 	email = email || '';
 	var em = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
 	return (em.test(email));
 }
 //验证电话号码
 function checkPhone(phone) {
 	phone = phone || '';
 	var re = /^1\d{10}$/;
 	return (re.test(phone));
 }
 //保存用户信息
 function savaUserMes() {
 	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/reg/appSaveNewUser.do';
 	mui.ajax(requestPath, {
 		data: {
 			'userId': userIdBox.value,
 			'userName': userNameBox.value,
 			'password': passwordBox.value,
 			'sex': sexBox.value,
 			'phone': telBox.value,
 			'email': emailBox.value
 		},
 		dataType: "json",
 		type: "post",
 		success: function(data) {
 			var reCode=data["reCode"];
 			/*var jsonData = JSON.parse(data);
 			var reCode = jsonData.reCode;
 			var reMsg = jsonData.reMsg;*/
 			//ar passwordConfirm = passwordConfirmBox.value;
 			if(reCode == 1) {
 				alert("输入的ID已经存在");
 			} else if(reCode == 2) {
 				alert("注册成功");
 				ca.newInterface({
 					url: 'login.html',
 					id: 'login',
 					show: {
 						aniShow: 'pop-in'
 					}
 				});
 			} else {
 				alert("注册失败");
 			}
 		},
 		error: function(data) {
 			console.log("网络错误，请求失败");
 		}
 	});
 }
 $(document).ready(function() {
 	regUserInfo();
 });