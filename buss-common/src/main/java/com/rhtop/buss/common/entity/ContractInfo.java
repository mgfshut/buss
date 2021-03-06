/**
 *
 */
package com.rhtop.buss.common.entity;

import java.math.BigDecimal;


//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 合同信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "ContractInfo")
public class ContractInfo {
	private String transactionInfoId;//交易ID
	
	private String carNum;//提货车牌号码
	//真正生成的合同编号
	private String conCode;
	//客户姓名
	private String cusName;
	
	private Double totPri;//总价
	
	private BigDecimal totPriBig;//总价
	
	private Float txAmo;//交易数量
	
	private String delvOpt;//配送方式选择
	
	private Float ctofPri;//回盘价
	
	private String driNum;//驾驶证号
	
	private String entAddr;//公司地址
	
	private String contStatus;//合同状态
	
	private String genckTime;//总经理审核时间
	
	private String csgId;//收货人身份证号
	
	private String categoryId;//品类ID
	
	private String contractInfoId;//合同编号
	
	private String endTime;//合同结束时间
	
	private String execTel;//执行人联系方式
	
	private String updateTime;//修改时间
	
	private String elecContUrl;//电子合同地址
	
	private String execAddr;//执行人文件接收地址
	
	private String csgAddr;//收货人详细地址
	
	private String entTel;//公司联系方式
	
	private String creditCode;//企业信用代码
	
	private String csgName;//收货人姓名
	
	private String execName;//执行人姓名
	
	private String contUlName;//合同文件名
	
	private String payPic;//财务收款截图相对路径
	
	private String csgTel;//收货人联系方式
	
	private String expressId;//快递单号
	
	private String createTime;//创建时间
	
	private String createUser;//创建人
	
	private String legalPer;//法人代表
	
	private String customerId;//客户ID
	
	private String updateUser;//修改人
	
	private Page page;//分页
	
	private String cateName;//品类名称
	//Http url
	private String httpUrl;
	
	private String dmRea;//合同驳回的原因
	
	/**打印所用字段begin**/
	private String buyName;//买方
	private String manuNum;//厂号
	private String comm;//规格
	private String cateStan;//包装规格
	private Float offerPri;//单价(这是经过了单位换算的价格)
	private String ztcsgName;//自提收货人姓名
	private String ztcsgTel;//自提收货人联系方式
	private String ztcsgAddr;//自提收货人详细地址
	private String ztcsgId;//自提收货人身份证号
	private String upperTotPri;//大写金额
	private String genckYear;//总经理确认时间年 
	private String genckMonth;//总经理确认时间月 
	private String genckDay;//总经理确认时间日
	private String jfzdPersonName;//甲方指定人员姓名
	private String jfzdPersonPhone;//甲方指定人员联系方式
	/**打印所用字段end**/
	
	
	
	public String getConCode() {
		return conCode;
	}

	public BigDecimal getTotPriBig() {
		return totPriBig;
	}

	public void setTotPriBig(BigDecimal totPriBig) {
		this.totPriBig = totPriBig;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}
	public Page getPage() {
		return page;
	}

	public String getJfzdPersonName() {
		return jfzdPersonName;
	}

	public void setJfzdPersonName(String jfzdPersonName) {
		this.jfzdPersonName = jfzdPersonName;
	}

	public String getJfzdPersonPhone() {
		return jfzdPersonPhone;
	}

	public void setJfzdPersonPhone(String jfzdPersonPhone) {
		this.jfzdPersonPhone = jfzdPersonPhone;
	}

	public String getGenckYear() {
		return genckYear;
	}

	public void setGenckYear(String genckYear) {
		this.genckYear = genckYear;
	}

	public String getGenckMonth() {
		return genckMonth;
	}

	public void setGenckMonth(String genckMonth) {
		this.genckMonth = genckMonth;
	}

	public String getGenckDay() {
		return genckDay;
	}

	public void setGenckDay(String genckDay) {
		this.genckDay = genckDay;
	}

	public String getUpperTotPri() {
		return upperTotPri;
	}

	public void setUpperTotPri(String upperTotPri) {
		this.upperTotPri = upperTotPri;
	}

	public String getZtcsgName() {
		return ztcsgName;
	}

	public void setZtcsgName(String ztcsgName) {
		this.ztcsgName = ztcsgName;
	}

	public String getZtcsgTel() {
		return ztcsgTel;
	}

	public void setZtcsgTel(String ztcsgTel) {
		this.ztcsgTel = ztcsgTel;
	}

	public String getZtcsgAddr() {
		return ztcsgAddr;
	}

	public void setZtcsgAddr(String ztcsgAddr) {
		this.ztcsgAddr = ztcsgAddr;
	}

