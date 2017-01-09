<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="sys/code/item/pager" method="post" onsubmit="return divSearch(this, 'codeitem-pager');">

<input type="hidden" name="currentPage" value="${page.currentPage}" />
<%-- <input type="hidden" name="totalResult" value="${page.totalResult}" /> --%>
<%-- <input type="hidden" name="totalPage" value="${page.totalPage}" /> --%>
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'code':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

</form> 
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
	<div class="btn-group">
		<a class="btn btn-primary btn-sm" href="sys/code/item/form?code=${param.code}" mask="true" target="navTab" rel="codeSaveTab" title="添加代码项"><i class="icon-plus"></i> <span>添加</span></a>
		<a class="btn btn-danger btn-sm" callback="removeCodeSuccess" href="service/codeValue-remove-{codeValueId}" target="ajaxTodo" title="确定要删除吗?"><i class="icon-minus"></i> <span>删除</span></a>
		<a class="btn btn-warning btn-sm" href="sys/code/item/form/{codeValueId}" mask="true" title="修改代码项" target="navTab" rel="codeSaveTab" ><i class="icon-pencil"></i> <span>修改</span></a>
	</div>
</div>
<table class="table" width="100%" layoutH="130" rel="codeitem-pager">
	<thead>
		<tr>
			<th width="150" orderField="code" 
				class="${param.orderField eq 'codeValue'?param.orderDirection:''}">代码值</th>
			<th orderField="name" class="${param.orderField eq 'codeValueDescribe'?param.orderDirection:''}">代码值描述</th>
		</tr>
		
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="codeValueId" rel="${item.codeValueId}"><td>${item.codeValue}</td><td>${item.codeValueDescribe}</td></tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage rel="codeitem-pager" style="min-width:300px;"></sdf:dwzPage>
</div>