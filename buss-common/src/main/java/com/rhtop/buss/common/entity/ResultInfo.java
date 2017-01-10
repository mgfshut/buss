package com.rhtop.buss.common.entity;

import java.util.List;

public class ResultInfo {
	
	private String code;//响应消息码
	
	private String message;//响应消息文本
	
	private Object resObject;//数据对象
	
	@SuppressWarnings("rawtypes")
	private List records;//数据列表
	
	private Page page;//分页属性
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
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
