
var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");


var userId = user;
var areaId = "22222";
// 初始化分页信息
var pageNum = parseInt('0');
var pageSize = parseInt('10');
var pages = parseInt('1');

mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		up: {
			contentrefresh: '正在加载...',
			contentnomore: '没有更多数据了',
			callback: pullupRefresh
		}
	}
});

castapp.init();

window.addEventListener('customRefreshPage', function(event){
	console.log("customRefreshPage");
	window.location.reload();
});




function pullupRefresh() {
	pageNum++;
	getSearchResult(true);
	
}

function getSearchResult(append) {

	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getMyLikePosts.do';
	mui.ajax(requestPath, {
		data: {
			"userId":userId,
			"areaId":areaId,
			"pageNum": pageNum,
			"pageSize": pageSize
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			var searchList = data['data'];
			pages = parseInt(data['pages']);
			initSearchList(append,searchList);
			if (pageNum == pages) {
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


function initSearchList(append,data) {
	var html="";
	for(var i=0;i<data.length;i++){
		
		html=html+
		'<li class="mui-table-view-cell" id=\"' + data[i]["postId"] +'\">' +
         '<div class="mui-table">' +
         '<div class="mui-table-cell mui-col-xs-7">' +
         '<div class="post-title mui-ellipsis">' + data[i]["postTitle"]+ '</div>' +
         '<p class="mui-h5 mui-ellipsis">'+data[i]["postContent"] +'</p>' +
         '</div>' +
         '<div class="mui-table-cell mui-col-xs-5  mui-text-right">' +
         '<p>' +
         '<span class="name">' +
         data[i]["createUser"] +
         '</span>' +
         '<span class="time">' +
         data[i]["time"] +
         '</span>' +
         '</p>' +
         '<p class="gz">' +
         '<span class="iconfont icon-guanzhu1">' +
         '</span>' +
         '<span id="gzN">' +
         data[i]["starNum"]+"关注" +
         '</span>' +
         '<span class="pl">' +
         data[i]["replyNum"]+"条回复" +
         '</span>' +
         '</p>' +
         '</div>' +
         '</div>' +
         '</li>';
	}
	if (append) {
		castapp.id('post-list').innerHTML += html;
	}
	else {
		castapp.id('post-list').innerHTML = html;
	}
	
	var list = castapp.id('post-list');
	var lis = list.getElementsByTagName('li');
	for (var i = 0; i < lis.length; i++) {
		var result = lis[i];
		castapp.click(result, function(o){
			setPostListClick(o);
		});
	}
}

//跳转至提问回复
function setPostListClick(o){
	var postId = $(o).attr('id');
	var title = $("#title"+postId).text();
	localStorage.setItem("forumPost.postId",postId);
	localStorage.setItem("forumPost.title", title);
	localStorage.setItem("forumPost.targetTabId", "star_post_sub");
	castapp.newInterface({
		url: 'common_post_reply.html',
		id: 'common_post_reply',
		showType: "pop-in",
		showTime: '200'
	});
}

$(document).ready(function() {
	if (mui.os.plus) {
		mui.plusReady(function() {
			setTimeout(function() {
				mui('#pullrefresh').pullRefresh().pullupLoading();
			}, 0);
		});
	} else {
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
		});
	}
});
