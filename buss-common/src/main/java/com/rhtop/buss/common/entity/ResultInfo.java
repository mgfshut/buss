package com.rhtop.buss.common.entity;

import java.util.List;

public class ResultInfo {
	private String code;
	private String message;
	private Object resObject;
	@SuppressWarnings("rawtypes")
	private List records;
	
	
	@SuppressWarnings("rawtypes")
	public List getRecords() {
		return records;
	}
	@SuppressWarnings("rawtypes")
	public void setRecords(List records) {
		this.records = records;
	}
	public void setResObject(Object resObject) {
		this.resObject = resObject;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResObject() {
		return resObject;
	}
	
}
