<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<li data-autochecked="true"><a href="#" tname="permissionList" tvalue="${treenode.code}" ${fn:contains(permissionList,treenode.code)?"checked='true'":""}>${treenode.text}</a>
		<c:if test="${not empty treenode.childrens}">
			<ul>
			<c:forEach items="${treenode.childrens}" var="item">
				<c:set var="treenode" value="${item}" scope="request" />
				<jsp:include page="/WEB-INF/jsp/module/sys/module/node.jsp"/>
			</c:forEach>
			</ul>
		</c:if>
	</li>
