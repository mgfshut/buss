/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 机构
 * @author
 * @version
 */
//@XmlRootElement(name = "Dept")
public class Dept {
	private String createTime;//创建时间
	
	private String deptCity;//所在城市
	
	private String updateTime;//修改时间
	
	private String deptType;//机构类型
	
	private String updateUser;//修改人
	
	private String createUser;//创建人
	
	private String parentDept;//上级机构
	
	private String deptId;//机构ID
	
	private String deptName;//机构名称
	
	private String deptCode;//机构编码
	
	private Page page;//分页
	
	private String parentDeptName;//上级机构名称
	
	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	public Page getPage() {
		return page;
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
	public String getDeptCity(){
		return this.deptCity;
	}
	
	public void setDeptCity(String deptCity){
		this.deptCity = deptCity;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getDeptType(){
		return this.deptType;
	}
	
	public void setDeptType(String deptType){
		this.deptType = deptType;
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
	public String getParentDept(){
		return this.parentDept;
	}
	
	public void setParentDept(String parentDept){
		this.parentDept = parentDept;
	}
	public String getDeptId(){
		return this.deptId;
	}
	
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}
	public String getDeptName(){
		return this.deptName;
	}
	
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}
	public String getDeptCode(){
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode){
		this.deptCode = deptCode;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((deptCity == null) ? 0 : deptCity.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((deptType == null) ? 0 : deptType.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((parentDept == null) ? 0 : parentDept.hashCode());
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result + ((deptCode == null) ? 0 : deptCode.hashCode());
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
		final Dept other = (Dept) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (deptCity == null) {
			if (other.deptCity != null)
				return false;
		} else if (!deptCity.equals(other.deptCity))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (deptType == null) {
			if (other.deptType != null)
				return false;
		} else if (!deptType.equals(other.deptType))
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
		if (parentDept == null) {
			if (other.parentDept != null)
				return false;
		} else if (!parentDept.equals(other.parentDept))
			return false;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}