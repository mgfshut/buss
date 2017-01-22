<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
$(document).ready(function() {
	var filePath = "";
	$('#file-zmwj').fileinput({
		language: 'zh',
	    uploadUrl: '<%=request.getContextPath()%>/interface/uploads',
	    allowedFileExtensions : ['jpg', 'png','gif','bmp']
	});
	
	/* $('#file-zmwj').on("fileuploaded", function(event, data, previewId, index) {
		var json = data.response;
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){ 
			filePath = filePath+json.resObject.catePic+",";
			$('#filePath').val(filePath);
		}else{
			alert("文件上传失败");
		}
		
	}); */
	$('#file-zmwj', navTab.getCurrentPanel()).on("fileuploaded", function(event, data, previewId, index) {
		var json = data.response;
		if (json.code == DWZ.statusCode.ok){
			filePath = json.resObject;
			$('#filePath', navTab.getCurrentPanel()).val(filePath);
		}else{
			alert("文件上传失败");
		}
	});
});

function categoryItemSaveDone(json){
	if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
		alertMsg.correct(json[DWZ.keys.message]);
		setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
	}else{
		alertMsg.error(json[DWZ.keys.message]);
	}
}
</script>
<div id="addCategoryForm" class="pageContent">
	<form method="post" action="service/category-save" class="form-horizontal pageForm " onsubmit="return validateCallback(this, categoryItemSaveDone)">
		<%-- <input type="hidden" name="code" value="${param.code ne null? param.code:code}" />
		<input type="hidden" name="codeValueId" value="${codeValueId}" /> --%>
		<div class="pageFormContent container-fluid" layoutH="68">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">品类名称：</label>
				<div class="col-sm-8">
				<input type="text" name="cateName" data-error="请输入1-20位字符" placeholder="请输入品类名称"  maxlength="20" class="form-control textInput" required="required" value="${cateName}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">厂号：</label>
				<div class="col-sm-8">
				<input type="text" name="manuNum" data-error="请输入1-10位字符" placeholder="请输入厂号" maxlength="10" class="form-control textInput" required="required" value="${manuNum}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">产地：</label>
				<div class="col-sm-8">
				<ys:codemapSelect2 codemap="prodPla" required="required" selectName="prodPla" value="${prodPla}" defaultText="请选择" classes="form-control"></ys:codemapSelect2>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">规格：</label>
				<div class="col-sm-8">
				<ys:codemapSelect2 codemap="cateStan" required="required" selectName="cateStan" value="${cateStan}" defaultText="请选择" classes="form-control"></ys:codemapSelect2>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">备注：</label>
				<div class="col-sm-8">
				<input type="text" name="comm" data-error="请输入1-256位字符" placeholder="请输入备注" maxlength="256" class="form-control textInput" required="required" value="${comm}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">供应商：</label>
				<div class="col-sm-8">
				<input type="text" name="cateSup" data-error="请输入1-32位字符" placeholder="请输入品类供应商" maxlength="32" class="form-control textInput" required="required" value="${cateSup}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">货币币种：</label>
				<div class="col-sm-8">
				<ys:codemapSelect2 codemap="currency" required="required" selectName="currency" value="${currency}" classes="form-control"></ys:codemapSelect2>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">计量单位：</label>
				<div class="col-sm-8">
				<ys:codemapSelect2 codemap="weight" required="required" selectName="unit" value="${unit}" classes="form-control"></ys:codemapSelect2>
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">报盘价：</label>
				<div class="col-sm-8">
				<input type="text" name="offerPri" data-error="请输入1-10位字符" placeholder="请输入报盘价" maxlength="10" class="form-control textInput" required="required" value="${offerPri}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">报盘时效：</label>
				<div class="col-sm-8">
				<input type="text" name="offerAging" data-error="请输入24的倍数位" placeholder="请输入报盘时效，如若大于24小时则必须是24的整数倍。" maxlength="10" class="form-control textInput" required="required" value="${offerAging}">
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<input  type="hidden" id="filePath" name="catePic"/>
			<div class="form-horizontal" align="center">
				<div class="form-group" align="center">
					<div class="col-xs-12" align="center">
						<input id="file-zmwj" type="file" name="files">
					</div>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary disabled" type="submit" style="pointer-events: all; cursor: pointer;">提交</button></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>
</div>