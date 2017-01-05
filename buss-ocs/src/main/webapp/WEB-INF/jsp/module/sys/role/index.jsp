<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-role-index/role-pager" method="post" onsubmit="return navTabSearch(this);">

<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-4"><input name="roleName" placeholder="输入代码筛选" type="text" class="form-control input-sm col-xs-4" size="10" value="${param.roleName}"></div>
		<div class="col-xs-4"><input name="roleDescribe" size="10" placeholder="输入名称筛选" type="text" class="form-control input-sm col-xs-4" value="${param.roleDescribe}"></div>
		<div class="col-xs-offset-2 col-xs-2" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
	</div>
</div>
</div>
</form> 
<div class="pageContent">
<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" data-parent="rolemanage" href="module/sys-role-form" mask="true" height="500" target="navTab" rel="roleSaveDialog" title="添加角色"><i class="icon-plus"></i> <span>添加</span></a>
			<a class="btn btn-danger btn-sm delete" data-parent="roleservice" href="service/role-remove-{roleId}" target="ajaxTodo" title="确定要删除吗?"><i class="icon-minus"></i> <span>删除</span></a>
			<a class="btn btn-success btn-sm" href="module/sys-role-form/role-{roleId}" mask="true" height="500" title="修改角色"  target="navTab" rel="roleSaveDialog"><i class="icon-pencil"></i> <span>修改</span></a>
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<!-- <th>角色ID</th> -->
			<th orderField="roleName" class="${param.orderField eq 'roleName'?param.orderDirection:''}">角色代码</th>
			<th orderField="roleDescribe" class="${param.orderField eq 'roleDescribe'?param.orderDirection:''}">角色名称</th>
			<th orderField="createTime" class="${param.orderField eq 'createTime'?param.orderDirection:''}">创建时间</th>
			<th orderField="updateTime" class="${param.orderField eq 'updateTime'?param.orderDirection:''}">修改时间</th>
			<th/>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="roleId" rel="${item.roleId}">
				<%-- <td>${item.roleId}</td> --%>
				<td>${item.roleName}</td>
				<td>${item.roleDescribe}</td>
				<td>${item.createTime}</td>
				<td>${item.updateTime}</td>
				<td>
<a href="module/sys-role-form/role-${item.roleId}" mask="true" height="500" title="修改角色" data-parent="rolemanage" target="navTab" rel="roleSaveDialog"><i class="icon-pencil"></i> <span>修改</span></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>