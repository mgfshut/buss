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
<input type="hidden" id="contStatus" name="contStatus" value="${contStatus }">
	<form method="post" data-delay="100" action="" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="container-fluid" layoutH="68">
						<div class="body">
						<div id="prtarea">
							<p class="firstp">合同编号：${conCode }</p>
							<h1>商品销售合同</h1>
							<p>
								甲方（卖家）：<br />
								代&nbsp;表&nbsp;人&nbsp;：<br />
								信用代码&nbsp;：<br />
								地&nbsp;址&nbsp;：<br />
								电&nbsp;话&nbsp;：
								<br /><br />
								乙方（买家）：${buyName }<br />
								代&nbsp;表&nbsp;人&nbsp;：${legalPer }<br />
								信用代码：${creditCode }<br />
								地&nbsp;址&nbsp;：${entAddr }<br />
								电&nbsp;话&nbsp;：${entTel }
							</p>
							<br />
							<p>
								&nbsp;&nbsp;甲、乙双方遵循 自愿、公平、等价有偿、诚实信用的原则，根据《中华人民共和国民法通则》、《中华人民共和国合同法》、《中华人民共和国食品安全法》、《中华人民共和国进出口商品检验法》及其他相关法律的规定，经友好协商，就甲方向乙方售出的商品达成如下一致意见，订立本合同，以资共同遵守。
							</p>
							<h2>第一部分  双方约定内容</h2>
							<h3>一、需求商品信息</h3>
							<table class="tab1" cellspacing="0" cellpadding="0" border="solid 1px #000000">
								<tr>
									<th>序号</th>
									<th>国家<br />厂号</th>
									<th>商品<br />名称</th>
									<th>规格</th>
									<th>包装<br />规格</th>
									<th>单价<br />(元)</th>
									<th>数量<br />(千克)</th>
									<th>船期</th>
									<th>小计<br />(元)</th>
									<th>备注</th>
								</tr>
								<tr>
									<td>1</td>
									<td>${manuNum }</td>
									<td>${cateName }</td>
									<td>${comm }</td>
									<td><ys:codemapConvert codemap="cateStan" value="${cateStan}"/></td>
									<td>${offerPri }</td>
									<td>${txAmo }</td>
									<td></td>
									<td>${totPriBig }</td>
									<td></td>
								</tr>
								<tr>
									<td>2</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>3</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>4</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>5</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td colspan="2">其他费用(元)</td>
									<!--<td></td>-->
									<td colspan="8"></td>
								</tr>
								<tr>
									<td colspan="2">合计金额</td>
									<!--<td></td>-->
									<td colspan="8">
										<p style="text-align: left;margin-bottom: -10px;"><span style="text-decoration:underline">${totPriBig }</span>元</p>
										<p style="text-align: left;margin-bottom: 0px;">大写金额：（${upperTotPri }）</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">备     注</td>
									<!--<td></td>-->
									<td colspan="8" style="text-align: left;">
										<p style="margin-bottom: -10px;">
										1、	本表所列单价为本合同约定的固定价格，不受国际市场的商品价格波动影响；
										</p>
										<p style="margin-bottom: -10px;">
										2、	附带食品检验检疫证明复印件；
										</p>
										<p style="margin-bottom: 0px;">
										3、	船期由甲方根据商品情况提供。 
										</p>
									</td>
								</tr>
							</table>
							<h3>二、交易要求</h3>
							<p>&nbsp;&nbsp;1.甲方会在合同生效后一周左右发送《到港期告知函》（见附件1）给乙方。</p>
							<p>&nbsp;&nbsp;2.货物到达国内港口后， 如由甲方安排商品运输，则双方协商确定商品运输方式，所产生费用另议。</p>
							<p>乙方应详细列明以下信息：</p>
							<p>&nbsp;&nbsp;指定收货人姓名：${csgName }</p>
							<p>&nbsp;&nbsp;收货人联系方式：${csgTel }</p>
							<p>&nbsp;&nbsp;收货详细地址：${csgAddr }</p>
							<p>&nbsp;&nbsp;身份证信息：${csgId }</p>
							<p>&nbsp;&nbsp;乙方授权委托书：参见附件</p>
							<p>&nbsp;&nbsp;2.货物到达国内港口后，如乙方自提，应在商品清关后即日办理提货，如延后提货，所产生的仓储费用由乙方承担。乙方需列明以下信息：</p>
							<p>&nbsp;&nbsp;指定收货人姓名：${ztcsgName }</p>
							<p>&nbsp;&nbsp;收货人联系方式：${ztcsgTel }</p>
							<p>&nbsp;&nbsp;提货车辆牌号：${carNum }</p>
							<p>&nbsp;&nbsp;车辆驾驶证号：${driNum }</p>
							<p>&nbsp;&nbsp;身份证信息：${ztcsgId }</p>
							<p>&nbsp;&nbsp;乙方授权委托书：参见附件</p>
							<p>&nbsp;&nbsp;3. 如乙方须变更收货人相关信息，应以书面方式告知甲方，并提供变更后的信息。</p>
							<p>&nbsp;&nbsp;4. 在执行本合同的过程中，按照第一项约定的商品数量进行交易。如需对商品数量进行调整，需经甲乙双方同意并另行签订补充合同。</p>
							<h3>三、合同有效期</h3>
							<p>&nbsp;&nbsp;本合同有效期自<span style="text-decoration:underline">${genckYear }</span>年
								<span style="text-decoration:underline">${genckMonth }</span>月
								<span style="text-decoration:underline">${genckDay }</span>日起至乙方指定收货人对商品进行验收确认为止。</p>
							<h2>第二部分  通用条款</h2>
							<h3>一、	主体资格</h3>
							<p>&nbsp;&nbsp;1. 甲方是从事本合同项下商品销售的具有法人资格的公司，能够向乙方提供符合本合同要求的商品。甲方承诺具有销售本合同项下商品所需的、根据中国法律法规从事食品生产经营所需具备的资格或许可；遵循国家的法律法规进行生产经营和管理。</p>
							<p>&nbsp;&nbsp;2. 乙方是一家具有良好经营状况的公司或良好商业信誉的个人/个体工商户。</p>
							<h3>二、	商品验收</h3>
							<p>&nbsp;&nbsp;1.无论是甲方安排商品运输还是乙方自提货，乙方收货人均须对接收的商品办理验收，并在甲方的《货物签收单》对签字确认。</p>
							<p>&nbsp;&nbsp;2. 乙方对商品质量有争议的，应在收到商品后十五日内向甲方书面提出，由共同确认的检验机构对商品进行检验，并以该检验机构依据货品产地质量标准进行鉴定的检验结果作为确认商品质量的依据，检验检疫费用由乙方先行垫付。如检验结果不符合质量标准，检验检疫费由甲方承担；符合质量标准，检验检疫费由乙方承担。检验只能申请一次，检验结论双方均予以接受。</p>
							<p>&nbsp;&nbsp;3.因乙方提出商检所发生的仓储费用及延期交付风险，费用由乙方承担。</p>
							<h3>三、款项支付</h3>
							<p>&nbsp;&nbsp;1. 甲乙双方签署合同后的三个工作日内，乙方应向甲方支付定金 ______________ 元（大写金额：______________________________________）。合同签署后十五个工作日内乙方未支付定金的，则视同乙方违约，甲方有权追究乙方责任并有权终止合同。</p>
							<p>&nbsp;&nbsp;2. 甲方在商品抵达国内港口前，甲方提前10天向乙方发送付款通知书，乙方收到付款通知书后三个工作日内应向甲方支付 _____________ 元（大写金额：_________________________________ ）尾款。尾款实际到账后，甲方安排商品交付事宜。</p>
							<p>&nbsp;&nbsp;3. 甲方账户信息如下：</p>
							<p>&nbsp;&nbsp;账户名称：</p>
							<p>&nbsp;&nbsp;账&nbsp;&nbsp;号：</p>
							<p>&nbsp;&nbsp;开  户 行：</p>
							<p>&nbsp;&nbsp;行&nbsp;&nbsp;号：</p>
							<h3>四、双方的权利和义务</h3>
							<h4>&nbsp;&nbsp;1.双方权利包括：</h4>
							<p>&nbsp;&nbsp;（1）	合同执行过程中，任何一方有新增需求或变更意见时，须提前二十个工作日向对方指定人员书面告知并协商解决。</p>
							<p>&nbsp;&nbsp;（2）	按本合同的规定接收商品或收取货款。</p>
							<h4>&nbsp;&nbsp;2.双方义务包括：</h4>
							<p>&nbsp;&nbsp;（1）	指定专门的机构或人员负责本合同项下有关商品销售/采购的具体联络、供求平衡、执行、协调等工作。</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;甲方指定人员：${jfzdPersonName }</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;联系方式（包括电话及EMAIL）：${jfzdPersonPhone }</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;指定人员文件接收地址：</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;乙方指定人员：${execName }</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;联系方式（包括电话及EMAIL）：${execTel }</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;指定人员文件接收地址：${execAddr }</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;如双方指定人员需作变更，应在变更前三个工作日内，以书面形式告知对方。</p>
							<p>&nbsp;&nbsp;（2）	按本合同规定交付商品或支付货款。</p>
							<h3>五、合同的生效、解除与终止</h3>
							<p>&nbsp;&nbsp;1. 本合同需甲、乙双方法定代表人或授权代表签字并加盖公章。如由授权代表签字的，须向对方提供有效的授权证明文件或该文件的复印件。</p>
							<p>&nbsp;&nbsp;2. 本合同自签订之日起生效，乙方签收货单之日终止。</p>
							<p>&nbsp;&nbsp;3. 除本合同另有约定外，甲乙双方协商一致，可以签订补充合同，补充合同与本合同具有同等法律效力。</p>
							<p>&nbsp;&nbsp;4. 在甲乙双方就本合同终止达成一致书面意见前，本合同任一方发出的终止合同通知，都不会影响本合同中列明的权利和义务。</p>
							<h3>六、不可抗力</h3>
							<p>&nbsp;&nbsp;1. 本合同中的不可抗力包含但不限于因地震、台风、战争、恐怖袭击、瘟疫流行、政治因素以及其他对其发生和后果不可预见、不可预防以及不可避免的事件。</p>
							<p>&nbsp;&nbsp;2. 任何一方因不可抗力事件导致不能履行本合同或延迟履行本合同的，受影响的一方应立即通知另一方，并在此后十五天内提供不可抗力事件的详情和证明不可抗力事件发生的有效证明文件，解释其无法履行或推迟履行本合同全部或部分的原因。双方应通过协商决定是否终止本合同、继续履行本合同、还是变更本合同中受不可抗力事件影响的合同内容。</p>
							<p>&nbsp;&nbsp;3. 一旦不可抗力事件终止，受不可抗力影响的一方应立即采取措施，继续履行可履行的合同部分。</p>
							<h3>七、违约责任</h3>
							<p>&nbsp;&nbsp;1. 除法律和本合同另有规定，任何一方违反本合同约定的条款，另一方当事人可向其发出书面通知，并要求违约方在指定的合理期限内做出补救，如违约方未在该指定的合理期限内对违约行为做出补救，则守约方可以立即终止本合同。守约方仍有向违约方求偿和其他法律允许的权利主张的权利。</p>
							<p>&nbsp;&nbsp;2. 发生违约情形，违约方应赔偿由此给对方造成的损失。如属双方过错，应各自承担相应责任。</p>
							<p>&nbsp;&nbsp;3．如因乙方单方面取消订单，承担定金责任。上述责任不足以抵偿甲方实际损失的，乙方应当补足。</p>
							<h3>八、争议的解决</h3>
							<p>&nbsp;&nbsp;1. 除本合同另有约定外，因履行本合同或与本合同有关的任何争议，双方应通过友好协商解决。协商不成的，任何一方有权将纠纷提交甲方所在地的人民法院进行裁决。</p>
							<p>&nbsp;&nbsp;2. 在裁决过程中，双方应继续履行本合同，但正在进行裁决的争议事项除外。</p>
							<h3>九、权利保留</h3>
							<p>&nbsp;&nbsp;如任何一方在任何时候未要求另一方严格遵守本合同中的任何条款，不应被视为该方放弃该条款或权利。相反，该条款或权利应继续完全适用，一方累积的权利或义务应该继续被享有或承担。</p>
							<h3>十、其他</h3>
							<p>&nbsp;&nbsp;1. 除非双方书面约定或法律有明文规定，本合同的权利义务不可转让、分包给第三方。</p>
							<p>&nbsp;&nbsp;2. 本合同未尽事宜，由双方另行协商，并签订补充合同。</p>
							<p>&nbsp;&nbsp;3. 本合同的各项条款属于双方经营活动内容, 任何一方未经对方当事人书面允许不得对外泄露。</p>
							<p>&nbsp;&nbsp;4. 本合同正本及附件壹式肆份，甲乙双方各执贰份，具有同等法律效力。</p>
							<p>&nbsp;&nbsp;5. 双方对本合同条款已尽相互的说明义务，并对本合同的所有条款均已充分注意并详细了解双方对各方根据本合同享有的权利和义务；双方对可能违反本合同时所应承担的违约责任的风险亦有清楚的了解；对合同中限制或免除双方责任的条款以及增加对方责任的条款，双方均已仔细阅读，充分理解并自愿接受该条款。</p>
							<p>&nbsp;&nbsp;（签字页见下页）</p>
							<p>&nbsp;&nbsp;（本页无正文内容）</p>
							<p>&nbsp;&nbsp;甲&nbsp;&nbsp;&nbsp;方：______________________________<br />&nbsp;（盖章）</p>
							<p>&nbsp;&nbsp;法定代表人：______________________________<br />&nbsp;（盖章）</p>
							<p>&nbsp;&nbsp;授权代表&nbsp;：______________________________<br />&nbsp;（盖章）</p>
							<p class="time">年&nbsp;&nbsp;月&nbsp;&nbsp;日</p>
							<p>&nbsp;&nbsp;乙&nbsp;&nbsp;&nbsp;方：______________________________<br />&nbsp;（盖章）</p>
							<p>&nbsp;&nbsp;法定代表人：______________________________<br />&nbsp;（盖章）</p>
							<p>&nbsp;&nbsp;授权代表&nbsp;：______________________________<br />&nbsp;（盖章）</p>
							<p class="time">年&nbsp;&nbsp;月&nbsp;&nbsp;日</p>
							
							
							<!-- <h3>附件1：</h3>
							<h1>到港期告知函</h1>
							<p>&nbsp;&nbsp;本告知函之主合同（合同编号：______________）内乙方指定购买的商品，甲方将按下述日期安排到港：</p>
							<table class="tab2" border="solid 1px #000000" cellspacing="0" cellpadding="0">
								<tr>
									<th>序号</th>
									<th>国家厂号</th>
									<th>商品名称</th>
									<th>规格</th>
									<th>包装规格</th>
									<th>到港日期</th>
									<th>备注</th>
								</tr>
								<tr>
									<td>1</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>2</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>3</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>4</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>5</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
							<p>&nbsp;&nbsp;请知悉！</p>
							<p class="time">告知方：天津荣汇国际贸易有限公司</p>
							<p class="time">时&nbsp;间：______年______月______日</p> -->
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