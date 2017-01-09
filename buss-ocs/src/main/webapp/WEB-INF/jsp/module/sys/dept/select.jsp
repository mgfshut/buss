<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(function () {
	$("#checkButton").click(function () {
		var ids = "";
		var names = "";
		$('input[type="checkbox"][name="gname"]:checked').each(function (i, e) {
			var value = $(this).val();
			if(value != null && value != ""){
				var arry = value.split("|");
				ids += arry[0] + ",";
				names += arry[1] + ","; 
			}
		});
		
		if(ids == ""){
			alertMsg.info('请选择!');
		}else{
			ids = ids.substring(0, ids.length - 1);
			names = names.substring(0,names.length - 1);
			getValues(ids,names,'${deptId}','${deptName}','${type}');
			$.pdialog.closeCurrent();			
		}
	});
});

function selectNode(id,name,idvalue,namevalue,type){
	getValues(idvalue,namevalue,id,name,type);
	$.pdialog.closeCurrent();
}

function closeDialog(){
	$.pdialog.closeCurrent();
}
</script>    
<div class="pageContent">
	<form class="pageForm">
	<div class="pageFormContent" layoutH="58">
		${tree}
	</div>
	<div class="formBar">
		<ul>	
			<c:if test="${type eq 'checkbox'}">
				<li><button class="btn btn-sm btn-primary" type="button" id="checkButton">提交</button></li>
			</c:if>
			<li><button class="btn btn-sm btn-warning" type="button" onclick="closeDialog();">取消</button></li>
		</ul>
	</div>
	</form>
</div>