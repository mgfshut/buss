<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-dept-index/dept-pager" method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="pageNum" value="${pageList.number+1}" />
<input type="hidden" name="numPerPage" value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" /> 
<input type="hidden" name="orderField" value="${param.orderField eq null?'num':param.orderField}" />
<input type="hidden" name="orderDirection" value="${param.orderDirection eq null?'asc':param.orderDirection}" />
<div class="pageHeader">
	<div class="searchBar container-fluid">
		<div class="row">
			<div class="col-xs-4"><input name="deptname" placeholder="输入机构名称" type="text" class="form-control input-sm"  value="${param.deptname}"></div>
			<div class="col-xs-4"><input name="parent.deptname" placeholder="输入上级机构名称" type="text" class="form-control input-sm" value="${param['parent.deptname']}"></div>
			<div class="col-xs-offset-2 col-xs-1" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
		</div>
	</div>
</div>
</form> 
<div class="pageContent" >
<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-dept-form" target="navTab" data-parent="depteditpage" rel="depteditpage" title="添加机构" ><i class="icon-plus"></i> <span>添加</span></a>
			<a class="btn btn-danger btn-sm" href="service/dept-remove-{id}" target="ajaxTodo" title="确定要删除机构吗?"><i class="icon-minus"></i> <span>删除</span></a>
			<a class="btn btn-success btn-sm" href="module/sys-dept-form/dept-update-{id}" target="navTab" data-parent="depteditpage" rel="depteditpage"  title="修改机构"><i class="icon-pencil"></i> <span>修改</span></a>
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="num" class="${param.orderField eq 'num'?param.orderDirection:''}">机构编号</th>
			<th orderField="deptname" class="${param.orderField eq 'deptname'?param.orderDirection:''}">机构名称</th>
			<th orderField="parent.deptname" class="${param.orderField eq 'parent.deptname'?param.orderDirection:''}" >上级机构</th>
			<th orderField="createtime" class="${param.orderField eq 'createtime'?param.orderDirection:''}" >创建时间</th>
			<th orderField="createUser.realname" class="${param.orderField eq 'createUser.realname'?param.orderDirection:''}" >操作人</th>
		
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageList.content }" var="item">
			<tr target="id" rel="${item.deptId}">
				<td><a href="module/sys-dept-detail/dept-detail-${item.deptId }" title="机构详情" target="navTab" data-parent="depteditpage" rel="depteditpage"><span>${item.num}</span></a></td>
				<td>${item.deptname }</td>
				<td>${item.parent.deptname}</td>
				<td>${item.createtime }</td>
				<td>${item.createUser.realname }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPagel style="width:300px;" rel="reuslt-page"></sdf:dwzPagel>
</div>