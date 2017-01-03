package com.rhtop.buss.common.service;


/**
 * 交易时限服务
 * @author mgf
 *
 */
public interface TxnTimeLimitService {
	/**
	 * 交易开始时间
	 * @return
	 */
	public boolean isTxnTime();
	
}
