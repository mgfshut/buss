<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>

<div id="fixOfferPriceForm" class="pageContent">
	<form method="post" action="service/category-save" class="form-horizontal pageForm required-validate" onsubmit="return validateCallback(this, categoryItemSaveDone)">
		<input type="hidden" name="code" value="${param.code ne null? param.code:code}" />
		<input type="hidden" name="codeValueId" value="${codeValueId}" />
		<div class="pageFormContent container-fluid" layoutH="68">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">品类名称：</label>
				<div class="col-sm-8">
				<input type="text" name="cateName" data-error="请输入1-20位字符" placeholder="请输入品类名称"  maxlength="20" class="form-control textInput" required="required" value="${cateName}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">厂号：</label>
				<div class="col-sm-8">
				<input type="text" name="manuNum" data-error="请输入1-10位字符" placeholder="请输入厂号" maxlength="10" class="form-control textInput" required="required" value="${manuNum}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">产地：</label>
				<div class="col-sm-8">
				<input type="text" name="prodPla" data-error="请输入1-10位字符" placeholder="请输入产地" maxlength="10" class="form-control textInput" required="required" value="${prodPla}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">规格：</label>
				<div class="col-sm-8">
				<input type="text" name="comm" data-error="请输入1-256位字符" placeholder="请输入规格" maxlength="256" class="form-control textInput" required="required" value="${comm}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">品类规格：</label>
				<div class="col-sm-8">
				<input type="text" name="cateStan" data-error="请输入1-10位字符" placeholder="请输入品类规格" maxlength="10" class="form-control textInput" required="required" value="${cateStan}">
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