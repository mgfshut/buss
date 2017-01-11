/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 交易记录从表
 * @author
 * @version
 */
//@XmlRootElement(name = "SlaTransactionInfo")
public class SlaTransactionInfo {
	private String updateTime;//修改时间
	
	private String createUser;//创建人
	
	private String ctofTime;//回盘时间
	
	private String cusAplSta;//客户确认状态
	
	private String transactionInfoId;//交易ID
	
	private String updateUser;//修改人
	
	private String pcasTime;//报价时间
	
	private String ctofCkSta;//回盘审核状态
	
	private String ctofCkTime;//回盘审核时间
	
	private Float domCtofPri;//决委会回盘价
	
	private String txAmo;//交易数量
	
	private String ctofPerId;//回盘人
	
	private String ctofAging;//回盘时效
	
	private Float pcasPri;//客户价
	
	private String createTime;//创建时间
	
	private String slaTransactionInfoId;//询盘ID
	
	private String ctofCkPer;//回盘审核人
	
	private Float uniCtofPri;//国际部回盘价
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getCtofTime(){
		return this.ctofTime;
	}
	
	public void setCtofTime(String ctofTime){
		this.ctofTime = ctofTime;
	}
	public String getCusAplSta(){
		return this.cusAplSta;
	}
	
	public void setCusAplSta(String cusAplSta){
		this.cusAplSta = cusAplSta;
	}
	public String getTransactionInfoId(){
		return this.transactionInfoId;
	}
	
	public void setTransactionInfoId(String transactionInfoId){
		this.transactionInfoId = transactionInfoId;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getPcasTime(){
		return this.pcasTime;
	}
	
	public void setPcasTime(String pcasTime){
		this.pcasTime = pcasTime;
	}
	public String getCtofCkSta(){
		return this.ctofCkSta;
	}
	
	public void setCtofCkSta(String ctofCkSta){
		this.ctofCkSta = ctofCkSta;
	}
	public String getCtofCkTime(){
		return this.ctofCkTime;
	}
	
	public void setCtofCkTime(String ctofCkTime){
		this.ctofCkTime = ctofCkTime;
	}
	public Float getDomCtofPri(){
		return this.domCtofPri;
	}
	
	public void setDomCtofPri(Float domCtofPri){
		this.domCtofPri = domCtofPri;
	}
	public String getTxAmo(){
		return this.txAmo;
	}
	
	public void setTxAmo(String txAmo){
		this.txAmo = txAmo;
	}
	public String getCtofPerId(){
		return this.ctofPerId;
	}
	
	public void setCtofPerId(String ctofPerId){
		this.ctofPerId = ctofPerId;
	}
	public String getCtofAging(){
		return this.ctofAging;
	}
	
	public void setCtofAging(String ctofAging){
		this.ctofAging = ctofAging;
	}
	public Float getPcasPri(){
		return this.pcasPri;
	}
	
	public void setPcasPri(Float pcasPri){
		this.pcasPri = pcasPri;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getSlaTransactionInfoId(){
		return this.slaTransactionInfoId;
	}
	
	public void setSlaTransactionInfoId(String slaTransactionInfoId){
		this.slaTransactionInfoId = slaTransactionInfoId;
	}
	public String getCtofCkPer(){
		return this.ctofCkPer;
	}
	
	public void setCtofCkPer(String ctofCkPer){
		this.ctofCkPer = ctofCkPer;
	}
	public Float getUniCtofPri(){
		return this.uniCtofPri;
	}
	
	public void setUniCtofPri(Float uniCtofPri){
		this.uniCtofPri = uniCtofPri;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((ctofTime == null) ? 0 : ctofTime.hashCode());
		result = prime * result + ((cusAplSta == null) ? 0 : cusAplSta.hashCode());
		result = prime * result + ((transactionInfoId == null) ? 0 : transactionInfoId.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((pcasTime == null) ? 0 : pcasTime.hashCode());
		result = prime * result + ((ctofCkSta == null) ? 0 : ctofCkSta.hashCode());
		result = prime * result + ((ctofCkTime == null) ? 0 : ctofCkTime.hashCode());
		result = prime * result + ((domCtofPri == null) ? 0 : domCtofPri.hashCode());
		result = prime * result + ((txAmo == null) ? 0 : txAmo.hashCode());
		result = prime * result + ((ctofPerId == null) ? 0 : ctofPerId.hashCode());
		result = prime * result + ((ctofAging == null) ? 0 : ctofAging.hashCode());
		result = prime * result + ((pcasPri == null) ? 0 : pcasPri.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((slaTransactionInfoId == null) ? 0 : slaTransactionInfoId.hashCode());
		result = prime * result + ((ctofCkPer == null) ? 0 : ctofCkPer.hashCode());
		result = prime * result + ((uniCtofPri == null) ? 0 : uniCtofPri.hashCode());
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
		final SlaTransactionInfo other = (SlaTransactionInfo) obj;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (ctofTime == null) {
			if (other.ctofTime != null)
				return false;
		} else if (!ctofTime.equals(other.ctofTime))
			return false;
		if (cusAplSta == null) {
			if (other.cusAplSta != null)
				return false;
		} else if (!cusAplSta.equals(other.cusAplSta))
			return false;
		if (transactionInfoId == null) {
			if (other.transactionInfoId != null)
				return false;
		} else if (!transactionInfoId.equals(other.transactionInfoId))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (pcasTime == null) {
			if (other.pcasTime != null)
				return false;
		} else if (!pcasTime.equals(other.pcasTime))
			return false;
		if (ctofCkSta == null) {
			if (other.ctofCkSta != null)
				return false;
		} else if (!ctofCkSta.equals(other.ctofCkSta))
			return false;
		if (ctofCkTime == null) {
			if (other.ctofCkTime != null)
				return false;
		} else if (!ctofCkTime.equals(other.ctofCkTime))
			return false;
		if (domCtofPri == null) {
			if (other.domCtofPri != null)
				return false;
		} else if (!domCtofPri.equals(other.domCtofPri))
			return false;
		if (txAmo == null) {
			if (other.txAmo != null)
				return false;
		} else if (!txAmo.equals(other.txAmo))
			return false;
		if (ctofPerId == null) {
			if (other.ctofPerId != null)
				return false;
		} else if (!ctofPerId.equals(other.ctofPerId))
			return false;
		if (ctofAging == null) {
			if (other.ctofAging != null)
				return false;
		} else if (!ctofAging.equals(other.ctofAging))
			return false;
		if (pcasPri == null) {
			if (other.pcasPri != null)
				return false;
		} else if (!pcasPri.equals(other.pcasPri))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (slaTransactionInfoId == null) {
			if (other.slaTransactionInfoId != null)
				return false;
		} else if (!slaTransactionInfoId.equals(other.slaTransactionInfoId))
			return false;
		if (ctofCkPer == null) {
			if (other.ctofCkPer != null)
				return false;
		} else if (!ctofCkPer.equals(other.ctofCkPer))
			return false;
		if (uniCtofPri == null) {
			if (other.uniCtofPri != null)
				return false;
		} else if (!uniCtofPri.equals(other.uniCtofPri))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}