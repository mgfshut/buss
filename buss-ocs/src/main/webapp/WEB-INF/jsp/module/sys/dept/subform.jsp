<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="deptMapForm" class="pageContent">
	<form method="post" action="service/dept-save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<p>
				<label>机构编号：</label>
				<input type="text" name="num" size="30" />
			</p>
			<p>
				<label>机构名称：</label>
				<input type="text" name="deptname" size="30" minlength="2" maxlength="32" class="required"/>
			</p>
			<p>
				<label>上级机构：</label>
				<input type="text" name="pname" size="30" value="${deptname }" disabled="disabled"/>
				<input type="hidden" name="parent.deptId" value="${deptId }">
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>

</div>