	public String getZtcsgId() {
		return ztcsgId;
	}

	public void setZtcsgId(String ztcsgId) {
		this.ztcsgId = ztcsgId;
	}

	public String getManuNum() {
		return manuNum;
	}

	public void setManuNum(String manuNum) {
		this.manuNum = manuNum;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getCateStan() {
		return cateStan;
	}

	public void setCateStan(String cateStan) {
		this.cateStan = cateStan;
	}

	public Float getOfferPri() {
		return offerPri;
	}

	public void setOfferPri(Float offerPri) {
		this.offerPri = offerPri;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getTransactionInfoId(){
		return this.transactionInfoId;
	}
	
	public void setTransactionInfoId(String transactionInfoId){
		this.transactionInfoId = transactionInfoId;
	}
	public String getCarNum(){
		return this.carNum;
	}
	
	public void setCarNum(String carNum){
		this.carNum = carNum;
	}
	public Double getTotPri(){
		return this.totPri;
	}
	
	public void setTotPri(Double totPri){
		this.totPri = totPri;
	}
	public Float getTxAmo(){
		return this.txAmo;
	}
	
	public void setTxAmo(Float txAmo){
		this.txAmo = txAmo;
	}
	public String getDelvOpt(){
		return this.delvOpt;
	}
	
	public void setDelvOpt(String delvOpt){
		this.delvOpt = delvOpt;
	}
	public Float getCtofPri(){
		return this.ctofPri;
	}
	
	public void setCtofPri(Float ctofPri){
		this.ctofPri = ctofPri;
	}
	public String getDriNum(){
		return this.driNum;
	}
	
	public void setDriNum(String driNum){
		this.driNum = driNum;
	}
	public String getEntAddr(){
		return this.entAddr;
	}
	
	public void setEntAddr(String entAddr){
		this.entAddr = entAddr;
	}
	public String getContStatus(){
		return this.contStatus;
	}
	
	public void setContStatus(String contStatus){
		this.contStatus = contStatus;
	}
	public String getCsgId(){
		return this.csgId;
	}
	
	public void setCsgId(String csgId){
		this.csgId = csgId;
	}
	public String getCategoryId(){
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	public String getContractInfoId(){
		return this.contractInfoId;
	}
	
	public void setContractInfoId(String contractInfoId){
		this.contractInfoId = contractInfoId;
	}
	public String getEndTime(){
		return this.endTime;
	}
	
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	public String getExecTel(){
		return this.execTel;
	}
	
	public void setExecTel(String execTel){
		this.execTel = execTel;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getElecContUrl(){
		return this.elecContUrl;
	}
	
	public void setElecContUrl(String elecContUrl){
		this.elecContUrl = elecContUrl;
	}
	public String getExecAddr(){
		return this.execAddr;
	}
	
	public void setExecAddr(String execAddr){
		this.execAddr = execAddr;
	}
	public String getCsgAddr(){
		return this.csgAddr;
	}
	
	public void setCsgAddr(String csgAddr){
		this.csgAddr = csgAddr;
	}
	public String getEntTel(){
		return this.entTel;
	}
	
	public void setEntTel(String entTel){
		this.entTel = entTel;
	}
	public String getCreditCode(){
		return this.creditCode;
	}
	
	public void setCreditCode(String creditCode){
		this.creditCode = creditCode;
	}
	public String getCsgName(){
		return this.csgName;
	}
	
	public void setCsgName(String csgName){
		this.csgName = csgName;
	}
	public String getExecName(){
		return this.execName;
	}
	
	public void setExecName(String execName){
		this.execName = execName;
	}
	public String getContUlName(){
		return this.contUlName;
	}
	
	public void setContUlName(String contUlName){
		this.contUlName = contUlName;
	}
	public String getCsgTel(){
		return this.csgTel;
	}
	
	public void setCsgTel(String csgTel){
		this.csgTel = csgTel;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getLegalPer(){
		return this.legalPer;
	}
	
	public void setLegalPer(String legalPer){
		this.legalPer = legalPer;
	}
	public String getCustomerId(){
		return this.customerId;
	}
	
	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	
	
	
	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactionInfoId == null) ? 0 : transactionInfoId.hashCode());
		result = prime * result + ((carNum == null) ? 0 : carNum.hashCode());
		result = prime * result + ((totPri == null) ? 0 : totPri.hashCode());
		result = prime * result + ((txAmo == null) ? 0 : txAmo.hashCode());
		result = prime * result + ((delvOpt == null) ? 0 : delvOpt.hashCode());
		result = prime * result + ((ctofPri == null) ? 0 : ctofPri.hashCode());
		result = prime * result + ((driNum == null) ? 0 : driNum.hashCode());
		result = prime * result + ((entAddr == null) ? 0 : entAddr.hashCode());
		result = prime * result + ((contStatus == null) ? 0 : contStatus.hashCode());
		result = prime * result + ((csgId == null) ? 0 : csgId.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((contractInfoId == null) ? 0 : contractInfoId.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((execTel == null) ? 0 : execTel.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((elecContUrl == null) ? 0 : elecContUrl.hashCode());
		result = prime * result + ((execAddr == null) ? 0 : execAddr.hashCode());
		result = prime * result + ((csgAddr == null) ? 0 : csgAddr.hashCode());
		result = prime * result + ((entTel == null) ? 0 : entTel.hashCode());
		result = prime * result + ((creditCode == null) ? 0 : creditCode.hashCode());
		result = prime * result + ((csgName == null) ? 0 : csgName.hashCode());
		result = prime * result + ((execName == null) ? 0 : execName.hashCode());
		result = prime * result + ((contUlName == null) ? 0 : contUlName.hashCode());
		result = prime * result + ((csgTel == null) ? 0 : csgTel.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((legalPer == null) ? 0 : legalPer.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContractInfo other = (ContractInfo) obj;
		if (transactionInfoId == null) {
			if (other.transactionInfoId != null)
				return false;
		} else if (!transactionInfoId.equals(other.transactionInfoId))
			return false;
		if (carNum == null) {
			if (other.carNum != null)
				return false;
		} else if (!carNum.equals(other.carNum))
			return false;
		if (totPri == null) {
			if (other.totPri != null)
				return false;
		} else if (!totPri.equals(other.totPri))
			return false;
		if (txAmo == null) {
			if (other.txAmo != null)
				return false;
		} else if (!txAmo.equals(other.txAmo))
			return false;
		if (delvOpt == null) {
			if (other.delvOpt != null)
				return false;
		} else if (!delvOpt.equals(other.delvOpt))
			return false;
		if (ctofPri == null) {
			if (other.ctofPri != null)
				return false;
		} else if (!ctofPri.equals(other.ctofPri))
			return false;
		if (driNum == null) {
			if (other.driNum != null)
				return false;
		} else if (!driNum.equals(other.driNum))
			return false;
		if (entAddr == null) {
			if (other.entAddr != null)
				return false;
		} else if (!entAddr.equals(other.entAddr))
			return false;
		if (contStatus == null) {
			if (other.contStatus != null)
				return false;
		} else if (!contStatus.equals(other.contStatus))
			return false;
		if (csgId == null) {
			if (other.csgId != null)
				return false;
		} else if (!csgId.equals(other.csgId))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (contractInfoId == null) {
			if (other.contractInfoId != null)
				return false;
		} else if (!contractInfoId.equals(other.contractInfoId))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (execTel == null) {
			if (other.execTel != null)
				return false;
		} else if (!execTel.equals(other.execTel))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (elecContUrl == null) {
			if (other.elecContUrl != null)
				return false;
		} else if (!elecContUrl.equals(other.elecContUrl))
			return false;
		if (execAddr == null) {
			if (other.execAddr != null)
				return false;
		} else if (!execAddr.equals(other.execAddr))
			return false;
		if (csgAddr == null) {
			if (other.csgAddr != null)
				return false;
		} else if (!csgAddr.equals(other.csgAddr))
			return false;
		if (entTel == null) {
			if (other.entTel != null)
				return false;
		} else if (!entTel.equals(other.entTel))
			return false;
		if (creditCode == null) {
			if (other.creditCode != null)
				return false;
		} else if (!creditCode.equals(other.creditCode))
			return false;
		if (csgName == null) {
			if (other.csgName != null)
				return false;
		} else if (!csgName.equals(other.csgName))
			return false;
		if (execName == null) {
			if (other.execName != null)
				return false;
		} else if (!execName.equals(other.execName))
			return false;
		if (contUlName == null) {
			if (other.contUlName != null)
				return false;
		} else if (!contUlName.equals(other.contUlName))
			return false;
		if (csgTel == null) {
			if (other.csgTel != null)
				return false;
		} else if (!csgTel.equals(other.csgTel))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (legalPer == null) {
			if (other.legalPer != null)
				return false;
		} else if (!legalPer.equals(other.legalPer))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getGenckTime() {
		return genckTime;
	}

	public void setGenckTime(String genckTime) {
		this.genckTime = genckTime;
	}

	public String getPayPic() {
		return payPic;
	}

	public void setPayPic(String payPic) {
		this.payPic = payPic;
	}

	public String getDmRea() {
		return dmRea;
	}

	public void setDmRea(String dmRea) {
		this.dmRea = dmRea;
	}
}
