<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>
<!-- 分页、搜索表单 -->
<form id="pagerForm" action="module/sys-module-index/module-pager"
	method="post" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="pageNum" value="${param.page.pageNum eq null?1:param.page.pageNum}" />
	<input type="hidden" name="numPerPage" value="${param.page.numPerPage eq null?10:param.page.numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />
	<div class="pageHeader"> 
		<div class="searchBar container-fluid">
			<div class="row">
				<div class="col-xs-4">
					<input name="name" type="text" value="${param.name}"
						placeholder="输入菜单名称" class="form-control input-sm col-xs-3"
						size="4">
				</div>
				<div class="col-xs-4">
					<input name="parent.name" type="text" value="${param['parent.name']}"
						placeholder="输入上级菜单" class="form-control input-sm col-xs-3"
						size="4">
				</div>
				
				<div class="col-xs-offset-1 col-xs-1" style="text-align: right">
					<button class="btn btn-primary btn-sm" type="submit">
						<i class="icon-search"></i> <span>检索</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">
	<div class="panelBar">
		<div class="btn-group" style="margin: 4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-module-form" data-parent="genreeditmanager"
				mask="true" height="500" target="navTab" rel="customerSaveDialog" title="添加菜单">
				<i class="icon-plus"></i> 
				<span>添加</span>
			</a> 
			<a class="btn btn-info btn-sm" href="module/sys-module-form1/module-{moduleId}" data-parent="genreeditmanager"
				mask="true" height="500" target="navTab" rel="customerSaveDialog" title="添加子级菜单">
				<i class="icon-list-alt"></i> 
				<span>添加子级菜单</span>
			</a> 
			<a class="btn btn-danger btn-sm" href="service/module-remove-{moduleId}" target="ajaxTodo" data-parent="genreeditmanager"
				title="确定要删除吗?">
				<i class="icon-minus"></i> 
				<span>删除</span>
			</a> 
			<a class="btn btn-success btn-sm" href="module/sys-module-form/module-{moduleId}" data-parent="genreeditmanager"
				mask="true" height="500" title="修改菜单" target="navTab" rel="customerUpdateDialog" >
				<i class="icon-pencil"></i> 
				<span>修改</span>
			</a>				
		</div>
	</div>
	<table class="table" width="100%" layoutH="135">
		<thead>
			<tr>
				<th  orderField="priority"
					class="${param.orderField eq 'priority'?param.orderDirection:''}">菜单序号</th>
				<th orderField="codeNum"
					class="${param.orderField eq 'codeNum'?param.orderDirection:''}">菜单编号</th>
				<th orderField="name"
					class="${param.orderField eq 'name'?param.orderDirection:''}">菜单名称</th>
				<th orderField="parentId.name"
					class="${param.orderField eq 'parent.name'?param.orderDirection:''}">上级菜单</th>
				<th orderField="url"
					class="${param.orderField eq 'url'?param.orderDirection:''}">菜单地址</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${content}" var="item" varStatus="status">
				<tr target="moduleId" rel="${item.moduleId}">
					<td>${ status.index + 1}</td>
					<td>${item.codeNum}</td>
					<td class="name">${item.name}</td>
					<td>${item.parent.name}</td>
					<td>${item.url}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>