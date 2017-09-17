function initBusinesses(mkActBusise) {
	var html = "";
	var html_pop = "";
	var j = 0;
	for(var i = 0; i < mkActBusise.length; i++) {
		var business = mkActBusise[i];
//		if(i == 0) {
//			html = html + '<li id="' + business["mkActBusId"] + '" class="business_li business_li_checked">';
//			html = html + '<div class="business business_checked">';
//			html = html + '<div class="business_name">';
//			html = html + business["mkActBusName"];
//			html = html + '</div>';
//			html = html + '<div class="business_price">';
//			html = html + business["mkActBusPrice"];
//			html = html + '</div>';
//			html = html + '</div>';
//			html = html + '</li>';
//		} else {
//			html = html + '<li id="' + business["mkActBusId"] + '" class="business_li">';
//			html = html + '<div class="business business_unchecked">';
//			html = html + '<div class="business_name">';
//			html = html + business["mkActBusName"];
//			html = html + '</div>';
//			html = html + '<div class="business_price">';
//			html = html + business["mkActBusPrice"];
//			html = html + '</div>';
//			html = html + '</div>';
//			html = html + '</li>';
//		}
		
		html = html + '<li id="' + business["mkActBusId"] + '" class="business_li">';
		html = html + '<div class="business business_unchecked">';
		html = html + '<div class="business_name">';
		html = html + business["mkActBusName"];
		html = html + '</div>';
		html = html + '<div class="business_price">';
		html = html + business["mkActBusPrice"];
		html = html + '</div>';
		html = html + '</div>';
		html = html + '</li>';
		
		var mkActBusId = business["mkActBusId"];
		if (j == 0) {
			html_pop = html_pop + '<li id="pop_detail_' + mkActBusId + '" class="pop_detail_li">';
			html_pop = html_pop + '<div class="pop_table_cell"><div id="pop_triangle_01" class="triangle"></div></div><div class="pop_table_cell"><div id="pop_triangle_02" class="triangle" style="display:none;"></div></div><div class="pop_table_cell"><div id="pop_triangle_03" class="triangle" style="display:none;"></div></div>';
			html_pop = html_pop + '<div class="pop_detail">';
			html_pop = html_pop + business["mkActBusDesc"];
			html_pop = html_pop + '</div>';
			html_pop = html_pop + '</li>';
			if (i == (mkActBusise.length - 1)) {
				html = html + html_pop;
				html_pop = "";
				break;
			}
			j++;
			continue;
		}
		if (j == 1) {
			html_pop = html_pop + '<li id="pop_detail_' + mkActBusId + '" class="pop_detail_li">';
			html_pop = html_pop + '<div class="pop_table_cell"><div id="pop_triangle_01" class="triangle" style="display:none;"></div></div><div class="pop_table_cell"><div id="pop_triangle_02" class="triangle"></div></div><div class="pop_table_cell"><div id="pop_triangle_03" class="triangle" style="display:none;"></div></div>';
			html_pop = html_pop + '<div class="pop_detail">';
			html_pop = html_pop + business["mkActBusDesc"];
			html_pop = html_pop + '</div>';
			html_pop = html_pop + '</li>';
			if (i == (mkActBusise.length - 1)) {
				html = html + html_pop;
				html_pop = "";
				break;
			}
			j++;
			continue;
		}
		if (j == 2) {
			html_pop = html_pop + '<li id="pop_detail_' + mkActBusId + '" class="pop_detail_li">';
			html_pop = html_pop + '<div class="pop_table_cell"><div id="pop_triangle_01" class="triangle" style="display:none;"></div></div><div class="pop_table_cell"><div id="pop_triangle_02" class="triangle" style="display:none;"></div></div><div class="pop_table_cell"><div id="pop_triangle_03" class="triangle"></div></div>';
			html_pop = html_pop + '<div class="pop_detail">';
			html_pop = html_pop + business["mkActBusDesc"];
			html_pop = html_pop + '</div>';
			html_pop = html_pop + '</li>';
			if (i == (mkActBusise.length - 1)) {
				html = html + html_pop;
				html_pop = "";
				break;
			}
			html = html + html_pop;
			j = 0;
			html_pop = "";
		}
	}
	$("#business_list").html(html);
}

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
			
			var businessId = getBusinessId();

			$("li[id*='pop_detail_']").css("display", "none");
			$("#pop_detail_" + businessId).css("display", "block");
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
	
	$("#submitTransact").attr("disabled", "disabled");
	
	var url = SERVER_CONTEXT_PATH + "marketingdetailweb/transact.do";
	showLayer();
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
			var businessLogId = data['businessLogId'];
			getTransactResult(businessLogId);
//			var ret = data["ret"];
//			var msg = data["msg"];
//			if ("1" == ret) {
//				swal(msg);
//				phonenumber = $("#phonenumber").val('');
//				verifycode = $("#verifycode").val('');
//				showTransactSection();
//			} else {
//				swal(msg);
//			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			swal("业务办理受理失败！");
			$("#submitTransact").removeAttr("disabled");
		}
	});
	
}

var fTimeId;

function getTransactResult(businessLogId) {
	var f = "getBusinessSuccessLog(\'" + businessLogId + "\')";
	fTimeId = window.setInterval(f, 500);
	window.setTimeout(function(){
		window.clearInterval(fTimeId);
		$("#submitTransact").removeAttr("disabled");
		phonenumber = $("#phonenumber").val('');
		verifycode = $("#verifycode").val('');
		hideLayer();
		$("#popcover").css("display", "none");
		$("#transact_section").css("display", "none");
	}, 10000);
}

function getBusinessSuccessLog(businessLogId){
	var url = SERVER_CONTEXT_PATH + "marketingdetailweb/getBusinessSuccessLog.do";
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"businessLogId": businessLogId
		},
		success:function(data, textStatus) {
			var businessSuccessLog = data['data'];
			showSuccessLog(businessSuccessLog);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function showSuccessLog(businessSuccessLog) {
	if (businessSuccessLog == null){
		return;
	}
	var state = businessSuccessLog['state'];
	if (state == 1) {
		window.clearInterval(fTimeId);
		hideLayer();
		swal("业务办理成功！");
		$("#submitTransact").removeAttr("disabled");
		$("#phonenumber").val('');
		$("#verifycode").val('');
		$("#popcover").css("display", "none");
		$("#transact_section").css("display", "none");
	} else {
		window.clearInterval(fTimeId);
		hideLayer();
		swal("业务办理失败！");
		$("#submitTransact").removeAttr("disabled");
		$("#phonenumber").val('');
		$("#verifycode").val('');
		$("#popcover").css("display", "none");
		$("#transact_section").css("display", "none");
	}
}

var mask;
function showLayer(){
	mask = layer.load(1, {
		shade: 0.3
	}); //0代表加载的风格，支持0-2
}

function hideLayer(){
	layer.close(mask);
}

var staffId;
var mkActId;
var mkActBusId;
var phonenumber;
var verifycode;

var totalTime = 60;
var btnSendCode;
$(document).ready(function(){
	initBusinesses(data['mkActBusise']);
	initBusinessList();
	
	$("#viewMore").click(function(){
		$("#mkActBusDesc").css("display", "none");
		$("#viewMore").css("display", "none");
		$("#mkActBusDescMore").css("display", "block");
	});
	
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
	
	$("#transactBtn").click(function(){
		transact();
	});
	
	$("#transact_section").click(function(){
		
	});
	
	$("#transact_img_close").click(function(){
		showTransactSection();
	});
	
	$("#sendcode").click(function(){
		sendcode();
	});
	
	$("#submitTransact").click(function(){
		submitTransact();
	});
});