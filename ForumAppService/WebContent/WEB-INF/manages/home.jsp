<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/manages/tag.jsp"%>
<html lang="cn">
	<head>
		<meta charset="utf-8" />
		<title>控制台 - App论坛控制台- 首页</title>
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

		<script type="text/javascript" src="${baseurl}/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		
		<style type="text/css">
			*{
				margin:0;
				padding:0;
			}
			body{
				width:100%;
				height: auto;
				background: #438EAA;
			}
		</style>
	</head>

	<body>
		
		<h1 style="text-align: center;">欢迎来到App论坛后台管理系统</h1>
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
		<script type="text/javascript" src="${baseurl}/assets/js/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/jquery.sparkline.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/flot/jquery.flot.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script type="text/javascript" src="${baseurl}/assets/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->
		
		<!--  common scripts -->
		<script type="text/javascript" src="${baseurl}/assets/js/app/common.js"></script>
		<script type="text/javascript" src="${baseurl}/assets/js/app/home.js"></script>
	</body>
</html>
