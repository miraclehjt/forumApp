var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");
/*var roles = common.getPublicItem("staffRoles");
var rolesList =JSON.stringify(roles);*/

var userId =user /*user['userId'];*/
var areaId ='11111'/* user['areaId'];*/
// 初始化分页信息
//var pageNum = parseInt('0');
//var pageSize = parseInt('10');
//var pages = parseInt('1');
var pageInfo = new common.PageInfo(0, 10, 1);

mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		down: {
			callback: pulldownRefresh
		},
		up: {
			contentrefresh: '正在加载...',
			contentnomore: '没有更多数据了',
			callback: pullupRefresh
		}
	}
});

castapp.init();

//返回刷新该页面 
window.addEventListener('customRefreshPage', function(event){
		console.log('forum_recommend_post.customRefreshPage...');
		window.location.reload();
});
//下拉刷新
function pulldownRefresh() {
	pageInfo.setPageNum(1);
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getRecommendPosts.do';
	mui.ajax(requestPath, {
		data: {
			"sessionId":sessionId,
			"userId":userId,
			"areaId":areaId,
			"pageNum": pageInfo.pageNum,
			"pageSize": pageInfo.pageSize
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			var list = plus.webview.getWebviewById('kb_forum_category');
			//触发列表界面的自定义事件（refresh）,从而进行数据刷新
			mui.fire(list, 'loadedList');
			var searchList = data['data'];
			pages = parseInt(data['pages']);
			pageInfo.setPages(pages);
			initSearchList(false,searchList);
			if (pageInfo.isLoadComplete()) {
				mui('#pullrefresh').pullRefresh().refresh(false);
			}
			else {
				mui('#pullrefresh').pullRefresh().refresh(true);
			}
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取数据失败，请再试稍候！");
		}
	});
}

//上拉加载
function pullupRefresh() {
//	pageNum++;
//	getSearchResult();
	pageInfo.addPageNum(1);
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getRecommendPosts.do';
	mui.ajax(requestPath, {
		data: {
			"rolesList":rolesList,
			"sessionId":sessionId,
			"userId":userId,
			"areaId":areaId,
			"pageNum": pageInfo.pageNum,
			"pageSize": pageInfo.pageSize
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			var searchList = data['data'];
			pages = parseInt(data['pages']);
			pageInfo.setPages(pages);
			initSearchList(true,searchList);
			if (pageInfo.isLoadComplete()) {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			} else {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				mui('#pullrefresh').pullRefresh().refresh(true);
			}
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取数据失败，请再试稍候！");
		}
	});
}

function initList(){
	if (mui.os.plus) {
		mui.plusReady(function() {
			pulldownRefresh();
		});
	} else {
		mui.ready(function() {
			pulldownRefresh();
		});
	}
}

function getSearchResult(append) {
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getRecommendPosts.do';
	mui.ajax(requestPath, {
		data: {
			"sessionId":sessionId,
			"userId":userId,
			"areaId":areaId,
			"pageNum": pageInfo.pageNum,
			"pageSize": pageInfo.pageSize
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			var searchList = data['data'];
			pages = parseInt(data['pages']);
			pageInfo.setPages(pages);
			initSearchList(append,searchList);
			if (pageInfo.isLoadComplete()) {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			} else {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				mui('#pullrefresh').pullRefresh().refresh(true);
			}
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取数据失败，请再试稍候！");
		}
	});
}


function initSearchList(append,data) {
	var html="";
	for(var i=0;i<data.length;i++){
		html=html+
		'<li class="mui-table-view-cell" id=\"' + data[i]["postId"] + '" postType="' + data[i]["postType"] +'">' +
         '<div class="mui-table">' +
         '<div class="mui-table-cell">' +
         '<h4 class="title mui-ellipsis" id="title' + data[i]["postId"] + '">' +
         data[i]["postTitle"] +
         '</h4>' +
         '<p class="mui-h6 mui-ellipsis content">' +
         data[i]["postContent"] +
         '</p>' +
         '</div>' +
         '<div class="listright">' +
         '<p>' +
         '<span class="staff">' +
         data[i]["createUser"] +
         '</span>' +
         '<span class="shijan">' +
         data[i]["time"] +
         '</span>' +
         '</p>' +
         '<p class="gz">' +
         '<span class="iconfont icon-guanzhu1">' +
         '</span>' +
         '<span class="gzN">' +
         data[i]["starNum"]+"关注" +
         '</span>' +
         '<span class="pl">' +
         data[i]["replyNum"]+"条回复" +
         '</span>' +
         '<span class="clickNum">' +
         '浏览数' + data[i]["clickNum"] +
         '</span>' +
         '</p>' +
         '</div>' +
         '</div>' +
         '</li>';
	}
	
	if (append) {
		castapp.id('recommend-list').innerHTML += emojione.shortnameToUnicode(html);
	} else {
		castapp.id('recommend-list').innerHTML = emojione.shortnameToUnicode(html);
	}
	
	var recommend_list = castapp.id('recommend-list');
	var recommendList = recommend_list.getElementsByTagName('li');
	for (var i = 0; i < recommendList.length; i++) {
		var result = recommendList[i];
		castapp.click(result, function(o){
			setPostListClick(o);
		});
	}
}

//跳转至提问回复
function setPostListClick(o){
	var postId = $(o).attr('id');
	var postType = $(o).attr('postType');
	var title = $("#title"+postId).text();
	localStorage.setItem("forumPost.postId",postId);
	localStorage.setItem("forumPost.title", title);
	if (postType == 1) {
		castapp.newInterface({
			url: 'common_post_reply.html',
			id: 'common_post_reply',
			showType: "pop-in",
			showTime: '200'
		});
		return;
	} else if (postType == 2) {
		castapp.newInterface({
			url: 'emergency_forumpostreply.html',
			id: 'emergency_forumpostreply',
			showType: "pop-in",
			showTime: '200'
		});
		return;
	} else {
		castapp.newInterface({
			url: 'common_post_reply.html',
			id: 'common_post_reply',
			showType: "pop-in",
			showTime: '200'
		});
		return;
	}
}

$(document).ready(function() {
	initList();
});
