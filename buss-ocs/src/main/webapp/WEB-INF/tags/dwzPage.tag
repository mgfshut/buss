<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="style" required="false" %>
<%@ attribute name="rel" required="false" %>
<div class="panelBar page">
	<div class="pages">
		<span>每页显示</span>
		<c:if test="${ rel eq null }">
		<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
		</c:if>
		<c:if test="${ rel ne null }">
		<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'${rel}')">
		</c:if>
		<c:forEach begin="10" end="30" step="5" varStatus="s">
				<option value="${s.index}" ${page.numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
		</c:forEach>
		</select>
		<span>总条数: ${page.totalResult}</span>
	</div>
	<c:if test="${ rel eq null }">
	<div class="pagination" style="${ style ne null ? style : ''}" 
		targetType="navTab" totalCount="${page.totalResult}" 
		numPerPage="${page.numPerPage eq null?10:page.numPerPage}" 
		pageNumShown="5" currentPage="${page.currentPage}"></div>
	</c:if>
	<c:if test="${ rel ne null }">
	<div class="pagination" rel="${rel}" style="${ style ne null ? style : ''}" 
		targetType="navTab" totalCount="${page.totalResult}" 
		numPerPage="${page.numPerPage eq null?10:page.numPerPage}" 
		pageNumShown="5" currentPage="${page.currentPage}"></div>
	</c:if>
</div>