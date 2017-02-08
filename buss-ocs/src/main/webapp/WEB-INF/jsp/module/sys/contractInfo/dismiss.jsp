<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<style type="text/css">
	.body{
		width: 590px;
		margin: 0 auto;
	}
	p{
		line-height: 30px;
	}
	h1{
		margin-bottom: 40px;
		text-align: center;
	}
	h2{
		margin-top: 20px;
		text-align: center;
	}
	.body table:nth-child(8){
		width: 100%;
	}
	.body table:nth-child(8) tr{
		height: 40px;
		text-align: center;
	}
	.time{
		text-align: right;
	}
	.tab2{
		width: 100%;
	}
	.tab2 tr{
		height: 40px;
		text-align: center;
	}
	.tab2 tr td{
		width: 14.2%;
	}
	h3{
		margin-top: 30px;
		margin-bottom: 20px;
	}
	h4{
		margin-bottom: 20px;
	}
	.firstp{
		text-align: right;
		margin-right: 80px;
	}
</style>
<div id="pagerForm" class="pageContent">
	<form method="post" data-delay="100" action="module/sys-contractInfo-index/contractInfo-dismissContranct" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="container-fluid" layoutH="68">
						<div class="body">
						<div id="prtarea">${contractInfoId }
						</br>
						${transactionInfoId }
						<input type="hidden" id="contractInfoId" name="contractInfoId" value="${contractInfoId }">
							<p class="firstp">合同编号：${conCode }</p>
							<h3>商品销售合同驳回原因填写</h3>
							<p>
								乙方（买家）：${buyName }<br />
								代&nbsp;表&nbsp;人&nbsp;：${legalPer }<br />
								信用代码：${creditCode }<br />
								地&nbsp;址&nbsp;：${entAddr }<br />
								电&nbsp;话&nbsp;：${entTel }
							</p>
							<div>
							<p>驳回原因（必填 *）</p>
							<textarea name="dmRea" id="dmRea" cols="100" rows="10" style="text-align: left;">请填写驳回原因</textarea>
							</div>
						</div>
			          </div>
		</div>
		<div class="formBar">
			<ul>
			    <li><button class="btn btn-sm btn-primary disabled" type="submit" style="pointer-events: all; cursor: pointer;">提交</button></li>
			    <!-- <li><button class="btn btn-sm btn-warning"  type="button">驳回</button></li> -->
			</ul>
		</div>
	</form>
</div>