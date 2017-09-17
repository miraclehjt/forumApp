<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="./tag.jsp"%>
<%@ page language="java"  import="com.chenzl.app.entity.SysUser" %>
 <%
    SysUser sysUser =(SysUser) request.getSession().getAttribute("sysUser");
	String jsessionid = request.getSession().getId();
	String userId = sysUser.getUserId();
	String userName = sysUser.getUserName();
	String areaId = sysUser.getAreaId();
	String path = request.getScheme() + "://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
%> 
<html lang="cn">
<head>
<meta charset="utf-8" />
<title>控制台 - 论坛学习App</title>
<meta name="keywords" content="论坛学习App" />
<meta name="description" content="论坛学习App" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- basic styles -->

<link href="${baseurl}/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${baseurl}/assets/css/font-awesome.min.css" />
<link rel="stylesheet" href="${baseurl}/assets/css/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="${baseurl}/assets/css/ace.min.css" />
<link rel="stylesheet" href="${baseurl}/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="${baseurl}/assets/css/ace-skins.min.css" />
<script src="${baseurl}/assets/js/ace-extra.min.js"></script>

<style>
    body, html{ height:100%; width:100%; overflow: hidden }
</style>
</head>

<body>
	<table id="frametable" cellpadding="0" cellspacing="0" width="100%" height="100%">
		<tr>
			<td colspan="2" height="45">

				<div class="top_header">
					<div class="navbar navbar-default" id="navbar">
						<div class="navbar-container" id="navbar-container">
							<div class="navbar-header pull-left">
								<a href="#" class="navbar-brand"> <small class="logo_area">
										<i class="logo"></i> <span>论坛学习APP管理系统</span>
								</small>
								</a>
								<!-- /.brand -->
							</div>
							<!-- /.navbar-header -->

							<div class="navbar-header pull-right" role="navigation">
								<ul class="nav ace-nav">

									<li class="light-blue"><a data-toggle="dropdown" href="#"
										class="dropdown-toggle"> <img class="nav-user-photo"
											src="${baseurl}/assets/avatars/avatar_icon.png" alt="用户头像" />
											<span class="user-info"> <small>欢迎你,</small>  <%=userName%> 
										</span> <i class="icon-caret-down"></i>
									</a>

										<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
									
											<li><a href="${baseurl}/manages/login/logout.do"> <i
													class="icon-off"></i> 注销
											</a></li>
										</ul></li>
								</ul>
								<!-- /.ace-nav -->
							</div>
							<!-- /.navbar-header -->
						</div>
						<!-- /.container -->
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td valign="top" width="191" height="100%">
				<div class="left">
					<div class="sidebar" id="sidebar">
						<ul class="nav nav-list">
							<li>
								<a href="javascript:void(0)" class="dropdown-toggle lvl1" menuid="1"
								menuname="轮播图配置" menuurl="" tobarString="轮播图配置">
									<i class="icon-inbox"></i> <span class="menu-text">轮播图配置
								</span> <b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
									<li><a href="javascript:void(0)" 
								 	menuid="12" menuname="轮播图" menuurl="${baseurl}/manages/page/innerSlider.do?dicName=slider" tobarString="轮播图"> <i
											class="icon-double-angle-right"></i> 轮播图
									</a></li>
								</ul>
							</li>
							
							<li>
								<a href="javascript:void(0)" class="dropdown-toggle lvl1" menuid="2"
								menuname="新闻头条配置" menuurl="" tobarString="新闻头条配置">
									<i class="icon-inbox"></i> <span class="menu-text">新闻头条配置
								</span> <b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
									<li><a href="javascript:void(0)" 
								 	menuid="21" menuname="新闻头条" menuurl="${baseurl}/manages/page/newsTop.do?dicName=news" tobarString="新闻头条"> <i
											class="icon-double-angle-right"></i> 新闻头条
									</a></li>
								</ul>
							</li>
						 
						 <li>
								<a href="javascript:void(0)" class="dropdown-toggle lvl1" menuid="3"
								menuname="搜索关键字配置" menuurl="" tobarString="搜索关键字配置">
									<i class="icon-inbox"></i> <span class="menu-text">搜索关键字配置
								</span> <b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
									<li><a href="javascript:void(0)" 
								 	menuid="31" menuname="搜索关键字" menuurl="${baseurl}/manages/page/search_keyword.do" tobarString="搜索关键字"> <i
											class="icon-double-angle-right"></i>搜索关键字
									</a></li>
								</ul>
							</li>
							<li>
								<a href="javascript:void(0)" class="dropdown-toggle lvl1" menuid="4" menuname="论坛社区配置" menuurl="" tobarString="论坛社区配置">
									<i class="icon-inbox"></i> <span class="menu-text">论坛社区配置</span> <b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
									<li>
										<a href="javascript:void(0)" menuid="41" menuname="问题列表管理" menuurl="${baseurl}/manages/page/post_list.do?dicName=forum" tobarString="问题列表管理">
											<i class="icon-double-angle-right"></i>问题列表管理
										</a>
									</li>
									<li>
										<a href="javascript:void(0)" menuid="42" menuname="版块信息配置" menuurl="${baseurl}/manages/page/forumcategory.do?dicName=forum" tobarString="版块信息配置">
											<i class="icon-double-angle-right"></i>版块信息配置
										</a>
									</li>
									<li>
										<a href="javascript:void(0)" menuid="43" menuname="调查评测配置" menuurl="${baseurl}/manages/page/questionnaire.do?dicName=forum" tobarString="调查评测配置">
											<i class="icon-double-angle-right"></i>调查评测配置
										</a>
									</li>
									<li>
										<a href="javascript:void(0)" menuid="44" menuname="问题审核管理" menuurl="${baseurl}/manages/page/forum_post.do?dicName=forum" tobarString="问题审核管理">
											<i class="icon-double-angle-right"></i>问题审核管理
										</a>
									</li>
									 <li>
										<a href="javascript:void(0)" menuid="45" menuname="问题审核人员配置" menuurl="${baseurl}/manages/page/audit_user.do?dicName=forum" tobarString="问题审核人员配置">
											<i class="icon-double-angle-right"></i>审核人员配置
										</a>
									</li> 
								</ul>
							</li>
							<li>
								<a href="javascript:void(0)" class="dropdown-toggle lvl1" menuid="5" menuname=" 知识推荐配置" menuurl="" tobarString=" 知识推荐配置">
									<i class="icon-inbox"></i> <span class="menu-text"> 知识推荐配置</span> <b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
									<li>
										<a href="javascript:void(0)" menuid="51" menuname="推荐知识" menuurl="${baseurl}/manages/page/learn.do?dicName=learn" tobarString="推荐知识">
											<i class="icon-double-angle-right"></i> 推荐知识
										</a>
									</li>
								</ul>
							</li>
						</ul>
						<!-- /.nav-list -->
						<div class="sidebar-collapse" id="sidebar-collapse">
							<i class="icon-double-angle-left"
								data-icon1="icon-double-angle-left"
								data-icon2="icon-double-angle-right"></i>
						</div>
					</div>
				</div>
			</td>
			<td valign="top" width="100%" height="100%">
				<iframe id="indexiframe" name="main" src="${baseurl}/manages/page/right.do" width="100%" allowtransparency="true" height="100%" frameborder="0" scrolling="no" style="overflow: visible;"></iframe>
			</td>
		</tr>
	</table>

	<!-- basic scripts -->

	<!--
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script src="${baseurl}/assets/js/jquery-1.10.2.min.js"></script>
	-->
	
	<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${baseurl}/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
	<!-- <![endif]-->

	<!--[if IE]>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${baseurl}/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<script src="${baseurl}/assets/js/html5shiv.js"></script>
		<script src="${baseurl}/assets/js/respond.min.js"></script>
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

	<!-- ace scripts -->
	<script src="${baseurl}/assets/plugins/yui/yahoo-dom-event.js"></script>
	<script src="${baseurl}/assets/plugins/yui/connection-min.js"></script>
	<script src="${baseurl}/assets/plugins/yui/element-beta-min.js"></script>
	<script src="${baseurl}/assets/plugins/yui/tabview-min.js"></script>

	<script src="${baseurl}/assets/js/ace-elements.min.js"></script>
	<script src="${baseurl}/assets/js/ace.min.js"></script>

	<!-- 10086 common scripts -->
	<script src="${baseurl}/assets/js/app/common.js"></script>

	<!-- inline scripts related to this page -->
