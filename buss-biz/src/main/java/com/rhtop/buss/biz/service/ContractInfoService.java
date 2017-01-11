/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.ContractInfo;

public interface ContractInfoService{
    
	/**
	 * 新增
	 */
	int insertContractInfo(ContractInfo contractInfo);
    
	/**
	 * 创建合同、修改交易记录的状态
	 */
	String createContract(ContractInfo con);
	
	/**
	 * 根据ID删除数据
	 */
	int deleteContractInfo(String contractInfoId);
	
	/**
	 * 修改
	 */
	int updateContractInfo(ContractInfo contractInfo);
	
	/**
	 * 根据Id查找数据
	 */
	ContractInfo selectByPrimaryKey(String contractInfoId);
	
	/**
	 * 根据条件查询列表
	 */
	List<ContractInfo> listContractInfos(ContractInfo contractInfo);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<ContractInfo> listPageContractInfo(ContractInfo contractInfo);
}