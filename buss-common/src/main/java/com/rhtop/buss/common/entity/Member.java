/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 用户信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "Member")
public class Member {
	private String updateUser;//修改人
	
	private String memberSex;//性别
	
	private String createTime;//创建时间
	
	private String memberName;//姓名
	
	private String memberEmail;//邮箱
	
	private String departmentCode;//部门编码
	
	private String memberIdCardNo;//身份证号码
	
	private String updateTime;//修改时间
	
	private String memberPhone;//手机号
	
	private String memberId;//用户ID
	
	private String createUser;//创建人
	
	private String companyCity;//所在城市
	
	private String headImage;//用户头像地址
	
	private Page page;//分页
	
	private String userName;//用户名
	
	private String userStatus;//用户状态
	
	private String token;//用户令牌
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getMemberSex(){
		return this.memberSex;
	}
	
	public void setMemberSex(String memberSex){
		this.memberSex = memberSex;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getMemberName(){
		return this.memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	public String getMemberEmail(){
		return this.memberEmail;
	}
	
	public void setMemberEmail(String memberEmail){
		this.memberEmail = memberEmail;
	}
	public String getDepartmentCode(){
		return this.departmentCode;
	}
	
	public void setDepartmentCode(String departmentCode){
		this.departmentCode = departmentCode;
	}
	public String getMemberIdCardNo(){
		return this.memberIdCardNo;
	}
	
	public void setMemberIdCardNo(String memberIdCardNo){
		this.memberIdCardNo = memberIdCardNo;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getMemberPhone(){
		return this.memberPhone;
	}
	
	public void setMemberPhone(String memberPhone){
		this.memberPhone = memberPhone;
	}
	public String getMemberId(){
		return this.memberId;
	}
	
	public void setMemberId(String memberId){
		this.memberId = memberId;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getCompanyCity(){
		return this.companyCity;
	}
	
	public void setCompanyCity(String companyCity){
		this.companyCity = companyCity;
	}
	public String getHeadImage(){
		return this.headImage;
	}
	
	public void setHeadImage(String headImage){
		this.headImage = headImage;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((memberSex == null) ? 0 : memberSex.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result + ((memberEmail == null) ? 0 : memberEmail.hashCode());
		result = prime * result + ((departmentCode == null) ? 0 : departmentCode.hashCode());
		result = prime * result + ((memberIdCardNo == null) ? 0 : memberIdCardNo.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((memberPhone == null) ? 0 : memberPhone.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((companyCity == null) ? 0 : companyCity.hashCode());
		result = prime * result + ((headImage == null) ? 0 : headImage.hashCode());
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
		final Member other = (Member) obj;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (memberSex == null) {
			if (other.memberSex != null)
				return false;
		} else if (!memberSex.equals(other.memberSex))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (memberName == null) {
			if (other.memberName != null)
				return false;
		} else if (!memberName.equals(other.memberName))
			return false;
		if (memberEmail == null) {
			if (other.memberEmail != null)
				return false;
		} else if (!memberEmail.equals(other.memberEmail))
			return false;
		if (departmentCode == null) {
			if (other.departmentCode != null)
				return false;
		} else if (!departmentCode.equals(other.departmentCode))
			return false;
		if (memberIdCardNo == null) {
			if (other.memberIdCardNo != null)
				return false;
		} else if (!memberIdCardNo.equals(other.memberIdCardNo))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (memberPhone == null) {
			if (other.memberPhone != null)
				return false;
		} else if (!memberPhone.equals(other.memberPhone))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (companyCity == null) {
			if (other.companyCity != null)
				return false;
		} else if (!companyCity.equals(other.companyCity))
			return false;
		if (headImage == null) {
			if (other.headImage != null)
				return false;
		} else if (!headImage.equals(other.headImage))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}