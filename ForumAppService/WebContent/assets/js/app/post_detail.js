var postId = urlParams("postId");

var editingArea;
var editingBtn;
var backupText = "";
var isEditing = false;

function getPostDetail() {
	console.log("getPostDetail:"+postId);
	alert(postId);
	var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/getFormPostDetail.do";
	
	$.ajax({
		url : url,
		method : "get",
		dataType : 'json',
		data : {
			"postId":postId,
		},
		success : function(data, textStatus) {
			
			console.log(data);
			
			setupPostWithData(data);


		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
			alert("请求问题 详情失败！"); 

		}
	});
}

function refreshPage() {
	getPostDetail();
}

function setupPostWithData(data) {

	var html = setupPostPage(data["data"]);
	
	
	
	$("#pContent").html(html);
	
}


function stickyPost(btn) {

	 var fpId = $(btn).data("fpid");
	 var postId = $(btn).data("postid");
		
		var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/stickyPostReply.do";
		
		$.ajax({
			url : url,
			method : "post",
			dataType : 'json',
			data : {
				"postReplyId":fpId,
				"postId":postId
			},
			success : function(data, textStatus) {
				
				console.log(textStatus+data);
				
//				$.simplyToast('置顶成功！','success');
				alert("置顶成功");
				
				refreshPage();
				

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				alert("请求失败！"); 

			}
		});
}

function delReply(btn) {
	$.messager.confirm('提示', '确定要删除该回复吗?', function (r) {
		 if (r) {
			 var fpId = $(btn).data("fpid");
				
				var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/deletePostReply.do";
				
				$.ajax({
					url : url,
					method : "post",
					dataType : 'json',
					data : {
						"postReplyId":fpId
					},
					success : function(data, textStatus) {
						
						console.log(textStatus+data);
						
						refreshPage();
						

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						
						alert("请求失败！"); 

					}
				});
		 
		 }
	});
}

function delDebate(btn) {
	
	$.messager.confirm('提示', '确定要删除该回复评论吗?', function (r) {
		 if (r) {
			 var fpId = $(btn).data("fpid");
				
				var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/deleteReplyDebate.do";
				
				$.ajax({
					url : url,
					method : "post",
					dataType : 'json',
					data : {
						"replyDebateId":fpId,
					},
					success : function(data, textStatus) {
						
						console.log(textStatus+data);
						
						refreshPage();
						

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						
						alert("请求失败！"); 

					}
				});
		 
		 }
	});
	
	
}



function onClickModifyReply(btn) {

	var elmReplyContent = $(btn).parent().siblings(".p-content");
	var editable = $(elmReplyContent).prop("contenteditable");
	
	if (editable == "true") {//编辑状态

		modifyReply(btn);
		
	}
	else {//非编辑状态
		
		if(isEditing) {//之前有正在编辑的数据
			$.messager.confirm('提示', '要放弃正在编辑的数据吗?', function(r) {
				if(r) {
					$(editingArea).text(backupText);
					$(editingBtn).val("修改");
					$(editingArea).prop("contenteditable","false");
					
					$(btn).val("保存");
					$(elmReplyContent).prop("contenteditable","true");
					$(elmReplyContent).focus();
					
					editingBtn = btn;
					editingArea = elmReplyContent;
					backupText = $(elmReplyContent).text();
					isEditing = true;

				}
				else {
					$(editingArea).focus();

				}
			});
		}
		else {
			$(btn).val("保存");
			$(elmReplyContent).prop("contenteditable","true");
			$(elmReplyContent).focus();
			
			editingBtn = btn;
			editingArea = elmReplyContent;
			backupText = $(elmReplyContent).text();
			isEditing = true;

		}
	}
}

function modifyReply(btn) {
	
	var elmReplyContent = $(btn).parent().siblings(".p-content");

	var replyContent = $(elmReplyContent).text();
	if(replyContent == "") {
		$.messager.alert("提示","内容不能为空！","info");
//		alert("内容不能为空!");
		return;
	}
	
	isEditing = false;
	$(btn).val("修改");

	var fpId = $(btn).data("fpid");
	
	$(elmReplyContent).prop("contenteditable","false");

	
	var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/modifyPostReplyContent.do";
	
	$.ajax({
		url : url,
		method : "post",
		dataType : 'json',
		data : {
			"postReplyId":fpId,
			"postReplyContent":replyContent
		},
		success : function(data, textStatus) {
			
			console.log(textStatus+data);
			
		
			$.messager.alert("提示","修改成功！","info");
			
//			refreshPage();
			

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
			alert("请求失败！"); 

		}
	});
	 
}


