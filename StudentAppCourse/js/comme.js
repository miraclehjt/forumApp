var common = {
	//后台请求地址前缀
	SERVICE_PREFIX_ADDRESS: "http://192.168.252.1:8090/ForumAppService/",

	getPublicItem : function(key){ 

		var publicStr = localStorage.getItem("publicStr");
		var obj = JSON.parse(publicStr);
		var v = null;
		try {
			v = obj[key];
		} catch (e) {
			v = "";
		}
		return v;
		//return obj[key];
	},

	getImagePath: function(relativePath) {

		var path = "http://192.168.252.1:8090/rsmanager/upload" + relativePath;

		return path;
	},

	// 返回提示退出
	initBackToExit: function() {
		mui.plusReady(function() { 
			var first;
			mui.back = function() {
				//首次按键，提示‘再按一次退出应用’
				if(!first) {
					first = new Date().getTime();
					mui.toast('再按一次退出应用');
					setTimeout(function() {
						first = null;
					}, 1000);
				} else {
					if(new Date().getTime() - first < 1000) {
						plus.runtime.quit();
						return;
					}
					return;
				}
			};
		});
	},
	
	//打开链接地址
	openUrl: function(url) {
		localStorage.setItem("web_browser.url", url);
		castapp.newInterface({
			url: 'web_browser.html',
			id: 'web_browser',
			showType: "pop-in",
			showTime: '200'
		});
	},
	//将时间戳格式化成 年-月-日
	formatterDateYMD: function(timestamp) {
		if(timestamp != null) {
			var a = new Date(timestamp);
			s = a.getFullYear() + "-";
			s += ("0" + (a.getMonth() + 1)).slice(-2) + "-";
			s += ("0" + a.getDate()).slice(-2);
			return s;
		} else {
			return "";
		}
	},
	// 分页相关参数封装
	PageInfo: function(pageNum, pageSize, pages) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.pages = pages;
	}
}

common.PageInfo.prototype.setPageNum = function(pageNum) {
	this.pageNum = pageNum;
}

common.PageInfo.prototype.addPageNum = function(page) {
	if (!page)
		page = 1;
	this.pageNum += page;
}

common.PageInfo.prototype.setPageSize = function(pageSize) {
	this.pageSize = pageSize;
}

common.PageInfo.prototype.setPages = function(pages) {
	this.pages = pages;
}

common.PageInfo.prototype.isLoadComplete = function() {
	return (this.pageNum == this.pages || this.pages == 0 || this.pages == 1);
}
