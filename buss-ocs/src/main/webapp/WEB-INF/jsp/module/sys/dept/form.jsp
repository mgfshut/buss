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
				<input type="text" name="num" pattern="^[A-Za-z0-9]{2,10}$" data-error="请输入2-10位英文、数字" maxlength="10" placeholder="请输入机构编码" class="form-control" required="required" value="${num}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">机构名称：</label>
				<div class="col-sm-4">
				<input type="text" name="deptname" pattern="^([\u4e00-\u9fa5]+|([a-zA]+\s?)+){2,32}$" data-error="请输入2-32位中,英文" placeholder="请输入机构名称" maxlength="32" class="form-control" required="required"  value="${deptname}"/>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">上级机构：</label>
				<div class="col-sm-4">
					<div class="input-group">
				 		<input name="parent.deptId" id="pid" value="${parent.deptIid }" type="hidden"/>
						<input name="parent.deptname" id="pname" data-error="请选择上级机构"  value="${parent.deptname }" class="form-control" type="text" readonly="readonly" placeholder="请选择上级机构"/>
					  	<span class="input-group-addon">
					  		<a href="module/sys-dept-select/dept-select-id:deptname" lookupGroup="parent" width="400" height="500" title="选择"><i class="icon-search"></i> 选择</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a title="清除" href="#" onclick="$('#pid').val('');$('#pname').val('')" ><i class="icon-trash"></i> 清除</a>
					  	</span>
					</div>
					<div class="help-block with-errors"></div>
				</div>
			</div>
			
			<c:if test="${id ne null}">
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">创建时间：</label>
					<div class="col-sm-4">
					<input type="text" name="createtime" class="form-control"  value="${createtime }" readonly="readonly">
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