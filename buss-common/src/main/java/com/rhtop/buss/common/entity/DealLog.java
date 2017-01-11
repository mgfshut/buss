/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * 实体: 交易流程日志表
 * @author
 * @version
 */
//@XmlRootElement(name = "DealLog")
public class DealLog {
	private String oprContent;//操作内容
	
	private String transactionInfoId;//交易ID
	
	private String oprUser;//操作人
	
	private String dealLogId;//操作记录ID
	
	private String oprTime;//操作时间
	
	private String oprType;//操作类型
	
	private String oprName;//操作名称
	
	private Page page;//分页
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getOprContent(){
		return this.oprContent;
	}
	
	public void setOprContent(String oprContent){
		this.oprContent = oprContent;
	}
	public String getTransactionInfoId(){
		return this.transactionInfoId;
	}
	
	public void setTransactionInfoId(String transactionInfoId){
		this.transactionInfoId = transactionInfoId;
	}
	public String getOprUser(){
		return this.oprUser;
	}
	
	public void setOprUser(String oprUser){
		this.oprUser = oprUser;
	}
	public String getDealLogId(){
		return this.dealLogId;
	}
	
	public void setDealLogId(String dealLogId){
		this.dealLogId = dealLogId;
	}
	public String getOprTime(){
		return this.oprTime;
	}
	
	public void setOprTime(String oprTime){
		this.oprTime = oprTime;
	}
	public String getOprType(){
		return this.oprType;
	}
	
	public void setOprType(String oprType){
		this.oprType = oprType;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oprContent == null) ? 0 : oprContent.hashCode());
		result = prime * result + ((transactionInfoId == null) ? 0 : transactionInfoId.hashCode());
		result = prime * result + ((oprUser == null) ? 0 : oprUser.hashCode());
		result = prime * result + ((dealLogId == null) ? 0 : dealLogId.hashCode());
		result = prime * result + ((oprTime == null) ? 0 : oprTime.hashCode());
		result = prime * result + ((oprType == null) ? 0 : oprType.hashCode());
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
		final DealLog other = (DealLog) obj;
		if (oprContent == null) {
			if (other.oprContent != null)
				return false;
		} else if (!oprContent.equals(other.oprContent))
			return false;
		if (transactionInfoId == null) {
			if (other.transactionInfoId != null)
				return false;
		} else if (!transactionInfoId.equals(other.transactionInfoId))
			return false;
		if (oprUser == null) {
			if (other.oprUser != null)
				return false;
		} else if (!oprUser.equals(other.oprUser))
			return false;
		if (dealLogId == null) {
			if (other.dealLogId != null)
				return false;
		} else if (!dealLogId.equals(other.dealLogId))
			return false;
		if (oprTime == null) {
			if (other.oprTime != null)
				return false;
		} else if (!oprTime.equals(other.oprTime))
			return false;
		if (oprType == null) {
			if (other.oprType != null)
				return false;
		} else if (!oprType.equals(other.oprType))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}

	public String getOprName() {
		return oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}
}