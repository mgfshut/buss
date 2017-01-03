<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><tiles:getAsString name="title" /></title>

<ys:style src="/css/dwz/silver/style.css"></ys:style>
<ys:style src="/css/dwz/css/core.css"></ys:style>
<!-- bootstrap -->
<ys:style src="css/bootstrap/bootstrap.css"/>
<ys:style src="css/bootstrap/bootstrap-overrides.css"/>
<!-- libraries -->
<ys:style src="css/lib/jquery-ui-1.10.2.custom.css"/>
<ys:style src="css/lib/font-awesome.css"/>
<!-- global styles -->
<ys:style src="css/detail/layout.css"/>
<ys:style src="css/detail/elements.css"/>
<ys:style src="css/detail/icons.css"/>
<ys:style src="css/lib/default-font.css"/>
<ys:style src="css/layout/detail/main.css"/>

<ys:script src="/js/dwz/jquery-1.11.1.min.js"></ys:script>
<ys:script src="/js/dwz/jquery.validate.js" />
<ys:script src="/js/dwz/dwz.min.js" />
<ys:script src="/js/dwz/dwz.plugins.js" />
<ys:script src="/js/lib/bootstrap.min.js"/>
<script src="/js/lib/jquery-ui-1.10.2.custom.min.js"></script>
<script src="/js/lib/jquery.knob.js"></script>
<script src="/js/lib/theme.js"></script>

<script type="text/javascript">
	var baseUrl = '${__baseUrl}';
	$.browser = {};
	$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
	$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
	$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
	$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
	$(function() {
		DWZ.init("dwz.frag.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录", // 弹出登录对话框
			//			loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			keys : {
				statusCode : "statusCode",
				message : "message"
			}, //【可选】
			ui : {
				hideMode : 'offsets'
			}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>
<tiles:insertAttribute name="head" defaultValue="" />
</head>
<body>
	<div id="layout">
		<div id="header"></div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
			<div id="sidebar-nav">
		        <ul id="dashboard-menu">
		            <li>                
		                <a href="module/index" rel="main" target="navTab">
		                    <i class="icon-home"></i>
		                    <span>Home</span>
		                </a>
		            </li>            
		            <li class="active">
		                <div class="pointer">
		                    <div class="arrow"></div>
		                    <div class="arrow_border"></div>
		                </div>
		                <a class="dropdown-toggle" href="#">
		                    <i class="icon-cog"></i>
		                    <span>系统管理</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <ul class="active submenu">
							<li><a href="module/sys-code-index" target="navTab"
								rel="main">代码管理</a></li>
							<li><a href="module/sys-module-index/module-pager"
								target="navTab" rel="main">功能管理</a></li>
							
							<li><a href="module/sys-user-index/user-pager"
								target="navTab" rel="main">用户管理</a></li>
							<li><a href="module/sys-role-index/role-pager"
								target="navTab" rel="main">角色管理</a></li>
							<li>
							<a
								href="module/sys-config-page/config-search-orderLimitStrategy"
								target="navTab" rel="main">订单时限策略</a></li>
							<li><a href="module/sys-config-page/config-search-orderAlertPersionsStategy"
								target="navTab" rel="main">订单通知人员策略</a></li>
							<li><a
								href="module/sys-config-page/config-search-provisionsAlert"
								target="navTab" rel="main">备付金预警值</a></li>
							<li><a href="module/sys-config-page/config-search-provisionsAlertPersionsStategy"
								target="navTab" rel="main">备付金通知策略</a></li>
							<li><a
								href="module/sys-config-page/config-search-tradeLimit"
								target="navTab" rel="main">交易时限</a></li>

		                </ul>
		            </li>
		            <li>
		                <a class="dropdown-toggle" href="#">
		                    <i class="icon-edit"></i>
		                    <span>Forms</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <ul class="submenu">
		                    <li><a href="form-showcase.html">Form showcase</a></li>
		                    <li><a href="form-wizard.html">Form wizard</a></li>
		                </ul>
		            </li>
		            <li>
		                <a href="gallery.html">
		                    <i class="icon-picture"></i>
		                    <span>Gallery</span>
		                </a>
		            </li>
		            <li>
		                <a href="calendar.html">
		                    <i class="icon-calendar-empty"></i>
		                    <span>Calendar</span>
		                </a>
		            </li>
		        </ul>
			</div>
			</div>
		</div>
		<div id="container" class="content">
			<div id="pad-wrapper">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<div class="alertInfo">
								<p>
									<a href="https://code.csdn.net/dwzteam/dwz_jui/tree/master/doc"
										target="_blank" style="line-height: 19px"><span>DWZ框架使用手册</span></a>
								</p>
								<p>
									<a href="http://pan.baidu.com/s/18Bb8Z" target="_blank"
										style="line-height: 19px">DWZ框架开发视频教材</a>
								</p>
							</div>
							<div class="right">
								<p style="color: red">
									DWZ官方微博 <a href="http://weibo.com/dwzui" target="_blank">http://weibo.com/dwzui</a>
								</p>
							</div>
							<p>
								<span>DWZ富客户端框架</span>
							</p>
							<p>
								DWZ官方微博:<a href="http://weibo.com/dwzui" target="_blank">http://weibo.com/dwzui</a>
							</p>
						</div>
						<div class="pageFormContent" layoutH="80"
							style="margin-right: 230px">
							<tiles:insertAttribute name="body" />

					</div>

				</div>
			</div>
		</div>
		</div>
	</div>
	</div>
	<div id="footer"></div>
</body>
</html>