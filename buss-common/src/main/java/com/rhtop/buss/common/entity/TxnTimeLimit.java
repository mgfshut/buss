package com.rhtop.buss.common.entity;

import java.io.Serializable;

/**
 * 交易时限
 * @author mgf
 *
 */
public interface TxnTimeLimit extends Serializable {
	/**
	 * 获取交易开始时间
	 * @return
	 */
	public String getStartTime();
	/**
	 * 获取交易结束时间
	 * @return
	 */
	public String getEndTime();
}
