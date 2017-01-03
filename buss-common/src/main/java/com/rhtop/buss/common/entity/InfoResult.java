package com.rhtop.buss.common.entity;

import java.util.List;

public class InfoResult<T> {
	private String code;
	private String msg;
	private List<T> resList;
	private T resObject;
	private Page page;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getResList() {
		return resList;
	}
	public void setResList(List<T> resList) {
		this.resList = resList;
	}
	public T getResObject() {
		return resObject;
	}
	public void setResObject(T resObject) {
		this.resObject = resObject;
	}
	
	
}
