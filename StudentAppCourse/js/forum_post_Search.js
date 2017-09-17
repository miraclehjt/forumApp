//社区问题搜索js
mui.init();
// 初始化全局castapp.js
castapp.init();
var keyword;
castapp.click(castapp.id("search_icon"), function() {
	document.getElementById("search_keyword").blur();
	ca.showWaiting("正在查询,请稍等...");
	keyword = ca.id("search_keyword").value;
	if(!keyword || keyword.length < 1) {
		ca.closeWaiting();
		ca.prompt("请输入关键字再进行搜索！");
		return;
	} else {
		var userId = common.getPublicItem("sessionUser")["userId"];
		var sessionId = common.getPublicItem("sessionId");
		mui.ajax(common.SERVICE_PREFIX_ADDRESS + "app/forum/querySearchPostList.do", {
			data: {
			    "userId": userId,
			    "sessionId": sessionId,
				"postTitle": keyword
			},
			dataType: "json",
			type: "post",
			success: function(data) {
				ca.closeWaiting();
				console.log(JSON.stringify(data));
				setResultList(data["rows"]);
			},
			error: function(data) {
				ca.closeWaiting();
				console.log(data);
			}
		});
	}
});

function doubleQuot(str) {
	return '"' + str + '"';
}

function setResultList(data) {
	if(data === null || data.length <= 0) {
		castapp.id("result-list").innerHTML = "";
		ca.prompt("你搜索的信息不存在，请重新搜索！");
		return;
	}
	var table = castapp.id("result-list");
	keyword = ca.id("search_keyword").value;
	var html="";
	for(var i=0;i<data.length;i++){
		html = html+
		'<li class="mui-table-view-cell" id=\"' + data[i]["postId"] +'" postType="'+data[i]["postType"]+'">' +
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
         '</p>' +
         '</div>' +
         '</div>' +
         '</li>';
	}
	table.innerHTML = html;
	listClick();
}


function listClick() {
	var certification_list = document.getElementById("result-list");
	var li_list = certification_list.getElementsByTagName("li");
	for (var i = 0; i < li_list.length; i++) {
		var ll = li_list[i];
		castapp.click(ll, function(o){
			setClickBind(o);
		});
	}
}

function setClickBind(o) {
	var postId = $(o).attr('id');
	var title = $("#title"+postId).text();
	var postType=$(o).attr('postType');
	localStorage.setItem("forumPost.postId",postId);
	localStorage.setItem("forumPost.title", title);
	if(postType == 1){
		castapp.newInterface({
			url: 'common_post_reply.html',
			id: 'common_post_reply',
			showType: "pop-in",
			showTime: '200'
		});
		return;
	} else if(postType == 2){
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
/*//定义一个自动搜索方法
function initSearch(){
	
}*/
//首页搜索
var questionsearch;
var questionkeyword;
var questiontype;
//var caption = "";
function initKeywords() {
	questionsearch = localStorage.getItem("home_search.questionsearch");
	questionkeyword = localStorage.getItem("home_search.questionkeyword");
	questiontype = localStorage.getItem("home_search.questiontype");
	if ("true" == questionsearch && "question" == questiontype) {
		keyword = questionkeyword;
		castapp.id("search_keyword").value = keyword;
		
		var userId = common.getPublicItem("sessionUser")["userId"];
		var sessionId = common.getPublicItem("sessionId");
		mui.ajax(common.SERVICE_PREFIX_ADDRESS + "app/forum/querySearchPostList.do", {
			beforeSend:common.sessionTimeOver(),
			data: {
			    "userId": userId,
			    "sessionId": sessionId,
				"postTitle": keyword
			},
			dataType: "json",
			type: "post",
			success: function(data) {
				ca.closeWaiting();
				console.log(JSON.stringify(data));
				setResultList(data["rows"]);
			},
			error: function(data) {
				ca.closeWaiting();
				console.log(data);
			}
		});
		localStorage.removeItem("home_search.questionsearch");
		localStorage.removeItem("home_search.questionkeyword");
		localStorage.removeItem("home_search.questiontype");
}
}
$(document).ready(function(){
	initKeywords();
});
