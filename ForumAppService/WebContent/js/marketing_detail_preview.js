function initBusinessList() {
	$("#business_list").children("li").each(function(){
		$(this).click(function(){
			var business_list = document.getElementById("business_list");
			var businesses = business_list.getElementsByTagName("li");
			for (var i = 0; i < businesses.length; i++) {
				var business_li = businesses[i];
				if ($(this).attr("id") == business_li.id && !$(business_li).children("div[class*='business']").hasClass("business_checked")) {
					$(business_li).children("div[class*='business']").removeClass("business_unchecked");
					$(business_li).children("div[class*='business']").addClass("business_checked");
					$(business_li).addClass("business_li_checked");
				} else {
					$(business_li).children("div[class*='business']").removeClass("business_checked");
					$(business_li).children("div[class*='business']").addClass("business_unchecked");
					$(business_li).removeClass("business_li_checked");
				}
			}
		});
	});
}

function getBusinessId() {
	var value = $("#business_list li[class='business_li business_li_checked']").attr("id");
	if (value == null || value == '')
		value = '';
	return value;
}

function showQrcodeSection() {
	if ($("#qrcode_section").css("display") == "none") {
		$("#popcover").css("display", "block");
		$("#qrcode_section").css("display", "block");
	} else {
		$("#popcover").css("display", "none");
		$("#qrcode_section").css("display", "none");
	}
}

function showTransactSection() {
	if ($("#transact_section").css("display") == "none") {
		$("#popcover").css("display", "block");
		$("#transact_section").css("display", "block");
	} else {
		$("#popcover").css("display", "none");
		$("#transact_section").css("display", "none");
	}
}

function transact(){
	staffId = $("#staffId").val();
	mkActId = $("#mkActId").val();
	mkActBusId = getBusinessId();
	if (mkActBusId == null || mkActBusId == "") {
		swal("没有选择营销业务", "请先选择要办理的营销业务！");
		return;
	}
	showTransactSection();
}

function sendcode() {
	if (totalTime < 60) {
		return false;
	}
	staffId = $("#staffId").val();
	phonenumber = $("#phonenumber").val();
	mkActBusId = getBusinessId();
	if (phonenumber == null || phonenumber == "") {
		swal("没有填写手机号", "请先输入手机号！");
		return;
	}
	
	var url = SERVER_CONTEXT_PATH + "marketingdetailweb/sendVerifyCode.do";
	
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"staffId": staffId,
			"phoneNumber": phonenumber,
			"mkActBusId": mkActBusId
		},
		success:function(data, textStatus) {
			swal("验证码发送成功！");
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			swal("验证码发送失败！");
		}
	});
	
	var loginTimer = setInterval(function() {
		totalTime--;
		if (totalTime == 0) {
			btnSendCode.innerText = "获取验证码";
			clearInterval(loginTimer);
			totalTime = 60;
		} else {
			btnSendCode.innerText = totalTime + "秒后重发";
		}
	}, 1000);
}

function submitTransact() {
	staffId = $("#staffId").val();
	mkActId = $("#mkActId").val();
	mkActBusId = getBusinessId();
	phonenumber = $("#phonenumber").val();
	verifycode = $("#verifycode").val();
	if (phonenumber == null || phonenumber == "") {
		swal("没有输入手机号", "手机好不能为空，请先输入手机号！");
		return;
	}
	if (verifycode == null || verifycode == "") {
		swal("没有输入验证码", "验证码不能为空，请先输入验证码！");
		return;
	}
	
	var url = SERVER_CONTEXT_PATH + "marketingdetailweb/transact.do";
	
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"mkActId": mkActId,
			"mkActBusId": mkActBusId,
			"staffId": staffId,
			"phoneNumber": phonenumber,
			"verifyCode": verifycode
		},
		success:function(data, textStatus) {
			var ret = data["ret"];
			var msg = data["msg"];
			if ("1" == ret) {
				swal(msg);
				phonenumber = $("#phonenumber").val('');
				verifycode = $("#verifycode").val('');
				showTransactSection();
			} else {
				swal(msg);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			swal("业务办理失败！");
		}
	});
	
}

var staffId;
var mkActId;
var mkActBusId;
var phonenumber;
var verifycode;

var totalTime = 60;
var btnSendCode;
$(document).ready(function(){
	initBusinessList();
	
	btnSendCode = document.getElementById("sendcode");
	
	$("#title_marketing_qrcode").click(function(){
		showQrcodeSection();
	});
	
//	$("#qrcode_section").click(function(){
//		showQrcodeSection();
//	});
	
	$("#qrcode_img_close").click(function(){
		showQrcodeSection();
	});
	
//	$("#transactBtn").click(function(){
//		transact();
//	});
	
	$("#transact_section").click(function(){
		
	});
	
	$("#transact_img_close").click(function(){
		showTransactSection();
	});
	
//	$("#sendcode").click(function(){
//		sendcode();
//	});
	
//	$("#submitTransact").click(function(){
//		submitTransact();
//	});
});