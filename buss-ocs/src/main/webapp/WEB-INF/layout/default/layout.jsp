<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title><tiles:getAsString name="title" /></title>
<ys:style src="/js/uploadify/css/uploadify.css"></ys:style>
<ys:style src="/css/dwz/silver/style.css"></ys:style>
<ys:style src="/css/dwz/css/core.css"></ys:style>
<!-- bootstrap -->
<ys:style src="/css/bootstrap/bootstrap.min.css"/>
<ys:style src="/css/bootstrap/bootstrap-overrides.css"/>
<!-- libraries -->
<ys:style src="/css/lib/jquery-ui-1.10.2.custom.css"/>
<ys:style src="/css/lib/font-awesome.css"/>
<!-- global styles -->
<ys:style src="/css/detail/layout.css"/>
<ys:style src="/css/detail/elements.css"/>
<ys:style src="/css/detail/icons.css"/>
<ys:style src="/css/detail/form-wizard.css" />
<ys:style src="/css/lib/default-font.css"/>
<ys:style src="/css/lib/bootstrap-datetimepicker.css"/>
<ys:style src="/css/lib/bootstrap.datepicker.css"/>
<ys:style src="/css/layout/default/main.css"/>

 <ys:script src="/js/dwz/jquery-1.11.1.min.js"></ys:script> 


<ys:script src="/js/dwz/validator.js" />
<ys:script src="/js/dwz/dwz.min.js" />
<ys:script src="/js/dwz/cascade/cascade.js" />
<ys:script src="/js/dwz/dwz.plugins.js" />



