/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.TransactionInfo;

public interface TransactionInfoMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(TransactionInfo transactionInfo);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String transactionInfoId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(TransactionInfo transactionInfo);
    /**
	 * 根据主键查询对象
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