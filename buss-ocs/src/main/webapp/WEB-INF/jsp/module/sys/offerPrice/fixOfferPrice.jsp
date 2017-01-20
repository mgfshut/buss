<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>

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
	<form method="post" action="service/category-updateCategoryPrice" class="form-horizontal pageForm " onsubmit="return validateCallback(this, categoryItemSaveDone)">
		<input type="hidden" name="code" value="${param.code ne null? param.code:code}" />
		<input type="hidden" name="codeValueId" value="${codeValueId}" />
		<div class="pageFormContent container-fluid" layoutH="68">
			<div class="form-group form-group-sm" style="display:none">
				<label class="col-sm-2 control-label">品类ID：</label>
				<div class="col-sm-8">
				<input type="text" name="categoryId" class="form-control textInput" readOnly="readonly" value="${categoryId}">
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
				<input type="text" name="prodPla" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="prodPla" value="${prodPla }"/>">
				<%-- <ys:codemapSelect2 codemap="prodPla" required="required" selectName="prodPla"  value="${prodPla}" classes="form-control"></ys:codemapSelect2> --%>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">规格：</label>
				<div class="col-sm-8">
				<input type="text" name="prodPla" class="form-control textInput" readOnly="readonly" value="<ys:codemapConvert codemap="cateStan" value="${cateStan }"/>">
				<%-- <ys:codemapSelect2 codemap="cateStan" required="required" selectName="cateStan" value="${cateStan}" classes="form-control"></ys:codemapSelect2> --%>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">备注：</label>
				<div class="col-sm-8">
				<input type="text" name="comm_" class="form-control textInput" readOnly="readonly" value="${comm}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">供应商：</label>
				<div class="col-sm-8">
				<input type="text" name="cateSup" class="form-control textInput"  required="required" value="${cateSup}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">货币币种：</label>
				<div class="col-sm-8">
				<ys:codemapSelect2 codemap="currency" required="required" selectName="currency" value="${currency}" classes="form-control"></ys:codemapSelect2>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">计量单位：</label>
				<div class="col-sm-8">
				<ys:codemapSelect2 codemap="weight" required="required" selectName="unit" value="${unit}" classes="form-control"></ys:codemapSelect2>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">报盘价：</label>
				<div class="col-sm-8">
				<input type="text" name="offerPri" data-error="请输入1-10位字符" placeholder="请输入报盘价" maxlength="10" class="form-control textInput" required="required" value="${offerPri}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">报盘时效：</label>
				<div class="col-sm-8">
				<input type="text" name="offerAging" data-error="请输入24的倍数位" placeholder="请输入报盘时效" maxlength="10" class="form-control textInput" required="required" value="${offerAging}">
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