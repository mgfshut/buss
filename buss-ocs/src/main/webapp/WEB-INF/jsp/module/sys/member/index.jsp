<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<!-- 分页、搜索表单 -->	
<form id="pagerForm" class="form-horizontal" role="form" action="module/sys-member-index/member-pager" 
	method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="pageNum" value="${param.page.pageNum eq null?1:param.page.pageNum}" />
<input type="hidden" name="numPerPage" value="${param.page.numPerPage eq null?10:param.page.numPerPage}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />
<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-2">
			<input id="memberName" name="memberName" size="10" placeholder="输入用户姓名" 
				type="text" class="form-control input-sm" value="${param.memberName}">
		</div>
		<div class="col-xs-offset-2 col-xs-2" style="text-align: right;">
			<button class="btn btn-primary btn-sm" type="submit">
				<i class="icon-search"></i> 
				<span>检索</span>
			</button>
		</div>
	</div>
</div>
</div>
</form> 
<div class="pageContent" >
<div class="panelBar">
	<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-member-form" mask="true" target="navTab" rel="usereditmanager" data-parent="usereditmanager" title="添加用户"><i class="icon-plus"></i> <span>添加</span></a>
			<a class="btn btn-danger btn-sm" href="service/member-remove-{id}" target="ajaxTodo" title="确定要删除用户吗?" ><i class="icon-minus"></i> <span>删除</span></a></li>
			<a class="btn btn-success btn-sm" href="module/sys-member-form/member-{id}" title="修改用户" target="navTab" rel="usereditmanager" data-parent="usereditmanager"  ><i class="icon-pencil"></i> <span>修改</span></a>
			<a class="btn btn-info btn-sm" href="module/sys-user-roles/user-roles-{id}" mask="true" title="角色分配" target="navTab" rel="usereditmanager" width="550" height="450"><i class="icon-user"></i> <span>角色分配</span></a>
	</div>	
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="memberId" class="${param.orderField eq 'memberId'?param.orderDirection:''}">用户ID</th>
			<th orderField="userName" class="${param.orderField eq 'userName'?param.orderDirection:''}">用户名</th>
			<th orderField="memberName" class="${param.orderField eq 'memberName'?param.orderDirection:''}" >姓名</th>
			<th orderField="memberSex" class="${param.orderField eq 'memberSex'?param.orderDirection:''}" >性别</th>
			<th orderField="memberPhone" class="${param.orderField eq 'memberPhone'?param.orderDirection:''}" >手机号</th>
			<th orderField="createTime" class="${param.orderField eq 'createTime'?param.orderDirection:''}" >创建时间</th>
			<th orderField="userStatus" class="${param.orderField eq 'userStatus'?param.orderDirection:''}" >用户状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="id" rel="${item.memberId}">
			<td>${item.memberId}</td>
			<td><a href="module/sys-user-detail/user-${item.memberId }" title="用户详细" target="navTab" rel="usereditmanager" data-parent="usereditmanager">${item.userName}</a></td>
			<td>${item.memberName}</td>
			<td>
				<ys:codemapConvert codemap="sex" value="${item.memberSex }"></ys:codemapConvert>
			</td>
			<td>${item.memberPhone }</td>
			<td>${item.createTime }</td>
			<td>
				<ys:codemapConvert codemap="userStatus" value="${item.userStatus }"></ys:codemapConvert>
			</td>
			
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>