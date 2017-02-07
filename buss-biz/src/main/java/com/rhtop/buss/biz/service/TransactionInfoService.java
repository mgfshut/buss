/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;

public interface TransactionInfoService{
    
	/**
	 * 新增
	 */
	int insertTransactionInfo(TransactionInfo transactionInfo);
    
	/**
	 * 新增交易记录
	 */
	String createDeal(TransactionInfo tx) throws Exception;
	
	/**
	 * 客户回盘
	 */
	ResultInfo cusNegotiate(ResultInfo readResult, TransactionInfo tx) throws Exception;
	
	/**
	 * 国际部回盘
	 */
	String universeNegotiate(TransactionInfo tx) throws Exception;
	
	/**
	 * 决委会回盘确认
	 */
	String domainNegotiate(TransactionInfo tx) throws Exception;
	
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
	
	/**
	 * 客户经理，分部经理，决策委员会 查询交易列表
	 * @author lujin
	 * @date 2017-1-13
	 * @param transactionInfo
	 * @return
	 */
	List<TransactionInfo> listPageTransactionInfoBycreateUser(TransactionInfo transactionInfo);
	
	/**
	 * 客户经理，部门经理,决策委员会 查看交易详情
	 * @author lujin
	 * @date 2017-1-13
	 * @param transactionInfo
	 * @return
	 */
	TransactionInfo selectTransactionInfo(TransactionInfo transactionInfo);
	
	/**
	 * 国际部查询 查看回盘信息
	 * 已回盘列表:交易状态为21 
	 * 未回盘列表:交易状态为20 
	 * @author lujin
	 * @date 2017-1-19
	 * @param transactionInfo
	 * @return
	 */
	List<TransactionInfo> listPageInfo(TransactionInfo transactionInfo);
	
	/**
	 * 国际人员 
	 * 回盘与报盘的详情信息
	 * 返回 客户信息与品类信息以及交易的回盘记录
	 * @author lujin
	 * @date 2017-1-22
	 * @param transactionInfo
	 * @return
	 */
	TransactionInfo CustAndCateAndPriceInfo (TransactionInfo transactionInfo);
	/**
	 * 分部经理查询交易
	 * @author mgf
	 * @date 2017年2月7日 下午1:59:24 
	 * @param userId
	 * @param transactionInfo
	 * @return
	 */
	List<TransactionInfo> listPageTransactionInfoByFB(
			String userId,TransactionInfo transactionInfo);
	
}
