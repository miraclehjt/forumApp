function setIframeHeight(obj) {
	try {
		var iframe = document.getElementById(obj);
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		var height = Math.max(bHeight, dHeight);
		iframe.style.height = height + "px";
	} catch (e) {}
}
var SERVER_CONTEXT_PATH = "http://localhost:8090/ForumAppService";

var IMAGE_PREFIEX_ADDRESS = "http://localhost:8090/rsmanager/upload";

var common = {
	
	SERVICE_PREFIX_ADDRESS : SERVER_CONTEXT_PATH + "/manages/",
	
	openPage : function(url) {
		$("#indexiframe").attr("src", url);
	}
};

/**
 * 格式化时间，显示年月日 2016-12-20
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function formatterDateYMD(cellvalue, options, rowObject) {
	if (cellvalue != null) {
		var a=new Date(cellvalue);
		s = a.getFullYear() + "-";
		s+=("0"+(a.getMonth()+1)).slice(-2) + "-";
		s+= ("0"+a.getDate()).slice(-2);
		return s;
	} else {
		return "";
	}
}
/**
 * 格式化时间。显示年与日 时分秒 2017-12-20 20:25:54
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function formatterDateYMDHMS(cellvalue, options, rowObject) {
	if (cellvalue != null) {
		var a=new Date(cellvalue);
		s = a.getFullYear() + "-";
		s+=("0"+(a.getMonth()+1)).slice(-2) + "-";
		s+= ("0"+a.getDate()).slice(-2);
		s+= " "+("0"+a.getHours()).slice(-2) +":";
		s+= ("0"+a.getMinutes()).slice(-2) +":";
		s+= ("0"+a.getSeconds()).slice(-2);
		return s;
	} else {
		return "";
	}
}

/**
 * 获取跳转链接中的参数
 * @param paramName 参数名
 * @returns
 */
function urlParams(paramName) {
	var reg = new RegExp("[\?&]" + paramName + "=([^&]*)[&]?", "i");
	var paramVal = window.location.search.match(reg);
	return paramVal == null ? "" : paramVal[1];
}

//取消enter键提交表单功能
function cancelEnter(){
	var txt=$(":text");
	$(this).keydown(function(e){
		if(e.keyCode ==13){
			e.stopPropagation();
			return false;
		}
	})
}