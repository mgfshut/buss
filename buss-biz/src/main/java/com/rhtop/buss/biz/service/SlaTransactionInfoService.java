/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.SlaTransactionInfo;

public interface SlaTransactionInfoService{
    
	/**
	 * 新增
	 */
	int insertSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteSlaTransactionInfo(String slaTransactionInfoId);
	
	/**
	 * 修改
	 */
	int updateSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo);
	
	/**
	 * 根据Id查找数据
	 */
	SlaTransactionInfo selectByPrimaryKey(String slaTransactionInfoId);
	
	/**
	 * 根据条件查询列表
	 */
	List<SlaTransactionInfo> listSlaTransactionInfos(SlaTransactionInfo slaTransactionInfo);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<SlaTransactionInfo> listPageSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo);
}