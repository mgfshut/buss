<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
$(document).ready(function() {
	
});
function categoryItemSaveDone(json){
	if (json.code == DWZ.statusCode.ok){
		alertMsg.correct(json[DWZ.keys.message]);
		setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
	}else{
		alertMsg.error(json[DWZ.keys.message]);
	}
}
</script>
<div id="fixOfferPriceForm" class="pageContent">
	<form method="post" action="" class="form-horizontal pageForm " onsubmit="return validateCallback(this, categoryItemSaveDone)">
		<input type="hidden" name="transactionInfoId" value="${transactionInfoId}" />
		<div class="pageFormContent container-fluid" layoutH="68">
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户名称：</label>
				<div class="col-sm-8">
				<input type="text" name="cusName" class="form-control textInput" readOnly="readonly" value="${cusName}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户渠道：</label>
				<div class="col-sm-8">
				<input type="text" name="cusCha" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="cusCha" value="${cusCha}"/> "/> 
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户地区：</label>
				<div class="col-sm-8">
				<input type="text" name="cusLoc" class="form-control textInput" readOnly="readonly" value="${cusLoc}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户类型：</label>
				<div class="col-sm-8">
				<input type="text" name="cusType" class="form-control textInput" 
					readOnly="readonly" value="<ys:codemapConvert codemap="customerType" value="${cust.cusType}"/>"/>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">品类名称：</label>
				<div class="col-sm-8">
				<input type="text" name="cateName" class="form-control textInput" readOnly="readonly" value="${cateName}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">厂号：</label>
				<div class="col-sm-8">
				<input type="text" name="manuNum" class="form-control textInput" readOnly="readonly" value="${manuNum}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">产地：</label>
				<div class="col-sm-8">
			 	<input type="text" name="prodPla" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="prodPla" value="${prodPla }"/> "/> 
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">规格：</label>
				<div class="col-sm-8">
 				 <input type="text" name="cateStan" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="cateStan" value="${cateStan }"/> "/> 
  				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">包装数量：</label>
				<div class="col-sm-8">
				<input type="text" name="pcasPri" class="form-control textInput" readOnly="readonly" value="${cate.pkgQuan}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">报盘价格：</label>
				<div class="col-sm-8">
				<input type="text" name="txAmo" class="form-control textInput" readOnly="readonly"  value="${cate.offerPri}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<table class="table" width="80%" layoutH="146">
				<thead>
					<tr>
						<th>数量（吨）</th>
						<th>客户价（元/吨）</th>
						<th>国际部回盘价（元/吨）</th>
						<th>决委会回盘价（元/吨）</th>
						<th>时效</th>
						<th>回盘时间</th>
						<th>报价时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${sla}" var="item">
					   	<tr target="transactionInfoId" rel="${item.transactionInfoId}" ondblclick="modifyInfo('${item.txStatus}')"  >
							<td>${item.txAmo}</td>
							<td>${item.pcasPri}</td>
							<td>${item.uniCtofPri}</td>
							<td>${item.domCtofPri }</td>
							<td>${item.ctofAging}</td>
							<td>${item.ctofTime}</td>
							<td>${item.pcasTime}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>
</div>
