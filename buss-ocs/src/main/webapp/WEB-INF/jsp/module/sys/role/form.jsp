<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
$(function(){
	
	 $("#submitBtn_role").click(function(){
		/* if($("#role_check").find("input[type='checkbox']:checked")&&$("#role_check").find("input[type='checkbox']:checked").length>0) 
				$("#pageCodeMapForm form").submit();
		else
			alertMsg.error("请给角色分配权限!"); */
		 $("#pageCodeMapForm form").submit();
		
	});
	
});
</script>

<div id="pageCodeMapForm" class="pageContent">
	<form method="post" data-delay="100" action="service/role-save" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div id="selectedModulesHiddenFileds"></div>
		<input type="hidden" name="roleId" value="${roleId}" />
		<div class="container-fluid" layoutH="38">
			<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-3 control-label">角色代码：</label>
				<div class="col-sm-7">
				<input type="text" name="roleName" pattern="^.{2,32}$" data-error="请输入2-32位字符" placeholder="请输入角色代码" size="30" maxlength="32"
					class="form-control" required="required" value="${roleName}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-3 control-label">角色描述：</label>
				<div class="col-sm-7">
					<input type="text" name="roleDescribe" pattern="^.{2,32}$" data-error="请输入2-32位字符" placeholder="请输入角色描述" size="30" maxlength="32" class="form-control" required="required" value="${roleDescribe}"/>
					<div class="help-block with-errors"></div>
				</div>
			</div>
			<%-- <div class="form-group form-group-sm">
				<label class="col-sm-3 control-label">权限选择：</label>
				<div class="col-sm-7" id="role_check">
					<c:import  charEncoding="UTF-8" url="/module/sys-module-tree/module-tree" />
				</div>
			</div> --%>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary"  type="button" id="submitBtn_role">提交</button></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>