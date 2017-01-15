/**
 *
 */
package com.rhtop.buss.common.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 客户信息表
 * @author
 * @version
 */
//@XmlRootElement(name = "Customer")
public class Customer {
	private String cusDesc;//客户说明
	
	@JsonIgnore //隐藏返回的数据
	private String updateTime;//修改时间
	
	private String cusType;//客户类型
	
	private String ckStatus;//审核状态
	
	private String cusName;//用户名称
	
	@JsonIgnore
	private String updateUser;//修改人
	
	private String cusLoc;//客户地区
	
	private String createUser;//创建人
	
	private String cusCreateTime;//拜访日期
	
	private String cusAddr;//用户地址
	
	private String flgUpdateTime;//价格修改时间
	
	private String cusCha;//客户渠道
	
	private String customerId;//客户UUID
	
	private String createTime;//创建时间
	
	private List<ContactsInfo> contacts;//该客户的联系人列表
	
	private List<Category> categorys;//该客户的新增品类列表
	
	private Page page;//分页
	
	private String crtQua; // 已成交数量
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getCusDesc(){
		return this.cusDesc;
	}
	
	public void setCusDesc(String cusDesc){
		this.cusDesc = cusDesc;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCusType(){
		return this.cusType;
	}
	
	public void setCusType(String cusType){
		this.cusType = cusType;
	}
	public String getCkStatus(){
		return this.ckStatus;
	}
	
	public void setCkStatus(String ckStatus){
		this.ckStatus = ckStatus;
	}
	public String getCusName(){
		return this.cusName;
	}
	
	public void setCusName(String cusName){
		this.cusName = cusName;
	}
	public String getUpdateUser(){
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}
	public String getCusLoc(){
		return this.cusLoc;
	}
	
	public void setCusLoc(String cusLoc){
		this.cusLoc = cusLoc;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getCusCreateTime(){
		return this.cusCreateTime;
	}
	
	public void setCusCreateTime(String cusCreateTime){
		this.cusCreateTime = cusCreateTime;
	}
	public String getCusAddr(){
		return this.cusAddr;
	}
	
	public void setCusAddr(String cusAddr){
		this.cusAddr = cusAddr;
	}
	public String getFlgUpdateTime(){
		return this.flgUpdateTime;
	}
	
	public void setFlgUpdateTime(String flgUpdateTime){
		this.flgUpdateTime = flgUpdateTime;
	}
	public String getCusCha(){
		return this.cusCha;
	}
	
	public void setCusCha(String cusCha){
		this.cusCha = cusCha;
	}
	public String getCustomerId(){
		return this.customerId;
	}
	
	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cusDesc == null) ? 0 : cusDesc.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((cusType == null) ? 0 : cusType.hashCode());
		result = prime * result + ((ckStatus == null) ? 0 : ckStatus.hashCode());
		result = prime * result + ((cusName == null) ? 0 : cusName.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((cusLoc == null) ? 0 : cusLoc.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((cusCreateTime == null) ? 0 : cusCreateTime.hashCode());
		result = prime * result + ((cusAddr == null) ? 0 : cusAddr.hashCode());
		result = prime * result + ((flgUpdateTime == null) ? 0 : flgUpdateTime.hashCode());
		result = prime * result + ((cusCha == null) ? 0 : cusCha.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
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
		final Customer other = (Customer) obj;
		if (cusDesc == null) {
			if (other.cusDesc != null)
				return false;
		} else if (!cusDesc.equals(other.cusDesc))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (cusType == null) {
			if (other.cusType != null)
				return false;
		} else if (!cusType.equals(other.cusType))
			return false;
		if (ckStatus == null) {
			if (other.ckStatus != null)
				return false;
		} else if (!ckStatus.equals(other.ckStatus))
			return false;
		if (cusName == null) {
			if (other.cusName != null)
				return false;
		} else if (!cusName.equals(other.cusName))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (cusLoc == null) {
			if (other.cusLoc != null)
				return false;
		} else if (!cusLoc.equals(other.cusLoc))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (cusCreateTime == null) {
			if (other.cusCreateTime != null)
				return false;
		} else if (!cusCreateTime.equals(other.cusCreateTime))
			return false;
		if (cusAddr == null) {
			if (other.cusAddr != null)
				return false;
		} else if (!cusAddr.equals(other.cusAddr))
			return false;
		if (flgUpdateTime == null) {
			if (other.flgUpdateTime != null)
				return false;
		} else if (!flgUpdateTime.equals(other.flgUpdateTime))
			return false;
		if (cusCha == null) {
			if (other.cusCha != null)
				return false;
		} else if (!cusCha.equals(other.cusCha))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}

	public List<ContactsInfo> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsInfo> contacts) {
		this.contacts = contacts;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}

	public String getCrtQua() {
		return crtQua;
	}

	public void setCrtQua(String crtQua) {
		this.crtQua = crtQua;
	}
}