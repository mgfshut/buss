/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 公司
 * @author
 * @version
 */
//@XmlRootElement(name = "Company")
public class Company {
	private String createUser;//创建人
	
	private String parentCompany;//上级公司
	
	private String updateTime;//修改时间
	
	private String companyName;//公司名称
	
	private String companyCode;//公司编码
	
	private String updateUser;//修改人
	
	private String companyStatus;//公司状态
	
	private String createTime;//创建时间
	
	private String companyCity;//所在城市
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getParentCompany(){
		return this.parentCompany;
	}
	
	public void setParentCompany(String parentCompany){
		this.parentCompany = parentCompany;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCompanyName(){
		return this.companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	public String getCompanyCode(){
		return this.companyCode;
	}
	
	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getCompanyStatus(){
		return this.companyStatus;
	}
	
	public void setCompanyStatus(String companyStatus){
		this.companyStatus = companyStatus;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCompanyCity(){
		return this.companyCity;
	}
	
	public void setCompanyCity(String companyCity){
		this.companyCity = companyCity;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((parentCompany == null) ? 0 : parentCompany.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((companyStatus == null) ? 0 : companyStatus.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((companyCity == null) ? 0 : companyCity.hashCode());
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
		final Company other = (Company) obj;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (parentCompany == null) {
			if (other.parentCompany != null)
				return false;
		} else if (!parentCompany.equals(other.parentCompany))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (companyStatus == null) {
			if (other.companyStatus != null)
				return false;
		} else if (!companyStatus.equals(other.companyStatus))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (companyCity == null) {
			if (other.companyCity != null)
				return false;
		} else if (!companyCity.equals(other.companyCity))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}