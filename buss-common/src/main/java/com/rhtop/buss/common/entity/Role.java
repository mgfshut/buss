/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 角色信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "Role")
public class Role {
	private String updateUser;//修改人
	
	private String roleDescribe;//角色描述
	
	private String updateTime;//修改时间
	
	private String createTime;//创建时间
	
	private String roleStatus;//角色状态
	
	private String roleName;//角色名称
	
	private String roleId;//角色ID
	
	private String createUser;//创建人
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getRoleDescribe(){
		return this.roleDescribe;
	}
	
	public void setRoleDescribe(String roleDescribe){
		this.roleDescribe = roleDescribe;
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
	public String getRoleStatus(){
		return this.roleStatus;
	}
	
	public void setRoleStatus(String roleStatus){
		this.roleStatus = roleStatus;
	}
	public String getRoleName(){
		return this.roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	public String getRoleId(){
		return this.roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
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
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((roleDescribe == null) ? 0 : roleDescribe.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((roleStatus == null) ? 0 : roleStatus.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		final Role other = (Role) obj;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (roleDescribe == null) {
			if (other.roleDescribe != null)
				return false;
		} else if (!roleDescribe.equals(other.roleDescribe))
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
		if (roleStatus == null) {
			if (other.roleStatus != null)
				return false;
		} else if (!roleStatus.equals(other.roleStatus))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
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
}