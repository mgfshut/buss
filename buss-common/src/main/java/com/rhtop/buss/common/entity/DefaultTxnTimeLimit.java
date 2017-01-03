package com.rhtop.buss.common.entity;

public class DefaultTxnTimeLimit implements TxnTimeLimit{
	private String startTime;
	private String endTime;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1308543062642588969L;

	public DefaultTxnTimeLimit(String start,String end){
		this.startTime = start;
		this.endTime = end;
	}
	
	@Override
	public String getStartTime() {
		// TODO Auto-generated method stub
		return startTime;
	}

	@Override
	public String getEndTime() {
		// TODO Auto-generated method stub
		return endTime;
	}

}
