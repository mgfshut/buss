<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>

<script type="text/javascript">
$(function(){
	
	console.info("%o-=-","${moduleId}");
	
});
</script>

<div id="pageCodeMapForm" class="pageContent" >	
	<form method="post" action="service/module-save" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent container-fluid" layoutH="58" >
			<div class="form-horizontal">
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">菜单编号：</label>
					<div class="col-sm-8">
						<input type="text" name="moduleNo"  value="" placeholder="请输入菜单编号" size="30" maxlength="32" class="form-control" required="required" />
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">菜单名称：</label>
					<div class="col-sm-8">
						<input type="text" name="moduleName"  value="" placeholder="请输入菜单名称" size="30" maxlength="32" class="form-control" required="required" />
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">菜单序号：</label>
					<div class="col-sm-8">
						<input type="text" name="menuOrder"  value="" placeholder="请输入菜单序号" pattern="\d+$" data-error="请输入非负整数" size="30" maxlength="2" class="form-control" required="required" />
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">授权名称：</label>
					<div class="col-sm-8">
						<input type="text" name="authName"  value="" placeholder="请输入菜单优先级" size="30" maxlength="32" class="form-control" required="required" />
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">上级菜单：</label>
					<div class="col-sm-8">
						<input type="text"  value="${moduleName}" readonly="readonly" size="100" maxlength="100" class="form-control" required="required" />
						<input type="hidden" name="parentId" value="${moduleId}" />		
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">URL地址：</label>
					<div class="col-sm-8">
						<input type="text" name="menuUrl"  value="" placeholder="请输入URL地址" size="100" maxlength="100" class="form-control" required="required" />
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">菜单描述：</label>
					<div class="col-sm-8">
						<input type="text" name="moduleDescribe"  value="" placeholder="请输入菜单描述" size="30" maxlength="32" class="form-control"  />
						<div class="help-block with-errors"></div>
					</div>
				</div>			
			</div>	
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary" type="submit">提交</button></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>
</div>