<ys:script src="/js/lib/bootstrap.min.js"/>
<ys:script src="/js/lib/bootstrap-datepicker.js"/>
<ys:script src="/js/lib/bootstrap-datetimepicker.js"/>
<ys:script src="/js/dwz/common.js"/>
<script src="/js/lib/jquery-ui-1.10.2.custom.min.js"></script>
<script src="/js/lib/jquery.knob.js"></script>
<script src="/js/lib/theme.js"></script>
<script src="/js/lib/fuelux.wizard.js"></script>
<ys:script src="/js/lib/showImg.js"/>
<ys:script src="/js/ckeditor/ckeditor.js"></ys:script> 
<ys:script src="/js/module/common.js"/>
<ys:script src="/js/uploadify/scripts/jquery.uploadify3.1.fixed.js"/>
<!-- websocket -->
<script type="text/javascript">
	var baseUrl = '${__baseUrl}';
	$.browser = {};
	$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
	$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
	$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
	$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
	$(function() {
//		var socket = new SockJS("/portfolio");
//		var stompClient = Stomp.over(socket);

//		stompClient.connect({}, function(frame) {
//		});

	/* 	queryTask();
		function queryTask(){
			$.ajax({
				url:'/task/async/query',
				dataType:'json',
				global:false,
				success:function(list){
					setTimeout(queryTask,20000);
					//console.log(list);
					$('#topUserTaskMenu .count').text(list.length);
					$('#topUserTaskMenu .notifications>h3').text('你有' + list.length + '个待办任务');
					$('#topUserTaskMenu .notifications>.item').remove();
					for(var i = 0; i < list.length; i++){
						$('#topUserTaskMenu .notifications>.footer').before('<a href="module/bus-task-check/task-'+list[i].taskId +'" title="任务详情" target="navTab" rel="ordermanager" class="item"><i class="' + (list[i].type=='0'?'icon-road':'icon-bullhorn') + '"></i>' + list[i].title + '</a>');
					}
					if(list.length > 0){
						$('#topUserTaskMenu .notifications>.footer').html('<a href="module/bus-task-index/task-pager" target="navTab" rel="ordermanager" class="logout">查看任务列表</a>');
					}
					else{
						$('#topUserTaskMenu .notifications>.footer').html('<a href="#" class="logout">无待办任务</a>');
					}
					initUI($('#topUserTaskMenu'));
				},
				error:function(e){
					console.log(e);
					setTimeout(queryTask,20000);
				}
			});
		} */
		DWZ.init("dwz.frag.xml", {
			//loginTitle : "登录", // 弹出登录对话框
			loginUrl:"/login",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 600
			}, //【可选】
			pageInfo : {
				currentPage : "currentPage",
				showCount : "showCount",
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
		
		//$("#defaultPage").loadUrl("/module/operate-basic-default/company-detail",null,null);
	});
</script>
<tiles:insertAttribute name="head" defaultValue="" />
</head>
<body>
	<div id="layout">
		<tiles:insertAttribute name="header" />
		<div id="leftside">
			<%-- <div id="sidebar-nav">
		        <ul id="dashboard-menu">
		           	<li class="active">
		                <div class="pointer">
		                    <div class="arrow"></div>
		                    <div class="arrow_border"></div>
		                </div>
		                <a class="dropdown-toggle" href="#">
		                    <i class="icon-sitemap"></i>
		                    <span>业务管理</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <ul class="active submenu">
							<li><a href="module/bas-product-page/product-pager" target="navTab" rel="prioductManager">产品维护</a></li>
							<li><a href="module/bas-genre-page/genre-pager" target="navTab" rel="genremanager">分类维护</a></li>
							<li><a href="module/bas-customer-index/customer-pager" target="navTab" rel="customermanage">客户维护</a></li>
							<li><a href="module/bas-identity-index/identity-pager" target="navTab" rel="identitymanage">身份维护</a></li>			
							<li><a href="module/bas-dailyPay-index/dailyPay-pager" target="navTab" rel="dailyPaymanage">生活缴费维护</a></li>
							<li><a href="module/bas-merchant-index/merchant-pager-1" target="navTab" rel="merchantmanage">商户维护</a></li>
							<li><a href="module/bas-utilities-index/merchant-pager-2" target="navTab" rel="utilitiesmanage">公共事业单位维护</a></li>
							<li><a href="module/bas-defray-index/defray-pager" target="navTab" rel="defraymanage">支付维护</a></li>
		                </ul>
		            </li>
		            <li>
		                <div class="pointer">
		                    <div class="arrow"></div>
		                    <div class="arrow_border"></div>
		                </div>
		                <a class="dropdown-toggle" href="#">
		                    <i class="icon-search"></i>
		                    <span>信息查询</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <ul class="submenu">
							<li><a href="module/bus-bankTxnRecord-index/bankTxnRecord-pager" target="navTab" rel="bankTxnRecordmanager">当日交易查询</a></li>
							<li><a href="module/bus-historytBankTxnRecord-index/hisBankTxnRecord-pager" target="navTab" rel="hisBankTxnRecordManager">历史交易查询</a></li>
							<li><a href="module/bus-task-index/task-pager" target="navTab" rel="taskmanager">待办任务查询</a></li>
		                	<li><a href="module/bus-userNotify-index/user-notify-pager" target="navTab" rel="userNotifymanager">用户消息查询</a></li>
		               		<li><a href="module/bus-smsRecord-index/smsRecord-pager" target="navTab" rel="smsRecordmanager">手机短信记录查询</a></li>               
		                </ul>
		            </li>
		            <li>
		                <div class="pointer">
		                    <div class="arrow"></div>
		                    <div class="arrow_border"></div>
		                </div>
		                <a class="dropdown-toggle" href="#">
		                    <i class="icon-check"></i>
		                    <span>日常运营</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <ul class="submenu">
		                	<li><a href="module/bas-search-index" target="navTab" rel="projectSearchIndex">产品服务查询</a></li>
							<li><a href="module/bus-order-wizard" target="navTab" rel="ordermanager">订单录入</a></li>
							<li><a href="module/bas-dailyPay-payIndex" target="navTab" rel="deilypaymanager">生活缴费</a></li>
							<li><a href="module/bus-payMent-index/payMent-pager" target="navTab" rel="payMentmanager">缴费记录查询</a></li>
							<li><a href="module/bus-order-query/order-pager" target="navTab" rel="orderquery">订单查询</a></li>
							<li><a href="module/bus-order-logisticsQuery/order-logistics-pager" target="navTab" rel="logisticsquery">物流管理</a></li>
							<li><a href="module/bus-order-monitor/order-monitor-pager" target="navTab" rel="monitorquery">订单监控</a></li>
							<li><a href="module/bus-provisions-page/provisions-pager" target="navTab" rel="provisionsManager">备付金管理</a></li>
		                </ul>
		            </li>
		            <li>
		                <div class="pointer">
		                    <div class="arrow"></div>
		                    <div class="arrow_border"></div>
		                </div>
		                <a class="dropdown-toggle" href="#">
		                    <i class="icon-cog"></i>
		                    <span>系统管理</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <ul class="submenu">
		              <shiro:hasPermission name="code:view">  
							<li><a href="module/sys-code-index" target="navTab" rel="codemanage">数据字典</a></li>
						</shiro:hasPermission>  
							<li><a href="module/sys-module-index/module-pager" target="navTab" rel="modulemanage">菜单管理</a></li>
							<li><a href="module/sys-dept-index/dept-pager" target="navTab" rel="deptmanage">机构管理</a></li>
							<li><a href="module/sys-user-index/user-pager" target="navTab" rel="usermanage">用户管理</a></li>
							<li><a href="module/sys-role-index/role-pager" target="navTab" rel="rolemanage">角色管理</a></li>
							<li><a href="module/sys-config-page/config-search-orderLimitStrategy" target="navTab" rel="orderLimitmanage">订单时限策略</a></li>
							<li><a href="module/sys-config-page/config-search-orderAlertPersionsStategy" target="navTab" rel="alertmanage">订单通知人员策略</a></li>
							<li><a href="module/sys-config-page/config-search-provisionsAlert" target="navTab" rel="warmmanage" >备付金预警值</a></li>
							<li><a href="module/sys-config-page/config-search-provisionsAlertPersionsStategy" target="navTab" rel="configmanage" >备付金通知策略</a></li>
							<li><a href="module/sys-config-page/config-search-tradeLimit" target="navTab" rel="tradeLimitmanage" >交易时限</a></li>
		                </ul>
		            </li>
		        </ul>
			</div>
			</div> --%>
			<div id="sidebar-nav" class="layoutBox">
		        <ul id="dashboard-menu">
				<c:forEach var="level1" items="${tree.list}" varStatus="ind">
					<li <c:if test="${ind.index == 0}">class="active"</c:if>>
		                <div class="pointer">
		                    <div class="arrow"></div>
		                    <div class="arrow_border"></div>
		                </div>
		                <a class="dropdown-toggle" href="#">
		                    <i class="${level1.entity.menuIcon}"></i>
		                    <span>${level1.entity.moduleName }</span>
		                    <i class="icon-chevron-down"></i>
		                </a>
		                <c:if test="${not empty level1.childrens}">
		                <ul class="<c:if test="${ind.index == 0}">active</c:if> submenu"
		                	layoutMaxH="${fn:length(tree.list) * 60}">
		                <c:forEach var="level2" items="${level1.childrens }">
							<li><a href="${level2.entity.menuUrl}" target="navTab" rel="${level2.entity.authName }">${level2.entity.moduleName }</a></li>
						</c:forEach>
						</ul>
						</c:if>
		            </li>												
				</c:forEach>
				</ul>
			</div>
		</div>
		<div id="container">
			<div>
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" style="display: none;" class="main"><a href="javascript:;"><span><span
										class="home_icon">首页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">首页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div layoutH="0" style="background: url('/img/bgs/index-backgroud.jpg');background-size:cover;">
							<tiles:insertAttribute name="body" />
						</div>
					</div>
			</div>
		</div>
		</div>
	</div>
	
	<div id="tbg" class="theme-popover" ondblclick="huanyuan();" onmousemove="showChangeButton(event);">
	<div id="imgNum"></div>
     <div class="theme-popbod dform">
          <div id='block1'  onmouseout='drag=0' onmouseover='oDragObj=block1; drag=1;' class="dragAble"> <img name='images1' id="images1" src='' border='0' width="796" height="556" onmousewheel="return bbimg(this)"></div> 
     </div>
     <div id="zoomd"></div>
	</div>
	<div id="bgdiv" class="theme-popover-mask" ></div>
	
	<div id="footer"></div>
</body>
</html>