function onClickModifyTitle(btn) {

	var elmPostTitle = $("h1.post-title");
	var editable = $(elmPostTitle).prop("contenteditable");
	
	if (editable == "true") {
		modifyTitle(btn);

	}
	else {

		if(isEditing) {//之前有正在编辑的数据
			$.messager.confirm('提示', '要放弃正在编辑的数据吗?', function(r) {
				if(r) {
					$(editingArea).text(backupText);
					$(editingBtn).val("修改");
					$(editingArea).prop("contenteditable","false");
					
					$(btn).val("保存");
					$(elmPostTitle).prop("contenteditable","true");
					$(elmPostTitle).focus();
					
					
					editingBtn = btn;
					editingArea = elmPostTitle;
					backupText = $(elmPostTitle).text();
					isEditing = true;

				}
				else {
					$(editingArea).focus();

				}
			});
		}
		else {
			$(btn).val("保存");
			$(elmPostTitle).prop("contenteditable","true");
			$(elmPostTitle).focus();
			
			editingBtn = btn;
			editingArea = elmPostTitle;
			backupText = $(elmPostTitle).text();
			isEditing = true;
		}
		
		
	}
	
}



function modifyTitle(btn) {
	var elmPostTitle = $("h1.post-title");
	var editable = $(elmPostTitle).prop("contenteditable");
	
	if (editable == "true") {
		
		var title = $(elmPostTitle).text();
		if(title == "" || title == null) {
//			alert("标题不能为空!");
			$.messager.alert("提示","标题不能为空！","info");
			return;
		}
		if (title.length > 50) {
//			alert("提问标题超长！");
			$.messager.alert("提示","提问标题不能超过50字！","info");
			return;
		}
		
		
		$(btn).val("修改");
		
		isEditing = false;
	
		var fpId = $(btn).data("fpid");
		
		$(elmPostTitle).prop("contenteditable","false");
		
		

		var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/modifyPostTitle.do";
		
		$.ajax({
			url : url,
			method : "post",
			dataType : 'json',
			data : {
				"postId":fpId,
				"postTitle":title
			},
			success : function(data, textStatus) {
				
				console.log(textStatus+data);

				$.messager.alert("提示","修改成功！","info");
				
//				refreshPage();
					

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				alert("请求失败！"); 

			}
		});
	}
}

function onClickModifyContent(btn) {
	
	var elmPostContent = $(btn).parent().siblings(".p-content");
	var editable = $(elmPostContent).prop("contenteditable");
	
	if (editable == "true") {
		
		modifyContent(btn); 
	}
	else {

		if(isEditing) {//之前有正在编辑的数据
			$.messager.confirm('提示', '要放弃正在编辑的数据吗?', function(r) {
				if(r) {
					$(editingArea).text(backupText);
					$(editingBtn).val("修改");
					$(editingArea).prop("contenteditable","false");
					
					$(btn).val("保存");
					$(elmPostContent).prop("contenteditable","true");
					$(elmPostContent).focus();
					
					editingBtn = btn;
					editingArea = elmPostContent;
					backupText = $(elmPostContent).text();
					isEditing = true;

				}
				else {
					$(editingArea).focus();

				}
			});
		}
		else {

			$(btn).val("保存");
			$(elmPostContent).prop("contenteditable","true");
			$(elmPostContent).focus();
			
			editingBtn = btn;
			editingArea = elmPostContent;
			backupText = $(elmPostContent).text();
			isEditing = true;
		}
	}
	 
}


function modifyContent(btn) {

	
	var elmPostContent = $(btn).parent().siblings(".p-content");
	var content = $(elmPostContent).text();
	if(content == "" || content == null) {
		alert("提问内容不能为空!");
		return;
	}

	
	$(btn).val("修改");
	isEditing = false;

	var fpId = $(btn).data("fpid");
	
	$(elmPostContent).prop("contenteditable","false");

	var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/modifyPostContent.do";
	
	$.ajax({
		url : url,
		method : "post",
		dataType : 'json',
		data : {
			"postId":fpId,
			"postContent":content
		},
		success : function(data, textStatus) {
			
			console.log(textStatus+data);

			$.messager.alert("提示","修改成功！","info");
			
//			refreshPage();
			

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
			alert("请求失败！"); 

		}
	});
	
	 
}


function dealPreviousEditing() {
	var ebtn = editingBtn;
	var eArea = editingArea;
	var eText = backupText;
	var eType = editingType;
	
	if(eType != 0) {//之前有正在编辑的数据
		$.messager.confirm('提示', '要保存正在编辑的数据吗?', function(r) {
			if(r) {
				if (eType == 1) {
					modifyTitle(ebtn);
				}
				else if (eType ==2) {
					modifyContent(ebtn);
				}
				else {
					modifyRely(ebtn);
				}
			}
			else {
				$(eArea).text(eText);
				$(ebtn).val("修改");
				$(eArea).prop("contenteditable","false");
			}
		});
	}
	
}

function getImagePath(relativaPath) {
	var path = IMAGE_PREFIEX_ADDRESS + relativePath;
	
	return path;
}


