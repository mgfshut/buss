/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 用户角色关系表
 * @author
 * @version
 */
//@XmlRootElement(name = "RsUserRole")
public class RsUserRole {
	private String rsUserRoleId;//用户角色关系ID
	
	private String memberId;//用户ID
	
	private String roleId;//角色ID
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getRsUserRoleId(){
		return this.rsUserRoleId;
	}
	
	public void setRsUserRoleId(String rsUserRoleId){
		this.rsUserRoleId = rsUserRoleId;
	}
	public String getMemberId(){
		return this.memberId;
	}
	
	public void setMemberId(String memberId){
		this.memberId = memberId;
	}
	public String getRoleId(){
		return this.roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rsUserRoleId == null) ? 0 : rsUserRoleId.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		final RsUserRole other = (RsUserRole) obj;
		if (rsUserRoleId == null) {
			if (other.rsUserRoleId != null)
				return false;
		} else if (!rsUserRoleId.equals(other.rsUserRoleId))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}