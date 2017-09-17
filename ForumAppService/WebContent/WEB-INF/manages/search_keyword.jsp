<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/manages/tag.jsp"%>
<%@ page language="java"  import="com.chenzl.app.entity.SysUser" %>
<%
SysUser sysUser = (SysUser) request.getSession().getAttribute("sysUser");
String sessionId = request.getSession().getId();
String userId=sysUser.getUserId();
String userName = sysUser.getUserName();
String areaId = sysUser.getAreaId();
String msg = (String) request.getAttribute("msg");
%> 
<html lang="cn">
<head>

<style type="text/css"> 
        #fm 
        { 
            margin: 0; 
            padding: 10px 30px; 
        } 
        .ftitle 
        { 
            font-size: 14px; 
            font-weight: bold; 
            padding: 5px 0; 
            margin-bottom: 10px; 
            border-bottom: 1px solid #ccc; 
        } 
        .fitem 
        { 
            margin-bottom: 5px; 
        } 
        .fitem label 
        { 
            display: inline-block; 
            width: 80px; 
        } 
        
        input[type='file']
        {
        display: inline!important;
        width:auto;
        }
        
        .toolBtns
        {
        float:right;
        }
        
        
        
</style> 

<meta charset="utf-8" />
<title>控制台 - 论坛App管理平台 - 搜索关键字配置</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
<link href="${baseurl}/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${baseurl}/assets/css/font-awesome.min.css" />

<!--[if IE 7]>
		<link rel="stylesheet" href="${baseurl}/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

<!-- page specific plugin styles -->

<link rel="stylesheet"
	href="${baseurl}/assets/css/jquery-ui-1.10.3.full.min.css" />

<!-- easyui -->
<link rel="stylesheet"
	href="${baseurl}/assets/libs/easyui/css/themes/default/easyui.css" />
<link rel="stylesheet" href="${baseurl}/assets/libs/easyui/css/themes/icon.css" />

<link rel="stylesheet" href="${baseurl}/assets/css/ace.min.css" />

<!-- fonts -->
<!--
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
		-->
<!-- ace styles -->

<link rel="stylesheet" href="${baseurl}/assets/css/ace.min.css" />
<link rel="stylesheet" href="${baseurl}/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="${baseurl}/assets/css/ace-skins.min.css" />

<!--[if lte IE 8]>
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->

<script src="${baseurl}/assets/js/ace-extra.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

</head>

