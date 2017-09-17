mui.init();
// 初始化全局castapp.js
castapp.init();
var sessionId = common.getPublicItem("sessionId");
var user= common.getPublicItem("sessionUser");
var userId =user;/*user['userId'];*/
var areaId ='11111'; /*user['areaId'];*/
// 互动提问
var postType = 1;

function getCategorys() {
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/getCategorys.do';
	mui.ajax(requestPath,{
		data : {
			'userId': userId,
			'areaId': areaId,
			'sessionId': sessionId
		},
		dataType : "json",
		type : "post",
		success : function(data){
			var categorys = data['data'];
			initCategorys(categorys);
		},
		error : function(data){
			castapp.closeWaiting();
			castapp.prompt("由于网络问题获取版块出错，请再试稍候！");
		}
	});
}

function initCategorys(categorys){
	var html = '';
	html = html + '<option value="" selected="true">--请选择--</option>';
	for (var i = 0; i < categorys.length; i++) {
		var o = categorys[i];
		html = html + '<option value="' + o['categoryId'] + '">';
		html = html + o['categoryName'];
		html = html + '</option>';
	}
	$("#category").html(html);
}

function saveForumPost() {
	var postTitle = $("#postTitle").val();
	if (postTitle === null || postTitle === '') {
		castapp.prompt("提问标题不能为空！");
		return;
	}
	if (postTitle !== null && postTitle !== '' && postTitle.length > 50) {
		castapp.prompt("提问标题不能大于100个字！");
		return;
	}
	var categoryId = $("#category option:selected").val();
	if (categoryId === null || categoryId === '') {
		castapp.prompt("提问版块不能为空！");
		return;
	}
	var postContent = $("#postContent").val();
	if (postContent === null || postContent === '') {
		castapp.prompt("提问内容不能为空！");
		return;
	}
	postTitle = emojione.toShort(postTitle);
	postContent = emojione.toShort(postContent);
	castapp.showWaiting();
	var requestPath = common.SERVICE_PREFIX_ADDRESS + 'app/forumpost/saveForumPost.do';
	mui.ajax(requestPath,{
		data : {
			'postTitle': postTitle,
			'categoryId': categoryId,
			'postContent': postContent,
			'postType': postType,
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
			castapp.prompt("由于网络问题保存提问失败，请再试稍候！");
		}
	});
}

$(document).ready(function() {
	
	$("#btnSave").click(function(){
		saveForumPost();
	});
	
	getCategorys();
});
