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
		<title>提问详情</title>
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

		<!-- fonts -->
		<!--
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
		-->
		<!-- ace styles -->

		<!-- <link rel="stylesheet" href="${baseurl}/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-skins.min.css" /> -->
	

		<!--[if lte IE 8]>
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->
		<link rel="stylesheet" href="${baseurl}/assets/css/forum/post_detail.css" />

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

					<div class="page-content" id="pContent">
					<!-- <h1>这是贴子标题</h1>
					<div class="post-list">
					
						<div class="author-main">
							<ul class="p-author">
								<li class="portrait">
									<div class="portrait-wrapper">
									<img src="${baseurl}/assets/images/gallery/image-1.jpg">
									</div>
								</li>
								<li class="name">绮罗生</li>
							</ul>
						</div>
						
						<div class="post-content-main">
						
							<div class="p-content">
								<div>神秘，神圣，宁静，纯洁，蓝天白云，无数人魂牵梦绕的西藏！</div>
							</div>
							
							<div class="p-reply">
								<ul class="reply-list">
									<li>好顶赞</li>
									<li>一定要去看看</li>
								</ul>
							</div>
						</div>
				
					
					</div> -->
					
					
					
						
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
		
		<!-- app common scripts -->
		<script type="text/javascript" src="${baseurl}/assets/js/app/common.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/libs/layer/layer.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/app/post_detail.js"></script>
		<script type="text/javascript">
			var baseurl = "${baseurl}";
			var sessionId = "<%=sessionId %>";
			var userId = "<%=userId %>"; 
			var areaId ="<%=areaId %>";
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
