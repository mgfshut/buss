/**
 *
 */
package com.rhtop.buss.common.entity;

import com.rhtop.buss.common.model.BaseEntity;
import com.rhtop.buss.common.model.TreeProp;
import com.rhtop.buss.common.model.TreeProp.TreePropType;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 模块菜单表
 * @author
 * @version
 */
//@XmlRootElement(name = "Module")
public class Module extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String moduleType;//类型
	
	private String updateUser;//修改人
	
	private String moduleName;//名称
	
	private String parentId;//父级ID
	
	private String authName;//授权名称
	
	private String updateTime;//修改时间
	
	private String menuUrl;//菜单地址
	
	private String moduleDescribe;//描述
	
	private String createTime;//创建时间
	
	private String menuOrder;//菜单序号
	
	private String createUser;//创建人
	
	private String moduleId;//模块功能ID
	
	private String moduleNo;//编号
	
	private String menuIcon;//菜单图标
	
	private Page page;//分页

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getModuleType(){
		return this.moduleType;
	}
	
	public void setModuleType(String moduleType){
		this.moduleType = moduleType;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	@TreeProp(TreePropType.TEXT)
	public String getModuleName(){
		return this.moduleName;
	}
	
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	@TreeProp(TreePropType.PARENT)
	public String getParentId(){
		return this.parentId;
	}
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	@TreeProp(TreePropType.CODE)
	public String getAuthName(){
		return this.authName;
	}
	
	public void setAuthName(String authName){
		this.authName = authName;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getMenuUrl(){
		return this.menuUrl;
	}
	
	public void setMenuUrl(String menuUrl){
		this.menuUrl = menuUrl;
	}
	public String getModuleDescribe(){
		return this.moduleDescribe;
	}
	
	public void setModuleDescribe(String moduleDescribe){
		this.moduleDescribe = moduleDescribe;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getMenuOrder(){
		return this.menuOrder;
	}
	
	public void setMenuOrder(String menuOrder){
		this.menuOrder = menuOrder;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	@TreeProp(TreePropType.ID)
	public String getModuleId(){
		return this.moduleId;
	}
	
	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}
	public String getModuleNo(){
		return this.moduleNo;
	}
	
	public void setModuleNo(String moduleNo){
		this.moduleNo = moduleNo;
	}
	public String getMenuIcon(){
		return this.menuIcon;
	}
	
	public void setMenuIcon(String menuIcon){
		this.menuIcon = menuIcon;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleType == null) ? 0 : moduleType.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((authName == null) ? 0 : authName.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((menuUrl == null) ? 0 : menuUrl.hashCode());
		result = prime * result + ((moduleDescribe == null) ? 0 : moduleDescribe.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((menuOrder == null) ? 0 : menuOrder.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((moduleNo == null) ? 0 : moduleNo.hashCode());
		result = prime * result + ((menuIcon == null) ? 0 : menuIcon.hashCode());
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
		final Module other = (Module) obj;
		if (moduleType == null) {
			if (other.moduleType != null)
				return false;
		} else if (!moduleType.equals(other.moduleType))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (moduleName == null) {
			if (other.moduleName != null)
				return false;
		} else if (!moduleName.equals(other.moduleName))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (authName == null) {
			if (other.authName != null)
				return false;
		} else if (!authName.equals(other.authName))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (menuUrl == null) {
			if (other.menuUrl != null)
				return false;
		} else if (!menuUrl.equals(other.menuUrl))
			return false;
		if (moduleDescribe == null) {
			if (other.moduleDescribe != null)
				return false;
		} else if (!moduleDescribe.equals(other.moduleDescribe))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (menuOrder == null) {
			if (other.menuOrder != null)
				return false;
		} else if (!menuOrder.equals(other.menuOrder))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (moduleNo == null) {
			if (other.moduleNo != null)
				return false;
		} else if (!moduleNo.equals(other.moduleNo))
			return false;
		if (menuIcon == null) {
			if (other.menuIcon != null)
				return false;
		} else if (!menuIcon.equals(other.menuIcon))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}