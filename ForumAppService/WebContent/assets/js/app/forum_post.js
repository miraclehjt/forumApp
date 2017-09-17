$(document).ready(function(){
	//取消enter键提交表单功能
	cancelEnter();
	//获得版块列表
	getCategoryList();
	//将焦点自动聚集到查询框里
	var input =document.getElementById('postNameSearch').focus();
	//获取提问信息
	getForumPostList();
	//提问条件查询
	$("#doSearchNewBtn").click(function(){
		doSearchNewBtn();
	});
});

//获取提问信息
function getForumPostList() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'forum/getForumPostList.do';
	$("#forum-grid").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"userId": userId,
			"sessionId": sessionId,
			"areaId":areaId
		},
		"idField": "postId",
		"striped": true,
		"fitColumns": true,
		"singleSelect": true,
		"rownumbers": true,
		"pagination": true,
		"nowrap": false,
		"pageNumber": 1,
		"pageSize": 10,
		"pageList": [10, 20, 50, 100, 150, 200],
		"showFooter": true,
		columns:[[
		    {field:'postId',title:'提问编号',rowspan:1,hidden:true},
			{field:'categoryId',title:'版块编号',rowspan:1,hidden:true},
			{field:'postTitle',title:'提问标题',rowspan:1,width:100,formatter:formatPost},
			{field:'postContent',title:'提问内容',rowspan:1,width:100,formatter:formatPost},
			{field:'categoryName',title:'提问板块',rowspan:1,width:60,align : 'center',formatter:formatPost},
			{field:'postType',title:'提问类型',rowspan:1,width:60,align : 'center',formatter:formatPostType},
			{field:'kbTitle',title:'知识标题',rowspan:1,width:60,align : 'center',formatter:formatPost},
			{field:'auditUser',title:'审核人',rowspan:1,width:60,align:'center'},
			{field:'auditState',title:'审核状态',rowspan:1,width:60,align : 'center',formatter:formatAuditState},
			{field:'auditComment',title:'审核意见',rowspan:1,width:100,formatter:formatPost},
			{field:'auditDate',title:'审核时间',rowspan:1,width:80,formatter: formatterDateYMDHMS},
			{field:'operate',title:'审核',rowspan:1,width:80,align:'center',formatter:formatOperate1}
		]],
		onBeforeLoad: function (param) {
			
		},
		onLoadSuccess: function (data) {
		},
		onLoadError: function () {
		
		},
		onClickRow: function(rowIndex, rowData) {
			
		},
		onClickCell: function (rowIndex, field, value) {
			
		},
		loadFilter : function(data){
		
			return loadFilter(data);
		}
	});
}

//获得版块列表
function getCategoryList(){
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumcategory/queryForumCategoryForPage.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"userId": userId,
		},
		success:function(data) {
				var categoryList = data["rows"];
				initForumCategoryList(categoryList);
		},
		error:function(data) {
			
		}
	});
}

//初始化版块列表
function initForumCategoryList(categoryList){
	var html = "";
	html = html +"提问版块："+ "<select id='forumTypeSearch' name='forumTypeSearch' style='width:150px'>";
	html = html + "<option value=''>----请选择----</option>";
	/*html = html + "<option value='1'>学习园地</option>";*/
	for (var i = 0; i < categoryList.length; i++) {
		var category = categoryList[i];
		html = html + "<option value=\"" + category["categoryId"] + "\" >";
		html = html + category["categoryName"];
		html = html + "</option>";
	}
	html = html + "</select>";
	$("#ForumCategoryList").html(html);
}

//用于过滤指定的一些数据
function loadFilter(data){
	var values = {
			total : data.total,
			rows : []
	}
	for(var i = 0; i < data['list'].length; i++){
		var o = {};
		_loadArray(data['list'][i],o,"");
		values.rows.push(o);
	}
	return values;
}
function _loadArray(data,o,pre){
	if(pre)
		pre = pre+ ".";
	for(var attr in data){
		var row = data[attr];
		if(typeof(row) == "object"){
			_loadArray(row,0,pre+attr);
		}else{
			o[pre+attr] = row;
		}
	}
}

