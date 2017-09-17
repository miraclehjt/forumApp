var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");
var userId =user; /*user['userId'];*/
var areaId ='11111';/* user['areaId'];*/

var userName ='陈造龙'; /*user['userName'];*/
$("#staffName").text(userName);

var portrait ="";/* common.getPublicItem("sessionUser")["portrait"];*/
if(portrait === null || portrait === "") {
	portrait = "../images/head.png";
} else {
	portrait = common.getImagePath(portrait);
}
$("#portrait").attr("src", portrait);



function getNewReplyNum() {
	console.log("getNewReplyNum");
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getNewReplyNum.do';
	mui.ajax(requestPath, {
		data: {
			"userId": userId,
			'sessionId': sessionId
		},
		dataType: "json",
		type: "get",
		success: function(data) {
			initNewReply(data);
		},
		error: function(data) {
			castapp.prompt("由于网络问题获取收藏目录失败，请再试稍候！");
		}
	});
}

function initNewReply(data) {

	var newReply = data["newReplyNum"];
	var newDebate = data["newDebateNum"];

	if(newReply > 0) {
		$("#new-replyMine").css("display", "inline-block");
		$("#new-replyMine").text(newReply);
	}
	else {
		$("#new-replyMine").css("display", "none");
	}

	if(newDebate > 0) {
		$("#new-debateMine").css("display", "inline-block");
		$("#new-debateMine").text(newDebate);
	}
	else {
		$("#new-debateMine").css("display", "none");
	}

}

$(document).ready(function() {

	getNewReplyNum();


	ca.click(castapp.id("ask-button"), function() {
		castapp.newInterface({
			url: 'forum_post.html',
			id: 'forum_post',
			showType: 'pop-in',
			showTime: '200'
		});
	});

	ca.click(castapp.id("my-post"), function() {
		castapp.newInterface({
			url: 'my_posts_main.html',
			id: 'my_posts_main',
			showType: "pop-in",
			showTime: '200'
		});
	});
	
	ca.click(castapp.id("reply-post"), function() {
		castapp.newInterface({
			url: 'my_reply_posts_main.html',
			id: 'my_reply_posts_main',
			showType: "pop-in",
			showTime: '200'
		});
	});

	ca.click(castapp.id("like-post"), function() {
		castapp.newInterface({
			url: 'star_post_main.html',
			id: 'star_post_main',
			showType: "pop-in",
			showTime: '200'
		});
	});
	
	/*ca.click(castapp.id("my-vote"), function() {
		castapp.newInterface({
			url: 'my_questionnaire_main.html',
			id: 'my_questionnaire_main',
			showType: "pop-in",
			showTime: '200'
		});
	});*/
	
	ca.click(castapp.id("back"), function() {
		mui.back();
	});

});
