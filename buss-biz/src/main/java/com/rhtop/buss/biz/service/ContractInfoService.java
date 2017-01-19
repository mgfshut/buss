/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.text.ParseException;
import java.util.List;

import com.rhtop.buss.common.entity.ContractInfo;

public interface ContractInfoService{
    
	/**
	 * 新增
	 */
	int insertContractInfo(ContractInfo contractInfo);
    
	/**
	 * 创建合同、修改交易记录的状态
	 * @throws ParseException 
	 */
	String createContract(ContractInfo con) throws ParseException;
	
	/**
	 * 总经理审核合同（修改状态 从10修改为20）
	 */
	String checkContract(ContractInfo con);
	
	/**
	 * 行政人员审核合同（修改状态 从20修改为30）
	 */
	String contractStamp(ContractInfo con);
	
	/**
	 * 合同下载：返回合同URL
	 */
	List<String> downloadContract(ContractInfo con);
	
	/**
	 * 财务审核合同（修改状态 从30修改为40）
	 */
	String treasurerCheckContract(ContractInfo con);
	
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
	/**
	 * 根据合同ID获取合同打印相关信息
	 * @author mgf
	 * @date 2017年1月15日 下午1:31:43 
	 * @param contractInfoId
	 * @return
	 */
	ContractInfo printByContractInfoId(String contractInfoId);
}