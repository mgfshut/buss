<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script>
function imgUploadifySuccess(file, data, response){
	var json = jQuery.parseJSON(data);
	
}
</script>
<div id="userMapForm" class="pageContent">
	<form id="userEditForm" method="post" action="service/test-save" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="codemapId" value="${codemapId}" />
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="58">
		<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">名称：</label>
				<div class="col-sm-4">
					<input type="text" name="codemapname" pattern="^([\u4e00-\u9fa5]{2,15}$" data-error="请输入2-15位汉字" maxlength="32" placeholder="请输入名称" class="form-control" required="required" value="${codemapname}" />
				<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">代码：</label>
				<div class="col-sm-4">
					<input type="text" name="code" maxlength="15" placeholder="请输入代码" class="form-control" value="${code}" />
					<div class="help-block with-errors"></div>
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">文件上传：</label>
				<div class="col-sm-4">
					<%-- 上传图片队列展示div --%>
					<div id="fileQueue" class="fileQueue"></div> 
					 <input id="testFileInputppp" type="file" name="file"<%-- 如果使用公用的上传方法，图片上传名必须为file。如果需要自定义，参考CommonController --%> 
						uploaderOption="{
							swf:'<%=request.getContextPath()%>/js/uploadify/scripts/uploadify.swf',
							uploader:'/file/upload;JSESSIONID=<%=session.getId()%>',<%-- 图片上传处理方法，jsessionid必填 --%>
							formData:{folder:'zjact',callbackid:'autoCallback'},<%-- 图片上传参数区域。folder必填，指明上传存放文件夹。callbackid上传成功后，图片路径存储对象ID --%>
							buttonText:'自动上传图片',
							method:'post',
					        fileTypeExts:'*.jpg;*.png;', 
					        fileSizeLimit:'102400KB',
					        queueID: 'fileQueue',<%-- 图片队列ID --%>
					        onUploadError:onUploadErrorFun,<%-- 图片上传失败，公共处理方法，必填。方法在lib\common.js文件中，可以自定义 --%>
					        onUploadSuccess:uploadifySuccessReloadPrview<%-- 图片上传成功，公共处理方法，必填。方法在lib\common.js文件中，可以自定义 --%>
						}"
					 />
					 <input type="hidden" name="img" id="autoCallback">		
					 <img id="previewImg" src="" border="0" width="120" height="80" style="display:none;cursor: hand">
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">文件上传：</label>
				<div class="col-sm-4">
					<%-- 上传图片队列展示div --%>
					<input type="hidden" id="detailImagesHidden">
					<div id="detailImagesQueue" class="fileQueue"></div>
					<input id="detailImages" type="file" name="file"<%-- 如果使用公用的上传方法，图片上传名必须为file。如果需要自定义，参考CommonController --%> 
						uploaderOption="{
							swf:'<%=request.getContextPath()%>/js/uploadify/scripts/uploadify.swf',
							uploader:'/file/upload;JSESSIONID=<%=session.getId()%>',<%-- 文件上传处理方法，jsessionid必填 --%>
							formData:{folder:'zjact',callbackid:'autoCallback'},<%-- 图片上传参数区域。folder必填，指明上传存放文件夹。callbackid上传成功后，图片路径存储对象ID --%>
							buttonText:'上传照片',
							method:'post',
					        fileTypeExts:'*.jpg;*.png;', 
					        fileSizeLimit:'102400KB',
					        queueID: 'detailImagesQueue',<%-- 队列ID --%>
					        auto:false,
					        multi:true,
					        onUploadError:onUploadErrorFun,<%-- 上传失败，公共处理方法，必填。方法在lib\common.js文件中，可以自定义 --%>
					        onUploadSuccess:imgUploadifySuccess<%-- 上传成功，公共处理方法，必填。方法在lib\common.js文件中，可以自定义 --%>
						}"
					 />
					 <div id="detailImgDiv"></div>
				</div>
			</div>
		</div>	
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-success" type="button" onclick="$('#detailImages').uploadify('upload','*');">上传详情图片</button></li>
				<li><button class="btn btn-sm btn-primary" type="submit">提交</button></li>
				<li><button class="btn btn-sm btn-warning closedialog" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>