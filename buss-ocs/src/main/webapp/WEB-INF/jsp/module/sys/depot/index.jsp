<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-depot-index/depot-pager" method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="pageNum" value="${pageList.number+1}" />
<input type="hidden" name="numPerPage" value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" /> 
<input type="hidden" name="orderField" value="${param.orderField eq null?'depotNo':param.orderField}" />
<input type="hidden" name="orderDirection" value="${param.orderDirection eq null?'asc':param.orderDirection}" />
<div class="pageHeader">
	<div class="searchBar container-fluid">
		<div class="row">
			<div class="col-xs-4"><input name="depotName" placeholder="输入仓库名称" type="text" class="form-control input-sm"  value="${param.depotName}"></div>
			<div class="col-xs-offset-2 col-xs-1" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
		</div>
	</div>
</div>
</form> 
<div class="pageContent" >
<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-depot-form" target="navTab" data-parent="depoteditpage" rel="depoteditpage" title="添加仓库" ><i class="icon-plus"></i> <span>添加</span></a>
			<a class="btn btn-danger btn-sm" href="service/depot-remove-{id}" target="ajaxTodo" title="确定要删除仓库吗?"><i class="icon-minus"></i> <span>删除</span></a>
			<a class="btn btn-success btn-sm" href="module/sys-depot-form/depot-update-{id}" target="navTab" data-parent="depoteditpage" rel="depoteditpage"  title="修改仓库"><i class="icon-pencil"></i> <span>修改</span></a>
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="depotNo" class="${param.orderField eq 'depotNo'?param.orderDirection:''}">仓库编号</th>
			<th orderField="depotName" class="${param.orderField eq 'depotName'?param.orderDirection:''}">仓库名称</th>
			<th orderField="createTime" class="${param.orderField eq 'createTime'?param.orderDirection:''}" >创建时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${content}" var="item">
			<tr target="id" rel="${item.depotId}">
				<td><a href="module/sys-depot-detail/depot-detail-${item.depotId }" title="仓库详情" target="navTab" data-parent="depoteditpage" rel="depoteditpage"><span>${item.depotNo}</span></a></td>
				<td>${item.depotName }</td>
				<td>${item.createTime }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPagel style="width:300px;" rel="reuslt-page"></sdf:dwzPagel>
</div>