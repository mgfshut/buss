/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 联系人信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "ContactsInfo")
public class ContactsInfo {
	private String createUser;//创建人
	
	private String contactsInfoId;//记录ID
	
	private String contactTel;//联系人电话
	
	private String updateUser;//修改人
	
	private String createTime;//创建时间
	
	private String contactName;//联系人姓名
	
	private String contactPhone;//联系人手机
	
	private String contactMail;//联系人邮箱
	
	private String contactAddr;//联系人地址
	
	private String comm;//联系人备注
	
	private String customerId;//客户ID
	
	private String updateTime;//修改时间
	
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
	public String getContactsInfoId(){
		return this.contactsInfoId;
	}
	
	public void setContactsInfoId(String contactsInfoId){
		this.contactsInfoId = contactsInfoId;
	}
	public String getContactTel(){
		return this.contactTel;
	}
	
	public void setContactTel(String contactTel){
		this.contactTel = contactTel;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getContactName(){
		return this.contactName;
	}
	
	public void setContactName(String contactName){
		this.contactName = contactName;
	}
	public String getCustomerId(){
		return this.customerId;
	}
	
	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	
	
	
	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((contactsInfoId == null) ? 0 : contactsInfoId.hashCode());
		result = prime * result + ((contactTel == null) ? 0 : contactTel.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
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
		final ContactsInfo other = (ContactsInfo) obj;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (contactsInfoId == null) {
			if (other.contactsInfoId != null)
				return false;
		} else if (!contactsInfoId.equals(other.contactsInfoId))
			return false;
		if (contactTel == null) {
			if (other.contactTel != null)
				return false;
		} else if (!contactTel.equals(other.contactTel))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
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