<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-category-index/category-pager" method="post" onsubmit="return navTabSearch(this);">

<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-4">
			<input id="selectCateName" name="cateName" placeholder="请输入品类名称" 
				type="text" class="form-control input-sm col-xs-4" size="10" value="${param.cateName}">
		</div>
		<div class="col-xs-4">
			<input id="selectManuNum" name="manuNum" size="10" placeholder="输入厂号筛选" 
			type="text" class="form-control input-sm col-xs-4" value="${param.manuNum}">
		</div>
		<div class="col-xs-offset-2 col-xs-2" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
	</div>
</div>
</div>
</form> 
<div class="pageContent">
<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-category-form"  
				 title="品类导入"  target="navTab" rel="categorySaveDialog"><i class="icon-upload-alt"></i> <span>导入EXCEL</span></a>
			<a class="btn btn-success btn-sm" id="export"><i class="icon-download-alt"></i> <span>导出EXCEL</span></a>
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
			<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
			<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
			<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格（kg/件）</th>
			<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装</th>
			<th>报盘价</th>
			<th>有效期</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="categoryId" rel="${item.categoryId}">
				<td>${item.cateName}</td>
				<td>${item.manuNum}</td>
				<td><ys:codemapConvert codemap="prodPla" value="${item.prodPla }"/></td>
				<td><ys:codemapConvert codemap="cateStan" value="${item.cateStan }"/></td>
				<td>${item.pkgQuan}</td>
				<td>${item.offerPri}</td>
				<td>${item.offerAging}</td>
				<td>${item.comm}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>

<script>
    $(function(){
    	//文件下载
    	jQuery.download = function(url, method, cateName, manuNum){
    	    jQuery('<form action="'+url+'" method="'+(method||'post')+'">' +  // action请求路径及推送方法
    	                '<input type="text" name="cateName" value="'+cateName+'"/>' + // 品类名称
    	                '<input type="text" name="manuNum" value="'+manuNum+'"/>' + // 厂号
    	            '</form>')
    	    .appendTo('body').submit().remove();
    	};
    	
    	
        $('#export').click(function(){
        	var cateName = $('#selectCateName').val();
        	var manuNum = $('#selectManuNum').val();
        	$.download('/sys/excel/category/export', 'post', cateName, manuNum); // 下载文件
        })
    })
</script>