
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
	window.location.reload();
});



function pullupRefresh() {
	pageNum++;
	getSearchResult(true);
	
}

function getSearchResult(append) {

	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getMyInitiatePosts.do';
	mui.ajax(requestPath, {
		data: {
			"sessionId":sessionId,
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
//	alert(JSON.stringify(data));
	for(var i=0;i<data.length;i++){
		var dotClass = "dot-hidden";
		var newReplyNum = data[i]["newReplyNum"];
		var auditstate=data[i]["auditState"];
		var mask="";
		if(newReplyNum > 0) {
			dotClass = "dot";
		}
		if(auditstate==0){
			mask='<span class="iconfont icon-shenhezhong"></span>';
		}
		if(auditstate ==2){
			mask ='<span class="iconfont icon-shenheweitongguo"></span>';
		}
		html=html+
		'<li class="mui-table-view-cell" id="' + data[i]["postId"] +'" state='+data[i]["auditState"]+ ' comment="'+data[i]["auditComment"] +'" >' +
         mask+'<div class="mui-table">' +
         '<div class="mui-table-cell mui-col-xs-7">' +
         '<div class="post-title">' +
         '<div class="mui-table"><div class="mui-table-cell mui-col-xs-10 mui-ellipsis">' + data[i]["postTitle"]+ '</div> '+ '<div class="mui-table-cell mui-col-xs-2">' +'<span class='+dotClass+'>' + newReplyNum +
         '</span>'+
         '</div>' +
         '</div></div>' +
         '<p class="mui-h6 mui-ellipsis">'+data[i]["postContent"] +'</p>' +
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
	
//	console.log(html);
	
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
	var auditState = $(o).attr("state");
	var auditComment = $(o).attr("comment");
	if(auditState == 0) {
		castapp.prompt("该问题正在审核中...");
	}
	else if (auditState == 2) {
		mui.confirm(auditComment,"审核不通过",["确认"],function(e){
			
		});
	}
	else {
	var title = $("#title"+postId).text();
	localStorage.setItem("forumPost.postId",postId);
	localStorage.setItem("forumPost.title", title);
	localStorage.setItem("forumPost.targetTabId", "my_posts_sub");
	castapp.newInterface({
		url: 'common_post_reply.html',
		id: 'common_post_reply',
		showType: "pop-in",
		showTime: '200'
	});
	}
	
}

$(document).ready(function() {
//	castapp.id('post-list').innerHTML ='<li class="mui-table-view-cell" id="1000000623" state=1><div class="mui-table"><div class="mui-table-cell mui-col-xs-8"><div class="post-title"><div class="mui-table"><div class="mui-table-cell mui-col-xs-10 mui-ellipsis">就尽量</div> <div class="mui-table-cell mui-col-xs-2"><span class=dot-hidden>0</span></div></div></div><p class="mui-h5 mui-ellipsis">1吧</p></div><div class="mui-table-cell mui-col-xs-4  mui-text-right"><p><span class="name">AIKF0020</span><span class="time">1小时前</span></p><p class="gz"><span class="mui-icon mui-icon-star"><span id="gzN">0关注</span></span><span class="pl">0条回复</span></p></div></div></li><li class="mui-table-view-cell" id="1000000624" state=1><div class="mui-table"><div class="mui-table-cell mui-col-xs-8"><div class="post-title"><div class="mui-table"><div class="mui-table-cell mui-col-xs-10 mui-ellipsis">一</div> <div class="mui-table-cell mui-col-xs-2"><span class=dot-hidden>0</span></div></div></div><p class="mui-h5 mui-ellipsis">哈哈</p></div><div class="mui-table-cell mui-col-xs-4  mui-text-right"><p><span class="name">AIKF0020</span><span class="time">1小时前</span></p><p class="gz"><span class="mui-icon mui-icon-star"><span id="gzN">0关注</span></span><span class="pl">0条回复</span></p></div></div></li><li class="mui-table-view-cell" id="1000000603" state=1><div class="mui-table"><div class="mui-table-cell mui-col-xs-8"><div class="post-title"><div class="mui-table"><div class="mui-table-cell mui-col-xs-10 mui-ellipsis">再来一发</div> <div class="mui-table-cell mui-col-xs-2"><span class=dot-hidden>0</span></div></div></div><p class="mui-h5 mui-ellipsis">关机了</p></div><div class="mui-table-cell mui-col-xs-4  mui-text-right"><p><span class="name">AIKF0020</span><span class="time">4小时前</span></p><p class="gz"><span class="mui-icon mui-icon-star"><span id="gzN">1关注</span></span><span class="pl">2条回复</span></p></div></div></li><li class="mui-table-view-cell" id="1000000604" state=1><div class="mui-table"><div class="mui-table-cell mui-col-xs-8"><div class="post-title"><div class="mui-table"><div class="mui-table-cell mui-col-xs-10 mui-ellipsis">测试每个</div> <div class="mui-table-cell mui-col-xs-2"><span class=dot-hidden>0</span></div></div></div><p class="mui-h5 mui-ellipsis">回来了</p></div><div class="mui-table-cell mui-col-xs-4  mui-text-right"><p><span class="name">AIKF0020</span><span class="time">4小时前</span></p><p class="gz"><span class="mui-icon mui-icon-star"><span id="gzN">0关注</span></span><span class="pl">1条回复</span></p></div></div></li><li class="mui-table-view-cell" id="1000000542" state=2><div class="mui-table"><div class="mui-table-cell mui-col-xs-8"><div class="post-title"><div class="mui-table"><div class="mui-table-cell mui-col-xs-10 mui-ellipsis">跳跳糖</div> <div class="mui-table-cell mui-col-xs-2"><span class=dot-hidden>0</span></div></div></div><p class="mui-h5 mui-ellipsis">沟沟壑壑</p></div><div class="mui-table-cell mui-col-xs-4  mui-text-right"><p><span class="name">AIKF0020</span><span class="time">2016-12-08</span></p><p class="gz"><span class="mui-icon mui-icon-star"><span id="gzN">0关注</span></span><span class="pl">0条回复</span></p></div></div></li>';
	
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
