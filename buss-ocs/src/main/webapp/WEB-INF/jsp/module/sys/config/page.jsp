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
	function validForm(){
		$("#configPagerForm").submit();
	}
	
	function selectUser(id){
		var selectIds = $("#configVlue_"+id).val();
		var url = "module/sys-dept-select/user-select-checkbox-configVlue_"+id+"-configVlue2_"+id +"?value="+selectIds;
		$("#selectUser").removeAttr("href").attr("href",url).click();
	}
</script>


<form id="configPagerForm" role="form" action="service/config-save" method="post" onsubmit="return validateCallback(this,navTabAjaxDone)">
<div class="pageHeader">
<div class="searchBar container-fluid">
	<h3>
		<c:if test="${type eq 'orderLimitStrategy'}">
			订单时限策略
		</c:if>
		<c:if test="${type eq 'orderAlertPersionsStategy'}">
			订单通知人员策略
		</c:if>
		<c:if test="${type eq 'provisionsAlert'}">
			备付金预警值
		</c:if>
		<c:if test="${type eq 'provisionsAlertPersionsStategy'}">
			备付金通知策略
		</c:if>
		<c:if test="${type eq 'tradeLimit'}">
			交易时限
		</c:if>
	</h3>
	<br><br>
	<input type="hidden" name="type" value="${type}">
	<div layoutH="58">
	
		<c:if test="${type eq 'orderLimitStrategy' || type eq 'provisionsAlert'}">
	
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
		</c:if>
		
		<c:if test="${type eq 'tradeLimit'}">
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
		</c:if>
		
		<c:if test="${type eq 'orderAlertPersionsStategy' || type eq 'provisionsAlertPersionsStategy' }">
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
		</c:if>
		<br>
		<div class="col-xs-offset-10 col-xs-1" style="text-align: right;"><button class="btn btn-sm btn-primary" type="button" onclick="validForm();"><i class="icon-save"></i>  <span>保存</span></button></div>
	</div>
</div>
</div>
</form> 
