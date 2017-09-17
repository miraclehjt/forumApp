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
<title>控制台 - 论坛App配置- 提问审核</title>
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

<link rel="stylesheet"
	href="${baseurl}/assets/css/jquery-ui-1.10.3.full.min.css" />

<!-- easyui -->
<link rel="stylesheet"
	href="${baseurl}/assets/libs/easyui/css/themes/default/easyui.css" />
<link rel="stylesheet"
	href="${baseurl}/assets/libs/easyui/css/themes/icon.css" />

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

<link rel="stylesheet" type="text/css"
	href="${baseurl}/assets/libs/ztree/css/zTreeStyle/zTreeStyle.css">

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

					<div class="row" style="margin-left: 0px; margin-right: 0px;">

						<div>

							<div class="row">
								<div class="col-xs-12">
									<div class="widget-box">

										<div class="widget-header widget-header-flat">
											<h4 class="smaller">
												<i class="smaller-80"></i> 查询条件
											</h4>
										</div>

										<div class="widget-body">
											<div class="widget-main">
												<form class="form-search">
													<div class="row">
														<div class="col-xs-12 col-sm-11">
															<div class="input-group">
																<div class="col-sm-3">
																	标题：<input id="postNameSearch" type="text"
																		placeholder="请输入提问标题" /> &nbsp;&nbsp;&nbsp;
																</div>
																<div class="col-sm-3">
																	审核状态：<select id="auditStateSearch"
																		name="auditStateSearch">
																		<option value="">----请选择----</option>
																		<option value="0">未审核</option>
																		<option value="1">审核通过</option>
																		<option value="2">审核不通过</option>
																	</select>
																</div>
																<div class="col-sm-4">
																	<div id="ForumCategoryList"></div>
																</div>
																</select> <span class="input-group-btn">
																	<button id="doSearchNewBtn" type="button"
																		class="btn btn-purple btn-sm">
																		查询 <i class="icon-search icon-on-right bigger-110"></i>
																	</button>
																</span>
															</div>
														</div>
													</div>
												</form>
											</div>
										</div>

									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-xs-12">&nbsp;</div>
							</div>

							<div class="row">
								<div class="col-xs-12">
									<table id="forum-grid" title="提问审核列表" class="easyui-datagrid"
										style="height: 350px;" rownumbers="true" pagination="true">
										<thead>
											<tr>
												<th field="postTitle" width="100">提问标题</th>
												<th field="postContent" width="100">提问内容</th>
												<th field="categoryName" width="60">提问板块</th>
												<th field="postType" width="60">提问类型</th>
												<th field="kbTitle" width="60">知识标题</th>
												<th field="auditUser" width="60">审核人</th>
												<th field="auditState" width="60">审核状态</th>
												<th field="auditComment" width="100">审核意见</th>
												<th field="audiDate" width="80">审核时间</th>
												<th field="operate" width="80">审核</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>

							<!-- -----------------提问审核------------------------------------------------------- -->
							<div class="row">
								<div class="col-xs-12">
									<div id="mk_activity_info" class="easyui-window" title="审核信息"
										closed="true"
										style="width: 800px; height: 600px; left: 155px; top: 20px; padding: 4px;">
										<table id="post-grid" border="0px" cellpadding="20px"
											cellspacing="20px">
											<tr>
												<td width="40%;">提问编号：<input type="text" id="postId"
													readonly="readonly" style="width: 80%;"></td>
											</tr>
											<tr>
												<td width="40%;">提问标题：<input type="text" id="postTitle"
													readonly="readonly" style="width: 80%;"></td>
											</tr>
											<tr>
												<td width="40%;">提问内容：<input type="text"
													id="postContent" readonly="readonly" style="width: 80%;"></td>
											</tr>
											<tr>
												<td width="40%;">意见：<select id="auditState"
													name="auditState" style="width: 150px;"
													onchange="selectOpinion()">
														<option value="1">同意</option>
														<option value="2">不同意</option>
												</select>
												<span id="huifu">
												     是否参与回复：
												  <input type="checkbox" id="checkYes" onchange="changecheckYes()">是
												</span>
												</td>
											</tr>
											<tr>
												<td width="40%;">
													<div id="huifudisagree">
														回复内容:
														<textarea id="huifuComment"
															style="width: 80%; height: 80px;"></textarea>
													</div>
													<span id="notice">
												     是否给提问者发短信：
												  <input type="checkbox" id="checkNotice" name="noticeFlag" value="1" onchange="noticeMsg()">是
												</span>
												</td>
											</tr>
											
											<tr>
												<td width="40%;">
													<div id="disagree">
														不同意理由:
														<textarea id="auditComment"
															style="width: 80%; height: 80px;"></textarea>
													</div>
												</td>
											</tr>
										</table>
										<p style="text-align: center; margin-top: 20px;">
											<button id="saveBtn" type="button"
												class="btn btn-sm btn-primary">
												<i class="icon-cog"></i>保存
											</button>
											<button id="cancelBtn" type="button"
												class="btn btn-sm btn-danger">
												<i class="icon-trash"></i>取消
											</button>
										</p>
									</div>
								</div>
							</div>
							<!-- -------------------------------------------------------------------------- -->
							<!-- -----------------审核详情------------------------------------------------------- -->
							<div class="row">
								<div class="col-xs-9">
									<div id="detail_info" class="easyui-window" title="审核详情"
										closed="true"
										style="width: 800px; height: 400px; left: 155px; top: 20px; padding: 4px;">
										<table id="post-grid" border="0px" cellpadding="20px"
											cellspacing="20px">
											<tr>
												<td width="40%;">提问标题：<span id="postTitleDetail"></span></td>
											</tr>
											<tr>
												<td width="40%;">提问内容：<span id="postContentDetail"></span></td>
											</tr>
											<tr>
												<td width="40%;">审核状态：<span id="auditStateDetail"></span></td>
											</tr>
											<tr>
												<td width="40%;">审核意见：<span id="auditCommentDetail"></span></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<!-- -------------------------------------------------------------------------- -->
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
	<script type="text/javascript"
		src="${baseurl}/assets/js/jquery-2.0.3.min.js"></script>
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

	<script type="text/javascript"
		src="${baseurl}/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/flot/jquery.flot.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/flot/jquery.flot.pie.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/flot/jquery.flot.resize.min.js"></script>

	<!-- ace scripts -->

	<script type="text/javascript"
		src="${baseurl}/assets/js/ace-elements.min.js"></script>
	<script type="text/javascript" src="${baseurl}/assets/js/ace.min.js"></script>

	<!-- easyui scripts -->
	<script type="text/javascript"
		src="${baseurl}/assets/libs/easyui/js/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/libs/easyui/locale/easyui-lang-zh_CN.js"></script>

	<!-- ztree scripts -->
	<script type="text/javascript"
		src="${baseurl}/assets/libs/ztree/js/jquery.ztree.all.min.js"></script>

	<!-- inline scripts related to this page -->

	<!-- 10086 common scripts -->
	<script type="text/javascript"
		src="${baseurl}/assets/js/app/common.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/libs/layer/layer.js"></script>
	<script type="text/javascript"
		src="${baseurl}/assets/js/app/forum_post.js"></script>

	<script type="text/javascript">
		var baseurl = "${baseurl}";
			var sessionId = "<%=sessionId %>";
			var userId = "<%=userId %>"; 
			var areaId = "<%=areaId %>";
		</script>
</body>
</html>
