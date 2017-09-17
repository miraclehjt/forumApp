
var editRow;
var areaList;

//初始
$(document).ready(function(){
	
	//取消enter键提交表单
	cancelEnter();
	//获取地市列表
	getAreas();
	
	//将焦点自动聚集到查询框里
	document.getElementById('expertUser').focus();

	//获取专业人员信息列表
	getExpertGridData();
	
	//获取人员列表
	getStaffGridData();
	
	//点击查询按钮查询审核人员信息
	$("#doSearchNewBtn").click(function(){
		doExpertSearch();
	});
	
	//点击查询按钮进行人员查询
	$("#doSearchBtn").click(function(){
		doSearch();
	});
	
	//点击新增按钮，将人员添加为审核人员
	$("#addBtn").click(function(){
		addExpert();
		document.getElementById('searchStaff').focus();
	});
	
	//点击删除按钮，删除选中的审核人员
	$("#deleteBtn").click(function(){
		deleteExpert();
	});
	
});

//获取专业人员信息列表
function getExpertGridData() {

	var url = common.SERVICE_PREFIX_ADDRESS + 'forum/getAuditStaff.do';
	$("#dg").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"staffId": userId,
			"sessionId": sessionId,
			"areaId":areaId
		},
		"idField": "",
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
			{field:'staffId',title:'工号',rowspan:1,width:80},
			{field:'staffName',title:'姓名',rowspan:1,width:80},
			{field:'areaName',title:'地市',rowspan:1,width:80},
			{field:'categoryName',title:'审核版块',rowspan:1,width:80},
			{field:'state',title:'状态',rowspan:1,width:80,formatter: formatStatus},
			{field:'operate',title:'操作',rowspan:1,width:100,align:'center',formatter:formatOperate1}
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

function formatDate(val, row, index) {

	var timestamp = val["time"];
	var d = new Date(timestamp); // 根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-"
			+ (d.getDate()) + " " + (d.getHours()) + ":" + (d.getMinutes())
			+ ":" + (d.getSeconds());

	return date;
}

function formatStatus(val,row,index) {
	
	var displayStatus = "已启用";
	if (val == "0") {
		displayStatus = "已禁用";
	}
	
	return displayStatus;
}

