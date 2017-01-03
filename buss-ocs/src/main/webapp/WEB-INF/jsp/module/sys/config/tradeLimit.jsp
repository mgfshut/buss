<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script type="text/javascript">
	function getValues(ids,names,id,name){
		$("#" + id).val(ids);
		$("#" + name).html(names);
	}
	function validForm5(){
		var type = 'tradeLimit';
		if(type == "tradeLimit"){
			var obj = document.getElementsByName("configVlues");
			if(obj != null){
				var startTime = obj[0].value;
				var endTime = obj[1].value;
				var st = startTime.replace(/:/g,"") - 0;
				var et = endTime.replace(/:/g,"") - 0;
				if(st > et){
					alertMsg.error("开始时间不能大于结束时间!");
					return false;
				}
			}
		}
		$("#configPagerForm5").submit();
	}
	
	function selectUser(id){
		var selectIds = $("#configVlue_"+id).val();
		var url = "module/sys-dept-select/user-select-checkbox-configVlue_"+id+"-configVlue2_"+id +"?value="+selectIds;
		$("#selectUser").removeAttr("href").attr("href",url).click();
	}
</script>


<form id="configPagerForm5" role="form" action="service/config-save" method="post" onsubmit="return validateCallback(this,navTabAjaxDone)">
<div class="pageHeader">
<div class="searchBar container-fluid">
	<h3>
		
			交易时限

	</h3>
	<br><br>
	<input type="hidden" name="type" value="tradeLimit">
	<div layoutH="58">
	
	<div class="form-inline" >
				<c:forEach items="${configs}" var="con">
					<div class="form-group form-group-sm">		
						<label class="col-sm-4 control-label">${con.etext}：</label>
						<div class="col-sm-6">
							<input name="configIds" type="hidden" value="${con.configId }"/>
							<input id="cv_${con.configId }" name="configVlues" pattern="^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$" data-error="请输入时分秒" type="text" class="form-control input-sm" value="${con.codevalue }" required="required" />
							<div class="help-block with-errors"></div>
						</div>
					</div>	
				</c:forEach>
			</div>

		
		
		<br>
		<div class="col-xs-offset-10 col-xs-1" style="text-align: right;"><button class="btn btn-sm btn-primary" type="button" onclick="validForm5();"><i class="icon-save"></i>  <span>保存</span></button></div>
	</div>
</div>
</div>
</form> 
