/**
 * 删除动作方法。先判断是否有数据被选中，如果有确认删除动作
 * @param {} title
 * @param {} callback
 */
function deleteData(name, title, callback){
	var cIndex = 0;
	$(":checkbox[name='"+name+"']").each(function(){
		$checkbox = $(this);
		if ($checkbox.attr("checked")){
			cIndex++;
		}	
	});
	if (cIndex == 0){
		alertMsg.warn("请先选择需要操作的数据！");
		return;
	}
	
	alertMsg.confirm(title, {
		okCall: function(){
			callback.call();
		}
	});
}
/**
 * 当选中行时，自动勾选行的checkbox框（如果有）
 * @param {} tr
 */
function clickSelectedRow(tr){
	$(tr).children().each(function(){
		try{
			var content = $(this).children(0).children(0);
			if (content.attr("type") == "checkbox"){
				if (content.attr("checked")){
					content.attr("checked", false);
				}else{
					content.attr("checked", true);
				}
				return;
			}
		}catch(e){}
	});
}
/**
 * 上传文件，出错响应方法。出错后暂不弹出任何提示
 * @param file
 * @param errorCode
 * @param errorMsg
 * @param errorString
 */
function onUploadErrorFun(file, errorCode, errorMsg, errorString){

}
/**
 * 图片上传成功，公用回调方法
 * @param file
 * @param data
 * @param response
 */
function uploadifySuccess(file, data, response){
	var json = jQuery.parseJSON(data);
	try{
		if (json.statusCode == 200){
			alertMsg.correct("图片上传成功！");
			$("#"+json.rel).val(json.message);
		}else{
			alertMsg.error(json.message);
		}
	}catch(e){
		
	}
}

function importFileSuccess(file, data, response){
	var json = jQuery.parseJSON(data);
	try{
		if (json.statusCode == 200){
			alertMsg.correct("文件上传成功！");
		}else{
			alertMsg.error(json.message);
		}
	}catch(e){
		
	}
}

function deleteImageCallback(json){
	DWZ.ajaxDone(json);
	if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
		$("#previewDiv").get(0).style = "display:none";
		$("#autoCallback").val("");
	}
}

function uploadifySuccessReloadPrview(file, data, response){
	var json = jQuery.parseJSON(data);
	try{
		if (json.statusCode == 200){
			alertMsg.correct("图片上传成功！");
			$("#"+json.rel).val(json.message);
			try{
				$("#previewImg").get(0).src = "/file/image?filepath="+json.message;
				$("#previewImg").get(0).style.display = "block";
			}catch(e){
				
			}
		}else{
			alertMsg.error(json.message);
		}
	}catch(e){
		
	}
}