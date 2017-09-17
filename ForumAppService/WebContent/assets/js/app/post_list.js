
var editRow;
var provincePower = 0;//0：地市管理员 1：省级管理员

function getForumPostGridData() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/getForumPostList.do';
	var keyword = $("#searchKeyword").val();
	$("#dg").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"keyword": keyword,
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
			{field:'postId',title:'主键',rowspan:1,hidden:true},
			{field:'postTitle',title:'标题',rowspan:1,width:80},
			{field:'postType',title:'类型',rowspan:1,width:40,align : 'center',formatter:formatPostType},
			{field:'categoryName',title:'所属版块',rowspan:1,width:40},
			{field:'kbTitle',title:'相关知识',rowspan:1,width:60},
			{field:'recommend',title:'是否推荐',rowspan:1,width:40,formatter:formatRecommendState},
			{field:'starNum',title:'关注数',rowspan:1,width:40},
			{field:'auditState',title:'审核状态',rowspan:1,width:40,formatter:formatAuditState},
			{field : 'createDate',title : '发表日期',rowspan : 1,width : 60,align : 'center',formatter : formatDate},
			{field : 'createUser',title : '发表者',rowspan : 1,width : 40}, 
//			{field : 'updateDate',title : '更新日期',rowspan : 1,width : 60,align : 'center',formatter : formatDate},
//			{field : 'updateStaff',title : '更新者',rowspan : 1,width : 40},
			{field : 'operate',title : '操作',rowspan : 1,width : 100,formatter : formatOperate}
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
			
		}
	});
}


function formatAuditState(val,row,index) {
	
	if(val == 0) {
		return "未审核";
	}
	else if (val == 1) {
		return "审核通过";
	}
	
	return "审核不通过";
}

function formatRecommendState(val,row,index)
{
	if (val == null || val==0) {
		return "否";
	}
	
	return "是";
}

