<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ys" tagdir="/WEB-INF/tags"%>

<!-- 分页、搜索表单 -->

<input type="hidden" name="pageNum" value="${number+1}" />
<input type="hidden" name="numPerPage"
	value="${sessionScope.numPerPage eq null?10:sessionScope.numPerPage}" />
<input type="hidden" name="orderField"
	value="${param.orderField eq null?'priority':param.orderField}" />
<input type="hidden" name="orderDirection"
	value="${param.orderDirection eq null?'asc':param.orderDirection}" />
<div class="pageHeader">

	<div class="searchBar">
		<form id="pagerForm"
			action="module/sys-tradeTimeLimit-index/strategy-updateTradeTL"
			method="post" onsubmit="return navTabSearch(this);">
			<input type="hidden" name="strategyId" value="${tradeTimeLimitStart.strategyId}" /> <input
				type="hidden" name="name" value="${tradeTimeLimitStart.name}" />
			<table style="float: left">
				<tr>
					<td align="right"
						style="padding: 1px 2px 1px 200px; line-height: 21px;">交易时限：</td>
					<td><input type="text" class="textInput" size="10" name="value"
						value="${tradeTimeLimitStart.value}"></td>
					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive" style="margin: 10px 5px">
										<div class="buttonContent">
											<button type="submit">修改</button>
										</div>
									</div></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<form id="pagerForm"
			action="module/sys-tradeTimeLimit-index/strategy-updateTradeTL"
			method="post" onsubmit="return navTabSearch(this);">
			<input type="hidden" name="strategyId" value="${tradeTimeLimitEnd.strategyId}" /> <input
				type="hidden" name="name" value="${tradeTimeLimitEnd.name}" />
			<table style="float: left">
				<tr>
					<td align="right"
						style="padding: 1px 2px 1px 20px; line-height: 21px;">至：</td>
					<td><input name="value" type="text" class="textInput"
						size="10" value="${tradeTimeLimitEnd.value}"></td>
					<td colspan="5">
						<div class="subBar">
							<ul>
								<li><div class="buttonActive" style="margin: 10px 5px">
										<div class="buttonContent">
											<button type="submit">修改</button>
										</div>
									</div></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

