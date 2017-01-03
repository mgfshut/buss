<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>

<!-- 分页、搜索表单 -->
<form id="pagerForm" action="module/sys-coverWarmValue-index/strategy-update"
	method="post" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="strategyId" value="${strategyId}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="pageNum" value="${number+1}" /> <input
		type="hidden" name="numPerPage"
		value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" />
	<input type="hidden" name="orderField"
		value="${param.orderField eq null?'priority':param.orderField}" /> <input
		type="hidden" name="orderDirection"
		value="${param.orderDirection eq null?'asc':param.orderDirection}" />
	<div class="pageHeader">
		<div class="searchBar">
			<table style="align: center;">
				<tr>
					
					<td align="right"
						style="padding: 1px 2px 1px 200px; line-height: 21px;">原备付金预警值：</td>
					<td><input type="text" class="textInput" size="10"
						disabled="disabled" value="${value}"></td>
					<td>分钟</td>
					<td align="right"
						style="padding: 1px 2px 1px 20px; line-height: 21px;">新备付金预警值：</td>
					<td><input name="value" type="text" class="textInput"
						size="10" value="${value}"></td>
					<td>分钟</td>

				</tr>
				<tr>
					<td colspan="4">
						<div class="subBar">
							<ul>
								<li><div class="buttonActive" style="margin: 10px 30px">
										<div class="buttonContent">
											<button type="submit">确认</button>
										</div>
									</div></li>
								<li><div class="buttonActive" style="margin: 10px 30px">
										<div class="buttonContent">
											<button type="reset">取消</button>
										</div>
									</div></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>

		</div>
	</div>
</form>
