

$(document).ready(function() {

    $.ajax({
        type: 'Get',
        url: baseurl + '/notice/getStaffZTree.do?staffId=' + staffId + '&areaId=' + areaId + '&deptId=0001',
        dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
        success: function(data) {
            $.fn.zTree.init($("#treeDemo"), setting, data['data']);
        },
        error: function(msg) {
            alert(" 数据加载失败！" + msg);
        }
    });


});

var setting = {
    view: {
        selectedMulti: false
    },
    check: {
        enable: true,
        autoCheckTrigger: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeCheck: beforeCheck,
        onCheck: onCheck
    }
};

var code, log, className = "dark";

function beforeCheck(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    console.log("[ " + getTime() + " beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
    return (treeNode.doCheck !== false);
}

function onCheck(e, treeId, treeNode) {
    var nodeLevel = treeNode.nodeLevel;
//    var nodeValue = treeNode.value;

    var html = "";
    if ("staff" == nodeLevel) {
        html = "<li id='"+treeNode.id+"'><input nodeId="+treeNode.id+" class='select-checkbox' type='checkbox' value='" + treeNode.value + "'/>" + treeNode.name + "</li>";
        if (treeNode.checked) {
            $('#select-ul').append(html);
        } else {
            $('#select-ul li[id=\"' + treeNode.id + '\"]').remove();
        }
    } 
    
}
function getChildNodes(treeNode) { 
    var childNodes = ztree.transformToArray(treeNode); 
    var nodes = new Array(); 
    for(var i = 0; i < childNodes.length; i++) { 
         nodes[i] = childNodes[i].id; 
    } 
    return nodes.join(","); 
}
/*获取某个节点下的所有叶子节点*/
function getAllChildrenNodes(treeNode){
	var result = "";
    if (treeNode.isParent) {
      var childrenNodes = treeNode.children;
      if (childrenNodes) {
          for (var i = 0; i < childrenNodes.length; i++) {
        	  if(childrenNodes[i].isParent){
        		  getAllChildrenNodes(childrenNodes[i]);
        	  }else{
        		  result += ',' + childrenNodes[i].id;
        	  }
          }
      }
  }
  return result;
}

//取消已选中节点
function cancelSelectNode() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    $("input[type='checkbox']:checked").each(function() {
        var id = $(this).attr('nodeId');
        var node = zTree.getNodeByParam("id", id);
        zTree.checkNode(node, false, true);
        $(this).parent().remove();
    });
}

function ensureSlectPerson(){
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	var selectIds = "";
	$("input[type='checkbox']").each(function() {
        var id = $(this).val();

        selectIds += id + ',';
    });
	var callbackName = $('#callbackName').val();
	if(selectIds == ""){
		parent.layer.msg("未选择人员！");
	}else{
		try{
			eval("parent." + callbackName + "('" + selectIds + "');");
			parent.layer.msg("选择人员成功！");
		}catch(e){
			console.log(e);
		}
	}
	
	
	parent.layer.close(index);
}

function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='" + className + "'>" + str + "</li>");
    if (log.children("li").length > 6) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}

function getTime() {
    var now = new Date(),
        h = now.getHours(),
        m = now.getMinutes(),
        s = now.getSeconds(),
        ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}

function checkNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        type = e.data.type,
        nodes = zTree.getSelectedNodes();
    if (type.indexOf("All") < 0 && nodes.length == 0) {
        alert("请先选择一个节点");
    }

    if (type == "checkAllTrue") {
        zTree.checkAllNodes(true);
    } else if (type == "checkAllFalse") {
        zTree.checkAllNodes(false);
    } else {
        var callbackFlag = $("#callbackTrigger").attr("checked");
        for (var i = 0, l = nodes.length; i < l; i++) {
            if (type == "checkTrue") {
                zTree.checkNode(nodes[i], true, false, callbackFlag);
            } else if (type == "checkFalse") {
                zTree.checkNode(nodes[i], false, false, callbackFlag);
            } else if (type == "toggle") {
                zTree.checkNode(nodes[i], null, false, callbackFlag);
            } else if (type == "checkTruePS") {
                zTree.checkNode(nodes[i], true, true, callbackFlag);
            } else if (type == "checkFalsePS") {
                zTree.checkNode(nodes[i], false, true, callbackFlag);
            } else if (type == "togglePS") {
                zTree.checkNode(nodes[i], null, true, callbackFlag);
            }
        }
    }
}

function setAutoTrigger(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.check.autoCheckTrigger = $("#autoCallbackTrigger").attr("checked");
    $("#autoCheckTriggerValue").html(zTree.setting.check.autoCheckTrigger ? "true" : "false");
}
