var editRow;
		
function getAllKeywords() {

	var url = common.SERVICE_PREFIX_ADDRESS + 'searchKeyword/getAllKeywords.do';
	

	$("#dg").datagrid({
		"height" : 350,
		"url" : url,
		"method" : "GET",
		"idField" : "keywordId",
		"striped" : true,
		"fitColumns" : true,
		"singleSelect" : true,
		"rownumbers" : true,
		"pagination" : true,
		"nowrap" : false,
		"showFooter" : true,
		columns : [ [ {
			field : 'keywordId',
			title : '关键字编号',
			rowspan : 1,
			hidden : true
		}, {
			field : 'keyword',
			title : '名称',
			rowspan : 1,
			width : 40
		}, {
			field : 'keywordLevel',
			title : '显示',
			rowspan : 1,
			width : 40,
			formatter : formatLevel
		}, {
			field : 'creatTime',
			title : '创建日期',
			rowspan : 1,
			width : 60,
			align : 'center',
			formatter : formatDate
		}, {
			field : 'createUser',
			title : '创建者',
			rowspan : 1,
			width : 40
		}, {
			field : 'updateTime',
			title : '更新日期',
			rowspan : 1,
			width : 60,
			align : 'center',
			formatter : formatDate
		}, {
			field : 'updateUser',
			title : '更新者',
			rowspan : 1,
			width : 40
		}, {
			field : 'operate',
			title : '操作',
			rowspan : 1,
			width : 80,
			align : 'center',
			formatter : formatOperate
		} ] ],
		onBeforeLoad : function(param) {

		},
		onLoadSuccess : function(data) {
			console.log("datagrid suc");

		},
		onLoadError : function() {
			console.log("datagrid error");

		},
		onClickRow : function(rowIndex, rowData) {

		},
		onClickCell : function(rowIndex, field, value) {

		}
	});
}



function formatOperate(val, row, index) {
	
	var operateRow = "";
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickEdit(\""
			+ index + "\")'>修改</a></span>";
	operateRow += "&nbsp;|&nbsp;";
	operateRow += "<span><a href='javascript:void(0);' onclick='onClickDel(\""
			+ index + "\")'>删除</a></span>";
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


function formatLevel(val,row,index) {
	
	var level;
	
	switch(val)
	{
	case 0:
		level = "推荐关键字";
	  break;
	case 1:
		level = "默认关键字";
	  break;
	default:
		level = "推荐关键字";
	}
	
	return level;
	alert(level);
}




function onClickTbAdd() {
	
	
	$("#saveDlg").dialog("open").dialog('setTitle','添加');

	$("#savFm").form("clear");
	
	$("#keywordName").focus();
}

function onClickTbEdit() {
	
	var row = $('#dg').datagrid('getSelected');
	
	if (row) {
		openUpdateDlg(row);
	}
}

function onClickTbDel() {
	var row = $('#dg').datagrid('getSelected');
	
	if (row) {
		openDelDlg(row);
	}
	
}


function onClickEdit(index) {
	
	$('#dg').datagrid('selectRow',index);// 关键在这里 
	 var row = $('#dg').datagrid('getSelected');

	
	
	openUpdateDlg(row);
	

}


function setKeywordData(keyword) {

	$("#edit-keywordId").val(keyword["keywordId"]);
	$("#edit-keywordName").val(keyword["keyword"]);
	$("#edit-keywordLevel").val(keyword["keywordLevel"]);

}

function openUpdateDlg(row) {
	editRow = row;
	
	var url = common.SERVICE_PREFIX_ADDRESS + "searchKeyword/getKeywordById.do";
	var keywordId = row['keywordId'];
	$.ajax({
		url:url,
		method:"get",
		dataType:'json',
		data: {
			"keywordId": keywordId,
		},
		success:function(data, textStatus) {
			var keyword = data["data"];
			setKeywordData(keyword);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});

	$("#updateDlg").dialog("open").dialog('setTitle','编辑');
	$("#edit-keywordName").focus();

}

function openDelDlg(row) {

	 $.messager.confirm('提示', '确定要删除关键字吗?', function (r) {
		 if (r) {
			 var url = common.SERVICE_PREFIX_ADDRESS + 'searchKeyword/deleteKeyword.do'; 
				
			 $.ajax({
					url:url,
					method:"post",
					dataType:'json',
					data: {
						"keywordId": row["keywordId"],
				
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

function onClickDel(index) {
	$('#dg').datagrid('selectRow',index);// 关键在这里 
	 var row = $('#dg').datagrid('getSelected');
	 
	 openDelDlg(row);

}


function editCtlg() {
	var row = $('#saveDlg').datagrid('getSelected');  
	if (row){  
	    $('#saveDlg').dialog('open');  

	}  
}




$("#saveBtn").click(function(){
	
});

$("#cancelBtn").click(function(){
	$("#saveDlg").dialog("close");
});


$("#edit-saveBtn").click(function(){
	
});

$("#edit-cancelBtn").click(function(){
	$("#updateDlg").dialog("close");
});
		

$(document).ready(function() {
	
	$("#userId").val(userId);
	$("#edit-staffId").val(userId);
	
	getAllKeywords();

});			