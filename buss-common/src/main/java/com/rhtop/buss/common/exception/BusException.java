package com.rhtop.buss.common.exception;

public class BusException extends RuntimeException {
	public final static String ERROR_CODE = "300";
	public final static String SUCCESS_CODE = "200";
	private String code = SUCCESS_CODE;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5058689238804568643L;
	
	public BusException() {
	}
	public BusException(String message){
		super(message);
		this.setCode(ERROR_CODE);
	}
	public BusException(Throwable cause){
		super(cause);
		this.setCode(ERROR_CODE);
	}
	public BusException(String message, Throwable cause){
		super(message, cause);
		this.setCode(ERROR_CODE);
	}
	public BusException(String message, String code){
		super(message);
		this.setCode(code);
	}
	public BusException(String message, String code,Throwable cause){
		super(message, cause);
		this.setCode(code);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