<script type="text/javascript">
    //导航
    $(function(){

        //点击节点生成TAB页
        function setTab(obj){
           window.main.addTab($(obj).attr('menuid'),$(obj).attr('menuname'),$(obj).attr('menuurl'));
           /*var tobarCurrString = "&nbsp;<a href=" + $(obj).attr('menuurl')+ " target=iFrame" + $(obj).attr('menuid') + ">"+ $(obj).attr('menuname') + "</a>";*/
           /*  parent.main.setTobarDiv($(this).attr('menuid'),$(this).attr('tobarString')+tobarCurrString);*/
        }

        $('.nav-list>li').on('click','.lvl1',function(ev){
            if($(this).parent('li').hasClass('active')){
                if ($(this).next('.submenu')) {
                    if ($(this).next('.submenu').hasClass('flag')) {
                        $(this).next('.submenu').css('display', 'none')
                        $(this).next('.submenu').removeClass('flag')
                    } else {
                        $(this).next('.submenu').css('display', 'block')
                        $(this).next('.submenu').addClass('flag')
                    }
                }
            }else{
                $('.nav-list>li').removeClass('active')
                $(this).parent('li').addClass('active')
                $('.nav-list>li>.submenu').css({'display':'none'})
                $('.nav-list>li>.submenu').removeClass('flag')
                if ($(this).next('.submenu')) {
                    $(this).next('.submenu').addClass('flag')
                    $(this).next('.submenu').css({'display':'block'})
                }
            }
            ev.preventDefault();
            ev.stopPropagation();
        });

        $('.nav-list ').on('click','a',function(ev){
            setTab(this);
            ev.preventDefault();
            return false;
        });

    });
</script>
</body>
</html>
