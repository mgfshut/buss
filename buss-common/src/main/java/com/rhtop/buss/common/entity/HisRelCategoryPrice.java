/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.biwnd.annotation.XmlRootElement;

/**
 * 实体: 品类与价格关系的历史记录表
 * @author
 * @version
 */
//@XmlRootElement(name = "HisRelCategoryPrice")
public class HisRelCategoryPrice {
	private String hisRelCategoryPriceId;//记录表的主键
	
	private String mgrId;//客户经理
	
	private Float wholesalePri;//批发价
	
	private String unit;//计量单位
	
	private Float interFutMax;//半期货价最大值
	
	private String flgUpdateTime;//价格修改时间
	
	private String currency;//货币币种
	
	private String relCategoryPriceId;//关系记录ID
	
	private String updateTime;//修改时间
	
	private String offerAging;//报盘时效
	
	
	private Float spotMin;//现货价最小值
	
	private String createTime;//创建时间
	
	private String createUser;//创建人
	
	private String regMgrId;//分部经理
	
	private String updateUser;//修改人
	
	private String midUpdateTime;//中间价更新时间
	
	private Float acptPri;//接盘价
	
	private Float futMin;//期货价最小值
	
	private Float spotMax;//现货价最大值
	
	private String mgrLoc;//客户经理地区
	
	private Float futMax;//期货价最大值
	
	private String categoryId;//品类
	
	private Float interFutMin;//半期货价最小值
	
	private String cateSup;//供应商
	
	private String uniMgrId;//国际部人员
	
	private String offerUpdateTime;//报盘价更新时间
	
	private Float catePri;//品类价格
	
	private String cusChaVal;//渠道值
	
	private Page page;//分页

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getMgrId(){
		return this.mgrId;
	}
	
	public void setMgrId(String mgrId){
		this.mgrId = mgrId;
	}
	public Float getWholesalePri(){
		return this.wholesalePri;
	}
	
	public void setWholesalePri(Float wholesalePri){
		this.wholesalePri = wholesalePri;
	}
	public String getUnit(){
		return this.unit;
	}
	
	public void setUnit(String unit){
		this.unit = unit;
	}
	public Float getInterFutMax(){
		return this.interFutMax;
	}
	
	public void setInterFutMax(Float interFutMax){
		this.interFutMax = interFutMax;
	}
	public String getFlgUpdateTime(){
		return this.flgUpdateTime;
	}
	
