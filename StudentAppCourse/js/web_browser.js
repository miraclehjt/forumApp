var sessionId = common.getPublicItem("sessionId");
var userId = common.getPublicItem("userId");
var title = localStorage.getItem("oTitle");
mui.init();
castapp.init();
var webBrowser = {};
webBrowser.openUrl = function(url) {
	
	//启用双击监听
	mui.init({
		swipeBack: false,
		gestureConfig:{
			doubletap:true
		},
		subpages:[{
			url:url,
			id:'web_browser_page',
			styles:{
				top: '44px',
				bottom: '0px',
				bounce: 'vertical'
			},
		}]
	});
	
}
window.onload=function(){
	mui.plusReady(function(){
		castapp.closeWaiting();
	});
}
$(document).ready(function(){
	$("#web_title").text(title);
	var url = localStorage.getItem("web_browser.url");
	webBrowser.openUrl(url);
});

