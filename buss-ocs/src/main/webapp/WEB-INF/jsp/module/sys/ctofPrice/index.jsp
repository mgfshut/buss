<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
function modifyInfo(status){
    $("#inputDetailBtn", navTab.getCurrentPanel()).click();
}
$(function(){
	$("td[txStatusTd]").each(function(){
		var valTd = $(this).attr("txStatusTd");
		var valStr = $("select[name='txStatus'] option[value='"+valTd+"']", navTab.getCurrentPanel()).text();
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
<form id="pagerForm" action="module/sys-ctofPrice-index/transactionInfo-ctofPrice" method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 

<!-- 状态字典缓存 -->
<ys:codemapSelect codemap="txStatus" name="txStatus" disabled="disabled" style="display:none"></ys:codemapSelect>
<!-- 后台排序按创建时间先后顺序排，asc  -->
<!-- 默认是查询所有状态数据，然后这个值根据tab选择来改变 -->
<input type="hidden" name="txStatus" value="${param.txStatus}" />
<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<ul class="nav nav-tabs">
         <li<c:if test="${empty(param.txStatus) || param.txStatus == ''}"> class="active"</c:if>><a href="#all" data-toggle="tab" onclick="changeZt('')">全部</a></li>
         <li<c:if test="${!empty(param.txStatus) && param.txStatus == '20'}"> class="active"</c:if>><a href="#dhp" data-toggle="tab" onclick="changeZt('20')">待回盘</a></li>
         <li<c:if test="${!empty(param.txStatus) && param.txStatus == '21'}"> class="active"</c:if>><a href="#yhp" data-toggle="tab" onclick="changeZt('21')">已回盘</a></li>
        </ul>
	</div>
</div>
</div>
</form> 

<div id="myTabContent" class="tab-content">
  <div class="tab-pane fade<c:if test="${empty(param.txStatus) || param.txStatus == ''}"> active in</c:if>" id="all">
  	<div class="pageContent">
	  	<div class="panelBar">
			<div class="btn-group" style="margin:4px 5px;">
				<a class="btn btn-primary btn-sm" href="module/sys-ctofPrice-form1/transactionInfo-{transactionInfoId}" mask="true" target="navTab" 
					rel="usereditmanager" data-parent="usereditmanager" title="输入二次采购价格" id="inputOfferPriBtn"><i class="icon-plus"></i> <span>输入回盘价格</span></a>
				<a class="btn btn-info btn-sm" href="module/sys-ctofPrice-detail/transactionInfo-{transactionInfoId}"  
					 title="查看详情"  target="navTab"   id="inputDetailBtn"><i class="icon-desktop"></i> <span>查看详情</span></a>
			</div>
		</div>
		<table class="table" width="100%" layoutH="146">
			<thead>
				<tr>
					<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
					<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
					<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
					<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格</th>
					<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装数量</th>
					<th orderField="cusCha" class="${param.orderField eq 'cusCha'?param.orderDirection:''}">渠道</th>
					<th orderField="cusName" class="${param.orderField eq 'cusName'?param.orderDirection:''}">客户名称</th>
					<th orderField="cusLoc" class="${param.orderField eq 'cusLoc'?param.orderDirection:''}">区域</th>
					<th orderField="cateSup" class="${param.orderField eq 'cateSup'?param.orderDirection:''}">供应商</th>
					<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">需求量</th>
					<th>客户价</th>
					<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resObject}" var="item">
				   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo('${item.txStatus}')"  >
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
						<td>${item.pcasPri}</td>
						<td> 
							<c:choose>
								<c:when test="${item.txStatus == '20'}">待回盘</c:when>
								<c:otherwise>已回盘</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
	</div>
  </div>
  <div class="tab-pane fade<c:if test="${empty(param.txStatus) || param.txStatus == '20'}"> active in</c:if>" id="dhp">
  	<div class="pageContent">
  		<div class="panelBar">
			<div class="btn-group" style="margin:4px 5px;">
				<a class="btn btn-primary btn-sm" href="module/sys-ctofPrice-form1/transactionInfo-{transactionInfoId}" mask="true" target="navTab" 
					rel="usereditmanager" data-parent="usereditmanager" title="输入二次采购价格" id="inputOfferPriBtn"><i class="icon-plus"></i> <span>输入回盘价格</span></a>
				<a class="btn btn-info btn-sm" href="module/sys-ctofPrice-detail/transactionInfo-{transactionInfoId}"   
					 title="查看详情"  target="navTab"  id="inputDetailBtn"><i class="icon-desktop"></i> <span>查看详情</span></a>
			</div>
		</div>
		<table class="table" width="100%" layoutH="140">
			<thead>
				<tr>
					<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
					<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
					<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
					<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格</th>
					<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装数量</th>
					<th orderField="cusCha" class="${param.orderField eq 'cusCha'?param.orderDirection:''}">渠道</th>
					<th orderField="cusName" class="${param.orderField eq 'cusName'?param.orderDirection:''}">客户名称</th>
					<th orderField="cusLoc" class="${param.orderField eq 'cusLoc'?param.orderDirection:''}">区域</th>
					<th orderField="cateSup" class="${param.orderField eq 'cateSup'?param.orderDirection:''}">供应商</th>
					<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">需求量</th>
					<th>客户价</th>
					<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resObject}" var="item">
				   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo('20')"  >
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
						<td>${item.pcasPri}</td>
						<td>待回盘</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
 	</div>
   </div>
   <div class="tab-pane fade<c:if test="${empty(param.txStatus) || param.txStatus == '21'}"> active in</c:if>" id="yhp">
  	<div class="pageContent">
  		<div class="panelBar">
			<div class="btn-group" style="margin:4px 5px;">
				<a class="btn btn-info btn-sm" href="module/sys-ctofPrice-detail/transactionInfo-{transactionInfoId}"  
					 title="查看详情"  target="navTab"  id="inputDetailBtn" ><i class="icon-desktop"></i> <span>查看详情</span></a>
			</div>
		</div>
		<table class="table" width="100%" layoutH="140">
		<thead>
			<tr>
				<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
				<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
				<th orderField="prodPla" class="${param.orderField eq 'prodPla'?param.orderDirection:''}">产地</th>
				<th orderField="cateStan" class="${param.orderField eq 'cateStan'?param.orderDirection:''}">规格</th>
				<th orderField="pkgQuan" class="${param.orderField eq 'pkgQuan'?param.orderDirection:''}">包装数量</th>
				<th orderField="cusCha" class="${param.orderField eq 'cusCha'?param.orderDirection:''}">渠道</th>
				<th orderField="cusName" class="${param.orderField eq 'cusName'?param.orderDirection:''}">客户名称</th>
				<th orderField="cateSup" class="${param.orderField eq 'cateSup'?param.orderDirection:''}">供应商</th>
				<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">需求量</th>
				<th>客户价</th>
				<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resObject}" var="item">
			   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo('21')"  >
					<td>${item.cateName}</td>
					<td>${item.manuNum}</td>
					<td><ys:codemapConvert codemap="prodPla" value="${item.prodPla }"/></td>
					<td><ys:codemapConvert codemap="cateStan" value="${item.cateStan }"/></td>
					<td>${item.pkgQuan}</td>
					<td><ys:codemapConvert codemap="cusCha" value="${item.cusCha }"/></td>
					<td>${item.cusName}</td>
					<td>${item.cateSup}</td>
					<td>${item.txAmo}</td>
					<td>${item.pcasPri}</td>
					<td>已回盘</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
	</div>
  </div>
</div>
