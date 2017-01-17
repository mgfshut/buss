<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script>
$(document).ready(function() {
	var filePath = "";
	$('#file-zmwj').fileinput({
		language: 'zh',
	    uploadUrl: '<%=request.getContextPath()%>/report/upload',
	    allowedFileExtensions : ['jpg', 'png','gif','bmp']
	});
	
	$('#file-zmwj').on("fileuploaded", function(event, data, previewId, index) {
		var json = data.response;
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			filePath = filePath+json.rel+",";
			$('#filePath').val(filePath);
		}else{
			alert("文件上传失败");
		}
		
	});
});
</script>
<div id="pagerForm" class="pageContent">
	<form method="post" data-delay="100" action="service/contractInfo-checkPay" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="contUlName" id="filePath">
		<input type="hidden" name="contractInfoId" value="${contractInfoId }">
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="68">
			<div class="form-horizontal">
				<div class="panel">
					<div class="panel-heading">客户信息</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-xs-1 control-label">客户名称：</label>
								<div class="col-xs-2">
									<input type="text" name="cusName" readOnly="readonly" 
										class="form-control" value="${cusName}" />
							</div>
							<label class="col-xs-1 control-label">法人代表：</label>
								<div class="col-xs-2">
									<input type="text" name="legalPer" readOnly="readonly"
										class="form-control" value="${legalPer}" />
							</div>
							<label class="col-xs-1 control-label">企业信用代码：</label>
								<div class="col-xs-2">
									<input type="text" name="creditCode" readOnly="readonly"
										class="form-control" value="${creditCode}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-1 control-label">联系方式：</label>
								<div class="col-xs-2">
									<input type="text" name="entTel" readOnly="readonly"
										class="form-control" value="${entTel}" />
								<div class="help-block with-errors"></div>
							</div>
							<label class="col-xs-1 control-label">客户地址：</label>
								<div class="col-xs-2">
									<input type="text" name="entAddr" readOnly="readonly"
										class="form-control" value="${entAddr}" />
								<div class="help-block with-errors"></div>
							</div>
						</div>
					</div>
				
				</div>
			<div class="form-horizontal" align="center">
				<div class="form-group" align="center">
					<div class="col-xs-12" align="center">
						<input id="file-zmwj" type="file" name="file">
					</div>
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