function setupPostPage(data) {


	var html = "";

	var pAuthorName =  data["userName"];
	var portrait =  data["userPortrait"];
	var pTitle = data["title"];
	var postId = data["postId"];

	html += '<div class="core-title"><h1 class="post-title" contenteditable="false">' + pTitle + '</h1><div class="title-tail"><input type="button" onclick="onClickModifyTitle(this);" class="operate-btn" value="修改" data-fpid="';
	html += postId;
	html += '"></div></div>';

	portrait = getImagePath(portrait);

	var content = data["content"];

	// 贴子内容
	
	//提问者头像
	html += '<div class="post-item first-floor"><div class="author-main"><ul class="p-author">';
	html += '<li class="portrait"><div class="portrait-wrapper"><img src="${baseurl}/assets/images/website/head.png"></div></li>';/*portrait*/


	//名称
	html += '<li class="name">' + pAuthorName + '</li></ul></div>';
	
	
	//内容
	html += '<div class="post-content-main"><div class="p-content" contenteditable="false">' + content + '</div>';
	html += '<div class="content-tail"><input type="button" onclick="onClickModifyContent(this);" class="operate-btn" value="修改" data-fpid="';
	html += postId;
	html += '"></div></div><div class="clear"></div></div>';


	// 评论
	var postList =  data["replies"];
	
	if (postList != "" || postList != null) {
		for (var i = 0; i < postList.length; i++) {
			var reply = postList[i];
			
			var lName =  reply["userName"];
			var lPortrait = getuserPortraitPath(reply);
			var lContent = reply["postContent"];
			var lFpId = reply["fpId"];

			var tempHtml = "";
			
			var mask = "";
			var sticky = reply["sticky"];
			var maskPath =  SERVER_CONTEXT_PATH + "assets/images/website/zhiding_icon.png";
			if (sticky > 0) mask = '<img id="mask" src="'+maskPath+'"/>';

			// post-item
			//头像
			html += '<div class="post-item"><div class="author-main"><ul class="p-author">';
			html += '<li class="portrait"><div class="portrait-wrapper"><img src=' + lPortrait +'></div></li>';


			//名称
			html += '<li class="name">' + lName + '</li></ul></div>';
			
			html += mask;
			
			//内容
			html += '<div class="post-content-main"><div class="p-content" contenteditable="false">' + lContent + '</div>';
			
			//置顶、修改、删除
			html += '<div class="reply-tail"><input type="button" onclick="stickyPost(this);" class="operate-btn" value="置顶" data-fpid="';
			html += lFpId;
			html += '" data-postid="'+postId;
			html += '">';
			html += '<input type="button" onclick="delReply(this);" class="operate-btn" value="删除" data-fpid="';
			html += lFpId;
			html += '">';
			html += '<input type="button" onclick="onClickModifyReply(this);" class="operate-btn" value="修改" data-fpid="';
			html += lFpId;
			html += '"></div>';

			// 评论回复列表
			tempHtml += '<div class="p-reply"><ul class="reply-list">';

			
			var postReplyList = reply["debateList"];
			
			if (postReplyList != "" || postReplyList != null) {
				for (var j = 0; j < postReplyList.length; j++) {
					var tempReplyBackend = postReplyList[j];

					var liBorderCls = "";
					if (j == 0) {
						liBorderCls = "first-no-border";
					}

					var lzlPortrait = getuserPortraitPath(tempReplyBackend);
					var lzlName = tempReplyBackend["userName"];
					var lzlContent = tempReplyBackend["postContent"];
					var lzlFpId = tempReplyBackend["fpId"];

					tempHtml += '<li class=' + liBorderCls + '><a class="rlz-author">';

					tempHtml += '<img src=' + lzlPortrait + '></a>';
			
					tempHtml += '<div class="lzl-cnt"><a class="lzl-name">' + lzlName + ': </a>';
					
					tempHtml += '<span class="lzl-post-content">' + lzlContent + '</span>';
			

					tempHtml += '<div class="lzl-del"><input type="button" onclick="delDebate(this);" class="del-btn" value="删除" data-fpid="';
					tempHtml += lzlFpId;
					tempHtml += '"></div></div></li>';

				}

				tempHtml += "</ul></div></div>";


				tempHtml += '<div class="clear"></div></div>';

				html += tempHtml;

			}
		}
			

	}
	
	console.log(html);
//	alert(html);


	return html;
}

function  getuserPortraitPath(post) {
	var portrait = post["userPortrait"];

	var imgPath = getImagePath(portrait);
	return imgPath;

//	return SERVER_CONTEXT_PATH + "assets/images/website/head.png";
}

function  getImagePath(relativaPath) {
	var path = "";

	if (relativaPath == null || relativaPath == "") {
		path = SERVER_CONTEXT_PATH + "assets/images/website/head.png";
	} else {
		path = IMAGE_PREFIEX_ADDRESS + relativaPath;
	}

	return SERVER_CONTEXT_PATH + "assets/images/website/head.png";
}

$(document).ready(function() {

	getPostDetail();


});