	public void setFlgUpdateTime(String flgUpdateTime){
		this.flgUpdateTime = flgUpdateTime;
	}
	public String getCurrency(){
		return this.currency;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getRelCategoryPriceId(){
		return this.relCategoryPriceId;
	}
	
	public void setRelCategoryPriceId(String relCategoryPriceId){
		this.relCategoryPriceId = relCategoryPriceId;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getOfferAging(){
		return this.offerAging;
	}
	
	public void setOfferAging(String offerAging){
		this.offerAging = offerAging;
	}
	public Float getSpotMin(){
		return this.spotMin;
	}
	
	public void setSpotMin(Float spotMin){
		this.spotMin = spotMin;
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
	public String getRegMgrId(){
		return this.regMgrId;
	}
	
	public void setRegMgrId(String regMgrId){
		this.regMgrId = regMgrId;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getMidUpdateTime(){
		return this.midUpdateTime;
	}
	
	public void setMidUpdateTime(String midUpdateTime){
		this.midUpdateTime = midUpdateTime;
	}
	public Float getAcptPri(){
		return this.acptPri;
	}
	
	public void setAcptPri(Float acptPri){
		this.acptPri = acptPri;
	}
	public Float getFutMin(){
		return this.futMin;
	}
	
	public void setFutMin(Float futMin){
		this.futMin = futMin;
	}
	public Float getSpotMax(){
		return this.spotMax;
	}
	
	public void setSpotMax(Float spotMax){
		this.spotMax = spotMax;
	}
	public String getMgrLoc(){
		return this.mgrLoc;
	}
	
	public void setMgrLoc(String mgrLoc){
		this.mgrLoc = mgrLoc;
	}
	public Float getFutMax(){
		return this.futMax;
	}
	
	public void setFutMax(Float futMax){
		this.futMax = futMax;
	}
	public String getCategoryId(){
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	public Float getInterFutMin(){
		return this.interFutMin;
	}
	
	public void setInterFutMin(Float interFutMin){
		this.interFutMin = interFutMin;
	}
	public String getCateSup(){
		return this.cateSup;
	}
	
	public void setCateSup(String cateSup){
		this.cateSup = cateSup;
	}
	public String getUniMgrId(){
		return this.uniMgrId;
	}
	
	public void setUniMgrId(String uniMgrId){
		this.uniMgrId = uniMgrId;
	}
	public String getOfferUpdateTime(){
		return this.offerUpdateTime;
	}
	
	public void setOfferUpdateTime(String offerUpdateTime){
		this.offerUpdateTime = offerUpdateTime;
	}
	public Float getCatePri(){
		return this.catePri;
	}
	
	public void setCatePri(Float catePri){
		this.catePri = catePri;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mgrId == null) ? 0 : mgrId.hashCode());
		result = prime * result + ((wholesalePri == null) ? 0 : wholesalePri.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((interFutMax == null) ? 0 : interFutMax.hashCode());
		result = prime * result + ((flgUpdateTime == null) ? 0 : flgUpdateTime.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((relCategoryPriceId == null) ? 0 : relCategoryPriceId.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((offerAging == null) ? 0 : offerAging.hashCode());
		result = prime * result + ((spotMin == null) ? 0 : spotMin.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((regMgrId == null) ? 0 : regMgrId.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((midUpdateTime == null) ? 0 : midUpdateTime.hashCode());
		result = prime * result + ((acptPri == null) ? 0 : acptPri.hashCode());
		result = prime * result + ((futMin == null) ? 0 : futMin.hashCode());
		result = prime * result + ((spotMax == null) ? 0 : spotMax.hashCode());
		result = prime * result + ((mgrLoc == null) ? 0 : mgrLoc.hashCode());
		result = prime * result + ((futMax == null) ? 0 : futMax.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((interFutMin == null) ? 0 : interFutMin.hashCode());
		result = prime * result + ((cateSup == null) ? 0 : cateSup.hashCode());
		result = prime * result + ((uniMgrId == null) ? 0 : uniMgrId.hashCode());
		result = prime * result + ((offerUpdateTime == null) ? 0 : offerUpdateTime.hashCode());
		result = prime * result + ((catePri == null) ? 0 : catePri.hashCode());
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
		final HisRelCategoryPrice other = (HisRelCategoryPrice) obj;
		if (mgrId == null) {
			if (other.mgrId != null)
				return false;
		} else if (!mgrId.equals(other.mgrId))
			return false;
		if (wholesalePri == null) {
			if (other.wholesalePri != null)
				return false;
		} else if (!wholesalePri.equals(other.wholesalePri))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (interFutMax == null) {
			if (other.interFutMax != null)
				return false;
		} else if (!interFutMax.equals(other.interFutMax))
			return false;
		if (flgUpdateTime == null) {
			if (other.flgUpdateTime != null)
				return false;
		} else if (!flgUpdateTime.equals(other.flgUpdateTime))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (relCategoryPriceId == null) {
			if (other.relCategoryPriceId != null)
				return false;
		} else if (!relCategoryPriceId.equals(other.relCategoryPriceId))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (offerAging == null) {
			if (other.offerAging != null)
				return false;
		} else if (!offerAging.equals(other.offerAging))
			return false;
		if (spotMin == null) {
			if (other.spotMin != null)
				return false;
		} else if (!spotMin.equals(other.spotMin))
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
		if (regMgrId == null) {
			if (other.regMgrId != null)
				return false;
		} else if (!regMgrId.equals(other.regMgrId))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (midUpdateTime == null) {
			if (other.midUpdateTime != null)
				return false;
		} else if (!midUpdateTime.equals(other.midUpdateTime))
			return false;
		if (acptPri == null) {
			if (other.acptPri != null)
				return false;
		} else if (!acptPri.equals(other.acptPri))
			return false;
		if (futMin == null) {
			if (other.futMin != null)
				return false;
		} else if (!futMin.equals(other.futMin))
			return false;
		if (spotMax == null) {
			if (other.spotMax != null)
				return false;
		} else if (!spotMax.equals(other.spotMax))
			return false;
		if (mgrLoc == null) {
			if (other.mgrLoc != null)
				return false;
		} else if (!mgrLoc.equals(other.mgrLoc))
			return false;
		if (futMax == null) {
			if (other.futMax != null)
				return false;
		} else if (!futMax.equals(other.futMax))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (interFutMin == null) {
			if (other.interFutMin != null)
				return false;
		} else if (!interFutMin.equals(other.interFutMin))
			return false;
		if (cateSup == null) {
			if (other.cateSup != null)
				return false;
		} else if (!cateSup.equals(other.cateSup))
			return false;
		if (uniMgrId == null) {
			if (other.uniMgrId != null)
				return false;
		} else if (!uniMgrId.equals(other.uniMgrId))
			return false;
		if (offerUpdateTime == null) {
			if (other.offerUpdateTime != null)
				return false;
		} else if (!offerUpdateTime.equals(other.offerUpdateTime))
			return false;
		if (catePri == null) {
			if (other.catePri != null)
				return false;
		} else if (!catePri.equals(other.catePri))
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

	public String getHisRelCategoryPriceId() {
		return hisRelCategoryPriceId;
	}

	public void setHisRelCategoryPriceId(String hisRelCategoryPriceId) {
		this.hisRelCategoryPriceId = hisRelCategoryPriceId;
	}

}