function formatPostState(val,row,index) {
	
	
	return val;
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

function formatOperate(val, row, index) {
	
	var state = "取消推荐";
	
	var recommendNum = row["recommend"];
	if(recommendNum == null || row["recommend"] == 0) state = "推荐";

	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickPreviewBtn(\""
			+ index + "\")'>预览</a></span>";
	operateRow += "&nbsp;|&nbsp;";
	
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickDeleteBtn(\""
		+ index + "\")'>删除</a></span>";
	operateRow += "&nbsp;|&nbsp;";
	
	
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickRecommendBtn(\""
		+ index + "\")'>"+state+"</a></span>";



	return operateRow;
}

function formatDate(val,row,index) {
	
	var timestamp = val["time"];
	var d = new Date(timestamp);    //根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + 
	           (d.getMonth() + 1) + "-" +
	           (d.getDate()) + " " + 
	           (d.getHours()) + ":" + 
	           (d.getMinutes()) + ":" + 
	           (d.getSeconds());
	           
	return date;
}



function onClickSearchBtn() {
	var keyword = $("#searchKeyword").val();
	$('#dg').datagrid('load',{
		"keyword" : keyword,
		"sessionId": sessionId
	});
}

function onClickPreviewBtn(index) {
	if (index) {
		$('#dg').datagrid('selectRow',index);
	}

	 var row = $('#dg').datagrid('getSelected');
	
	 if (row == null) {
		alert('请先选择一条提问！');
		return;
	}
	
		var postId = row["postId"];
	
			var index = layer.open({
				type: 2,
				title: '提问详情',
				shadeClose: true,
				shade: 0.3,
				maxmin: true, //开启最大化最小化按钮
				area: ['60%', '80%'],
				content: common.SERVICE_PREFIX_ADDRESS + '/page/post_detail.do?dicName=forum&postId=' + postId,
				success : function(layero, index){
//					var callback = layer.getChildFrame('#callbackName', index);
//					callback.val(parentMethod);
					
				}
			});
			layer.full(index);
	
	 
	 
}


function recommendPost() {
	 var row = $('#dg').datagrid('getSelected');
		
	 if (row == null) {
		alert('请先选择一条提问！');
		return;
	}
	 
	var recommendNum = row["recommend"];

	
	if (recommendNum==0) {//未推荐
		if (provincePower == 1) {

			$("#area_select").window("open");
		}
		else {
			var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/recommendPost.do'; 
			
			 $.ajax({
					url:url,
					method:"post",
					dataType:'json',
					data: {
						"postId": row["postId"],
						"userId" : userId,
						"status" : "1",
						"areaIds" : areaId
			
					},
					success:function(data, textStatus) {
						
						$("#area_select").window("close");
						
						$('#dg').datagrid('unselectAll');
						  
			            $('#dg').datagrid('reload');   
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
						$.messager.show({  
		                   title: 'Error',  
		                   msg: textStatus  
		               });
					}
				});
		}
	}
	else {//已经推荐过
		if (provincePower == 1) {
			var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/getPostRecommendAreas.do'; 
			
			 $.ajax({
					url:url,
					method:"get",
					dataType:'json',
					data: {
						"postId": row["postId"],
						"userId" : userId
			
					},
					success:function(data, textStatus) {
						setSelectedAreas(data["data"]);  
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
						$.messager.show({  
		                   title: 'Error',  
		                   msg: textStatus  
		               });
					}
				});
			
			$("#area_select").window("open");
		}
		else {
			alert('该提问已经设置为推荐！');
		}
		
	}

}

function onClickRecommendBtn(index) {
	if (index) {
		$('#dg').datagrid('selectRow',index);
	}

	 var row = $('#dg').datagrid('getSelected');
	
	 if (row == null) {
		alert('请先选择一条提问！');
		return;
	}
	 
	var recommendNum = row["recommend"];
	var status = "0";

	if(recommendNum==0 || recommendNum==null) {
		status = "1";
	}


	//推荐时弹出地市选择界面
	if (status=="1" && provincePower==1) {

		var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/getPostRecommendAreas.do';
		
		 $.ajax({
				url:url,
				method:"get",
				dataType:'json',
				data: {
					"postId": row["postId"],
					"userId" : userId
		
				},
				success:function(data, textStatus) {
					setSelectedAreas(data["data"]);  
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.messager.show({  
	                   title: 'Error',  
	                   msg: textStatus  
	               });
				}
			});
		
		$("#area_select").window("open");
	}
	else {
		var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/recommendPost.do'; 
		
		 $.ajax({
				url:url,
				method:"post",
				dataType:'json',
				data: {
					"postId": row["postId"],
					"userId" : userId,
					"status" : status,
					"areaIds" : areaId
		
				},
				success:function(data, textStatus) {
					
					$("#area_select").window("close");
					
					$('#dg').datagrid('unselectAll');
					  
		            $('#dg').datagrid('reload');   
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.messager.show({  
	                   title: 'Error',  
	                   msg: textStatus  
	               });
				}
			});
	}
	 
	 
}

function setSelectedAreas(areaIds) {
	
	for (var i = 0; i < areaIds.length; i++) {
		var areaIdVal = areaIds[i];
		$("#areaId" + areaIdVal).prop("checked", true);
	}
	
}

function onClickDeleteBtn(index) {
	if (index) {
		$('#dg').datagrid('selectRow',index);
	}

	 var row = $('#dg').datagrid('getSelected');
	
	 if (row == null) {
		alert('请先选择一条提问！');
		return;
	}
	
	$.messager.confirm('提示', '确定要删除该提问吗?', function (r) {
		 if (r) {
			 var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/deleteForumPost.do'; 
				
			 $.ajax({
					url:url,
					method:"post",
					dataType:'json',
					data: {
						"postId": row["postId"],
						"userId" : userId
				
					},
					success:function(data, textStatus) {
						
						$('#dg').datagrid('unselectAll');
						  
			             $('#dg').datagrid('reload');   
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
						$.messager.show({  
		                    title: 'Error',  
		                    msg: textStatus  
		                });
					}
				});
		 }
	 
	 });
}

function onClickAddPostBtn() {
	
	$("#saveFm").form("clear");
	
	$("#add_post").window("open");
	
	
}


function onClickSaveBtn() {
	var title = $("#postTitle").val();
	var content = $("#postContent").val();
	var areaIds = getPostAreaIds();
	
	if (title == null || title == "") {
		$.messager.alert("提示","标题不能为空！");
	}
	else if (content==null || content=="") {
		$.messager.alert("提示","内容不能为空！");
	}
	else if (areaIds==null || areaIds=="") {
		$.messager.alert("提示","范围不能为空！");
	}
	else {
		
		var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/saveForumPost.do'; 
		
		 $.ajax({
				url:url,
				method:"post",
				dataType:'json',
				data: {
					"userId" : userId,
					"postTitle":title,
					"postContent":content,
					"postArea":areaIds
				},
				success:function(data, textStatus) {
					$("#add_post").window("close");
					
					$('#dg').datagrid('unselectAll');
					  
		             $('#dg').datagrid('reload');   
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.messager.show({  
	                  title: 'Error',  
	                  msg: textStatus  
	              });
				}
			});
	} 
}

function onClickSaveAreaBtn() {
	
	var areaIds = getRecommendAreaIds();
	
	 var row = $('#dg').datagrid('getSelected');
		
	 if (row == null) {
		alert('请先选择一条提问！');
		return;
	}
	 
	 if (areaIds==null || areaIds=="") {
			$.messager.alert("提示","地市不能为空！");
			return;
	}
	
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/recommendPost.do'; 
	
	 $.ajax({
			url:url,
			method:"post",
			dataType:'json',
			data: {
				"postId": row["postId"],
				"userId" : userId,
				"status" : "1",
				"areaIds" : areaIds
	
			},
			success:function(data, textStatus) {
				$("#area_select").window("close");
				
				$('#dg').datagrid('unselectAll');
				  
	             $('#dg').datagrid('reload');   
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				$.messager.show({  
                  title: 'Error',  
                  msg: textStatus  
              });
			}
		});
}

function getAreas() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/getDisplayAreas.do';
	$.ajax({
		url:url,
		method:"get",
		dataType:'json',
		data: {
			"userId": userId,
			"areaId": areaId
		},
		success:function(data, textStatus) {
			try {
				var areaList = data["data"];
				initPostAreas(areaList);
				initRecommendAreas(areaList);
			} catch (e) {
				
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

//管理员发起话题地区
function initPostAreas(areaList) {
var html = "";
	
	if (provincePower == 1) {
		html = html + "<div class=\"col-xs-2\">";
		html = html + "<div class=\"checkbox\">";
		html = html + "<label>";
		html = html + "<input name=\"areaIds\" id=\"allAreaId\" type=\"checkbox\" value=\"0\" />";
		html = html + "全部";
		html = html + "</label>";
		html = html + "</div>";
		html = html + "</div>";
	}
	
	for (var i = 0; i < areaList.length; i++) {
		var area = areaList[i];
		html = html + "<div class=\"col-xs-2\">";
		html = html + "<div class=\"checkbox\">";
		html = html + "<label>";
		html = html + "<input name=\"areaIds\" id=\"" + "areaId" + area["AREA_ID"] + "\" type=\"checkbox\" value=\"" + area["AREA_ID"] + "\" />";
		html = html + area["AREA_NAME"];
		html = html + "</label>";
		html = html + "</div>";
		html = html + "</div>";
	}
	$("#postArea").html(html);
	
	$("#postArea input[type=checkbox]").click(function(){
		if($(this).val() == "0" && $(this).prop("checked")) {
			$("input[type=checkbox]").each(function(){
				if($(this).val() != "0") {
					$(this).prop("checked", true);
				}
			});
			return;
		}
		
		if($(this).val() == "0" && !$(this).prop("checked")) {
			$("input[type=checkbox]").each(function(){
				if($(this).val() != "0") {
					$(this).prop("checked", false);
				}
			});
			return;
		}
	});
	
	if (provincePower == 0) {
		$("#postArea input[type=checkbox]").each(function(){
			$(this).prop("checked", true);
			$(this).click(function(){
				$(this).prop("checked", !$(this).prop("checked"));
			});
		});
	}
	
}


//推荐地区
function initRecommendAreas(areaList) {
	var html = "";
	
	html = html + "<div class=\"col-xs-2\">";
	html = html + "<div class=\"checkbox\">";
	html = html + "<label>";
	html = html + "<input name=\"areaIds\" id=\"allAreaId\" type=\"checkbox\" value=\"0\" />";
	html = html + "全部";
	html = html + "</label>";
	html = html + "</div>";
	html = html + "</div>";
	
	
	for (var i = 0; i < areaList.length; i++) {
		var area = areaList[i];
		html = html + "<div class=\"col-xs-2\">";
		html = html + "<div class=\"checkbox\">";
		html = html + "<label>";
		html = html + "<input name=\"areaIds\" id=\"" + "areaId" + area["AREA_ID"] + "\" type=\"checkbox\" value=\"" + area["AREA_ID"] + "\" />";
		html = html + area["AREA_NAME"];
		html = html + "</label>";
		html = html + "</div>";
		html = html + "</div>";
	}
	$("#areaIdsGroup").html(html);
	
	$("#areaIdsGroup input[type=checkbox]").click(function(){
		if($(this).val() == "0" && $(this).prop("checked")) {
			$("#areaIdsGroup input[type=checkbox]").each(function(){
				if($(this).val() != "0") {
					$(this).prop("checked", true);
				}
			});
			return;
		}
		
		if($(this).val() == "0" && !$(this).prop("checked")) {
			$("#areaIdsGroup input[type=checkbox]").each(function(){
				if($(this).val() != "0") {
					$(this).prop("checked", false);
				}
			});
			return;
		}
	});
	
}
	

function getRecommendAreaIds() {
	var areaIds = "";
	$("#areaIdsGroup input[type=checkbox]:checked").each(function(){
		var area = $(this).val();
		if (area != "0") {
			areaIds += ($(this).val());
			areaIds += "_";
		}
	
	});
	return areaIds;
}

function getPostAreaIds() {
	var areaIds = "";
	$("#postArea input[type=checkbox]:checked").each(function(){
		var area = $(this).val();
		if (area != "0") {
			areaIds += ($(this).val());
			areaIds += "_";
		}
	});
	return areaIds;
}


/*
function getStaffPower() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/getStaffPower.do'; 
	
	 $.ajax({
			url:url,
			method:"get",
			dataType:'json',
			data: {
				
				"userId" : userId
			},
			success:function(data, textStatus) {
				if(data["retCode"] == 0) {
					provincePower = data["provincePower"];
				}
				
				 
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
		
			}
		});
}*/


$(document).ready(function(){

	//取消enter键提交表单功能
	cancelEnter();
	
	//獲取區域方法
	getAreas();
	
	//搜索
	$("#doSearchBtn").click(function(){
		onClickSearchBtn();
	});
	
	//预览
	$("#previewBtn").click(function(){
		onClickPreviewBtn();
	});
	
	//推荐
	$("#recommendBtn").click(function(){
		recommendPost();
	});
	
	//删除
	$("#deleteBtn").click(function(){
		onClickDeleteBtn();
	});
	

	//发起话题
	$("#addPostBtn").click(function() {
		
		onClickAddPostBtn();
		
	});
	
	//取消
	$("#cancelBtn").click(function(){
		$("#addDlg").dialog("close");
	});
	
	//确定
	$("#saveBtn").click(function(){
		
		onClickSaveBtn();

	});
	
	//取消选择地市
	$("#cancelAreaSelectBtn").click(function(){
		$("#area_select").dialog("close");
	});
	
	//确定选择地市
	$("#saveAreaSelectBtn").click(function(){
		
		onClickSaveAreaBtn();

	});
		
	$("#createUser").val(userId);
	$("#areaId").val(areaId);
	//getStaffPower();
	
	getForumPostGridData();
	

});