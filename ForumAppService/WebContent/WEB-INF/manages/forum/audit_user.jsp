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
	<meta charset="utf-8" />
	<title>控制台 - 10086随身秘书 - 审核人员管理</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link rel="stylesheet" href="${baseurl}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${baseurl}/assets/css/font-awesome.min.css" />

	<!--[if IE 7]>
	<link rel="stylesheet" href="${baseurl}/assets/css/font-awesome-ie7.min.css" />
	<![endif]-->

	<!-- page specific plugin styles -->

	<link rel="stylesheet" href="${baseurl}/assets/css/jquery-ui-1.10.3.full.min.css" />

	<!-- easyui -->
	<link rel="stylesheet" href="${baseurl}/assets/libs/easyui/css/themes/default/easyui.css" />
	<link rel="stylesheet" href="${baseurl}/assets/libs/easyui/css/themes/icon.css" />

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

	<link rel="stylesheet" type="text/css" href="${baseurl}/assets/libs/ztree/css/zTreeStyle/zTreeStyle.css" >

	<!-- inline styles related to this page -->

	<!-- ace settings handler -->

	<script src="${baseurl}/assets/js/ace-extra.min.js"></script>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
</head>

<body>
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>

	<div class="main-container-inner">

		<div class="main-content" style="margin-left: 0px;">

			<div class="page-content">
				<div class="row">
					<div>
						<div class="row">
							<div class="col-xs-12">
								<div class="widget-box">

									<div class="widget-header widget-header-flat">
										<h4 class="smaller">
											<i class="smaller-80"></i>
											查询条件
										</h4>
									</div>

									<div class="widget-body">
										<div class="widget-main">
											<form class="form-search">
												<%--<div class="row">--%>
												<%--<div class="col-xs-12 col-sm-10">--%>
												<%--<div class="input-group">--%>
												<%--员工：<input id="expertStaff" type="text" placeholder="请输入专家工号或姓名"   style="width: 30%;"/>--%>
												<%--&nbsp;&nbsp;&nbsp;状态：<select id="stateSearch" name="stateSearch" style="width: 15%;" >--%>
												<%--<option value="">----全部----</option>--%>
												<%--<option value="1">启用</option>--%>
												<%--<option value="0">禁用</option>--%>
												<%--</select>--%>
												<%--&nbsp;&nbsp;&nbsp;地市：<div id="areaSearchList" class="form-group"></div>--%>
												<%--<span class="input-group-btn">--%>
												<%--<button id="doSearchNewBtn" type="button" class="btn btn-purple btn-sm">--%>
												<%--查询--%>
												<%--<i class="icon-search icon-on-right bigger-110"></i>--%>
												<%--</button>--%>
												<%--</span>--%>
												<%--</div>--%>
												<%--</div>--%>
												<%--</div>--%>
												<div class="row">
													<div class="form-group">
														<div class="col-sm-3">
															<input id="expertStaff" class="form-control" type="text" placeholder="请输入专家工号或姓名">
														</div>

														<%--<label class="col-sm-1 control-label" for="stateSearch">状态</label>--%>
														<div class="col-sm-2">
															<select id="stateSearch" name="stateSearch" class="form-control">
																<option value="">------状态------</option>
																<option value="1">启用</option>
																<option value="0">禁用</option>
															</select>
														</div>
														<div class="col-sm-2">
															<div id="areaSearchList"></div>
														</div>
														<button id="doSearchNewBtn" type="button" class="btn btn-purple btn-sm">
															查询<i class="icon-search icon-on-right bigger-110"></i>
														</button>
													</div>
												</div>
											</form>
										</div>
									</div>

								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-xs-12">
								&nbsp;
							</div>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<table id="dg" title="审核人员信息列表" class="easyui-datagrid" style="height:350px;" rownumbers="true" pagination="true">
									<thead>
									<tr>
										<th field="userId" width="80">编号</th>
										<th field="userName" width="80">姓名</th>
										<th field="createUser" width="60">创建人</th>
										<th field="createDate" width="80">创建时间</th>
										<th field="state" width="80">状态</th>
										<th field="operate" width="100">操作</th>
									</tr>
									</thead>
								</table>
							</div>
						</div>

						<div style="display:none;">
							<input type="hidden" id="expertId" name="expertId"/>
						</div>
						
						<div class="col-xs-12">&nbsp;</div>

						<div class="row">
							<div class="col-xs-12">
								<p>

									<button id="addBtn" class="btn btn-sm btn-primary">
										<i class="icon-cog"></i>
										增加
									</button>

									<button id="deleteBtn" class="btn btn-sm btn-danger">
										<i class="icon-trash"></i>
										删除
									</button>

								</p>
							</div>
						</div>


						<!-- -----------------将人员选为专家人员------------------------------------------------------- -->
						<div class="row">
							<div class="col-xs-12">
								<div id="staff_info" class="easyui-window" title="人员列表" closed="true" style="width:800px; height:550px; left:155px; top:0px; padding:4px;">
									<div class="row">
										<div class="col-xs-12">
											<div class="widget-box">
												<div class="widget-header widget-header-flat">
													<h4 class="smaller">
														<i class="smaller-80"></i>
														查询条件
													</h4>
												</div>

												<div class="widget-body">
													<div class="widget-main">
														<form class="form-search">
															<div class="row">
																<div class="form-group">
																	
																	<div class="col-sm-3">
																			<input id="searchStaff" type="text" class="form-control search-query" placeholder="请输入员工工号或姓名" />
																	</div>
																
																	<div class="col-sm-3">
																		<div id="areaFilter"></div>
																	</div>
																		
																	
																	<button id="doSearchBtn" type="button" class="btn btn-purple btn-sm">
																						查询
																		<i class="icon-search icon-on-right bigger-110"></i>
																	</button>
																</div>
															</div>
														</form>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<table id="staff-grid" title="人员列表" class="easyui-datagrid" style="height:350px;" rownumbers="true" pagination="true">
												<thead>
												<tr>
													<th field="userId" width="80px">编号</th>
													<th field="userName" width="60">姓名</th>
													<th field="operate" width="80">操作</th>
												</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- 板块选择窗口begin -->
						<div class="row">
							<div class="col-xs-12">
								<div id="category_select" class="easyui-window" title="版块" closed="true" style="width:800px; height:550px; left:155px; top:0px; padding:4px;">
									<div class="col-xs-12" id="categoryGroup">
									</div>
									<div class="col-xs-12">
										<p style="text-align: center;">
											<button id="saveSelectBtn" type="button" class="btn btn-sm btn-primary" onclick="onClickSaveSelect();"><i class="icon-cog"></i>保存</button>
											<button id="cancelSelectBtn" type="button" class="btn btn-sm btn-danger"><i class="icon-trash" onclick="onClickCancelSelect();"></i>取消</button>
										</p>
									</div>
								</div>
							</div>
						</div>
						<!-- 板块选择窗口end -->
						
					</div>   <!-- /.col -->
				</div><!-- /.row -->

			</div><!-- /.page-content -->
		</div><!-- /.main-content -->

	</div><!-- /.main-container-inner -->

