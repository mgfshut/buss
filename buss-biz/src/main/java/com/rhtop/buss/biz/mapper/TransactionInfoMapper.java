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
	 
	/**
     * 客户经理，部门经理，决策委员会（createUser赋值为空） 查看 交易列表
     */
	List<TransactionInfo> listPageTransactionInfoBycreateUser(TransactionInfo transactionInfo);
   
	/**
     * 国际采购人员回盘信息
     * @author lujin
     * @date 2017-1-16
     * @param transactionInfo
     * @return
     */
	List<TransactionInfo> listPageInfo (TransactionInfo transactionInfo);
	
	/**
     * 根据条件查询列表
     * 根据状态查询 
     * @author lujin
     * @date 2017-1-19
     * @param transactionInfo
     * transactionInfo.setTxStatus("21")
     * @return
     */
	List<TransactionInfo> listPageInfoByTxStatus(TransactionInfo transactionInfo);
	
}