function formatPost(val, row, index){
	if(val!=""&&val!=null&&val!=undefined){
		var oldVal = val;
		if(val.length>10){
			
			var s=val.substring(0,10)+"....";
			var str = "<span title='"+oldVal+"'>"+s+"</span>";
			return str;
		}else{
			return val;
		}
	}
	
}
//审核操作链接
function formatOperate1(val, row, index){
	
	var auditState= row["auditState"];
	var operateRow = ""; 
	if(auditState=="1"||auditState=="2"||auditState==1||auditState==2){
		operateRow+="<span>已审核</span>"
		operateRow += "&nbsp;|&nbsp;";
	}else{
		operateRow += "<span><a href='javascript:void(0);' onclick='check(\"" + index + "\")'>审核</a></span>";
		operateRow += "&nbsp;&nbsp;|&nbsp;";
	}
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickPreviewBtn(\""
		+ index + "\")'>详情</a></span>";
	return operateRow;
	
}

function formatPostType(val,row,index) {
	if(val == 1) {
		return "互动提问";
	}
	else if (val == 2) {
		return "应急咨询";
	}
	
	return val;
	
}

function formatAuditState(val,row,index) {
	if(val == 1) {
		return "审核通过";
	}
	else if (val == 2) {
		return "审核不通过";
	}else{
		return "未审核";
	}
	
	return val;
	
}
//提问条件查询
function doSearchNewBtn() {
	var postNameSearch =($("#postNameSearch").val());
	var auditStateSearch=$("#auditStateSearch option:selected").val();	
	var forumcategoryId=$("#forumTypeSearch option:selected").val();
	
	$('#forum-grid').datagrid('load',{
		"postNameSearch": postNameSearch,
		"auditStateSearch": auditStateSearch,
		"forumcategoryId": forumcategoryId,
		"userId": userId,
		"sessionId": sessionId,
		"areaId":areaId
	});
}


//审核
function check(i) {
	$('#forum-grid').datagrid('selectRow', i);
	var row = $('#forum-grid').datagrid('getSelected');
	var postId = row["postId"];
	var postTitle = row["postTitle"];
	var postContent = row["postContent"];
	var auditComment = row["auditComment"];
	var replyPost=row[""];
	createUser=row["createUser"];	 
	
	$("#postId").val(postId);
	$("#postTitle").val(postTitle);
	$("#postContent").val(postContent);
	$("#auditComment").val(auditComment);
	$('#mk_activity_info').window('open');
//	$("#huifu").css("display","none");
	$("#huifudisagree").css("display","none");
	$("#disagree").css("display","none");
	$("#notice").css("display","none");
}


function onClickPreviewBtn(index) {

	$('#forum-grid').datagrid('selectRow', index);
	var row = $('#forum-grid').datagrid('getSelected');
	
	var postTitle = row["postTitle"];
	var postContent = row["postContent"];
	var auditComment = row["auditComment"];
	var auditState = row["auditState"];
	if(auditState==1||auditState=="1"){
		auditState="通过";
	}else if(auditState==2||auditState=="2"){
		auditState="不通过";
	}else{
		auditState="未审核";
	}

	$("#postTitleDetail").html(postTitle);
	$("#postContentDetail").html(postContent);
	$("#auditStateDetail").html(auditState);
	$("#auditCommentDetail").html(auditComment);
	$('#detail_info').window('open');
}

//审核处理时，触发事件，不同意必须要写不同意理由
function selectOpinion(){
	var option=$("#auditState option:selected").val();	
	if(option=="2"){
		$("#huifu").css("display","none");
		$("#huifudisagree").css("display","none");
		$("#disagree").css("display","block");
	}else{
		$("#huifu").css("display","block");
		$("#disagree").css("display","none");	
	}
}
function changecheckYes(){
    var ch=document.getElementById("checkYes");
	if(ch.checked==true){
		$("#huifudisagree").css("display","block");
		$("#notice").css("display","block");
	}else{
		$("#huifudisagree").css("display","none");
		$("#notice").css("display","none");
	}
}
//是否给提问者发短信
function noticeMsg(){
     //var checkboxVules=$("input:checkbox[value='1']").attr('checked','true');//获取checkbox的值
	 var ch=document.getElementById("checkNotice")
	   var  noticeFlag=ch.checked;
        
}

//关闭审核界面
$("#cancelBtn").click(function(){
	$("#mk_activity_info").window("close");
	$("#huifuComment").val("");
	$("#auditComment").val("");
	$("#checkNotice").attr("checked",false);
	$("#checkYes").attr("checked",false);
}); 


//保存审核结果
$("#saveBtn").click(function(){
	var noticeFlag=document.getElementById("checkNotice").checked;
	var postId=$("#postId").val();
	var auditState=$("#auditState option:selected").val();	
	var auditComment=$("#auditComment").val();
	var mreplyPost=$("#huifuComment").val();
	if(document.getElementById("checkYes").checked==true){
		if(mreplyPost==""||mreplyPost==null){
			$.messager.alert("提示","回复内容不能为空！");
			return;
		}else{
			var url=common.SERVICE_PREFIX_ADDRESS + 'forumPost/addPostReplyContent.do';
			$.ajax({
				url:url,
				method:"post",
				dataType:'json',
				data: {
					"postId":postId,
					"postReplyContent":mreplyPost,
					"userId": userId,
					"areaId": areaId,
					"sessionId" : sessionId,
				},
				success:function(data) {
      				stickyPost(data);
				},
				error:function(data) {
					alert("请求失败！");
				}
			});
		}
	}
	if(auditState=="2"){
		if(auditComment==""||auditComment==null){
			$.messager.alert("提示","请输入不同意理由！");
			return;
		}
	}else{
		auditComment="审核通过";
	}
	var url = common.SERVICE_PREFIX_ADDRESS + 'forum/saveCheck.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"postId":postId,
			"auditState":auditState,
			"auditComment":auditComment,
			"userId": userId,
			"areaId": areaId,
			"sessionId" : sessionId,
			"noticeFlag":noticeFlag,
			"createUser":createUser
		},
		success:function(data, textStatus) {
			var retCode = data["retCode"];
			var retMsg = data["retMsg"];
			if (retCode == "1") {
				getForumPostList();	
				$("#mk_activity_info").window("close");
				$("#huifuComment").val("");
				$("#auditComment").val("");
			} else {
				$.messager.alert("提示",retMsg);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
});

function stickyPost(data) {
	var postReplyId=data["postReplyId"];
	var postId=$("#postId").val();
		var url = common.SERVICE_PREFIX_ADDRESS + "forumPost/stickyPostReply.do";
		$.ajax({
			url : url,
			method : "post",
			dataType : 'json',
			data : {
				"postReplyId":postReplyId,
				"postId":postId
			},
			success : function(data, textStatus) {
				console.log(textStatus+data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert("请求失败！"); 
			}
		});
}