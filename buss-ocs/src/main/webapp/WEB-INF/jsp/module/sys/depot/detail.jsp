<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf" %>
<div id="depotMapForm" class="pageContent">
	<form class="pageForm">
	<div class="pageFormContent container-fluid" align="center"  layoutH="58">
			<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">仓库编号：</label>
				<div class="col-sm-4">
				<input type="text" name="depotNo" readonly="readonly" pattern="^[A-Za-z0-9]{2,10}$" data-error="请输入2-10位英文、数字" maxlength="10" placeholder="请输入仓库编码" class="form-control" required="required" value="${depotNo}" />
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">仓库名称：</label>
				<div class="col-sm-4">
				<input type="text" name="depotName" readonly="readonly" pattern="^.{2,32}$" data-error="请输入2-32位中,英文" placeholder="请输入仓库名称" maxlength="32" class="form-control" required="required"  value="${depotName}"/>
				</div>
			</div>
			
			<c:if test="${id ne null}">
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">创建时间：</label>
					<div class="col-sm-4">
					<input type="text" name="createTime" class="form-control"  value="${createTime }" readonly="readonly">
					</div>
				</div>
			</c:if>
		</div>
		</div>
		<div class="formBar">
			<ul>
				<li><a class="btn btn-sm btn-primary" href="module/sys-depot-form/depot-update-${depotId}" target="navTab" data-parent="depoteditpage" rel="depoteditpage" title="修改机构">修改</a></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>
</div>