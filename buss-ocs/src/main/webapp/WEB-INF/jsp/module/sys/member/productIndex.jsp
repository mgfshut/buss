<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>
<script type="text/javascript">
// 表格双击事件调用的方法
function gridDbclick(item){
	var id=$(item).attr("rel");
	var name=$(item).attr("mid");
	//将id和name属性值传递回去 
	$.bringBack({"id":id, "deptname":name});
	$.pdialog.closeCurrent();
}
</script>
<!-- 分页、搜索表单 -->
<form id="pagerForm" action="module/sys-user-productIndex/merchant-pager-1" method="post" onsubmit="return dialogSearch(this);">
	<input type="hidden" name="pageNum" value="${number+1}" /> 
	<input type="hidden" name="numPerPage" value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField eq null?'':param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection eq null?'asc':param.orderDirection}" />
	<div class="pageHeader">
		<div class="searchBar container-fluid">
			<div class="form-group form-group-sm">
				<div class="col-sm-2">
					<input type="text" name="fullName" value="${param.fullName}" placeholder="输入商户名称" maxlength="32" class="form-control" />
				</div>
				<%-- <div class="col-sm-2">
					<ys:codemapSelect codemap="merchantTrade" name="trade"
						value="${param.trade}" defaultText="请选择所属行业" defaultValue=""
						classes="form-control input-sm "></ys:codemapSelect>
				</div>
				<div class="col-sm-2">
					<ys:codemapSelect codemap="merchantCity" name="city" value="${param.city}" defaultText="请选择所属城市" classes="form-control input-sm "></ys:codemapSelect>					
				</div>
				<div class="col-sm-2">
					<ys:codemapSelect codemap="merchantsStatus" name="status" value="${param.status}" defaultText="请选择商户状态" classes="form-control input-sm "></ys:codemapSelect>
				</div>
				<div class="col-sm-2">
					<input type="text" name="registerDate" value="${param.registerDate}" placeholder="请选择注册日期" data-provide="datepicker" maxlength="32" class="form-control" />				
				</div> --%>
				<div class="col-sm-1">
					<button class="btn btn-primary btn-sm" type="submit">
						<i class="icon-search"></i> <span>检索</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">
	<!-- 	<div class="panelBar">
			<div class="btn-group" style="margin: 4px 5px;">
				<a class="btn btn-primary btn-sm" href="module/sys-user-productForm"
					 target="navTab" rel="customerSaveDialog" data-parent="genreeditmanager"
					title="添加商户信息"> <i class="icon-plus"></i> <span>添加</span>
				</a> <a class="btn btn-danger btn-sm"
					href="service/merchant-remove-{merchantId}" target="ajaxTodo" data-parent="genreeditmanager"
					title="确定要删除吗?"> <i class="icon-minus"></i> <span>删除</span>
				</a> <a class="btn btn-success btn-sm"
					href="module/sys-user-productForm/merchant-{merchantId}" title="修改商户信息" target="navTab" data-parent="genreeditmanager"
					rel="customerUpdateDialog"> <i class="icon-pencil"></i> <span>修改</span>
				</a>
			</div>
		</div> -->

	<table class="table" width="100%" layoutH="140">
		<thead>
			<tr>
				<th>商户序号</th>
				<th orderField="fullName"
					class="${param.orderField eq 'fullName'?param.orderDirection:''}">商户名称</th>
				<th orderField="trade"
					class="${param.orderField eq 'trade'?param.orderDirection:''}">所属行业</th>
				<th orderField="businesstype"
					class="${param.orderField eq 'businesstype'?param.orderDirection:''}">业务类型</th>
				<th orderField="address"
					class="${param.orderField eq 'address'?param.orderDirection:''}">商户地址</th>
				<th orderField="linkman"
					class="${param.orderField eq 'linkman'?param.orderDirection:''}">联系人</th>
				<th orderField="user.realname"
					class="${param.orderField eq 'user.realname'?param.orderDirection:''}">操作人</th>
					<th orderField="createTime"
					class="${param.orderField eq 'createTime'?param.orderDirection:''}">创建时间</th>
				<th orderField="status"
					class="${param.orderField eq 'status'?param.orderDirection:''}">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${content}" var="item" varStatus="status">
				<c:if test="${item.category eq 1 }">
					<tr target="merchantId" ondblclick ="gridDbclick(this)" rel="${item.merchantId}" mid="${item.fullName}">
						<td >${ status.index + 1}</td>
						<td>
							<a href="module/bas-merchant-check/merchant-${item.merchantId}"
							 title="查看商户信息" target="navTab" rel="merchantDialog">						
								${item.fullName}
							</a>
						</td>
						<td >
							<ys:codemapConvert  codemap="merchantTrade" value="${item.trade}"></ys:codemapConvert>						
						</td>
						<td>	
							<ys:codemapConvert  codemap="businesstype" value="${item.businesstype}"></ys:codemapConvert>						
						</td>
						<td>${item.address}</td>
						<td>${item.linkman}</td>
						<td>${item.user.realname}</td>
						<td>${item.createTime}</td>
						<td>
							<ys:codemapConvert  codemap="merchantsStatus" value="${item.status}"></ys:codemapConvert>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>

	<sdf:dwzPage></sdf:dwzPage>
</div>