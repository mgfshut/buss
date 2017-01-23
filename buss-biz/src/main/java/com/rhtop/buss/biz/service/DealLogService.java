/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.DealLog;
import com.rhtop.buss.common.entity.TransactionInfo;

public interface DealLogService{
    
	/**
	 * 新增
	 */
	int insertDealLog(DealLog dealLog);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteDealLog(String dealLogId);
	
	/**
	 * 修改
	 */
	int updateDealLog(DealLog dealLog);
	
	/**
	 * 根据Id查找数据
	 */
	DealLog selectByPrimaryKey(String dealLogId);
	
	/**
	 * 根据条件查询列表
	 */
	List<DealLog> listDealLogs(DealLog dealLog);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<DealLog> listPageDealLog(DealLog dealLog);
	
	/**
	 * 得到交易记录的流程的详情
	 */
	public List<TransactionInfo> DealLogList(TransactionInfo transactionInfo);
}