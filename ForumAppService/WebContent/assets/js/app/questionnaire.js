var questionnaire = new Object();

function formatType(val, row, index) {
	var questionnaireType = row['questionnaireType'];
	var operateRow = "";
	switch(questionnaireType) {
		case 1 :
			operateRow = "调查";
			break;
		case 2 :
			operateRow = "投票";
			break;
		default:
			operateRow = "调查";
	}
	return operateRow;
}

function formatState(val, row, index) {
	var questionnaireState = row['questionnaireState'];
	var operateRow = "";
	switch(questionnaireState) {
		case 0 :
			operateRow = "准备";
			break;
		case 1 :
			operateRow = "发布";
			break;
		default:
			operateRow = "准备";
	}
	return operateRow;
}

function formatQuestionnaireDetail(val, row, index){
	var questionnaireId = row['questionnaireId'];
	var questionnaireType = row['questionnaireType'];
	var questionnaireState = row['questionnaireState'];
	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='questionnaireDetail(\"" + questionnaireId + "\",\"" + questionnaireType + "\",\"" + questionnaireState + "\")'>管理明细</a></span>";
	operateRow += "<br/>";
	operateRow += "<span><a href='javascript:void(0);' onclick='questionnaireDetailUpload(\"" + questionnaireId + "\",\"" + questionnaireType + "\",\"" + questionnaireState + "\")'>上传问卷</a></span>";
	return operateRow;
}

function formatStaff(val, row, index) {
	var questionnaireId = row['questionnaireId'];
	var endDate = row['endDateStr'];
	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='addUserIds(\"" + questionnaireId + "\",\"" + endDate + "\")'>添加人员</a></span>";
	operateRow += "<br/>";
	operateRow += "<span><a href='javascript:void(0);' onclick='delUserIds(\"" + questionnaireId + "\",\"" + endDate + "\")'>删除人员</a></span>";
	return operateRow;
}

function formatQuestionnaireResult(val, row, index) {
	var questionnaireId = row['questionnaireId'];
	var questionnaireType = row['questionnaireType'];
	var questionnaireState = row['questionnaireState'];
	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='questionnaireResult(\"" + questionnaireId + "\",\"" + questionnaireType + "\",\"" + questionnaireState + "\")'>查看结果</a></span>";
	operateRow += "<br/>";
//	var urlpath = baseurl + "/manage/questionnaire/exportQuestionnaireDetail.do?questionnaireId=" + questionnaireId;
//	operateRow += '<a target="_blank" href="' + urlpath + '">' + '导出结果' + '</a>';
	operateRow += "<span><a href='javascript:void(0);' onclick='questionnaireResultExport(\"" + questionnaireId + "\",\"" + questionnaireType + "\",\"" + questionnaireState + "\")'>导出结果</a></span>";
	return operateRow;
}

function getQuestionnaireGridData() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'questionnaire/getQuestionnaireList.do';
	var questionnaireName = $("#qQuestionnaireName").val();
	var questionnaireType = $("#qQuestionnaireType").val();
	$("#questionnaire-grid").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"questionnaireName": questionnaireName,
			"questionnaireType": questionnaireType,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		"idField": "questionnaireId",
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
			{field:'questionnaireId',title:'调查问卷ID',rowspan:1,hidden:true},
			{field:'questionnaireName',title:'问卷名称',rowspan:1,width:80},
			{field:'questionnaireType',title:'问卷类型',rowspan:1,width:40,formatter:formatType},
			{field:'questionnaireState',title:'问卷状态',rowspan:1,width:40,formatter:formatState},
			{field:'endDateStr',title:'截止日期',rowspan:1,width:60},
			{field:'createUser',title:'创建人',rowspan:1,width:40},
			{field:'updateUser',title:'修改人',rowspan:1,width:40},
			{field:'questionnaireDetail',title:'问卷明细',rowspan:1,width:40,align:'center',formatter:formatQuestionnaireDetail},
			{field:'users',title:'人员',rowspan:1,width:40,align:'center',formatter:formatStaff},
			{field:'questionnaireResult',title:'查看结果',rowspan:1,width:40,align:'center',formatter:formatQuestionnaireResult}
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

function doSearchQuestionnaire() {
	var questionnaireName = $("#qQuestionnaireName").val();
	var questionnaireType = $("#qQuestionnaireType").val();
	$('#questionnaire-grid').datagrid('load',{
		"questionnaireName": questionnaireName,
		"questionnaireType": questionnaireType,
		"userId": userId,
		"areaId": areaId,
		"sessionId": sessionId
	});
}

function setQuestionnaireData(questionnaire) {
	$("#questionnaireId").val(questionnaire["questionnaireId"]);
	$("#questionnaireName").val(questionnaire["questionnaireName"]);
	$("#questionnaireType").val(questionnaire["questionnaireType"]);
	$("#questionnaireState").val(questionnaire["questionnaireState"]);
	$("#endDate").val(questionnaire["endDateStr"]);
	$("#userId").val(userId);
	$("#questionnaireState").attr("disabled",false);
}

function addQuestionnaire() {
	questionnaire = new Object();
	setQuestionnaireData(questionnaire);
	$("#questionnaireState").val("0");
	$("#questionnaireState").attr("disabled",true);
	$('#questionnaire_info').window('open');
	$('#questionnaireName').focus();
}

function modifyQuestionnaire() {
	var row = $('#questionnaire-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条调查问卷！');
		return;
	}
	var questionnaireId = row["questionnaireId"];
	var questionnaireState = row["questionnaireState"];
	if (questionnaireState == 1) {
		alert("调查问卷已发布，不能修改内容");
		return;
	}
	var url = common.SERVICE_PREFIX_ADDRESS + 'questionnaire/getQuestionnaireById.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"questionnaireId": questionnaireId,
			"userId":userId,
			"sessionId":sessionId
		},
		success:function(data, textStatus) {
			var questionnaire = data["data"];
			setQuestionnaireData(questionnaire);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	$('#questionnaire_info').window('open');
	$('#questionnaireName').focus();
}

