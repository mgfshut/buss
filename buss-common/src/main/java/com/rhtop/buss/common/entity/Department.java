/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 部门
 * @author
 * @version
 */
//@XmlRootElement(name = "Department")
public class Department {
	private String parentDepartment;//上级部门
	
	private String updateTime;//修改时间
	
	private String describe;//部门描述
	
	private String updateUser;//修改人
	
	private String companyCode;//公司编码
	
	private String departmentCode;//部门编码
	
	private String createTime;//创建时间
	
	private String createUser;//创建人
	
	private String departmentName;//部门名称
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getParentDepartment(){
		return this.parentDepartment;
	}
	
	public void setParentDepartment(String parentDepartment){
		this.parentDepartment = parentDepartment;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getDescribe(){
		return this.describe;
	}
	
	public void setDescribe(String describe){
		this.describe = describe;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getCompanyCode(){
		return this.companyCode;
	}
	
	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}
	public String getDepartmentCode(){
		return this.departmentCode;
	}
	
	public void setDepartmentCode(String departmentCode){
		this.departmentCode = departmentCode;
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
	public String getDepartmentName(){
		return this.departmentName;
	}
	
	public void setDepartmentName(String departmentName){
		this.departmentName = departmentName;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentDepartment == null) ? 0 : parentDepartment.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((describe == null) ? 0 : describe.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((departmentCode == null) ? 0 : departmentCode.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
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
		final Department other = (Department) obj;
		if (parentDepartment == null) {
			if (other.parentDepartment != null)
				return false;
		} else if (!parentDepartment.equals(other.parentDepartment))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (describe == null) {
			if (other.describe != null)
				return false;
		} else if (!describe.equals(other.describe))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (departmentCode == null) {
			if (other.departmentCode != null)
				return false;
		} else if (!departmentCode.equals(other.departmentCode))
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
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}