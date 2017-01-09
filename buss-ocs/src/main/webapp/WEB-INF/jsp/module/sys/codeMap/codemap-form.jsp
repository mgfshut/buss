<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>

<div id="pageCodeMapForm" class="pageContent">
	
	<form method="post" action="service/codeMap-save" class="form-horizontal pageForm required-validate" onsubmit="return validateCallback(this, codeMapSaveDone)">
		<input type="hidden" name="codeMapId" value="${codeMapId}" />
		<div class="pageFormContent container-fluid" layoutH="68">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">代码：</label>
				<div class="col-sm-8">
				<input type="text" name="code" ${not empty codeMapId ? "readonly":""} data-error="请输入1-32位字符" placeholder="请输入代码"  maxlength="32" class="form-control textInput" required="required" value="${code}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">名称：</label>
				<div class="col-sm-8">
				<input type="text" name="codeMapName" data-error="请输入1-32位字符" placeholder="请输入名称" maxlength="32" class="form-control textInput" required="required" value="${codeMapName}">
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