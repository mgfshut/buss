/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.TransactionInfo;

public interface TransactionInfoService{
    
	/**
	 * 新增
	 */
	int insertTransactionInfo(TransactionInfo transactionInfo);
    
	/**
	 * 新增交易记录
	 */
	String createDeal(TransactionInfo tx);
	
	/**
	 * 客户回盘
	 */
	String cusNegotiate(TransactionInfo tx);
	
	/**
	 * 国际部回盘
	 */
	String universeNegotiate(TransactionInfo tx);
	
	/**
	 * 根据ID删除数据
	 */
	int deleteTransactionInfo(String transactionInfoId);
	
	/**
	 * 修改
	 */
	int updateTransactionInfo(TransactionInfo transactionInfo);
	
	/**
	 * 根据Id查找数据
	 */
	TransactionInfo selectByPrimaryKey(String transactionInfoId);
	
	/**
	 * 根据条件查询列表
	 */
	List<TransactionInfo> listTransactionInfos(TransactionInfo transactionInfo);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<TransactionInfo> listPageTransactionInfo(TransactionInfo transactionInfo);
}