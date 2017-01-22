<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
function modifyInfo(){
    //$("#kcipUpdBtn", navTab.getCurrentPanel()).click();
    $("#inputOfferPriBtn", navTab.getCurrentPanel()).click();
}
</script>
<script>
$(function(){
	$("td[contStatusTd]").each(function(){
		var valTd = $(this).attr("contStatusTd");
		var valStr = $("select[name='contStatus'] option[value='"+valTd+"']", navTab.getCurrentPanel()).text();
		$(this).html(valStr);
	});
})
function changeZt(val){
	$("input[name='txStatus']", navTab.getCurrentPanel()).val(val);
	$("#pagerForm", navTab.getCurrentPanel()).submit();
}
</script>
<!-- txStatus -->

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-offerPrice-index/transactionInfo-ctofPrice" method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<!-- 状态字典缓存 -->
<ys:codemapSelect codemap="txStatus" name="txStatus" disabled="disabled" style="display:none"></ys:codemapSelect>
<!-- 后台排序按创建时间先后顺序排，asc  -->
<!-- 默认是查询所有状态数据，然后这个值根据tab选择来改变 -->
<input type="hidden" name="txStatus" value="${txStatus}" />
<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<ul class="nav nav-tabs">
         <li<c:if test="${empty(param.txStatus) || param.txStatus == ''}"> class="active"</c:if>><a href="#all" data-toggle="tab" onclick="changeZt('')">全部</a></li>
         <li<c:if test="${!empty(param.txStatus) && param.txStatus == '20'}"> class="active"</c:if>><a href="#htsd" data-toggle="tab" onclick="changeZt('20')">待回盘</a></li>
         <li<c:if test="${!empty(param.txStatus) && param.txStatus == '21'}"> class="active"</c:if>><a href="#xzgz" data-toggle="tab" onclick="changeZt('21')">已回盘</a></li>
        </ul>
	</div>
</div>
</div>

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

<div id="myTabContent" class="tab-content">
  <div class="tab-pane fade<c:if test="${empty(param.txStatus) || param.txStatus == ''}"> active in</c:if>" id="all">
  	<div class="pageContent">
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
			<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
			<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
			<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格</th>
			<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装规格</th>
			<th orderField="cusCha" class="${param.orderField eq 'cusCha'?param.orderDirection:''}">渠道</th>
			<th orderField="cusName" class="${param.orderField eq 'cusName'?param.orderDirection:''}">客户名称</th>
			<th orderField="cusLoc" class="${param.orderField eq 'cusLoc'?param.orderDirection:''}">区域</th>
			<th orderField="cateSup" class="${param.orderField eq 'cateSup'?param.orderDirection:''}">供应商</th>
			<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">需求量</th>
			<th orderField="offerAging" class="${param.orderField eq 'offerAging'?param.orderDirection:''}">价格时效</th>
			<th orderField="ctofPri" class="${param.orderField eq 'ctofPri'?param.orderDirection:''}">采购价格</th>
			<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resObject}" var="item">
		   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo()"  >
				<td>${item.cateName}</td>
				<td>${item.manuNum}</td>
				<td><ys:codemapConvert codemap="prodPla" value="${item.prodPla }"/></td>
				<td><ys:codemapConvert codemap="cateStan" value="${item.cateStan }"/></td>
				<td>${item.pkgQuan}</td>
				<td><ys:codemapConvert codemap="cusCha" value="${item.cusCha }"/></td>
				<td>${item.cusName}</td>
				<td>${item.cusLoc}</td>
				<td>${item.cateSup}</td>
				<td>${item.txAmo}</td>
				<td>${item.offerAging}</td>
				<td>${item.ctofPri}</td>
				<td>${item.txStatus}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
		<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
	</div>
  </div>
 </div> 
  <div class="tab-pane fade<c:if test="${empty(param.txStatus) || param.txStatus == ''}"> active in</c:if>" id="htsd">
  	<div class="pageContent">
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
			<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
			<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
			<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格</th>
			<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装规格</th>
			<th orderField="cusCha" class="${param.orderField eq 'cusCha'?param.orderDirection:''}">渠道</th>
			<th orderField="cusName" class="${param.orderField eq 'cusName'?param.orderDirection:''}">客户名称</th>
			<th orderField="cusLoc" class="${param.orderField eq 'cusLoc'?param.orderDirection:''}">区域</th>
			<th orderField="cateSup" class="${param.orderField eq 'cateSup'?param.orderDirection:''}">供应商</th>
			<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">需求量</th>
			<th orderField="offerAging" class="${param.orderField eq 'offerAging'?param.orderDirection:''}">价格时效</th>
			<th orderField="ctofPri" class="${param.orderField eq 'ctofPri'?param.orderDirection:''}">采购价格</th>
			<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resObject}" var="item">
		   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo()"  >
				<td>${item.cateName}</td>
				<td>${item.manuNum}</td>
				<td><ys:codemapConvert codemap="prodPla" value="${item.prodPla }"/></td>
				<td><ys:codemapConvert codemap="cateStan" value="${item.cateStan }"/></td>
				<td>${item.pkgQuan}</td>
				<td><ys:codemapConvert codemap="cusCha" value="${item.cusCha }"/></td>
				<td>${item.cusName}</td>
				<td>${item.cusLoc}</td>
				<td>${item.cateSup}</td>
				<td>${item.txAmo}</td>
				<td>${item.offerAging}</td>
				<td>${item.ctofPri}</td>
				<td>${item.txStatus}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
 </div>
</div>
   <div class="tab-pane fade<c:if test="${empty(param.txStatus) || param.txStatus == ''}"> active in</c:if>" id="xzgz">
  	<div class="pageContent">
	<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
			<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
			<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
			<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格</th>
			<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装规格</th>
			<th orderField="cusCha" class="${param.orderField eq 'cusCha'?param.orderDirection:''}">渠道</th>
			<th orderField="cusName" class="${param.orderField eq 'cusName'?param.orderDirection:''}">客户名称</th>
			<th orderField="cusLoc" class="${param.orderField eq 'cusLoc'?param.orderDirection:''}">区域</th>
			<th orderField="cateSup" class="${param.orderField eq 'cateSup'?param.orderDirection:''}">供应商</th>
			<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">需求量</th>
			<th orderField="offerAging" class="${param.orderField eq 'offerAging'?param.orderDirection:''}">价格时效</th>
			<th orderField="ctofPri" class="${param.orderField eq 'ctofPri'?param.orderDirection:''}">采购价格</th>
			<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resObject}" var="item">
		   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo()"  >
				<td>${item.cateName}</td>
				<td>${item.manuNum}</td>
				<td><ys:codemapConvert codemap="prodPla" value="${item.prodPla }"/></td>
				<td><ys:codemapConvert codemap="cateStan" value="${item.cateStan }"/></td>
				<td>${item.pkgQuan}</td>
				<td><ys:codemapConvert codemap="cusCha" value="${item.cusCha }"/></td>
				<td>${item.cusName}</td>
				<td>${item.cusLoc}</td>
				<td>${item.cateSup}</td>
				<td>${item.txAmo}</td>
				<td>${item.offerAging}</td>
				<td>${item.ctofPri}</td>
				<td>${item.txStatus}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
	</div>
  </div>
 
