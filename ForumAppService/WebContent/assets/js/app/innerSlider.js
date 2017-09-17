var editRow;
		
function getAllSlider() {
	var url = common.SERVICE_PREFIX_ADDRESS + 'slider/getAllInnerSlider.do';
	$("#dg").datagrid({
		"height" : 350,
		"url" : url,
		"method" : "GET",
		"idField" : "sliderId",
		"striped" : true,
		"fitColumns" : true,
		"singleSelect" : true,
		"rownumbers" : true,
		"pagination" : true,
		"nowrap" : false,
		"showFooter" : true,
		columns : [ [ {
			field : 'sliderId',
			title : '类别编号',
			rowspan : 1,
			hidden : true
		}, {
			field : 'sliderName',
			title : '名称',
			rowspan : 1,
			width : 40
		}, {
			field : 'sliderImg',
			title : '图标',
			rowspan : 1,
			width : 50,
			align : 'center',
			formatter:formatIcon
		},{
			field : 'sliderLink',
			title : '链接',
			rowspan : 1,
			width : 100
		},{
			field : 'state',
			title : '显示',
			rowspan : 1,
			width : 40,
			formatter : formatState
		}, {
			field : 'createDate',
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
			field : 'updateDate',
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
		},{
			field : 'sortId',
			title : '排序号',
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
//			console.log("datagrid suc");

		},
		onLoadError : function() {
//			console.log("datagrid error");

		},
		onClickRow : function(rowIndex, rowData) {

		},
		onClickCell : function(rowIndex, field, value) {

		}
	});
}

function formatIcon(val,row,index) {
/*	console.log("formatIcon val:"+val);
console.log("formatIcon row:"+JSON.stringify(row));
console.log("formatIcon index:"+index);*/
	var url = IMAGE_PREFIEX_ADDRESS + val;
	return '<img style="width:57px;height=auto" src='+url+'>';

}

function formatOperate(val, row, index) {
//	console.log("formatOperate val:"+val);
//	console.log("formatOperate row:"+JSON.stringify(row));
//	console.log("formatOperate index:"+index);
	
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
	           (d.getDate());
	           
	return date;
}


function formatState(val,row,index) {
	var state;
	switch(val)
	{
	case 0:
	 state = "否";
	  break;
	case 1:
	state = "是";
	  break;
	default:
	  state = "否";
	}
	return state;
}




function onClickTbAdd() {
	
	var url = common.SERVICE_PREFIX_ADDRESS + "slider/getInnerMaxSortId.do";
	$.ajax({
		url:url,
		method:"get",
		dataType:'json',
		success:function(data, textStatus) {
			
			$("#sortId").val(data["maxSortId"]+1);
			
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	
	$("#saveDlg").dialog("open").dialog('setTitle','添加');
	$("#sortId-error").css("display","none");
	$("#savFm").form("clear");
}

function onClickTbEdit() {
	
	var row = $('#dg').datagrid('getSelected');
//	alert(row["sliderId"]);
	
	if (row) {
		openUpdateDlg(row);
	}else{
		alert("请选中要修改的一条记录");
	}
}

function onClickTbDel() {
	var row = $('#dg').datagrid('getSelected');
//	alert(row["sliderId"]);
	if (row) {
		openDelDlg(row);
	}else{
		alert("请选中要删除的一条记录");
	}
	
}


function onClickEdit(index) {
	
	$('#dg').datagrid('selectRow',index);// 关键在这里 

	 var row = $('#dg').datagrid('getSelected');

	
	
	openUpdateDlg(row);
	

}


function setSliderData(slider) {
	$("#edit-sliderId").val(slider["sliderId"]);
	$("#edit-sliderName").val(slider["sliderName"]);
	$("#edit-state").val(slider["state"]);
	$("#edit-sliderLink").val(slider["sliderLink"]);
	$("#edit-sortId").val(slider["sortId"]);
}

function openUpdateDlg(row) {
	editRow = row;
	
	var url = common.SERVICE_PREFIX_ADDRESS + "slider/getInnerSliderById.do";
	var sliderId = row['sliderId'];
	$.ajax({
		url:url,
		method:"get",
		dataType:'json',
		data: {
			"sliderId": sliderId,
		},
		success:function(data, textStatus) {
			var slider = data["data"];
			setSliderData(slider);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});

	$("#updateDlg").dialog("open").dialog('setTitle','编辑');
	$("#edit-sortId-error").css("display","none");
}

function openDelDlg(row) {
	
	 $.messager.confirm('提示', '确定要删除类别吗?', function (r) {
		 if (r) {
			 var url = common.SERVICE_PREFIX_ADDRESS + 'slider/deleteInnerSlider.do'; 
				
			 $.ajax({
					url:url,
					method:"post",
					dataType:'json',
					data: {
						"sliderId": row["sliderId"],
				
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




function checkAdd() {
	
	var icon = $("#iconFile").val();
	if (icon == null || icon == "") {
		$.messager.alert("提示","请选择图片！");
	}
	else if (checkSortId() == false) {
		$("#sortId-error").css('display',"inline");
	}
	else {
		$("#saveFm").submit();
	}


}

function checkUpdate() {
	
	if (checkUpdateSortId() == true) {
		$("#updateFm").submit();
	}
	else {
		$("#edit-sortId-error").css('display',"inline");
	}

}

function checkSortId() {
	var sortId = $("#sortId").val();
	
	var g = /^[0-9]*[1-9][0-9]*$/;
  
    return g.test(sortId);
 

}

function checkUpdateSortId() {
var sortId = $("#edit-sortId").val();
	
	var g = /^[0-9]*[1-9][0-9]*$/;
  
    return g.test(sortId);
 
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
	$("#edit-userId").val(userId);
	
	getAllSlider();

});			