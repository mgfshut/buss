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
	function validForm4(){
		var type = 'provisionsAlertPersionsStategy';
		$("#configPagerForm4").submit();
	}
	
	function selectUser(id){
		var selectIds = $("#configVlue_"+id).val();
		var url = "module/sys-dept-select/user-select-checkbox-configVlue_"+id+"-configVlue2_"+id +"?value="+selectIds;
		$("#selectUser").removeAttr("href").attr("href",url).click();
	}
</script>


<form id="configPagerForm4" role="form" action="service/config-save" method="post" onsubmit="return validateCallback(this,navTabAjaxDone)">
<div class="pageHeader">
<div class="searchBar container-fluid">
	<h3>
	
	
			备付金通知策略

	</h3>
	<br><br>
	<input type="hidden" name="type" value="provisionsAlertPersionsStategy">
	<div layoutH="58">
	
	
		
	
		
			<c:forEach items="${configs}" var="con">
				<div class="row col-xs-10">	
					<input name="configIds" type="hidden" value="${con.configId }"/>
					<input id="configVlue_${con.configId}" name="configVlues" type="hidden" value="${con.codevalue }"/>
					
					<label for="oldvalues" class="col-sm-6 control-label">${con.etext}</label>
					<div class="col-xs-6"><textarea id="configVlue2_${con.configId }" name="configVlue2s" rows="" class="form-control input-sm" cols="80" readonly="readonly" onclick="selectUser('${con.configId }');">${con.codevalue2 }</textarea></div>
					<label style="display: none">
						<a id="selectUser" href="#" lookupGroup="usersLook" width="400" height="500"><i class="icon-search"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label>
						<a href="#" onclick="selectUser('${con.configId }');"><i class="icon-search"></i> 选择</a>
					</label>
				</div>
			</c:forEach>	
	
		<br>
		<div class="col-xs-offset-10 col-xs-1" style="text-align: right;"><button class="btn btn-sm btn-primary" type="button" onclick="validForm4();"><i class="icon-save"></i>  <span>保存</span></button></div>
	</div>
</div>
</div>
</form> 
