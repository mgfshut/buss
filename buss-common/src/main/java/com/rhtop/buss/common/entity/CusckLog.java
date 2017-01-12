package com.rhtop.buss.common.entity;

public class CusckLog {
	
	private String cusckLogId;
	
	private String oprUser;
	
	private String oprTime;
	
	private String customerId;
	
	private String oprName;

	public String getCusckLogId() {
		return cusckLogId;
	}

	public void setCusckLogId(String cusckLogId) {
		this.cusckLogId = cusckLogId;
	}

	public String getOprUser() {
		return oprUser;
	}

	public void setOprUser(String oprUser) {
		this.oprUser = oprUser;
	}

	public String getOprTime() {
		return oprTime;
	}

	public void setOprTime(String oprTime) {
		this.oprTime = oprTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOprName() {
		return oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	@Override
	public String toString() {
		return "CusckLog [cusckLogId=" + cusckLogId + ", oprUser=" + oprUser
				+ ", oprTime=" + oprTime + ", customerId=" + customerId
				+ ", oprName=" + oprName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cusckLogId == null) ? 0 : cusckLogId.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((oprName == null) ? 0 : oprName.hashCode());
		result = prime * result + ((oprTime == null) ? 0 : oprTime.hashCode());
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
		CusckLog other = (CusckLog) obj;
		if (cusckLogId == null) {
			if (other.cusckLogId != null)
				return false;
		} else if (!cusckLogId.equals(other.cusckLogId))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (oprName == null) {
			if (other.oprName != null)
				return false;
		} else if (!oprName.equals(other.oprName))
			return false;
		if (oprTime == null) {
			if (other.oprTime != null)
				return false;
		} else if (!oprTime.equals(other.oprTime))
			return false;
		if (oprUser == null) {
			if (other.oprUser != null)
				return false;
		} else if (!oprUser.equals(other.oprUser))
			return false;
		return true;
	}
	
	
}
