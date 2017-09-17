mui.init();

castapp.init();

var activeTabId;
var self;
var sub_pages;

var subpages = ['home.html', 'kb_forum_category.html', 'MySetting.html'];
var subpagesIds = ['home', 'forum', 'MySetting'];
var subpage_style = {
    top: '0px',
    bottom: '50px',
    scrollIndicator: "none" ,
};

function initMenu(){
	self = plus.webview.currentWebview();
	
	//创建子页面，首个选项卡页面显示，其它均隐藏；
	mui.plusReady(function() {
		// 初始化所有Tab页面
		for(var i = 0; i < subpages.length; i++) {
			var sub = plus.webview.create(subpages[i], subpagesIds[i], subpage_style);	
			self.append(sub);
			// 隐藏第一个以外的Tab页面
			if (i > 0) {
				sub.hide();
			}
		}
		
		// 显示第一个Tab页面
		activeTabId = subpagesIds[0];
		plus.webview.show(activeTabId);
		
		// 添加 mui-bar-tab > a 点击事件
		mui('.mui-bar-tab').on('tap', 'a', function(e) {
			// 处理点击逻辑
			var targetTabId = this.getAttribute('id');
			var targetTab = this.getAttribute('href');
			
			if(targetTabId == activeTabId) {
				return;
			}
			
			// 显示点击页面
			plus.webview.show(targetTabId);
			
			// 隐藏当前显示页面
			plus.webview.hide(activeTabId);
			
			// 更改当前激活的选项卡
			activeTabId = targetTabId;
		});
		
		document.addEventListener('gohome',function () {
		    var defaultTab = document.getElementById("home");
		    mui.trigger(defaultTab,'tap');
		    var current = document.querySelector(".mui-bar-tab>.mui-tab-item.mui-active");
		    if(defaultTab!==current){
		        current.classList.remove('mui-active');
		        defaultTab.classList.add('mui-active');
		    }
		});
	});
}

$(document).ready(function(){
	mui.plusReady(function() {
		initMenu();
	});
	
	// 保留原back方法
	var oldback = mui.back;

	common.initBackToExit();
});
