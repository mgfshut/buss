<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
$(document).ready(function() {
	
	var txStatus = "${txStatus}";
	if (txStatus == "21"){
		alertMsg.error("已回盘记录不需要再次操作！");
		navTab.closeCurrentTab();
		return;
	}
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
<%-- ${cate }
${cust }
${ctofPri } 
${slaInfo}--%>
	<form method="post" action="service/transactionInfo-updateCategoryPrice" class="form-horizontal pageForm " onsubmit="return validateCallback(this, categoryItemSaveDone)">
		<input type="hidden" name="code" value="${param.code ne null? param.code:code}" />
		<input type="hidden" name="codeValueId" value="${codeValueId}" />
		<input type="hidden" name="transactionInfoId" value="${transactionInfoId}" />
		<input type="hidden" name="slaTransactionInfoId" value="${slaInfo.slaTransactionInfoId}" />
		<div class="pageFormContent container-fluid" layoutH="68">
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户名称：</label>
				<div class="col-sm-8">
				<input type="text" name="cusName" class="form-control textInput" readOnly="readonly" value="${cust.cusName}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户渠道：</label>
				<div class="col-sm-8">
				<input type="text" name="cusCha" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="cusCha" value="${cust.cusCha}"/> "/> 
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm" >
				<label class="col-sm-2 control-label">客户地区：</label>
				<div class="col-sm-8">
				<input type="text" name="cusLoc" class="form-control textInput" readOnly="readonly" value="${cust.cusLoc}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">品类名称：</label>
				<div class="col-sm-8">
				<input type="text" name="cateName" class="form-control textInput" readOnly="readonly" value="${cate.cateName}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">厂号：</label>
				<div class="col-sm-8">
				<input type="text" name="manuNum" class="form-control textInput" readOnly="readonly" value="${cate.manuNum}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">产地：</label>
				<div class="col-sm-8">
			 	<input type="text" name="prodPla" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="prodPla" value="${cate.prodPla }"/> "/> 
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">规格：</label>
				<div class="col-sm-8">
 				 <input type="text" name="cateStan" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="cateStan" value="${cate.cateStan }"/> "/> 
  				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">客户价：</label>
				<div class="col-sm-8">
				<input type="text" name="pcasPri" class="form-control textInput" readOnly="readonly" value="${slaInfo.pcasPri}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">交易数量：</label>
				<div class="col-sm-8">
				<input type="text" name="txAmo" class="form-control textInput" readOnly="readonly"  value="${txAmo}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">回盘价：</label>
				<div class="col-sm-8">
				<input type="text" name="uniCtofPri" data-error="请输入1-10位字符" placeholder="请输入回盘价" maxlength="10" class="form-control textInput"  value="${uniCtofPri}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">回盘时效：</label>
				<div class="col-sm-8">
				<input type="text" name="ctofAging" data-error="请输入24的倍数位" placeholder="请输入回盘时效" maxlength="10" class="form-control textInput"  value="${ctofAging}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary disabled" type="submit" style="pointer-events: all; cursor: pointer;">提交</button></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>
</div>
