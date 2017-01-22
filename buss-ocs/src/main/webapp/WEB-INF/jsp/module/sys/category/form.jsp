<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script>
$(document).ready(function() {
	$('#file-zmwj').fileinput({
		language: 'zh',
	    uploadUrl: '<%=request.getContextPath()%>/report/upload',
	    allowedFileExtensions : ['xls']
	});
	
	$('#file-zmwj').on("fileuploaded", function(event, data, previewId, index) {
		var json = data;
		console.log(json);
		if (json.response.statusCode == DWZ.statusCode.ok){
			var filePath = json.rel;
			alertMsg.correct("数据导入成功！");
			$('#filePath').val(filePath);
		}else{
			alertMsg.error(json.response.message);
		}
		
	});
});

function downloadTemplate(){
	window.open("<%=request.getContextPath()%>/template/template.xls");
}
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
				<div class="col-xs-12" align="left">
					<button class="btn btn-lg btn-info" type="button" onclick="downloadTemplate()">下载导入模板</button>
				</div>
			</div>
		</div>	
		</div>
		<div class="formBar">
			<ul>
				<!-- <li><button class="btn btn-sm btn-primary" type="submit">提交</button></li> -->
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>