function deleteQuestionnaire() {
	var row = $('#questionnaire-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条调查问卷！');
		return;
	}
	var questionnaireState = row["questionnaireState"];
	if (questionnaireState == 1) {
		alert("调查问卷已发布，不能删除");
		return;
	}
	if (!window.confirm("确定要删除调查问卷？")) {
		return;
	}
	var questionnaireId = row["questionnaireId"];
	var url = common.SERVICE_PREFIX_ADDRESS + 'questionnaire/deleteQuestionnaire.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"questionnaireId": questionnaireId,
			"userId": userId,
			"areaId": areaId,
			"sessionId" : sessionId
		},
		success:function(data, textStatus) {
			doSearchQuestionnaire();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function questionnaireDetail(questionnaireId, questionnaireType, questionnaireState) {
	if (questionnaireState == 1) {
		alert("调查问卷已发布，不能修改内容");
		return;
	}
	$("#detailQuestionnaireId").val(questionnaireId);
	$("#detailQuestionnaireType").val(questionnaireType);
	$("#questionnaire_detail_info").window('open');
	var url = baseurl + "/manage/page/questionnaire_detail.do?dicName=forum";
	$("#questionnaire_detail").attr("src", url);
}

function questionnaireDetailUpload(questionnaireId, questionnaireType, questionnaireState) {
	if (questionnaireState == 1) {
		alert("调查问卷已发布，不能修改内容");
		return;
	}
	$("#detailQuestionnaireId").val(questionnaireId);
	$("#detailQuestionnaireType").val(questionnaireType);
	$("#questionnaire_detail_info").window('open');
	var url = baseurl + "/manage/page/questionnaire_detail_upload.do?dicName=forum";
	$("#questionnaire_detail").attr("src", url);
}

function closeQuestionnaireDetailInfo() {
	$('#questionnaire_detail_info').window('close');
	doSearchQuestionnaire();
}

function closeQuestionnaireStaffsInfo() {
	$('#questionnaire_staffs_info').window('close');
	doSearchQuestionnaire();
}

function saveUserIds(studentIds){
	var questionnaireId = $("#detailQuestionnaireId").val();
	var userIds = studentIds;
	var url = common.SERVICE_PREFIX_ADDRESS + 'questionnaire/createQuestionnaireStaffs.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"questionnaireId": questionnaireId,
			"userIds": userIds,
			"userId": userId,
			"areaId": areaId,
			"sessionId" : sessionId
		},
		success:function(data, textStatus) {
			var retCode = data["retCode"];
			var retMsg = data["retMsg"];
			if (retCode == "1") {
				alert(retMsg);
				$("#detailQuestionnaireId").val("");
			}
			doSearchQuestionnaire();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function addUserIds(questionnaireId, endDate) {
	var eDate = Date.parse(endDate + ' 23:59:59');
	var now = new Date();
	if (now > eDate) {
		alert("调查已截止，不能修改调查人员");
		return;
	}
	$("#detailQuestionnaireId").val(questionnaireId);
	getSelectedPerson('saveUserIds');
}

function delUserIds(questionnaireId, endDate) {
	var eDate = Date.parse(endDate + ' 23:59:59');
	var now = new Date();
	if (now > eDate) {
		alert("调查已截止，不能修改调查人员");
		return;
	}
	$("#detailQuestionnaireId").val(questionnaireId);
	$('#questionnaire_staffs_info').window('open');
	var url = baseurl + "/manage/page/questionnaire_staffs_delete.do?dicName=forum";
	$("#questionnaire_staffs").attr("src", url);
}

function questionnaireResult(questionnaireId, questionnaireType, questionnaireState) {
	if (questionnaireState == 0) {
		alert("调查投票未发布，不能查看结果");
		return;
	}
	$("#detailQuestionnaireId").val(questionnaireId);
	$("#detailQuestionnaireType").val(questionnaireType);
	$("#questionnaire_result_info").window('open');
	var url = baseurl + "/manage/page/questionnaire_result.do?dicName=forum";
	$("#questionnaire_result").attr("src", url);
}

function questionnaireResultExport(questionnaireId, questionnaireType, questionnaireState) {
	if (questionnaireState == 0) {
		alert("调查投票未发布，不能导出结果");
		return;
	}
	var urlpath = baseurl + "/manage/questionnaire/exportQuestionnaireDetail.do?questionnaireId=" + questionnaireId;
	window.location.href = urlpath;
}

$(document).ready(function(){
	
	$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	//取消enter键提交表单
	cancelEnter();
	
	//将焦点自动聚集到查询框里
	document.getElementById('qQuestionnaireName').focus();

	$("#doSearchBtn").click(function(){
		doSearchQuestionnaire();
	});
	
	$("#addBtn").click(function(){
		addQuestionnaire();
	});
	
	$("#modifyBtn").click(function(){
		modifyQuestionnaire();
	});
	
	$("#deleteBtn").click(function(){
		deleteQuestionnaire();
	});
	
	$("#saveBtn").click(function(){
		$("#questionnaireState").attr("disabled",false);
		$("#form1").submit();
	});
	
	$("#cancelBtn").click(function(){
		$('#questionnaire_info').window('close');
	});
	
	$("#form1").validate();
	
	getQuestionnaireGridData();
	
	window.setInterval("setIframeHeight('questionnaire_detail')", 500);
	
	window.setInterval("setIframeHeight('questionnaire_staffs')", 500);
	
	window.setInterval("setIframeHeight('questionnaire_result')", 500);
});