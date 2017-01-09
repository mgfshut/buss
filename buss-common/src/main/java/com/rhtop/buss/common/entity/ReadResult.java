package com.rhtop.buss.common.entity;

public class ReadResult<T> {
	private String code;
	private String message;
	private T resObject;
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
	public T getResObject() {
		return resObject;
	}
	public void setResObject(T resObject) {
		this.resObject = resObject;
	}
	
}
