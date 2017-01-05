<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="sys/code/item/pager" method="post" onsubmit="return divSearch(this, 'codeitem-pager');">
<input type="hidden" name="pageNum" value="${number+1}" />
<input type="hidden" name="numPerPage" value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" /> 
<input type="hidden" name="orderField" value="${param.orderField eq null?'code':param.orderField}" />
<input type="hidden" name="orderDirection" value="${param.orderDirection eq null?'asc':param.orderDirection}" />
<input type="hidden" name="codemap" value="${param.codemap}" />
</form> 
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
	<div class="btn-group">
		<a class="btn btn-primary btn-sm" href="sys/code/item/form?codemap=${param.codemap}" mask="true" target="navTab" rel="codeSaveTab" title="添加代码项"><i class="icon-plus"></i> <span>添加</span></a>
		<a class="btn btn-danger btn-sm" callback="removeCodeSuccess" href="service/code-item-remove-{codeitem}" target="ajaxTodo" title="确定要删除吗?"><i class="icon-minus"></i> <span>删除</span></a>
		<a class="btn btn-warning btn-sm" href="sys/code/item/form/{codeitem}" mask="true" title="修改代码项" target="navTab" rel="codeSaveTab" ><i class="icon-pencil"></i> <span>修改</span></a>
	</div>
</div>
<table class="table" width="100%" layoutH="130" rel="codeitem-pager">
	<thead>
		<tr>
			<th width="150" orderField="code" 
				class="${param.orderField eq 'code'?param.orderDirection:''}">代码</th>
			<th orderField="name" class="${param.orderField eq 'name'?param.orderDirection:''}">名称</th>
		</tr>
		
	</thead>
	<tbody>
		<c:forEach items="${content}" var="item">
			<tr target="codeitem" rel="${item.codeitemId}"><td>${item.code}</td><td>${item.codeitemname}</td></tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage rel="codeitem-pager" style="min-width:300px;"></sdf:dwzPage>
</div>