</div><!-- /.main-container -->

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

<script type="text/javascript" type="text/javascript">
	if("ontouchend" in document) document.write("<script src='${baseurl}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>
<script src="${baseurl}/assets/js/bootstrap.min.js"></script>
<script src="${baseurl}/assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script type="text/javascript" src="${baseurl}/assets/js/excanvas.min.js"></script>
<![endif]-->

<script type="text/javascript" src="${baseurl}/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/jquery.easy-pie-chart.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/jquery.sparkline.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/flot/jquery.flot.resize.min.js"></script>

<!-- ace scripts -->

<script type="text/javascript" src="${baseurl}/assets/js/ace-elements.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/ace.min.js"></script>

<!-- easyui scripts -->
<script type="text/javascript" src="${baseurl}/assets/libs/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseurl}/assets/libs/easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- ztree scripts -->
<script type="text/javascript" src="${baseurl}/assets/libs/ztree/js/jquery.ztree.all.min.js"></script>

<!-- inline scripts related to this page -->

<!-- 10086 common scripts -->
<script type="text/javascript" src="${baseurl}/assets/js/app/common.js"></script>
<script type="text/javascript" src="${baseurl}/assets/libs/layer/layer.js"></script>
<script type="text/javascript" src="${baseurl}/assets/js/app/audit_user.js"></script>

<script type="text/javascript">
	var baseurl = "${baseurl}";
	var sessionId = "<%=sessionId %>";
	var userId = "<%=userId %>";
	var areaId = "<%=areaId %>";
</script>
</body>
</html>
