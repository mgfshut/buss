<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
$(function(){
	
	$("td[contStatusTd]").each(function(){
		var valTd = $(this).attr("contStatusTd");
		var valStr = $("select[name='contStatus'] option[value='"+valTd+"']", navTab.getCurrentPanel()).text();
		$(this).html(valStr);
	});
})
function changeZt(val){
	$("input[name='contStatus']", navTab.getCurrentPanel()).val(val);
	$("#pagerForm", navTab.getCurrentPanel()).submit();
}
</script>
<!-- 分页、搜索表单 -->	
<form id="pagerForm" action="module/sys-checkPay-index/contractInfo-CWpager" method="post" onsubmit="return navTabSearch(this);">
<input type="hidden" name="currentPage" value="${page.currentPage}" />
<input type="hidden" name="totalResult" value="${page.totalResult}" />
<input type="hidden" name="totalPage" value="${page.totalPage}" />
<input type="hidden" name="showCount" value="${param.page.showCount eq null?10:param.page.showCount}" /> 
<input type="hidden" name="orderDirection" value="${param.page.orderDirection eq null?'asc':param.page.orderDirection}" />
<!-- 状态字典缓存 -->
<ys:codemapSelect codemap="contStatus" name="contStatus" disabled="disabled" style="display:none"></ys:codemapSelect>
<!-- 后台排序按创建时间先后顺序排，asc  -->
<!-- 默认是查询所有状态数据，然后这个值根据tab选择来改变 -->
<input type="hidden" name="contStatus" value="${param}" />
<div class="pageHeader">
<div class="searchBar container-fluid">
	<div class="row">
		<ul class="nav nav-tabs">
         <li<c:if test="${empty(param.contStatus) || param.contStatus == ''}"> class="active"</c:if>><a href="#all" data-toggle="tab" onclick="changeZt('')">全部</a></li>
         <%-- <li<c:if test="${!empty(param.contStatus) && param.contStatus == '20'}"> class="active"</c:if>><a href="#htsd" data-toggle="tab" onclick="changeZt('20')">合同审定</a></li> --%>
         <li<c:if test="${!empty(param.contStatus) && param.contStatus == '30'}"> class="active"</c:if>><a href="#xzgz" data-toggle="tab" onclick="changeZt('30')">待处理</a></li>
         <li<c:if test="${!empty(param.contStatus) && param.contStatus == '40'}"> class="active"</c:if>><a href="#cwqr" data-toggle="tab" onclick="changeZt('40')">已处理</a></li>
         <li<c:if test="${!empty(param.contStatus) && param.contStatus == '41'}"> class="active"</c:if>><a href="#jyqx" data-toggle="tab" onclick="changeZt('41')">交易取消</a></li>
       </ul>
	</div>
</div>
</div>
</form>

