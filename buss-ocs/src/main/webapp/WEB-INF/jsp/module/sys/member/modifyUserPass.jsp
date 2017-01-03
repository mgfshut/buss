<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys"%>
<div id="pageCodeMapForm" class="pageContent">	
	<form method="post" data-delay="100" action="service/user-modifyUserPass" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div id="selectedModulesHiddenFileds"></div>
		<div class="pageFormContent container-fluid" layoutH="58">
			<div class="form-horizontal" style="margin-top: 30px;">	
				<div class="form-group form-group-sm">
					<label class="col-xs-1 control-label" style="width: 100px;">请输入旧密码：</label>
					<div class="col-xs-8" style="height: 38px;">
						<input type="password" size="30" class="form-control" name="password" required="required" pattern="^[A-Za-z0-9]{5,15}$" data-error="请输入5-15位英文、数字" placeholder="请输入旧密码"/>
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-xs-1 control-label" style="width: 100px;">请输入新密码：</label>
					<div class="col-xs-8" style="height: 38px;">
						<input type="password" size="30" id="newPassword" class="form-control"  name="newPassword" pattern="^[A-Za-z0-9]{5,15}$" data-error="请输入5-15位英文、数字" placeholder="请输入新密码"  required="required" maxlength="32"/>
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-xs-1 control-label" style="width: 100px;">请重复新密码：</label>
					<div class="col-xs-8" style="height: 38px;">
						<input type="password" size="30"  id="newPassword1" class="form-control"  name="newPassword1" pattern="^[A-Za-z0-9]{5,15}$" data-error="请输入5-15位英文、数字" required="required" placeholder="请重复新密码"  maxlength="32"/>
						<div class="help-block with-errors" id="erroInfo"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary" type="submit">提交</button></li>
			</ul>
		</div>
	</form>

</div>