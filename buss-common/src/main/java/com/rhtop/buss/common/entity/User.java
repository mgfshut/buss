/**
 *
 */
package com.rhtop.buss.common.entity;

import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 登录用户表
 * @author
 * @version
 */
//@XmlRootElement(name = "User")
public class User {
	private String updateUser;//修改人
	
	private String userType;//用户类型
	
	private String userPassword;//密码
	
	private String createUser;//创建人
	
	private String pwErrorNumber;//密码错误次数
	
	private String userName;//用户名
	
	private String isNotFirstPw;//是否初始密码
	
	private String createTime;//创建时间
	
	private String lastLoginTime;//上次登录时间
	
	private String userId;//用户ID
	
	private String userStatus;//用户状态
	
	private String updateTime;//修改时间
	
	private Page page;//分页
	
	private List<String> permissionList;
	
	private String newPassword;//新密码
	
	private String newPassword1;//确认新密码
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword1() {
		return newPassword1;
	}

	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}

	public List<String> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}

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
	public String getUserType(){
		return this.userType;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
	}
	public String getUserPassword(){
		return this.userPassword;
	}
	
	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getPwErrorNumber(){
		return this.pwErrorNumber;
	}
	
	public void setPwErrorNumber(String pwErrorNumber){
		this.pwErrorNumber = pwErrorNumber;
	}
	public String getUserName(){
		return this.userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getIsNotFirstPw(){
		return this.isNotFirstPw;
	}
	
	public void setIsNotFirstPw(String isNotFirstPw){
		this.isNotFirstPw = isNotFirstPw;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getLastLoginTime(){
		return this.lastLoginTime;
	}
	
	public void setLastLoginTime(String lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserStatus(){
		return this.userStatus;
	}
	
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((userPassword == null) ? 0 : userPassword.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((pwErrorNumber == null) ? 0 : pwErrorNumber.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((isNotFirstPw == null) ? 0 : isNotFirstPw.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((lastLoginTime == null) ? 0 : lastLoginTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		final User other = (User) obj;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (pwErrorNumber == null) {
			if (other.pwErrorNumber != null)
				return false;
		} else if (!pwErrorNumber.equals(other.pwErrorNumber))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (isNotFirstPw == null) {
			if (other.isNotFirstPw != null)
				return false;
		} else if (!isNotFirstPw.equals(other.isNotFirstPw))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (lastLoginTime == null) {
			if (other.lastLoginTime != null)
				return false;
		} else if (!lastLoginTime.equals(other.lastLoginTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userStatus == null) {
			if (other.userStatus != null)
				return false;
		} else if (!userStatus.equals(other.userStatus))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}