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
		<title>控制台 - 论坛App控制台-新闻配置</title>
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
		<link rel="stylesheet" href="${baseurl}/assets/css/chosen.css" />
		<link rel="stylesheet" href="${baseurl}/assets/css/datepicker.css" />
		
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

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="${baseurl}/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	</head>
	<body>
		
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed');}catch(e){}
			</script>
			
			<div class="main-container-inner">
				
				<div class="main-content" style="margin-left: 0px;">
					
					<div class="page-content">
						
						<div class="row">
							<div class="col-xs-12">
								
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
													<form class="form-search" id="form0">
														<div class="row">
															<div class="col-xs-12 col-sm-9">
																<div class="input-group">
																	<input id="qnewsTitle" type="text" class="form-control search-query" placeholder="请输入新闻标题名称" />
																	<span class="input-group-btn">
																		<button id="doSearchBtn" type="button" class="btn btn-purple btn-sm">
																			查询
																			<i class="icon-search icon-on-right bigger-110"></i>
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
									<div class="col-xs-12">
									&nbsp;
									</div>
								</div>
								
								<div class="row">
									<div class="col-xs-12">
									
										<div id="news_info" class="easyui-window" title="新闻信息" closed="true" style="width:800px; height:550px; left:155px; top:0px; padding:4px;">
											<div class="row" style="margin-left:0px;margin-right:0px;">
												<div class="col-xs-12">
													<form class="form-horizontal" role="form" id="form1" method="post" action="${baseurl}/manages/news/saveNews.do">
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="newsTitle">新闻标题</label>

															<div class="col-sm-10">
																<input type="hidden" id="userId" name="userId"/>
																<input type="hidden" id="newsId" name="newsId"/>
																<input type="text" id="newsTitle" name="newsTitle" placeholder="新闻标题" class="col-xs-10 col-sm-5 required" maxlength="50" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="content">新闻描述</label>
															<div class="col-sm-10">
																<textarea id="description" name="description" rows="4" class="col-xs-10 col-sm-8 required" placeholder="新闻描述" maxlength="200"></textarea>
															</div>
														</div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="newsLink">新闻链接</label>
															<div class="col-sm-10">
																<input id="newsLink" name="newsLink" rows="4" type="url" class="col-xs-10 col-sm-8 required" placeholder="新闻链接" maxlength="200"></input>
															</div>
														</div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="createDate">新闻发布时间</label>
															
															<div class="col-sm-10">
																<input type="text" id="createDate" name="createDate" placeholder="新闻发布时间" class="col-xs-10 col-sm-5 date-picker required" data-date-format="yyyy-mm-dd" />
															</div>
														</div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="endDate">知识结束时间</label>
															
															<div class="col-sm-10">
																<input type="text" id="endDate" name="endDate" placeholder="新闻 结束时间" class="col-xs-10 col-sm-5 date-picker required" data-date-format="yyyy-mm-dd" />
															</div>
														</div> 
														<div class="row">
															<div class="col-xs-12">
																<p style="text-align: center;">
																	<button id="saveBtn" type="button" class="btn btn-sm btn-primary">
																		<i class="icon-cog"></i>
																		保存
																	</button>
																	<button id="cancelBtn" type="button" class="btn btn-sm btn-danger">
																		<i class="icon-trash"></i>
																		取消
																	</button>
																</p>
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>
														
										<table id="news_list-grid" title="新闻列表" class="easyui-datagrid" style="height:350px;" rownumbers="true" pagination="true" toolbar="#toolbar">
											<thead>
												<tr>
													<th field="newsTitle" width="80px">新闻名称</th>
													<th field="newsLink" width="80px">链接地址</th>
													<th field="description" width="80px">新闻描述</th>
													<th field="type" width="80px">类型</th>
													<th field="createDate" width="60px">新闻发布时间</th>
													<th field="endDate" width="60px">新闻结束时间</th>
													<th field="createUser" width="40px">发布人</th>
													<th field="updateUser" width="40px">发布人</th>
													<th field="operate" width="80">操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
								
								<div class="row">
									<div class="col-xs-12">
									&nbsp;
									</div>
								</div>
								
								<div class="row">
									<div class="col-xs-12">
										<p>
											<button id="addBtn" class="btn btn-sm btn-primary">
												<i class="icon-cog"></i>
												增加
											</button>
											<button id="modifyBtn" class="btn btn-sm btn-success">
												<i class="icon-edit"></i>
												修改
											</button>
											<button id="deleteBtn" class="btn btn-sm btn-danger">
												<i class="icon-trash"></i>
												删除
											</button>
										</p>
									</div>
								</div>
								
							</div><!-- /.col -->
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

		<script type="text/javascript">
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
		<script type="text/javascript" src="${baseurl}/assets/js/date-time/bootstrap-datepicker.min.js"></script>

		<!-- ace scripts -->

		<script type="text/javascript" src="${baseurl}/assets/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/ace.min.js"></script>

		<!-- easyui scripts -->
		<script type="text/javascript" src="${baseurl}/assets/libs/easyui/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/libs/easyui/locale/easyui-lang-zh_CN.js"></script>
		
		<!-- jquery-validation -->
		<script type="text/javascript" src="${baseurl}/assets/libs/jquery-validation/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/libs/jquery-validation/localization/messages_zh.min.js"></script>

		<!-- inline scripts related to this page -->
		
		<!-- common scripts -->
		<script type="text/javascript" src="${baseurl}/assets/js/app/common.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/libs/layer/layer.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/app/newsTop.js"></script>
		<script type="text/javascript">
		    var baseurl = "${baseurl}";
		    var sessionId = "<%=sessionId %>";
			var userId =  "<%=userId %>";
			var areaId =  "<%=areaId %>";
		</script>
	</body>
</html>