<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<script type="text/javascript">
	$(function(){
		$("#codemap-pager form").submit();
		$('#codemap-pager').on('click','.gridTbody tr',function(event){
			$("#codeitem-pager").loadUrl('sys/code/item/pager',{codemap:$('td.code',this).text(),orderField:'code'},function(){
				$('#codeitem-pager [layoutH]').layoutH();
			});
		});
	});
	function codeItemSaveDone(json){
		DWZ.ajaxDone(json);
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			var $pagerForm = $("#codeitem-pager #pagerForm");
			$pagerForm.submit();
			navTab.closeCurrentTab();
		}
	}
	function codeMapSaveDone(json){
		DWZ.ajaxDone(json);
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			var $pagerForm = $("#codemap-pager #pagerForm");
			$pagerForm.submit();
			navTab.closeCurrentTab();
		}
	}
	function removeCodeSuccess(json){
		var $pagerForm = $("#codemap-pager #pagerForm");
		$pagerForm.submit();
		removeCodeItemSuccess(json);
	}
	function removeCodeItemSuccess(json){
		var $pagerForm = $("#codeitem-pager #pagerForm");
		$pagerForm.submit();
	}
	
	function reflashCach(json){
		DWZ.ajaxDone(json);
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			alertMsg.correct(json[DWZ.keys.message]);
			var $pagerForm = $("#codemap-pager #pagerForm");
			$pagerForm.submit();
		}
	}
</script>
<style>
	#pageCodeManager .pageFormContent dl{width:230px;}
	#pageCodeManager .pageFormContent dt{width:60px;}
	#pageCodeManager .pageFormContent dd{width:100px;}
	#pageCodeManager .pageFormContent input{width:100%;}
	#pageCodeManager .panel .panelBar,
	#pageCodeManager .panel .grid{border-left:0;border-right:0;}
</style>
<div id="pageCodeManager">
<div style="width:500px;position: absolute;">
<div class="panel" minH="70">
	<h1>代码集管理</h1>
	<div id="codemap-pager">
		<jsp:include page="codemap-pager.jsp" />
	</div>
</div>
</div>
<div  style="margin-left:502px;">
<div class="panel" minH="100">
	<h1>代码项管理</h1>
	<div id="codeitem-pager">
	</div>
</div>
</div>

</div>