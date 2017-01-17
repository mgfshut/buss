<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 

<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-contractInfo-index/contractInfo-pager" method="post" onsubmit="return navTabSearch(this);">

<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderField" value="${param.page.orderField eq null?'memberId':param.page.orderField}" />
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />

<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<div class="col-xs-4"><input  name="contractInfoId" placeholder="输入合同编号筛选" type="text" 
			class="form-control input-sm col-xs-4" size="10" value="${param.contractInfoId}"></div>
		<div class="col-xs-4"><input  name="totPri" size="10" placeholder="输入总价筛选" type="text" 
			class="form-control input-sm col-xs-4" value="${param.totPri}"></div>
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
			<a class="btn btn-primary btn-sm" href="module/sys-contractInfo-form/contractInfo-print-{contractInfoId}"  
				 title="合同审核"  target="navTab" rel="contractInfoSaveDialog"><i class="icon-check"></i> <span>合同审核</span></a>
			<!-- <a class="btn btn-primary btn-sm" href="sys/print/contractInfo/{contractInfoId}"  
				 title="打印合同"  target="_blank" ><i class="icon-check"></i> <span>打印合同</span></a> -->
		</div>
</div>
<table class="table" width="100%" layoutH="140">
	<thead>
		<tr>
			<th orderField="contractInfoId" class="${param.orderField eq 'contractInfoId'?param.orderDirection:''}">合同编号</th>
			<th orderField="totPri" class="${param.orderField eq 'totPri'?param.orderDirection:''}">总价</th>
			<th orderField="txAmo" class="${param.orderField eq 'txAmo'?param.orderDirection:''}">交易数量</th>
			<th orderField="ctofPri" class="${param.orderField eq 'ctofPri'?param.orderDirection:''}">回盘价</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="item">
			<tr target="contractInfoId" rel="${item.contractInfoId}">
				<td>${item.contractInfoId}</td>
				<td>${item.totPri}</td>
				<td>${item.txAmo}</td>
				<td>${item.ctofPri}</td>
				<td>
					<a class="btn btn-primary btn-sm" href="sys/print/contractInfo/${item.contractInfoId}" style="line-height:11px;"
				 	title="打印合同"  target="_blank" ><span>打印合同</span></a>
				</td>
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
    	
    	
        $('#check').click(function(){
        	var cateName = $('#selectCateName').val();
        	var manuNum = $('#selectManuNum').val();
        	$.download('/sys/excel/category', 'post', cateName, manuNum); // 下载文件
        })
        
        function printContract(contractInfoId){
        	alert(contractInfoId);
        };
        
    })
</script>