<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="./tag.jsp"%>
<html>
<head>
    <title>论坛App管理平台</title>
    <link rel="stylesheet" href="${baseurl}/assets/plugins/yui/tabview.css" />
    <style>
    	.iframe_container{position:absolute; left:0; top:32px; bottom:0; right:0;height:auto; }
    </style>
</head>
<body onload="init();" style="margin:0;padding:0; background:#fff">
<div id="yuicontainer">
</div>
	
	<!--[if !IE]> -->
		<script type="text/javascript" src="${baseurl}/assets/js/jquery-2.0.3.min.js"></script>
	<!-- <![endif]-->
	<!--[if IE 9]>
		<script type="text/javascript" src="${baseurl}/assets/js/jquery-2.0.3.min.js"></script>
	<![endif]-->
	<!--[if lte IE 8]>
		<script src="${baseurl}/assets/js/jquery-1.9.1.min.js"></script>
		<script src="${baseurl}/assets/js/jquery-migrate-1.1.0.js"></script>
		<script src="${baseurl}/assets/js/html5shiv.js"></script>
		<script src="${baseurl}/assets/js/respond.min.js"></script>
		<script src="${baseurl}/assets/js/json2.js"></script>
	<![endif]-->
	<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${baseurl}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
	</script>
    <script src="${baseurl}/assets/plugins/yui/yahoo-dom-event.js"></script>
    <script src="${baseurl}/assets/plugins/yui/connection-min.js"></script>
    <script src="${baseurl}/assets/plugins/yui/element-beta-min.js"></script>
    <script src="${baseurl}/assets/plugins/yui/tabview-min.js"></script>
    <script src="${baseurl}/assets/plugins/yui/tabview-min.js"></script>
    <script type="text/javascript" src="${baseurl}/assets/libs/layer/layer.js"></script>
    <script type='text/javascript' charset='utf-8'>
	
        var tabView = new YAHOO.widget.TabView();
        var tabHeight;
        //最多打开tab个数
        var tabNum =8;
        //tab index
        var index;
        init = function()
        {
            tabView.appendTo('yuicontainer');
            addTab('0', '工作台', '${baseurl}/manages/page/home.do');

        }

        addTab = function(id,label,src)
        {
            //同一菜单只允许打开一次,多次打开视为切换tab页
            for(var tabIndex=0;tabIndex<tabView.get("tabs").length;tabIndex++){
                var tab=tabView.getTab(tabIndex);
                if(tab.get("id")==id){
                    tabView.set('activeTab',tab,true);
                    return;
                }
            }

            //if(document.getElementById("iFrame"+id))
            //{
            //是否一个ID链接只能打开一个tab
            //}
            //判断是否tab个数达到限定个数
            if(tabView.get("tabs").length >= tabNum)
            {
                index = layer.confirm("打开窗口个数已经达到"+tabNum+"个,新开窗口将会关闭第一个窗口,是否继续?", {
                	  btn: ['确定','取消'] //按钮
                	}, function(){
                		//删除第一个窗口
                        tabView.removeTab(tabView.getTab("0"));
                        var labelText = label;
                        var content = '<div class="iframe_container"><div id="tobarDiv'+id+'" class="tobarDiv"></div><iframe class="tabFrame" id="iFrame'+id+'" name="iFrame'+id+'" frameBorder=0 scrolling="auto" width="100%" height="100%" resizeable="yes"  src="'+src+'"></iframe></div>';
                        var tab = new YAHOO.widget.Tab({ label: labelText,content:content });
                        tabView.addTab(tab);
                        tabView.set('activeTab',tab,true);
                        tab.set('title',labelText);
                        tab.set("id",id);
                        tab.addListener('dblclick',dblClickEvent);
                        layer.close(index);
                	}, function(){
                	  //layer.msg('已取消');
                	  layer.msg("已取消");
                });
                
            }else{
            	var labelText = label;
                var content = '<div class="iframe_container"><div id="tobarDiv'+id+'" class="tobarDiv"></div><iframe class="tabFrame" id="iFrame'+id+'" name="iFrame'+id+'" frameBorder=0 scrolling="auto" width="100%" height="100%" resizeable="yes"  src="'+src+'"></iframe></div>';
                var tab = new YAHOO.widget.Tab({ label: labelText,content:content });
                tabView.addTab(tab);
                tabView.set('activeTab',tab,true);
                tab.set('title',labelText);
                tab.set("id",id);
                tab.addListener('dblclick',dblClickEvent);
            }
            
        }
        //双击TAB头关闭事件
        function dblClickEvent(e)
        {
            var tab=tabView.get('activeTab');
            var id=tab.get("id");
            document.getElementById("iFrame"+id).src="";
            tabView.removeTab(tab);
        }

        //获取当前TAB的resource_id
        function getResourceId()
        {
            if(tabView.get('activeTab'))
            {
                return tabView.get('activeTab').get("id");
            }
            else
            {
                return null;
            }

        }

        function setTobarDiv(id,content){
/*            var tobarDiv=document.getElementById("tobarDiv"+id);
            if(tobarDiv!=null&&tobarDiv){
                tobarDiv.innerHTML=content;
            }*/
        }

        /**
         * 关闭菜单
         * tabId
         *菜单编号
         */
        function closeTab(tabId)
        {
            for(var tabIndex=0;tabIndex<tabView.get("tabs").length;tabIndex++){
                var tab=tabView.getTab(tabIndex);
                if(tab.get("id")==tabId){
                    document.getElementById("iFrame"+tabId).src="";
                    tabView.removeTab(tab);
                    return;
                }
            }
        }


    </script>
</body>
</html>