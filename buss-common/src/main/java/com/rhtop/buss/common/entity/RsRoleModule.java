/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 角色模块菜单关系表
 * @author
 * @version
 */
//@XmlRootElement(name = "RsRoleModule")
public class RsRoleModule {
	private String roleId;//角色ID
	
	private String moduleId;//模块功能ID
	
	private String rsRoleModuleId;//角色模块菜单关系ID
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getRoleId(){
		return this.roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public String getModuleId(){
		return this.moduleId;
	}
	
	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}
	public String getRsRoleModuleId(){
		return this.rsRoleModuleId;
	}
	
	public void setRsRoleModuleId(String rsRoleModuleId){
		this.rsRoleModuleId = rsRoleModuleId;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((rsRoleModuleId == null) ? 0 : rsRoleModuleId.hashCode());
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
		final RsRoleModule other = (RsRoleModule) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (rsRoleModuleId == null) {
			if (other.rsRoleModuleId != null)
				return false;
		} else if (!rsRoleModuleId.equals(other.rsRoleModuleId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}