/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 客户与品类关系表
 * @author
 * @version
 */
//@XmlRootElement(name = "RelCustomerCategory")
public class RelCustomerCategory {
	private String createTime;//创建时间
	
	private String cateScale;//品类规模
	
	private String updateUser;//修改人
	
	private String createUser;//创建人
	
	private String updateTime;//修改时间
	
	private String categoryId;//品类ID
	
	private String relCustomerCategoryId;//客户与品类关系ID
	
	private String cooIntenComm;//合作意向备注
	
	private String cooInten;//合作意向
	
	private String cusLoc;//客户地区
	
	private String customerId;//客户ID
	
	private String cusChaId;//渠道ID
	
	private String cusChaVal;//渠道值
	
	private String mgrCk;//客户经理对价格信息的完善状态 00未完善 02已完善
	
	private String regMgrCk;//分部经理对价格的完善状态  00未完善  02已完善
	
	private Page page;//分页
	
	/**导出用字段 begin**/
	private String cateName;//品类名称
	
	private String cateStan;//规格
	
	private String pkgQuan;//包装
	
	private String manuNum;//厂号
	
	private String prodPla;//产地
	
	private String comm;//备注
	
	private String memberName;//客户经理
	
	private String wholesalePri;//批发价
	
	private String acptPri;//接盘价
	
	private String spotMin;//现货价最小值
	
	private String spotMax;//现货价最大值
	
	private String interFutMin;//半期货价最小值
	
	private String interFutMax;//半期货价最大值
	
	private String futMin;//期货价最小值
	
	private String futMax;//期货价最大值
	
	private String cateSup;//供应商
	
	private Float offerPri;//报盘价(这是经过了单位换算的价格)
	
	private String offerAging;//报盘时效
	/**导出用字段end**/
	
	
	public Page getPage() {
		return page;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCateStan() {
		return cateStan;
	}

	public void setCateStan(String cateStan) {
		this.cateStan = cateStan;
	}

	public String getPkgQuan() {
		return pkgQuan;
	}

	public void setPkgQuan(String pkgQuan) {
		this.pkgQuan = pkgQuan;
	}

	public String getManuNum() {
		return manuNum;
	}

	public void setManuNum(String manuNum) {
		this.manuNum = manuNum;
	}

	public String getProdPla() {
		return prodPla;
	}

	public void setProdPla(String prodPla) {
		this.prodPla = prodPla;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getWholesalePri() {
		return wholesalePri;
	}

	public void setWholesalePri(String wholesalePri) {
		this.wholesalePri = wholesalePri;
	}

	public String getAcptPri() {
		return acptPri;
	}

	public void setAcptPri(String acptPri) {
		this.acptPri = acptPri;
	}

	public String getSpotMin() {
		return spotMin;
	}

	public void setSpotMin(String spotMin) {
		this.spotMin = spotMin;
	}

	public String getSpotMax() {
		return spotMax;
	}

	public void setSpotMax(String spotMax) {
		this.spotMax = spotMax;
	}

	public String getInterFutMin() {
		return interFutMin;
	}

	public void setInterFutMin(String interFutMin) {
		this.interFutMin = interFutMin;
	}

	public String getInterFutMax() {
		return interFutMax;
	}

	public void setInterFutMax(String interFutMax) {
		this.interFutMax = interFutMax;
	}

	public String getFutMin() {
		return futMin;
	}

	public void setFutMin(String futMin) {
		this.futMin = futMin;
	}

	public String getFutMax() {
		return futMax;
	}

	public void setFutMax(String futMax) {
		this.futMax = futMax;
	}

	public String getCateSup() {
		return cateSup;
	}

	public void setCateSup(String cateSup) {
		this.cateSup = cateSup;
	}

	public Float getOfferPri() {
		return offerPri;
	}

	public void setOfferPri(Float offerPri) {
		this.offerPri = offerPri;
	}

	public String getOfferAging() {
		return offerAging;
	}

	public void setOfferAging(String offerAging) {
		this.offerAging = offerAging;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCateScale(){
		return this.cateScale;
	}
	
	public void setCateScale(String cateScale){
		this.cateScale = cateScale;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCategoryId(){
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	public String getRelCustomerCategoryId(){
		return this.relCustomerCategoryId;
	}
	
	public void setRelCustomerCategoryId(String relCustomerCategoryId){
		this.relCustomerCategoryId = relCustomerCategoryId;
	}
	public String getCooIntenComm(){
		return this.cooIntenComm;
	}
	
	public void setCooIntenComm(String cooIntenComm){
		this.cooIntenComm = cooIntenComm;
	}
	public String getCooInten(){
		return this.cooInten;
	}
	
	public void setCooInten(String cooInten){
		this.cooInten = cooInten;
	}
	public String getCusLoc(){
		return this.cusLoc;
	}
	
	public void setCusLoc(String cusLoc){
		this.cusLoc = cusLoc;
	}
	public String getCustomerId(){
		return this.customerId;
	}
	
	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((cateScale == null) ? 0 : cateScale.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((relCustomerCategoryId == null) ? 0 : relCustomerCategoryId.hashCode());
		result = prime * result + ((cooIntenComm == null) ? 0 : cooIntenComm.hashCode());
		result = prime * result + ((cooInten == null) ? 0 : cooInten.hashCode());
		result = prime * result + ((cusLoc == null) ? 0 : cusLoc.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
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
		final RelCustomerCategory other = (RelCustomerCategory) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (cateScale == null) {
			if (other.cateScale != null)
				return false;
		} else if (!cateScale.equals(other.cateScale))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (relCustomerCategoryId == null) {
			if (other.relCustomerCategoryId != null)
				return false;
		} else if (!relCustomerCategoryId.equals(other.relCustomerCategoryId))
			return false;
		if (cooIntenComm == null) {
			if (other.cooIntenComm != null)
				return false;
		} else if (!cooIntenComm.equals(other.cooIntenComm))
			return false;
		if (cooInten == null) {
			if (other.cooInten != null)
				return false;
		} else if (!cooInten.equals(other.cooInten))
			return false;
		if (cusLoc == null) {
			if (other.cusLoc != null)
				return false;
		} else if (!cusLoc.equals(other.cusLoc))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}

	public String getCusChaVal() {
		return cusChaVal;
	}

	public void setCusChaVal(String cusChaVal) {
		this.cusChaVal = cusChaVal;
	}

	public String getCusChaId() {
		return cusChaId;
	}

	public void setCusChaId(String cusChaId) {
		this.cusChaId = cusChaId;
	}

	public String getMgrCk() {
		return mgrCk;
	}

	public void setMgrCk(String mgrCk) {
		this.mgrCk = mgrCk;
	}

	public String getRegMgrCk() {
		return regMgrCk;
	}

	public void setRegMgrCk(String regMgrCk) {
		this.regMgrCk = regMgrCk;
	}
}