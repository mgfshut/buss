<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys"%>
<script type="text/javascript">
	function getVlue(v){
		$.pdialog.closeCurrent();
	}
</script>
<div id="deptMapForm" class="pageContent">
	<form method="post" data-delay="100"  action="service/dept-save" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div id="selectedModulesHiddenFileds" ></div>
		<input type="hidden" name="deptId" value="${deptId}" />
		
		<div class="pageFormContent container-fluid" layoutH="58">
			<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">机构编号：</label>
				<div class="col-sm-4">
				<input type="text" name="deptCode" pattern="^[A-Za-z0-9]{2,10}$" data-error="请输入2-10位英文、数字" maxlength="10" placeholder="请输入机构编码" class="form-control" required="required" value="${deptCode}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">机构名称：</label>
				<div class="col-sm-4">
				<input type="text" name="deptName" pattern="^([\u4e00-\u9fa5]+|([a-zA]+\s?)+){2,32}$" data-error="请输入2-32位中,英文" placeholder="请输入机构名称" maxlength="32" class="form-control" required="required"  value="${deptName}"/>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">机构类型：</label>
				<div class="col-sm-4">
					<ys:codemapSelect2 codemap="deptType" selectName="deptType" value="${deptType }" 
						classes="form-control"></ys:codemapSelect2>
				</div>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">上级机构：</label>
				<div class="col-sm-4">
					<div class="input-group">
				 		<input name="parentDept" id="parentDept" value="${parentDept }" type="hidden"/>
						<input name="parentDeptName" id="parentDeptName" data-error="请选择上级机构"  value="${parentDeptName }" class="form-control" type="text" readonly="readonly" placeholder="请选择上级机构"/>
					  	<span class="input-group-addon">
					  		<a href="module/sys-dept-select/dept-select-parentDept:parentDeptName" lookupGroup="parent" width="400" height="500" title="选择"><i class="icon-search"></i> 选择</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a title="清除" href="#" onclick="$('#parentDept').val('');$('#parentDeptName').val('')" ><i class="icon-trash"></i> 清除</a>
					  	</span>
					</div>
					<div class="help-block with-errors"></div>
				</div>
			</div>
			
			<c:if test="${deptId ne null}">
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">创建时间：</label>
					<div class="col-sm-4">
					<input type="text" name="createTime" class="form-control"  value="${createTime }" readonly="readonly">
					</div>
				</div>
			</c:if>
			
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