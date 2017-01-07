/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 品类信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "Category")
public class Category {
	private String updateTime;//修改时间
	
	private String comm;//备注信息
	
	private String cateName;//品类名称
	
	private String createTime;//创建时间
	
	private String manuNum;//厂号
	
	private String categoryId;//品类ID
	
	private String createUser;//创建人
	
	private String offerAging;//报盘时效（品类表和价格关系表中的公用字段）
	
	private String offerPri;//报盘价(这是经过了单位换算的价格)
	
	private String prodPla;//产地
	
	private String cateStan;//品类规格
	
	private String pkgQuan;//包装数量
	
	private String catePic;//品类图片
	
	private String updateUser;//修改人
	
	private String cateScale;//品类规模
	
	private String cooInten;//合作意向
	
	private String cooIntenComm;//合作意向备注
	
	private String cateSup;//品类供应商（品类价格关系表中的字段）
	
	private String currency;//货币币种（品类价格关系表中的字段）
	
	private String unit;//计量单位（品类价格关系表中的字段）
	
	private String catePri;//品类价格（未经过单位换算的价格）（品类价格关系表中的字段）
	
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
	public String getComm(){
		return this.comm;
	}
	
	public void setComm(String comm){
		this.comm = comm;
	}
	public String getCateName(){
		return this.cateName;
	}
	
	public void setCateName(String cateName){
		this.cateName = cateName;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getManuNum(){
		return this.manuNum;
	}
	
	public void setManuNum(String manuNum){
		this.manuNum = manuNum;
	}
	public String getCategoryId(){
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getOfferAging(){
		return this.offerAging;
	}
	
	public void setOfferAging(String offerAging){
		this.offerAging = offerAging;
	}
	public String getOfferPri(){
		return this.offerPri;
	}
	
	public void setOfferPri(String offerPri){
		this.offerPri = offerPri;
	}
	public String getProdPla(){
		return this.prodPla;
	}
	
	public void setProdPla(String prodPla){
		this.prodPla = prodPla;
	}
	public String getCateStan(){
		return this.cateStan;
	}
	
	public void setCateStan(String cateStan){
		this.cateStan = cateStan;
	}
	public String getPkgQuan(){
		return this.pkgQuan;
	}
	
	public void setPkgQuan(String pkgQuan){
		this.pkgQuan = pkgQuan;
	}
	public String getCatePic(){
		return this.catePic;
	}
	
	public void setCatePic(String catePic){
		this.catePic = catePic;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((comm == null) ? 0 : comm.hashCode());
		result = prime * result + ((cateName == null) ? 0 : cateName.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((manuNum == null) ? 0 : manuNum.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((offerAging == null) ? 0 : offerAging.hashCode());
		result = prime * result + ((offerPri == null) ? 0 : offerPri.hashCode());
		result = prime * result + ((prodPla == null) ? 0 : prodPla.hashCode());
		result = prime * result + ((cateStan == null) ? 0 : cateStan.hashCode());
		result = prime * result + ((pkgQuan == null) ? 0 : pkgQuan.hashCode());
		result = prime * result + ((catePic == null) ? 0 : catePic.hashCode());
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
		final Category other = (Category) obj;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (comm == null) {
			if (other.comm != null)
				return false;
		} else if (!comm.equals(other.comm))
			return false;
		if (cateName == null) {
			if (other.cateName != null)
				return false;
		} else if (!cateName.equals(other.cateName))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (manuNum == null) {
			if (other.manuNum != null)
				return false;
		} else if (!manuNum.equals(other.manuNum))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (offerAging == null) {
			if (other.offerAging != null)
				return false;
		} else if (!offerAging.equals(other.offerAging))
			return false;
		if (offerPri == null) {
			if (other.offerPri != null)
				return false;
		} else if (!offerPri.equals(other.offerPri))
			return false;
		if (prodPla == null) {
			if (other.prodPla != null)
				return false;
		} else if (!prodPla.equals(other.prodPla))
			return false;
		if (cateStan == null) {
			if (other.cateStan != null)
				return false;
		} else if (!cateStan.equals(other.cateStan))
			return false;
		if (pkgQuan == null) {
			if (other.pkgQuan != null)
				return false;
		} else if (!pkgQuan.equals(other.pkgQuan))
			return false;
		if (catePic == null) {
			if (other.catePic != null)
				return false;
		} else if (!catePic.equals(other.catePic))
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

	public String getCateScale() {
		return cateScale;
	}

	public void setCateScale(String cateScale) {
		this.cateScale = cateScale;
	}

	public String getCooInten() {
		return cooInten;
	}

	public void setCooInten(String cooInten) {
		this.cooInten = cooInten;
	}

	public String getCooIntenComm() {
		return cooIntenComm;
	}

	public void setCooIntenComm(String cooIntenComm) {
		this.cooIntenComm = cooIntenComm;
	}

	public String getCateSup() {
		return cateSup;
	}

	public void setCateSup(String cateSup) {
		this.cateSup = cateSup;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCatePri() {
		return catePri;
	}

	public void setCatePri(String catePri) {
		this.catePri = catePri;
	}
}