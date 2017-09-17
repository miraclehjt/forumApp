var pageInfo = new common.PageInfo(0, 10, 1);

var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");
var userId="STU001";
var areaId = '11111'
var targetTabId = localStorage.getItem("forumPost.targetTabId");
//互动版块Id
var categoryId = localStorage.getItem("forumPost.categoryId");

// 问题类型 互动提问
var postType = 1;

mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		down:{
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

window.addEventListener('customRefreshPage', function(event){
	//通过event.detail可获得传递过来的参数内容
	window.location.reload();
});

//下拉刷新
function pulldownRefresh(){
	pageInfo.setPageNum(1);
	mui.ajax(common.SERVICE_PREFIX_ADDRESS +"app/forum/queryForumPostList.do",{
			beforeSend:common.sessionTimeOver(),
		data:{
			"categoryId":categoryId,
			"postType": postType,
			"page": pageInfo.pageNum,
			"rows": pageInfo.pageSize,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "get",
		success:function(data){
			var pages = parseInt(data['pages']);
			setForumPostList(false,data["rows"]);
			pageInfo.setPages(pages);
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
			if (pageInfo.isLoadComplete()) {
				mui('#pullrefresh').pullRefresh().refresh(false);
			} else {
				mui('#pullrefresh').pullRefresh().refresh(true);
			}
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取社区问题失败，请稍候再试！");
		}
	});
}
//上拉加载
function pullupRefresh() {
	pageInfo.addPageNum(1);
	mui.ajax(common.SERVICE_PREFIX_ADDRESS +"app/forum/queryForumPostList.do",{
		data:{
			"categoryId":categoryId,
			"postType": postType,
			"page": pageInfo.pageNum,
			"rows": pageInfo.pageSize,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "get",
		success:function(data){
			var pages = parseInt(data['pages']);
			setForumPostList(true,data["rows"]);
			pageInfo.setPages(pages);
			if (pageInfo.isLoadComplete()) {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			} else {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				mui('#pullrefresh').pullRefresh().refresh(true);
			}
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取社区问题失败，请稍候再试！");
		}
	});
}

function initList(){
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
}


//添加双引号
function doubleQuot(str) {
	return '"' + str + '"';
}

//获得互动提问版块下的问题列表
function getForumPostList(append){
	mui.ajax(common.SERVICE_PREFIX_ADDRESS +"app/forum/queryForumPostList.do",{
		data:{
			"categoryId":categoryId,
			"postType": postType,
			"page": pageInfo.pageNum,
			"rows": pageInfo.pageSize,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "get",
		success:function(data){
			var pages = parseInt(data['pages']);
			setForumPostList(append,data["rows"]);
			pageInfo.setPages(pages);
			if (pageInfo.isLoadComplete()) {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			} else {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				mui('#pullrefresh').pullRefresh().refresh(true);
			}
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取社区问题失败，请稍候再试！");
		}
	});
}
//将查询出来的问题列表拼接到页面上显示出来
function setForumPostList(append,data){
	var createDate=common.formatterDateYMD(data["createDateStr"]);
	var html="";
	for(var i=0;i<data.length;i++){
		html=html+   
		'<li class="mui-table-view-cell" id=\"' + data[i]["postId"] +'">' +
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
         "创建人:"+data[i]["createUser"] +
         '</span>' +
         '<span class="shijan">' +
         "时间:"+createDate +
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
		castapp.id('postList').innerHTML += emojione.shortnameToUnicode(html);
	}
	else {
		castapp.id('postList').innerHTML = emojione.shortnameToUnicode(html);
	}
//	$("#postList").append(html);
	listClick();
}
//给LI设置点击事件
function listClick() {
	var postReply_list = document.getElementById("postList");
	var reply_list = postReply_list.getElementsByTagName("li");
	for (var i = 0; i < reply_list.length; i++) {
		var ll = reply_list[i];
		castapp.click(ll, function(o){
			setPostReplyClick(o);
		});
	}
}
//点击互动提问的版块查询版块下面的提问信息
function setPostReplyClick(o){
	var postId = $(o).attr('id');
	var title = $("#title"+postId).text();
	localStorage.setItem("forumPost.postId",postId);
	localStorage.setItem("forumPost.title", title);
	localStorage.setItem("forumPost.targetTabId", targetTabId);
	castapp.newInterface({
		url: 'common_post_reply.html',
		id: 'common_post_reply',
		showType: "pop-in",
		showTime: '200'
	});
}
//初始化方法
$(document).ready(function() {
	initList();
});
