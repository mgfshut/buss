/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 代码值
 * @author
 * @version
 */
//@XmlRootElement(name = "CodeValue")
public class CodeValue {
	private String updateUser;//修改人
	
	private String updateTime;//修改时间
	
	private String createUser;//创建人
	
	private String codeMapId;//代码集ID
	
	private String codeValueId;//代码值ID
	
	private String createTime;//创建时间
	
	private String codeValue;//代码值
	
	private String codeValueDescribe;//代码值描述
	
	private Page page;//分页
	
	private String code;//代码集编码
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCreateUser(){
		return this.createUser;
	}
	
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getCodeMapId(){
		return this.codeMapId;
	}
	
	public void setCodeMapId(String codeMapId){
		this.codeMapId = codeMapId;
	}
	public String getCodeValueId(){
		return this.codeValueId;
	}
	
	public void setCodeValueId(String codeValueId){
		this.codeValueId = codeValueId;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCodeValue(){
		return this.codeValue;
	}
	
	public void setCodeValue(String codeValue){
		this.codeValue = codeValue;
	}
	public String getCodeValueDescribe(){
		return this.codeValueDescribe;
	}
	
	public void setCodeValueDescribe(String codeValueDescribe){
		this.codeValueDescribe = codeValueDescribe;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((codeMapId == null) ? 0 : codeMapId.hashCode());
		result = prime * result + ((codeValueId == null) ? 0 : codeValueId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((codeValue == null) ? 0 : codeValue.hashCode());
		result = prime * result + ((codeValueDescribe == null) ? 0 : codeValueDescribe.hashCode());
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
		final CodeValue other = (CodeValue) obj;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (codeMapId == null) {
			if (other.codeMapId != null)
				return false;
		} else if (!codeMapId.equals(other.codeMapId))
			return false;
		if (codeValueId == null) {
			if (other.codeValueId != null)
				return false;
		} else if (!codeValueId.equals(other.codeValueId))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (codeValue == null) {
			if (other.codeValue != null)
				return false;
		} else if (!codeValue.equals(other.codeValue))
			return false;
		if (codeValueDescribe == null) {
			if (other.codeValueDescribe != null)
				return false;
		} else if (!codeValueDescribe.equals(other.codeValueDescribe))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}