<body>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">

			<div class="main-content" style="margin-left: 0px;">

				<div class="page-content">

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="row">

								<div id="saveDlg" class="easyui-dialog" style="width: 600px; height: 400px; padding: 10px 20px;" closed="true"  modal="true">
									
									<form id="saveFm" role="form" action="${baseurl}/manages/searchKeyword/saveKeyword.do" method="post">
										<div class="fitem">
											<label for="keywordName">名称:</label> <input id="keywordName" name="keyword"
												class="easyui-validatebox" required="true">
										</div>
										
										<div class="fitem">
											<label for="keywordLevel">显示:</label> 
											<select id="keywordLevel" name="keywordLevel">
											<option value=0>推荐关键字</option>
											<option value=1>默认关键字</option>
											</select>
										</div>
										<input type="hidden" id="userId" name="createUser"/>
										
										
								<div id="saveDlg-buttons" class="row toolBtns">
									<button id="saveBtn" type="submit" class="btn btn-sm btn-primary">
									<i class="icon-cog"></i>保存</button>
									<button id="cancelBtn" type="button" class="btn btn-sm btn-danger">
									<i class="icon-trash"></i>取消</button>
								</div>
										
									</form>
								</div>
								
								
								
								<div id="updateDlg" class="easyui-dialog" style="width: 600px; height: 400px; padding: 10px 20px;" closed="true" >
									
									<form id="updateFm" role="form" action="${baseurl}/manages/searchKeyword/saveKeyword.do" method="post">
										<div class="fitem">
											<label for="edit-keywordName">名称:</label> <input id="edit-keywordName" name="keyword"
												class="easyui-validatebox" required="true">
										</div>
										
										<div class="fitem">
											<label for="edit-keywordLevel">显示:</label> 
											<select id="edit-keywordLevel" name="keywordLevel">
											<option value=0>推荐关键字</option>
											<option value=1>默认关键字</option>
											</select>
										
										</div>

										<input type="hidden" id="edit-staffId" name="updateUser"/>
										<input type="hidden" id="edit-keywordId" name="keywordId">
								
								<div id="updateDlg-buttons" class="row toolBtns">
								 	<button id="edit-saveBtn" type="submit" class="btn btn-sm btn-primary">
									<i class="icon-cog"></i>保存</button>
									<button id="edit-cancelBtn" type="button" class="btn btn-sm btn-danger">
									<i class="icon-trash"></i>取消</button>

								</div>
										
									</form>
								</div>
								
								</div>
								

								<div class="row">
								<div class="col-xs-12">

									<table id="dg" class="easyui-datagrid" style="height: 350px" title="关键字" rownumbers="true" pagination="true" toolbar="#tb">
										<thead>
											<tr>
												<th field="sliderName" width="80">名称</th>
												<th field="state" width="80">显示</th>
												<th field="creatTime" width="100" align="right">创建日期</th>
												<th field="createUser" width="80" align="right">创建者</th>
												<th field="updateTime" width="100">更新日期</th>
												<th field="updateUser" width="80" align="center">更新者</th>
												<th field="operate" width="80">操作</th>
											</tr>
										</thead>
									</table>

									<div id="tb">
									<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:onClickTbAdd()">添加</a> 
										 <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:onClickTbEdit()">编辑</a> 
										 <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:onClickTbDel()">删除</a>
									</div>
								</div>
								</div>



								<!-- PAGE CONTENT ENDS -->
							</div>
							<!-- /.col -->
						</div>
						<!-- /.row -->
					</div>
					<!-- /.page-content -->
				</div>
				<!-- /.main-content -->

			</div>
			<!-- /.main-container-inner -->
			
			</div>

		<!-- /.main-container -->

		<!-- basic scripts -->

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
			if ("ontouchend" in document) document.write("<script src='${baseurl}/assets/js/jquery.mobile.custom.min.js'>" + "<"+"script>");
		</script>
		<script src="${baseurl}/assets/js/bootstrap.min.js"></script>
		<script src="${baseurl}/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="${baseurl}/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${baseurl}/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="${baseurl}/assets/js/jquery.slimscroll.min.js"></script>
		<script src="${baseurl}/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="${baseurl}/assets/js/jquery.sparkline.min.js"></script>
		<script src="${baseurl}/assets/js/flot/jquery.flot.min.js"></script>
		<script src="${baseurl}/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="${baseurl}/assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="${baseurl}/assets/js/ace-elements.min.js"></script>
		<script src="${baseurl}/assets/js/ace.min.js"></script>
		
		<!-- easyui -->
		<script src="${baseurl}/assets/libs/easyui/js/jquery.easyui.min.js"></script>
		<script src="${baseurl}/assets/libs/easyui/locale/easyui-lang-zh_CN.js"></script>

		<!-- inline scripts related to this page -->

		<!-- 10086 common scripts -->
		<script src="${baseurl}/assets/js/app/common.js"></script>
		
		<script src="${baseurl}/assets/js/app/search_keyword.js"></script>

		<script type="text/javascript">
		 var baseurl = "${baseurl}";
			var sessionId = "<%=sessionId %>";
			var userId ="<%=userId %>";
			var areaId = "<%=areaId %>"; 
		</script>
		
	<%
			if(msg != null){
		
		%> 
			<script type="text/javascript">
				 $.messager.alert("提示",'<%=msg %>'); 
			</script>
	<% 
			}
		%> 
</body>
</html>
