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
	function validForm2(){
		var type = 'orderLimitStrategy'
		$("#configPagerForm2").submit();
	}
	
	function selectUser(id){
		var selectIds = $("#configVlue_"+id).val();
		var url = "module/sys-dept-select/user-select-checkbox-configVlue_"+id+"-configVlue2_"+id +"?value="+selectIds;
		$("#selectUser").removeAttr("href").attr("href",url).click();
	}
</script>


<form id="configPagerForm2" role="form" action="service/config-save" method="post" onsubmit="return validateCallback(this,navTabAjaxDone)">
<div class="pageHeader">
<div class="searchBar container-fluid">
	<h3>
		订单时限策略	
	</h3>
	<br><br>
	<input type="hidden" name="type" value="orderLimitStrategy">
	<div layoutH="58">
	
	
	
			<c:forEach items="${configs}" var="con">
				<div class="form-inline" >
				<div class="form-group col-xs-4" >	
					<label class="col-sm-4 control-label">原${con.etext}：</label>
					<div class="col-sm-6">
					<input name="configIds" type="hidden" value="${con.configId }"/>					
					<input id="oldvalues" name="oldvalues" type="text" class="form-control input-sm" value="<fmt:formatNumber value="${con.codevalue }" pattern="##,###,##0.##"/>" readonly="readonly">
					</div>
				</div>	
				<div class="form-group form-group-sm">		
					<label class="col-sm-4 control-label">新${con.etext}：</label>
					<div class="col-sm-6">
					<input id="configVlues" name="configVlues" placeholder="请输入正整数" maxlength="9" type="text" pattern="^[0-9]{1,9}" data-error="请输入1~9位正整数" class="form-control input-sm" required="required" value="">
					<div class="help-block with-errors"></div>
					</div>
				</div>		
			</div>
				
			</c:forEach>

		
	
		<br>
		<div class="col-xs-offset-10 col-xs-1" style="text-align: right;"><button class="btn btn-sm btn-primary" type="button" onclick="validForm2();"><i class="icon-save"></i>  <span>保存</span></button></div>
	</div>
</div>
</div>
</form> 
