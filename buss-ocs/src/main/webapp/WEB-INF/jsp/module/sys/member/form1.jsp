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
				<label class="col-sm-2 control-label">用户名：</label>
				<div class="col-sm-4">
					<input type="text" name="userName" pattern="^[A-Za-z0-9]{6,32}$" 
						data-error="请输入6-32位英文、数字" maxlength="32" placeholder="请输入用户名" 
						class="form-control" required="required" value="${userName}" readonly="readonly" />
				<div class="help-block with-errors"></div>
				</div>
			</div>

				<%-- <div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">密码：</label>
					<div class="col-sm-4">
					<input type="password" name="password" pattern="^[A-Za-z0-9]{6,32}$" data-error="请输入6-32位英文、数字" maxlength="32" placeholder="请输入6-32位英文、数字密码" class="form-control" ${uid ne null ?'':'required' } value="${password}"/>
					<div class="help-block with-errors"></div>
					</div>
				</div>	 --%>
			
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">姓名：</label>
				<div class="col-sm-4">
					<input type="text" name="memberName" pattern="^([\u4e00-\u9fa5]+|([a-zA-Z]+\s?)+){2,15}$" 
						data-error="请输入2-15位英文、汉字" maxlength="15" placeholder="请输入用户姓名" 
						class="form-control" required="required" value="${memberName}" />
					<div class="help-block with-errors"></div>
				</div>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">职务：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="job" required="required" selectName="memberJob" value="${memberJob }" 
						classes="form-control"></ys:codemapSelect2>
				</div>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">状态：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="status" defaultValue="" selectName="userStatus" 
						value="${userStatus }" required="required" classes="form-control"></ys:codemapSelect2>
				</div>	
			</div>
<%-- 				<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">工号：</label>
				<div class="col-sm-4">
					<input type="text" name="workNumber" pattern="^([\u4e00-\u9fa5]+|([a-zA-Z0-9]+\s?)+){2,15}$" data-error="请输入2-15位英文、汉字" maxlength="15" placeholder="请输入工号" class="form-control" required="required" value="${workNumber}" />
					<div class="help-block with-errors"></div>
				</div>
			</div> --%>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">所属机构：</label>
				<div class="col-sm-4 " >
					<div class="input-group">
					<input name="deptId" id="deptPid" value="${deptId }"  type="hidden" data-error="请选择上级机构" required="required"/>
					<input name="deptName" id="deptPname" value="${deptName }" class="form-control" required="required" readonly="readonly" type="text"/>
					<span class="input-group-addon">
					<a id="chooseLink" href="module/sys-dept-select/dept-select-deptId:deptName" target="dialog" lookupGroup="dept" minable=false title="选择"><i class="icon-search"></i> 选择</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a title="清除" href="#" onclick="$('#deptPid').val('');$('#deptPname').val('');" ><i class="icon-trash"></i> 清除</a>
					</span>
					</div>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">性别：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="sex" selectName="memberSex" value="${memberSex }" 
						classes="form-control"></ys:codemapSelect2>
				</div>
			</div>
			
			
			
			<%-- <div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">年龄：</label>
				<div class="col-sm-4">
					<input type="text" name="age" size="30" maxlength="3" pattern="^([1-9][0-9]*){1,3}$" data-error="请输入1~3位的正整数"  placeholder="请输入年龄" class="form-control" value="${age}"/>
					<div class="help-block with-errors"></div>
				</div>	
			</div> --%>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">Phone：</label>
				<div class="col-sm-4">
					<input type="text" name="memberPhone" size="30" maxlength="11" 
						pattern="^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$" 
						data-error="请输入手机号码" placeholder="请输入手机号码" class="form-control" value="${memberPhone}"/>
					<div class="help-block with-errors"></div>
				</div>	
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">Email：</label>
				<div class="col-sm-4">
					<input type="email" name="memberEmail" maxlength="128" data-error="请输入Email" 
						placeholder="请输入Email" class="form-control" class="email" value="${memberEmail}"/>
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