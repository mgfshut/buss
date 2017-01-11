<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar navbar-inverse" role="banner">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" id="menu-toggler">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            		<div class="logo_show">
	            		<c:if test="${company ne null }">
							<c:if test="${company.logo ne null and company.logo ne ''}">
								<div>
								<a class="navbar-brand" href="/" >
									<img alt="" src="${fileServer }${company.logo}" width="80" height="44">
								</a>
								</div>
							</c:if>            		
	            		</c:if>
	            		<div class="text_h">
	            			<%-- ${company.abbreviation }  --%>后台管理系统
	            		</div>
            		</div>
        </div>
        <ul class="nav navbar-nav pull-right hidden-xs">
            <li class="notification-dropdown hidden-xs hidden-sm" id="topUserTaskMenu" style="display: none;">
                <a href="#" class="trigger">
                    <i class="icon-warning-sign"></i>
                    <span class="count">${fn:length(task)}</span>
                </a>
                <div class="pop-dialog">
                    <div class="pointer right">
                        <div class="arrow"></div>
                        <div class="arrow_border"></div>
                    </div>
                    <div class="body">
                        <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                        <div class="notifications">
                            <h3>您有${fn:length(task)}个待办任务</h3>
                            <c:forEach var="item" items="${task}">
	                            <a href="module/bus-task-check/task-${item.taskId }"  title="任务详情" target="navTab" rel="ordermanager" class="item">
	                                <i class="${item.type == '0'?'icon-road':'icon-bullhorn'}"></i> ${item.title}
	                                <!-- <span class="time"><i class="icon-time"></i> 13 min.</span>-->
	                            </a>
                            </c:forEach>
                            <div class="footer">
                            	<c:if test="${fn:length(task) gt 0}">
	                                <a href="module/bus-task-index/task-pager" target="navTab" rel="ordermanager" class="logout">查看任务列表</a>
                                </c:if>
                             	<c:if test="${fn:length(task) eq 0}">
	                                <a href="#" class="logout">无待办任务</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle hidden-xs hidden-sm" data-toggle="dropdown">
                	<shiro:principal property="userName"></shiro:principal>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a  href="module/sys-member-modifyUserPass" title="修改密码" target="dialog" width="420" height="320">
						修改密码</a></li>
                    <li><a href="user/logout">退出系统</a></li>
                </ul>
            </li>
        </ul>
    </header>