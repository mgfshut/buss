<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<!-- 分页、搜索表单 -->	
<form id="pagerForm" class="form-horizontal" role="form" action="module/test-index/test-pager" method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="pageNum" value="${number+1}" />
<input type="hidden" name="numPerPage" value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" /> 
<input type="hidden" name="orderField" value="${param.orderField eq null?'codemapId':param.orderField}" />
<input type="hidden" name="orderDirection" value="${param.orderDirection eq null?'asc':param.orderDirection}" />
<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-4"><input name="codemapname" size="10" placeholder="输入名称" type="text" class="form-control input-sm" value="${param.codemapname}"></div>
		<div class="col-xs-4"><input name="code" size="10" placeholder="输入代码" type="text" class="form-control input-sm" value="${param.code}"></div>
		<div class="col-xs-offset-2 col-xs-2" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
	</div>
</div>
</div>
</form> 
<div class="pageContent" >
<div class="panelBar">
	<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/test-form" mask="true" target="dialog" rel="usereditmanager" data-parent="usereditmanager" title="添加用户"><i class="icon-plus"></i> <span>添加</span></a>
			<a class="btn btn-danger btn-sm" href="service/test-remove-{id}" target="ajaxTodo" title="确定要删除用户吗?" ><i class="icon-minus"></i> <span>删除</span></a></li>
			<a class="btn btn-success btn-sm" href="module/test-formb/testcol-{id}" title="修改用户" target="dialog" rel="usereditmanager" data-parent="usereditmanager"  ><i class="icon-pencil"></i> <span>修改</span></a>
	</div>	
</div>
<table class="table" width="100%" layoutH="138">
	<thead>
		<tr>
			<th orderField="codemapId" class="${param.orderField eq 'codemapId'?param.orderDirection:''}">ID</th>
			<th orderField="codemapname" class="${param.orderField eq 'codemapname'?param.orderDirection:''}">名称</th>
			<th orderField="code" class="${param.orderField eq 'code'?param.orderDirection:''}" >代码</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${content}" var="item">
			<tr target="id" rel="${item.codemapId}">
			<td>${item.codemapId}</td>
			<td>${item.codemapname}</td>
			<td>${item.code}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>