<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="sdf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ys"%>
<script type="text/javascript">
	$(function(){
		setup()
	});
</script>
<div id="pageCodeMapForm" class="pageContent" >
	<form method="post" action="service/merchant-save"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="merchantId" value="${merchantId}" />
		<input type="hidden" id="checkAddress"  value="${address}" />
		<input type="hidden" name="category" value="1" />
		<div class="container-fluid" layoutH="58">			
			<div class="form-horizontal">
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label" >商户全称：</label>
					<div class="col-sm-9">
						<input type="text" name="fullName"  value="${fullName}" required="required" pattern="^.{1,64}$" data-error="请输入1-64个字符】"  maxlength="64" class="form-control  form-group"/>
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label" >商户简称：</label>
					<div class="col-sm-3">
						<input type="text" name="shortName"   value="${shortName}"  maxlength="32" class="form-control form-group"/>
						<div class="help-block "></div>
					</div>
					<label class="col-sm-1 control-label" >联系人：</label>
					<div class="col-sm-2">
						<input type="text" name="linkman"  value="${linkman}"  maxlength="16" class="form-control form-group"/>
						<div class="help-block "></div>
					</div>	
					<label class="col-sm-1 control-label" >联系电话：</label>
					<div class="col-sm-2">
						<input type="text" name="linkmanPhone"  value="${linkmanPhone}"  pattern="(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)" data-error="请输入正确的联系电话" maxlength="16" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>					
				</div>
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label">所在地址：</label>
					<div class="col-sm-2">
						<select id="s1"  class="form-control form-group"><option>省份</option></select>
					</div>
					<div class="col-sm-2">
						<select id="s2" class="form-control form-group"><option>地级市</option></select>
					</div>
					<div class="col-sm-2">
						<select id="s3" class="form-control form-group"><option>县级市</option></select>
					</div>
					<div class="col-sm-3">
						<input type="text" name="address" required="required" class="form-control form-group" id="address" maxlength="256" value="${address}" />	
					</div>
				</div>
				
				<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label" >所属行业：</label>
					<div class="col-sm-3">
						<ys:codemapSelect codemap="merchantTrade" name="trade" value="${trade}"  defaultText="--请选择--" required="required" classes="form-control form-group" ></ys:codemapSelect>											
					 </div>	
					<label class="col-sm-1 control-label" >所属城市：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="merchantCity" name="city" required="required"
						value="${city}" defaultText="--请选择--" 
						classes="form-control form-group input-sm "></ys:codemapSelect>
					</div>				
					<label class="col-sm-1 control-label" >来源渠道：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="sourceChannel" name="sourceChannel" value="${sourceChannel}" defaultText="--请选择--" classes="form-control form-group" ></ys:codemapSelect>											
					 </div>				
									
				</div>
				
				<div class="form-group form-group-sm">							
					<label class="col-sm-2 control-label" >组织机构代码：</label>
					<div class="col-sm-4">
						<input type="text" name="organizationCode"  value="${organizationCode}"  maxlength="16" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>
					
					<label class="col-sm-2 control-label" >营业执照号码：</label>
					<div class="col-sm-3">
						<input type="text" name="businessLicenceNum"  value="${businessLicenceNum}"  maxlength="16" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>
											
				</div>
				<div class="form-group form-group-sm">	
					
					<label class="col-sm-2 control-label" >主营业务：</label>
					<div class="col-sm-2">
						<input type="text" name="primaryBusiness"  value="${primaryBusiness}"  maxlength="16" class="form-control"/>
					</div>				
					<label class="col-sm-1 control-label" >所属地市：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="belongArea" name="belongArea"
						value="${belongArea}" defaultText="--请选择--" defaultValue=""
						classes="form-control input-sm "></ys:codemapSelect>
					</div>	
					
					<label class="col-sm-1 control-label" >商户邮箱：</label>
					<div class="col-sm-3">
						<input type="email" name="email"  value="${email}"   data-error="请输入正确的邮箱"  maxlength="16" class="form-control "/>
						<div class="help-block with-errors"></div>
					</div>											
				</div>
				<div class="form-group form-group-sm">	
					
					<label class="col-sm-2 control-label" >推荐人：</label>
					<div class="col-sm-2">
						<input type="text" name="referee"  value="${referee}"  maxlength="16" class="form-control"/>
					</div>				
					<label class="col-sm-2 control-label" >推荐人手机：</label>
					<div class="col-sm-2">
						<input type="text" name="refereePhone"  value="${refereePhone}"  pattern="(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)" data-error="请输入正确的联系电话"  maxlength="16" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>	
					
					<label class="col-sm-1 control-label" >业务类型：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="businessType" required="required"   name="businesstype" value="${businesstype}" defaultText="--请选择--"  classes="form-control form-group" ></ys:codemapSelect>
					</div>	 
											
				</div>
				
				<div class="form-group form-group-sm">
					<label class="col-sm-2 control-label" >财务联系人：</label>
					<div class="col-sm-2">
						<input type="text" name="financialContact"  value="${financialContact}"  maxlength="16" class="form-control"/>
					</div>
					<label class="col-sm-2 control-label" >财务联系人电话：</label>
					<div class="col-sm-2">
						<input type="text" name="financialPhone"  value="${financialPhone}" pattern="(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)" data-error="请输入正确的联系电话" maxlength="16" class="form-control "/>
						<div class="help-block with-errors"></div>
					</div>
					
					<label class="col-sm-1 control-label" >财务传真：</label>
					<div class="col-sm-2">
						<input type="text" name="financialFax"  value="${financialFax}"  maxlength="16" class="form-control"/>
					</div>					
				</div>
				
				
				<div class="form-group form-group-sm">							
					<label class="col-sm-2 control-label" >结算账户开户行代码：</label>
					<div class="col-sm-4">
						<input type="text" name="accountBankCode"  value="${accountBankCode}"  maxlength="32" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>
					
					<label class="col-sm-2 control-label" >结算账户开户行名称：</label>
					<div class="col-sm-3">
						<input type="text" name="accountBankName"  value="${accountBankName}"  maxlength="16" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>
											
				</div>
				<div class="form-group form-group-sm">							
					<label class="col-sm-2 control-label" >结算账户开户银行：</label>
					<div class="col-sm-4">
						<input type="text" name="accountBank"  value="${accountBank}"  maxlength="16" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>
					
					<label class="col-sm-2 control-label" >结算账户名称：</label>
					<div class="col-sm-3">
						<input type="text" name="accountName"  value="${accountName}"  maxlength="32" class="form-control form-group"/>
						<div class="help-block with-errors"></div>
					</div>
					
											
				</div>
				<div class="form-group form-group-sm">							
				
					<label class="col-sm-2 control-label" >结算账户账号：</label>
					<div class="col-sm-3">
						<input type="text" name="accountNumber"  value="${accountNumber}"  maxlength="32" class="form-control"/>
					</div>
					
					<label class="col-sm-1 control-label" >结算周期：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="settlementCycle" name="settlementCycle"
						value="${settlementCycle}" defaultText="--请选择--" defaultValue=""
						classes="form-control input-sm "></ys:codemapSelect>
					</div>
					<label class="col-sm-1 control-label" >结算模式：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="settlePattern" name="settlementPattern"
						value="${settlementPattern}" defaultText="--请选择--" defaultValue=""
						classes="form-control input-sm "></ys:codemapSelect>
					</div>
											
				</div>
				<div class="form-group form-group-sm">		
					<label class="col-sm-2 control-label" >营业执照有效期：</label>
					<div class="col-sm-2">
						<input type="text" name="validityBuslicence"  value="${validityBuslicence}" pattern="^[0-9]{0,15}$" data-error="整数0~15位" maxlength="8" class="form-control"/>
						<div class="help-block with-errors"></div>
					</div>				
					<label class="col-sm-1 control-label" >注册日期：</label>
					<div class="col-sm-3">
						<input type="text" name="registerDate"  value="${registerDate}" data-provide="datepicker"   maxlength="16" class="form-control"/>
					</div>	
					<label class="col-sm-1 control-label" >商户性质：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="merchantNature" name="nature"
						value="${nature}" defaultText="--请选择--" defaultValue=""
						classes="form-control input-sm "></ys:codemapSelect>
					</div>	
																	
				</div>
				
				<div class="form-group form-group-sm">	
				
					<label class="col-sm-2 control-label" >注册资本：</label>
					<div class="col-sm-2">
						<input type="text" name="registeredCapital"  value="${registeredCapital}" pattern="^(([0-9]|([1-9][0-9]{0,15}))((\.[0-9]{1,2})?))$" data-error="整数0~16位,小数0~2位"  maxlength="12" class="form-control"/>
						<div class="help-block with-errors"></div>
					</div>	
					
					<label class="col-sm-1 control-label" >资金用途：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="purposeCapital" name="purposeCapital"
						value="${purposeCapital}" defaultText="--请选择--" defaultValue=""
						classes="form-control form-group input-sm "></ys:codemapSelect>
					</div>							
									
					<label class="col-sm-1 control-label" >公司规模：</label>
					<div class="col-sm-3">
						<ys:codemapSelect codemap="companyScale" name="companyScale"
						value="${companyScale}" defaultText="--请选择--" defaultValue=""
						classes="form-control form-group input-sm "></ys:codemapSelect>
					</div>						
				</div>
				<div class="form-group form-group-sm">	
					<label class="col-sm-2 control-label" >节假日是否并表：</label>
					<div class="col-sm-2">
						<ys:codemapSelect codemap="mergeTable" name="mergeTable"
						value="${mergeTable}" defaultText="--请选择--" defaultValue=""
						classes="form-control form-group input-sm "></ys:codemapSelect>
					</div>
					<label class="col-sm-1 control-label" >状态：</label>
					<div class="col-sm-2">					
						<ys:codemapSelect codemap="merchantsStatus" name="status" 
						value="${status}" defaultText="--请选择--" required="required"
						classes="form-control form-group input-sm "></ys:codemapSelect>
					</div>																
					<label class="col-sm-1 control-label" >经营范围：</label>
					<div class="col-sm-3">
						<ys:codemapSelect codemap="businessScope" name="businessScope"
						value="${businessScope}" defaultText="--请选择--" defaultValue=""
						classes="form-control form-group input-sm "></ys:codemapSelect>
					</div>											
				</div>		
				<div class="form-group ">
					<label class="col-sm-2 control-label" >备注：</label>
					<div class="col-sm-9 " >
						<textarea rows="3" name="remark" class="form-control form-group">${remark}</textarea>			
					</div>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><button class="btn btn-sm btn-primary" type="submit">提交</button></li>
				<li><button class="btn btn-sm btn-warning" data-dismiss="modal" type="button">取消</button></li>
			</ul>
		</div>
	</form>

</div>