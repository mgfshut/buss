<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link href="<%=request.getContextPath()%>/css/fileinput/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/fileinput/fileinput.min.js"></script>
<script src="<%=request.getContextPath()%>/js/fileinput/locales/zh.js"></script>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script>
var timeout = 10;
var autoInterval = -1;
$(document).ready(function(){
	$('#file-zmwj').fileinput({
		language: 'zh',
	    showUpload: false,
	    showPreview: false,
	    uploadAsync:false
	});
	
	$('#file-zmwj').on("fileuploaded", function(event, data, previewId, index) {
		
	});
});

function updCallback(json){
	try{
		DWZ.ajaxDone(json);
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			if (json.navTabId){
				navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
			} else {
				var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
				var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {}
				navTabPageBreak(args, json.rel);
			}
		}
	}catch(e){
		alert(e);
	}
	
}

function changeType(devType){
	if (devType){
		$.post("<%=request.getContextPath()%>/service/upgrade-getCurVer-"+devType,function(json){
			try{
				if (json){
					$("input[name='versionCode']").val(json.versionCode);
				}
			}catch(e){
				
			}
		});
	}
}

</script>
<form id="userEditForm" method="post" action="<%=request.getContextPath()%>/upgrade/uploadNewVer" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, updCallback)">
<div id="selectedModulesHiddenFileds" ></div>
<div class="pageFormContent  container-fluid" layoutH="58">
	<div class="form-horizontal">
		<div class="form-group form-group-sm">
			<label class="col-sm-2 control-label">程序类型：</label>
			<div class="col-sm-4">
				<select class="form-control" name="devType" onchange="changeType(this.value)">
					<option value="" selected="selected">请选择</option>
					<option value="android">Android</option>
					<option value="ios">ios</option>
				</select>
			</div>	
		</div>
		<div class="form-group form-group-sm">
			<label class="col-sm-2 control-label">更新版本号：</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" value="" placeholder="1.0" name="versionCode">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">更新文件：</label>
			<div class="col-sm-4">
				<input id="file-zmwj" type="file" name="file">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">更新说明：</label>
			<div class="col-sm-4">
				<textarea rows="5" cols="30" class="form-control" name="content"></textarea>
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