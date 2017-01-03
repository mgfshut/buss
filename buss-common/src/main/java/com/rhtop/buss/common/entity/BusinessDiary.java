/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 业务日志表
 * @author
 * @version
 */
//@XmlRootElement(name = "BusinessDiary")
public class BusinessDiary {
	private String businessDiaryId;//操作记录ID
	
	private String oprTime;//操作时间
	
	private String oprContent;//操作内容
	
	private String oprType;//操作类型
	
	private String oprUser;//操作人
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getBusinessDiaryId(){
		return this.businessDiaryId;
	}
	
	public void setBusinessDiaryId(String businessDiaryId){
		this.businessDiaryId = businessDiaryId;
	}
	public String getOprTime(){
		return this.oprTime;
	}
	
	public void setOprTime(String oprTime){
		this.oprTime = oprTime;
	}
	public String getOprContent(){
		return this.oprContent;
	}
	
	public void setOprContent(String oprContent){
		this.oprContent = oprContent;
	}
	public String getOprType(){
		return this.oprType;
	}
	
	public void setOprType(String oprType){
		this.oprType = oprType;
	}
	public String getOprUser(){
		return this.oprUser;
	}
	
	public void setOprUser(String oprUser){
		this.oprUser = oprUser;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessDiaryId == null) ? 0 : businessDiaryId.hashCode());
		result = prime * result + ((oprTime == null) ? 0 : oprTime.hashCode());
		result = prime * result + ((oprContent == null) ? 0 : oprContent.hashCode());
		result = prime * result + ((oprType == null) ? 0 : oprType.hashCode());
		result = prime * result + ((oprUser == null) ? 0 : oprUser.hashCode());
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
		final BusinessDiary other = (BusinessDiary) obj;
		if (businessDiaryId == null) {
			if (other.businessDiaryId != null)
				return false;
		} else if (!businessDiaryId.equals(other.businessDiaryId))
			return false;
		if (oprTime == null) {
			if (other.oprTime != null)
				return false;
		} else if (!oprTime.equals(other.oprTime))
			return false;
		if (oprContent == null) {
			if (other.oprContent != null)
				return false;
		} else if (!oprContent.equals(other.oprContent))
			return false;
		if (oprType == null) {
			if (other.oprType != null)
				return false;
		} else if (!oprType.equals(other.oprType))
			return false;
		if (oprUser == null) {
			if (other.oprUser != null)
				return false;
		} else if (!oprUser.equals(other.oprUser))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}