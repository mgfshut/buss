/**
 *
 */
package com.rhtop.buss.common.entity;

import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 代码集
 * @author
 * @version
 */
//@XmlRootElement(name = "CodeMap")
public class CodeMap {
	private String updateUser;//修改人
	
	private String createTime;//创建时间
	
	private String code;//代码集编码
	
	private String updateTime;//修改时间
	
	private String codeMapName;//代码集名称
	
	private String createUser;//创建人
	
	private String codeMapId;//代码集ID
	
	private Page page;//分页
	
	private List<CodeValue> CodeValueList;//代码值列表
	
	public List<CodeValue> getCodeValueList() {
		return CodeValueList;
	}

	public void setCodeValueList(List<CodeValue> codeValueList) {
		CodeValueList = codeValueList;
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
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCode(){
		return this.code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getCodeMapName(){
		return this.codeMapName;
	}
	
	public void setCodeMapName(String codeMapName){
		this.codeMapName = codeMapName;
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
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((codeMapName == null) ? 0 : codeMapName.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((codeMapId == null) ? 0 : codeMapId.hashCode());
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
		final CodeMap other = (CodeMap) obj;
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (codeMapName == null) {
			if (other.codeMapName != null)
				return false;
		} else if (!codeMapName.equals(other.codeMapName))
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
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}