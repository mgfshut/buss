<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
	<form class="pageForm">
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="58">
		<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">用户名：</label>
				<div class="col-sm-4">
					<input type="text" name="userName" pattern="^[A-Za-z0-9]{6,15}$" data-error="请输入6-15位英文、数字" maxlength="15" placeholder="请输入用户名" class="form-control" required="required" readonly="readonly" value="${userName}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">姓名：</label>
				<div class="col-sm-4">
					<input type="text"  maxlength="15"  class="form-control"  readonly="readonly" value="${memberName}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">性别：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="sex" defaultText="未填写" defaultValue="" selectName="memberSex" value="${memberSex}" classes="form-control" disabled="disabled"></ys:codemapSelect2>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">职务：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="job" defaultText="未填写" defaultValue="" selectName="memberJob" value="${memberJob}" classes="form-control" disabled="disabled"></ys:codemapSelect2>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">所属机构：</label>
				<div class="col-sm-4 " >
					<input name="deptName" id="pname" value="${deptName }" class="form-control" required="required" readonly type="text"/>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">手机号：</label>
				<div class="col-sm-4">
					<input type="text" name="memberPhone" size="30" maxlength="11" readonly="readonly" 
						pattern="^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$" 
						data-error="请输入手机号码" placeholder="请输入手机号码" class="form-control" value="${memberPhone}"/>
				</div>	
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">Email：</label>
				<div class="col-sm-4">
					<input type="email" name="memberEmail" maxlength="128" readonly="readonly" 
						data-error="请输入Email" placeholder="请输入Email" class="form-control" class="email" value="${memberEmail}"/>
				</div>	
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">用户状态：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="status" defaultText="" defaultValue="" selectName="userStatus" 
						value="${userStatus }" classes="form-control" disabled="disabled"></ys:codemapSelect2>
				</div>	
			</div>
		</div>	
		</div>
		<div class="formBar">
			<ul>
				<li><a class="btn btn-sm btn-primary" href="module/sys-member-form1/member-${memberId}" target="navTab"  
					rel="usereditmanager" data-parent="usereditmanager" title="修改用户">修改</a></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
</form>