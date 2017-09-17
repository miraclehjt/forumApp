var news= new Object();
var newsId = {"newsId":'', "newsId":''};

function formatDate(val,row,index) {
	var timestamp = val["createDate"];
	var d = new Date(timestamp);    //根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + 
	           (d.getMonth() + 1) + "-" +
	           (d.getDate());
	           
	return date;
}

function getSysNewsGridData() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'news/getNewsList.do';
	var newsTitle = $("#qnewsTitle").val();
	$("#news_list-grid").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"newsTitle": newsTitle,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		"idField": "newsId",
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
			{field:'newsId',title:'新闻 编号',rowspan:1,hidden:true},
			{field:'newsTitle',title:'新闻标题',rowspan:1,width:60,align:'center'},
			{field:'newsLink',title:'新闻链接',rowspan:1,width:80,align:'center'},
			{field:'description',title:'新闻描述',rowspan:1,width:80,align:'center'},
			{field:'createDate',title:'新闻发布时间',rowspan:1,width:60,formatter : formatDate,align:'center'},
			{field:'endDate',title:'新闻结束时间',rowspan:1,width:60,formatter : formatDate,align:'center'},
			{field:'createUser',title:'发布人',rowspan:1,width:40,hidden:true,align:'center'},
			{field:'updateUser',title:'更新人',rowspan:1,width:40,hidden:true,align:'center'},
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

function doSearchNews() {
	var newsTitle = $("#qnewsTitle").val();
	$('#news_list-grid').datagrid('load',{
		"newsTitle": newsTitle,
		"userId": userId,
		"areaId": areaId,
		"sessionId": sessionId
	});
}

function addNews() {
	news = new Object();
	setNewsData(news);
	$('#news_info').window('open');
	$('#newsTitle').focus();
}

function modifyNews() {
	var row = $('#news_list-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条新闻！');
		return;
	}
	var newsId = row["newsId"];
	alert(newsId);
	var url = common.SERVICE_PREFIX_ADDRESS + 'news/getNewsById.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"newsId": newsId,
			"userId":userId,
			"sessionId":sessionId
		},
		success:function(data, textStatus) {
			var news = data["data"];
			setNewsData(news);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	$('#news_info').window('open');
	$('#qnewsTitle').focus();
}

function deleteNews() {
	var row = $('#news_list-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条新闻 ！');
		return;
	}
	if (!window.confirm("确定要删除新闻？")) {
		return;
	}
	
	var newsId = row['newsId'];
	var url = common.SERVICE_PREFIX_ADDRESS + 'news/deleteNews.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"newsId": newsId,
			"userId": userId,
			"areaId": areaId,
			"sessionId" : sessionId
		},
		success:function(data, textStatus) {
			doSearchNews();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function setNewsData(news) {
	$("#newsId").val(news["newsId"]);
	$("#newsTitle").val(news["newsTitle"]);
	$("#newsLink").val(news["newsLink"]);
	$("#description").val(news["description"]);
	$("#createDate").val(news["createDate"]);
	$("#endDate").val(news["endDate"]);
	$("#userId").val(userId);
}

$("#saveBtn").click(function(){
   $("#form1").submit();
});

$("#cancelBtn").click(function(){
	$("#saveDlg").dialog("close");
});


$("#edit-saveBtn").click(function(){
	modifyNews();
});

$("#edit-cancelBtn").click(function(){
	$("#updateDlg").dialog("close");
});
$(document).ready(function(){
	
	//取消enter键提交表单功能
	cancelEnter();
	
	//将焦点自动聚集到查询框里
	document.getElementById('qnewsTitle').focus();

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
		doSearchNews();
	});
	
	$("#addBtn").click(function(){
		addNews();
	});
	
	$("#modifyBtn").click(function(){
		modifyNews();
	});
	
	$("#deleteBtn").click(function(){
		deleteNews();
	});
	
	$("#cancelBtn").click(function(){
		$('#news_info').window('close');
	});
	

/*	$("#doAddKbSearchBtn").click(function(){
		doAddKbSearch();
	});*/
	
	getSysNewsGridData();
	
});