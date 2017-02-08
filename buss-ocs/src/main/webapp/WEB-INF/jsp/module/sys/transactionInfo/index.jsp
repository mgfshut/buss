<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-transactionInfo-index/transactionInfo-pager" method="post" onsubmit="return navTabSearch(this);">

<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
	
		<div class="col-xs-4 input-group">
            <input name="tranStartTime" placeholder="交易时间始" type="text" class="form-control" value="${param.tranStartTime}" readonly="readonly" data-provide="datepicker" data-format="yyyy-mm-dd">
            <span class="input-group-addon btn" id="clearBtn-tranStartTime">清除</span>
            <span class="input-group-addon">-</span>
            <input name="tranEndTime"  placeholder="交易时间止" type="text" class="form-control" value="${param.tranEndTime}" readonly="readonly" data-provide="datepicker" data-format="yyyy-mm-dd">
            <span class="input-group-addon btn" id="clearBtn-tranEndTime">清除</span>
        </div>
        
		<div class="col-xs-4">
			<ys:codemapSelect2 codemap="txStatus"  selectName="txStatus" value="" defaultText="交易状态"
					classes="form-control"></ys:codemapSelect2>
		</div>
		<div class="col-xs-offset-2 col-xs-2" style="text-align: right;">
			<button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> <span>检索</span></button>
		</div>
	</div>
</div>
</div>
</form> 
<div class="pageContent">
	<div class="panelBar">
		<div class="btn-group" style="margin:4px 5px;">
			<a class="btn btn-info btn-sm" href="module/sys-transactionInfo-detail/transactionInfo-detail-{transactionInfoId}"  
				 title="查看详情"  target="navTab"   id="inputDetailBtn"><i class="icon-desktop"></i> <span>查看详情</span></a>
		</div>
	</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="cateName" class="${param.orderField eq 'cateName'?param.orderDirection:''}">品类名称</th>
			<th orderField="manuNum" class="${param.orderField eq 'manuNum'?param.orderDirection:''}">厂号</th>
			<th>产地</th>
			<th>规格</th>
			<th>客户姓名</th>
			<th>渠道</th>
			<th>区域</th>
			<th orderField="txStatus" class="${param.orderField eq 'txStatus'?param.orderDirection:''}">交易状态</th>
			<th orderField="dealTime" class="${param.orderField eq 'dealTime'?param.orderDirection:''}">交易日期</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="transactionInfoId" rel="${item.transactionInfoId}">
				<td>${item.cateName}</td>
				<td>${item.manuNum}</td>
				<td>${item.prodPla}</td>
				<td>${item.cateStan}</td>
				<td>${item.cusName}</td>
				<td><ys:codemapConvert codemap="cusCha" value="${item.cusCha }"/></td>
				<td>${item.cusLoc}</td>
				<td><ys:codemapConvert codemap="txStatus" value="${item.txStatus }"/></td>
				<td>${item.dealTime}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
</div>