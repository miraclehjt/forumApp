<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.lang.String" %>
<%@ include file="/WEB-INF/manages/tag.jsp"%>
<%
	String username = request.getAttribute("username") == null ? "" : (String)request.getAttribute("username");
	String rememberMe = request.getAttribute("rememberMe") == null ? "" : (String)request.getAttribute("rememberMe");
%>
<html lang="cn">
	<head>
		<meta charset="utf-8" />
		<title>论坛App配置平台</title>
		<meta name="keywords" content=" app" />
		<meta name="description" content="本系统主要用于论坛App的所有配置" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="${baseurl}/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${baseurl}/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="${baseurl}/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- ace styles -->

		<link rel="stylesheet" href="${baseurl}/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${baseurl}/assets/css/ace-rtl.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${baseurl}/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

	</head>
	
	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="white">论坛App管理系统</span>
								</h1>
							<!-- 	<h4 class="blue">&copy; chenzl</h4> -->
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												请输入您的登录信息
											</h4>

											<div class="space-6"></div>

											<form action="${baseurl}/manages/login/loginManage.do" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="username" name="username" type="text" class="form-control" placeholder="请输入登录帐号" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="password" name="password" type="password" class="form-control" placeholder="请输入密码" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input id="rememberMe" name="rememberMe" type="checkbox" class="ace" />
															<span class="lbl">记住我</span>
														</label>

														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>
															登录
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

											<!-- <div class="social-or-login center">
												<span class="bigger-110">Or Login Using</span>
											</div>

											<div class="social-login center">
												<a class="btn btn-primary">
													<i class="icon-facebook"></i>
												</a>

												<a class="btn btn-info">
													<i class="icon-twitter"></i>
												</a>

												<a class="btn btn-danger">
													<i class="icon-google-plus"></i>
												</a>
											</div> -->
										</div><!-- /widget-main -->

										<div class="toolbar clearfix">
											<!-- <div>
												<a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
													<i class="icon-arrow-left"></i>
													I forgot my password
												</a>
											</div>

											<div>
												<a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
													I want to register
													<i class="icon-arrow-right"></i>
												</a>
											</div> -->
										</div>
									</div><!-- /widget-body -->
								</div><!-- /login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="icon-key"></i>
												Retrieve Password
											</h4>

											<div class="space-6"></div>
											<p>
												Enter your email and to receive instructions
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="icon-lightbulb"></i>
															Send Me!
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												Back to login
												<i class="icon-arrow-right"></i>
											</a>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="icon-group blue"></i>
												New User Registration
											</h4>

											<div class="space-6"></div>
											<p> Enter your details to begin: </p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="icon-retweet"></i>
														</span>
													</label>

													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="icon-refresh"></i>
															Reset
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															Register
															<i class="icon-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												<i class="icon-arrow-left"></i>
												Back to login
											</a>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /signup-box -->
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

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
		if("ontouchend" in document) document.write("<script src='${baseurl}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
	function show_box(id) {
		jQuery('.widget-box.visible').removeClass('visible');
		jQuery('#'+id).addClass('visible');
	}
	function setLoginInfo() {
		var username = "<%=username%>";
		var rememberMe = "<%=rememberMe%>";
		if (username != null && username != '') {
			$("#username").val(username);
		}
		if (rememberMe != null && rememberMe != '') {
			$("#rememberMe").prop("checked", true);
		}
	}
	$(document).ready(function(){
		setLoginInfo();
	});
	</script>
</body>
</html>
