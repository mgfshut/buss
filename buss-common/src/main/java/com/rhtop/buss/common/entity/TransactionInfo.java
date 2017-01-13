/**
 *
 */
package com.rhtop.buss.common.entity;

import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 交易信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "TransactionInfo")
public class TransactionInfo {
	private Float ctofPri;//回盘价
	
	private String txStatus;//交易状态
	
	private String clRea;//交易取消原因
	
	private String txAmo;//交易数量
	
	private String updateTime;//修改时间
	
	private String createTime;//创建时间
	
	private Float pcasPri;//客户价
	
	private String categoryId;//品类ID
	
	private String updateUser;//修改人
	
	private String ctofAging;//回盘时效
	
	private String transactionInfoId;//交易ID
	
	private String timingSta;//交易时效
	
	private String pcasTime;//报价时间
	
	private String dealTime;//交易日期
	
	private String customerId;//客户的ID
	
	private String createUser;//创建人
	
	private String endTime;//交易失效时间
	
	private Page page;//分页
	
	private String cusName;//客户姓名
	
	private String custCha;//客户渠道
	
	private String cateName;//品类名
	
	private String manuNum;//厂号
	
	private Category cate;//交易的品类
	
	private Customer cust;//交易的客户
	
	private ContractInfo contract;//交易产生的合同
	
	private List<SlaTransactionInfo>  sla;//回盘记录
	
	private RelCategoryPrice rel;
	
	public ContractInfo getContract() {
		return contract;
	}

	public void setContract(ContractInfo contract) {
		this.contract = contract;
	}

	public List<SlaTransactionInfo> get() {
		return sla;
	}

	public void setSla(List<SlaTransactionInfo> sla ) {
		this.sla  = sla ;
	}

	public Category getCate() {
		return cate;
	}

	public void setCate(Category cate) {
		this.cate = cate;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCustCha() {
		return custCha;
	}

	public void setCustCha(String custCha) {
		this.custCha = custCha;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getManuNum() {
		return manuNum;
	}

	public void setManuNum(String manuNum) {
		this.manuNum = manuNum;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public Float getCtofPri(){
		return this.ctofPri;
	}
	
	public void setCtofPri(Float ctofPri){
		this.ctofPri = ctofPri;
	}
	public String getTxStatus(){
		return this.txStatus;
	}
	
	public void setTxStatus(String txStatus){
		this.txStatus = txStatus;
	}
	public String getTxAmo(){
		return this.txAmo;
	}
	
	public void setTxAmo(String txAmo){
		this.txAmo = txAmo;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public Float getPcasPri(){
		return this.pcasPri;
	}
	
	public void setPcasPri(Float pcasPri){
		this.pcasPri = pcasPri;
	}
	public String getCategoryId(){
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getCtofAging(){
		return this.ctofAging;
	}
	
	public void setCtofAging(String ctofAging){
		this.ctofAging = ctofAging;
	}
	public String getTransactionInfoId(){
		return this.transactionInfoId;
	}
	
	public void setTransactionInfoId(String transactionInfoId){
		this.transactionInfoId = transactionInfoId;
	}
	public String getTimingSta(){
		return this.timingSta;
	}
	
	public void setTimingSta(String timingSta){
		this.timingSta = timingSta;
	}
	public String getPcasTime(){
		return this.pcasTime;
	}
	
	public void setPcasTime(String pcasTime){
		this.pcasTime = pcasTime;
	}
	public String getDealTime(){
		return this.dealTime;
	}
	
	public void setDealTime(String dealTime){
		this.dealTime = dealTime;
	}
	public String getCustomerId(){
		return this.customerId;
	}
	
	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctofPri == null) ? 0 : ctofPri.hashCode());
		result = prime * result + ((txStatus == null) ? 0 : txStatus.hashCode());
		result = prime * result + ((txAmo == null) ? 0 : txAmo.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((pcasPri == null) ? 0 : pcasPri.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((ctofAging == null) ? 0 : ctofAging.hashCode());
		result = prime * result + ((transactionInfoId == null) ? 0 : transactionInfoId.hashCode());
		result = prime * result + ((timingSta == null) ? 0 : timingSta.hashCode());
		result = prime * result + ((pcasTime == null) ? 0 : pcasTime.hashCode());
		result = prime * result + ((dealTime == null) ? 0 : dealTime.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
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
		final TransactionInfo other = (TransactionInfo) obj;
		if (ctofPri == null) {
			if (other.ctofPri != null)
				return false;
		} else if (!ctofPri.equals(other.ctofPri))
			return false;
		if (txStatus == null) {
			if (other.txStatus != null)
				return false;
		} else if (!txStatus.equals(other.txStatus))
			return false;
		if (txAmo == null) {
			if (other.txAmo != null)
				return false;
		} else if (!txAmo.equals(other.txAmo))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (pcasPri == null) {
			if (other.pcasPri != null)
				return false;
		} else if (!pcasPri.equals(other.pcasPri))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (ctofAging == null) {
			if (other.ctofAging != null)
				return false;
		} else if (!ctofAging.equals(other.ctofAging))
			return false;
		if (transactionInfoId == null) {
			if (other.transactionInfoId != null)
				return false;
		} else if (!transactionInfoId.equals(other.transactionInfoId))
			return false;
		if (timingSta == null) {
			if (other.timingSta != null)
				return false;
		} else if (!timingSta.equals(other.timingSta))
			return false;
		if (pcasTime == null) {
			if (other.pcasTime != null)
				return false;
		} else if (!pcasTime.equals(other.pcasTime))
			return false;
		if (dealTime == null) {
			if (other.dealTime != null)
				return false;
		} else if (!dealTime.equals(other.dealTime))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getClRea() {
		return clRea;
	}

	public void setClRea(String clRea) {
		this.clRea = clRea;
	}

	public RelCategoryPrice getRel() {
		return rel;
	}

	public void setRel(RelCategoryPrice rel) {
		this.rel = rel;
	}
}