var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");

var userId = common.getPublicItem("sessionUser");
//var userTel = staff['staffTel'];
var areaId ='11111'; /*user['areaId'];*/
var targetTabId = localStorage.getItem("forumPost.targetTabId");

mui.init({
	beforeback: function() {
		//获得列表界面的webview
		var v = plus.webview.getWebviewById(targetTabId);
		//触发列表界面的自定义事件（refresh）,从而进行数据刷新
		mui.fire(v, 'customRefreshPage');
		//返回true，继续页面关闭逻辑
		return true;
	}
});

castapp.init();

window.addEventListener('customRefreshPage', function(event) {
	window.location.reload();
});
//获取postId
var postId = localStorage.getItem("forumPost.postId");
var title = localStorage.getItem("forumPost.title");
//知识信息
var kbId;
var kbTitle;
//管理员回复的信息——标准答案
var mreplypost;
// 关注人数
var starNum = 0;
// 发帖人
var createUser = "";
// 是否已关注
var isStared = false;
//获取提问信息
function getForumPost() {
	mui.ajax(common.SERVICE_PREFIX_ADDRESS + "app/forum/getForumPost.do", {
	data: {
			"postId": postId,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			var forumPost = data['data'];
			if(forumPost !== null) {
				setForumPost(forumPost);
				forumPostReaded(forumPost['postId']);
				forumPostClick(forumPost['postId']);
			}
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取社区提问信息失败，请稍候再试！");
		}
	});
}

function setForumPost(forumPost) {
	var portrait = forumPost["portrait"];
	if(portrait === null || portrait === "") {
		portrait = "../images/head.png";
	} else {
		portrait = common.getImagePath(portrait);
	}
	$("#portrait").attr("src", portrait);
	//	$("#title").html(forumPost['postTitle']);
	$("#post_title").html(emojione.shortnameToUnicode(forumPost['postTitle']));
	$("#staff").html(forumPost['createUser']);
	$("#create_time").html(forumPost['createDateStr']);
	$("#stars").html(forumPost['starNum'] + '关注');
	$("#post_content").html(emojione.shortnameToUnicode(forumPost['postContent']));

	starNum = parseInt(forumPost['starNum']);
	createUser = forumPost['createUser'];
	// 查看知识
	kbId = forumPost['kbId'];
	kbTitle = forumPost['kbTitle'];
	initKb(kbId, kbTitle);

	// 判断是否已关注
	isForumStared();
}

function forumPostReaded(postId) {
	mui.ajax(common.SERVICE_PREFIX_ADDRESS + "app/forumpost/forumPostReaded.do", {
	data: {
			"postId": postId,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			var retCode = data['retCode'];
		},
		error: function(data) {
			castapp.prompt("由于网络问题更新提问已读失败，请稍候再试！");
		}
	});
}

function forumPostClick(postId) {
	var urlPth=common.SERVICE_PREFIX_ADDRESS + "app/forumpost/forumPostClick.do";
	mui.ajax(urlPth, {
	data: {
			"postId": postId,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			var retCode = data['retCode'];
		},
		error: function(data) {
			castapp.prompt("由于网络问题更新浏览数失败，请稍候再试！");
		}
	});
}

function initKb(kbId, kbTitle) {
	// 没有关联知识，就不显示知识点击
	if(kbId === null || kbId === '') {
		$("#kb_content").css("display", "none");
		return;
	}
	// 有关联知识，就显示知识标题并提供查看
	if(kbId !== null && kbId !== '') {
		$("#kb_content").css("display", "block");
		$("#kbTitle").html(kbTitle);
		$("#kbTitle").click(function() {
			localStorage.setItem("knowledge.kbId", kbId);
			localStorage.setItem("knowledge.kbTitle", kbTitle);
			localStorage.setItem("knowledgeDetail.entrance", "normal");
			castapp.newInterface({
				url: 'web_browser.html',
				id: 'web_browser',
				showType: 'zoom-fade-out',
				showTime: '200'
			});
		});
	}
}

