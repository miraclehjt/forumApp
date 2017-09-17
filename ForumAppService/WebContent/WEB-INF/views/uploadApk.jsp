<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String message = (String) request.getAttribute("message") != null ? (String) request.getAttribute("message") : "";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>上传安卓应用包</title>
	<style type="text/css">
		#content {
			width: 100%;
		}
		
		#form_view {
			width:500px;
			margin-left:auto;
			margin-right:auto;
		}
		
		#form_view span {
			margin-top: 8px;
			display: inline-block;
		}
		
		.label_name {
			width:70px;
		}
	</style>
</head>
<body>
	<div id="content">
		<form id="form1" action="uploadApkFile.do" method="post" enctype="multipart/form-data">
			<div id="form_view">
				<div>
					<span class="label_name"><label>应用包名</label></span>
					<span><input type="text" id="appid" name="appId"></span>
				</div>
				<div>
					<span class="label_name"><label>版本号</label></span>
					<span><input type="text" id="version" name="version" ></label></span>
				</div>
				<div>
					<span class="label_name"><label>工号</label></span>
					<span><input type="text" id="staffId" name="staffId" ></label></span>
				</div>
				<div>
					<span class="label_name"><label>APK文件</label></span>
					<span><input type="file" id="apkFile" name=apkFile ></label></span>
				</div>
				<div>
					<button id="upload" type="button">上传</button>
				</div>
			</div>
		</form>
	</div>
	<script src="../lib/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		function validate() {
			var appid = $("#appid").val();
			if (appid == null || appid == "") {
				alert("应用包名不能为空！");
				return false;
			}
			var version = $("#version").val();
			if (version == null || version == "") {
				alert("版本号不能为空！");
				return false;
			}
			var staffId = $("#staffId").val();
			if (staffId == null || staffId == "") {
				alert("工号不能为空！");
				return false;
			}
			var apkFile = $("#apkFile").val();
			if (apkFile == null || apkFile == "") {
				alert("APK文件不能为空！");
				return false;
			}
			return true;
		}
		$(document).ready(function(){
			var message = '<%=message%>';
			if (message != null && message != "") {
				alert(message);
			}
			$("#upload").click(function(){
				if (validate()) {
					$("#form1").submit();
				}
			});
		});
	</script>
</body>
</html>