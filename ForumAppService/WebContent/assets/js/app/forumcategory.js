var questionnaire = new Object();

function formatCategoryType(val, row, index) {
	var categoryType = row['categoryType'];
	var operateRow = "";
	switch(categoryType) {
		case 1 :
			operateRow = "互动提问";
			break;
		case 2 :
			operateRow = "应急咨询";
			break;
		case 3 :
			operateRow = "调查评测";
			break;
		default:
			operateRow = "互动提问";
	}
	return operateRow;
}
	function formatDate(val,row,index) {
		//var timestamp = val["time"];
		var d = new Date();    //根据时间戳生成的时间对象
		var date = (d.getFullYear()) + "-" + 
		           (d.getMonth() + 1) + "-" +
		           (d.getDate()) + " " + 
		           (d.getHours()) + ":" + 
		           (d.getMinutes()) + ":" + 
		           (d.getSeconds());
		           
		return date;
	}

function getForumCategoryGridData() {
	
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumcategory/queryForumCategoryForPage.do';
	alert(url);
	var categoryName = $("#qcategoryName").val();
	$("#shequcategory-grid").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"categoryName": categoryName,
			"userId": userId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		"idField": "categoryId",
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
			{field:'categoryId',title:'版块Id',rowspan:1,hidden:true},
			{field:'categoryName',title:'版块名称',rowspan:1,width:80},
			{field:'categoryType',title:'版块类型',rowspan:1,width:40,formatter:formatCategoryType},
			{field:'createUser',title:'创建人',rowspan:1,width:40},
			{field:'updateUser',title:'修改人',rowspan:1,width:40},
			{field:'createDate',title:'创建时间',rowspan:1,width:40,formatter:formatDate},
			{field:'updateDate',title:'修改时间',rowspan:1,width:40,formatter:formatDate}
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

function doSearchCategory() {
	var categoryName = $("#qCategoryName").val();
	$('#shequcategory-grid').datagrid('load',{
		"categoryName": categoryName,
		"userId": userId,
		"areaId": areaId,
		"sessionId": sessionId
	});
}

function setCategoryData(category) {
	$("#categoryId").val(category["categoryId"]);
	$("#categoryName").val(category["categoryName"]);
	$("#categoryType").val(category["categoryType"]);
	$("#userId").val(userId);
}

function addCategory() {
	category = new Object();
	alert("增加");
	category["categoryType"] = "1";
	setCategoryData(category);
	$('#categories_info').window('open');
	$('#categoryName').focus();
}

function canNotOperate(row){
	var categoryName = row["categoryName"];
	var categoryType = row["categoryType"];
	if ((categoryName == '互动提问' && categoryType == 1) || (categoryName == '应急咨询' && categoryType == 2)) {
		return true;
	}
	return false;
}

function modifyCategory() {
	var row = $('#shequcategory-grid').datagrid('getSelected');
	if (row == null) {
		alert('请选择一条版块！');
		return;
	}
	if (canNotOperate(row)) {
		alert('初始版块不允许修改');
		return;
	}
	var categoryId = row["categoryId"];
	alert(categoryId);
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumcategory/getCategoryById.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"categoryId": categoryId,
			"userId":userId,
			"sessionId":sessionId
		},
		success:function(data, textStatus) {
			var category = data["data"];
			setCategoryData(category);
			alert(category);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	$('#categories_info').window('open');
	$('#categoryName').focus();
}

function deleteCategory() {
	var row = $('#shequcategory-grid').datagrid('getSelected');
	if (row == null) {
		alert('请先选择一条版块！');
		return;
	}
	if (canNotOperate(row)) {
		alert('初始版块不允许删除');
		return;
	}
	if (!window.confirm("确定要删除版块及版块下的问题？")) {
		return;
	}
	var categoryId = row["categoryId"];
	alert("删除的版块ID"+categoryId);
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumcategory/deleteForumCategory.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data: {
			"categoryId": categoryId,
			"userId": userId,
			"areaId": areaId,
			"sessionId" : sessionId
		},
		success:function(data, textStatus) {
			doSearchCategory();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function submitForm(){
	alert("保存");
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumcategory/getForumCategoryByType.do';
	alert("路径"+url);
//	var categoryType = $("#categoryType option:selected").val();
	var categoryType = $("#categoryType").attr("value");
	if (categoryType == null || categoryType == '') {
		alert('版块类型不能为空！');
		return;
	}
	if (categoryType == '1') {
		$("#form1").submit();
		return;
	}
	if (categoryType == '2') {
		$.ajax({
			url:url,
			method:"post",
			dataType:"json",
			data: {
				"categoryType": categoryType,
				"userId": userId,
				"areaId": areaId,
				"sessionId" : sessionId
			},
			success:function(data, textStatus) {
				var list = data['data'];
				if (list != null && list.length > 0) {
					alert('应急咨询版块只能创建一个！');
					return;
				} else {
					$("#form1").submit();
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
	}
}

$(document).ready(function(){
	
	//取消enter键提交表单功能
	cancelEnter();
	
	//将焦点自动聚集到查询框里
	document.getElementById('qCategoryName').focus();
	
	$("#doSearchBtn").click(function(){
		doSearchCategory();
	});
	
	$("#addBtn").click(function(){
		addCategory();
	});
	
	$("#modifyBtn").click(function(){
		modifyCategory();
	});
	
	$("#deleteBtn").click(function(){
		deleteCategory();
	});
	
	$("#saveBtn").click(function(){
		submitForm();
	});
	
	$("#cancelBtn").click(function(){
		$('#categories_info').window('close');
	});
	
	$("#form1").validate();
	
	getForumCategoryGridData();
});