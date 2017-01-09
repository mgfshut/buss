<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-dept-index/dept-pager" method="post" onsubmit="return navTabSearch(this);">

	<input type="hidden" name="currentPage" value="${page.currentPage}" />
	<input type="hidden" name="totalResult" value="${page.totalResult}" />
	<input type="hidden" name="totalPage" value="${page.totalPage}" />
	<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
	<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader">
	<div class="searchBar container-fluid">
		<div class="row">
			<div class="col-xs-4"><input name="deptName" placeholder="输入机构名称" type="text" class="form-control input-sm"  value="${param.deptName}"></div>
			<div class="col-xs-4"><input name="parentDeptName" placeholder="输入上级机构名称" type="text" class="form-control input-sm" value="${param.parentDeptName}"></div>
			<div class="col-xs-offset-2 col-xs-1" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
		</div>
	</div>
</div>
</form> 
<div class="pageContent" >
<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-dept-form" target="navTab" data-parent="depteditpage" rel="depteditpage" title="添加机构" ><i class="icon-plus"></i> <span>添加</span></a>
			<a class="btn btn-danger btn-sm" href="service/dept-remove-{deptId}" target="ajaxTodo" title="确定要删除机构吗?"><i class="icon-minus"></i> <span>删除</span></a>
			<a class="btn btn-success btn-sm" href="module/sys-dept-form/dept-{deptId}" target="navTab" data-parent="depteditpage" rel="depteditpage"  title="修改机构"><i class="icon-pencil"></i> <span>修改</span></a>
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="deptCode" class="${param.orderField eq 'deptCode'?param.orderDirection:''}">机构编号</th>
			<th orderField="deptName" class="${param.orderField eq 'deptName'?param.orderDirection:''}">机构名称</th>
			<th orderField="deptType" class="${param.orderField eq 'deptType'?param.orderDirection:''}">机构类型</th>
			<th orderField="parentDeptName" class="${param.orderField eq 'parentDeptName'?param.orderDirection:''}" >上级机构</th>
			<th orderField="createTime" class="${param.orderField eq 'createTime'?param.orderDirection:''}" >创建时间</th>
			<th orderField="updateTime" class="${param.orderField eq 'updateTime'?param.orderDirection:''}" >修改时间</th>
		
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="deptId" rel="${item.deptId}">
				<%-- <td><a href="module/sys-dept-detail/dept-detail-${item.deptId }" title="机构详情" 
					target="navTab" data-parent="depteditpage" rel="depteditpage"><span>${item.deptCode}</span></a></td> --%>
				<td>${item.deptCode }</td>
				<td>${item.deptName }</td>
				<td>
					<ys:codemapConvert codemap="deptType" value="${item.deptType }"></ys:codemapConvert>
				</td>
				<td>${item.parentDeptName}</td>
				<td>${item.createTime }</td>
				<td>${item.updateTime }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPagel style="width:300px;" rel="reuslt-page"></sdf:dwzPagel>
</div>