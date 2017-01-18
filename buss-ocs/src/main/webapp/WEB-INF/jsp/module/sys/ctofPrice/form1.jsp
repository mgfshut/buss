<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script type="text/javascript">
var deptname="${dept.deptname}";
var type="${type}";
$(function(){
    // 当目前为修改操作时候  选择跳转的相应页面 
	if(type!=""&&type=="0"){
			$("#chooseLink").attr("href","module/sys-dept-select/dept-select-id:deptname");
			$("#chooseLink").attr("width",600);
		    $("#chooseLink").attr("height",450);
	}if(type!=""&&type=="1"){ 
			$("#chooseLink").attr("href","module/sys-user-productIndex/merchant-pager-1");
		    $("#chooseLink").attr("width",600);
		    $("#chooseLink").attr("height",450);
	}
	

	//提交按钮执行的方法 
	$("#userSubmitButton").click(function(){
		    var deptPid = $("#deptPid").val();
			if(deptPid != ""){
				$("#userEditForm").submit();
			}else{
				alertMsg.error("请选择机构!");
			}
	});

	
});
</script>
<div id="userMapForm" class="pageContent">
	<form id="userEditForm" method="post" action="service/member-save" class="pageForm required-validate" 
		onsubmit="return validateCallback(this,navTabAjaxDone)">
		<input type="hidden" name="memberId" value="${memberId}" />
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="58">
		<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">品	名：</label>
				<div class="col-sm-4">
					<input type="text" name="userName" pattern="^[A-Za-z0-9]{6,32}$" 
						data-error="请输入6-32位英文、数字" maxlength="32" placeholder="请输入用户名" 
						class="form-control" required="required" value="${userName}" readonly="readonly" />
				<div class="help-block with-errors"></div>
				</div>
			</div>

				
		</div>	
		</div>
			<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary" type="button" id="userSubmitButton">提交</button></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>