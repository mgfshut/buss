<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script>
function imgUploadifySuccess(file, data, response){
	var json = jQuery.parseJSON(data);
	$('#detailImagesHidden').val("C:/work/决策委员会用表.xls");
	
}

$(document).ready(function() {
	$('#file-zmwj').fileinput({
		language: 'zh',
	    uploadUrl: '<%=request.getContextPath()%>/report/upload',
	    //传递值内容至后台
	    uploadExtraData:{reportId:'${reportId}'},
	    allowedFileExtensions : ['xls']
	});
	
	$('#file-zmwj').on("fileuploaded", function(event, data, previewId, index) {
		var json = data.response;
		console.log(json);
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			var filePath = json.rel;
			$('#filePath').val(filePath);
		}else{
			alert("文件上传失败");
		}
		
	});
});
</script>
<div id="pagerForm" class="pageContent">
	<form method="post" data-delay="100" action="service/category-excelImport" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="filePath" id="filePath">
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="68">
		<div class="form-horizontal" align="center">
			<div class="form-group" align="center">
				<div class="col-xs-12" align="center">
					<input id="file-zmwj" type="file" name="file">
				</div>
			</div>
		</div>	
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary" type="submit">提交</button></li>
				<li><button class="btn btn-sm btn-warning closedialog" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>