//获取社区版块问题的回复
function getPostReplyList() {
	mui.ajax(common.SERVICE_PREFIX_ADDRESS + "app/forum/appQueryPostReplyList.do", {
	data: {
			"postId": postId,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			setPostReplyList(data["rows"]);
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取社区问题回复失败，请稍候再试！");
		}
	});
}
//在页面上拼接接口查询出来的数据
function setPostReplyList(data) {
	for(var i = 0; i < data.length; i++) {
		var html = "";
		var postReplyObject = data[i];

		var portrait = postReplyObject["portrait"];
		if(portrait === null || portrait === "") {
			portrait = "../images/head.png";
		} else {
			portrait = common.getImagePath(portrait);
		}
		
		var mask = "";
		var sticky = postReplyObject["sticky"];
 
		if (sticky > 0) mask = '<img id="mask" src="../images/zhiding_icon.png" />'; 

		html = html +
			'<li id="' + data[i]["postReplyId"] + '" class="replyp mui-table-view-cell">' +
			mask + 
			'<div class="user_info">' +
			'<img class="user_head" src="' + portrait + '"/>' +
			'<div class="user_name">' +
			data[i]["createUser"] +
			'</div>' +
			'<span class="toDebate"' + ' postReplyId="' + data[i]["postReplyId"] + '">' +
			'评论回复' +
			'</span>' +
			'</div>' +
			'<div class="reply_content">' +
			data[i]["postReplyContent"] +
			'</div>' +
			'<div class="reply_date">' +
			data[i]["createDateStr"] + '<span class="reply_type">' + '</span>' +
			'</div>' +
			'</li>';
		$("#postReplylist").append(emojione.shortnameToUnicode(html));
		var cctDebateList = data[i]['cctForumPostReplyDebateList'];
		
		if(cctDebateList !== null && cctDebateList.length !== 0) {
			var debatesHtml = '';
			debatesHtml += '<ul class="reply_debate_view">';
			for(var j = 0; j < cctDebateList.length; j++) {
				var debateObject = cctDebateList[j];
				var lzlPortrait = debateObject["portrait"];
				if(lzlPortrait === null || lzlPortrait === "") {
					lzlPortrait = "../images/head.png";
				} else {
					lzlPortrait = common.getImagePath(lzlPortrait);
				}

				debatesHtml = debatesHtml +
					'<li id="' + debateObject["postReplyDebateId"] + '" class="replyp">' +
					'<div class="user_info">' +
					'<img class="user_head" src="' + lzlPortrait + '"/>' +
					'<div class="user_name">' +
					debateObject["createUser"] +
					'</div>' +
					'</div>' +
					'<div class="reply_content">' +
					debateObject["postReplyDebateContent"] +
					'</div>' +
					'<div class="reply_date">' +
					debateObject["createDateStr"] + '<span class="reply_type">' + '</span>' +
					'</div>' +
					'</li>';
			}
			debatesHtml += '</ul>';
			$('li[id="' + postReplyObject["postReplyId"] + '"]').append(emojione.shortnameToUnicode(debatesHtml));
		}
	}

	$("span[class='toDebate']").each(function() {
		$(this).click(function() {
			var postReplyId = $(this).attr('postReplyId');
			localStorage.setItem("forumPost.postReplyId", postReplyId);
			castapp.newInterface({
				url: 'forum_reply_debate.html',
				id: 'forum_reply_debate',
				showType: "pop-in",
				showTime: '200'
			});
		});
	});
	//	$("#postReplylist").append(html);

	//console.log($("#content").html());
	//alert($("#content").html());
}

function isForumStared() {
	mui.ajax(common.SERVICE_PREFIX_ADDRESS + "forumpoststar/isForumPostStared.do", {
	data: {
			"postId": postId,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			isStared = data['data'];
			setStaredState();
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取问题关注状态，请稍候再试！");
		}
	});
}