function formatOperate1(val, row, index){
	
	var state = row["state"];
	
	
	var displayStatus = "启用";
	if (state == "1") {
		displayStatus = "禁用";
	}

	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickDisable(\""
			+ index + "\")'>"+displayStatus+"</a></span>";
	operateRow += "&nbsp;|&nbsp;";
	
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickEditCategoryBtn(\""
		+ index + "\")'>修改版块</a></span>";
	return operateRow;
}


function onClickEditCategoryBtn(index) {
	if (index) {
		$('#dg').datagrid('selectRow',index);
	}
	
	editRow = $('#dg').datagrid('getSelected');
	
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumcategory/getAllCategories.do';
	$.ajax({
		url:url,
		method:"get",
		dataType:'json',
		data: {
			"staffId": staffId,
			"areaId": areaId
		},
		success:function(data, textStatus) {
			var categories = data["data"];
			initCategories(categories);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	
	$('#category_select').window('open');
}


function initCategories(categories) {
	var html = "";
	var selected = false;
	
	html = html + "<div class=\"col-xs-3\">";
	html = html + "<div class=\"radio\">";
	html = html + "<label>";
	html = html + "<input name=\"categoryId\" id=\"allCategoryId\" type=\"radio\" value=\"0\" />";
	html = html + "全部";
	html = html + "</label>";
	html = html + "</div>";
	html = html + "</div>";
	
	for (var i=0; i<categories.length; i++) {
//		if(category["categoryId"] == editRow["categoryId"]) selected=true;
		var category = categories[i];
		html = html + "<div class=\"col-xs-3\">";
		html = html + "<div class=\"radio\">";
		html = html + "<label>";
		html = html + "<input name=\"categoryId\" id=\"" + "areaId" + category["categoryId"] + "\" type=\"radio\" value=\"" + category["categoryId"] + "\" />";
		html = html + category["categoryName"];
		html = html + "</label>";
		html = html + "</div>";
		html = html + "</div>";
	}
	
//	$("input[type='radio']).val(editRow["categoryId"]);
	
	var editCategoryId = editRow["categoryId"];

	$("#categoryGroup").html(html);
	
	$("input[type=radio]").each(function(){
		if($(this).val() == editCategoryId) {
			$(this).prop("checked", true);
		}
		
	});
}


function onClickSaveSelect() {
	var categoryId = $("input[type='radio']:checked").val();
	var auditStaff = editRow["staffId"];
	var url = common.SERVICE_PREFIX_ADDRESS + 'forum/setAuditCategory.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"staffId": staffId,
			"categoryId":categoryId,
			"auditStaff":auditStaff,
			"areaId": areaId
		},
		success:function(data, textStatus) {
			
			$('#category_select').window('close');
			
			$('#dg').datagrid('reload');

		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function onClickCancelSelect() {
	$('#category_select').window('close');
}

function onClickDisable(i) {

	$('#dg').datagrid('selectRow', i);
	var row = $('#dg').datagrid('getSelected');
	var state = row["state"];
	var auditStaff = row["staffId"];
	var states=state==1 ? "禁用" : "启用";
	$.messager.confirm('提示', '确定要'+states+'该审核人员吗?', function(r) {
		if (r) {
			state = state==1 ? "0" : "1";
			var url = common.SERVICE_PREFIX_ADDRESS + "forum/setAuditStaffEnable.do";
			$.ajax({
				url : url,
				method : "post",
				dataType : 'json',
				data : {
					"state":state,
					"staffId": staffId,
					"sessionId": sessionId,
					"areaId":areaId,
					"auditStaff":auditStaff
				},
				success : function(data, textStatus) {
					var retCode = data["retCode"];
					var retMsg = data["retMsg"];
					if (retCode == "1") {	
						getExpertGridData();
					} else {
						$.messager.alert("提示",retMsg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$.messager.alert("提示","操作失败"); 
		
				}
			});
		}
	});
}

//点击查询按钮查询审核人员信息
function doExpertSearch() {
	auditStaff =$("#auditStaff").val();
	var stateSearch=$("#stateSearch").val();
	var areaSearch=$("#areaSearch").val();
	$('#dg').datagrid('load',{
		"auditStaff": auditStaff,	
		"stateSearch": stateSearch,
		"areaSearch": areaSearch,
		"staffId": staffId,
		"sessionId": sessionId,
		"areaId":areaId
	});
}

//删除已选择的审核人员
function deleteExpert(){
	
	var row = $('#dg').datagrid('getSelected');
	if (row == null) {
		$.messager.alert("提示","请先选择一条审核人员信息！");
		return;
	}
	var auditStaff = row["staffId"];
	$.messager.confirm('提示', '确定要删除该审核人员信息吗?', function(r) {
		if (r) {
			var url = common.SERVICE_PREFIX_ADDRESS + 'forum/deleteAuditStaff.do';
			$.ajax({
				url:url,
				method:"post",
				dataType:'json',
				data: {
					"staffId": staffId,
					"areaId": areaId,
					"sessionId" : sessionId,
					"auditStaff":auditStaff
				},
				success:function(data, textStatus) {
					var retCode = data["retCode"];
					var retMsg = data["retMsg"];
					if (retCode == "1") {
					//	$.messager.alert("提示",retMsg);

						$('#dg').datagrid('unselectAll');
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("提示",retMsg);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			});
		}
	});
	
}

//点击新增按钮，打开人员信息div
function addExpert() {
	initStaffAreaFilter(areaList);
	$('#staff_info').window('open');
}

//人员关联操作链接
function formatOperate(val, row, index){
	var staffIdSelect = row['staffId'];
	var areaIdSelect= row['areaId'];
	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='insertExpertStaff(\"" + staffIdSelect + "\",\"" + areaIdSelect + "\")'>关联</a></span>";
	
	return operateRow;
}

//将选择的人员关联为审核人员
function insertExpertStaff(staffIdSelect,areaIdSelect) {

	var url = common.SERVICE_PREFIX_ADDRESS + 'forum/insertAuditStaff.do';
	$.ajax({
		url:url,
		method:"post",
		dataType:'json',
		data: {
			"auditStaff": staffIdSelect,
			"areaIdSelect":areaIdSelect,
			"staffId": staffId,
			"areaId": areaId,
			"sessionId" : sessionId
		},
		success:function(data, textStatus) {
			var retCode = data["retCode"];
			var retMsg = data["retMsg"];
			if (retCode == "1") {	
				//$.messager.alert("提示",retMsg);
				getExpertGridData();
				$('#staff_info').window('close');
			} else {
				$.messager.alert("提示",retMsg);
				$('#staff_info').window('close');
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

//展示人员信息
function getStaffGridData() {
	
	var url = common.SERVICE_PREFIX_ADDRESS + 'forum/getStaffByAreaId.do';
	$("#staff-grid").datagrid({
		"height": 350,
		"url": url,
		"method": "POST",
		"queryParams": {
			"staffId": staffId,
			"areaId": areaId,
			"sessionId": sessionId
		},
		"idField": "staffId",
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
			{field:'areaId',title:'地市编号',rowspan:1,hidden:true},
			{field:'staffId',title:'工号',rowspan:1,width:80},
			{field:'staffName',title:'姓名',rowspan:1,width:60},
			{field:'areaName',title:'地市',rowspan:1,width:60},
			{field:'operate',title:'操作',rowspan:1,width:80,align:'center',formatter:formatOperate}
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

//点击查询按钮，进行人员信息查询
function doSearch() {
	searchStaff = $("#searchStaff").val();
	var areaSearchId = $("#areaStaffFilter").val();
	$('#staff-grid').datagrid('load',{
		"searchStaff": searchStaff,
		"staffId": staffId,
		"areaId": areaId,
		"searchAreaId":areaSearchId,
		"sessionId": sessionId
	});
}

function getAreas() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'forumPost/getAreas.do';
	$.ajax({
		url:url,
		method:"get",
		dataType:'json',
		data: {
			"staffId": staffId,
			"areaId": areaId
		},
		success:function(data, textStatus) {
			try {
				areaList = data["data"];
				initAreas(areaList);
//				initStaffAreaFilter(areaList);
			} catch (e) {
				
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function initAreas(areaList){
	var html = "";
	html = html + "<select id='areaSearch' name='areaSearch' class='form-control'>";
	html = html + "<option value=''>------地市------</option>";
	for (var i = 0; i < areaList.length; i++) {
		var area = areaList[i];
		html = html + "<option value=\"" + area["AREA_ID"] + "\" >";
		html = html + area["AREA_NAME"];
		html = html + "</option>";
	}
	html = html + "</select>";
	$("#areaSearchList").html(html);
}

function initStaffAreaFilter(areaList){
	var html = "";
	html = html + "<select id='areaStaffFilter' name='areaStaffFilter' class='form-control'>";
	html = html + "<option value=''>------地市------</option>";
	for (var i = 0; i < areaList.length; i++) {
		var area = areaList[i];
		html = html + "<option value=\"" + area["AREA_ID"] + "\" >";
		html = html + area["AREA_NAME"];
		html = html + "</option>";
	}
	html = html + "</select>";
	
	$("#areaFilter").html(html);
}

