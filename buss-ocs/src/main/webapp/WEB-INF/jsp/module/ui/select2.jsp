<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<select id="${param.selectId }" name="${param.selectName}" class="${param.classes}" style="${param.style}" ${param.disabled } ${param.required } data-error="${param.dataerror }">
	<c:if test="${param.defaultText ne null and param.defaultText ne ''}">
	<option value="${param.defaultValue}">${param.defaultText}</option>
	</c:if>
	<c:forEach items="${list}" var="item">
		<option value="${item[param.codefield]}" ${item[param.codefield] eq param.value ? 'selected':'' }>${item[param.namefield]}</option>
	</c:forEach>
</select>