function setStaredState() {
	if(isStared) {
		$("#icon").removeClass("iconfont icon-guanzhu1");
		$("#icon").addClass("iconfont icon-guanzhu");
	} else {
		$("#icon").removeClass("iconfont icon-guanzhu");
		$("#icon").addClass("iconfont icon-guanzhu1");
	}
}

function saveForumPostReply() {
	var postReplyContent = $("#postReplyContent").val();
	if(postReplyContent === null || postReplyContent === '') {
		castapp.prompt("回复内容不能为空！");
		return;
	}
	postReplyContent = emojione.toShort(postReplyContent);
	castapp.showWaiting();
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/saveForumPostReply.do';
	mui.ajax(requestPath, {
		data: {
			'postId': postId,
			'postReplyContent': postReplyContent,
			'userId': userId,
			'areaId': areaId,
			'sessionId': sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			castapp.closeWaiting();
			var retCode = data['retCode'];
			/*$("#postReplyContent").val('');
				var postReply = data['data'];
				appendPostReply(postReply);*/
			if(retCode == '1') {
				$("#postReplyContent").val('');
				var postReply = data['data'];
				appendPostReply(postReply);
			} else {
				castapp.prompt("发送失败"); 
			}
		},
		error: function(data) {
			castapp.closeWaiting();
			castapp.prompt("由于网络问题保存回复失败，请再试稍候！");
		}
	});
}

function appendPostReply(postReply) {
	var portrait = common.getPublicItem("sessionUser")["userPortrait"];
	if(portrait === null || portrait === "") {
		portrait = "../images/head.png";
	} else {
		portrait = common.getImagePath(portrait);
	}
	var html = '';
	html = html +
		'<li id="reply_post" class="replyp">' +
		'<div class="user_info">' +
		'<img class="user_head" src="' + portrait + '"/>' +
		'<div class="user_name">' +
		postReply["createUser"] +
		'</div>' +
		'<span class="toDebate"' + ' postReplyId="' + postReply["postReplyId"] + '">' +
		'评论回复' +
		'</span>' +
		'</div>' +
		'<div class="reply_content">' +
		postReply["postReplyContent"] +
		'</div>' +
		'<div class="reply_date">' +
		postReply["createDateStr"] +
		'</div>' +
		'</li>';
	$("#postReplylist").append(emojione.shortnameToUnicode(html));
	$("span[class='toDebate']").each(function() {
		$(this).click(function() {
			var postReplyId = $(this).attr('postReplyId');
			localStorage.setItem("forumPost.postReplyId", postReplyId);
			castapp.newInterface({
				url: 'forum_reply_debate.html',
				id: 'forum_reply_debate',
				showType: "pop-in",
				showTime: '200'
			});
		});
	});
	scrollToBottom();
}

function postStar() {
	var  urlPth=common.SERVICE_PREFIX_ADDRESS + "forumpoststar/forumPostStar.do";
	mui.ajax(urlPth, {
	data: {
			"postId": postId,
			"userId": userId,
			"sessionId": sessionId
		},
		dataType: "json",
		type: "post",
		success: function(data) {
			var retCode = data['retCode'];
			if(retCode == '1') {
				changeStarNum();
			}
		},
		error: function(data) {
			castapp.prompt("由于网络问题关注问题失败，请稍候再试！");
		}
	});
}

function changeStarNum() {
	if(isStared) {
		isStared = false;
		starNum--;
	} else {
		isStared = true;
		starNum++;
	}
	setStaredState();
	$("#stars").html(starNum + '关注');
	
}

function scrollToBottom() {
	$('html, body, .mui-content').animate({
		scrollTop: $(document).height()
	}, 300);
}

$(document).ready(function() {
	// 获取社区提问信息
	getForumPost();
	// 获取社区提问信息回复
	getPostReplyList();

	$("#btnSend").click(function() {
		saveForumPostReply();
	});

	$("#zhan1").click(function() {
		postStar();
	});

	mui.plusReady(function() {
		plus.webview.currentWebview().setStyle({
			softinputMode: "adjustResize" // 弹出软键盘时自动改变webview的高度
		});
	});
});
