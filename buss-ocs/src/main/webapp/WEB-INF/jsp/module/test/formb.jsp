<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script type="text/javascript">
function submitData(){
	;
	$.ajax({
		type: 'POST',
		url:$form.attr("action"),
		data:$("#userEditForm").serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
}
</script>
<div id="userMapForm" class="pageContent">
	<form id="userEditForm" method="post" action="service/testcol-save" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="codemapId" value="${codea.codemapId}" />
		<input type="hidden" name="codemaId" value="${codeb.codemaId}" />
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="58">
		<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">名称：</label>
				<div class="col-sm-4">
					<input type="text" name="codemapname" pattern="^([\u4e00-\u9fa5]{2,15}$" data-error="请输入2-15位汉字" maxlength="32" placeholder="请输入名称" class="form-control" required="required" value="${codea.codemapname}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">代码：</label>
				<div class="col-sm-4">
					<input type="text" name="code" maxlength="15" placeholder="请输入代码" class="form-control" value="${codea.code}" />
					<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">用户名：</label>
				<div class="col-sm-4">
					<input type="text" name="user" maxlength="15" placeholder="请输入代码" class="form-control" value="${codea.code}" />
					<div class="help-block with-errors"></div>
				</div>
			</div>
		</div>	
		<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">名称B：</label>
				<div class="col-sm-4">
					<input type="text" name="codemapnameb" pattern="^([\u4e00-\u9fa5]{2,15}$" data-error="请输入2-15位汉字" maxlength="32" placeholder="请输入名称" class="form-control" required="required" value="${codeb.codemapnameb}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">代码B：</label>
				<div class="col-sm-4">
					<input type="text" name="codeb" maxlength="15" placeholder="请输入代码" class="form-control" value="${codeb.codeb}" />
					<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">用户名B：</label>
				<div class="col-sm-4">
					<input type="text" name="user" maxlength="15" placeholder="请输入代码" class="form-control" value="${codea.code}" />
					<div class="help-block with-errors"></div>
				</div>
			</div>
		</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary" type="submit">提交</button></li>
				<li><button class="btn btn-sm btn-warning closedialog" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>