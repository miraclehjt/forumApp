// 版块类型 互动版块
var categoryType = 1;

mui.init({
	swipeBack: false
});
castapp.init();
//点击打开我的页面、
castapp.click(castapp.id("portrait"), function() {
	castapp.newInterface({
		url:'question_center.html',
		id:'question_center',
		showType:'pop-in',
		showTime:'200'
	});
});

//点击我要发帖进入发帖
castapp.click(castapp.id("my_post"),function(){
	castapp.newInterface({
		url:'forum_post.html',
		id:'forum_post',
		showType:'pop-in',
		showTime:'200'
	});
});

//点击搜索图标进入搜索页面
castapp.click(castapp.id("forumSearch"),function(){
	castapp.newInterface({
		url:'forum_post_Search.html',
		id:'forum_post_Search',
		showType:'pop-in',
		showTime:'200'
	});
});
//获取头像
var portrait = common.getPublicItem("sessionUser")["portrait"];
if(portrait == null || portrait == "") {
	portrait = "../images/head.png";
} else {
	portrait = common.getImagePath(portrait);
}
$("#portrait").attr("src", portrait);

//获取社区版块信息
function getForumCategoryList(){
	castapp.showWaiting('正在加载...');
	var userId = "STU001";/*common.getPublicItem("sessionUser")["userId"];*/
	var sessionId = common.getPublicItem("sessionId");
	var requestUrl=common.SERVICE_PREFIX_ADDRESS + "app/forum/queryCategoryForPage.do";
	mui.ajax(requestUrl,{
		data:{
			"categoryType": categoryType,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "get",
		success:function(data){
			setCategoryList(data["data"]);
		},
		error: function(data) {
			castapp.closeWaiting();
			castapp.prompt("由于网络问题获取版块信息失败，请稍候再试！");
		}
	});
}

//经获取出来的版块信息动态的拼接到页面上来
function setCategoryList(data){
	var html="";
	for(var i=0;i<data.length;i++){
		html = html +
		'<a class="mui-control-item" id=\"' + 'forum_post_list_' + data[i]["categoryId"] + '\"' +
		' categoryId="' + data[i]["categoryId"] + '\"' +
		' href=\"' + 'forum_post_list.html' + '\">' +
		data[i]["categoryName"] +
		'</a>';
		subpages.push("forum_post_list.html");
		subpagesIds.push("forum_post_list_" + data[i]["categoryId"]);
	}
	$("#menus").append(html);
	initCategoryMenu();
}

// 页面地址
// 屏蔽应急咨询版块
// var subpages = ['forum_recommend_post.html', 'forum_hot_post.html', 'forum_learn_garden.html', 'questionnaire_list.html', 'emergency_forum_post_list.html'];
var subpages = ['forum_recommend_post.html', 'forum_hot_post.html'];
// 页面ID
// 屏蔽应急咨询版块
// var subpagesIds = ['forum_recommend_post', 'forum_hot_post', 'forum_learn_garden', 'questionnaire_list', 'emergency_forum_post_list'];
var subpagesIds = ['forum_recommend_post', 'forum_hot_post'];

// 已激活页面的ID
var activeTabId;
var self;
var sub_pages;
function initCategoryMenu() {

	var subpages_style = {
		top: '80px',
		bottom: '0px',
		bouce: 'none'
	};
	
	var subpage_style = {
		top: '0px',
		bottom: '0px',
		bouce: 'none'
	};
	
	//创建子页面，首个选项卡页面显示，其它均隐藏；
	mui.plusReady(function() {
		self = plus.webview.currentWebview();
		
		sub_pages = plus.webview.create('kb_forum_category_pages.html', 'kb_forum_category_pages', subpages_style);
		
		self.append(sub_pages);
		
		// 去掉批量加载页面
//		for(var i = 0; i < subpages.length; i++) {
//			var sub = plus.webview.create(subpages[i], subpagesIds[i], subpage_style);
//			sub_pages.append(sub);
//			if(i > 0) {
//				sub.hide();
//			}
//		}
		
		// 显示第一个Tab页面
		var sub = plus.webview.create(subpages[0], subpagesIds[0], subpage_style);
		sub_pages.append(sub);
		activeTabId = subpagesIds[0];
		plus.webview.show(activeTabId);
		
		mui('.mui-segmented-control').on('tap', 'a', function(e) {
			
			var targetTab = this.getAttribute('href');
			var targetTabId = this.getAttribute('id');
			var categoryId = this.getAttribute('categoryId');
			if (categoryId !== null && categoryId !== '') {
				localStorage.setItem("forumPost.categoryId", categoryId);
			}
			localStorage.setItem("forumPost.targetTabId", targetTabId);
			
			if(targetTabId == activeTabId) {
				return;
			}
			
			var wv = plus.webview.getWebviewById(targetTabId);
			if (wv == null) {
				var sub = plus.webview.create(targetTab, targetTabId, subpage_style);
				sub_pages.append(sub);
			}
			plus.webview.show(targetTabId);
			
			// 暂时屏蔽页面点击刷新
//			var wv = plus.webview.getWebviewById(targetTabId);
//			if (wv != null) {
//				wv.reload(true);
//			}
			
			// 隐藏当前显示页面
			plus.webview.hide(activeTabId);
			
			// 更改当前激活的选项卡
			activeTabId = targetTabId;
			
		});
	});
}

window.addEventListener('loadedList', function(event){
	castapp.closeWaiting();
});
	

//初始化各个方法
$(document).ready(function() {
	
	mui.plusReady(function() {
		getForumCategoryList();
	});
	
	// 先关闭所有子页面，然后关闭整个页面
	castapp.click(castapp.id("go_back"), function(e) {
		for (var i = 0; i < sub_pages.length; i++) {
			sub_pages.remove(sub_pages[i]);
			plus.webview.close(sub_pages[i]);
		}
		self.remove(sub_pages);
		plus.webview.close(sub_pages);
		plus.webview.close(self);
	});
});
