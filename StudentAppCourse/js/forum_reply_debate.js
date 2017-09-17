mui.init({
	beforeback: function(){
		var v_forumpostreply = plus.webview.getWebviewById('common_post_reply');
		//触发列表界面的自定义事件（refresh）,从而进行数据刷新
		mui.fire(v_forumpostreply,'customRefreshPage');
		//返回true，继续页面关闭逻辑
		return true;
	}
});

// 初始化全局castapp.js
castapp.init();

var sessionId = common.getPublicItem("sessionId");
var user = common.getPublicItem("sessionUser");
var userId =user;
var areaId ='11111'; /*user['areaId'];*/

// 回复ID
var postReplyId = localStorage.getItem("forumPost.postReplyId");

function saveForumReplyDebate() {
	var postReplyDebateContent = $("#postReplyDebateContent").val();
	if (postReplyDebateContent == null || postReplyDebateContent == '') {
		castapp.prompt("评论内容不能为空！");
		return;
	}
	postReplyDebateContent = emojione.toShort(postReplyDebateContent);
	castapp.showWaiting();
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/saveForumReplyDebate.do';
	mui.ajax(requestPath,{
		data : {
			'postReplyId': postReplyId,
			'postReplyDebateContent': postReplyDebateContent,
			'userId': userId,
			'areaId': areaId,
			'sessionId': sessionId
		},
		dataType : "json",
		type : "post",
		success : function(data){
			castapp.closeWaiting();
			var retCode = data['retCode'];
			if (retCode == '1') {
				castapp.prompt("保存成功");
				castapp.closeCurrentInterface();
			} else {
				castapp.prompt("保存失败");
			}
		},
		error : function(data){
			castapp.closeWaiting();
			castapp.prompt("由于网络问题保存评论失败，请再试稍候！");
		}
	});
}

$(document).ready(function() {
	
	$("#btnSave").click(function(){
		saveForumReplyDebate();
	});
	
});