<div id="myTabContent" class="tab-content">
  <div class="tab-pane fade<c:if test="${empty(param.contStatus) || param.contStatus == ''}"> active in</c:if>" id="all">
  	<div class="pageContent">
		<div class="panelBar">
				<div class="btn-group" style="margin:4px 5px;">
					<a class="btn btn-primary btn-sm" href="module/sys-checkPay-form/contractInfo-{contractInfoId}"  
						 title="合同审核"  target="navTab" rel="contractInfoSaveDialog"><i class="icon-check"></i> <span>处理</span></a>
					<a class="btn btn-info btn-sm" href="module/sys-checkPay-detail/contractInfo-{contractInfoId}"  
						 title="查看详情"  target="navTab" ><i class="icon-desktop"></i> <span>查看详情</span></a>
				</div>
		</div>
	    <table class="table" width="100%" layoutH="146">
			<thead>
				<tr>
					<th>客户名称</th>
					<th>法人代表</th>
					<th>企业信用代码</th>
					<th>联系方式</th>
					<th>客户地址</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resList}" var="item">
					<tr target="contractInfoId" rel="${item.contractInfoId}">
						<td>${item.cusName}</td>
						<td>${item.legalPer}</td>
						<td>${item.creditCode}</td>
						<td>${item.entTel}</td>
						<td>${item.entAddr}</td>
						<td <c:if test="${!empty(item.contStatus)}">contStatusTd="${item.contStatus}"</c:if>>&nbsp;</td>
						<!-- <td>
							<a class="btn btn-primary btn-sm" href="sys/print/contractInfo/${item.contractInfoId}" style="line-height:11px;"
						 	title="打印合同"  target="_blank" ><span>打印合同</span></a>
						</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
	</div>
  </div>
  <div class="tab-pane fade<c:if test="${!empty(param.contStatus) && param.contStatus == '20'}"> active in</c:if>" id="htsd">
  	<div class="pageContent">
		<div class="panelBar">
				<div class="btn-group" style="margin:4px 5px;">
					<a class="btn btn-info btn-sm" href="module/sys-checkPay-detail/contractInfo-{contractInfoId}"  
						 title="查看详情"  target="navTab" ><i class="icon-desktop"></i> <span>查看详情</span></a>
				</div>
		</div>
	    <table class="table" width="100%" layoutH="146">
			<thead>
				<tr>
					<th>客户名称</th>
					<th>法人代表</th>
					<th>企业信用代码</th>
					<th>联系方式</th>
					<th>客户地址</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resList}" var="item">
					<tr target="contractInfoId" rel="${item.contractInfoId}">
						<td>${item.cusName}</td>
						<td>${item.legalPer}</td>
						<td>${item.creditCode}</td>
						<td>${item.entTel}</td>
						<td>${item.entAddr}</td>
						<td <c:if test="${!empty(item.contStatus)}">contStatusTd="${item.contStatus}"</c:if>>&nbsp;</td>
						<!-- <td>
							<a class="btn btn-primary btn-sm" href="sys/print/contractInfo/${item.contractInfoId}" style="line-height:11px;"
						 	title="打印合同"  target="_blank" ><span>打印合同</span></a>
						</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
	</div>
  </div>
  <div class="tab-pane fade<c:if test="${!empty(param.contStatus) && param.contStatus == '30'}"> active in</c:if>" id="xzgz">
  	<div class="pageContent">
		<div class="panelBar">
				<div class="btn-group" style="margin:4px 5px;">
					<a class="btn btn-primary btn-sm" href="module/sys-checkPay-form/contractInfo-{contractInfoId}"  
						 title="合同审核"  target="navTab" rel="contractInfoSaveDialog"><i class="icon-check"></i> <span>处理</span></a>
					<a class="btn btn-info btn-sm" href="module/sys-checkPay-detail/contractInfo-{contractInfoId}"  
						 title="查看详情"  target="navTab" ><i class="icon-desktop"></i> <span>查看详情</span></a>
				</div>
		</div>
	    <table class="table" width="100%" layoutH="146">
			<thead>
				<tr>
					<th>客户名称</th>
					<th>法人代表</th>
					<th>企业信用代码</th>
					<th>联系方式</th>
					<th>客户地址</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resList}" var="item">
					<tr target="contractInfoId" rel="${item.contractInfoId}">
						<td>${item.cusName}</td>
						<td>${item.legalPer}</td>
						<td>${item.creditCode}</td>
						<td>${item.entTel}</td>
						<td>${item.entAddr}</td>
						<td <c:if test="${!empty(item.contStatus)}">contStatusTd="${item.contStatus}"</c:if>>&nbsp;</td>
						<!-- <td>
							<a class="btn btn-primary btn-sm" href="sys/print/contractInfo/${item.contractInfoId}" style="line-height:11px;"
						 	title="打印合同"  target="_blank" ><span>打印合同</span></a>
						</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
  </div>
  <div class="tab-pane fade<c:if test="${!empty(param.contStatus) && param.contStatus == '40'}"> active in</c:if>" id="cwqr">
  	<div class="pageContent">
		<div class="panelBar">
				<div class="btn-group" style="margin:4px 5px;">
					<a class="btn btn-info btn-sm" href="module/sys-checkPay-detail/contractInfo-{contractInfoId}"  
						 title="查看详情"  target="navTab" ><i class="icon-desktop"></i> <span>查看详情</span></a>
				</div>
		</div>
	    <table class="table" width="100%" layoutH="146">
			<thead>
				<tr>
					<th>客户名称</th>
					<th>法人代表</th>
					<th>企业信用代码</th>
					<th>联系方式</th>
					<th>客户地址</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resList}" var="item">
					<tr target="contractInfoId" rel="${item.contractInfoId}">
						<td>${item.cusName}</td>
						<td>${item.legalPer}</td>
						<td>${item.creditCode}</td>
						<td>${item.entTel}</td>
						<td>${item.entAddr}</td>
						<td <c:if test="${!empty(item.contStatus)}">contStatusTd="${item.contStatus}"</c:if>>&nbsp;</td>
						<!-- <td>
							<a class="btn btn-primary btn-sm" href="sys/print/contractInfo/${item.contractInfoId}" style="line-height:11px;"
						 	title="打印合同"  target="_blank" ><span>打印合同</span></a>
						</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
  </div>
  <div class="tab-pane fade<c:if test="${!empty(param.contStatus) && param.contStatus == '41'}"> active in</c:if>" id="jyqx">
  	<div class="pageContent">
		<div class="panelBar">
				<div class="btn-group" style="margin:4px 5px;">
					<a class="btn btn-info btn-sm" href="module/sys-checkPay-detail/contractInfo-{contractInfoId}"  
						 title="查看详情"  target="navTab" ><i class="icon-desktop"></i> <span>查看详情</span></a>
				</div>
		</div>
	    <table class="table" width="100%" layoutH="146">
			<thead>
				<tr>
					<th>客户名称</th>
					<th>法人代表</th>
					<th>企业信用代码</th>
					<th>联系方式</th>
					<th>客户地址</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resList}" var="item">
					<tr target="contractInfoId" rel="${item.contractInfoId}">
						<td>${item.cusName}</td>
						<td>${item.legalPer}</td>
						<td>${item.creditCode}</td>
						<td>${item.entTel}</td>
						<td>${item.entAddr}</td>
						<td <c:if test="${!empty(item.contStatus)}">contStatusTd="${item.contStatus}"</c:if>>&nbsp;</td>
						<!-- <td>
							<a class="btn btn-primary btn-sm" href="sys/print/contractInfo/${item.contractInfoId}" style="line-height:11px;"
						 	title="打印合同"  target="_blank" ><span>打印合同</span></a>
						</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<sdf:dwzPage style="width:300px;"></sdf:dwzPage>
  </div>
</div>

