var kb= new Object();
var knowledge = {"kbId":'', "kbId":''};

/*
function formatOperate(val, row, index){
	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickEdit(\""
			+ index + "\")'>修改</a></span>";
	operateRow += "&nbsp;|&nbsp;";
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickDel(\""
			+ index + "\")'>删除</a></span>";
	return operateRow;
}

function onClickDel(index) {
	$('#kb_learn-grid').datagrid('selectRow',index);// 关键在这里 
	 var row = $('#kb_learn-grid').datagrid('getSelected');
	
	 openDelDlg(row);

}
function onClickEdit(index) {
	
	$('#kb_learn-grid').datagrid('selectRow',index);// 关键在这里 

	 var row = $('#kb_learn-grid').datagrid('getSelected');
	
	openUpdateDlg(row);
}*/
function formatDate(val,row,index) {
	var timestamp = val["time"];
	var d = new Date(timestamp);    //根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + 
	           (d.getMonth() + 1) + "-" +
	           (d.getDate());
	           
	return date;
}

function getSysKbGridData() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'learn/getSysKbList.do';
	var kbTitle = $("#qKbTitle").val();
	$("#kb_learn-grid").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"kbTitle": kbTitle,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		"idField": "kbId",
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
			{field:'kbId',title:'知识学习编号',rowspan:1,hidden:true},
			{field:'kbTitle',title:'知识名称',rowspan:1,width:60,align:'center'},
			{field:'kbLink',title:'知识链接',rowspan:1,width:80,align:'center'},
			{field:'content',title:'知识描述',rowspan:1,width:80,align:'center'},
			{field:'createDate',title:'知识发布时间',rowspan:1,width:60,formatter : formatDate,align:'center'},
			{field:'updateDate',title:'知识结束时间',rowspan:1,width:60,formatter : formatDate,align:'center'},
			{field:'createUser',title:'创建人',rowspan:1,width:40,hidden:true,align:'center'},
			{field:'updateUser',title:'修改人',rowspan:1,width:40,hidden:true,align:'center'},
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

function doSearchKb() {
	var kbTitle = $("#qKbTitle").val();
	$('#kb_learn-grid').datagrid('load',{
		"kbTitle": kbTitle,
		"userId": userId,
		"areaId": areaId,
		"sessionId": sessionId
	});
}

function addKb() {
	kb = new Object();
	setKbData(kb);
	$('#kb_info').window('open');
	$('#kbTitle').focus();
}

function modifyKb() {
	var row = $('#kb_learn-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条岗位认证！');
		return;
	}
	var kbId = row["kbId"];
	alert(kbId);
	var url = common.SERVICE_PREFIX_ADDRESS + 'learn/getKbById.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"kbId": kbId,
			"userId":userId,
			"sessionId":sessionId
		},
		success:function(data, textStatus) {
			var kb = data["data"];
			setKbData(kb);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	$('#kb_info').window('open');
	$('#qKbTitle').focus();
}

function deleteKb() {
	var row = $('#kb_learn-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条岗位认证！');
		return;
	}
	if (!window.confirm("确定要删除岗位认证？")) {
		return;
	}
	
	var kbId = row['kbId'];
	var url = common.SERVICE_PREFIX_ADDRESS + 'learn/deleteKb.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"kbId": kbId,
			"userId": userId,
			"areaId": areaId,
			"sessionId" : sessionId
		},
		success:function(data, textStatus) {
			doSearchKb();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function setKbData(kb) {
	//alert(kb["kbId"]);
	$("#kbId").val(kb["kbId"]);
	$("#kbTitle").val(kb["kbTitle"]);
	$("#kbLink").val(kb["kbLink"]);
	$("#content").val(kb["content"]);
	$("#createDate").val(kb["createDate"]);
	$("#updateDate").val(kb["updateDate"]);
	$("#userId").val(userId);
}

$("#saveBtn").click(function(){
   $("#form1").submit();
//	saveCertificationAttachment();
});

$("#cancelBtn").click(function(){
	$("#saveDlg").dialog("close");
});


$("#edit-saveBtn").click(function(){
	modifyKb();
});

$("#edit-cancelBtn").click(function(){
	$("#updateDlg").dialog("close");
});
$(document).ready(function(){
	
	//取消enter键提交表单功能
	cancelEnter();
	
	//将焦点自动聚集到查询框里
	document.getElementById('qKbTitle').focus();

	/*
	 * Translated default messages for the jQuery validation plugin.
	 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
	 */
	$.extend($.validator.messages, {
		required: "这是必填字段",
		remote: "请修正此字段",
		email: "请输入有效的电子邮件地址",
		url: "请输入有效的网址",
		date: "请输入有效的日期",
		dateISO: "请输入有效的日期 (YYYY-MM-DD)",
		number: "请输入有效的数字",
		digits: "只能输入正整数数字",
		creditcard: "请输入有效的信用卡号码",
		equalTo: "你的输入不相同",
		extension: "请输入有效的后缀",
		maxlength: $.validator.format("最多可以输入 {0} 个字符"),
		minlength: $.validator.format("最少要输入 {0} 个字符"),
		rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
		range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
		max: $.validator.format("请输入不大于 {0} 的数值"),
		min: $.validator.format("请输入不小于 {0} 的数值")
	});
	
	$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	$("#doSearchBtn").click(function(){
		doSearchKb();
	});
	
	$("#addBtn").click(function(){
		addKb();
	});
	
	$("#modifyBtn").click(function(){
		modifyKb();
	});
	
	$("#deleteBtn").click(function(){
		deleteKb();
	});
	
	$("#cancelBtn").click(function(){
		$('#kb_info').window('close');
	});
	

/*	$("#doAddKbSearchBtn").click(function(){
		doAddKbSearch();
	});*/
	
	getSysKbGridData();
	
});