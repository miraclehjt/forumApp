//初始化全局castapp.js
mui.init({
	//监听Android手机的back、menu按键
	keyEventBind: {
		backbutton: false, //Boolean(默认true)关闭back按键监听
	},
});
castapp.init();
var loginButton =castapp.id('login'); //登录按钮\
/*var userName = castapp.id('userName').value; //用户名输入框
var passWord = castapp.id('password').value; //密码框*/
var autoLoginButton = castapp.id("autoLogin"); //自动登录按钮
var regButton = castapp.id('regNm'); //注册按钮
var forgetButton = castapp.id('forgetPassword'); //忘记密码按钮
//填写动态密码后登录app
ca.click(loginButton, function() {
var userName = castapp.id('userName').value; //用户名输入框
var passWord = castapp.id('password').value; //密码框
	if(userName == "" || userName.length < 5) {
		ca.prompt('请输入正确帐号');
		return false;
	}
	if(passWord == "" || passWord.length < 6 || passWord == "请输入密码") {
		ca.prompt("请输入正确密码！");
		return false;
	}
	castapp.showWaiting('正在登录...');
	ca.ajax({
		url: common.SERVICE_PREFIX_ADDRESS + "app/login/loginApp.do",
		type: 'get',
		data: {
			"userName": userName,
			"passWord": passWord
		},
		succFn: function(data) {
			var jsonData = JSON.parse(data); 
			var retCode = jsonData.retCode;
			var retMsg = jsonData.retMsg;
			if(retCode == 0) {
				//登录成功后，将数据放到本地存储中，供全局使用
				console.log(data);
				localStorage.setItem("publicStr", data);
				setStaffId2LS(userName);
				var arrayData = [{
					url: 'main.html',
					id: 'footer'
				}];
				castapp.preLoad(arrayData, function(data) {
						setTimeout(function() {
							castapp.closeWaiting();
							ca.newInterface({
								url: 'main.html',
								id: 'footer',
								styles: {
									top: '0px',
									bottom: '0'
								},
								showType: 'zoom-fade-out',
								showTime: '300'
							});
						}, 2000);
					});
				} else {
				castapp.closeWaiting();
				mui.alert(retMsg,"登录失败");
			}
		},
		errFn: function(data) {
			castapp.closeWaiting();
			ca.prompt("由于网络问题登录失败，请稍候再试！");
		}
	});
});

function setStaffId2LS(userName) {
	var logedStaffList = [];
	var logedStaffListStr = "";
	try {
		logedStaffList = (localStorage.getItem("logedStaffList")).split(',');
		var listLength = logedStaffList.length;
		while(listLength--) {
			if(userName == logedStaffList[listLength]) {
				logedStaffList.splice(listLength, 1);
				logedStaffList.push(userName);
				logedStaffListStr = logedStaffList.toString();
				localStorage.setItem('logedStaffList', logedStaffListStr);
				return;
			}
		}
		logedStaffList.push(userName);
		logedStaffListStr = logedStaffList.toString();
		localStorage.setItem('logedStaffList', logedStaffListStr);
	} catch(e) {
		logedStaffList = [];
		logedStaffList.push(userName);
		logedStaffListStr = logedStaffList.toString();
		localStorage.setItem('logedStaffList', logedStaffListStr);
	}
}

function setStaffId2Input() {
	try {
		var logedStaffList = (localStorage.getItem("logedStaffList")).split(',');
		var inputText = logedStaffList.pop();
		var staffIdInput = castapp.id("userName").value = inputText;
	} catch(e) {}
}
//打开注册页面
function openReg(){
	regButton.addEventListener('tap', function(event) {
		ca.newInterface({
				url: 'reg.html',
				id: 'reg',
				preload: true,
				show: {
					aniShow: 'pop-in'
				},
				styles: {
					popGesture: 'hide'
				},
				waiting: {
					autoShow: false
				}
			});
		}, false);
}
//打开忘记密码页面 
function forgetPassWord(){
		forgetButton.addEventListener('tap', function(event) {
			ca.newInterface({
				url: 'forget_password.html',
				id: 'forget_password',
				preload: true,
				show: {
					aniShow: 'pop-in'
				},
				styles: {
					popGesture: 'hide'
				},
				waiting: {
					autoShow: false
				}
			});
		}, false);
}
$(document).ready(function(){
	mui.plusReady(function() {
		var currentVersion = plus.runtime.version;
		/*showUpdate(currentVersion);*/
	});
	//setStaffId2Input();
	openReg();
	forgetPassWord();
	
});