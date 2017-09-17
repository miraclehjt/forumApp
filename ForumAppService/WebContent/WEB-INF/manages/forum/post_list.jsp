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
		<title>控制台 - 论坛App管理平台 - 社区管理配置</title>
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

		<!-- easyui -->
		<link rel="stylesheet" href="${baseurl}/assets/libs/easyui/css/themes/default/easyui.css" />
		<link rel="stylesheet" href="${baseurl}/assets/libs/easyui/css/themes/icon.css" />

		<link rel="stylesheet" href="${baseurl}/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-skins.min.css" />

		<script src="${baseurl}/assets/js/ace-extra.min.js"></script>

		
		<style type="text/css">
		.row {
			margin-right:0 !important;
			margin-left:0 !important;
		}
		</style>
		
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
							<div class="col-xs-12">
								<div id="add_post" class="easyui-window" title="话题信息" closed="true" style="width:800px; height:550px; padding:4px;">
											<div class="row" style="margin-left:0px;margin-right:0px;">
												<div class="col-xs-12">
													<form class="form-horizontal" role="form" id="saveFm" method="post" action="${baseurl}/manages/forumPost/saveForumPost.do">
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="postTitle">话题标题</label>

															<div class="col-sm-10">
																<input type="hidden" id="areaId" name="areaId" />
																<input type="hidden" id="createUser" name="createUser" />
																<input type="text" id="postTitle" name="postTitle" placeholder="话题标题" class="col-xs-10 col-sm-5 required" maxlength="50" />
															</div>
														</div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="postCategory">话题板块</label>
															
															<div class="col-sm-10">
																<select class="col-xs-10 col-sm-5 required" id="postCategory" name="postCategory">
																	<option value="" selected="selected">-- 请选择 --</option>
																	<option value="2">热门</option>
																	<option value="3">推荐</option>
															
																</select>
															</div>
														</div>
														
														
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="postContent">话题内容</label>
															
															<div class="col-sm-10">
																<textarea id="postContent" name="postContent" rows="4" class="col-xs-10 col-sm-8 required" placeholder="话题内容" maxlength="100"></textarea>
															</div>
														</div>
														
													<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="postArea">范围</label>
															<div class="col-sm-10">
															
																<div id="postArea" class="col-xs-12">
																
																</div>
																
															</div>
														</div>
														
														<div class="row">
															<div class="col-xs-12">
																<p style="text-align: center;">
																	<button id="saveBtn" type="button" class="btn btn-sm btn-primary" >
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
						</div>
						</div>
						
						<div class="row">
							<div class="col-xs-12">
								<div id="area_select" class="easyui-window" title="地市" closed="true" style="width:800px; height:550px; padding:4px;">
										<div class="row" style="margin-left:0px;margin-right:0px;">
								
											<div class="col-xs-12">
												
												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" for="areaIdsGroup">选择推荐地市:</label>
														<div class="col-sm-10">
																
															<div class="col-xs-12" id="areaIdsGroup"></div>
														</div>
												</div>					
														
														<div class="row">
															<div class="col-xs-12">
																<p style="text-align: center;">
																	<button id="saveAreaSelectBtn" type="button" class="btn btn-sm btn-primary" >
																		<i class="icon-cog"></i>
																		保存
																	</button>
																	<button id="cancelAreaSelectBtn" type="button" class="btn btn-sm btn-danger">
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
						</div>
						</div>
					
					
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
													<form class="form-search">
														<div class="row">
															<div class="col-xs-12 col-sm-8">
																<div class="input-group">
																	<input id="searchKeyword" type="text" class="form-control search-query" placeholder="请输入提问标题" />
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
										<div id="post_info" class="easyui-window" title="提问详情" closed="true" style="width:800px; height:550px; left:155px; top:0px; padding:4px;">
									
										</div>
										<table id="dg" title="提问列表" class="easyui-datagrid" style="height:350px;" rownumbers="true" pagination="true" toolbar="#toolbar">
											<thead>
												<tr>
													<th field="mkBnTitle" width="80px">提问标题</th>
													<th field="mkBnIcon" width="80px">提问内容</th>
													<th field="mkBnNum" width="60px">提问类型</th>
													<th field="mkBnDisplay" width="60px">是否显示</th>
													<th field="operate" width="80px">操作</th>
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
										<!-- 
										<button id="previewBtn" class="btn btn-sm btn-purple">
												<i class="icon-print"></i>
												预览
											</button>
											
											<button id="recommendBtn" class="btn btn-sm btn-warning">
												<i class="icon-arrow-up"></i>
												推荐
											</button> 
											
											<button id="deleteBtn" class="btn btn-sm btn-danger">
												<i class="icon-trash"></i>
												删除
											</button> -->
											
											<button id="addPostBtn" class="btn btn-sm btn-primary">
												<i class="icon-cog"></i>
												发起话题
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
		<script type="text/javascript" src="${baseurl}/assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		<script src="${baseurl}/assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script type="text/javascript" src="${baseurl}/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/jquery.ui.touch-punch.min.js"></script>

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
		
		<!-- 10086 common scripts -->
		<script type="text/javascript" src="${baseurl}/assets/js/app/common.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/libs/layer/layer.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/app/post_list.js"></script>
		<script type="text/javascript">
			var baseurl = "${baseurl}";
			var sessionId = "<%=sessionId %>";
			var userId = "<%=userId %>"; 
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
