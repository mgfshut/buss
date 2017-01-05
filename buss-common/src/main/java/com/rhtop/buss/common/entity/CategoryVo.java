package com.rhtop.buss.common.entity;

public class CategoryVo extends Category {
	private String cateScale;
	private String cooInten;
	private String cooIntenComm;
	
	public CategoryVo() {
		super();
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

	@Override
	public String toString() {
		return "CategoryVo [cateScale=" + cateScale + ", cooInten=" + cooInten
				+ ", cooIntenComm=" + cooIntenComm + ", getPage()=" + getPage()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getComm()="
				+ getComm() + ", getCateName()=" + getCateName()
				+ ", getCreateTime()=" + getCreateTime() + ", getManuNum()="
				+ getManuNum() + ", getCategoryId()=" + getCategoryId()
				+ ", getCreateUser()=" + getCreateUser() + ", getOfferAging()="
				+ getOfferAging() + ", getOfferPri()=" + getOfferPri()
				+ ", getProdPla()=" + getProdPla() + ", getCateStan()="
				+ getCateStan() + ", getPkgQuan()=" + getPkgQuan()
				+ ", getCatePic()=" + getCatePic() + ", getUpdateUser()="
				+ getUpdateUser() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + "]";
	}

	
	
}
