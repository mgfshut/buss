<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys" %>
<script>
$(document).ready(function() {
	
});
var csgId = '${csgId}';
</script>
<div id="pagerForm" class="pageContent">
	<form method="post" data-delay="100" action="service/contractInfo-checkPay" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="contUlName" id="filePath">
		<input type="hidden" name="contractInfoId" value="${contractInfoId }">
		<div id="selectedModulesHiddenFileds" ></div>
		<div class="pageFormContent  container-fluid" layoutH="68">
			<div class="form-horizontal">
				<div class="panel panel-default">
					<div class="panel-heading">客户信息</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-sm-1 control-label">客户名称：</label>
								<div class="col-sm-2">
									<input type="text" name="cusName" readOnly="readonly" 
										class="form-control" value="${cusName}" />
							</div>
							<label class="col-sm-1 control-label">法人代表：</label>
								<div class="col-sm-2">
									<input type="text" name="legalPer" readOnly="readonly"
										class="form-control" value="${legalPer}" />
							</div>
							<label class="col-sm-1 control-label">企业信用代码：</label>
								<div class="col-sm-3">
									<input type="text" name="creditCode" readOnly="readonly"
										class="form-control" value="${creditCode}" />
							</div>
							
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">联系方式：</label>
								<div class="col-sm-2">
									<input type="text" name="entTel" readOnly="readonly"
										class="form-control" value="${entTel}" />
							</div>
							<label class="col-sm-1 control-label">客户地址：</label>
								<div class="col-sm-6">
									<input type="text" name="entAddr" readOnly="readonly"
										class="form-control" value="${entAddr}" />
							</div>
						</div>
					</div>
					<div class="panel-heading">合同执行信息</div>
					<div class="panel-body">
						<div class="form-group col-sm-12">
							<label class="col-sm-2 control-label">执行人姓名：</label>
								<div class="col-sm-4">
									<input type="text" name="execName" readOnly="readonly" 
										class="form-control" value="${execName}" />
							</div>
							<label class="col-sm-2 control-label">执行人联系方式：</label>
								<div class="col-sm-5">
									<input type="text" name="execTel" readOnly="readonly"
										class="form-control" value="${execTel}" />
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label class="col-sm-2 control-label">文件接收地址：</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" class="form-control" value="${execAddr}" />
							</div>
						</div>
					</div>
   					<c:if test="${delvOpt eq '01'}">
						<div class="panel-heading">提货信息（安排物流）</div>
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-1 control-label">收货人姓名：</label>
										<div class="col-sm-2">
											<input type="text" name="csgName" readOnly="readonly" 
												class="form-control" value="${csgName}" />
									</div>
									<label class="col-sm-1 control-label">收货人联系方式：</label>
										<div class="col-sm-2">
											<input type="text" name="csgTel" readOnly="readonly"
												class="form-control" value="${csgTel}" />
									</div>
									<label class="col-sm-1 control-label">收货人身份证号：</label>
										<div class="col-sm-2">
											<input type="text" class="form-control" value="${csgId}" readonly="readonly">
									</div>
								</div>
								<div class="form-body">
									<label class="col-sm-2 control-label">收货人地址：</label>
										<div class="col-sm-8">
											<input type="text" name="csgAddr" readOnly="readonly" 
												class="form-control" value="${csgAddr}" />
									</div>
								</div>
							</div>
					</c:if>
					<c:if test="${delvOpt eq '02'}">
						<div class="panel-heading">提货信息（客户自提）</div>
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-1 control-label">收货人姓名：</label>
										<div class="col-sm-2">
											<input type="text" name="csgName" readOnly="readonly" 
												class="form-control" value="${csgName}" />
									</div>
									<label class="col-sm-1 control-label">收货人联系方式：</label>
										<div class="col-sm-2">
											<input type="text" name="csgTel" readOnly="readonly"
												class="form-control" value="${csgTel}" />
									</div>
									<label class="col-sm-1 control-label">收货人身份证号：</label>
										<div class="col-sm-2">
											<c:set var="csgIdLen" value="${fn:length(csgId) }"></c:set>
											<input type="text" class="form-control" value="${fn:substring(csgId, 0, 8)} ****** ${fn:substring(csgId, (csgIdLen-4), csgIdLen)}" readonly="readonly">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-1 control-label">提货车牌号码：</label>
										<div class="col-sm-2">
											<input type="text" name="carNum" readOnly="readonly" 
												class="form-control" value="${carNum}" />
									</div>
									<label class="col-sm-1 control-label">司机驾驶证号：</label>
										<div class="col-sm-2">
											<input type="text" name="driNum" readOnly="readonly"
												class="form-control" value="${driNum}" />
									</div>
								</div>
							</div>
					</c:if>
				</div>
			<div class="form-horizontal" align="center">
				<div class="form-group" align="center">
					<div class="col-sm-6" align="center">
						电子合同扫描件
						<div id="myCarousel" class="carousel slide">
						    <!-- 轮播（Carousel）项目 -->
						    <div class="carousel-inner">
						    	<c:forEach items="${fn:split(contUlName, ',')}" var="img" varStatus="ind">
						        <div class="item ${ind.index == 0 ? ' active' : '' }">
						            <img src="${httpUrl}${img}" alt="First slide ${ind.index}" style="height: 380px">
						        </div>
						        </c:forEach>
						    </div>
						    <!-- 轮播（Carousel）导航 -->
						    <a class="carousel-control left" href="#myCarousel" 
						        data-slide="prev">&lsaquo;
						    </a>
						    <a class="carousel-control right" href="#myCarousel" 
						        data-slide="next">&rsaquo;
						    </a>
						</div>	
					</div>
					<div class="col-sm-6" align="center">
						银行支付凭证
						<div id="yinhang" class="carousel slide">
						    <!-- 轮播（Carousel）项目 -->
						    <div class="carousel-inner">
						    	<c:forEach items="${fn:split(payPic, ',')}" var="img" varStatus="ind">
						        <div class="item ${ind.index == 0 ? ' active' : '' }">
						            <img src="${httpUrl}${img}" alt="First slide ${ind.index}" style="height: 380px">
						        </div>
						        </c:forEach>
						    </div>
						    <!-- 轮播（Carousel）导航 -->
						    <a class="carousel-control left" href="#yinhang" 
						        data-slide="prev">&lsaquo;
						    </a>
						    <a class="carousel-control right" href="#yinhang" 
						        data-slide="next">&rsaquo;
						    </a>
						</div>	
					</div>
				</div>
			</div>
		</div>
				
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>