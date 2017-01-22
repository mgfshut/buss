<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
function modifyInfo(){
    //$("#kcipUpdBtn", navTab.getCurrentPanel()).click();
    $("#fixOfferPriceDialog", navTab.getCurrentPanel()).click();
}
</script>
<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-offerPrice-index/category-pager" method="post" onsubmit="return navTabSearch(this);">

<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-4"><input id="selectCateName" name="cateName" placeholder="输入名称筛选" type="text" class="form-control input-sm col-xs-4" size="10" value="${param.cateName}"></div>
		<div class="col-xs-4"><input id="selectManuNum" name="manuNum" size="10" placeholder="输入厂号筛选" type="text" class="form-control input-sm col-xs-4" value="${param.manuNum}"></div>
		<div class="col-xs-offset-2 col-xs-2" style="text-align: right;"><button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button></div>
	</div>
</div>
</div>
</form> 
<div class="pageContent">
<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-primary btn-sm" href="module/sys-offerPrice-addCategory"  
				 title="品类导入"  target="navTab" rel="offerPriceSaveDialog"><i class="icon-plus"></i> <span>品类添加</span></a>
			<!-- <a class="btn btn-success btn-sm" id="fixOfferPrice"><i class="icon-pencil"></i> <span>报盘价录入</span></a>  -->
			<a class="btn btn-success btn-sm" href="module/sys-offerPrice-fixOfferPrice/category-{categoryId}" 
				mask="true" height="500" title="报盘价录入"  target="navTab" rel="fixOfferPriceDialog" id="fixOfferPriceDialog">
				<i class="icon-pencil"></i> <span>报盘价录入</span>
			</a>
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
			<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
			<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
			<th orderField="comm" class="${param.orderField eq 'comm'?param.orderDirection:''}">规格</th>
			<th orderField="offerPri" class="${param.orderField eq 'offerPri'?param.orderDirection:''}">报盘价</th>
			<th orderField="offerAging" class="${param.orderField eq 'offerAging'?param.orderDirection:''}">报盘时效</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="categoryId" rel="${item.categoryId}" ondblclick="modifyInfo()">
				<td>${item.cateName}</td>
				<td>${item.manuNum}</td>
				<td><ys:codemapConvert codemap="prodPla" value="${item.prodPla }"/></td>
				<td><ys:codemapConvert codemap="cateStan" value="${item.cateStan }"/></td>
				<td>${item.offerPri}</td>
				<td>${item.offerAging}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>

<script>
    $(function(){
    	
        $('#fixOfferPrice').click(function(){
        	var categoryId  = $('#selectCateName').val();
        	var manuNum = $('#selectManuNum').val();
        	//$.download('/sys/excel/offerPrice/export', 'post', cateName, manuNum); // 下载文件
        })
    })
</script>