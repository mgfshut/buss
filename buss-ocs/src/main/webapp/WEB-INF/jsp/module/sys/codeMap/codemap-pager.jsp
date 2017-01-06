<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="sys/code/pager" method="post" onsubmit="return divSearch(this, 'codemap-pager');">
	
<input type="hidden" name="currentPage" value="${page.currentPage}" />
<%-- <input type="hidden" name="totalResult" value="${page.totalResult}" /> --%>
<%-- <input type="hidden" name="totalPage" value="${page.totalPage}" /> --%>
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'code':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader" style="border:1px #B8D0D6 solid">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-5"><input name="code" placeholder="输入代码" type="text" class="form-control input-sm textInput" value="${param.code}"></div>
		<div class="col-xs-5"><input name="codeMapName" placeholder="输入名称" type="text" class="form-control input-sm textInput" value="${param.codeMapName}"></div>
		<div class="col-xs-2" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
	</div>
</div>
</div>
</form> 
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<div class="btn-group">
		<a class="btn btn-success btn-sm" callback="reflashCach" href="codemapClean" target="ajaxTodo" mask="true" title="确定要刷新缓存吗?"><i class="icon-refresh"></i> <span>刷新缓存</span></a>
		<a class="btn btn-primary btn-sm" href="sys/code/form" mask="true" target="navTab" rel="codeSaveTab" title="添加代码集"><i class="icon-plus"></i> <span>添加</span></a>
		<a class="btn btn-warning btn-sm" href="sys/code/form/{codeMapId}" mask="true" title="修改代码集" target="navTab" rel="codeSaveTab" ><i class="icon-pencil"></i> <span>修改</span></a>
		<a class="btn btn-danger btn-sm" callback="removeCodeSuccess" href="service/codeMap-remove-{codeMapId}" target="ajaxTodo" title="确定要删除吗?"><i class="icon-minus"></i> <span>删除</span></a>
		</div>
</div>
<table class="table" width="100%" layoutH="173" rel="codemap-pager">
	<thead>
		<tr>
			<th width="150" orderField="code" 
				class="${param.orderField eq 'code'?param.orderDirection:''}">代码</th>
			<th orderField="codeMapName" class="${param.orderField eq 'codeMapName'?param.orderDirection:''}">名称</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="codeMapId" rel="${item.codeMapId}"><td class="code">${item.code}</td><td>${item.codeMapName}</td></tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage rel="codemap-pager" style="width:300px;"></sdf:dwzPage>
</div>