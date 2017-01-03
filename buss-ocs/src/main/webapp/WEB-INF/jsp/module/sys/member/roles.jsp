<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags" %> 
<script type="text/javascript">
	$(function(){
		var ids = '${userRoles}';
		var ar = ids.split(",");
		if(ids != ""){
			$("#nowNum").html(ar.length);
		}
		for(var i = 0 ; i < ar.length; i++){
			$("#role_id_" + ar[i]).attr("checked",true);
			$("#role_name_" + ar[i]).css("color","blue");
		} 
		
		/* $("#selectRoleButton").bind("click",function(){
			var flag = false;
			$("[name='roleIds']:checkbox").each(function(){
				if($(this).is(":checked")){
					flag = true;
				}
			});
			
			if(flag){
				$("#selectRoleForm").submit();
			}else{
				alertMsg.error("请选择角色!");
			}
			
		}); */
	});
	
</script>
<!-- 分页、搜索表单 -->	
<div class="pageHeader" style="border:1px #B8D0D6 solid">
<div class="searchBar">
<table>
	<tr>
		<td style=""><h1>为用户&nbsp;[<font style="color: red;font-weight: 900;">${user.username }</font>]&nbsp;分配角色</h1> &nbsp;&nbsp;
			
	 
		</td>
		<%-- <td align="right" style="padding:1px 2px; line-height:21px;">角色名称：</td>
		<td><input name="name" size="10" type="text" class="textInput" value="${param.name}"></td>
		<td>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >检索</button></div></div></li>
				</ul>
			</div>		
		</td>  --%>
	</tr>
	<tr>
		<td style="text-align: right">
		角色已分配数量：<span id="nowNum" style="color: blue;font-weight: 900">0</span>&nbsp;&nbsp; (<font color="blue">蓝色角色名称</font>)
		</td>
	</tr>
</table>
</div>
</div>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">


<form method="post" action="service/user-updateUserRoles" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
<input type="hidden" name="userId" id="userId" value="${user.uid }"/>
<table class="table" width="100%" layoutH="120" >
	<thead>
		<tr>
			<th width="20"></th>
			<th width="50" orderField="roleId" class="${param.orderField eq 'roleId'?param.orderDirection:''}">角色ID</th>
			<th width="100" orderField="roleName" class="${param.orderField eq 'roleName'?param.orderDirection:''}">角色名称</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${allRoles}" var="item">
			<tr>
				<td><label><input id="role_id_${item.roleId }" type="checkbox" name="roleIds" value="${item.roleId }" /></label></td>
				<td>${item.roleId}</td>
				<td id="role_name_${item.roleId }">${item.roleName }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="formBar">
	<ul>
		<li><button class="btn btn-sm btn-primary" type="submit" id="selectRoleButton">提交</button></li>
		<li><button class="btn btn-sm btn-warning " data-dismiss="modal" type="button">取消</button></li>
	</ul>
</div>
</form>
</div>